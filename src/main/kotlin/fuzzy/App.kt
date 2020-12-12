package fuzzy

import net.sourceforge.jFuzzyLogic.FIS
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart
import net.sourceforge.jFuzzyLogic.rule.Variable

fun main(args: Array<String>) {
    val fileName = "src/main/resources/max_engine_power.fcl"
    val fis: FIS = FIS.load(fileName, false).apply{
        setVariable("max_engine_power", 25.0)
    }.onEach { functionBlock ->
        JFuzzyChart.get().chart(functionBlock)
    }
    var lightLevel: Double
    var timeOfDay: Double
    var weight: Double
    var humidity: Double
    while (true) {
        print("Enter light level (as real number between 0 and 100, default 0): ")
        lightLevel = readLine()?.toDouble() ?: 0.0
        print("Enter weight (as real number between 0 and 100, default 0): ")
        weight = readLine()?.toDouble() ?: 0.0
        print("Enter humidity (as real number between 0 and 100, default 0): ")
        humidity = readLine()?.toDouble() ?: 0.0
        print("Enter time of day (as real number between 0 and 23, default 0): ")
        timeOfDay = readLine()?.toDouble() ?: 0.0
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
    }
}

