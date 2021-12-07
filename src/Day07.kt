import kotlin.math.abs

fun main() {
    val input = readInput("07").first().split(",").map { it.toInt() }
    print { part1(input) }
    print { part2(input) }
}

private fun part1(input: List<Int>) =
    input.indices.minOf { i -> input.sumOf { abs(it - i) } }

private fun part2(input: List<Int>) =
    input.indices.minOf { i -> input.sumOf { n -> abs(n - i).let { (it * (it + 1)) / 2 } } }
