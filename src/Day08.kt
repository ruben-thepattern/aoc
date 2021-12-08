fun main() {
    val input = readInput("08").map { s ->
        val (a, b) = s.split('|').map { it.trim() }
        a.split(' ') to b.split(' ')
    }
    print { part1(input) }
    print { part2(input) }
}

private fun part1(input: List<Pair<List<String>, List<String>>>): Int =
    input.sumOf { (_, v) -> v.count { it.length in setOf(2, 3, 4, 7) } }

private fun part2(input: List<Pair<List<String>, List<String>>>): Int =
    input.sumOf { (signals, values) ->
        val lengths = signals.associate { it.length to it.toSet() }
        values.map { it decodeFrom lengths }.joinToString("").toInt()
    }

private infix fun String.decodeFrom(lengths: Map<Int, Set<Char>>): Char =
    with(toSet().let { it.size to (it intersect lengths[4]!!).size to (it intersect lengths[2]!!).size }) {
        // first: number of segments
        // second: number of segments shared with 4
        // third: number of segments shared with 2
        when {
            first == 2 -> '1'
            first == 3 -> '7'
            first == 4 -> '4'
            first == 7 -> '8'
            first == 5 && second == 2 -> '2'
            first == 5 && second == 3 && third == 1 -> '5'
            first == 5 && second == 3 && third == 2 -> '3'
            first == 6 && second == 4 -> '9'
            first == 6 && second == 3 && third == 1 -> '6'
            first == 6 && second == 3 && third == 2 -> '0'
            else -> error("")
        }
    }
