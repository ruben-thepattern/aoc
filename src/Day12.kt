fun main() {
    val cave = mutableMapOf<String, List<String>>()
    readInput("12").forEach {
        val (a, b) = it.split('-')
        cave.compute(a) { _, v -> v?.plus(b) ?: listOf(b) }
        cave.compute(b) { _, v -> v?.plus(a) ?: listOf(a) }
    }
    print { part1(cave) }
    print { part2(cave) }
}

private fun part1(input: Map<String, List<String>>) = search(input, setOf(), false)

private fun part2(input: Map<String, List<String>>) = search(input, setOf(), true)

private fun search(input: Map<String, List<String>>, seen: Set<String>, canRepeat: Boolean, cave: String = "start"): Int {
    if (cave == "end") return 1
    if (cave in seen) {
        if (cave == "start") return 0
        if (cave == cave.lowercase()) {
            return if (!canRepeat) 0 else input[cave]?.sumOf { search(input, seen + cave, false, it) } ?: 0
        }
    }
    return input[cave]?.sumOf { search(input, seen + cave, canRepeat, it) } ?: 0
}
