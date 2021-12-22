package day22

import org.apache.commons.geometry.euclidean.threed.RegionBSPTree3D
import org.apache.commons.geometry.euclidean.threed.Vector3D
import org.apache.commons.geometry.euclidean.threed.shape.Parallelepiped
import org.apache.commons.numbers.core.Precision
import java.io.File


object Day22 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = File("inputs/input22t.text").useLines { it.toList() }
        val tree: RegionBSPTree3D = RegionBSPTree3D.empty()

        fun addObj(s: String) {
            val coords = Regex("[-\\d]+").findAll(s).map { it.value.toInt().toDouble() }.toList()
            println(coords)
            val cuboid = Parallelepiped.axisAligned(
                Vector3D.of(coords[0], coords[2], coords[4]),
                Vector3D.of(coords[1]+1, coords[3]+1, coords[5]+1), Precision.doubleEquivalenceOfEpsilon(1e-6))
            if (s.startsWith("on")) {
                tree.union(cuboid.toTree())
            } else {
                tree.difference(cuboid.toTree())
            }
        }

        lines.forEach(::addObj)
        println(tree.size)
    }
}
