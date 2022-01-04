fun main() {
    val (xRange, yRange) = readText("17")
        .split(": ")[1]
        .split(", ")
        .map { it.split("=")[1] }
        .map { s ->
            val (a, b) = s.split("..").map { it.toInt() }
            a..b
        }
    print { part1(yRange.min) }
    print { part2(xRange to yRange) }
}

private fun part1(y: Int) = (y * (y + 1)) / 2

private fun part2(area: Area): Int {
    val xRange = (((1..area.x.min).first { it.let { (1 + it) * it / 2 } >= area.x.min })..area.x.max)
    val yRange = (area.y.min until -area.y.min)
    return xRange.sumOf { x ->
        yRange.count { y ->
            val xPossibilities = generateSequence(0 to x) { it.first + it.second to maxOf(0, it.second - 1) }
                .takeWhile { it.first <= area.x.last }
                .map { it.first }
            val yPossibilities = generateSequence(0 to y) { it.first + it.second to it.second - 1 }
                .takeWhile { it.first >= area.y.min }
                .map { it.first }
            (xPossibilities zip yPossibilities).any { it in area }
        }
    }
}

private typealias Area = Pair<IntRange, IntRange>

private val Area.x get() = first
private val Area.y get() = second
private operator fun Area.contains(p: Pair<Int, Int>) = p.first in first && p.second in y
private val IntRange.min get() = first
private val IntRange.max get() = last
