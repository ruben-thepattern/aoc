fun main() {
    val school = readInput("06").first().split(",").map { it.toInt() }
    print { part1(school) }
    print { part2(school) }
}

private fun part1(school: List<Int>) = school.evolve(80)
private fun part2(school: List<Int>) = school.evolve(256)

private fun List<Int>.evolve(days: Int, rate: Int = 8) =
    (0 until days).fold((0..rate).map { n -> count { it == n }.toLong() }.toLongArray()) { school, it ->
        school[(it % school.size + (rate - 1)) % school.size] += school[it % school.size]
        school
    }.sum()
