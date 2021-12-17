import Day16.Packet.Literal
import Day16.Packet.Operator

fun main() {
    println(Day16.solvePart1())
    println(Day16.solvePart2())
}

object Day16 {
    private val input = readInput("day16").single()

    fun solvePart1(): Int {
        fun Packet.sumVersions(): Int = when (this) {
            is Literal -> version
            is Operator -> version + subpackets.sumOf { it.sumVersions() }
        }
        return Bits.fromHex(input).readPacket().sumVersions()
    }

    fun solvePart2() = Bits.fromHex(input).readPacket().eval()

    private fun Bits.readPacket(): Packet {
        val version = read(3)
        return when (val type = read(3)) {
            4 -> Literal(version, readLiteral())
            else -> Operator(version, readSubpackets(), operatorFn(type))
        }
    }

    private fun operatorFn(type: Int): (List<Packet>) -> Long = when (type) {
        0 -> { pkts -> pkts.sumOf { it.eval() } }
        1 -> { pkts -> pkts.fold(1) { acc, pkt -> acc * pkt.eval() } }
        2 -> { pkts -> pkts.minOf { it.eval() } }
        3 -> { pkts -> pkts.maxOf { it.eval() } }
        5 -> { (a, b) -> if (a.eval() > b.eval()) 1 else 0 }
        6 -> { (a, b) -> if (a.eval() < b.eval()) 1 else 0 }
        7 -> { (a, b) -> if (a.eval() == b.eval()) 1 else 0 }
        else -> error("xoxoxo")
    }

    private fun Bits.readLiteral(): Long {
        var n = 0L
        var last: Boolean
        do {
            last = read(1) == 0
            n = (n shl 4) + read(4)
        } while (!last)
        return n
    }

    private fun Bits.readSubpackets(): List<Packet> {
        val result = mutableListOf<Packet>()
        val lenType = read(1)

        if (lenType == 0) {
            val numOfBits = read(15)
            val bits = readBits(numOfBits)
            while (bits.hasNext()) {
                result += bits.readPacket()
            }
        } else {
            val numOfPackets = read(11)
            repeat(numOfPackets) {
                result += readPacket()
            }
        }
        return result
    }

    private sealed interface Packet {
        val version: Int

        fun eval(): Long

        data class Literal(
            override val version: Int,
            val value: Long,
        ) : Packet {
            override fun eval() = value
        }

        data class Operator(
            override val version: Int,
            val subpackets: List<Packet>,
            private val eval: (List<Packet>) -> Long
        ) : Packet {
            override fun eval() = eval(subpackets)
        }
    }

    class Bits(private val bits: String) {
        private var i = 0

        fun hasNext() = i < bits.lastIndex

        fun read(nbits: Int): Int {
            val result = bits.substring(i until i + nbits).toInt(2)
            i += nbits
            return result
        }

        fun readBits(nbits: Int): Bits {
            val result = Bits(bits.substring(i until i + nbits))
            i += nbits
            return result
        }

        companion object {
            fun fromHex(hex: String) = hex.asSequence()
                .map { it.digitToInt(16) }
                .map { it.toString(2) }
                .map { it.padStart(4, '0') }
                .joinToString(separator = "")
                .let {
                    Bits(it)
                }
        }
    }
}
