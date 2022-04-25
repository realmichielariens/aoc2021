import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class aoc1Test {

    @Test
    fun testAOC1_simpleStepCounting(){
        val input = readInput("src/test/resources/aoc1.txt"){ s -> s.toInt() }
        val downsteps = countStepdowns(input)
        print(downsteps)
        assertEquals(downsteps, 1655)
    }

    @Test
    fun testAOC2_SubSumStepCounting(){
        val input = readInput("src/test/resources/aoc1.txt"){ s -> s.toInt() }
        val sublists = createSublists(input, 3)
        val sums = sublists.map{ sublist -> sublist.reduce{acc, i -> acc + i}}
        print(countStepdowns(sums))
        assertEquals(countStepdowns(sums), 1683)
    }

    @Test
    fun testAOC3_movingSubmarine(){
        val sub = SimpleSubmarine()
        val commands = readInput("src/test/resources/aoc3.txt"){ s -> SubCommand.stringToSubCommand(s) }
        for(command in commands){
            command.apply(sub)
        }
        print("$sub (depth*x = ${sub.depth*sub.x})")
        assertEquals(sub.depth * sub.x,  1250395)
    }

    @Test
    fun testAOC4_movingAimingSubmarine(){
        val sub = AimingSubmarine()
        val commands = readInput("src/test/resources/aoc3.txt"){ s -> SubCommand.stringToSubCommand(s) }
        for(command in commands){
            command.apply(sub)
        }
        print("$sub (depth*x = ${sub.depth*sub.x})")
        assertEquals(sub.depth * sub.x,  1451210346)
    }


}