package day18

class Part1 {
    fun run() {
        val input = INPUT
        val w = 71
        val h = 71
        val grid = Array(h) {BooleanArray(w)}
        val lines = INPUT.split("\n")
        for (i in 0..<1024) {
            val (x,y) = lines[i].split(",").map { it.toInt() }
            grid[y][x] = true
        }

        val dist = Array(h) {IntArray(w) {Int.MAX_VALUE} }
        dist[0][0] = 0
        val q = mutableListOf(Point(0, 0))
        while (q.isNotEmpty()) {
            val u = q.removeFirst()
            val d = dist[u.y][u.x]
            if (u.x == w - 1 && u.y == h - 1) {
                println(d)
                break
            }
            fun visit(x: Int, y: Int) {
                if (grid[y][x] || dist[y][x] < Int.MAX_VALUE) return
                dist[y][x] = d + 1
                q.add(Point(x, y))
            }
            if (u.x > 0) visit(u.x - 1, u.y)
            if (u.x < w - 1) visit(u.x + 1, u.y)
            if (u.y > 0) visit(u.x, u.y - 1)
            if (u.y < h - 1) visit(u.x, u.y + 1)
        }
    }

    private data class Point(val x: Int, val y: Int)
}