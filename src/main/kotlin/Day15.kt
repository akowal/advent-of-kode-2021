import java.util.PriorityQueue
import kotlin.math.abs

fun main() {
    println(Day15.solvePart1())
    println(Day15.solvePart2())
}

object Day15 {
    private val risks = readInput("day15").map { it.map(Char::digitToInt) }

    fun solvePart1() = findMinRisk(risks)

    fun solvePart2() = findMinRisk(tile(risks, 5))

    private fun findMinRisk(risks: List<List<Int>>): Int {
        val start = Point(0, 0)
        val end = Point(risks.lastIndex, risks.first().lastIndex)
        val cameFrom = mutableMapOf<Point, Point>()
        val gscore = mutableMapOf(start to 0)
        val fscore = mutableMapOf(start to manhattanDist(start, end))
        val frontier = PriorityQueue<Point> { a, b -> manhattanDist(b, end) compareTo manhattanDist(a, end) }

        frontier.add(start)

        fun Point.adjacent() = listOf(
            Point(x - 1, y),
            Point(x + 1, y),
            Point(x, y - 1),
            Point(x, y + 1)
        ).filter { it.x in 0..end.x && it.y in 0..end.y }

        while (frontier.isNotEmpty()) {
            val cur = frontier.poll()
            if (cur == end) {
                return pathRisk(end, risks, cameFrom)
            }
            cur.adjacent().forEach { neighbour ->
                val tentativeGscore = gscore[cur]!! + risks[neighbour.x][neighbour.y]
                if (tentativeGscore < (gscore[neighbour] ?: Int.MAX_VALUE)) {
                    cameFrom[neighbour] = cur
                    gscore[neighbour] = tentativeGscore
                    fscore[neighbour] = tentativeGscore + manhattanDist(neighbour, end)
                    frontier.remove(neighbour)
                    frontier.add(neighbour)
                }
            }

        }
        error("xoxoxo")
    }

    private fun pathRisk(end: Point, risks: List<List<Int>>, cameFrom: Map<Point, Point>): Int {
        var risk = 0
        var n: Point? = end
        while (n != null) {
            risk += risks[n.x][n.y]
            n = cameFrom[n]
        }
        return risk - risks[0][0]
    }

    private fun tile(src: List<List<Int>>, tiles: Int): List<List<Int>> {
        val tmp = src.map { list ->
            (0 until tiles).flatMap { tile ->
                list.map { addRisk(it, tile) }
            }
        }
        return (0 until tiles).flatMap { tile ->
            tmp.map { list ->
                list.map { addRisk(it, tile) }
            }
        }
    }

    private fun addRisk(a: Int, b: Int) = (a + b).let {
        if (it > 9) it - 9 else it
    }

    private fun manhattanDist(a: Point, b: Point) = abs(a.x - b.x) + abs(a.y - b.y)
}
