package org.sjoblomj.adventofcode.day6

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Day6Tests {

	@Test
	fun `Calculates the correct answers`() {
		assertEquals(Pair(223251, 430), day6())
	}


	@Test
	fun `Can calculate orbits`() {
		val input = "" +
			"COM)B\n" +
			"B)C\n" +
			"C)D\n" +
			"D)E\n" +
			"E)F\n" +
			"B)G\n" +
			"G)H\n" +
			"D)I\n" +
			"E)J\n" +
			"J)K\n" +
			"K)L"

		assertEquals(42, calculateNumberOfOrbits(getOrbitList(input.split("\n"))))
	}


	@Test
	fun `Can find minimum number of orbital transfers between objects`() {
		val input = "" +
			"COM)B\n" +
			"B)C\n" +
			"C)D\n" +
			"D)E\n" +
			"E)F\n" +
			"B)G\n" +
			"G)H\n" +
			"D)I\n" +
			"E)J\n" +
			"J)K\n" +
			"K)L\n" +
			"K)YOU\n" +
			"I)SAN"
		val celestialBodies = getOrbitList(input.split("\n"))

		val result = calculateMinimumPathBetweenObjects(celestialBodies, "YOU", "SAN")

		assertEquals(listOf("K", "J", "E", "D", "I"), result.map { it.name })
		assertEquals(4, calculateMinimumOrbitalTransfersBetweenObjects(result))
	}

	@Test
	fun `Minimum number of orbital transfers for illegal objects throws exceptions`() {
		val input = "" +
			"COM)B\n" +
			"B)C\n" +
			"C)D\n" +
			"D)E\n" +
			"E)F\n" +
			"B)G\n" +
			"G)H\n" +
			"D)I\n" +
			"E)J\n" +
			"J)K\n" +
			"K)L\n" +
			"K)YOU\n" +
			"I)SAN"
		val celestialBodies = getOrbitList(input.split("\n"))

		assertFailsWith(NoSuchElementException::class) {
			calculateMinimumPathBetweenObjects(celestialBodies, "YOU", "Apa")
		}
	}
}
