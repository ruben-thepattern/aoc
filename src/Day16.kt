fun main() {
    val input = readText("16").trim()
        .map { c -> c.digitToInt(16).toString(2).padStart(4, '0') }
        .joinToString("")
        .parse()
    print { part1(input) }
    print { part2(input) }
}

private fun part1(input: Packet): Int {
    fun Packet.versionSum(): Int = when (this) {
        is Literal -> bits.take(3).toInt(2)
        is Operator -> bits.take(3).toInt(2) + subpackets.sumOf { it.versionSum() }
    }
    return input.versionSum()
}

private fun part2(input: Packet) = input.value

sealed class Packet(val bits: String) {
    abstract val value: Long
    val type = bits.drop(3).take(3).toInt(2)
}

class Literal(bits: String) : Packet(bits) {
    override val value: Long = bits.drop(6)
        .chunked(5)
        .joinToString("") { it.drop(1) }
        .toLong(2)
}

class Operator(bits: String, val subpackets: List<Packet>) : Packet(bits) {
    override val value: Long
        get() = when (type) {
            0 -> subpackets.sumOf { it.value }
            1 -> subpackets.fold(1) { acc, packet -> acc * packet.value }
            2 -> subpackets.minOf { it.value }
            3 -> subpackets.maxOf { it.value }
            5 -> if (subpackets[0].value > subpackets[1].value) 1L else 0L
            6 -> if (subpackets[0].value < subpackets[1].value) 1L else 0L
            7 -> if (subpackets[0].value == subpackets[1].value) 1L else 0L
            else -> error("")
        }
}

private fun String.parse(): Packet {
    val type = drop(3).take(3).toInt(2)
    val rest = drop(6)
    return when (type) {
        4 -> rest.chunked(5)
            .let { s -> s.takeWhile { it.first() == '1' } + s.first { it.first() == '0' } }
            .let { Literal("${take(6)}${it.joinToString("")}") }
        else -> {
            if (rest.first() == '0') {
                val length = rest.drop(1).take(15).toInt(2)
                val subpackets = buildList<Packet> {
                    while (sumOf { it.bits.length } < length) {
                        rest.drop(16 + sumOf { it.bits.length }).parse().also { add(it) }
                    }
                }
                Operator("${take(22)}${subpackets.joinToString("") { it.bits }}", subpackets)
            } else {
                val count = rest.drop(1).take(11).toInt(2)
                val subpackets = buildList<Packet> {
                    repeat(count) {
                        rest.drop(12 + sumOf { it.bits.length }).parse().also { add(it) }
                    }
                }
                Operator("${take(18)}${subpackets.joinToString("") { it.bits }}", subpackets)
            }
        }
    }
}
