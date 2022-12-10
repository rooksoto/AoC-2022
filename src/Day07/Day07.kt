package Day07

import profile
import readInputActual
import readInputTest

private const val DAY = "Day07"
private const val ROOT = "ROOT"
private const val PD = "/"
private const val TOTAL_SPACE: Int = 70000000
private const val NEEDED_SPACE: Int = 30000000

var fileSystem: MutableMap<String, Directory> = mutableMapOf()
var workingPath: String = ""

fun main() {
    fun part1(input: List<String>): Int {

        reset()
        input.populateFileSystem(fileSystem)

        return fileSystem.toTotalSizes()
            .filter { it <= 100000 }
            .sum()
    }


    fun part2(input: List<String>): Int {

        reset()
        input.populateFileSystem(fileSystem)

        val unUsedSpace = TOTAL_SPACE - (fileSystem[ROOT]?.totalSize() ?: 0)

        return fileSystem.toTotalSizes()
            .sorted()
            .find { it + unUsedSpace > NEEDED_SPACE } ?: 0
    }


    val testInput = readInputTest(DAY)
    val input = readInputActual(DAY)

    check(part1(testInput) == 95437)
    profile(shouldLog = true) {
        println("Part 1: ${part1(input)}")
    } // Answer: 1454188 @ 38ms

    check(part2(testInput) == 24933642)
    profile(shouldLog = true) {
        println("Part 2: ${part2(input)}")
    } // Answer: 4183246 @ 12ms
}

private fun reset() {
    workingPath = ""
    fileSystem = mutableMapOf()
}

private fun List<String>.populateFileSystem(
    fileSystem: MutableMap<String, Directory>
) = splitWhen { s ->
    s.startsWith("$")
}.map { payload ->

    val command = payload.parseCommand()

    when (command.first()) {
        "cd" -> {
            workingPath = when (val key: String = command.last()) {
                ".." -> workingPath.dropPathSegment()
                PD -> ROOT
                else -> workingPath.addPathSegment(key)
            }
        }

        "ls" -> {
            payload
                .drop(1)
                .groupBy {
                    it.startsWith("dir")
                }.let { map ->
                    val fileSize = map[false]?.sumOf {
                        it.split(" ").first().toInt()
                    } ?: 0

                    val subDirectoryNames = map[true]?.map {
                        val name = it.split(" ").last()
                        workingPath.addPathSegment(name)
                    } ?: emptyList()

                    fileSystem[workingPath] = Directory(
                        workingPath,
                        fileSize,
                        subDirectoryNames
                    )
                }
        }
    }
}

private fun <T> List<T>.splitWhen(
    predicate: (T) -> Boolean
): List<List<T>> {
    val result = mutableListOf<List<T>>()
    indices.forEach { index ->
        if (predicate(this[index])) {
            val nextIndex: Int = subList(index + 1, size)
                .indexOfFirst { predicate(it) }.let { nIndex ->
                    if (nIndex == -1) size
                    else nIndex + (index + 1)
                }
            result.add(subList(index, nextIndex))
        }
    }
    return result
}

private fun String.addPathSegment(segment: String): String =
    split(PD).plus(segment).joinToString(PD)

private fun String.dropPathSegment(): String =
    split(PD).dropLast(1).joinToString(PD)

private fun List<String>.parseCommand() =
    first().split(" ").drop(1)

private fun Directory.totalSize(): Int =
    size + childPaths.sumOf { c -> fileSystem[c]?.totalSize() ?: 0 }

private fun Map<String, Directory>.toTotalSizes(): List<Int> =
    map { it.value }.map { it.totalSize() }

data class Directory(
    val path: String,
    var size: Int,
    val childPaths: List<String>
)
