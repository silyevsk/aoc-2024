package day16

import java.util.PriorityQueue

class Part1 {
    fun run() {

        val input = INPUT
        val grid = input.split("\n")
        val start = Position(1, grid.lastIndex - 1, 1, 0)
        val ends = setOf(Position(grid[0].lastIndex - 1, 1, 1, 0), Position(grid[0].lastIndex - 1, 1, 0, -1))
        val dist = mutableMapOf<Position, Long>()
        val q = PriorityQueue<Position>(compareBy { dist[it]!! })
        dist[start] = 0
        q.add(start)
        while (q.isNotEmpty()) {
            val u = q.poll()
            val d0 = dist[u]!!
            if (u in ends) {
                println(d0)
                return
            }
            fun visit(v: Position, d: Long) {
                if (grid[v.y][v.x] == '#') return
                val d1 = dist[v]
                if (d1 != null && d1 <= d) return

                dist[v] = d
                q.add(v)
            }
            visit(Position(u.x, u.y, u.dy, u.dx), d0 + 1000)
            visit(Position(u.x, u.y, -u.dy, -u.dx), d0 + 1000)
            visit(Position(u.x + u.dx, u.y + u.dy, u.dx, u.dy), d0 + 1)
        }
    }

    private data class Position(val x: Int, val y: Int, val dx: Int, val dy: Int)
}