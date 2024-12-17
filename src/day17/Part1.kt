package day17

import java.util.PriorityQueue

class Part1 {
    fun run() {
        val input = INPUT
        val (regsInput, progInput) = input.split("\n\n")
        val regs = regsInput.split("\n").map {it.split(": ")[1].toLong()}.toLongArray()
        val prog = progInput.split(": ")[1].split(",").map { it.toInt() }

        var ip = 0
        fun literal(): Long {
            return prog[ip++].toLong()
        }
        fun combo(): Long {
            return when (val a = prog[ip++]) {
                0,1,2,3 -> a.toLong()
                4,5,6 -> regs[a - 4]
                7 -> throw Exception("Can't get combo 7")
                else -> throw Exception("Got combo $a")
            }
        }
        val output = mutableListOf<Long>()
        while (ip in prog.indices) {
            when (prog[ip++]) {
                0 -> { // adv
                    regs[0] = regs[0] / (1L shl combo().toInt())
                }
                1 -> { // bxl
                    regs[1] = regs[1] xor literal()
                }
                2 -> { // bst
                    regs[1] = combo() % 8
                }
                3 -> { // jnz
                    val arg = literal()
                    if (regs[0] != 0L) ip = arg.toInt()
                }
                4 -> { // bxc
                    literal()
                    regs[1] = regs[1] xor regs[2]
                }
                5 -> { // out
                    output.add(combo() % 8)
                }
                6 -> { // bdv
                    regs[1] = regs[0] / (1L shl combo().toInt())
                }
                7 -> { // cdv
                    regs[2] = regs[0] / (1L shl combo().toInt())
                }
            }
        }
        println(output.joinToString(","))
    }
}