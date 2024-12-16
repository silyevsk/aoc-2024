package day16

import java.util.PriorityQueue

class Part2 {
    fun run() {

        val input = INPUT
        val grid = input.split("\n")
        val start = Position(1, grid.lastIndex - 1, 1, 0)
        val ends = setOf(Position(grid[0].lastIndex - 1, 1, 1, 0), Position(grid[0].lastIndex - 1, 1, 0, -1))
        val dist = mutableMapOf<Position, Long>()
        val prev = mutableMapOf<Position, MutableList<Position>>()
        val q = PriorityQueue<Position>(compareBy { dist[it]!! })
        dist[start] = 0
        q.add(start)
        var bestScore: Long? = null
        while (q.isNotEmpty()) {
            val u = q.poll()
            val d0 = dist[u]!!
            if (u in ends && bestScore == null) {
                bestScore = d0
                continue
            }
            if (bestScore != null &&  d0 >= bestScore) continue
            fun visit(v: Position, d: Long) {
                if (grid[v.y][v.x] == '#') return
                val d1 = dist[v]
                if (d1 != null && d1 < d) return

                dist[v] = d
                prev.getOrPut(v) { mutableListOf()}.add(u)
                q.add(v)
            }
            visit(Position(u.x, u.y, u.dy, u.dx), d0 + 1000)
            visit(Position(u.x, u.y, -u.dy, -u.dx), d0 + 1000)
            visit(Position(u.x + u.dx, u.y + u.dy, u.dx, u.dy), d0 + 1)
        }

        val ans = mutableSetOf(Point(start.x, start.y))
        val visited = mutableSetOf<Position>()
        fun dfs(u: Position) {
            if (u in visited) return
            visited.add(u)
            val children = prev[u] ?: return
            ans.add(Point(u.x, u.y))
            for (v in children) dfs(v)
        }
        for (end in ends) dfs(end)
        println(ans.size)
    }

    private data class Position(val x: Int, val y: Int, val dx: Int, val dy: Int)
    private data class Point(val x: Int, val y: Int)
}