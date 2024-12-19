package day19

class Part2 {
    fun run() {
        val input = INPUT
        val (inputA, inputB) = input.split("\n\n")
        val words = inputA.split(", ")
        var ans = 0L
        for (design in inputB.split("\n")) {
            val mem = LongArray(design.length) {-1L}
            fun dp(start: Int): Long {
                if (start == design.length) return 1
                if (start > design.length) return 0
                var r = mem[start]
                if (r == -1L) {
                    r = 0L
                    for (word in words) {
                        if (design.startsWith(word, start)) r += dp(start + word.length)
                    }
                }
                mem[start] = r
                return r
            }
            ans += dp(0)
        }
        println(ans)
    }
}