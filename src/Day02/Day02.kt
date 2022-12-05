package Day02

import profile
import readInputActual
import readInputTest

private const val DAY = "Day02"

fun main() {
    fun part1(
        input: List<String>
    ): Int = toCharPairs(input)
        .map(RPS::fromPairOfChars)
        .sumOf(::evaluateMatchScore)

    fun part2(
        input: List<String>
    ): Int = toCharPairs(input)
        .map { charPair ->
            val opponentMove = RPS.fromChar(charPair.first)
            Pair(opponentMove, opponentMove.desiredOutcome(charPair.second))
        }.sumOf(::evaluateMatchScore)


    val testInput = readInputTest(DAY)
    val input = readInputActual(DAY)

    check(part1(testInput) == 15)
    profile(shouldLog = true) {
        println("Part 1: ${part1(input)}")
    } // Answer: 14264 @ 25ms

    check(part2(testInput) == 12)
    profile(shouldLog = true) {
        println("Part 2: ${part2(input)}")
    } // Answer: 12382 @ 7ms
}

private fun toCharPairs(
    input: List<String>
): List<Pair<Char, Char>> = input.map { it.split(" ") }
    .map { Pair(it.first().first(), it.last().first()) }

private fun evaluateMatchScore(
    rpsPair: Pair<RPS, RPS>
): Int = RPS.evaluateWin(rpsPair) + RPS.evaluateExtraPoints(rpsPair.second)


sealed class RPS {

    abstract val value: Int
    abstract val beats: RPS
    abstract val losesTo: RPS

    infix fun beats(
        other: RPS
    ): Boolean = other == beats

    infix fun losesTo(
        other: RPS
    ): Boolean = other == losesTo

    fun desiredOutcome(
        code: Char
    ): RPS = when (code) {
        'X' -> this.beats
        'Y' -> this
        'Z' -> this.losesTo
        else -> throw IllegalArgumentException("Invalid input: $code")
    }

    companion object {

        fun fromChar(
            input: Char
        ): RPS = when (input) {
            'A', 'X' -> Rock
            'B', 'Y' -> Paper
            'C', 'Z' -> Scissors
            else -> throw IllegalArgumentException("Invalid input: $input")
        }

        fun fromPairOfChars(
            charPair: Pair<Char, Char>
        ): Pair<RPS, RPS> = Pair(fromChar(charPair.first), fromChar(charPair.second))

        fun evaluateWin(
            rpsPair: Pair<RPS, RPS>
        ) = when {
            rpsPair.second beats rpsPair.first -> 6
            rpsPair.second losesTo rpsPair.first -> 0
            else -> 3
        }

        fun evaluateExtraPoints(
            rps: RPS
        ) = when (rps) {
            is Rock -> 1
            is Paper -> 2
            is Scissors -> 3
        }
    }

    object Rock : RPS() {
        override val value: Int = 1
        override val beats: RPS = Scissors
        override val losesTo: RPS = Paper
    }

    object Paper : RPS() {
        override val value: Int = 2
        override val beats: RPS = Rock
        override val losesTo: RPS = Scissors
    }

    object Scissors : RPS() {
        override val value: Int = 3
        override val beats: RPS = Paper
        override val losesTo: RPS = Rock
    }
}
