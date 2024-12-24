package day24

class Part2 {
    fun run() {
        val input = INPUT
        val (inputsInput, gatesInput) = input.split("\n\n")
        val bitInputs = mutableMapOf<String, Int>()
        for (line in inputsInput.split("\n")) {
            val (wire, str) = line.split(": ")
            bitInputs[wire] = str.toInt()
        }
        val gates = mutableMapOf<String, Gate>()
        for (line in gatesInput.split("\n")) {
            val (from, to) = line.split(" -> ")
            val (a, op, b) = from.split(" ")
            gates[to] = Gate(a, b, op)
        }

        var size = 0
        val gatesKeys = gates.keys.toList()
        while (bitInputs.containsKey("x${size.toString().padStart(2, '0')}")) size++

        fun bitGates(bit: Int): List<String> {
            val r = mutableListOf<String>()
            val wires = mutableMapOf<String, Int>()
            fun dfs(wire: String): Int {
                if (wire in bitInputs) {
                    r.add(wire)
                    return bitInputs[wire]!!
                }
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
                    if (!wire.startsWith("z")) r.add(wire)
                    wires[wire] = value
                }
                return value
            }
            dfs("z${bit.toString().padStart(2, '0')}")
            return r.sorted()
        }

        fun checkSum(x: String, y: String): Boolean {
            val wires = mutableMapOf<String, Int>()
            val visited = mutableSetOf<String>()
            fun dfs(wire: String): Int? {
                var value = wires[wire]
                if (value == null) {
                    val gate = gates[wire]
                    if (gate == null) {
                        val i = wire.substring(1).toInt()
                        if (i == x.length) return 0
                        if (i > x.length) return null
                        value = when (wire[0]) {
                            'x' -> x[i].digitToInt()
                            'y' -> y[i].digitToInt()
                            else -> throw Exception("B")
                        }
                    } else {
                        if (wire in visited) return null
                        visited.add(wire)
                        val a = dfs(gate.a) ?: return null
                        val b = dfs(gate.b) ?: return null
                        value = when (gate.op) {
                            "AND" -> a and b
                            "OR" -> a or b
                            "XOR" -> a xor b
                            else -> throw Exception("A")
                        }
                    }
                    wires[wire] = value
                }
                return value
            }
            val expected = (x.toLong(2) + y.toLong(2)).toString(2).padStart(x.length, '0')
            val actual = mutableListOf<Int>()
            for (i in 0..<x.length) {
                val r = dfs("z${i.toString().padStart(2, '0')}") ?: return false
                actual.add(r)
            }

            return expected.substring(expected.length - x.length, expected.length) == actual.reversed().joinToString("")
        }

        fun checkBit(bit: Int, requiredGates: List<String>): Pair<Boolean, List<String>> {
            val usedInputBits = mutableSetOf<Int>()
            val usedWires = mutableSetOf<String>()
            val wires = mutableMapOf<String, String>()
            val visited = mutableSetOf<String>()
            fun dfs(wire: String): String? {
                if (wire in bitInputs) {
                    usedInputBits.add(wire.substring(1).toInt())
                    usedWires.add(wire)
                    return wire
                }
                var value = wires[wire]
                if (value == null) {
                    if (wire in visited) return null
                    visited.add(wire)
                    val gate = gates[wire]!!
                    val a = dfs(gate.a) ?: return null
                    val b = dfs(gate.b) ?: return null
                    value = when (gate.op) {
                        "AND" -> "($a & $b)"
                        "OR" -> "($a | $b)"
                        "XOR" -> "($a ^ $b)"
                        else -> throw Exception("A")
                    }
                    wires[wire] = value
                    usedWires.add(wire)
                }
                return value
            }
            dfs("z${bit.toString().padStart(2, '0')}") ?: return Pair(false, emptyList())
            val list = usedInputBits.toList().sorted()
            val nextRequiredGates = bitGates(bit)
            if (list.size != bit + 1 || list[0] != 0 || list.last() != bit) {
                return Pair(false, nextRequiredGates)
            }
            if (requiredGates.any { it !in usedWires }) return Pair(false, nextRequiredGates)
            if (bit > 2 && nextRequiredGates.size - requiredGates.size != 8) return Pair(false, nextRequiredGates)
            val zeros = (0..bit).map { '0' }.joinToString("")
            val ones = (0..bit).map { '1' }.joinToString("")
            if (
                !checkSum(zeros, zeros) ||
                !checkSum(zeros, ones) ||
                !checkSum(ones, ones)
            ) {
                return Pair(false, nextRequiredGates)
            }
            return Pair(true, nextRequiredGates)
        }

        val x = bitInputs.keys.filter { it.startsWith("x") }.sortedDescending().map { wire ->
            bitInputs[wire].toString()
        }.joinToString("")
        val y = bitInputs.keys.filter { it.startsWith("y") }.sortedDescending().map { wire ->
            bitInputs[wire].toString()
        }.joinToString("")
        val expectedSum = (x.toLong(2) + y.toLong(2)).toString(2)

        val swapped = mutableSetOf<String>()
        fun next(bit: Int, requiredGates: List<String>): Boolean {
            if (bit == size) {
                println(swapped.sorted().joinToString(","))
                return true
            }

            val (res, nextRequiredGates) = checkBit(bit, requiredGates)
            if (res) return next(bit + 1, nextRequiredGates)

            fun swapGates(a: String, b: String) {
                val t = gates[a]!!
                gates[a] = gates[b]!!
                gates[b] = t
            }
            val all = gatesKeys.filter { it !in requiredGates }
            for (i in all.indices) {
                if (all[i] in swapped) continue
                for (j in i + 1 ..< all.size) {
                    if (all[j] in swapped) continue
                    swapGates(all[i], all[j])
                    val res1 = checkBit(bit, requiredGates)
                    if (res1.first) {
                        swapped.add(all[i])
                        swapped.add(all[j])
                        if (next(bit + 1, res1.second)) return true
                        swapped.remove(all[i])
                        swapped.remove(all[j])
                    }
                    swapGates(all[i], all[j])
                }
            }
            return false
        }
        next(0, emptyList())
    }

    private class Gate(val a: String, val b: String, val op: String)
}