package org.sjoblomj.adventofcode.day3

import org.sjoblomj.adventofcode.readFile
import kotlin.math.abs

private const val inputFile = "src/main/resources/inputs/day3.txt"

fun day3(): Pair<Int, Int> {
	val content = readFile(inputFile)
	val coordinates0 = instructionsToCoordinates(content[0])
	val coordinates1 = instructionsToCoordinates(content[1])

	val distance = findDistance(coordinates0, coordinates1)
	val steps = findSteps(coordinates0, coordinates1)

	println("Shortest distance: $distance")
	println("Shortest amount of steps: $steps")
	return distance to steps
}

internal fun findSteps(coordinates0: List<Coord>, coordinates1: List<Coord>): Int {

	return coordinates0.intersect(coordinates1)
		.filter { it != Coord(0, 0) }
		.map { coord -> coord.stepsTaken + coordinates1.first { it == coord }.stepsTaken }
		.min()
		?: throw RuntimeException("Failed to find shortest distance")
}

internal fun findDistance(coordinates0: List<Coord>, coordinates1: List<Coord>): Int {

	return coordinates0.intersect(coordinates1)
		.filter { it != Coord(0, 0) }
		.map { coord -> distance(coord) }
		.min()
		?: throw RuntimeException("Failed to find shortest distance")
}

/**
 * Manhattan distance, from (0, 0)
 */
private fun distance(coord: Coord) = abs(coord.x) + abs(coord.y)

internal fun instructionsToCoordinates(instructions: String): List<Coord> {
	var x = 0
	var y = 0
	var stepsTaken = 0
	val coords = mutableListOf<Coord>()

	for (instruction in createInstructionList(instructions)) {
		coords.addAll(
			when (instruction.direction) {
				'R' -> (0 until instruction.steps).map { Coord(x++, y, stepsTaken++) }
				'L' -> (0 until instruction.steps).map { Coord(x--, y, stepsTaken++) }
				'U' -> (0 until instruction.steps).map { Coord(x, y++, stepsTaken++) }
				'D' -> (0 until instruction.steps).map { Coord(x, y--, stepsTaken++) }
				else -> throw IllegalArgumentException("Could not parse '${instruction.direction}${instruction.steps}'")
			})
	}
	coords.add(Coord(x, y, stepsTaken))
	return coords
}

internal fun createInstructionList(instructions: String): List<Instruction> {
	return instructions.split(",")
		.filter { it != "" }
		.map { Instruction(it[0], it.substring(1).toInt()) }
}

internal data class Instruction(val direction: Char, val steps: Int)

internal data class Coord(val x: Int, val y: Int, val stepsTaken: Int = 0) {
	/**
	 * Ignore the stepsTaken parameter
	 */
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Coord

		if (x != other.x) return false
		if (y != other.y) return false

		return true
	}

	/**
	 * Ignore the stepsTaken parameter
	 */
	override fun hashCode(): Int {
		var result = x
		result = 31 * result + y
		return result
	}
}
