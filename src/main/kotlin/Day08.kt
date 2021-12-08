import Day08.Segment.*
import java.util.*

fun main() {
    println(Day08.solvePart1())
    println(Day08.solvePart2())
}

object Day08 {
    private val entries = readInput("day08")
        .map {
            val patterns = it.substringBefore("|").trim().split(' ')
            val output = it.substringAfter("|").trim().split(' ')
            Entry(patterns, output)
        }

    fun solvePart1() = entries.flatMap { it.output }.count { it.length in setOf(2, 3, 4, 7) }

    fun solvePart2() = entries.map { decipher(it) }.sum()

    private fun decipher(entry: Entry): Int {
        val wiring = decipherWiring(entry)
        return entry.output.map { it.map { c -> wiring[c]!! } }
            .map { it.toSet() }
            .map { digitBySegments[it]!! }
            .fold(0) { acc, digit -> acc * 10 + digit }
    }

    private fun decipherWiring(entry: Entry): Map<Char, Segment> {
        val patternByLen = entry.patterns.groupBy(String::length, String::toSet)
        val p1 = patternByLen[2]!!.single()
        val p7 = patternByLen[3]!!.single()
        val p4 = patternByLen[4]!!.single()
        val p8 = patternByLen[7]!!.single()
        val len5common = patternByLen[5]!!.reduce(Set<Char>::intersect)
        val len6common = patternByLen[6]!!.reduce(Set<Char>::intersect)

        val wiring = mutableMapOf<Char, Segment>()
        fun wiring(vararg segments: Segment) = wiring.filterValues { segments.contains(it) }.keys
        fun wire(segment: Segment, set: Set<Char>) {
            wiring[set.single()] = segment
        }
        wire(T, (p7 - p1))
        wire(BR, (len6common % p1))
        wire(TR, (p1 - wiring(BR)))
        wire(TL, (len6common % p4 - wiring(BR)))
        wire(M, (p4 - wiring(TL, TR, BR)))
        wire(B, (len5common - wiring(T, M)))
        wire(BL, (p8 - wiring(T, B, M, TL, TR, BR)))
        return wiring
    }

    /*
         T
        +--+
     TL |  | TR
        +--+<--M
     BL |  | BR
        +--+
         B
     */
    enum class Segment {
        T, B, TL, TR, BL, BR, M
    }

    operator fun <T> Set<T>.rem(other: Set<T>) = this.intersect(other)

    val digitBySegments = mapOf(
        // @formatter:off
        setOf(             TR,     BR) to 1,
        setOf(T,           TR,     BR) to 7,
        setOf(      M, TL, TR,     BR) to 4,
        setOf(T, B, M, TL, TR, BL, BR) to 8,

        setOf(T, B, M,     TR, BL    ) to 2,
        setOf(T, B, M,     TR,     BR) to 3,
        setOf(T, B, M, TL,         BR) to 5,

        setOf(T, B, M, TL,     BL, BR) to 6,
        setOf(T, B, M, TL, TR,     BR) to 9,
        setOf(T, B,    TL, TR, BL, BR) to 0,
        // @formatter:on
    )

    private data class Entry(
        val patterns: List<String>,
        val output: List<String>,
    )
}
