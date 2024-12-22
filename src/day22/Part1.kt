package day22

class Part1 {
    fun run() {
        println(INPUT.split("\n").map {it.toLong()}.map { initial ->
            var num = initial
            for (i in 1..2000) {
                num = ((num * 64) xor num) % 16777216
                num = ((num / 32) xor num) % 16777216
                num = ((num * 2048) xor num) % 16777216
            }
            num
        }.sum())
    }
}