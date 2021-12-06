fun main() {
    val input = readInput("03")
    val ints = input.map { it.toInt(2) }
    val width = input.first().length
    print { part1(ints, width) }
    print { part2(ints, width) }
}

private fun List<Int>.mostCommon(i: Int) = (sumOf { (it shr i) and 1 } / (size * 1.0f) + 0.5f).toInt()

private fun part1(input: List<Int>, width: Int): Int {
    val gamma = (0 until width).sumOf { i -> input.mostCommon(i) shl i }
    val epsilon = (0 until width).sumOf { i -> (1 - input.mostCommon(i)) shl i }
    return gamma * epsilon
}

private fun part2(input: List<Int>, width: Int): Int {
    fun calculateRating(a: Int): Int {
        var remaining = input
        var i = width - 1
        while (remaining.size > 1) {
            val most = remaining.mostCommon(i) xor a
            remaining = remaining.filter { it shr i and 1 == most }
            i--
        }
        return remaining.single()
    }

    val o2 = calculateRating(1)
    val co2 = calculateRating(0)
    return o2 * co2
}
