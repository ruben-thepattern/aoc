fun main() {
    val input = readText("13").split("\n\n")
    val map = input[0].split('\n').filter { it.isNotEmpty() }.map {
        val (x, y) = it.split(",")
        x.toInt() to y.toInt()
    }.toSet()
    val folds = input[1].split('\n').filter { it.isNotEmpty() }.map {
        val (d, v) = it.split(' ')[2].split("=")
        d[0] to v.toInt()
    }
    print { part1(map, folds) }
    print { part2(map, folds) }
}

private fun part1(dots: Set<Pair<Int, Int>>, folds: List<Pair<Char, Int>>) =
    dots.fold(folds[0].first, folds[0].second).count()

private fun part2(dots: Set<Pair<Int, Int>>, folds: List<Pair<Char, Int>>) =
    folds.fold(dots) { ff, (axis, v) -> ff.fold(axis, v) }.print()

private fun Set<Pair<Int, Int>>.fold(axis: Char, v: Int) =
    (filter { (x, y) -> if (axis == 'x') x <= v else y <= v } + filter { (x, y) -> if (axis == 'x') x > v else y > v }.map { (x, y) -> if (axis == 'x') (x - (x - v) * 2 to y) else (x to y - (y - v) * 2) }).toSet()

private fun Set<Pair<Int, Int>>.print() =
    with(Array(maxOf { it.second } + 1) { CharArray(maxOf { it.first } + 1) { ' ' } }) {
        this@print.forEach { (x, y) -> this[y][x] = '#' }
        "\n" + joinToString("\n") { it.joinToString("") }
    }
