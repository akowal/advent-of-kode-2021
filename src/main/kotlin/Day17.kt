import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun main() {
    println(Day17.solvePart1())
    println(Day17.solvePart2())
}

object Day17 {
    private val targetTopLeft: Point
    private val targetBottomRight: Point

    init {
        val input = readInput("day17").single()
        val (x1, x2, y1, y2) = """.*x=(.+)\.\.(.+), y=(.+)\.\.(.+)""".toRegex().matchEntire(input)
            ?.groupValues?.drop(1)?.map(String::toInt) ?: error("xoxoxo")
        targetTopLeft = Point(min(x1, x2), max(y1, y2))
        targetBottomRight = Point(max(x1, x2), min(y1, y2))
    }

    fun solvePart1() = findVelocitiesHittingTarget().map { (_, vy) -> vy * (vy - 1) / 2 }.maxOf { it }

    fun solvePart2() = findVelocitiesHittingTarget().count()

    private fun findVelocitiesHittingTarget() = sequence {
        for (vx in 1..targetBottomRight.x) {
            for (vy in -targetBottomRight.y.absoluteValue..targetBottomRight.y.absoluteValue) {
                if (hitsTarget(vx, vy)) {
                    yield(vx to vy)
                }
            }
        }
    }

    private fun hitsTarget(vx0: Int, vy0: Int): Boolean {
        var x = 0
        var y = 0
        var vx = vx0
        var vy = vy0

        fun hitTarget() = (x in targetTopLeft.x..targetBottomRight.x) && (y in targetBottomRight.y..targetTopLeft.y)
        fun overshoot() = x > targetBottomRight.x || y < targetBottomRight.y

        while (!hitTarget() && !overshoot()) {
            x += vx
            y += vy
            vx = vx.dec().coerceAtLeast(0)
            vy--
        }
        return hitTarget()
    }
}
