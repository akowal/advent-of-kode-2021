fun main() {
    println(Day06.solvePart1())
    println(Day06.solvePart2())
}

object Day06 {
    private const val SPAWN_PERIOD = 7
    private const val INCUBATION= 2

    private val input = readInput("day06")
        .single()
        .toIntArray()


    fun solvePart1() = simulate(80)
    fun solvePart2() = simulate(256)

    private fun simulate(days: Int): Long {
        val spawnQueue = LongArray(SPAWN_PERIOD + INCUBATION)
        input.forEach { spawnQueue[it]++ }

        repeat(days) {
            val spawn = spawnQueue[0]
            for (i in 0 until spawnQueue.lastIndex) {
                spawnQueue[i] = spawnQueue[i + 1]
            }
            spawnQueue[SPAWN_PERIOD - 1] += spawn
            spawnQueue[spawnQueue.lastIndex] = spawn
        }
        return spawnQueue.sum()
    }
}
