import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

fun main() {
    val input = readInput("05").map {
        val (a, b) = it.split(" -> ").map { p ->
            val (x, y) = p.split(",")
            Point(x.toInt(), y.toInt())
        }
        a to b
    }
    print { part1(input) }
    print { part2(input) }
}

private fun part1(input: List<Pair<Point, Point>>): Int =
    input.asSequence()
        .filter { it.isStraight }
        .flatMap { it.first lineTo it.second }
        .groupingBy { it }
        .eachCount()
        .count { it.value >= 2 }

private fun part2(input: List<Pair<Point, Point>>): Int =
    input.asSequence()
        .flatMap { it.first lineTo it.second }
        .groupingBy { it }
        .eachCount()
        .count { it.value >= 2 }

private infix fun Point.lineTo(destination: Point): List<Point> {
    val xDir = (destination.x - x).sign
    val yDir = (destination.y - y).sign
    return (0..max(abs(destination.x - x), abs(destination.y - y))).map { Point(x + it * xDir, y + it * yDir) }
}

private val Pair<Point, Point>.isStraight get() = first.x == second.x || first.y == second.y
