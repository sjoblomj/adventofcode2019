package org.sjoblomj.adventofcode.day2

import org.sjoblomj.adventofcode.readFile

private const val OPCODE_ADD = 1
private const val OPCODE_MUL = 2
private const val OPCODE_EXIT = 99
private const val WANTED_OUTPUT = 19690720

private const val inputFile = "src/main/resources/inputs/day2.txt"

fun day2(): Pair<Int, Int> {
	val content = readFile(inputFile)[0].toIntList()
	val program = initializeProgram(content, 12, 2)
	val programOutput = calculateProgramOutput(program)
	println("The program output is $programOutput")

	val (noun, verb) = findInputPair(content, WANTED_OUTPUT)
	val nounAndVerbCalculation = nounAndVerbCalculation(noun, verb)
	println("The noun and verb producing $WANTED_OUTPUT is $nounAndVerbCalculation")

	return Pair(programOutput, nounAndVerbCalculation)
}

internal fun nounAndVerbCalculation(noun: Int, verb: Int) = 100 * noun + verb

internal fun initializeProgram(content: List<Int>, noun: Int, verb: Int) =
	listOf(content[0], noun, verb)
		.plus(content.subList(3, content.size))

internal fun calculateProgramOutput(program: List<Int>) = calculateOpcodes(program.toMutableList())[0]

internal fun calculateOpcodes(prg: MutableList<Int>): List<Int> {
	loop@ for (ip in (0 until prg.size) step 4) {
		when {
			prg[ip] == OPCODE_ADD  -> prg[prg[ip + 3]] = prg[prg[ip + 1]] + prg[prg[ip + 2]]
			prg[ip] == OPCODE_MUL  -> prg[prg[ip + 3]] = prg[prg[ip + 1]] * prg[prg[ip + 2]]
			prg[ip] == OPCODE_EXIT -> break@loop
			else                   -> throw IllegalArgumentException("Unexpected data at position $ip: ${prg[ip]}")
		}
	}
	return prg
}


internal fun findInputPair(input: List<Int>, wantedOutput: Int, maxValue: Int = 100): Pair<Int, Int> {
	for (noun in (0 until maxValue)) {
		for (verb in (0 until maxValue)) {

			val program = initializeProgram(input, noun, verb)
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
