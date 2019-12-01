package org.sjoblomj.adventofcode.day1

import org.sjoblomj.adventofcode.readFile
import kotlin.system.measureTimeMillis

private const val inputFile = "src/main/resources/inputs/day1.txt"

fun day1() {
	println("== DAY 1 ==")
	val timeTaken = measureTimeMillis { calculateAndPrintDay1() }
	println("Finished Day 1 in $timeTaken ms\n")
}

fun calculateAndPrintDay1() {
	val content = readFile(inputFile).map { it.toInt() }
	println("The fuel for the masses is ${calculateFuelGivenMasses(content)}")
	println("The total fuel required is ${calculateTotalFuelGivenMasses(content)}")
}

internal fun calculateFuelGivenMasses(masses: List<Int>) = masses.map { calculateFuelGivenMass(it) }.sum()

internal fun calculateFuelGivenMass(mass: Int) = (mass / 3) - 2

internal fun calculateTotalFuelGivenMasses(masses: List<Int>) = masses.map { calculateTotalFuelGivenMass(it) }.sum()

internal fun calculateTotalFuelGivenMass(mass: Int): Int {
	val fuel = calculateFuelGivenMass(mass)
	if (fuel <= 0)
		return 0
	return fuel + calculateTotalFuelGivenMass(fuel)
}
