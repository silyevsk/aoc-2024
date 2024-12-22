package day22

class Part2 {
    fun run() {
        val input = INPUT
        val nums = input.split("\n").map {it.toLong()}.map { initial ->
            var num = initial
            val arr = IntArray(2001)
            arr[0] = (num % 10).toInt()
            for (i in 1..2000) {
                num = ((num * 64) xor num) % 16777216
                num = ((num / 32) xor num) % 16777216
                num = ((num * 2048) xor num) % 16777216
                arr[i] = (num % 10).toInt()
            }
            arr
        }
        val sequences = mutableMapOf<List<Int>, Int>()
        for (arr in nums) {
            val used = mutableSetOf<List<Int>>()
            for (i in 0 ..< arr.size - 4) {
                val sequence = (0..3).map { arr[i + it + 1] - arr[i + it] }
                if (sequence in used) continue
                used.add(sequence)
                sequences[sequence] = (sequences[sequence] ?: 0) + arr[i + 4]
            }
        }
        println(sequences.values.max())
    }
}