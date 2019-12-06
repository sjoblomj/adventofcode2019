package org.sjoblomj.adventofcode.day5

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Day5Tests {

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
	fun `Simple program`() {
		val input = "1101,100,-1,4,0"
		assertEquals("1101,100,-1,4,99".listify(), calculateOpcodes(input))
	}


	@Test
	fun `Program with equals`() {
		val input = "3,9,8,9,10,9,4,9,99,-1,8"
		calculateOpcodes(input, 8) // TODO: Get output
	}

	private fun String.listify() = this.toIntList().toMutableList()
}
