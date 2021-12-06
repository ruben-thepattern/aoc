fun main() {
    val input = readInput("02").map { it.split(" ").let { p -> Dir.valueOf(p[0].uppercase()) to p[1].toInt() } }
    print { part1(input) }
    print { part2(input) }
}

private enum class Dir { FORWARD, DOWN, UP }

private fun part1(input: List<Pair<Dir, Int>>) =
    input.fold(0 to 0) { (horizontal, depth), (direction, x) ->
        when (direction) {
            Dir.FORWARD -> (horizontal + x) to depth
            Dir.DOWN -> horizontal to (depth + x)
            Dir.UP -> horizontal to (depth - x)
        }
    }.let { (horizontal, depth) -> horizontal * depth }

private fun part2(input: List<Pair<Dir, Int>>) =
    input.fold(0 to 0 to 0) { (horizontal, depth, aim), (direction, x) ->
        when (direction) {
            Dir.FORWARD -> horizontal + x to depth + (aim * x) to aim
            Dir.DOWN -> horizontal to depth to aim + x
            Dir.UP -> horizontal to depth to aim - x
        }
    }.let { (horizontal, depth, aim) -> horizontal * depth }
