fun main() {
    println(Day14.solvePart1())
    println(Day14.solvePart2())
}

object Day14 {
    private val input = readInput("day14")
    private val template = input.first()
    private val insertions = input.drop(2)
        .map { it.split(" -> ") }
        .associate { (pair, insert) -> pair to insert.single() }

    fun solvePart1() = polymerize(10)
    fun solvePart2() = polymerize(40)

    private fun polymerize(steps: Int): Long {
        val pairs = mutableMapOf<String, Long>()
        val freq = mutableMapOf<Char, Long>()
        template.windowed(2).forEach { pairs.addCount(it, 1) }
        template.forEach { freq.addCount(it, 1) }

        repeat(steps) {
            val inserted = mutableMapOf<String, Long>()
            insertions.forEach { (pair, insert) ->
                val count = pairs.remove(pair) ?: return@forEach
                inserted.addCount("${pair[0]}$insert", count)
                inserted.addCount("$insert${pair[1]}", count)
                freq.addCount(insert, count)
            }
            inserted.forEach { (pair, count) -> pairs.addCount(pair, count) }
        }
        return freq.maxOf { it.value } - freq.minOf { it.value }
    }

    private fun <T> MutableMap<T, Long>.addCount(key: T, x: Long) = compute(key) { _, n -> (n ?: 0) + x }
}
