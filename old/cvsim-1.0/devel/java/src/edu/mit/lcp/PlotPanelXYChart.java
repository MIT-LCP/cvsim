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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.List;
import java.util.ArrayList;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

// Custom plotter, extension of JPanel
public class PlotPanelXYChart extends PlotPanel {

    private static final int NUM_TICKS = 5;

    private int traceBufferSize;
    private TraceListModel traceList;
    private JLabel titleLabel;
    private xScaleComponent xScale;
    private JSlider timeSlider;
    //private AddXYChartTraceComponent addComponent;
    private double trailLength;

    public double xMax = 0;
    public double xMin = 0;
    public double yMax = 0;
    public double yMin = 0;
    public boolean showTitle = true;
    public boolean showXScale = false;
    public boolean showYScale = true;
    public boolean multipleXScales = false;
    //public boolean multipleYScales = false;
    public PlotComponent plot;

    public PlotPanelXYChart(TraceListModel model) {
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

	plot = new PlotComponent(traceList, PlotComponent.PARAMETRIC);
	plot.setBorder(new CompoundBorder(new MatteBorder(14,0,14,0, getBackground()), 
					  new LineBorder(Color.BLACK, 1)));

 	// register handler for resize events
  	plot.addComponentListener( new ComponentAdapter() {
  		public void componentResized(ComponentEvent e) {
		    updateTraceBufferSizes();
 		}
  	    });

	plot.addMouseListener(new PlotMouseListener());
	
	// Add scales
	yScale = new yScaleComponent(traceList, 15, NUM_TICKS, this);
	xScale = new xScaleComponent(traceList, 15, NUM_TICKS, this);

	List<SimulationOutputVariable> tmpVarList = 
	    new ArrayList<SimulationOutputVariable>(CVSim.sim.getOutputVariables());
 	tmpVarList.remove(CVSim.sim.getOutputVariable("TIME"));
	final JComboBox xTraceBox = new JComboBox(new SimulationOutputVariableListModel(tmpVarList));
	final JComboBox yTraceBox = new JComboBox(new SimulationOutputVariableListModel(tmpVarList));
	xTraceBox.setSelectedIndex(2);

	JButton addButton = new JButton("Add Trace");
	addButton.setMargin(new Insets(1,2,2,2));
	addButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    createNewTrace((SimulationOutputVariable)xTraceBox.getSelectedItem(), 
				   (SimulationOutputVariable)yTraceBox.getSelectedItem());
		}
	    });
	
	timeSlider = new JSlider(1,60,30);
	timeSlider.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
			System.out.println("New traillength: " + (double)source.getValue());
			setTrailLength((double)source.getValue());
		    } 
		} 
	    }); 
	
	TitledBorder titledBorder = BorderFactory.createTitledBorder(
	    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
	    "Time History");
	titledBorder.setTitleJustification(TitledBorder.CENTER);
	timeSlider.setBorder(titledBorder);

	JCheckBox multipleXScalesCheckBox = new JCheckBox("Multiple X Scales");
	multipleXScalesCheckBox.addItemListener(MultipleXScalesListener);

	JPanel controlPanel = new JPanel(new FlowLayout());
	controlPanel.add(new JLabel("X:"));
	controlPanel.add(xTraceBox);
	controlPanel.add(new JLabel("Y:"));
	controlPanel.add(yTraceBox);
	controlPanel.add(addButton);
	controlPanel.add(timeSlider);
	controlPanel.add(multipleXScalesCheckBox);
	controlPanel.add(multipleYScalesCheckBox);
	controlPanel.setPreferredSize(new Dimension(100,85));

	// layout
	this.setLayout(new BorderLayout());
	add(BorderLayout.PAGE_START, controlPanel);
	add(BorderLayout.PAGE_END, xScale);
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

	// Set default trail length, and generate a default trace
	setTrailLength((double)30.0);
	calculateTraceBufferSize();
    } // end constructor 
    
    public boolean getMultipleXScales() { return multipleXScales; } 
    
    public void setMultipleXScales(boolean newValue) { 
	boolean oldValue = multipleXScales; 
	multipleXScales = newValue; 
	xScale._changes.firePropertyChange(xScale.PROP_MULTISCALES, oldValue, newValue);
    }
    
    private ItemListener MultipleXScalesListener = new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if ( ((JCheckBox)e.getSource()).isSelected() ) 
		    setMultipleXScales(true);
		else {
		    setMultipleXScales(false);
		}
	    }
	};

     public void setMultipleYScales(boolean newValue) { 
 	boolean oldValue = getMultipleYScales(); 
 	multipleYScales = newValue; 
 	yScale._changes.firePropertyChange(yScale.PROP_MULTISCALES, oldValue, newValue);
	xScale.repaint();
     }
    
    public yScaleComponent getYScale() { return yScale; } 
    public PlotComponent getPlot() { return plot; }

    public Dimension getPreferredSize() {
	return getMinimumSize();
    }

    public Dimension getMinimumSize() {
	return new Dimension(yScale.getPreferredSize().width, yScale.getPreferredSize().width*8);
    }

    public void createNewTrace(SimulationOutputVariable xvar, SimulationOutputVariable yvar) {
	//System.out.println("createNewTrace(" + xvar + "," + yvar +")");
	VariableRecorderInterface<Double> x =  new SimulationOutputVariableBuffer<Double>(traceBufferSize, xvar, new Double(0.0));
	VariableRecorderInterface<Double> y =  new SimulationOutputVariableBuffer<Double>(traceBufferSize, yvar, new Double(0.0));
	
	Trace newTrace = new Trace<Double,Double>(x, y, traceList.getNextColor());
	addTrace(newTrace);
    }
    
    public void addTrace(Trace<?,?> t) {
	//System.out.println("addTrace(" + t + ")");
	traceList.add(t);
	CVSim.sim.addVariableRecorder(t.getXVar());
	CVSim.sim.addVariableRecorder(t.getYVar());
    }

    public void removeTrace(Trace<?,?> t) {
	//System.out.println("removeTrace(" + t + ")");
	traceList.remove(t);
	CVSim.sim.removeVariableRecorder(t.getXVar());
	CVSim.sim.removeVariableRecorder(t.getYVar());
    }

    public void removeAllTraces() {
	//System.out.println("removeAllTraces()");
	for (Trace t: traceList)
	    removeTrace(t);
    }

    private int calculateTraceBufferSize() {
	int stepSize = CVSim.sim.getDataCompressionFactor();
	traceBufferSize = (int)(1000*trailLength)/(stepSize);
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
	    
	    if (CVSim.simThread.isRunning()) {
		wasRunning = true;
		CVSim.simThread.stop();
	    }
	    	    
	    for (Trace tr: traceList) {
		SimulationOutputVariableBuffer newX = resizeBuffer((SimulationOutputVariableBuffer)tr.getXVar(), traceBufferSize);
		SimulationOutputVariableBuffer newY = resizeBuffer((SimulationOutputVariableBuffer)tr.getYVar(), traceBufferSize);
		
		tr.setXVar(newX);
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
		xrange = trace.getXRange();
		yrange = trace.getYRange();
		xdist = xrange.upper.doubleValue() - xrange.lower.doubleValue();
		ydist = yrange.upper.doubleValue() - yrange.lower.doubleValue();

		// apply additional scaling for the data
 		at.scale(1/xdist, 1/ydist);
 		at.translate(-xrange.lower.doubleValue(), 
 			     -yrange.lower.doubleValue());

	    }
	}
    }

    public void setTrailLength(double seconds) {
	trailLength = seconds;
	updateTraceBufferSizes();
    }

    // Class that defines a component to Add new traces to the plot
//     private class AddXYChartTraceComponent extends JComponent {
//  	private JComboBox xTraceBox;
//  	private JComboBox yTraceBox;
// 	PlotPanelXYChart plotPanel;
	
//  	AddXYChartTraceComponent(PlotPanelXYChart plot, List<SimulationOutputVariable> vars) {
// 	    setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
// 	    plotPanel = plot;
	    
// 	    xTraceBox = new JComboBox(new SimulationOutputVariableListModel(vars));
// 	    yTraceBox = new JComboBox(new SimulationOutputVariableListModel(vars));
	    
// 	    JButton addButton = new JButton("Add Trace");
// 	    addButton.setMargin(new Insets(1,2,2,2));
// 	    addButton.addActionListener(new ActionListener() {
// 		    public void actionPerformed(ActionEvent e) {
// 			plotPanel.createNewTrace((SimulationOutputVariable)xTraceBox.getSelectedItem(), (SimulationOutputVariable)yTraceBox.getSelectedItem());
// 		    }
// 		});
// 	    add(new JLabel("Y:"));
// 	    add(yTraceBox);
// 	    add(new JLabel("X:"));
// 	    add(xTraceBox);
// 	    add(addButton);
// 	}
//     }

    class PlotMouseListener implements MouseListener {
	public void mouseClicked( MouseEvent e ) {
	    // can only take measurement if sim is running
	    if ( !CVSim.simThread.isRunning() ) {
		PlotPoints point = new PlotPoints(e.getX(), e.getY());
		Point2D screenPt, transPt;
		screenPt = e.getPoint();
		try {
		    if ( getMultipleXScales() || getMultipleYScales() ) {
			Trace t0 = traceList.get(0);
			transPt = getScaledPoint(t0.getTransform(), screenPt);
			String str = String.format("%.2f, %.2f", transPt.getX(), transPt.getY());
			point.add(str, t0.getColor());
			for (Trace t: traceList) {
			    transPt = getScaledPoint(t.getTransform(), screenPt);
			    str = String.format("%.2f, %.2f", transPt.getX(), transPt.getY());
			    // don't want duplicate measurements
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


