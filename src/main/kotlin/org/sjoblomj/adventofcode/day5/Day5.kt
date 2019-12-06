package org.sjoblomj.adventofcode.day5

import org.sjoblomj.adventofcode.day5.Instruction.InstructionMode.IMMEDIATE
import org.sjoblomj.adventofcode.day5.Instruction.InstructionMode.POSITION
import org.sjoblomj.adventofcode.day5.Instruction.InstructionType.*
import org.sjoblomj.adventofcode.readFile
import kotlin.streams.toList


private const val inputFile = "src/main/resources/inputs/day5.txt"

fun day5(): Pair<Int, Int> {
	val content = readFile(inputFile)[0]

	calculateOpcodes(content)

	return Pair(-1, -1)
}


internal fun calculateOpcodes(opcodes: String, inputValue: Int = 1) = calculateOpcodes(opcodes.toIntList().toMutableList(), inputValue)

internal fun calculateOpcodes(prg: MutableList<Int>, inputValue: Int): List<Int> {
	val outputs = mutableListOf<Int>()
	var ip = 0
	while (true) {
		val instruction = Instruction(prg[ip])
		val numOfParams = instruction.instructionType.numberOfParameters

		when (instruction.instructionType) {
			ADD       -> { applyTwoParamOperation(instruction, prg, ip) { a, b -> a + b }; ip += numOfParams }
			MUL       -> { applyTwoParamOperation(instruction, prg, ip) { a, b -> a * b }; ip += numOfParams }
			LESS_THAN -> { applyTwoParamOperation(instruction, prg, ip) { a, b -> if (a <  b) 1 else 0 }; ip += numOfParams }
			EQUALS    -> { applyTwoParamOperation(instruction, prg, ip) { a, b -> if (a == b) 1 else 0 }; ip += numOfParams }

			JUMP_IF_TRUE  -> { ip = jumpOperation(instruction, prg, ip) { it != 0 } ?: ip + numOfParams }
			JUMP_IF_FALSE -> { ip = jumpOperation(instruction, prg, ip) { it == 0 } ?: ip + numOfParams }

			INPUT  -> { prg[prg[ip + 1]] = inputValue; ip += numOfParams }
			OUTPUT -> { outputs.add(prg[prg[ip + 1]]); ip += numOfParams }
			STOP   -> { ip = prg.size }
		}
		if (ip >= prg.size)
			break
	}
	println(outputs)
	return prg
}

private fun jumpOperation(instruction: Instruction, prg: List<Int>, ip: Int, comparator: (Int) -> Boolean): Int? {
	val p1 = instruction.getValue(prg, ip, 1)
	val p2 = instruction.getValue(prg, ip, 2)

	return if (comparator.invoke(p1)) {
		p2
	} else {
		null
	}
}

private fun applyTwoParamOperation(instruction: Instruction, prg: MutableList<Int>, ip: Int, operation: (Int, Int) -> Int) {
	val p1 = instruction.getValue(prg, ip, 1)
	val p2 = instruction.getValue(prg, ip, 2)

	val newValue = operation(p1, p2)
	if (instruction.modeParams[3] == POSITION)
		prg[prg[ip + 3]] = newValue
	else
		prg[ip + 3] = newValue
}


internal class Instruction(opcode: Int) {
	val instructionType: InstructionType
	val modeParams: List<InstructionMode>

	init {
		val digits = opcode
			.toString()
			.padStart(5, '0')
			.chars()
			.map { it - '0'.toInt() }
			.toList()
			.reversed()

		this.instructionType = parseInstructionType(digits[1].toString() + digits[0].toString())
		this.modeParams = listOf(POSITION) // Element 0 is not actually used (would be the instructionType itself), but we need something in index 0
			.plus(listOf(parseInstructionMode(digits[2]), parseInstructionMode(digits[3]), parseInstructionMode(digits[4])))
	}

	internal fun getValue(prg: List<Int>, ip: Int, offset: Int): Int {
		return if (modeParams[offset] == POSITION)
			prg[prg[ip + offset]]
		else
			prg[ip + offset]
	}

	private fun parseInstructionType(instructionType: String): InstructionType {
		return when (instructionType) {
			"01" -> ADD
			"02" -> MUL
			"03" -> INPUT
			"04" -> OUTPUT
			"05" -> JUMP_IF_TRUE
			"06" -> JUMP_IF_FALSE
			"07" -> LESS_THAN
			"08" -> EQUALS
			"99" -> STOP
			else -> throw IllegalArgumentException("Could not recognise instruction type '$instructionType'")
		}
	}

	private fun parseInstructionMode(instructionMode: Int): InstructionMode {
		return when (instructionMode) {
			0 -> POSITION
			1 -> IMMEDIATE
			else -> throw IllegalArgumentException("Could not recognise instruction mode '$instructionMode'")
		}
	}

	enum class InstructionMode { POSITION, IMMEDIATE }
	enum class InstructionType(val numberOfParameters: Int) {
		ADD(4),
		MUL(4),
		INPUT(2),
		OUTPUT(2),
		JUMP_IF_TRUE(3),
		JUMP_IF_FALSE(3),
		LESS_THAN(4),
		EQUALS(4),
		STOP(1);
	}
}


internal fun String.toIntList() = this
	.split(",")
	.map { it.toInt() }
