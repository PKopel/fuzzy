package fuzzy.view

import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart
import net.sourceforge.jFuzzyLogic.rule.Variable
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle
import javax.swing.JPanel

class VariableChart(private val variable: Variable) : JPanel() {
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val chart = Rectangle()
        JFuzzyChart.get().draw(g as Graphics2D, chart, variable)
    }
}