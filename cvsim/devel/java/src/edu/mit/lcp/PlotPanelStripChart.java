package edu.mit.lcp;

import java.awt.geom.Point2D;
import java.awt.Insets;
import javax.swing.JComponent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListDataListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListDataEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.List;
import java.util.ArrayList;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

// Custom plotter, extension of JPanel
public class PlotPanelStripChart extends PlotPanel {

    ///////////////////////
    // private variables
    //////////////////////
    private static final int NUM_TICKS = 5;

    private int traceBufferSize;
    private TraceListModel traceList;
    private JLabel titleLabel;
    private yScaleComponent xScale;
    //private yScaleComponent yScale;
    private JSlider speedSlider;
    private AddStripChartTraceComponent addComponent;
    private VariableRecorderInterface<Double> timeAxisBuffer;
    private double secondsPerUnit;

    ///////////////////////
    // public variables
    //////////////////////
    public double xMax = 0;
    public double xMin = 0;
    public double yMax = 0;
    public double yMin = 0;

    public boolean showTitle = true;
    public boolean showXScale = false;
    public boolean showYScale = true;
    
    public PlotComponent plot;

    public PlotPanelStripChart(TraceListModel model) {
	// store local reference to data model holding the traces
	traceList = model;
	//System.out.println("PlotPanelStripChart()");
	titleLabel = new JLabel("Plot Title");

	// register handler for changes in traceList
	traceList.addListDataListener( new ListDataListener() {
		public void intervalAdded(ListDataEvent e) {}
		public void intervalRemoved(ListDataEvent e){}
		public void contentsChanged(ListDataEvent e){}    
	    });    
	
	// register handler for changes in simulation compression (step size change)
	CVSim.sim.addPropertyChangeListener(CSimulation.COMPRESSION, new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent pce) {
		    updateTraceBufferSizes();
		}
	    });

    	plot = new PlotComponent(traceList, PlotComponent.SCALE_X_DPI, PlotComponent.STRIPCHART);
	plot.setBorder(new CompoundBorder(new MatteBorder(14,0,14,0, getBackground()), 
					  new LineBorder(Color.BLACK, 1)));

 	// register handler for resize events
  	plot.addComponentListener( new ComponentAdapter() {
  		public void componentResized(ComponentEvent e) {
		    updateTraceBufferSizes();
 		}
  	    });

	plot.addMouseListener(new PlotMouseListener());
	
 	yScale = new yScaleComponent(traceList, 15, NUM_TICKS, this);

	// Setup Add widget and speed slider
	List<SimulationOutputVariable> tmpVarList = new ArrayList<SimulationOutputVariable>(CVSim.sim.getOutputVariables());
	tmpVarList.remove(CVSim.sim.getOutputVariable("TIME"));
	
	final JComboBox yTraceBox = new JComboBox(new SimulationOutputVariableListModel(tmpVarList));
	JButton addButton = new JButton("Add Trace");
	addButton.setMargin(new Insets(1,2,2,2));
	addButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    createNewTrace((SimulationOutputVariable)yTraceBox.getSelectedItem());
		}
	    });

	speedSlider = new JSlider(1,100,5);
	speedSlider.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
			System.out.println("New Paper Speed: " + (double)source.getValue());
			setPaperSpeed((double)source.getValue()/10.0);
		    } 
		} 
	    }); 

	TitledBorder titledBorder = BorderFactory.createTitledBorder(
	    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
	    "Plot Speed");
	titledBorder.setTitleJustification(TitledBorder.CENTER);
	speedSlider.setBorder(titledBorder);

	JPanel controlPanel = new JPanel(new FlowLayout());
	controlPanel.add(new JLabel("Y: "));
	controlPanel.add(yTraceBox);
	controlPanel.add(addButton);
	controlPanel.add(speedSlider);
	controlPanel.add(multipleYScalesCheckBox);
	controlPanel.setPreferredSize(new Dimension(25, 75));
	
 	//add(xScale);
	this.setLayout(new BorderLayout());
	add(BorderLayout.PAGE_START, controlPanel);
 	add(BorderLayout.LINE_START, yScale);
	add(BorderLayout.CENTER, plot);

	sourceDataChanged = new ChangeListener() {
		int count=0;
		public void stateChanged(ChangeEvent event) {
		    if ((count++ % 4) == 0) {
			updateTraceTransforms();
			plot.repaint();
		    }
		}
	    };

	// Set default paper speed, and generate a default trace
	secondsPerUnit = 1;
	calculateTraceBufferSize();

    }
    
    public void setMultipleYScales(boolean newValue) { 
 	boolean oldValue = getMultipleYScales(); 
 	multipleYScales = newValue; 
 	yScale._changes.firePropertyChange(yScale.PROP_MULTISCALES, oldValue, newValue);
     }

    public PlotComponent getPlot() { return plot; }

    public Dimension getPreferredSize() {
	return getMinimumSize();
    }

    public Dimension getMinimumSize() {
	return new Dimension(yScale.getPreferredSize().width, yScale.getPreferredSize().width*8);
    }

    private void createTimeAxisBuffer() {
	//System.out.println("PlotPanelStripChart() Creating timeAxisBuffer");
	timeAxisBuffer = new SimulationOutputVariableBuffer<Double>
	    (traceBufferSize, CVSim.sim.getOutputVariable("TIME"), new Double(0.0),
	     SimulationOutputVariableBuffer.DATA_MONOTONIC_INCREASING);
	CVSim.sim.addVariableRecorder(timeAxisBuffer);
    }

    public void createNewTrace(SimulationOutputVariable var) {
	//System.out.println("createNewTrace(" + var + ")");
	if (timeAxisBuffer == null) createTimeAxisBuffer();
	VariableRecorderInterface<Double> y =  new SimulationOutputVariableBuffer<Double>
	    (traceBufferSize, var, new Double(0.0));
	
	Trace newTrace = new Trace<Double,Double>(timeAxisBuffer, y, traceList.getNextColor());
	addTrace(newTrace);
    }
    
    public void addTrace(Trace<?,?> t) {
	//System.out.println("addTrace(" + t + ")");
	traceList.add(t);
	CVSim.sim.addVariableRecorder(t.getYVar());
    }

    public void removeTrace(Trace<?,?> t) {
	//System.out.println("removeTrace(" + t + ")");
	traceList.remove(t);
	CVSim.sim.removeVariableRecorder(t.getYVar());
	if (traceList.isEmpty() && (timeAxisBuffer != null)) {
	    CVSim.sim.removeVariableRecorder(timeAxisBuffer);
	    timeAxisBuffer = null;
	}   
    }

    public void removeAllTraces() {
	//System.out.println("removeAllTraces()");
	for (Trace t: traceList)
	    removeTrace(t);
    }

    private int calculateTraceBufferSize() {
	int stepSize = CVSim.sim.getDataCompressionFactor();
	double plotTimeRange = secondsPerUnit*plot.getScaledPlotBounds().getWidth();
	traceBufferSize = (int)(1000*plotTimeRange)/(stepSize);
	return traceBufferSize;
    }

    private SimulationOutputVariableBuffer resizeBuffer(SimulationOutputVariableBuffer b, int newSize) {
	SimulationOutputVariableBuffer<Double> newB;

	if (b.getSize() == newSize) {
	    newB = b;
	} else {
	    //System.out.println("resizeBuffer() " + b + " - from " + b.getSize() + " to " + newSize);	
	    CVSim.sim.removeVariableRecorder(b);
	    newB = new SimulationOutputVariableBuffer<Double>(traceBufferSize, b);
	    CVSim.sim.addVariableRecorder(newB);
	}
	return newB;
    }

    private void updateTraceBufferSizes() {
	boolean wasRunning = false;

	int oldTraceBufferSize = traceBufferSize;
	calculateTraceBufferSize();
	if (oldTraceBufferSize != traceBufferSize) {
	    //System.out.println("updateTraceBufferSizes() with " + traceBufferSize);
	    //System.out.println(" -> " + (double)java.lang.Math.round((double)traceBufferSize/plot.getBounds().getWidth()*1000) /1000 + " data points per pixel");
	    
	    if (CVSim.simThread.isRunning()) {
		wasRunning = true;
		CVSim.simThread.stop();
	    }
	    
	    if (timeAxisBuffer != null) {
		SimulationOutputVariableBuffer newTime = resizeBuffer((SimulationOutputVariableBuffer)timeAxisBuffer, traceBufferSize);
		timeAxisBuffer = newTime;
	    }
	    
	    for (Trace tr: traceList)
		tr.setXVar(timeAxisBuffer);
	    
	    for (Trace tr: traceList) {
		SimulationOutputVariableBuffer newY = resizeBuffer((SimulationOutputVariableBuffer)tr.getYVar(), traceBufferSize);
		
		tr.setYVar(newY);
	    }
	    
	    if (wasRunning) {
		CVSim.simThread.start();
	    }
	}
    }

    private Dimension getTitleSize() {
	return showTitle ? titleLabel.getSize() : new Dimension(0,0);
    }

    private Dimension getYScaleSize() {
	return showYScale ? yScale.getSize() : new Dimension(0,0);
    }

    private Dimension getXScaleSize() {
	return showXScale ? xScale.getSize() : new Dimension(0,0);
    }

    public ListModel getListModel() { return traceList; }

    public void setTitle(String title) {
	titleLabel.setText(title);
    }
    
    public String getTitle() {
	return titleLabel.getText();
    }
    
    private void updateTraceTransforms() {
	AffineTransform plotTransform = plot.getPlotTransform();
	Rectangle2D plotArea = plot.getScaledPlotBounds();

	Range xrange, yrange;
	Double xdist, ydist;
	
      	for (Trace<?,?> trace: traceList) {
	    AffineTransform at = trace.getTransform();
	    if ((trace.isEnabled()) && (at != null)) {
		// start by setting the trace's transform to the
		//  value of the plot transform
		at.setTransform(plotTransform);

		// prepare scaling and translation values
		xrange = trace.getXDataRange();
		yrange = trace.getYRange();
		xdist = xrange.upper.doubleValue() - xrange.lower.doubleValue();
		ydist = yrange.upper.doubleValue() - yrange.lower.doubleValue();

		// apply additional scaling for the data
 		at.scale(-1/secondsPerUnit, 1/ydist);
 		at.translate(-xrange.upper.doubleValue(), 
 			     -yrange.lower.doubleValue());

	    }
	}
    }

    public void setPaperSpeed(double inchesPerSecond) {
	secondsPerUnit = 1/inchesPerSecond;
	updateTraceBufferSizes();
    }

    // Class that defines a component to Add new traces to the plot
    private class AddStripChartTraceComponent extends JComponent {
	private JComboBox yTraceBox;
	PlotPanelStripChart plotPanel;
	
	AddStripChartTraceComponent(PlotPanelStripChart plot, List<SimulationOutputVariable> vars) {
	    setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
	    plotPanel = plot;

	    yTraceBox = new JComboBox(new SimulationOutputVariableListModel(vars));
	    
	    JButton addButton = new JButton("Add Trace");
	    addButton.setMargin(new Insets(1,2,2,2));
	    addButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			plotPanel.createNewTrace((SimulationOutputVariable)yTraceBox.getSelectedItem());
		    }
		});
	    add(new JLabel("Plot Variable: "));
	    add(yTraceBox);
	    add(addButton);
	}
	
    }

    class PlotMouseListener implements MouseListener {
	public void mouseClicked( MouseEvent e ) {
	    // can only take measurement if sim is running
	    if ( !CVSim.simThread.isRunning() ) {
		PlotPoints point = new PlotPoints(e.getX(), e.getY());
		Point2D screenPt, transPt;
		screenPt = e.getPoint();
		try {
		    if ( getMultipleYScales() ) {
			Trace t0 = traceList.get(0);
			transPt = getScaledPoint(t0.getTransform(), screenPt);
			String str = String.format("%.2f, %.2f", transPt.getX(), transPt.getY());
			point.add(str, t0.getColor());
			for (Trace t: traceList) {
			    transPt = getScaledPoint(t.getTransform(), screenPt);
			    str = String.format("%.2f, %.2f", transPt.getX(), transPt.getY());
			    // don't want duplicate points
			    if ( !(point.getString(0).equals(str)) ) 
				point.add(str, t.getColor());
			}
		    } else {
			transPt = getScaledPoint(traceList.get(0).getTransform(), screenPt);
			String str = String.format("%.2f, %.2f", transPt.getX(), transPt.getY());
			point.add(str, Color.BLACK);
		    }

		    point.setEnabled(true);
		    plot.pointList.add(point);
		    plot.repaint();
		} catch (NoninvertibleTransformException ex) {
		    System.out.println("PlotPanel.java: NoninvertibleTransformException");
		}
	    }
	}	
	public void mousePressed( MouseEvent event) {}	
	public void mouseReleased( MouseEvent event ) {}
	public void mouseEntered( MouseEvent event ) {}
	public void mouseExited( MouseEvent event ) {}
    }
} // end class PlotPanel


