package day23

class Part1 {
    fun run() {
        val input = INPUT
        val nodes = mutableMapOf<String, Node>()
        for (line in input.split("\n")) {
            val (a, b) = line.split("-")
            val u = nodes.getOrPut(a) {Node(a)}
            val v = nodes.getOrPut(b) {Node(b)}
            u.neighbors.add(v)
            v.neighbors.add(u)
        }
        val list = nodes.values.toList()
        var ans = 0
        for (i in list.indices) {
            val u = list[i]
            for (j in (i+1)..<list.size) {
                val v = list[j]
                if (v !in u.neighbors) continue
                for (k in (j+1)..<list.size) {
                    val w = list[k]
                    if (w !in u.neighbors || w !in v.neighbors) continue
                    if (u.id.startsWith("t") || v.id.startsWith("t") || w.id.startsWith("t")) ans++
                }
            }
        }
        println(ans)
    }

    private class Node(val id: String) {
        val neighbors = mutableSetOf<Node>()
    }
}