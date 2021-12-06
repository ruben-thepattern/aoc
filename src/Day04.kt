fun main() {
    fun List<String>.getBoards() = drop(1).map { b ->
        val a = b.split("\n").filter { it.isNotBlank() }
            .map { r ->
                r.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toIntArray()
            }.toTypedArray()
        a to Array(a.size) { BooleanArray(a[0].size) }
    }

    val input = readText("04").split("\n\n")
    val numbers = input.first().split(",").map { it.toInt() }
    var boards = input.getBoards()
    print { part1(numbers, boards) }
    boards = input.getBoards()
    print { part2(numbers, boards) }
}

private fun part1(numbers: List<Int>, boards: List<Board>): Int {
    numbers.forEach { n ->
        boards.forEach {
            it.mark(n)
            if (it.isComplete) return it.sum * n
        }
    }
    return 0
}

private fun part2(numbers: List<Int>, boards: List<Board>): Int {
    var remaining = boards
    numbers.forEach { n ->
        remaining.forEach { it.mark(n) }
        val winners = remaining.filter { it.isComplete }
        remaining = remaining.filterNot { it in winners }
        if (remaining.isEmpty()) return winners.last().sum * n
    }
    return 0
}

private typealias Board = Pair<Array<IntArray>, Array<BooleanArray>>

private fun Board.mark(n: Int) {
    first.forEachIndexed { y, row -> row.forEachIndexed { x, c -> if (c == n) second[y][x] = true } }
}

private val Board.isComplete
    get() = second.indices.any { y -> second[y].all { it } } || second[0].indices.any { x -> second.all { it[x] } }

private val Board.sum: Int
    get() {
        var sum = 0
        second.indices.forEach { y ->
            second[y].indices.forEach { x ->
                if (!second[y][x]) sum += first[y][x]
            }
        }
        return sum
    }
