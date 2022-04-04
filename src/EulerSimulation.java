import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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

        Scanner consoleScanner = new Scanner(System.in);
        int numSimulations = 1000000;
        boolean validInput = false;
        double estimatedValue = 0;

        System.out.println("This program estimates Euler's Number given a specified number of simulations used to" +
                " generate the estimate.");

        do{
            try{
                System.out.print("Enter the number of simulations: ");
                numSimulations = consoleScanner.nextInt();
                validInput = true;
                if(numSimulations <= 0) {
                    validInput = false;
                    System.out.println("Please enter a positive integer.");
                }
            }catch (InputMismatchException e)
            {
                consoleScanner.nextLine();
                validInput = false;
                System.out.println("Please enter an integer.");
            }
        }while(!validInput);

        System.out.println(numSimulations);



        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Avg. number of summations");
        List<Integer> values = new ArrayList<>();


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
            estimatedValue = countSum/(i+1);
            series1.add(i+1, estimatedValue);
        }
        dataset.addSeries(series1);

        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                "Simulation of Euler's Number", // Chart title
                "# of Simulations", // X-Axis Label
                "Average summations", // Y-Axis Label
                dataset // Dataset for the Chart
        );

        Double percentError = (Math.abs((estimatedValue - Math.exp(1)))/Math.exp(1)) * 100;
        String strPercentError = String.format("%.5f", percentError);

        System.out.println("Estimated value of e: " + estimatedValue);
        System.out.println("Percent error between estimated value and theoretical value of Euler's Number: "
                + strPercentError + "%.");

        System.out.println("Please do not close the program while the graph is being generated.");

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

        System.out.println("-------------------------------------------------------------------");
        System.out.println("Graph of Euler's Number simulation is complete.");

    }
}
