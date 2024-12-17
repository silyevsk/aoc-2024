package day17

import java.util.PriorityQueue

class Part2 {
    fun run() {
        val input0 = """Register A: 2024
Register B: 0
Register C: 0

Program: 0,3,5,4,3,0"""
        val input = INPUT
        val (regsInput, progInput) = input.split("\n\n")
        val prog = progInput.split(": ")[1].split(",").map { it.toInt() }


        fun dfs(prefix: Long, length: Int): Boolean {
            if (length == prog.size) {
                println(prefix)
                return true
            }

            for (suffix in 0..7) {
                var ip = 0
                val regs = longArrayOf((prefix shl 3) + suffix, 0, 0)

                fun literal(): Int {
                    return prog[ip++]
                }
                fun combo(): Long {
                    return when (val a = prog[ip++]) {
                        0, 1, 2, 3 -> a.toLong()
                        4, 5, 6 -> regs[a - 4]
                        7 -> throw Exception("Can't get combo 7")
                        else -> throw Exception("Got combo $a")
                    }
                }

                while (ip in prog.indices) {
                    when (prog[ip++]) {
                        0 -> { // adv
                            regs[0] = regs[0] / (1L shl combo().toInt())
                        }

                        1 -> { // bxl
                            regs[1] = regs[1] xor literal().toLong()
                        }

                        2 -> { // bst
                            regs[1] = combo() % 8
                        }

                        3 -> { // jnz
                            val arg = literal()
                            if (regs[0] != 0L) ip = arg
                        }

                        4 -> { // bxc
                            literal()
                            regs[1] = regs[1] xor regs[2]
                        }

                        5 -> { // out
                            val byte = (combo() % 8).toInt()
                            if (prog[prog.lastIndex - length] == byte) {
                                if (dfs((prefix shl 3) + suffix, length + 1)) return true
                            }
                            break
                        }

                        6 -> { // bdv
                            regs[1] = regs[0] / (1L shl combo().toInt())
                        }

                        7 -> { // cdv
                            regs[2] = regs[0] / (1L shl combo().toInt())
                        }
                    }
                }
            }

            return false
        }
        dfs(0L, 0)
    }
}