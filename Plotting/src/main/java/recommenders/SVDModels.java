package recommenders;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
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
public class SVDModels extends ExamplePanel{
	public static void main(String[] args) {
		String file_name = "..\\SVDModels.csv";
		new SVDModels(file_name);
	}
	
	protected static final double MOST_POP = 0.0499;
	
	protected static final Color COLOR1 = new Color( 55, 170, 200);
	protected static final Color COLOR2 = new Color(200,  80,  75);
	protected static final Color COLOR3 = Color.GREEN;
	
	protected static final Color COLOR4 = COLOR1.darker();
	protected static final Color COLOR5 = COLOR2.darker();
	protected static final Color COLOR6 = COLOR3.darker();
	
	protected static final Color COLOR7 = Color.GRAY;
	
	public SVDModels(String file_name) {
		Path path = FileSystems.getDefault().getPath(file_name);

		DataTable dataTable = null;
		// Read data from file into dataTable
		try {
			BufferedReader reader = Files.newBufferedReader(path);
			String line = reader.readLine();
			String[] headers = line.split(",");
			int fieldsNum = headers.length;
			dataTable = new DataTable(fieldsNum+1, Double.class);
			
			while((line = reader.readLine()) != null){
				String[] fields = line.split(",");
				Double[] dataFields = Arrays.stream(fields).map(x -> Double.parseDouble(x)).toArray(size -> new Double[size]);
				Double[] dataFieldsWithMostPop = Arrays.copyOf(dataFields, fieldsNum+1);
				dataFieldsWithMostPop[fieldsNum] = MOST_POP;
				dataTable.add(dataFieldsWithMostPop);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Plot
		DataSeries series1 = new DataSeries("Log 100 features", dataTable, 0, 1);
		DataSeries series2 = new DataSeries("Log 200 features", dataTable, 0, 2);
		DataSeries series3 = new DataSeries("Log 300 features", dataTable, 0, 3);
		DataSeries series4 = new DataSeries("Lin 100 features", dataTable, 0, 4);
		DataSeries series5 = new DataSeries("Lin 200 features", dataTable, 0, 5);
		DataSeries series6 = new DataSeries("Lin 300 features", dataTable, 0, 6);
		DataSeries series7 = new DataSeries("MostPop", dataTable, 0, 7);
		XYPlot plot = new XYPlot(series1, series2, series3, series4, series5, series6, series7);
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
		axisRendererX.setLabel(new Label("# models"));
		axisRendererY.setLabel(new Label("MAP"));
		plot.setAxisRenderer(XYPlot.AXIS_X, axisRendererX);
		plot.setAxisRenderer(XYPlot.AXIS_Y, axisRendererY);
		
		// Format rendering of data points
		PointRenderer pointRenderer1 = new DefaultPointRenderer2D();
		pointRenderer1.setColor(GraphicsUtils.deriveDarker(COLOR1));
		pointRenderer1.setShape(new Rectangle2D.Double(-3.5,-3.5,7,7));
		plot.setPointRenderers(series1, pointRenderer1);
		
		PointRenderer pointRenderer2 = new DefaultPointRenderer2D();
		pointRenderer2.setColor(GraphicsUtils.deriveDarker(COLOR2));
		pointRenderer2.setShape(new Rectangle2D.Double(-3.5,-3.5,7,7));
		plot.setPointRenderers(series2, pointRenderer2);
		
		PointRenderer pointRenderer3 = new DefaultPointRenderer2D();
		pointRenderer3.setColor(GraphicsUtils.deriveDarker(COLOR3));
		pointRenderer3.setShape(new Rectangle2D.Double(-3.5,-3.5,7,7));
		plot.setPointRenderers(series3, pointRenderer3);
		
		PointRenderer pointRenderer4 = new DefaultPointRenderer2D();
		pointRenderer4.setColor(GraphicsUtils.deriveDarker(COLOR4));
		pointRenderer4.setShape(new Ellipse2D.Double(-3.5,-3.5,7,7));
		plot.setPointRenderers(series4, pointRenderer4);
		
		PointRenderer pointRenderer5 = new DefaultPointRenderer2D();
		pointRenderer5.setColor(GraphicsUtils.deriveDarker(COLOR5));
		pointRenderer5.setShape(new Ellipse2D.Double(-3.5,-3.5,7,7));
		plot.setPointRenderers(series5, pointRenderer5);
		
		PointRenderer pointRenderer6 = new DefaultPointRenderer2D();
		pointRenderer6.setColor(GraphicsUtils.deriveDarker(COLOR6));
		pointRenderer6.setShape(new Ellipse2D.Double(-3.5,-3.5,7,7));
		plot.setPointRenderers(series6, pointRenderer6);
		
		PointRenderer pointRenderer7 = new DefaultPointRenderer2D();
		pointRenderer7.setShape(null);
		plot.setPointRenderers(series7, pointRenderer7);
		
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
		
		LineRenderer lineRenderer6 = new DefaultLineRenderer2D();
		lineRenderer6.setColor(COLOR6);
		plot.setLineRenderers(series6, lineRenderer6);
		
		LineRenderer lineRenderer7 = new DefaultLineRenderer2D();
		lineRenderer7.setColor(COLOR7);
		plot.setLineRenderers(series7, lineRenderer7);
		
		// Add plot to Swing component
		add(new InteractivePanel(plot), BorderLayout.CENTER);
		showInFrame();
	}

	@Override
	public String getTitle() {
		return "SVD";
	}

	@Override
	public String getDescription() {
		return "SVD";
}
}
