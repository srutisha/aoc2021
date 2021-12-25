package day25

import java.io.File

object Day25 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = File("inputs/input25t.text").useLines { it.toList() }
        val cucumbers: MutableMap<Pair<Int, Int>, Char> = mutableMapOf()
        val yd = lines.size
        val xd = lines[0].length
        var moveCount = 1

        for ((y, line) in lines.withIndex()) {
            for ((x, char) in line.withIndex()) {
                if (char != '.') cucumbers[Pair(x, y)] = char
            }
        }

        fun move(c: Char, dx: Int, dy: Int): Boolean {
            val moveSet = cucumbers
                .filter { it.value == c }
                .filter {
                    !cucumbers.contains(
                        Pair(
                            (it.key.first + dx) % xd,
                            (it.key.second + dy) % yd
                        )
                    )
                }

            moveSet.forEach {
                cucumbers.remove(it.key)
                cucumbers[Pair((it.key.first + dx) % xd, (it.key.second + dy) % yd)] = it.value
            }

            return moveSet.isNotEmpty()
        }

        fun moveOneStep():Boolean {
            val m1 = move('>', 1, 0)
            val m2 = move('v', 0, 1) // don't short-circuit the second move!
            return m1 || m2
        }

        while (moveOneStep()) ++moveCount
        println(moveCount)
    }
}