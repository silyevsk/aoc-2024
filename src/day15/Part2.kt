package day15

class Part2 {
    fun run() {
        val input0 = """#######
#...#.#
#.....#
#..OO@#
#..O..#
#.....#
#######

<vv<<^^<<^^"""
        val input1 = """##########
#..O..O.O#
#......O.#
#.OO..O.O#
#..O@..O.#
#O#..O...#
#O..O..O.#
#.OO.O.OO#
#....O...#
##########

<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
>^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^"""

        val input = INPUT
        val (layout, commands) = input.split("\n\n")
        val grid = layout.split("\n")
        val walls = mutableSetOf<Point>()
        val boxesL = mutableSetOf<Point>()
        val boxesR = mutableSetOf<Point>()
        var x = -1
        var y = -1

        fun draw() {
            return
            for (i in grid.indices) {
                for (j in 0..<grid[0].length * 2) {
                    val p = Point(j, i)
                    print(if (p in walls) '#' else if (p in boxesL) '[' else if (p in boxesR) ']' else if (y == i && x == j) '@' else '.')
                }
                println()
            }
            println()
        }
        for (i in grid.indices) {
            for (j in grid[0].indices) {
                when (grid[i][j]) {
                    '#' -> {
                        walls.add(Point(j * 2, i))
                        walls.add(Point(j * 2 + 1, i))
                    }
                    'O' -> {
                        boxesL.add(Point(j * 2, i))
                        boxesR.add(Point(j * 2 + 1, i))
                    }
                    '@' -> {x = j * 2; y = i}
                }
            }
        }
//        println("  $boxesL")
        for (cmd in commands) {
            val d = when (cmd) {
                '<' -> Point(-1, 0)
                '>' -> Point(1, 0)
                '^' -> Point(0, -1)
                'v' -> Point(0, 1)
                '\n' -> continue
                else -> throw Exception("A")
            }
            val q = mutableListOf<Point>()
            if (d.x != 0) {
                var i = 1
                var p = Point(x + i * d.x, y)
                while (p in boxesL || p in boxesR) {
                    q.add(if (d.x == 1) p else Point(p.x - 1, p.y))
                    i += 2
                    p = Point(x + i * d.x, y)
                }
                if (p !in walls) {
                    for (box in q) {
                        boxesL.remove(box)
                        boxesR.remove(Point(box.x + 1, box.y))
                        boxesL.add(Point(box.x + d.x, box.y))
                        boxesR.add(Point(box.x + d.x + 1, p.y))
                    }
                    x += d.x
                    if (q.isNotEmpty()) {
//                        println("$cmd ${boxesL}")
                        draw()
                    }
                }
            } else {
                var a = mutableListOf(Point(x, y + d.y))
                while (a.any { it in boxesL || it in boxesR } && a.none {it in walls}) {
                    val b = mutableListOf<Point>()
                    for (u in a) {
                        if (u in boxesL) {
                            q.add(u)
                            b.add(Point(u.x, u.y + d.y))
                            b.add(Point(u.x + 1, u.y + d.y))
                        } else if (u in boxesR) {
                            q.add(Point(u.x - 1, u.y))
                            b.add(Point(u.x - 1, u.y + d.y))
                            b.add(Point(u.x, u.y + d.y))
                        }
                    }
                    a = b
                }
                if (a.none {it in walls}) {
                    for (box in q.reversed()) {
                        boxesL.remove(box)
                        boxesR.remove(Point(box.x + 1, box.y))
                        boxesL.add(Point(box.x, box.y + d.y))
                        boxesR.add(Point(box.x + 1, box.y + d.y))
                    }
                    y += d.y
                    if (q.isNotEmpty()) {
//                        println("$cmd $boxesL")
                        draw()
                    }
                }
            }
        }

        var ans = 0
        for (box in boxesL) {
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