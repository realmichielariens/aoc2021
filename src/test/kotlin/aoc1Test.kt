import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertEquals

internal class aoc1Test {

    @Test
    fun testAOC1_simpleStepCounting(){
        val input = readInput("src/test/resources/aoc1.txt"){ s -> s.toInt() }
        val downsteps = countStepdowns(input)
        assertEquals(downsteps, 1655)
    }

    @Test
    fun testAOC2_SubSumStepCounting(){
        val input = readInput("src/test/resources/aoc1.txt"){ s -> s.toInt() }
        val sublists = createSublists(input, 3)
        val sums = sublists.map{ sublist -> sublist.reduce{acc, i -> acc + i}}
        assertEquals(countStepdowns(sums), 1683)
    }

    @Test
    fun testAOC3_movingSubmarine(){
        val sub = SimpleSubmarine()
        val commands = readInput("src/test/resources/aoc3.txt"){ s -> SubCommand.stringToSubCommand(s) }
        for(command in commands){
            command.apply(sub)
        }
        assertEquals(sub.depth * sub.x,  1250395)
    }

    @Test
    fun testAOC4_movingAimingSubmarine(){
        val sub = AimingSubmarine()
        val commands = readInput("src/test/resources/aoc3.txt"){ s -> SubCommand.stringToSubCommand(s) }
        for(command in commands){
            command.apply(sub)
        }
        assertEquals( 1451210346, sub.depth * sub.x)
    }

    @Test
    fun testOAC_3_1_binary_diagnostic(){
        val bins = readInput("src/test/resources/aoc_d3.txt"){ s -> s.toInt(2)}
        val columns = 12
        val diagnosticReport = DiagnosticReport(bins, columns)
        assertEquals(4006064, diagnosticReport.gamma * diagnosticReport.epsilon)
    }

    @Test
    fun testOAC_3_2_lifeSupportRating(){
        val bins = readInput("src/test/resources/aoc_d3.txt"){ s -> s.toInt(2)}
        val columns = 12
        val diagnosticReport = DiagnosticReport(bins, columns)
        assertEquals(5941884, diagnosticReport.getOxygenGeneratorRating() * diagnosticReport.getCO2ScrubberRating())
    }

    @Test
    fun testOAC_4_1_bingo(){
        val (inputs, boards) = readBingoConfig("src/test/resources/aoc_d4.txt")
        for(i in inputs){
            boards.map { bingoBoard -> bingoBoard.mark(i) }
            val winners = boards.filter { bingoBoard -> bingoBoard.checkWinner() }
            if(winners.isNotEmpty()){
                println("We have a winner")
                winners.forEach{w -> println(w)}
                println(winners[0].calculateScore()*i)
                assertEquals(51776, winners[0].calculateScore()*i)
                break
            }
        }

    }

    @Test
    fun testOAC_4_2_bingoWinLast(){
        val (inputs, boards) = readBingoConfig("src/test/resources/aoc_d4.txt")
        val winners = mutableSetOf<BingoBoard>()
        for(i in inputs){
            boards.map { bingoBoard -> bingoBoard.mark(i) }
            val winnersThisTurn = boards.filter { bingoBoard -> bingoBoard.checkWinner() }
            winners.addAll(winnersThisTurn)
            if(winners.size == boards.size){
                println("The game is over! In last place is: ")
                println(winners.last())
                println(winners.last().calculateScore()*i)
                assertEquals(16830, winners.last().calculateScore()*i)
                break
            }
        }

    }


    @Test
    fun testOAC_5_1_lineSegments(){
        val lineSegments = readLineSegments("src/test/resources/aoc_d5.txt")
        val vertHorzSegments = lineSegments.filter { lineSegment -> lineSegment.isHorizontalOrVertical() }

        val vents = mutableMapOf<Pair<Int,Int>, Int>()
        vertHorzSegments.forEach { lineSegment ->
            lineSegment.getPointsBetween().forEach { pair ->
                vents[pair] = vents.getOrDefault(pair,0) + 1
            }
        }

        val overlaps = vents.filterValues { i -> i >= 2 }.size
        println(overlaps)
        assertEquals(5835, overlaps)
    }

    @Test
    fun testOAC_5_2_diagonalLineSegments(){
        val lineSegments = readLineSegments("src/test/resources/aoc_d5.txt")

        val vents = mutableMapOf<Pair<Int,Int>, Int>()
        lineSegments.forEach { lineSegment ->
            lineSegment.getPointsBetween().forEach { pair ->
                vents[pair] = vents.getOrDefault(pair,0) + 1
            }
        }

        val overlaps = vents.filterValues { i -> i >= 2 }.size
        println(overlaps)
        assertEquals(17013, overlaps)
    }

    @Test
    fun testAOC_6_1_fish(){
        var fishies = readInput("src/test/resources/aoc_d6.txt"){ s -> s.split(',').map { s ->  s.toInt() }}.flatten()
        for (i in 0 until 80){
            println("iteration: $i")
            fishies = fishies.map { i -> i - 1}.flatMap { i -> if(i == -1) listOf(6,8) else listOf(i) }
        }
        println(fishies.size)
        assertEquals(359999, fishies.size)
    }

    @Test
    fun testAOC_6_1_MOARfish(){
        val initialFish = readInput("src/test/resources/aoc_d6.txt"){ s -> s.split(',').map { s ->  s.toInt() }}.flatten()
        var fishCounts = MutableList(9){index ->  initialFish.count { fishReproductionCounter -> fishReproductionCounter == index }.toLong()}
        for (i in 0 until 256){
            val numberOfNewFish = fishCounts[0]
            fishCounts.removeAt(0)
            fishCounts.add(numberOfNewFish)
            fishCounts[6] += numberOfNewFish
        }
        val fishCount = fishCounts.reduce{acc, i -> acc + i}
        println(fishCount)
        assertEquals(1631647919273, fishCount)
    }

    @Test
    fun testAOC_7_1_crabsubs(){
        var crabs = readInput("src/test/resources/aoc_d7.txt"){ s -> s.split(',').map { s ->  s.toInt() }}.flatten()
        var highestPosition = crabs.maxOf { i -> i }
        val diffs = mutableListOf<Int>()
        for(i in 0..highestPosition){
            diffs.add(crabs.map { crabPos -> abs(i - crabPos) }.reduce { acc, i -> i + acc })
        }
        val optimalPosition = diffs.indexOf( diffs.minOf { i -> i } )
        println(diffs[optimalPosition])
        assertEquals(333755, diffs[optimalPosition])
    }

    @Test
    fun testAOC_7_1_crabsubs_increasing_fuel(){
        var crabs = readInput("src/test/resources/aoc_d7.txt"){ s -> s.split(',').map { s ->  s.toInt() }}.flatten()
        var highestPosition = crabs.maxOf { i -> i }
        val diffs = mutableListOf<Int>()
        for(i in 0..highestPosition){
            diffs.add(crabs.map { crabPos -> triangular(abs(i - crabPos)) }.reduce { acc, i -> i + acc })
        }
        val optimalPosition = diffs.indexOf( diffs.minOf { i -> i } )
        println(diffs[optimalPosition])
        assertEquals(94017638, diffs[optimalPosition])
    }

    @Test
    fun testAOC_8_1_CountFourDigitCodes(){
        val KNOWN_LENGTHS = listOf(2, 4, 3, 7)
        val (rawInputs, rawOutputs) = readInput("src/test/resources/aoc_d8.txt"){ s -> s.split('|').toPair()}.unzip()
        val inputs = rawInputs.map { s -> s.split(' ').filter { s -> s.isNotEmpty() }}
        val outputs = rawOutputs.map { s -> s.split(' ').filter { s-> s.isNotEmpty() }}
        var countOfKnownDigits = outputs.map { l -> l.filter { s -> s.length in KNOWN_LENGTHS }.size }

        outputs.zip(countOfKnownDigits).map { (output, count) -> println("$output - $count") }
        println(countOfKnownDigits.sum())
    }

    @Test
    fun testAOC_8_2_unscamble(){
        val (rawInputs, rawOutputs) = readInput("src/test/resources/aoc_d8.txt"){ s -> s.split('|').toPair()}.unzip()
        val inputs = rawInputs.map { s -> s.split(' ').filter { s -> s.isNotEmpty() }}
        val outputs = rawOutputs.map { s -> s.split(' ').filter { s-> s.isNotEmpty() }}

        val possibleDigitsPerCode = inputs.map{ input -> input.map { s -> Pair(s, codeLengthToDisplayValueMap[s.length]!!) }.sortedBy { pair -> pair.first.length }}

        val testRow = possibleDigitsPerCode[0]
        for((inputString, digits) in testRow){
            val inputs = inputString.split("").filter { s -> s.isNotEmpty() }
            for(outputDigit in digits){
                val segments = defaultDisplayMap[outputDigit]!!
                val possibleCombinations = createPossibleCombinations(inputs, segments)

                println(possibleCombinations)

                // c d -> c f
                // c-c d-f | c-f d-c
                // (a-? b-? c-c d-f e-? f-?) OR (a-? b-? c-f d-c e-? f-?)

                // d c b -> a c f
                // d-a c-c b-f | d-a c-f b-c | d-c c-a b-f | d-c c-f b-a | d-f c-a b-c | d-f c-c b-a
                // X             X             X             Y             X             Y
                // (a-? b-a c-c d-f e-? f-?) OR (a-? b-a c-f d-c e-? f-?)


            }

        }




    }

    fun createPossibleCombinations(inputs: List<String>, segments: List<String>): List<Map<String, String>> {
        val results = mutableListOf<Map<String,String>>()
        for(i in segments.indices){
            val shifted = segments.rotate(i)
            results.add(inputs.zip(shifted).toMap())
        }
        return results
    }


}

private fun <E> List<E>.rotate(i: Int): List<E> {
    return this.subList(i,this.size).plus(this.subList(0, i))
}

