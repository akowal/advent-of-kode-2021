fun main() {
    println(Day11.solvePart1())
    println(Day11.solvePart2())
}

object Day11 {
    private val input = readInput("day11")

    fun solvePart1(): Int {
        val levels = input.map { it.map(Char::digitToInt).toIntArray() }.toTypedArray()
        var flashes = 0
        repeat(100) {
            flashes += runStep(levels)
        }
        return flashes
    }

    fun solvePart2(): Int {
        val levels = input.map { it.map(Char::digitToInt).toIntArray() }.toTypedArray()
        val flashAll = levels.size * levels.first().size
        var steps = 1
        while (runStep(levels) != flashAll) {
            steps++
        }
        return steps
    }

    private fun runStep(levels: Array<IntArray>): Int {
        var flashes = 0
        val flashed = mutableSetOf<Point>()
        val flash = ArrayDeque<Point>()

        for (x in 0..levels.lastIndex) {
            for (y in 0..levels.first().lastIndex) {
                if (levels[x][y]++ >= 9) {
                    flash += Point(x, y)
                }
            }
        }

        while (flash.isNotEmpty()) {
            val p = flash.removeFirst()
            if (!flashed.add(p)) {
                continue
            }
            flashes++
            levels[p.x][p.y] = 0
            flashed += p
            p.neighors()
                .filter { it !in flashed }
                .filter { levels.getOrNull(it.x)?.getOrNull(it.y) != null }
                .forEach {
                    if (levels[it.x][it.y]++ >= 9) {
                        flash += it
                    }
                }
        }
        return flashes
    }

    private fun Point.neighors() = listOf(
        Point(x, y + 1),
        Point(x, y - 1),
        Point(x + 1, y),
        Point(x - 1, y),
        Point(x - 1, y - 1),
        Point(x + 1, y + 1),
        Point(x + 1, y - 1),
        Point(x - 1, y + 1),
    )
}
