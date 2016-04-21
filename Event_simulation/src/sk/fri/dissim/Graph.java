package sk.fri.dissim;

import com.lowagie.text.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Jakub Janus
 */
public class Graph extends JPanel {
    
    private String label = "xxx";
    private String xLabel = "xxx";
    private String yLabel = "xxx";
    private XYSeries series;
    
    public Graph(){
        series = new XYSeries("Meno");
        this.label = "Popis";
        xLabel = "Osa x";
        yLabel = "Osa y";
        ChartPanel chartPanel = getChartPanel();
        chartPanel.setPreferredSize(new Dimension(350,250));
        setBackground(Color.white);
        add(chartPanel);
        validate();    
    }
    
    public Graph(String name, String label, String x, String y, int width, int height)
    {
        series = new XYSeries(name);
        this.label = label;
        xLabel = x;
        yLabel = y;
        ChartPanel chartPanel = getChartPanel();
        chartPanel.setPreferredSize(new Dimension(width,height));
        setBackground(Color.white);
        add(chartPanel);
        validate();
    }

    public void setLabel(String label){
        this.label = label;
    }
    
    public void setXName(String x){
        xLabel = x;
    }
    
    public void setYName(String y){
        yLabel = y;
    }
    
    private ChartPanel getChartPanel() 
    {
        JFreeChart lineGraph = ChartFactory.createXYLineChart(label, xLabel, yLabel, new XYSeriesCollection(series), PlotOrientation.VERTICAL, true, true, false);
        java.awt.Font font = new java.awt.Font("Arial", Font.BOLD, 14);
        TextTitle tt = new TextTitle(label, font);
        lineGraph.setTitle(tt);
        XYPlot xyPlot = (XYPlot)lineGraph.getPlot();
        xyPlot.setDomainCrosshairVisible(false);
        xyPlot.setRangeCrosshairVisible(false);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        
        NumberAxis yAxis = (NumberAxis)xyPlot.getRangeAxis();
        yAxis.setAutoRange(true);
        yAxis.setAutoTickUnitSelection(true);
        yAxis.setAutoRangeIncludesZero(false);
        yAxis.setVerticalTickLabels(false);

        NumberAxis xAxis = (NumberAxis)xyPlot.getDomainAxis();
        xAxis.setAutoRange(true);
        xAxis.setAutoTickUnitSelection(true);
        xAxis.setAutoRangeIncludesZero(false);
        xAxis.setVerticalTickLabels(false);
        
        ChartPanel result = new ChartPanel(lineGraph);
        return result;
    }
    
    public void addPoint(double yAxis, int xAxis)
    {
        series.add(xAxis, yAxis, true);
    }
    
    public void reset()
    {
        series.clear();
    }
}