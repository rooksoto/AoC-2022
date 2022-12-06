package Day03

import profile
import readInputActual
import readInputTest

private const val DAY = "Day03"
private const val UPPERCASE_BASELINE = 38
private const val LOWERCASE_BASELINE = 96

fun main() {
    fun part1(
        input: List<String>
    ): Int = input.map(::toCharArrayPair)
        .map(::toIntersectedChar)
        .map(::toIntValue)
        .sum()

    fun part2(
        input: List<String>
    ): Int = input.chunked(3)
        .map(::toBadgeChar)
        .map(::toIntValue)
        .sum()


    val testInput = readInputTest(DAY)
    val input = readInputActual(DAY)

    check(part1(testInput) == 157)
    profile(shouldLog = true) {
        println("Part 1: ${part1(input)}")
    } // Answer: 8109 @ 24ms

    check(part2(testInput) == 70)
    profile(shouldLog = true) {
        println("Part 2: ${part2(input)}")
    } // Answer: 2738 @ 5ms
}

private val String.halfWayPoint: Int
    get() = this.length / 2

private fun <T> List<T>.second(): T = this[1]

private fun toCharArrayPair(
    input: String
): Pair<CharArray, CharArray> =
    with(input) {
        Pair(
            substring(0, halfWayPoint).toCharArray(),
            substring(halfWayPoint, length).toCharArray()
        )
    }

private fun toBadgeChar(
    stringList: List<String>
): Char = with(stringList) {
    (first().toSet().intersect(second().toSet())).intersect(last().toSet())
}.first()

private fun toIntersectedChar(
    stringPair: Pair<CharArray, CharArray>
): Char = with(stringPair) {
    first.toSet().intersect(second.toSet()).first()
}

private fun toIntValue(
    char: Char
): Int = with(char) {
    if (isUpperCase()) code - UPPERCASE_BASELINE
    else code - LOWERCASE_BASELINE
}
