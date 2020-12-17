@file:Suppress("JoinDeclarationAndAssignment")

package fuzzy

import net.sourceforge.jFuzzyLogic.FIS
import net.sourceforge.jFuzzyLogic.FunctionBlock
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart
import net.sourceforge.jFuzzyLogic.rule.Variable
import kotlin.system.exitProcess

//approximate values
const val scooterWeight = 15
const val scooterEnginePower = 300

fun powerToSpeed(power: Double, weight: Double): Double =
    power / (0.4 * (scooterWeight + weight))

fun main(args: Array<String>) {
    val fileName = "src/main/resources/max_engine_power.fcl"
    val fis: FIS = FIS.load(fileName)
    val functionBlock: FunctionBlock = fis.getFunctionBlock("engine_power")
    var input: String?

    print("Show charts? [y/n]:")
    input = readLine()
    if (input?.contains('y', true) == true)
        JFuzzyChart.get().chart(functionBlock)

    print("Enter light intensity (in lx, as a real number between 20 and 5000, default 500): ")
    input = readLine()
    val lightIntensity: Double = if (input?.isNotEmpty() == true) input.toDouble() else 80.0
    print("Enter driver weight (in kg, as a real number between 0 and 150, default 70): ")
    input = readLine()
    val driverWeight: Double = if (input?.isNotEmpty() == true) input.toDouble() else 70.0
    print("Enter humidity (in %, as a real number between 0 and 100, default 30): ")
    input = readLine()
    val humidity: Double = if (input?.isNotEmpty() == true) input.toDouble() else 30.0
    print("Enter time of day (in hours, as a real number between 0 and 23, default 10): ")
    input = readLine()
    val timeOfDay: Double = if (input?.isNotEmpty() == true) input.toDouble() else 10.0

    fis.apply {
        setVariable(functionBlock.name, "max_engine_power", 25.0)
        setVariable(functionBlock.name, "light_intensity", lightIntensity)
        setVariable(functionBlock.name, "time_of_day", timeOfDay)
        setVariable(functionBlock.name, "driver_weight", driverWeight)
        setVariable(functionBlock.name, "humidity", humidity)
        evaluate()
    }

    val maxEnginePowerPercentage: Variable = functionBlock.getVariable("max_engine_power")

    val maxEnginePower = (maxEnginePowerPercentage.defuzzify()) / 100 * scooterEnginePower
    println("Max engine power : $maxEnginePower W")
    println("Max speed: ${powerToSpeed(maxEnginePower, driverWeight)} m/s")

    print("Show chart? [y/n]:")
    input = readLine()
    if (input?.contains('y', true) == true) {
        JFuzzyChart.get().chart(maxEnginePowerPercentage, maxEnginePowerPercentage.defuzzifier, true)
        print("Press enter to close")
        readLine()
    }

    exitProcess(0)
}

