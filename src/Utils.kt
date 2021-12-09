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

fun Collection<Int>.min() = minOrNull()!!
fun Collection<Int>.max() = maxOrNull()!!

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun times(factor: Int) = Point(x * factor, y * factor)
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
