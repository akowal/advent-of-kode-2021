import kotlin.math.abs

fun main() {
    println(Day07.solvePart1())
    println(Day07.solvePart2())
}

object Day07 {
    private val positions = readInput("day07")
        .single()
        .toIntArray()
        .sorted()

    fun solvePart1(): Int {
        val median = positions.run { (get(size / 2) + get(size / 2 + size % 2)) / 2 }
        return positions.sumOf { abs(it - median) }
    }

    fun solvePart2(): Int {
        fun cost(a: Int, b: Int) = abs(a - b).let { it * (it + 1) / 2 }
        val mean = positions.average()
        val min = (mean - 1).toInt()
        val max = (mean + 1).toInt()
        return (min..max).minOf { pos -> positions.sumOf { cost(it, pos) } }
    }
}
