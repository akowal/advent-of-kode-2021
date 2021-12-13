fun main() {
    println(Day13.solvePart1())
    Day13.solvePart2()
}

object Day13 {
    private val input = readInput("day13")
    private val points = input.takeWhile { it.isNotBlank() }
        .map { it.toIntArray() }
        .map { (x, y) -> Point(x, y) }
    private val folds = input.takeLastWhile { it.isNotBlank() }
        .map { it.substringAfter("fold along ") }
        .map { it.split('=') }
        .map { (axis, value) ->
            when (axis) {
                "x" -> Fold.X(value.toInt())
                "y" -> Fold.Y(value.toInt())
                else -> error("xoxoxo")
            }
        }

    fun solvePart1() = fold(points.toMutableSet(), folds.take(1)).size

    fun solvePart2() {
        val result = fold(points.toMutableSet(), folds)
        val width = result.maxOf { it.x } + 1
        result.groupBy { it.y }.toSortedMap().forEach { (_, row) ->
            val line = CharArray(width) { ' ' }
            row.forEach { line[it.x] = '#' }
            println(line)
        }
    }

    private fun fold(points: MutableSet<Point>, folds: List<Fold>): Set<Point> {
        folds.forEach { fold ->
            points.filter(fold::filter).forEach {
                points -= it
                points += fold.apply(it)
            }
        }
        return points
    }

    private sealed interface Fold {
        fun filter(p: Point): Boolean
        fun apply(p: Point): Point

        data class X(private val x: Int) : Fold {
            override fun filter(p: Point) = p.x > x
            override fun apply(p: Point) = Point(x - (p.x - x), p.y)
        }

        data class Y(private val y: Int) : Fold {
            override fun filter(p: Point) = p.y > y
            override fun apply(p: Point) = Point(p.x, y - (p.y - y))
        }
    }
}
