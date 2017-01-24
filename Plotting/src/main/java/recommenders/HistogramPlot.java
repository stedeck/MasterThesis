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
import java.util.Random;


import java.util.Random;

import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.EnumeratedData;
import de.erichseifert.gral.data.statistics.Histogram;
import de.erichseifert.gral.data.statistics.Histogram1D;
import de.erichseifert.gral.data.statistics.Statistics;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.util.MathUtils;
import de.erichseifert.gral.graphics.Orientation;

public class HistogramPlot extends ExamplePanel {
	/** First corporate color used for normal coloring.*/
	protected static final Color COLOR1 = new Color( 55, 170, 200);
	/** Second corporate color used as signal color */
	protected static final Color COLOR2 = new Color(200,  80,  75);
	
	protected static final Color COLOR3 = Color.GREEN;
	protected static final Color COLOR4 = Color.BLACK;
	
	
	public static void main(String[] args) {
		String file_name = "tmp\\stats\\articlePurchaseDistribution.txt";
		new HistogramPlot(file_name);
	}
	
	public HistogramPlot(String file_name) {
		Path path = FileSystems.getDefault().getPath(file_name);

		DataTable dataTable = null;
		// Read data from file into dataTable
		try {
			BufferedReader reader = Files.newBufferedReader(path);
			String line = reader.readLine();
			String[] headers = line.split(" ");
			int fieldsNum = headers.length;
			dataTable = new DataTable(fieldsNum, Double.class);
			
			while((line = reader.readLine()) != null){
				String[] fields = line.split(" ");
				Double[] dataFields = Arrays.stream(fields).map(x -> Double.parseDouble(x)).toArray(size -> new Double[size]);
				dataTable.add(dataFields);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create new bar plot
		BarPlot plot = new BarPlot(dataTable);

		// Format plot
		plot.setInsets(new Insets2D.Double(20.0, 65.0, 50.0, 40.0));
		plot.getTitle().setText("Distribution of article purchases");
//		plot.setBarWidth(0.78);

		// Format x axis
//		plot.getAxisRenderer(BarPlot.AXIS_X).setTickAlignment(0.0);
		plot.getAxisRenderer(BarPlot.AXIS_X).setTickSpacing(1000);
		plot.getAxisRenderer(BarPlot.AXIS_X).setMinorTicksVisible(false);
		plot.getAxis(BarPlot.AXIS_X).setRange(0, 11656);
		
		// Format y axis
//		plot.getAxis(BarPlot.AXIS_Y).setRange(0.0,
//				MathUtils.ceil(histogram.getStatistics().get(Statistics.MAX)*1.1, 25.0));
//		plot.getAxisRenderer(BarPlot.AXIS_Y).setTickAlignment(0.0);
//		plot.getAxisRenderer(BarPlot.AXIS_Y).setMinorTicksVisible(false);
//		plot.getAxisRenderer(BarPlot.AXIS_Y).setIntersection(-4.4);

		// Format bars
//		PointRenderer barRenderer = plot.getPointRenderers(histogram2d).get(0);
//		barRenderer.setColor(GraphicsUtils.deriveWithAlpha(COLOR1, 128));
//		barRenderer.setValueVisible(true);

		// Add plot to Swing component
//		InteractivePanel panel = new InteractivePanel(plot);
//		panel.setPannable(false);
//		panel.setZoomable(false);
//		add(panel);
//		showInFrame();
	
		// Add plot to Swing component
		add(new InteractivePanel(plot), BorderLayout.CENTER);
		showInFrame();
	}
	
	@Override
	public String getTitle() {
		return "Article purchase distribution";
	}

	@Override
	public String getDescription() {
		return "Article purchase distribution";
	}

}
