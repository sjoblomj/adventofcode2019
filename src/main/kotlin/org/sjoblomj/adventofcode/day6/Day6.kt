package org.sjoblomj.adventofcode.day6

import org.sjoblomj.adventofcode.readFile


private const val inputFile = "src/main/resources/inputs/day6.txt"

fun day6(): Pair<Int, Int> {
	val content = readFile(inputFile)
	val orbitList = getOrbitList(content)

	val numberOfOrbits = calculateNumberOfOrbits(orbitList)
	val minimumPathBetweenObjects = calculateMinimumPathBetweenObjects(orbitList, "YOU", "SAN")
	val minimumOrbitalTransfersBetweenObjects = calculateMinimumOrbitalTransfersBetweenObjects(minimumPathBetweenObjects)

	println("Total number of direct and indirect orbits: $numberOfOrbits")
	println("Minimum number of orbital transfers: $minimumOrbitalTransfersBetweenObjects")

	return Pair(numberOfOrbits, minimumOrbitalTransfersBetweenObjects)
}


internal fun calculateNumberOfOrbits(orbitList: List<CelestialBody>): Int {

	val distanceMap = HashMap<CelestialBody, Int>()
	orbitList.forEach { calculateDistance(it, distanceMap) }
	return distanceMap.values.sum()
}

internal fun calculateDistance(celestialBody: CelestialBody?, distanceMap: HashMap<CelestialBody, Int>): Int {
	if (celestialBody == null)
		return 0
	if (distanceMap.containsKey(celestialBody))
		return distanceMap[celestialBody]!!
	if (celestialBody.bodyRevolvingAround == null) {
		distanceMap[celestialBody] = 0
		return 0
	}
	distanceMap[celestialBody] = 1 + calculateDistance(celestialBody.bodyRevolvingAround, distanceMap)
	return distanceMap[celestialBody]!!
}

internal fun getOrbitList(inputs: List<String>): List<CelestialBody> {
	val celestialBodies = hashMapOf<String, CelestialBody>()

	val bodyPairs = inputs.map { it.split(")") }

	bodyPairs
		.forEach { (parentName, childName) -> run {
				celestialBodies.putIfAbsent(parentName, CelestialBody(parentName))
				celestialBodies.putIfAbsent(childName,  CelestialBody(childName))
		} }

	bodyPairs
		.forEach { (parentName, childName) -> run {
			celestialBodies[childName]?.bodyRevolvingAround = celestialBodies[parentName]
			celestialBodies[parentName]?.satellites?.add(celestialBodies[childName]!!)
		} }

	return celestialBodies.values.toList()
}


internal fun calculateMinimumPathBetweenObjects(celestialBodies: List<CelestialBody>, o1: String, o2: String): List<CelestialBody> {
	val b1 = celestialBodies.first { it.name == o1 }
	val b2 = celestialBodies.first { it.name == o2 }

	val paths = calculateMinimumPathBetweenObjects(emptyList(), b1, b2)
		?: throw IllegalArgumentException("Failed to find minimum paths between $o1 and $o2")

	return paths.minus(b1).minus(b2)
}

internal fun calculateMinimumPathBetweenObjects(paths: List<CelestialBody>, currNode: CelestialBody, endNode: CelestialBody): List<CelestialBody>? {
	val currPath = paths.plus(currNode)

	return if (currNode == endNode)
		currPath
	else {
		val unvisitedNeighbours = currNode.satellites
			.plus(currNode.bodyRevolvingAround)
			.filterNotNull()
			.subtract(paths)

		if (unvisitedNeighbours.isEmpty())
			null
		else
			unvisitedNeighbours
				.mapNotNull { calculateMinimumPathBetweenObjects(currPath, it, endNode) }
				.minBy { it.size }
	}
}

internal fun calculateMinimumOrbitalTransfersBetweenObjects(path: List<CelestialBody>) = path.size - 1

class CelestialBody(val name: String, var bodyRevolvingAround: CelestialBody? = null,
										val satellites: MutableList<CelestialBody> = mutableListOf())
