package errors;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Label;
import de.erichseifert.gral.graphics.Location;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.axes.LogarithmicRenderer2D;
import de.erichseifert.gral.plots.legends.Legend;
import de.erichseifert.gral.plots.legends.SeriesLegend;
import de.erichseifert.gral.plots.legends.ValueLegend;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.DiscreteLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.plots.points.SizeablePointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Orientation;

import recommenders.ExamplePanel;

public class errorsFiveModels extends ExamplePanel{
	public static void main(String[] args) {
		String file_name1 = "..\\simple-recommenders\\tmp\\errors\\v2\\lin\\43\\adaBoostErrors_0_v1.txt";
		String file_name2 = "..\\simple-recommenders\\tmp\\errors\\v2\\lin\\43\\adaBoostErrors_1_v1.txt";
		String file_name3 = "..\\simple-recommenders\\tmp\\errors\\v2\\lin\\43\\adaBoostErrors_2_v1.txt";
		String file_name4 = "..\\simple-recommenders\\tmp\\errors\\v2\\lin\\43\\adaBoostErrors_3_v1.txt";
		String file_name5 = "..\\simple-recommenders\\tmp\\errors\\v2\\lin\\43\\adaBoostErrors_4_v1.txt";
		new errorsFiveModels(new String[]{file_name1, file_name2, file_name3, file_name4, file_name5});
	}
	
	private static final Random random = new Random();
	
	/** First corporate color used for normal coloring.*/
	protected static final Color COLOR1 = new Color( 55, 170, 200);
	/** Second corporate color used as signal color */
	protected static final Color COLOR2 = new Color(200,  80,  75);
	
	protected static final Color COLOR3 = Color.GREEN;
	protected static final Color COLOR4 = Color.YELLOW;
	protected static final Color COLOR5 = Color.CYAN;
	

	
	public errorsFiveModels(String[] file_names) {

		DataTable[] tables = new DataTable[5];
		for(int i = 0; i < 5; i++){
			Path path = FileSystems.getDefault().getPath(file_names[i]);

			DataTable dataTable = null;
			// Read data from file into dataTable
			try {
				BufferedReader reader = Files.newBufferedReader(path);
				
				dataTable = new DataTable(Integer.class, Double.class);
				int c = -1;
				String number = "";
				int iterNum = 1;
				while((c = reader.read()) != -1){
					if(c == 44){
						dataTable.add(iterNum, Double.parseDouble(number));
						number = "";
						iterNum++;
					}else{
						number += (char)c;
					}
				}
				tables[i] = dataTable;
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		// Plot
		DataSeries series1 = new DataSeries("Model 1", tables[0], 0, 1);
		DataSeries series2 = new DataSeries("Model2", tables[1], 0, 1);
		DataSeries series3 = new DataSeries("Model 3", tables[2], 0, 1);
		DataSeries series4 = new DataSeries("Model4", tables[3], 0, 1);
		DataSeries series5 = new DataSeries("Model 5", tables[4], 0, 1);
		XYPlot plot = new XYPlot(series1, series2, series3, series4, series5);
		plot.setLegendVisible(true);
		plot.getLegend().setAlignmentX(1);
		plot.getLegend().setAlignmentY(1);
		plot.getPlotArea().setBorderStroke(null);
		
		// Format plot
		plot.setInsets(new Insets2D.Double(70.0, 70.0, 70.0, 70.0));
		plot.setBackground(Color.WHITE);
		plot.getTitle().setText(getDescription());
		
		// Format axes
		AxisRenderer axisRendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
		AxisRenderer axisRendererY = plot.getAxisRenderer(XYPlot.AXIS_Y);
		axisRendererY.setLabelDistance(2);
		axisRendererX.setLabel(new Label("iteration"));
		axisRendererY.setLabel(new Label("mean absolute error"));
		plot.setAxisRenderer(XYPlot.AXIS_X, axisRendererX);
		plot.setAxisRenderer(XYPlot.AXIS_Y, axisRendererY);
		
		// Format rendering of data points
		PointRenderer pointRenderer1 = new DefaultPointRenderer2D();
		pointRenderer1.setShape(null);
		pointRenderer1.setColor(GraphicsUtils.deriveDarker(COLOR1));
		plot.setPointRenderers(series1, pointRenderer1);
		
		PointRenderer pointRenderer2 = new DefaultPointRenderer2D();
		pointRenderer2.setShape(null);
		pointRenderer2.setColor(GraphicsUtils.deriveDarker(COLOR2));
		plot.setPointRenderers(series2, pointRenderer2);
		
		PointRenderer pointRenderer3 = new DefaultPointRenderer2D();
		pointRenderer3.setShape(null);
		pointRenderer3.setColor(GraphicsUtils.deriveDarker(COLOR3));
		plot.setPointRenderers(series3, pointRenderer3);
		
		PointRenderer pointRenderer4 = new DefaultPointRenderer2D();
		pointRenderer4.setShape(null);
		pointRenderer4.setColor(GraphicsUtils.deriveDarker(COLOR4));
		plot.setPointRenderers(series4, pointRenderer4);
		
		PointRenderer pointRenderer5 = new DefaultPointRenderer2D();
		pointRenderer5.setShape(null);
		pointRenderer5.setColor(GraphicsUtils.deriveDarker(COLOR5));
		plot.setPointRenderers(series5, pointRenderer5);
		
		// Format data lines
		LineRenderer lineRenderer1 = new DefaultLineRenderer2D();
		lineRenderer1.setColor(COLOR1);
		plot.setLineRenderers(series1, lineRenderer1);
		
		LineRenderer lineRenderer2 = new DefaultLineRenderer2D();
		lineRenderer2.setColor(COLOR2);
		plot.setLineRenderers(series2, lineRenderer2);
		
		LineRenderer lineRenderer3 = new DefaultLineRenderer2D();
		lineRenderer3.setColor(COLOR3);
		plot.setLineRenderers(series3, lineRenderer3);
		
		LineRenderer lineRenderer4 = new DefaultLineRenderer2D();
		lineRenderer4.setColor(COLOR4);
		plot.setLineRenderers(series4, lineRenderer4);
		
		LineRenderer lineRenderer5 = new DefaultLineRenderer2D();
		lineRenderer5.setColor(COLOR5);
		plot.setLineRenderers(series5, lineRenderer5);
		
		// Add plot to Swing component
		add(new InteractivePanel(plot), BorderLayout.CENTER);
		showInFrame();
	}

	@Override
	public String getTitle() {
		return "predition error";
	}

	@Override
	public String getDescription() {
		return "prediction error";
}
}
