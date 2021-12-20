package day20

import java.io.File

object Day20 {
    @JvmStatic
    fun main(args: Array<String>) {
        val offset = 2
        val numberOfEnhancements = 50
        val lines = File("inputs/input20t.text").useLines { it.toList() }
        var currentPicture: MutableMap<Pair<Int,Int>, Char> = mutableMapOf()
        val xs = lines[2].length
        val ys = lines.size - 2
        var infiniteChar = '.'

        for ((yi, line) in lines.drop(2).withIndex())
            for (xi in line.indices)
                currentPicture[Pair(xi, yi)] = line[xi]

        fun window(x: Int, y: Int): String {
            val sb:StringBuilder = StringBuilder()
            for (yi in y-1 .. y + 1)
                for (xi in x-1 .. x+1)
                    sb.append(currentPicture.getOrDefault(Pair(xi,yi), infiniteChar))
            return sb.toString()
        }

        fun toIdx(s: String): Int {
            var n = 0
            for (c in s) {
                n = n shl 1
                if (c == '#') n++
            }
            return n
        }

        fun newChar(x: Int, y: Int): Char = lines[0][toIdx(window(x, y))]

        for (i in 1 .. numberOfEnhancements) {
            val newPicture: MutableMap<Pair<Int,Int>, Char> = mutableMapOf()
            val xrange = (- i * offset).. (xs + (i * offset))
            val yrange = (- i * offset).. (ys + (i * offset))
            for (y in yrange) {
                for (x in xrange) {
                    newPicture[Pair(x, y)] = newChar(x, y)
                }
            }
            infiniteChar = newChar(-1000, -1000)
            currentPicture = newPicture
        }

        println(currentPicture.values.filter { it == '#' }.size)
    }
}
