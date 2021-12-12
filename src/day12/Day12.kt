package day12

import java.io.File

object Day12 {

    @OptIn(ExperimentalStdlibApi::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val isOnceVisitNode = { n: String ->  n == n.toLowerCase() }
        val edgeMap: MutableMap<String, Set<String>> = mutableMapOf()
        val addPair = { n1: String, n2: String ->
            edgeMap.merge(n1, mutableSetOf(n2)) { os, es -> os.union(es) }
        }
        val onceVisitNodes: MutableSet<String> = mutableSetOf()

        val addEdge: (String) -> Unit = { line ->
            val (n1, n2) = line.split("-")
            addPair(n1, n2)
            addPair(n2, n1)
            for (n in listOf(n1, n2))
                if (isOnceVisitNode(n) && n != "start" && n != "end") onceVisitNodes.add(n)
        }

        File("inputs/input12t1.text").readLines().forEach(addEdge)

        val paths: MutableSet<String> = mutableSetOf()
        val visited: MutableMap<String, Int> = mutableMapOf("start" to 1)
        val currentPath: MutableList<String> = mutableListOf()

        fun visit(n:String, onceVisitNode: String) {
            currentPath.add(n)
            if ("end" == n) {
                paths.add(currentPath.joinToString("-"))
            } else {
                val nextEdges = edgeMap[n]!!
                for (nextNode in nextEdges) {
                    if (isOnceVisitNode(nextNode)) {
                        if (!visited.contains(nextNode) || visited[nextNode] == 0
                            || (nextNode == onceVisitNode && visited[nextNode] == 1)) {
                            visited.merge(nextNode, 1, Int::plus)
                            visit(nextNode, onceVisitNode)
                            visited[nextNode] = visited[nextNode]!! - 1
                        }
                    } else {
                        visit(nextNode, onceVisitNode)
                    }
                }
            }
            currentPath.removeLast()
        }

        if (args.elementAtOrNull(0) == "part2") {
            for (onceVisitNode in onceVisitNodes) {
                visit("start", onceVisitNode)
            }
        } else {
            visit("start", "**")
        }

        println(paths.size)
    }
}
