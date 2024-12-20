package day20

import kotlin.math.abs

class Part2 {
    fun run() {
        val input0 = """###############
#...#...#.....#
#.#.#.#.#.###.#
#S#...#.#.#...#
#######.#.#.###
#######.#.#...#
#######.#.###.#
###..E#...#...#
###.#######.###
#...###...#...#
#.#####.#.###.#
#.#...#.#.#...#
#.#.#.#.#.#.###
#...#...#...###
###############"""
        val input = INPUT
        val grid = input.split("\n")
        var sx: Int = -1
        var sy: Int = -1
        var ex: Int = -1
        var ey: Int = -1
        for (y in grid.indices) {
            for (x in grid[0].indices) {
                when (grid[y][x]) {
                    'S' -> {sx = x; sy = y}
                    'E' -> {ex = x; ey = y}
                }
            }
        }
        val s = Point(sx, sy)
        val e = Point(ex, ey)

        val path = mutableListOf(Point(-1, -1), s)
        p@while (path.last() != e) {
            for (d in listOf(Point(1, 0), Point(0, 1), Point(-1, 0), Point(0, -1))) {
                val p = path.last() + d
                if (p.x in grid[0].indices && p.y in grid.indices && grid[p.y][p.x] != '#' && p != path[path.lastIndex - 1]) {
                    path.add(p)
                    continue@p
                }
            }
        }
        var ans = 0
        val bySeconds = mutableMapOf<Int, Int>()
        for (i in 1 ..< path.lastIndex - 1) {
            val a = path[i]
            for (j in i + 3 ..< path.size) {
                val b = path[j]
                val d = abs(a.x - b.x) + abs(a.y - b.y)
                if (d <= 20) {
                    val t = j - i - d
                    if (t >= 100) ans++
                    if (t >= 50) bySeconds[t] = (bySeconds[t] ?: 0) + 1
                }
            }
        }
//        println(bySeconds)
        println(ans)
    }
    private data class Point(val x: Int, val y: Int) {
        operator fun plus(p: Point): Point {
            return Point(p.x + x, p.y + y)
        }

        override fun toString(): String {
            return "$x,$y"
        }
    }
}