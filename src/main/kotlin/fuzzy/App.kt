package fuzzy

import net.sourceforge.jFuzzyLogic.FIS
import net.sourceforge.jFuzzyLogic.FunctionBlock
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart
import net.sourceforge.jFuzzyLogic.rule.Variable
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val fileName = "src/main/resources/max_engine_power.fcl"
    val fis: FIS = FIS.load(fileName)
    val functionBlock: FunctionBlock = fis.getFunctionBlock("engine_power")

    JFuzzyChart.get().chart(functionBlock)

    print("Enter light level (as real number between 0 and 100, default 0): ")
    val lightLevel: Double = readLine()?.toDouble() ?: 0.0
    print("Enter weight (as real number between 0 and 150, default 0): ")
    val weight: Double = readLine()?.toDouble() ?: 0.0
    print("Enter humidity (as real number between 0 and 100, default 0): ")
    val humidity: Double = readLine()?.toDouble() ?: 0.0
    print("Enter time of day (as real number between 0 and 23, default 0): ")
    val timeOfDay: Double = readLine()?.toDouble() ?: 0.0

    fis.apply {
        setVariable(functionBlock.name, "max_engine_power", 25.0)
        setVariable(functionBlock.name,"light", lightLevel)
        setVariable(functionBlock.name,"time_of_day", timeOfDay)
        setVariable(functionBlock.name,"weight", weight)
        setVariable(functionBlock.name,"humidity", humidity)
        evaluate()
    }

    val volumeChange: Variable = functionBlock.getVariable("max_engine_power")
    println("Max engine power: ${volumeChange.defuzzify()}")
    JFuzzyChart.get().chart(volumeChange, volumeChange.defuzzifier, true)


    print("Press enter to close")
    readLine()
    exitProcess(0)
}

