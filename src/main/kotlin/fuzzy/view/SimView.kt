package fuzzy.view

import net.sourceforge.jFuzzyLogic.FIS
import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.Rectangle
import java.awt.TextField
import javax.swing.JFrame
import javax.swing.JPanel

class SimView(val fis: FIS) : JFrame() {

    private val lightTextField = TextField()
    private val lightChart = VariableChart(fis.getVariable("light"))
    private val weightTextField = TextField()
    private val weightChart = VariableChart(fis.getVariable("weight"))
    private val humidityTextField = TextField()
    private val humidityChart = VariableChart(fis.getVariable("humidity"))
    private val timeOfDayTextField = TextField()
    private val timeOfDayChart  = VariableChart(fis.getVariable("time_of_day"))
    private val maxEnginePowerTextField = TextField().apply {
        isEditable = false
    }
    private val maxEnginePowerChart  = VariableChart(fis.getVariable("max_engine_power"))

    init {
        val charts = JPanel(GridLayout(1, 5))
        charts.add(lightChart)
        charts.add(weightChart)
        charts.add(humidityChart)
        charts.add(timeOfDayChart)
        charts.add(maxEnginePowerChart)
        this.add(BorderLayout.CENTER, charts)
        val textFields = JPanel(GridLayout(1, 5))
        textFields.add(lightTextField)
        textFields.add(weightTextField)
        textFields.add(humidityTextField)
        textFields.add(timeOfDayTextField)
        textFields.add(maxEnginePowerTextField)
        this.add(BorderLayout.SOUTH, textFields)
    }

}