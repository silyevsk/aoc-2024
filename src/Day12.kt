import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val input = Path("inputs/12.txt").readText().trim()
    fun part1() {
        data class Point(val x: Int, val y: Int)

        val grid = input.split("\n")
        var ans = 0L
        val counted = mutableSetOf<Point>()
        for (y0 in grid.indices) {
            for (x0 in grid[0].indices) {
                if (Point(x0, y0) in counted) continue

                var s = 0L
                var p = 0L
                val visited = mutableSetOf<Point>()
                val c0 = grid[y0][x0]
                fun dfs(x: Int, y: Int): Boolean {
                    if (x !in grid[0].indices || y !in grid.indices || grid[y][x] != c0) return true
                    val pt = Point(x, y)
                    if (pt in visited) return false
                    visited.add(pt)
                    s++
                    if (dfs(x - 1, y)) p++
                    if (dfs(x + 1, y)) p++
                    if (dfs(x, y - 1)) p++
                    if (dfs(x, y + 1)) p++
                    return false
                }
                dfs(x0, y0)
                ans += s * p
                counted += visited
            }
        }
        println(ans)
    }

    fun part2() {
        data class Point(val x: Int, val y: Int)

        val grid = input.split("\n")
        var ans = 0L
        val counted = mutableSetOf<Point>()
        for (y0 in grid.indices) {
            for (x0 in grid[0].indices) {
                if (Point(x0, y0) in counted) continue

                var s = 0L
                val visited = mutableSetOf<Point>()
                val c0 = grid[y0][x0]
                val upRows = Array(grid.size + 1) { mutableListOf<Int>() }
                val downRows = Array(grid.size + 1) { mutableListOf<Int>() }
                val upCols = Array(grid[0].length + 1) { mutableListOf<Int>() }
                val downCols = Array(grid[0].length + 1) { mutableListOf<Int>() }
                fun dfs(x: Int, y: Int): Boolean {
                    if (x !in grid[0].indices || y !in grid.indices || grid[y][x] != c0) return true
                    val pt = Point(x, y)
                    if (pt in visited) return false
                    visited.add(pt)
                    s++
                    if (dfs(x - 1, y)) upCols[x].add(y)
                    if (dfs(x + 1, y)) downCols[x + 1].add(y)
                    if (dfs(x, y - 1)) upRows[y].add(x)
                    if (dfs(x, y + 1)) downRows[y + 1].add(x)
                    return false
                }
                dfs(x0, y0)
                var p = 0L
                for (list in upCols + downCols + upRows + downRows) {
                    if (list.isEmpty()) continue
                    list.sort()
                    p++
                    for (i in 1..<list.size) {
                        if (list[i - 1] != list[i] - 1) p++
                    }
                }
                ans += s * p
                counted += visited
            }
        }
        println(ans)
    }

    part1()
    part2()
}