fun main() {
    println(Day04.solvePart1())
    println(Day04.solvePart2())
}

object Day04 {
    private const val BOARD_SIZE = 5

    private val numbers: IntArray
    private val boards: List<Board>

    init {
        val input = inputScanner("day04")
        numbers = input.nextLine().toIntArray()
        boards = sequence { while (input.hasNextInt()) yield(input.nextInt()) }
            .windowed(BOARD_SIZE * BOARD_SIZE, BOARD_SIZE * BOARD_SIZE)
            .map { createBoard(it) }
            .toList()
    }

    fun solvePart1(): Int {
        numbers.forEach { number ->
            boards.forEach { board ->
                if (board.mark(number) && board.hasWon()) {
                    return board.unmarked.keys.sum() * number
                }
            }
        }
        error("ouch")
    }

    fun solvePart2(): Int {
        val boardsLeft = boards.toMutableList()
        numbers.forEach { number ->
            val iter = boardsLeft.iterator()
            while (iter.hasNext()) {
                val board = iter.next()
                if (board.mark(number) && board.hasWon()) {
                    if (boardsLeft.size == 1) {
                        return board.unmarked.keys.sum() * number
                    }
                    iter.remove()
                }
            }
        }
        error("ouch")
    }

    private fun createBoard(values: List<Int>): Board {
        val numToPos = values.mapIndexed { i, value ->
            value to Position(i/ BOARD_SIZE, i % BOARD_SIZE)
        }
        return Board(numToPos.toMap().toMutableMap())
    }

    private data class Position(val row: Int, val col: Int)

    @Suppress("ArrayInDataClass")
    private data class Board(
        val unmarked: MutableMap<Int, Position>,
        val rowHits: IntArray = IntArray(BOARD_SIZE),
        val colHits: IntArray = IntArray(BOARD_SIZE),
    )

    private fun Board.hasWon() = rowHits.any { it == BOARD_SIZE } || colHits.any { it == BOARD_SIZE }

    private fun Board.mark(number: Int): Boolean {
        return unmarked.remove(number)?.apply {
            rowHits[row]++
            colHits[col]++
        } != null
    }
}
