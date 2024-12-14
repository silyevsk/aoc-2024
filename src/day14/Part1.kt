import kotlin.io.path.Path
import kotlin.io.path.readText

class Part1 {
    fun run() {
        val input = Path("inputs/14.txt").readText().trim()
        val w = 101
        val h = 103
        val bots = input.split("\n").map { line ->
            val (x, y, dx, dy) = line.split(" v=", "p=", ",").mapNotNull { it.toIntOrNull() }
            Bot(x, y, dx, dy)
        }
        for (i in 1..100) {
            for (bot in bots) {
                bot.x = (bot.x + w + bot.dx) % w
                bot.y = (bot.y + h + bot.dy) % h
            }
        }
        var a = 0
        var b = 0
        var c = 0
        var d = 0
        for (bot in bots) {
            if (bot.x < w / 2) {
                if (bot.y < h / 2) a++ else if (bot.y > h / 2) b++
            } else if (bot.x > w / 2) {
                if (bot.y < h / 2) c++ else if (bot.y > h / 2) d++
            }
        }
        println(a.toLong() * b * c * d)
    }

    private class Bot(var x: Int, var y: Int, val dx: Int, val dy: Int)
}