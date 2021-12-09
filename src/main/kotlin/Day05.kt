import Day05.Orientation.*

fun main() {
    println(Day05.solvePart1())
    println(Day05.solvePart2())
}

object Day05 {
    private val allLines = readInput("day05")
        .asSequence()
        .map { it.replace(" -> ", ",") }
        .map { it.toIntArray() }
        .map { (x1, y1, x2, y2) -> Line(x1, y1, x2, y2) }

    fun solvePart1() = countIntersections(allLines.filter { it.orientation != DIAGONAL })
    fun solvePart2() = countIntersections(allLines)

    private fun countIntersections(lines: Sequence<Line>): Int {
        val map = mutableMapOf<Point, Int>()
        lines.flatMap { it.calculatePoints() }
            .forEach {
                map.compute(it) { _, hits -> hits?.inc() ?: 1 }
            }

        return map.values.count { it > 1 }
    }

    private fun Line.calculatePoints() = when (orientation) {
        VERTICAL -> range(y1, y2).map { Point(x1, it) }
        HORIZONTAL -> range(x1, x2).map { Point(it, y1) }
        DIAGONAL -> range(x1, x2).zip(range(y1, y2)).map { (x, y) -> Point(x, y) }
    }

    private fun range(a: Int, b: Int) = if (a < b) {
        a..b
    } else {
        a downTo b
    }.asSequence()

    private enum class Orientation { VERTICAL, HORIZONTAL, DIAGONAL }

    private data class Line(
        val x1: Int,
        val y1: Int,
        val x2: Int,
        val y2: Int,
        val orientation: Orientation = when {
            x1 == x2 -> VERTICAL
            y1 == y2 -> HORIZONTAL
            else -> DIAGONAL
        }
    )
}
