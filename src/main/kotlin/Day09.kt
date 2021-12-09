fun main() {
    println(Day09.solvePart1())
    println(Day09.solvePart2())
}

object Day09 {
    private val heightMap = readInput("day09").map { it.map(Char::digitToInt) }

    fun solvePart1() = findLowPoints().sumOf { heightMap[it.x][it.y] + 1 }

    fun solvePart2() = findLowPoints()
        .map { calculateBasinSize(it) }
        .sortedDescending()
        .take(3)
        .reduce(Int::times)

    private fun findLowPoints(): List<Point> {
        val result = mutableListOf<Point>()
        for (x in 0..heightMap.lastIndex) {
            for (y in 0..heightMap.first().lastIndex) {
                val value = heightMap[x][y]
                val adjacentValues = adjacentPoints(x, y).mapNotNull { getOrNull(it) }
                if (adjacentValues.all { it > value }) {
                    result += Point(x, y)
                }
            }
        }
        return result
    }

    private fun calculateBasinSize(start: Point): Int {
        val seen = mutableSetOf<Point>()
        val queue = ArrayDeque<Point>().also { it.add(start) }
        var result = 0
        while (queue.isNotEmpty()) {
            val p = queue.removeFirst()
            seen.add(p) || continue
            result++
            queue += adjacentPoints(p.x, p.y).filter { getOrNull(it)?.let { v -> v < 9 } ?: false }
        }
        return result
    }

    private fun getOrNull(p: Point): Int? = heightMap.getOrNull(p.x)?.getOrNull(p.y)

    private fun adjacentPoints(x: Int, y: Int) = listOf(
        x - 1 to y,
        x + 1 to y,
        x to y - 1,
        x to y + 1
    ).map { (x, y) -> Point(x, y) }
}
