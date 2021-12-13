fun main() {
    println(Day10.solvePart1())
    println(Day10.solvePart2())
}

object Day10 {
    private val input = readInput("day10")
    private val closing = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )
    private val errorScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )
    private val autocompleteScores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    fun solvePart1() = input.sumOf { errorScore(it) }

    fun solvePart2() = input.filter { errorScore(it) == 0 }
        .map { autocompleteScore(it) }
        .sorted()
        .let { it[it.size / 2] }

    private fun errorScore(s: String): Int {
        val expect = ArrayDeque<Char>()
        for (c in s) {
            when (c) {
                '(', '[', '{', '<' -> expect.addLast(closing[c]!!)
                ')', ']', '}', '>' -> c == expect.removeLast() || return errorScores[c]!!
            }
        }
        return 0
    }

    private fun autocompleteScore(s: String): Long {
        val expect = ArrayDeque<Char>()
        for (c in s) {
            when (c) {
                '(', '[', '{', '<' -> expect.addLast(closing[c]!!)
                ')', ']', '}', '>' -> c == expect.removeLast() || error("oops")
            }
        }
        return expect.reversed().fold(0L) { acc, c -> acc * 5 + autocompleteScores[c]!! }
    }
}
