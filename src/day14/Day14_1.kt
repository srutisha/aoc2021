package day14

import day13.Day13

object Day14_1 {
    @OptIn(ExperimentalStdlibApi::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = Day13.readLines("inputs/input14s.text")
        val matcher: MutableMap<String, String> = mutableMapOf()
        val initial = lines[0]

        for (index in 2 until lines.size) {
            val line = lines[index].split(" -> ")
            matcher.put(line[0], line[1])
        }

        fun solveOnce(s: String): String {
            val agg: StringBuilder = StringBuilder()
            for (idx in 0 until s.length-1) {
                val tuple = s.substring(idx, idx+2)
                val letter = matcher.get(tuple)
                agg.append(tuple.get(0))
                agg.append(letter)
            }
            agg.append(s.last())
            return agg.toString()
        }

        fun charQuantities(s: String): Map<Char, Int> {
            val cm: MutableMap<Char, Int> = mutableMapOf()
            for (c in s) {
                cm.merge(c, 1, Int::plus)
            }
            return cm.toMap()
        }
        var solved = solveOnce(initial)
        for (i in 1 until 10) solved = solveOnce(solved)
        val qties = charQuantities(solved)
        val delta = (qties.map { q -> q.value }.max()!!) - (qties.map { q -> q.value }.min()!!)

        println(delta)
    }
}