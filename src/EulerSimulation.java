import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class EulerSimulation {
    public static void main(String[] args) throws IOException {


        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Avg. number of summations");


        List<Integer> values = new ArrayList<>();

        int numSimulations = 1000000;
        double countSum = 0;

        for(int i = 0; i < numSimulations; i++) {
            int count = 0;
            double sum = 0;
            while (!(sum > 1)) {
                sum += Math.random();
                count++;
                if (sum > 1) {
                    //values.add(count);
                    countSum += count;
                    count = 0;
                }
            }
            //series1.add(i+1,values.stream().mapToInt(e -> e).average().getAsDouble());
            series1.add(i+1, countSum/(i+1));
        }
        dataset.addSeries(series1);

        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                "Simulation of Euler's Number", // Chart title
                "# of Simulations", // X-Axis Label
                "Average summations", // Y-Axis Label
                dataset // Dataset for the Chart
        );



        XYPlot plot = (XYPlot) scatterPlot.getPlot();

        //Sets range for x and y axes
        plot.getRangeAxis().setRange(2.5,3);
        plot.getDomainAxis().setRange(0, numSimulations);

        //Configures the point and line thickness
        XYLineAndShapeRenderer lineRenderer = new XYLineAndShapeRenderer();
        lineRenderer.setSeriesShape(0, new Ellipse2D.Double(-1, -1, 2, 2));
        lineRenderer.setSeriesStroke(0, new BasicStroke(0.7f));

        //Creates horizontal line for the value of e
        ValueMarker euler = new ValueMarker(Math.exp(1));
        euler.setPaint(Color.BLACK);
        plot.addRangeMarker(euler);

        lineRenderer.setSeriesLinesVisible(0, true);
        plot.setRenderer(lineRenderer);

        String fileName = "Simulation of Euler's Number with " + numSimulations + " Simulations.png";
        ChartUtils.saveChartAsPNG(new File(fileName), scatterPlot, 1000, 1000);

    }
}
