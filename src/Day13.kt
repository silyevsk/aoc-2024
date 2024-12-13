import kotlin.io.path.Path
import kotlin.io.path.readText

val input = Path("inputs/13.txt").readText().trim()

fun main() {
    fun part1() {
        var ans = 0
        for (block in input.split("\n\n")) {
            val lines = block.split("\n")
            val (ax, ay) = lines[0].split("+", ", ").map { it.toIntOrNull() }.filterNotNull()
            val (bx, by) = lines[1].split("+", ", ").map { it.toIntOrNull() }.filterNotNull()
            val (px, py) = lines[2].split("=", ", ").map { it.toIntOrNull() }.filterNotNull()

            var best = Int.MAX_VALUE
            for (ca in 0..100) {
                for (cb in 0..100) {
                    if (ca * ax + cb * bx == px && ca * ay + cb * by == py) {
                        best = minOf(best, ca * 3 + cb * 1)
                    }
                }
            }
            if (best != Int.MAX_VALUE) ans += best
        }
        println(ans)
    }

    fun part2() {
        var ans = 0L
        for (block in input.split("\n\n")) {
            val lines = block.split("\n")
            val (ax, ay) = lines[0].split("+", ", ").mapNotNull { it.toLongOrNull() }
            val (bx, by) = lines[1].split("+", ", ").mapNotNull { it.toLongOrNull() }
            val (s, t) = lines[2].split("=", ", ").mapNotNull { it.toIntOrNull() }.map { 10000000000000L + it }

            val b = (t * ax - s * ay) / (ax * by - bx * ay)
            if ((ax * by - bx * ay) * b != (t * ax - s * ay)) continue
            val a = (s - b * bx) / ax
            if (ax * a != (s - b * bx)) continue
            ans += a * 3 + b
        }
        println(ans)
    }

    part1()
    part2()
}

