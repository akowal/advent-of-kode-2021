import java.io.File

fun readInput(name: String): List<String> {
    val file = File("src/main/resources/inputs", "$name.txt")
    return file.readLines()
}
