package org.sjoblomj.adventofcode.day5

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Day5Tests {

	@Test
	fun `Calculates the correct answers`() {
		assertEquals(Pair(7988899, 13758663), day5())
	}


	@Test
	fun `Can parse opcode parameters -- addition`() {
		val instruction = Instruction(101)

		assertEquals(Instruction.InstructionType.ADD, instruction.instructionType)
		assertEquals(Instruction.InstructionMode.IMMEDIATE, instruction.modeParams[1])
		assertEquals(Instruction.InstructionMode.POSITION, instruction.modeParams[2])
		assertEquals(Instruction.InstructionMode.POSITION, instruction.modeParams[3])
	}

	@Test
	fun `Can parse opcode parameters -- multiplication`() {
		val instruction = Instruction(1002)

		assertEquals(Instruction.InstructionType.MUL, instruction.instructionType)
		assertEquals(Instruction.InstructionMode.POSITION, instruction.modeParams[1])
		assertEquals(Instruction.InstructionMode.IMMEDIATE, instruction.modeParams[2])
		assertEquals(Instruction.InstructionMode.POSITION, instruction.modeParams[3])
	}

	@Test
	fun `Can parse opcode parameters -- input`() {
		val instruction = Instruction(3)

		assertEquals(Instruction.InstructionType.INPUT, instruction.instructionType)
	}

	@Test
	fun `Can parse opcode parameters -- output`() {
		val instruction = Instruction(4)

		assertEquals(Instruction.InstructionType.OUTPUT, instruction.instructionType)
	}

	@Test
	fun `Can parse opcode parameters -- stop`() {
		val instruction = Instruction(99)

		assertEquals(Instruction.InstructionType.STOP, instruction.instructionType)
	}

	@Test
	fun `Unrecognised instructionType and instructionMode`() {
		assertFailsWith(IllegalArgumentException::class) {
			Instruction(1071)
		}
		assertFailsWith(IllegalArgumentException::class) {
			Instruction(1402)
		}
	}

	@Test
	fun `Simple program without output`() {
		val input = "1101,100,-1,4,0"
		assertEquals(emptyList(), calculateOpcodes(input, 1))
	}


	@Test
	fun `Program with equals`() {
		val input1 = "3,9,8,9,10,9,4,9,99,-1,8"
		assertEquals(listOf(0), calculateOpcodes(input1, 7))
		assertEquals(listOf(1), calculateOpcodes(input1, 8))
		assertEquals(listOf(0), calculateOpcodes(input1, 9))

		val input2 = "3,3,1108,-1,8,3,4,3,99"
		assertEquals(listOf(0), calculateOpcodes(input2, 7))
		assertEquals(listOf(1), calculateOpcodes(input2, 8))
		assertEquals(listOf(0), calculateOpcodes(input2, 9))
	}

	@Test
	fun `Program with less than`() {
		val input1 = "3,9,7,9,10,9,4,9,99,-1,8"
		assertEquals(listOf(1), calculateOpcodes(input1, 7))
		assertEquals(listOf(0), calculateOpcodes(input1, 8))
		assertEquals(listOf(0), calculateOpcodes(input1, 9))

		val input2 = "3,3,1107,-1,8,3,4,3,99"
		assertEquals(listOf(1), calculateOpcodes(input2, 7))
		assertEquals(listOf(0), calculateOpcodes(input2, 8))
		assertEquals(listOf(0), calculateOpcodes(input2, 9))
	}

	@Test
	fun `Program with Jump`() {
		val input1 = "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9"
		assertEquals(listOf(0), calculateOpcodes(input1, 0))
		assertEquals(listOf(1), calculateOpcodes(input1, 1))
		assertEquals(listOf(1), calculateOpcodes(input1, 7))

		val input2 = "3,3,1105,-1,9,1101,0,0,12,4,12,99,1"
		assertEquals(listOf(0), calculateOpcodes(input2, 0))
		assertEquals(listOf(1), calculateOpcodes(input2, 1))
		assertEquals(listOf(1), calculateOpcodes(input2, 7))
	}

	@Test
	fun `Larger program`() {
		val input = "" +
			"3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
			"1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
			"999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"
		assertEquals(listOf(999), calculateOpcodes(input, 0))
		assertEquals(listOf(999), calculateOpcodes(input, 6))
		assertEquals(listOf(999), calculateOpcodes(input, 7))
		assertEquals(listOf(1000), calculateOpcodes(input, 8))
		assertEquals(listOf(1001), calculateOpcodes(input, 9))
		assertEquals(listOf(1001), calculateOpcodes(input, 10))
	}
}
