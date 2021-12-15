import java.util.*

fun main() {
    val input = readInput("15").map { s -> s.map { it.digitToInt() }.toIntArray() }.toTypedArray()
    print { part1(input) }
    print { part2(input) }
}

private fun part1(input: Array<IntArray>) = solve(input)

private fun part2(input: Array<IntArray>) = solve(input.expand())

private fun solve(map: Array<IntArray>): Int {
    val levels = Array(map.size) { Array(map[0].size) { Int.MAX_VALUE } }.apply { get(0)[0] = 0 }
    val visited = mutableSetOf<Point>()
    val queue = PriorityQueue<Point> { a, b -> levels[a.y][a.x].compareTo(levels[b.y][b.x]) }
        .also { it += Point() }
    while (queue.isNotEmpty()) {
        val p = queue.poll().also { visited += it }
        p.cardinalNeighbours.filter { it.y in map.indices && it.x in map[0].indices && it !in visited }.forEach {
            val level = levels[p.y][p.x] + map[it.y][it.x]
            if (level < levels[it.y][it.x]) {
                levels[it.y][it.x] = level
                queue += it
            }
        }
    }
    return levels.last().last()
}

private fun Array<IntArray>.expand(n: Int = 5): Array<IntArray> {
    val right = map { r ->
        (1 until n).fold(r) { acc, i -> acc + r.map { it + i }.map { if (it > 9) it - 9 else it }.toIntArray() }
    }.toTypedArray()
    return (1 until n).fold(right) { acc, i ->
        acc + right.map { r -> r.map { it + i }.map { if (it > 9) it - 9 else it }.toIntArray() }.toTypedArray()
    }
}
