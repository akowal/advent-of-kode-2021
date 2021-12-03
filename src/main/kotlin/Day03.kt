fun main() {
    println(Day03.solvePart1())
    println(Day03.solvePart2())
}


object Day03 {
    private val input = readInput("day03")
    private val nbits = input.first().length

    fun solvePart1(): Int {
        val gamma = (0 until nbits)
            .map { pos -> input.count { bits -> bits[pos] == '1' } }
            .fold(0) { acc, ones ->
                var x = acc.shl(1)
                if (ones > input.size / 2) {
                    x++
                }
                x
            }
        val epsilon = gamma.inv().and(1.shl(nbits) - 1)
        return gamma * epsilon
    }

    fun solvePart2(): Int {
        val o2GeneratorRating = calculateRating { zeros, ones -> zeros > ones }
        val co2ScrubberRating = calculateRating { zeros, ones -> zeros <= ones }

        return o2GeneratorRating * co2ScrubberRating
    }

    private fun calculateRating(selectZeros: (Int, Int) -> Boolean): Int {
        var candidates = input

        for (pos in 0 until nbits) {
            val zeros = candidates.filter { it[pos] == '0' }
            val ones = candidates.filter { it[pos] == '1' }

            if (selectZeros(zeros.size, ones.size)) {
                candidates = zeros
            } else {
                candidates = ones
            }

            if (candidates.size == 1) {
                break
            }
        }

        return candidates.single().toInt(2)
    }
}
