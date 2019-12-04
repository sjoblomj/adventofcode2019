package org.sjoblomj.adventofcode.day4

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day4Tests {


	@Test
	fun `Calculates the correct answers`() {
		assertEquals(Pair(1099, 710), day4())
	}


	@Test
	fun `Test if passwords have adjacent equal digits`() {
		assertFalse(hasAdjacentEqualDigits(123789))
		assertFalse(hasAdjacentEqualDigits(121719))
		assertFalse(hasAdjacentEqualDigits(987656))

		assertTrue(hasAdjacentEqualDigits(111111))
		assertTrue(hasAdjacentEqualDigits(223450))
		assertTrue(hasAdjacentEqualDigits(987776))
		assertTrue(hasAdjacentEqualDigits(987966))
	}

	@Test
	fun `Test if passwords are increasing or Equals`() {
		assertFalse(digitsAreIncreasingOrEquals(123787))
		assertFalse(digitsAreIncreasingOrEquals(121719))
		assertFalse(digitsAreIncreasingOrEquals(987656))

		assertTrue(digitsAreIncreasingOrEquals(111111))
		assertTrue(digitsAreIncreasingOrEquals(223455))
		assertTrue(digitsAreIncreasingOrEquals(123789))
		assertTrue(digitsAreIncreasingOrEquals(111123))
		assertTrue(digitsAreIncreasingOrEquals(122345))
		assertTrue(digitsAreIncreasingOrEquals(135679))
	}

	@Test
	fun `Can count passwords fulfilling first criteria`() {
		assertEquals(1, countPasswordsFulfillingFirstCriteria(100, 111))
		assertEquals(10, countPasswordsFulfillingFirstCriteria(100, 123))
	}


	@Test
	fun `Test if passwords have adjacent equal digits that are not part of larger group`() {
		assertFalse(hasAdjacentEqualDigitsNotPartOfLargerGroup(123789))
		assertFalse(hasAdjacentEqualDigitsNotPartOfLargerGroup(121719))
		assertFalse(hasAdjacentEqualDigitsNotPartOfLargerGroup(987656))
		assertFalse(hasAdjacentEqualDigitsNotPartOfLargerGroup(123444))
		assertFalse(hasAdjacentEqualDigitsNotPartOfLargerGroup(111111))
		assertFalse(hasAdjacentEqualDigitsNotPartOfLargerGroup(987776))

		assertTrue(hasAdjacentEqualDigitsNotPartOfLargerGroup(223450))
		assertTrue(hasAdjacentEqualDigitsNotPartOfLargerGroup(987966))
		assertTrue(hasAdjacentEqualDigitsNotPartOfLargerGroup(112233))
		assertTrue(hasAdjacentEqualDigitsNotPartOfLargerGroup(111122))
	}

	@Test
	fun `Can count passwords fulfilling second criteria`() {
		assertEquals(0, countPasswordsFulfillingSecondCriteria(1000, 1110))
		assertEquals(7, countPasswordsFulfillingSecondCriteria(1200, 1230))
	}
}
