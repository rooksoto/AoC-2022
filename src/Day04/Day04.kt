package Day04

import profile
import readInputActual
import readInputTest

private const val DAY = "Day04"

fun main() {
    fun part1(input: List<String>): Int =
        toFormattedSectionLists(input)
            .count {
                it.first.containsAll(it.second) || it.second.containsAll(it.first)
            }

    fun part2(input: List<String>): Int =
        toFormattedSectionLists(input)
            .count {
                it.first.intersect(it.second.toSet()).isNotEmpty()
            }


    val testInput = readInputTest(DAY)
    val input = readInputActual(DAY)

    check(part1(testInput) == 2)
    profile(shouldLog = true) {
        println("Part 1: ${part1(input)}")
    } // Answer: 450 @ 39ms

    check(part2(testInput) == 4)
    profile(shouldLog = true) {
        println("Part 2: ${part2(input)}")
    } // Answer: 837 @ 46ms
}

private fun toFormattedSectionLists(
    input: List<String>
): List<Pair<List<Int>, List<Int>>> =
    input.map(::toSectionStringPairs)
        .map(::toIntRangePairs)
        .map(::toSectionListPairs)

private fun toSectionStringPairs(
    input: String
): Pair<String, String> =
    with(input.split(",")) {
        Pair(first(), last())
    }

private fun toIntRangePairs(
    stringPair: Pair<String, String>
): Pair<IntRange, IntRange> {
    val (firstRangeStart, firstRangeEnd) = stringPair.first.split("-")
    val (secondRangeStart, secondRangeEnd) = stringPair.second.split("-")
    return Pair(
        firstRangeStart.toInt()..firstRangeEnd.toInt(),
        secondRangeStart.toInt()..secondRangeEnd.toInt()
    )
}

private fun toSectionListPairs(
    intRangePair: Pair<IntRange, IntRange>
): Pair<List<Int>, List<Int>> =
    with(intRangePair) {
        Pair(first.toList(), second.toList())
    }
