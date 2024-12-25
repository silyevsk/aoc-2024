package day25

class Part1 {
    fun run() {
        val input = INPUT
        val blocks = input.split("\n\n")
        val locks = mutableListOf<IntArray>()
        val keys = mutableListOf<IntArray>()
        for (block in blocks) {
            val rows = block.split("\n")
            val heights = IntArray(5) { j ->
                (1..5).count { i -> rows[i][j] == '#' }
            }
            if (rows[0][0] == '#') locks.add(heights) else keys.add(heights)
        }

        var ans = 0
        for (key in keys) {
            lock@for (lock in locks) {
                for (i in 0..<5) {
                    if (key[i] + lock[i] > 5) {
                        continue@lock
                    }
                }
                ans++
            }
        }
        println(ans)
    }
}