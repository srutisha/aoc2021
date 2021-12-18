package day18

import java.io.File
import kotlin.math.max

object Day18 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = File("inputs/input18t.text").useLines { it.toList() }
        val elements = lines.map(::lexer)
        partOne(elements)
        partTwo(elements)
    }

    private fun partTwo(elements: List<List<Element>>) {
        var max = Long.MIN_VALUE
        for (e1 in elements)
            for (e2 in elements)
                max = max(magn(add(e1, e2)), max)
        println(max)
    }

    private fun partOne(elements: List<List<Element>>) {
        println(magn(elements.reduce(this::add)))
    }

    private fun magn(sum: List<Element>) = ((repeatApply(sum) { magnitudify(it) })[0] as Number).nb

    private fun add(n1: List<Element>, n2: List<Element>): List<Element> = reduce(mutableListOf(BO) + n1 + CO + n2 + BC)

    private fun reduce(elms: List<Element>): List<Element> {
        return repeatApply(elms) { elms1: List<Element> ->
            val exploded = repeatApply(elms1) { tryExplode(it) }
            trySplit(exploded)
        }
    }

    private fun repeatApply(initial: List<Element>, fn: (List<Element>) -> List<Element>):  List<Element> {
        var previous: List<Element>
        var applied = initial
        do {
            previous = applied
            applied = fn(applied)
        } while (previous != applied)
        return applied
    }

    private fun magnitudify(elements: List<Element>): List<Element> {
        if (elements.size == 1) return elements
        var ci = 1
        while (ci < elements.size - 3) {
            val ml = elements[ci]
            val mc = elements[ci + 1]
            val mr = elements[ci + 2]
            if (ml is Number && mc is CO && mr is Number) {
                return elements.replaceSegment(ci - 1, ci + 4, listOf(Number(3 * ml.nb + 2 * mr.nb)))
            }
            ci ++
        }
        return elements
    }

    private fun trySplit(elements: List<Element>): List<Element> {
        for ((idx, elem) in elements.withIndex()) {
            if (elem is Number && elem.nb > 9) {
                return elements.replaceSegment(idx, idx + 1, listOf(
                    BO, Number(elem.nb / 2), CO, Number(elem.nb - (elem.nb / 2)), BC
                ))
            }
        }
        return elements
    }

    private fun tryExplode(elements: List<Element>): List<Element> {
        var explodePos = -1
        var depth = 0
        for ((idx, elem) in elements.withIndex()) {
            when (elem) {
                BO -> depth ++
                BC -> depth --
                else -> {}
            }
            if (depth == 5) {
                explodePos = idx
                break
            }
        }
        if (explodePos != -1) {
            val le = elements[explodePos + 1]
            val re = elements[explodePos + 3]
            if (le is Number && elements[explodePos+2] is CO && re is Number) {
                val (toLeft, toRight) = listOf(le.nb, re.nb)
                val spliced = elements.replaceSegment(explodePos, explodePos + 5, listOf((Number(0)))).toMutableList()
                var (sl, sr) = listOf(explodePos, explodePos)
                while (--sl > 0) {
                    if (replaceIfNumber(spliced, sl, toLeft)) break
                }
                while (++sr < spliced.size) {
                    if (replaceIfNumber(spliced, sr, toRight)) break
                }
                return spliced.toList()
            } else {
                println("whoops: " + elements.subList(explodePos+1, explodePos+4))
            }
            return elements
        } else {
            return elements
        }
    }

    private fun <T> List<T>.replaceSegment(si: Int, ei: Int, replace: List<T>): List<T> {
        val spliced = this.subList(0, si).toMutableList()
        spliced.addAll(replace)
        spliced.addAll(this.subList(ei, this.size))
        return spliced.toList()
    }

    private fun replaceIfNumber(spliced: MutableList<Element>, idx: Int, replace: Long): Boolean {
        val element = spliced[idx]
        return if (element is Number) {
            spliced[idx] = Number(element.nb + replace)
            true
        } else false
    }

    private fun lexer(s: String): List<Element> {
        var idx = 0
        val elements: MutableList<Element> = mutableListOf()
        while (idx < s.length) {
            when (s[idx]) {
                '[' -> { elements.add(BO); idx++ }
                ']' -> { elements.add(BC); idx++ }
                ',' -> { elements.add(CO); idx++ }
                else -> {
                    val nbs = Regex("[\\d+]").find(s, idx)!!.value
                    elements.add(Number(nbs.toLong()))
                    idx += nbs.length
                }
            }
        }

        return elements.toList()
    }

    sealed interface Element
    object BO: Element
    object BC: Element
    object CO: Element
    data class Number(var nb: Long): Element
}
