package Day05

import profile
import readInputActual
import readInputTest
import java.util.*

private typealias Board = List<Stack<Char>>
private typealias Instruction = Triple<Int, Int, Int>

private const val DAY = "Day05"

fun main() {
    fun part1(input: List<String>): String {
        input.toRawGameComponents()
            .toParsedGameComponents()
            .let { (board, instructions) ->
                instructions.forEach {
                    it.applyTo(board)
                }
                return board.topLetters()
            }
    }

    fun part2(input: List<String>): String =
        input.toRawGameComponents()
            .toParsedGameComponents()
            .let { (board, instructions) ->
                instructions.forEach {
                    it.applyMultiTo(board)
                }
                return board.topLetters()
            }


    val testInput = readInputTest(DAY)
    val input = readInputActual(DAY)

    check(part1(testInput) == "CMZ")
    profile(shouldLog = true) {
        println("Part 1: ${part1(input)}")
    } // Answer: SPFMVDTZT @ 18ms

    check(part2(testInput) == "MCD")
    profile(shouldLog = true) {
        println("Part 2: ${part2(input)}")
    } // Answer: ZFSJBPRFP @ 4ms
}

private fun List<Any?>.breakPoint(): Int =
    indexOf("")

private fun List<String>.toRawGameComponents(): Triple<List<String>, String, List<String>> {
    val boardStringList = subList(0, breakPoint() - 1)
    val columnsString = subList(breakPoint() - 1, breakPoint()).first()
    val instructionsStringList = subList(breakPoint() + 1, size)
    return Triple(boardStringList, columnsString, instructionsStringList)
}

private fun Triple<List<String>, String, List<String>>.toParsedGameComponents(
): Pair<Board, List<Instruction>> {
    val columns = second.last { it.isDigit() }.toString().toInt()
    val board: List<Stack<Char>> = MutableList(columns) { Stack() }
    first.reversed()
        .map { row ->
            row.chunked(4)
                .map { it.dropLast(1) }
                .map { it.filterNot { c -> c == ']' || c == '[' } }
                .map { it.first() }
                .forEachIndexed { index, s ->
                    if (s.isLetter()) board[index].push(s)
                }
        }
    val instructions = third.map {
        it.split(" ")
            .filter { s -> s.first().isDigit() }
            .map(String::toInt)
            .let { (a, b, c) -> Instruction(a, b, c) }
    }

    return Pair(board, instructions)
}

private fun Instruction.applyTo(board: Board) {
    val (a, b, c) = this
    repeat(a) { board[c - 1].push(board[b - 1].pop()) }
}

private fun Instruction.applyMultiTo(board: Board) {
    val (a, b, c) = this
    val temp = Stack<Char>()
    repeat(a) { temp.push(board[b - 1].pop()) }
    repeat(a) { board[c - 1].push(temp.pop()) }
}

private fun Board.topLetters(): String =
    map { it.peek() }
        .joinToString("")
