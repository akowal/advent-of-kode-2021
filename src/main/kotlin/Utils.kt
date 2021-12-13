import java.io.File
import java.util.Scanner


fun readInput(name: String) = inputFile(name).useLines {
    it.toList()
}

fun inputScanner(name: String) = Scanner(inputFile(name))

private fun inputFile(name: String) = File("src/main/resources/inputs", "$name.txt")

fun String.toIntArray(delimiter: Char = ',') = split(delimiter).map { it.toInt() }.toIntArray()

data class Point(val x: Int, val y: Int)
