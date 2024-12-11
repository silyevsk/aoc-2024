import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val input = Path("inputs/11.txt").readText().trim()
    fun part1() {
        var a = input.split(" ").map {it.toLong()}
        for (i in 1..25) {
            val b = mutableListOf<Long>()
            for (x in a) {
                if (x == 0L) {
                    b.add(1L)
                    continue
                }
                val s = x.toString()
                if (s.length % 2 == 0) {
                    b.add(s.substring(0, s.length / 2).toLong())
                    b.add(s.substring(s.length / 2, s.length).toLong())
                    continue
                }
                b.add(x * 2024)
            }
            a = b
        }
        println(a.size)
    }

    fun part2() {
        var a = input.split(" ").associate { it.toLong() to 1L }
        for (i in 1..75) {
            val b = mutableMapOf<Long, Long>()
            for ((x, n) in a) {
                if (x == 0L) {
                    b[1] = (b[1] ?: 0) + n
                    continue
                }
                val s = x.toString()
                if (s.length % 2 == 0) {
                    val y = s.substring(0, s.length / 2).toLong()
                    val z = s.substring(s.length / 2, s.length).toLong()
                    b[y] = (b[y] ?: 0) + n
                    b[z] = (b[z] ?: 0) + n
                    continue
                }
                val y = x * 2024
                b[y] = (b[y] ?: 0) + n
            }
            a = b
        }
        println(a.values.sum())
    }

    part1()
    part2()
}
