package day24

class Part1 {
    fun run() {
        val input = INPUT
        val (inputsInput, gatesInput) = input.split("\n\n")
        val wires = mutableMapOf<String, Int>()
        for (line in inputsInput.split("\n")) {
            val (wire, str) = line.split(": ")
            wires[wire] = str.toInt()
        }
        val gates = mutableMapOf<String, Gate>()
        for (line in gatesInput.split("\n")) {
            val (from, to) = line.split(" -> ")
            val (a, op, b) = from.split(" ")
            gates[to] = Gate(a, b, op)
        }

        fun dfs(wire: String): Int {
            var value = wires[wire]
            if (value == null) {
                val gate = gates[wire]!!
                val a = dfs(gate.a)
                val b = dfs(gate.b)
                value = when (gate.op) {
                    "AND" -> a and b
                    "OR" -> a or b
                    "XOR" -> a xor b
                    else -> throw Exception("A")
                }
                wires[wire] = value
            }
            return value
        }

        val ans = gates.keys.filter { it.startsWith("z") }.sortedDescending().map { wire ->
            dfs(wire).toString()
        }.joinToString("").toLong(2)
        println(ans)
    }

    private class Gate(val a: String, val b: String, val op: String)
}