fun main() {
    val input = readInput("01").map { it.toInt() }
    print { part1(input) }
    print { part2(input) }
}

private fun part1(input: List<Int>) = input.windowed(2).count { it.first() < it.last() }
private fun part2(input: List<Int>) = input.windowed(4).count { it.first() < it.last() }
