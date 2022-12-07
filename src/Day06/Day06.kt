package Day06

import profile
import readInputActual
import readInputTest

private const val DAY = "Day06"

fun main() {
    fun part1(input: List<String>): Int =
        input.first()
            .solveWithWindowSize(4)


    fun part2(input: List<String>): Int =
        input.first()
            .solveWithWindowSize(14)


    val testInput = readInputTest(DAY)
    val input = readInputActual(DAY)

    check(part1(testInput) == 7)
    profile(shouldLog = true) {
        println("Part 1: ${part1(input)}")
    } // Answer: 1965 @ 20ms

    check(part2(testInput) == 19)
    profile(shouldLog = true) {
        println("Part 2: ${part2(input)}")
    } // Answer: 2773 @ 3ms
}

private fun String.solveWithWindowSize(windowSize: Int): Int =
    windowedSequence(windowSize) {
        it.toSet().size == windowSize
    }.indexOfFirst { it }
        .plus(windowSize)
