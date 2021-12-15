package day15

import java.io.File
import java.util.*

object Day15 {

    @OptIn(ExperimentalStdlibApi::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val (maxy, maxx, chitons) = makeLarger(parseIntMatrix(readLines("inputs/input15s.text")))
        val dmap = Array(maxx) { IntArray(maxy) { Int.MAX_VALUE } }
        val visitQueue = PriorityQueue<Triple<Int,Int,Int>>(compareBy { it.third })

        visitQueue.add(Triple(0, 0, 0))
        dmap[0][0] = 0

        outer@
        while (! visitQueue.isEmpty()) {
            val current = visitQueue.remove()
            for (deltas in arrayOf(Pair(0, -1), Pair(0, 1), Pair(1, 0), Pair(-1, 0))) {
                val xc: Int = current.first + deltas.first
                val yc: Int = current.second + deltas.second
                if (xc.coerceAtMost(yc) >= 0 && xc < maxx && yc < maxy) {
                    val costToNode = dmap[current.first][current.second] + chitons[xc][yc]
                    if (costToNode < dmap[xc][yc]) {
                        dmap[xc][yc] = costToNode
                        if (xc == maxx-1 && yc == maxy-1) break@outer
                        visitQueue.add(Triple(xc, yc, costToNode))
                    }
                }
            }
        }

        println(dmap[maxx-1][maxy-1])
    }

    private fun makeLarger(small: Triple<Int, Int, Array<IntArray>>): Triple<Int, Int, Array<IntArray>> {
        val (maxy, maxx, chitons) = small
        val nm = Array(maxx * 5) { IntArray(maxy * 5) }
        for (mx in 0 until 5)
            for (my in 0 until 5)
                for (y in 0 until maxy)
                    for (x in 0 until maxx)
                        nm[mx * maxx + x][my * maxy + y] = ((chitons[x][y] + (mx + my) - 1) % 9) + 1

        return Triple(maxx * 5, maxy * 5, nm)
    }

    fun parseIntMatrix(lines: List<String>): Triple<Int, Int, Array<IntArray>> {
        val maxy = lines.size; val maxx = lines[0].length
        val values = Array(maxx) { IntArray(maxy) }
        for (y in 0 until maxy) {
            val line = lines[y]
            for (x in 0 until maxx) {
                values[x][y] = ("" + line[x]).toInt()
            }
        }
        return Triple(maxy, maxx, values)
    }

    fun readLines(fileName: String): List<String> = File(fileName).useLines { it.toList() }
}
