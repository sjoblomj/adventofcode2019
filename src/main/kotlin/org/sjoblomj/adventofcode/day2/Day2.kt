package org.sjoblomj.adventofcode.day2

import org.sjoblomj.adventofcode.readFile

private const val OPCODE_ADD = 1
private const val OPCODE_MUL = 2
private const val OPCODE_EXIT = 99
private const val WANTED_OUTPUT = 19690720

private const val inputFile = "src/main/resources/inputs/day2.txt"

fun day2(): Pair<Int, Int> {
	val content = readFile(inputFile)[0].toIntList()
	val program = initializeProgram(content, 12, 2).toMutableList()
	val programOutput = calculateProgramOutput(program)
	println("The program output is $programOutput")

	val (noun, verb) = findInputPair(content, WANTED_OUTPUT)
	val nounAndVerbCalculation = nounAndVerbCalculation(noun, verb)
	println("The noun and verb producing $WANTED_OUTPUT is $nounAndVerbCalculation")

	return Pair(programOutput, nounAndVerbCalculation)
}

internal fun nounAndVerbCalculation(noun: Int, verb: Int) = 100 * noun + verb

internal fun initializeProgram(content: MutableList<Int>, noun: Int, verb: Int) =
	listOf(content[0], noun, verb)
		.plus(content.subList(3, content.size))

internal fun calculateProgramOutput(input: MutableList<Int>) = calculateOpcodes(input)[0]

internal fun calculateOpcodes(program: MutableList<Int>): List<Int> {
	loop@ for (ip in (0 until program.size) step 4) {
		when {
			program[ip] == OPCODE_ADD  -> program[program[ip + 3]] = program[program[ip + 1]] + program[program[ip + 2]]
			program[ip] == OPCODE_MUL  -> program[program[ip + 3]] = program[program[ip + 1]] * program[program[ip + 2]]
			program[ip] == OPCODE_EXIT -> break@loop
			else                       -> throw IllegalArgumentException("Unexpected data at position $ip: ${program[ip]}")
		}
	}
	return program
}


internal fun findInputPair(input: MutableList<Int>, wantedOutput: Int, maxValue: Int = 100): Pair<Int, Int> {
	for (noun in (0 until maxValue)) {
		for (verb in (0 until maxValue)) {

			val program = initializeProgram(input, noun, verb).toMutableList()
			if (calculateProgramOutput(program) == wantedOutput) {
				return noun to verb
			}
		}
	}
	throw RuntimeException("Failed to find wanted noun and verb")
}


internal fun String.toIntList() = this
	.split(",")
	.map { it.toInt() }
	.toMutableList()
