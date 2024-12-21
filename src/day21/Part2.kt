package day21

class Part2 {
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
            val shortest = dp(1 + 25, line, numPad)
            ans += num * shortest
        }
        println(ans)
    }

    private var mem = Array(27) { mutableMapOf<String, Long>()}
    private fun dp(level: Int, seq: String, pad: Map<Char, Point>): Long {
        if (level == 0) return seq.length.toLong()
        var r = mem[level][seq]
        if (r == null) {
            r = Long.MAX_VALUE
            val variants = calc(seq, pad)
            for (variant in variants) {
                val sv = variant.split("A").map { "${it}A" }.toMutableList()
                sv.removeLast()
                var size = 0L
                for (part in sv) {
                    size += dp(level - 1, part, dirPad)
                }
                r = minOf(r!!, size)
            }
            mem[level][seq] = r
        }
        return r
    }

    private fun calc(sequence: String, map: Map<Char, Point>): List<String> {
        val allowedPositions = map.values.toSet()
        var ans = mutableListOf<String>()
        var shortestSize = Int.MAX_VALUE
        val parents = mutableMapOf<String, String>()
        val sb = mutableListOf<Char>()
        fun next(x: Int, y: Int, i: Int) {
            if (i == sequence.length) {
                if (sb.size < shortestSize) {
                    ans = mutableListOf()
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
        return ans
    }

    private data class Point(val x: Int, val y: Int)
}