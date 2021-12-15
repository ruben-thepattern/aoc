fun main() {
    val input = readInput("14")
    val template = input.take(1)[0]
    val recipes = input.drop(1).associate { s -> s.split(" -> ").let { it[0] to it[1] } }
    print { part1(template, recipes) }
    print { part2(template, recipes) }
}

private fun part1(template: String, recipes: Map<String, String>) = simulate(template, recipes, 10)

private fun part2(template: String, recipes: Map<String, String>) = simulate(template, recipes, 40)

private fun simulate(template: String, recipes: Map<String, String>, steps: Int): Long {
    var bigrams = template.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }
    repeat(steps) {
        bigrams = buildMap {
            bigrams.forEach { (pair, count) ->
                val element = recipes[pair]!!
                compute(pair[0] + element) { _, v -> (v ?: 0) + count }
                compute(element + pair[1]) { _, v -> (v ?: 0) + count }
            }
        }
    }
    val counts = mutableMapOf(template.last() to 1L).apply {
        bigrams.entries.forEach { (pair, count) -> pair.forEach { compute(it) { _, v -> (v ?: 0) + count } } }
    }.mapValues { it.value / 2 }
    return counts.values.max() - counts.values.min()
}
