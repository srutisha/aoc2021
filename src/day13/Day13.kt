package day13

import java.io.File

object Day13 {

    enum class Dir { X, Y }

    @OptIn(ExperimentalStdlibApi::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = readLines("inputs/input13t2.text")
        val dots: MutableSet<Pair<Int,Int>> = mutableSetOf()
        val folds: MutableList<Pair<Dir, Int>> = mutableListOf()
        var endIdx = 0

        for ((index, line) in lines.withIndex()) {
            if (line.isEmpty()) { endIdx = index; break }
            val (x, y) = line.split(",")
            dots.add(Pair(x.toInt(), y.toInt()))
        }

        for (index in endIdx+1 until lines.size) {
            val line = lines[index]
            val d = if (line[11] == 'y') Dir.Y else Dir.X
            val n = line.substring(13).toInt()
            folds.add(Pair(d, n))
        }

        fun fold(tofold: Set<Pair<Int,Int>>, fold: Pair<Dir, Int>): MutableSet<Pair<Int,Int>> {
            val newDots: MutableSet<Pair<Int,Int>> = mutableSetOf()
            for (dot in tofold) {
                val topivot = if (fold.first == Dir.X) dot.first else dot.second
                val pivoted = if (topivot > fold.second) 2 * fold.second - topivot else topivot
                newDots.add(if (fold.first == Dir.X) Pair(pivoted, dot.second) else Pair(dot.first, pivoted))
            }
            return newDots
        }

        // Step 1
        // println(fold(dots, folds[0]).size)

        printDots(folds.scan(dots) { cd, f -> fold(cd, f) }.last())
    }

    private fun printDots(dots: MutableSet<Pair<Int, Int>>) {
        val my = dots.map { it.second }.max()!!
        val mx = dots.map { it.first }.max()!!
        val canvas = Array(my + 1) { CharArray(mx + 1) { ' ' } }
        for (dot in dots) canvas[dot.second][dot.first] = '*'
        for (y in 0 .. my) {
            for (x in 0 .. mx) print(canvas[y][x])
            println()
        }
    }

    fun readLines(fileName: String): List<String> = File(fileName).useLines { it.toList() }
}
