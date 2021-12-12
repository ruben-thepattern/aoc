fun main() {
    val input = readInput("11").map { s -> s.map { it.digitToInt() }.toIntArray() }.toTypedArray()
    print { part1(input) }
    print { part2(input) }
}

private fun part1(input: Array<IntArray>): Int {
    val m = input.map { it.copyOf() }.toTypedArray()
    return (0 until 100).sumOf { m.step() }
}

private fun part2(input: Array<IntArray>): Int {
    val m = input.map { it.copyOf() }.toTypedArray()
    var i = 0
    while (true) {
        i++
        m.step()
        if (m.flatMap { it.toSet() }.toSet().size == 1) return i
    }
}

private fun Array<IntArray>.step(): Int {
    indices.forEach { y -> this[y].indices.forEach { x -> this[y][x]++ } }
    var flashes = 0
    while (true) {
        var hasFlashes = false
        indices.forEach { y ->
            this[y].indices.forEach { x ->
                if (this[y][x] > 9) {
                    (-1..1).forEach { dy ->
                        (-1..1).forEach { dx ->
                            if (y + dy in indices && x + dx in this[y + dy].indices && this[y + dy][x + dx] != 0) this[y + dy][x + dx]++
                        }
                    }
                    this[y][x] = 0
                    flashes++
                    hasFlashes = true
                }
            }
        }
        if (!hasFlashes) break
    }
    return flashes
}
