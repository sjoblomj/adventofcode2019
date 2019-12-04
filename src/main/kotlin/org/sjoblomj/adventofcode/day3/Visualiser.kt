package org.sjoblomj.adventofcode.day3

import org.sjoblomj.adventofcode.day3.Instruction.Direction.*


fun visualise(firstInstructions: String, secondInstructions: String = ""): String {
	val symbolMap = HashMap<Coord, Char>()
	createSymbolMap(symbolMap, firstInstructions)
	createSymbolMap(symbolMap, secondInstructions)

	val xMin = symbolMap.keys.map { it.x }.min() ?: 0
	val xMax = symbolMap.keys.map { it.x }.max() ?: 0
	val yMin = symbolMap.keys.map { it.y }.min() ?: 0
	val yMax = symbolMap.keys.map { it.y }.max() ?: 0

	val stringBuilder = StringBuilder()
	for (y in (yMax + 1 downTo yMin - 1)) {
		for (x in (xMin - 1 .. xMax + 1)) {
			val coord = Coord(x, y)
			if (symbolMap.containsKey(coord)) {
				stringBuilder.append(symbolMap[coord])
			} else {
				stringBuilder.append('.')
			}
		}
		stringBuilder.append("\n")
	}

	return stringBuilder.toString().trim()
}

private fun createSymbolMap(symbols: HashMap<Coord, Char>, instructions: String) {
	var x = 0
	var y = 0

	for (instruction in createInstructionList(instructions)) {
		when (instruction.direction) {
			R -> {
				(0 until instruction.steps - 1).forEach { _ -> insert(symbols, Coord(++x, y), '-') }
				insert(symbols, Coord(++x, y), '+')
			}
			L -> {
				(0 until instruction.steps - 1).forEach { _ -> insert(symbols, Coord(--x, y), '-') }
				insert(symbols, Coord(--x, y), '+')
			}
			U -> {
				(0 until instruction.steps - 1).forEach { _ -> insert(symbols, Coord(x, ++y), '|') }
				insert(symbols, Coord(x, ++y), '+')
			}
			D -> {
				(0 until instruction.steps - 1).forEach { _ -> insert(symbols, Coord(x, --y), '|') }
				insert(symbols, Coord(x, --y), '+')
			}
		}
	}
	symbols[Coord(0, 0)] = 'o'
}

private fun insert(symbols: HashMap<Coord, Char>, coord: Coord, symbol: Char) {
	symbols[coord] = if (symbols.containsKey(coord)) {
		'X'
	} else {
		symbol
	}
}
