import java.io.File
import java.util.*


fun readInput(name: String) = inputFile(name).useLines {
    it.filter(String::isNotBlank).toList()
}

fun inputScanner(name: String) = Scanner(inputFile(name))

private fun inputFile(name: String) = File("src/main/resources/inputs", "$name.txt")

fun String.toIntArray(delimiter: Char = ',') = split(delimiter).map { it.toInt() }.toIntArray()
