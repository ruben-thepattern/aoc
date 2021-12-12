import kotlin.collections.ArrayDeque

fun main() {
    val input = readInput("10")
    print { part11(input) }
    print { part22(input) }
}

private fun part11(input: List<String>) =
    input.sumOf {
        val chars = ArrayDeque<Char>()
        it.forEach { c ->
            when (c) {
                ')' -> if (chars.firstOrNull() == '(') chars.removeFirst() else return@sumOf 3
                ']' -> if (chars.firstOrNull() == '[') chars.removeFirst() else return@sumOf 57
                '}' -> if (chars.firstOrNull() == '{') chars.removeFirst() else return@sumOf 1197
                '>' -> if (chars.firstOrNull() == '<') chars.removeFirst() else return@sumOf 25137
                else -> chars.addFirst(c)
            }
        }
        0L
    }

private fun part22(input: List<String>): Long {
    val scores = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
    return input.mapNotNull {
        val chars = ArrayDeque<Char>()
        it.forEach { c ->
            when (c) {
                ')' -> if (chars.firstOrNull() == '(') chars.removeFirst() else return@mapNotNull null
                ']' -> if (chars.firstOrNull() == '[') chars.removeFirst() else return@mapNotNull null
                '}' -> if (chars.firstOrNull() == '{') chars.removeFirst() else return@mapNotNull null
                '>' -> if (chars.firstOrNull() == '<') chars.removeFirst() else return@mapNotNull null
                else -> chars.addFirst(c)
            }
        }
        chars.fold(0L) { acc, c -> acc * 5 + scores[c]!! }
    }.sorted().median()
}
