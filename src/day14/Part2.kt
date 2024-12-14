import kotlin.io.path.Path
import kotlin.io.path.readText

class Part2 {
    fun run() {
        val input = Path("inputs/14.txt").readText().trim()
        val w = 101
        val h = 103
        val bots = input.split("\n").map { line ->
            val (x, y, dx, dy) = line.split(" v=", "p=", ",").mapNotNull { it.toIntOrNull() }
            Bot(x, y, dx, dy)
        }
        val states = mutableSetOf<String>()
        fun state(): String {
            return bots.map { "${it.x},${it.y}" }.joinToString(" ")
        }
        for (i in 1..100000) {
            val s = state()
            if (s in states) {
                println(i)
                break
            }
            states.add(s)
            for (bot in bots) {
                bot.x = (bot.x + w + bot.dx) % w
                bot.y = (bot.y + h + bot.dy) % h
            }
            var a = 0
            var b = 0
            for (bot in bots) {
                if (bot.x + bot.y < (w + h) / 4) a++
                if ((w - bot.x) + bot.y < (w + h) / 4) b++
            }
            if (a + b < 50) {
                println("--- $i ---")
                val points = bots.map { Point(it.x, it.y) }.toSet()
                for (y in 0..<h) {
                    for (x in 0..<w) {
                        print(if (points.contains(Point(x, y))) '#' else '.')
                    }
                    println("")
                }
            }
        }
    }

    private class Bot(var x: Int, var y: Int, val dx: Int, val dy: Int)
    private data class Point(val x: Int, val y: Int)
}