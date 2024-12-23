package day23

class Part2 {
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
        val all = nodes.values.toList().sortedBy { it.neighbors.size }
        var largestGroup: List<Node> = listOf()
        val list = mutableListOf<Node>()
        fun next(start: Int) {
            if (start == all.size) {
                if (list.size > largestGroup.size) largestGroup = list.toList()
                return
            }

            val u = all[start]
            val connected = !list.any { it !in u.neighbors }
            if (connected) {
                list.add(u)
                next(start + 1)
                list.removeLast()
            }
            next(start + 1)
        }
        next(0)
        println(largestGroup.map { it.id }.sorted().joinToString(","))
    }

    private class Node(val id: String) {
        val neighbors = mutableSetOf<Node>()
    }
}