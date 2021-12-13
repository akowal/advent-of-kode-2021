fun main() {
    println(Day12.solvePart1())
    println(Day12.solvePart2())
}

object Day12 {
    private val connections = readInput("day12")
        .map { it.split('-') }
        .flatMap { (a, b) -> listOf(a to b, b to a) }
        .groupBy({ it.first }, { it.second })

    fun solvePart1() = traverse("start", emptySet(), false)
    fun solvePart2() = traverse("start", emptySet(), true)

    private fun traverse(
        cave: String,
        path: Set<String>,
        allowSmallCaveReentrance: Boolean
    ): Int {
        val reenteredSmallCave = cave.all { it.isLowerCase() } && cave in path
        when {
            cave == "end" -> return 1
            reenteredSmallCave && cave == "start" -> return 0
            reenteredSmallCave && !allowSmallCaveReentrance -> return 0
        }
        return connections[cave]!!.sumOf {
            traverse(it, path + cave, allowSmallCaveReentrance && !reenteredSmallCave)
        }
    }
}
