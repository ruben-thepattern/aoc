fun main() {
    val input = readInput("09")
    print { part1(input) }
    print { part2(input) }
}

private fun part1(input: List<String>) =
    input.withIndex().sumOf { (y, s) ->
        s.withIndex().sumOf { (x, c) ->
            val neighbours = listOfNotNull(
                input.getOrNull(y - 1)?.getOrNull(x),
                input.getOrNull(y + 1)?.getOrNull(x),
                s.getOrNull(x - 1),
                s.getOrNull(x + 1)
            )
            if (neighbours.all { it > c }) c.digitToInt() + 1 else 0
        }
    }

private fun part2(input: List<String>): Int {
    val basins = mutableListOf<Int>()
    val grid = Array(input.size) { y -> BooleanArray(input[y].length) { x -> input[y][x].digitToInt() >= 9 } }
    input.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (!grid[y][x] && c.digitToInt() < 9) basins += grid.getBasin(x, y)
        }
    }
    return basins.sorted().takeLast(3).fold(1) { x, y -> x * y }
}

private fun Array<BooleanArray>.getBasin(x: Int, y: Int): Int {
    this[y][x] = true
    var basin = 0
    val stack = mutableListOf(x to y)
    while (true) {
        val (x2, y2) = stack.removeLastOrNull() ?: break
        basin++
        CardinalDirection.forEach(x2, y2) { x3, y3 ->
            if (y3 in this.indices && x3 in this[y3].indices && !this[y3][x3]) {
                this[y3][x3] = true
                stack += x3 to y3
            }
        }
    }
    return basin
}
