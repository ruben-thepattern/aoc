import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.pow
import kotlin.system.measureNanoTime

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> = File("inputs", "$name.txt").readLines().filter { it.isNotBlank() }

fun readText(name: String): String = File("inputs", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun <T> print(block: () -> T) {
    var result: T?
    val elapsed = measureNanoTime { result = block() }
    println("${String.format("%.2f", elapsed / 1_000_000.0)}ms: $result")
}

infix fun <A, B, C> Pair<A, B>.to(c: C): Triple<A, B, C> = Triple(first, second, c)

fun Int.pow(n: Int): Int = toDouble().pow(n).toInt()
fun Long.pow(n: Int): Long = toDouble().pow(n).toLong()
fun <T : Comparable<T>> List<T>.median(): T = sorted()[size / 2]

fun <T : Comparable<T>> Collection<T>.min() = minOrNull()!!
fun <T : Comparable<T>> Collection<T>.max() = maxOrNull()!!

data class Point(val x: Int = 0, val y: Int = 0) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun times(factor: Int) = Point(x * factor, y * factor)

    val neighbours by lazy {
        listOf(
            Point(x - 1, y - 1),
            Point(x, y - 1),
            Point(x + 1, y + 1),
            Point(x - 1, y),
            Point(x + 1, y),
            Point(x + 1, y - 1),
            Point(x, y + 1),
            Point(x + 1, y + 1)
        )
    }
    val cardinalNeighbours by lazy { listOf(north, east, south, west) }
    val north by lazy { Point(x, y - 1) }
    val south by lazy { Point(x, y + 1) }
    val east by lazy { Point(x + 1, y) }
    val west by lazy { Point(x - 1, y) }
}

enum class CardinalDirection {
    NORTH, EAST, SOUTH, WEST;

    companion object {
        inline fun forEach(x: Int, y: Int, block: (x: Int, y: Int) -> Unit) {
            block(x, y - 1)
            block(x - 1, y)
            block(x + 1, y)
            block(x, y + 1)
        }
    }
}
