package day25

import kotlin.io.path.Path
import kotlin.io.path.readText

val INPUT = Path("inputs/25.txt").readText().trim()

fun main(args: Array<String>) {
    println("--- PART 1 ---")
    Part1().run()
}
