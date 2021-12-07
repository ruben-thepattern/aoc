import kotlin.math.abs

fun main() {
    val input = readInput("07").first().split(",").map { it.toInt() }
    print { part1(input) }
    print { part2(input) }
}

private fun part1(input: List<Int>): Int =
    input.sorted()[input.size / 2].let { median -> input.sumOf { abs(it - median) } }

private fun part2(input: List<Int>): Int =
    input.average().toInt().let { mean -> input.sumOf { abs(it - mean) * (abs(it - mean) + 1) / 2 } }
