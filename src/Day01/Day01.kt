package Day01

import profile
import readInputActual
import readInputTest

private const val DAY = "Day01"
private const val NEWLINE = "\n"

fun main() {
    fun part1(input: List<String>): Int =
        toCalorieValues(input)
            .max()


    fun part2(input: List<String>): Int =
        toCalorieValues(input)
            .sortedDescending()
            .take(3)
            .sum()

    val testInput = readInputTest(DAY)
    val input = readInputActual(DAY)

    check(part1(testInput) == 24000)
    profile(shouldLog = true) {
        println("Part 1: ${part1(input)}")
    } // Answer: 74711 @ 13ms

    check(part2(testInput) == 45000)
    profile(shouldLog = true) {
        println("Part 2: ${part2(input)}")
    } // Answer: 209481 @ 6ms
}

private fun toCalorieValues(
    input: List<String>
): List<Int> = input.joinToString(NEWLINE)
    .split(NEWLINE.repeat(2))
    .map { inputString ->
        inputString.split(NEWLINE)
    }.map { group ->
        group.sumOf { item ->
            item.toIntOrZero()
        }
    }

private fun String.toIntOrZero(): Int = toIntOrNull() ?: 0

