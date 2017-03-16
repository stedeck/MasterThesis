package recommenders;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Label;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.graphics.Insets2D;

@SuppressWarnings("serial")
public class CFItemNNCandidates extends ExamplePanel{
	public static void main(String[] args) {
		String file_name = "..//CFItemNNCandidates.csv";
		new CFItemNNCandidates(file_name);
	}
	
	protected static final double MOST_POP = 0.0499;
	
	protected static final Color COLOR1 = new Color( 55, 170, 200);
	protected static final Color COLOR2 = Color.GRAY;
	

	
	public CFItemNNCandidates(String file_name) {
		Path path = FileSystems.getDefault().getPath(file_name);

		DataTable dataTable = null;
		// Read data from file into dataTable
		try {
			BufferedReader reader = Files.newBufferedReader(path);
			
			String line = reader.readLine();
			String[] headers = line.split(",");
			int fieldsNum = headers.length;
			dataTable = new DataTable(fieldsNum, Double.class);
			
			dataTable = new DataTable(fieldsNum+1, Double.class);
			while((line = reader.readLine()) != null){
				String[] fields = line.split(",");
				Double[] dataFields = Arrays.stream(fields).map(x -> Double.parseDouble(x)).toArray(size -> new Double[size]);
				Double[] doubleWithMostPop = Arrays.copyOf(dataFields, fieldsNum+1);
				doubleWithMostPop[fieldsNum] = MOST_POP;
				dataTable.add(doubleWithMostPop);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Plot
		DataSeries series1 = new DataSeries("CFUserNN", dataTable, 0, 1);
		DataSeries series2 = new DataSeries("MostPop", dataTable, 0, 2);

		XYPlot plot = new XYPlot(series1, series2);
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
		axisRendererX.setLabel(new Label("candidate threshold"));
		axisRendererY.setLabel(new Label("MAP"));
		plot.setAxisRenderer(XYPlot.AXIS_X, axisRendererX);
		plot.setAxisRenderer(XYPlot.AXIS_Y, axisRendererY);
		
		// Format rendering of data points
		PointRenderer pointRenderer1 = new DefaultPointRenderer2D();
		pointRenderer1.setColor(GraphicsUtils.deriveDarker(COLOR1));
		plot.setPointRenderers(series1, pointRenderer1);
		
		PointRenderer pointRenderer2 = new DefaultPointRenderer2D();
		pointRenderer2.setShape(null);
		plot.setPointRenderers(series2, pointRenderer2);
		
		// Format data lines
		LineRenderer lineRenderer1 = new DefaultLineRenderer2D();
		lineRenderer1.setColor(COLOR1);
		plot.setLineRenderers(series1, lineRenderer1);
		
		LineRenderer lineRenderer2 = new DefaultLineRenderer2D();
		lineRenderer2.setColor(COLOR2);
		plot.setLineRenderers(series2, lineRenderer2);
		
		// Add plot to Swing component
		add(new InteractivePanel(plot), BorderLayout.CENTER);
		showInFrame();
	}

	@Override
	public String getTitle() {
		return "CFItemNN";
	}

	@Override
	public String getDescription() {
		return "CFItemNN";
}
}
