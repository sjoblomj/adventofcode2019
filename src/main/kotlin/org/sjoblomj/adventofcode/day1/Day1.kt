package org.sjoblomj.adventofcode.day1

import org.sjoblomj.adventofcode.readFile

private const val inputFile = "src/main/resources/inputs/day1.txt"

fun day1(): Pair<Int, Int> {
	val content = readFile(inputFile).map { it.toInt() }
	val fuelGivenMasses = calculateFuelGivenMasses(content)
	val totalFuelGivenMasses = calculateTotalFuelGivenMasses(content)

	println("The fuel for the masses is $fuelGivenMasses")
	println("The total fuel required is $totalFuelGivenMasses")

	return fuelGivenMasses to totalFuelGivenMasses
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
