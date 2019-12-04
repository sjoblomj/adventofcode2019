package org.sjoblomj.adventofcode.day3

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Day3Tests {

	@Test
	fun `Calculates the correct answers`() {
		assertEquals(Pair(1195, 91518), day3())
	}


	@Test
	fun `Empty instructions will return a list of the origin`() {
		assertEquals(listOf(Coord(0, 0)), instructionsToCoordinates(""))
	}

	@Test
	fun `Can turn instructions into coordinate list`() {
		assertEquals(listOf(
			Coord(0, 0), Coord(1, 0), Coord(2, 0), Coord(3, 0), Coord(4, 0), Coord(5, 0), Coord(6, 0), Coord(7, 0), Coord(8, 0),
			Coord(8, 1), Coord(8, 2), Coord(8, 3), Coord(8, 4), Coord(8, 5),
			Coord(7, 5), Coord(6, 5), Coord(5, 5), Coord(4, 5), Coord(3, 5),
			Coord(3, 4), Coord(3, 3), Coord(3, 2)
			), instructionsToCoordinates("R8,U5,L5,D3"))
	}

	@Test
	fun `Throws Exception on invalid instruction`() {
		assertFailsWith(IllegalArgumentException::class) {
			instructionsToCoordinates("R8,G5,L5,D3")
		}
		assertFailsWith(IllegalArgumentException::class) {
			visualise("R8,G5,L5,D3")
		}
	}

	@Test
	fun `Can visualise one instruction`() {
		val expected = """
			...........
			....+----+.
			....|....|.
			....|....|.
			....+....|.
			.........|.
			.o-------+.
			...........
		""".trimIndent()

		assertEquals(expected, visualise("R8,U5,L5,D3"))
	}

	@Test
	fun `Can visualise two instructions`() {
		val expected = """
			...........
			.+-----+...
			.|.....|...
			.|..+--X-+.
			.|..|..|.|.
			.|.+X--+.|.
			.|..+....|.
			.|.......|.
			.o-------+.
			...........
		""".trimIndent()

		assertEquals(expected, visualise("R8,U5,L5,D3", "U7,R6,D4,L4"))
	}

	@Test
	fun `Can find distance`() {
		assertEquals(6, findDistance(toCoords("R8,U5,L5,D3"), toCoords("U7,R6,D4,L4")))
		assertEquals(159, findDistance(toCoords("R75,D30,R83,U83,L12,D49,R71,U7,L72"), toCoords("U62,R66,U55,R34,D71,R55,D58,R83")))
		assertEquals(135, findDistance(toCoords("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"), toCoords("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")))
	}

	@Test
	fun `Can find steps taken`() {
		assertEquals(30, findSteps(toCoords("R8,U5,L5,D3"), toCoords("U7,R6,D4,L4")))
		assertEquals(610, findSteps(toCoords("R75,D30,R83,U83,L12,D49,R71,U7,L72"), toCoords("U62,R66,U55,R34,D71,R55,D58,R83")))
		assertEquals(410, findSteps(toCoords("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"), toCoords("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")))
	}

	/**
	 * Shorter function name to make tests more readable
	 */
	private fun toCoords(str: String) = instructionsToCoordinates(str)
}
