fun main() {
    println(Day18.solvePart1())
    println(Day18.solvePart2())
}

object Day18 {
    private val numbers = readInput("day18").map { parse(it) }

    fun solvePart1() = numbers.reduce(::add).magnitude()

    fun solvePart2() = (0 until numbers.lastIndex)
        .flatMap { i ->
            (i + 1..numbers.lastIndex).flatMap { j ->
                listOf(i to j, j to i)
            }
        }
        .map { (i, j) -> add(numbers[i], numbers[j]) }
        .maxOf { it.magnitude() }

    private fun add(a: SNumber, b: SNumber): SNumber {
        return SNumber((a.values + b.values).map { it.copy(depth = it.depth + 1) }.toMutableList()).reduce()
    }

    private fun SNumber.reduce(): SNumber {
        while (explode() || split()) {
            // the show must go on
        }
        return this
    }

    private fun SNumber.explode(): Boolean {
        val i = values.windowed(2).indexOfFirst { (l, r) -> l.depth == r.depth && l.depth >= 5 }.takeIf { it >= 0 } ?: return false
        val l = values.removeAt(i)
        val r = values.removeAt(i)
        values.getOrNull(i - 1)?.apply { value += l.value }
        values.getOrNull(i)?.apply { value += r.value }
        values.add(i, SValue(0, l.depth - 1))
        return true
    }

    private fun SNumber.split(): Boolean {
        val i = values.indexOfFirst { it.value >= 10 }.takeIf { it >= 0 } ?: return false
        val num = values.removeAt(i)
        values.add(i, SValue(num.value - num.value / 2, num.depth + 1))
        values.add(i, SValue(num.value / 2, num.depth + 1))
        return true
    }

    private fun SNumber.magnitude(): Int {
        val v = values.toMutableList()
        for (depth in 4 downTo 1) {
            var i = v.indexOfFirst { it.depth == depth }
            while (i >= 0) {
                val l = v.removeAt(i).value
                val r = v.removeAt(i).value
                v.add(i, SValue(l * 3 + r * 2, depth - 1))
                i = v.indexOfFirst { it.depth == depth }
            }
        }
        return v.single().value
    }

    private fun parse(line: String): SNumber {
        var depth = 0
        return line.fold<MutableList<SValue>>(mutableListOf()) { list, c ->
            when (c) {
                '[' -> depth++
                ']' -> depth--
                in '0'..'9' -> list += SValue(c.digitToInt(), depth)
            }
            list
        }.let { SNumber(it) }
    }

    private data class SValue(var value: Int, val depth: Int)
    private data class SNumber(val values: MutableList<SValue>)
}
