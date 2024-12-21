package day21

class Part1 {
    private val numPad = mapOf(
        '7' to Point(0, 0),
        '8' to Point(1, 0),
        '9' to Point(2, 0),
        '4' to Point(0, 1),
        '5' to Point(1, 1),
        '6' to Point(2, 1),
        '1' to Point(0, 2),
        '2' to Point(1, 2),
        '3' to Point(2, 2),

        '0' to Point(1, 3),
        'A' to Point(2, 3),
    )
    private val dirPad = mapOf(
        '^' to Point(1, 0),
        'A' to Point(2, 0),
        '<' to Point(0, 1),
        'v' to Point(1, 1),
        '>' to Point(2, 1),
    )

    fun run() {
        val input = INPUT
        var ans = 0L
        for (line in input.split("\n")) {
            val num = line.substring(0, line.lastIndex).toLong()
            val r1 = calc(setOf(line), numPad)
            val r2 = calc(r1, dirPad)
            val r3 = calc(r2, dirPad)
            ans += num * r3.first().length
        }
        println(ans)
    }

    private fun calc(sequences: Set<String>, map: Map<Char, Point>): Set<String> {
        val allowedPositions = map.values.toSet()
        var ans = mutableSetOf<String>()
        var shortestSize = Int.MAX_VALUE
        val parents = mutableMapOf<String, String>()
        for (sequence in sequences) {
            val sb = mutableListOf<Char>()
            fun next(x: Int, y: Int, i: Int) {
                if (i == sequence.length) {
                    if (sb.size < shortestSize) {
                        ans = mutableSetOf()
                        shortestSize = sb.size
                    }
                    ans.add(sb.joinToString(""))
                    parents[sb.joinToString("")] = sequence
                    return
                }

                if (sb.size >= shortestSize) return

                val target = map[sequence[i]]!!
                val dx = target.x - x
                val dy = target.y - y
                if (dx == 0 && dy == 0) {
                    sb.add('A')
                    next(x, y, i + 1)
                    sb.removeLast()
                }

                fun visit(p: Point, c: Char) {
                    if (p !in allowedPositions) {
                        return
                    }
                    sb.add(c)
                    next(p.x, p.y, i)
                    sb.removeLast()
                }
                if (dx < 0) visit(Point(x - 1, y), '<')
                if (dx > 0) visit(Point(x + 1, y), '>')
                if (dy < 0) visit(Point(x, y - 1), '^')
                if (dy > 0) visit(Point(x, y + 1), 'v')
            }

            val a = map['A']!!
            next(a.x, a.y, 0)
        }
        return ans
    }

    private data class Point(val x: Int, val y: Int)
}