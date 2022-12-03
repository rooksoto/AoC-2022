import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.system.measureTimeMillis

/**
 * Reads lines from the given input txt file.
 */
fun readInputActual(
    dayName: String
): List<String> = readInput("$dayName/$dayName")

fun readInputTest(
    dayName: String
): List<String> = readInput("$dayName/${dayName}_test")

private fun readInput(
    fileName: String
): List<String> = File("src", "$fileName.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

fun profile(
    shouldLog: Boolean = false,
    block: () -> Unit
): Long = measureTimeMillis(block).apply {
    if (shouldLog) println("Execution time: ${this}ms.")
}

