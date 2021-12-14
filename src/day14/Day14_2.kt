package day14

import day13.Day13

object Day14_2 {
    @OptIn(ExperimentalStdlibApi::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = Day13.readLines("inputs/input14s.text")
        val matcher: MutableMap<String, String> = mutableMapOf()
        val pairMap: MutableMap<String, Long> = mutableMapOf()
        val letterFrequency: MutableMap<Char, Long> = mutableMapOf()

        for (idx in 0 until  lines[0].length-1)
            pairMap.merge(lines[0].substring(idx, idx+2), 1, Long::plus)

        for (c in lines[0])
            letterFrequency.merge(c, 1, Long::plus)

        for (index in 2 until lines.size) {
            val line = lines[index].split(" -> ")
            matcher[line[0]] = line[1]
        }

        fun solveOnce(oldPairMap: Map<String, Long>): Map<String, Long> {
            val newPairMap: MutableMap<String, Long> = mutableMapOf()
            for ((k, v) in oldPairMap) {
                val newChar = matcher[k]!!
                newPairMap.merge("" + k[0] + newChar, v, Long::plus)
                newPairMap.merge("" + newChar + k[1], v, Long::plus)
                letterFrequency.merge(newChar[0], v, Long::plus) // side effect! whoops.
            }
            return newPairMap.toMap()
        }

        var solved = solveOnce(pairMap)
        for (i in 1 until 40) solved = solveOnce(solved)

        val delta = (letterFrequency.map { q -> q.value }.max()!!) - (letterFrequency.map { q -> q.value }.min()!!)

        println(delta)
    }
}