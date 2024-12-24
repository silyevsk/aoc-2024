package day23

import kotlin.io.path.Path
import kotlin.io.path.readText

val INPUT = Path("inputs/23.txt").readText().trim()

fun main(args: Array<String>) {
    println("--- PART 1 ---")
    Part1().run()
    println("\n--- PART 2 ---")
    Part2().run()
}