fun main() {
    println(Day02.solvePart1())
    println(Day02.solvePart2())
}


object Day02 {
    private val input = readInput("day02")
        .map { it.split(" ") }
        .map { (direction, distance) -> direction to distance.toInt() }

    fun solvePart1(): Int {
        var depth = 0
        var totalDistance = 0

        input.forEach { (direction, distance) ->
            when (direction) {
                "forward" -> totalDistance += distance
                "up" -> depth -= distance
                "down" -> depth += distance
            }
        }

        return depth * totalDistance
    }

    fun solvePart2(): Int {
        var aim = 0
        var depth = 0
        var totalDistance = 0

        input.forEach { (direction, distance) ->
            when (direction) {
                "forward" -> {
                    totalDistance += distance
                    depth += aim * distance
                }
                "up" -> aim -= distance
                "down" -> aim += distance
            }
        }
        
        return depth * totalDistance
    }
}
