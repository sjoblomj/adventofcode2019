package org.sjoblomj.adventofcode.day4

import kotlin.streams.toList

private const val START = 245182
private const val STOP = 790572

fun day4(): Pair<Int, Int> {
	val numberOfPasswordsFulfillingFirstCriteria = countPasswordsFulfillingFirstCriteria(START, STOP)
	val numberOfPasswordsFulfillingSecondCriteria = countPasswordsFulfillingSecondCriteria(START, STOP)

	println("Number of passwords fulfilling first criteria $numberOfPasswordsFulfillingFirstCriteria")
	println("Number of passwords fulfilling second criteria $numberOfPasswordsFulfillingSecondCriteria")

	return numberOfPasswordsFulfillingFirstCriteria to numberOfPasswordsFulfillingSecondCriteria
}


internal fun countPasswordsFulfillingFirstCriteria(start: Int, stop: Int): Int {
	return (start .. stop)
		.filter { digitsAreIncreasingOrEquals(it) }
		.filter { hasAdjacentEqualDigits(it) }
		.count()
}

internal fun countPasswordsFulfillingSecondCriteria(start: Int, stop: Int): Int {
	return (start .. stop)
		.filter { digitsAreIncreasingOrEquals(it) }
		.filter { hasAdjacentEqualDigitsNotPartOfLargerGroup(it) }
		.count()
}

internal fun digitsAreIncreasingOrEquals(digits: Int): Boolean {
	val chars = intToCharList(digits)
	return (0 until chars.size - 1).none { chars[it] > chars[it + 1] }
}

internal fun hasAdjacentEqualDigits(digits: Int): Boolean {
	val chars = intToCharList(digits)
	return (0 until chars.size - 1).any { chars[it] == chars[it + 1] }
}

internal fun hasAdjacentEqualDigitsNotPartOfLargerGroup(digits: Int): Boolean {
	val chars = intToCharList(digits)
	return (0 until chars.size - 1).any { chars[it] == chars[it + 1] && chars.filter { char -> char == chars[it] }.count() == 2 }
}

private fun intToCharList(digits: Int) = digits.toString().chars().toList()
