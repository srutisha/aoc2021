package day16

import java.io.File
import java.lang.IllegalStateException

object Day16 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = File("inputs/input16s.text").useLines { it.toList() }
        lines.forEach(::solve)
    }

    class StringBitStream(hex: String) {
        var bitPos = 0
        private val bitString = hex.map { c -> ("" + c).toInt(16).toString(2).padStart(4, '0') }.joinToString("")

        fun getNext(sz: Int): Long {
            val chunk = bitString.substring(bitPos, bitPos + sz)
            bitPos += sz
            return chunk.toLong(2)
        }
    }

    private fun solve(s: String) {
        var versionSum: Long = 0
        val hbs = StringBitStream(s)

        fun parsePacket(): Long {
            val version = hbs.getNext(3)
            val type = hbs.getNext(3)
            versionSum += version

            if (type == 4L) {
                var next: Long
                var nb: Long = 0

                do {
                    next = hbs.getNext(5)
                    if (nb > 0) nb = nb shl 4
                    nb += next and 0b1111
                } while (next shr 4 > 0)

                return nb
            } else {
                val lengthType = hbs.getNext(1)
                val packetValues: MutableList<Long> = mutableListOf()
                if (lengthType == 0L) {
                    val lengthInBits = hbs.getNext(15)
                    val currentPos = hbs.bitPos
                    while(hbs.bitPos < currentPos + lengthInBits) {
                        packetValues.add(parsePacket())
                    }
                } else {
                    val lengthInPackets = hbs.getNext(11)
                    for (pi in 0 until lengthInPackets) {
                        packetValues.add(parsePacket())
                    }
                }
                return when(type.toInt()) {
                    0 -> packetValues.sum()
                    1 -> packetValues.fold(1, Long::times)
                    2 -> packetValues.min()!!
                    3 -> packetValues.max()!!
                    5 -> if (packetValues[0] > packetValues[1]) 1 else 0
                    6 -> if (packetValues[0] < packetValues[1]) 1 else 0
                    7 -> if (packetValues[0] == packetValues[1]) 1 else 0
                    else -> throw IllegalStateException("Unknown type: $type")
                }
            }
        }

        println("packet result = " + parsePacket())
        println("version sum = $versionSum")
    }
}
