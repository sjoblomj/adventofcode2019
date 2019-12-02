package org.sjoblomj.adventofcode.day1

import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Tests {

	@Test
	fun `Calculates the correct answers`() {
		assertEquals(Pair(3226822, 4837367), day1())
	}


	@Test
	fun `Can calculate fuel for single mass`() {
		assertEquals(2, calculateFuelGivenMass(12))
		assertEquals(2, calculateFuelGivenMass(14))
		assertEquals(654, calculateFuelGivenMass(1969))
		assertEquals(33583, calculateFuelGivenMass(100756))
	}

	@Test
	fun `Can calculate fuel for many masses`() {
		assertEquals(2 + 2 + 654 + 33583, calculateFuelGivenMasses(listOf(12, 14, 1969, 100756)))
	}

	@Test
	fun `Can calculate total fuel for single mass`() {
		assertEquals(0, calculateTotalFuelGivenMass(8))
		assertEquals(2, calculateTotalFuelGivenMass(12))
		assertEquals(2, calculateTotalFuelGivenMass(14))
		assertEquals(966, calculateTotalFuelGivenMass(1969))
		assertEquals(50346, calculateTotalFuelGivenMass(100756))
	}

	@Test
	fun `Can calculate total fuel for many masses`() {
		assertEquals(2 + 2 + 966 + 50346, calculateTotalFuelGivenMasses(listOf(12, 14, 1969, 100756)))
	}
}
