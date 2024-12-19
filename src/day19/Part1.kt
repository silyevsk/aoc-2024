package day19

class Part1 {
    fun run() {
        val input = INPUT
        val (inputA, inputB) = input.split("\n\n")
        val words = inputA.split(", ")
        var ans = 0
        for (design in inputB.split("\n")) {
            fun dp(start: Int): Boolean {
                if (start == design.length) return true
                if (start > design.length) return false
                for (word in words) {
                    if (design.startsWith(word, start) && dp(start + word.length)) return true
                }
                return false
            }
            if (dp(0)) ans++
        }
        println(ans)
    }
}