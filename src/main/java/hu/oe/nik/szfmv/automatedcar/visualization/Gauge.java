package hu.oe.nik.szfmv.automatedcar.visualization;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.DialScale;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.StandardGradientPaintTransformer;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Dashboard visual component representing a gauge.
 *
 * @author beregvarizoltan
 * @author BazsoGabor
 * @author Kadi96
 *
 * Date: 2019-03-28
 *
 * The component uses the JFreeChart library.
 * @see <a href="http://www.jfree.org/jfreechart">JFreeChart home page</a>
 * @see <a href="https://github.com/jfree/jfreechart">JFreeChart GitHub repository</a>
 */
public class Gauge extends JPanel {

    private static final Color BACKGROUD_COLOR = new Color(0x888888);

    private DefaultValueDataset dataset;

    public Gauge (int x, int y, int width, int height, String text, double minValue, double maxValue, double step) {
        setBounds(x, y, width, height);
        setBackground(BACKGROUD_COLOR);
        ChartPanel chartPanel = new ChartPanel(createJFreeChart(text, minValue, maxValue, step));
        chartPanel.setBackground(BACKGROUD_COLOR);
        chartPanel.setPreferredSize(new Dimension(width - 5, height - 5));
        add(chartPanel);
    }

    public void setValue(double value) {
        if (value < 0) {
            dataset.setValue(Math.abs(value));
        } else {
            dataset.setValue(value);
        }
    }

    private JFreeChart createJFreeChart(String text, double minValue, double maxValue, double step) {
        DialPlot dialPlot = new DialPlot();
        dataset = new DefaultValueDataset(minValue);
        dialPlot.setDataset(dataset);

        dialPlot.setDialFrame(new StandardDialFrame());

        dialPlot.setBackground(createBackgroud());

        dialPlot.addLayer(createDialTextAnnotation(text));

        dialPlot.addLayer(createDialValueIndicator());

        dialPlot.addScale(0, createDialScale(minValue, maxValue, step));

        DialPointer.Pointer pointer = new DialPointer.Pointer();
        pointer.setFillPaint(Color.yellow);
        dialPlot.addPointer(pointer);

        dialPlot.setCap(new DialCap());

        JFreeChart jFreeChart = new JFreeChart(dialPlot);
        jFreeChart.setBackgroundPaint(BACKGROUD_COLOR);
        return jFreeChart;
    }

    private DialBackground createBackgroud() {
        GradientPaint gradientPaint = new GradientPaint(
            new Point(), new Color(255, 255, 255),
            new Point(), new Color(170, 170, 220));
        DialBackground dialBackground = new DialBackground(gradientPaint);
        dialBackground.setGradientPaintTransformer(
            new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        return dialBackground;
    }

    private DialTextAnnotation createDialTextAnnotation(String text) {
        DialTextAnnotation dialTextAnnotation = new DialTextAnnotation(text);
        dialTextAnnotation.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
        dialTextAnnotation.setRadius(0.53);
        return dialTextAnnotation;
    }

    private DialValueIndicator createDialValueIndicator() {
        DialValueIndicator dialValueIndicator = new DialValueIndicator();
        dialValueIndicator.setNumberFormat(NumberFormat.getIntegerInstance());
        dialValueIndicator.setRadius(0.35);
        dialValueIndicator.setInsets(new RectangleInsets(0, 4, 0, 4));
        dialValueIndicator.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
        return dialValueIndicator;
    }

    private DialScale createDialScale(double minValue, double maxValue, double step) {
        StandardDialScale standardDialScale = new StandardDialScale(
            minValue, maxValue,
            -135D, -270D,
            step, 3);
        standardDialScale.setTickRadius(0.88D);
        standardDialScale.setTickLabelOffset(0.2D);
        standardDialScale.setTickLabelFont(new Font(Font.DIALOG, 0, 20));
        standardDialScale.setTickLabelFormatter(NumberFormat.getIntegerInstance());
        return standardDialScale;
    }
}
