import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val input = Path("inputs/10.txt").readText().trim()

    fun solve(countDistinct: Boolean) {
        val grid = input.split("\n").map { line ->
            line.map {it - '0'}
        }
        var ans = 0
        for (y0 in grid.indices) {
            for (x0 in grid[0].indices) {
                if (grid[y0][x0] != 0) continue

                val visited = mutableSetOf<Point>()
                fun dfs(p: Point) {
                    if (!countDistinct) {
                        if (p in visited) return
                        visited.add(p)
                    }
                    val v = grid[p.y][p.x] + 1
                    if (v == 10) {
                        ans++
                        return
                    }
                    fun visit(x: Int, y: Int) {
                        if (grid[y][x] == v) dfs(Point(x, y))
                    }
                    if (p.x > 0) visit(p.x - 1, p.y)
                    if (p.x < grid[0].lastIndex) visit(p.x + 1, p.y)
                    if (p.y > 0) visit(p.x, p.y - 1)
                    if (p.y < grid.lastIndex) visit(p.x, p.y + 1)
                }
                dfs(Point(x0, y0))
            }
        }
        println(ans)
    }

    solve(false)
    solve(true)
}

private data class Point(val x: Int, val y: Int)
