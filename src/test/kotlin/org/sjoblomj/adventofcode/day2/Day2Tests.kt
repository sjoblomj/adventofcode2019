package org.sjoblomj.adventofcode.day2

import org.junit.Test
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Day2Tests {

	@Test
	fun `Calculates the correct answers`() {
		assertEquals(Pair(5866663, 4259), day2())
	}


	@Test
	fun `Can initialize program`() {
		val input = "2,4,4,5,99,0".listify()
		assertEquals("2,12,2,5,99,0".listify(), initializeProgram(input, 12, 2))
	}

	@Test
	fun `Single Adding opcode`() {
		val input0 = "1,2,1,2".listify()
		assertEquals("1,2,3,2".listify(), calculateOpcodes(input0))

		val input1 = "1,0,0,0".listify()
		assertEquals("2,0,0,0".listify(), calculateOpcodes(input1))
	}

	@Test
	fun `Single Multiplying opcode`() {
		val input0 = "2,2,1,2".listify()
		assertEquals("2,2,2,2".listify(), calculateOpcodes(input0))

		val input1 = "2,3,0,3".listify()
		assertEquals("2,3,0,6".listify(), calculateOpcodes(input1))
	}

	@Test
	fun `Input of two opcodes`() {
		val input = "1,4,1,3,2,3,1,0".listify()
		assertEquals("24,4,1,6,2,3,1,0".listify(), calculateOpcodes(input))
	}

	@Test
	fun `Can handle Exit opcode`() {
		val input = "1,4,1,3,2,3,1,0,99".listify()
		assertEquals("24,4,1,6,2,3,1,0,99".listify(), calculateOpcodes(input))
	}

	@Test
	fun `Can handle additional data after Exit opcode`() {
		val input = "1,4,1,3,2,3,1,0,99,30,40,50".listify()
		assertEquals("24,4,1,6,2,3,1,0,99,30,40,50".listify(), calculateOpcodes(input))
	}

	@Test
	fun `Throws exception on unexpected data`() {
		assertFailsWith(IllegalArgumentException::class) {
			calculateOpcodes("1,4,1,3,71".listify())
		}
	}

	@Test
	fun `Example input`() {
		val input0 = "2,4,4,5,99,0".listify()
		assertEquals("2,4,4,5,99,9801".listify(), calculateOpcodes(input0))

		val input1 = "1,1,1,4,99,5,6,0,99".listify()
		assertEquals("30,1,1,4,2,5,6,0,99".listify(), calculateOpcodes(input1))

		val input2 = "1,9,10,3,2,3,11,0,99,30,40,50".listify()
		assertEquals("3500,9,10,70,2,3,11,0,99,30,40,50".listify(), calculateOpcodes(input2))
	}


	@Test
	fun `Calculate program output`() {
		val input = "1,9,10,3,2,3,11,0,99,30,40,50".listify()
		assertEquals(3500, calculateProgramOutput(input))
	}

	@Test
	fun `Can calculate noun and verb`() {
		assertEquals(1202, nounAndVerbCalculation(12, 2))
	}


	@Test
	fun `Finding no verb and noun will throw Exception`() {
		assertFailsWith(RuntimeException::class) {
			val input = "1,9,10,3,2,3,11,0,99,30,40,50".listify()
			findInputPair(input, 151, 10)
		}
	}

	@Test
	fun `Find input pair`() {
		val input = "1,9,10,3,2,3,11,0,99,30,40,50".listify()

		val (noun, verb) = findInputPair(input, 250, 10)

		assertEquals(3, noun)
		assertEquals(2, verb)
	}


	private fun String.listify() = this.toIntList().toMutableList()
}
