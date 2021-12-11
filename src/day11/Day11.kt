package day11

import java.io.File
import kotlin.math.max
import kotlin.math.min

object Day11 {

    @JvmStatic
    fun main(args: Array<String>) {
        val lines = readLines("inputs/input11.text")
        var (maxy, maxx, octos) = parseIntMatrix(lines)

        fun doForOctos(fn: (x: Int, y: Int) -> Unit) {
            for (y in 0 until maxy) {
                for (x in 0 until maxx) {
                    fn.invoke(x, y)
                }
            }
        }

        fun countZeros(): Int {
            var cz = 0
            doForOctos { x, y -> if (octos[x][y] == 0) cz++ }
            return cz
        }

        fun runStep(): Int {
            doForOctos { x, y -> octos[x][y]++ }

            fun checkPosition(x: Int, y: Int) {
                if (octos[x][y] > 9) {
                    octos[x][y] = 0

                    for (dy in max(0, y - 1)..min(maxy - 1, y + 1)) {
                        for (dx in max(0, x - 1)..min(maxx - 1, x + 1)) {
                            if (octos[dx][dy] != 0) octos[dx][dy]++
                            checkPosition(dx, dy)
                        }
                    }
                }
            }

            doForOctos { x, y -> checkPosition(x, y) }
            return countZeros()
        }

        var flashCount = 0
        for (step in 1..100) flashCount += runStep()
        println("Step 1: $flashCount")

        var stepCount = 0; octos = parseIntMatrix(lines).third

        while (countZeros() != 100) {
            runStep()
            stepCount++
        }
        println("Step 2: $stepCount")
    }

    fun parseIntMatrix(lines: List<String>): Triple<Int, Int, Array<IntArray>> {
        val maxy = lines.size; val maxx = lines[0].length
        val heightmap = Array(maxx) { IntArray(maxy) }
        for (y in 0 until maxy) {
            val line = lines[y]
            for (x in 0 until maxx) {
                heightmap[x][y] = ("" + line[x]).toInt()
            }
        }
        return Triple(maxy, maxx, heightmap)
    }

    fun readLines(fileName: String): List<String> = File(fileName).useLines { it.toList() }
}