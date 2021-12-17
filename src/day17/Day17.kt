package day17

import java.io.File
import kotlin.math.abs
import kotlin.math.max

object Day17 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = File("inputs/input17.text").useLines { it.toList() }
        lines.forEach(Day17::solve)
    }

    private fun solve(l: String) {
        val (x1,x2,y1,y2) = Regex("[-0-9]+").findAll(l).map { it.value.toInt() }.toList()

        fun run(xdd: Int, ydd: Int): Pair<Boolean, Int> {
            var (x, y, xd, yd, maxy) = listOf(0, 0, xdd, ydd, Int.MIN_VALUE)

            while (x <= x2 && y >= y1) {
                if (x in x1..x2 && y in y1..y2) return Pair(true, maxy)
                x += xd
                y += yd

                maxy = max(maxy, y)

                xd += if (xd != 0) -(abs(xd)/xd) else 0
                yd--
            }

            return Pair(false, maxy)
        }

        var totalmaxy = Int.MIN_VALUE
        var matchcnt = 0

        for (xs in 1 .. 1000)
            for (ys in -1000..1000) {
                val (success, max)  = run(xs,ys)
                if (success) {
                    matchcnt++
                    totalmaxy = max(totalmaxy, max)
                }
            }

        println("matchcnt = $matchcnt , max=$totalmaxy")
    }
}