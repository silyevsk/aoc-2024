package day15

class Part1 {
    fun run() {
        val input = INPUT
        val (layout, commands) = input.split("\n\n")
        val grid = layout.split("\n")
        val walls = mutableSetOf<Point>()
        val boxes = mutableSetOf<Point>()
        var x = -1
        var y = -1
        for (i in grid.indices) {
            for (j in grid[0].indices) {
                when (grid[i][j]) {
                    '#' -> walls.add(Point(j, i))
                    'O' -> boxes.add(Point(j, i))
                    '@' -> {x = j; y = i}
                }
            }
        }
        for (cmd in commands) {
            val d = when (cmd) {
                '<' -> Point(-1, 0)
                '>' -> Point(1, 0)
                '^' -> Point(0, -1)
                'v' -> Point(0, 1)
                '\n' -> continue
                else -> throw Exception("A")
            }
            var i = 1
            var p = Point(x + i * d.x, y + i * d.y)
            var firstBox: Point? = null
            while (p in boxes) {
                if (firstBox == null) firstBox = p
                i++
                p = Point(x + i * d.x, y + i * d.y)
            }
            if (p !in walls) {
                if (firstBox != null) {
                    boxes.remove(firstBox)
                    boxes.add(p)
                }
                x += d.x
                y += d.y
            }
        }

        var ans = 0
        for (box in boxes) {
            ans += box.y * 100 + box.x
        }
        println(ans)
    }

    private data class Point(val x: Int, val y: Int) {
        override fun toString(): String {
            return "$x,$y"
        }
    }
}