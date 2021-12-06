fun main() {
    val school = readInput("06").first().split(",").map { it.toInt() }
    print { part1(school) }
    print { part2(school) }
}

private fun part1(school: List<Int>) = school.evolve(80)
private fun part2(school: List<Int>) = school.evolve(256)

private fun List<Int>.evolve(days: Int): Long {
    val school = (0..8).map { n -> count { it == n }.toLong() }.toLongArray()
    repeat(days) {
        val d = it.mod(school.size)
        school[(d + 7).mod(school.size)] += school[d]
    }
    return school.sum()
}
