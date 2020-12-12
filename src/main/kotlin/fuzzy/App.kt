package fuzzy

import net.sourceforge.jFuzzyLogic.FIS
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart
import net.sourceforge.jFuzzyLogic.rule.Variable
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val fileName = "src/main/resources/max_engine_power.fcl"
    val fis: FIS = FIS.load(fileName, false).apply {
        setVariable("max_engine_power", 25.0)
    }.onEach { functionBlock ->
        JFuzzyChart.get().chart(functionBlock)
    }

    print("Enter light level (as real number between 0 and 100, default 0): ")
    val lightLevel: Double = readLine()?.toDouble() ?: 0.0
    print("Enter weight (as real number between 0 and 150, default 0): ")
    val weight: Double = readLine()?.toDouble() ?: 0.0
    print("Enter humidity (as real number between 0 and 100, default 0): ")
    val humidity: Double = readLine()?.toDouble() ?: 0.0
    print("Enter time of day (as real number between 0 and 23, default 0): ")
    val timeOfDay: Double = readLine()?.toDouble() ?: 0.0

    fis.apply {
        setVariable("light", lightLevel)
        setVariable("time_of_day", timeOfDay)
        setVariable("weight", weight)
        setVariable("humidity", humidity)
        evaluate()
    }.onEach { functionBlock ->
        val volumeChange: Variable = functionBlock.getVariable("max_engine_power")
        JFuzzyChart.get().chart(volumeChange, volumeChange.defuzzifier, true)
    }

    print("Press enter to close")
    readLine()
    exitProcess(0)
}

