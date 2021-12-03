fun main() {
    println(Day01.solvePart1())
    println(Day01.solvePart2())
}


object Day01 {
    private val input = readInput("day01").map { it.toInt() }

    fun solvePart1() = input.windowed(2)
        .count {(a, b) -> a < b}

    fun solvePart2() = input.windowed(3)
        .map { it.sum() }
        .windowed(2)
        .count {(a, b) -> a < b}
}
