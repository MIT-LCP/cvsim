package edu.mit.lcp;

import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

//import java.lang.Math;

public class PlotComponent extends JComponent {

    public static final int PARAMETRIC = 1;
    public static final int STRIPCHART = 2;

    public final static int SCALE_X_DPI  = 1 << 0;
    public final static int SCALE_Y_DPI  = 1 << 1;

    private static final int NUM_TICKS = 5;
    private TraceListModel traceList;    
    private Rectangle bounds;
    private AffineTransform plotTransform;
    private double _deltaY;
    private double _deltaX;

    private boolean showXGridlines = false;
    private boolean showYGridlines = false;
    

    private int _flags;
    private int DPI;
    private Rectangle2D.Double scaledBounds;
    public List<PlotPoints> pointList = new ArrayList<PlotPoints>(); 
    private int _plotType;

    public PlotComponent(TraceListModel model, int plotType) {
	this(model, 0, plotType);
    }

    public PlotComponent(TraceListModel model, int flags, int plotType) {
	// store local reference to data model holding the traces
	traceList = model;
	_flags = flags;
	_plotType = plotType;

	DPI = Toolkit.getDefaultToolkit().getScreenResolution();

	//System.out.println("PlotComponent() - "+DPI+" DPI");
	plotTransform = new AffineTransform();
	scaledBounds = new Rectangle2D.Double(0,0,1,1);

 	// register handler for resize events
  	addComponentListener( new ComponentAdapter() {
  		public void componentResized(ComponentEvent e) {
		    updateBounds();
		    repaint();
 		}
  	    });

	setOpaque(true);
    }

    public TraceListModel getListModel() { 
	return traceList;
    }

    public void setListModel(TraceListModel listModel) { 
	traceList = listModel;
    }

    public AffineTransform getPlotTransform() {
	return plotTransform;
    }
    
    public void setShowXGridlines(boolean b) {
	showXGridlines = b;
    }
    
    public void setShowYGridlines(boolean b) {
	showYGridlines = b;
    }

    public double getDeltaX() {
	return _deltaX;
    }

    public double getDeltaY() {
	return _deltaY;
    }

    private void updatePlotWindowTransform() {
	switch (_plotType) {
	
	case PARAMETRIC: 
	    plotTransform.setToIdentity();
	    // set bottom right corner as origin
	    plotTransform.translate(bounds.x, bounds.height+bounds.y);
	    // make y grow in up direction
	    plotTransform.scale(1,-1);
	    // scale veiwport to 1x1
	    plotTransform.scale(bounds.width/scaledBounds.getWidth(), 
				bounds.height/scaledBounds.getHeight());
	    break;
	 
	case STRIPCHART:
	    plotTransform.setToIdentity();
	    // set bottom right corner as origin
	    plotTransform.translate(bounds.width+bounds.x, bounds.height+bounds.y);
	    // make y grow in up direction
	    plotTransform.scale(-1,-1);
	    // scale veiwport to 1x1
	    plotTransform.scale(bounds.width/scaledBounds.getWidth(), 
				bounds.height/scaledBounds.getHeight());
	    break;

	default: throw new UnsupportedOperationException();
        }

    }
   
    protected void paintComponent(Graphics g) {
	//long startTime = System.nanoTime();

	// draw
	updateBounds();
 	Graphics2D g2d = (Graphics2D)g.create();
	g2d.clipRect(bounds.x, bounds.y, bounds.width, bounds.height);

	drawBackground(g2d);
	drawGridlines(g2d);
 	drawTraces(g2d);
	drawPoints(g2d);

	//long endTime = System.nanoTime();
	//long duration = endTime-startTime;
	//System.out.println("PlotComponent.paintComponent(): " + (double)duration/1000000 + " ms");
    }

    // paint background
    private void drawBackground(Graphics2D g2d) {
	// draw plot background (white filled rectangle)
	g2d.setColor(Color.WHITE);
	g2d.fillRect(bounds.x, bounds.y, bounds.width,bounds.height);
    }

    // paint traces on top of the plot background
    private void drawTraces(Graphics2D g2d) {

	// draw every trace that has been added to the plot
  	for (Trace<?,?> trace: traceList) {
	    if (trace.isEnabled()) {
		g2d.setColor(trace.getColor());
		g2d.setStroke(trace.getStroke());
		g2d.draw(trace);
	    }
	}
    }

    private void drawPoints(Graphics2D g2d) {

	if (CVSim.simThread.isRunning()) {
	    pointList.clear();
	} else {
	    int ypad = 15;
	    int xpad = 10;

	    RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, 
						      RenderingHints.VALUE_ANTIALIAS_ON); 
	    g2d.setRenderingHints(hints);

	    for (PlotPoints points: pointList) {
		if ( points.isEnabled() ) {
		    g2d.setColor(Color.BLACK);
		    Shape shape = (Shape)(new Ellipse2D.Float(points.getX(), points.getY(), 5, 5)); 
		    g2d.draw(shape);
		    g2d.fill(shape);
		    
		    for (int i=0; i < points.getSize(); i++) {
			g2d.setColor(points.getColor(i));
			g2d.drawString(points.getString(i), 
				       points.getX() + xpad, 
				       points.getY() - i*ypad);
		    }
		}
	    }
	}
    }

    private void drawGridlines(Graphics2D g2d) {
	AffineTransform transform = new AffineTransform();
	_deltaX = bounds.getWidth()/NUM_TICKS;
	_deltaY = bounds.getHeight()/NUM_TICKS;

	// reset the transform
	transform.setToIdentity();
	// find bottom left corner as starting point
	transform.translate(bounds.x,bounds.height+bounds.y);
	// make y grow in up direction
	transform.scale(1,-1);
	// scale to data space, whole window
	transform.scale(_deltaX, _deltaY);

	// define paths
	GeneralPath gridPath = new GeneralPath();
	GeneralPath ytickPath = new GeneralPath();
	GeneralPath xtickPath = new GeneralPath();
	for (int i=1; i < NUM_TICKS; i++) {
	    gridPath.moveTo(0, i);
	    gridPath.lineTo(NUM_TICKS, i);
	    
	    ytickPath.moveTo(0, i);
 	    ytickPath.lineTo(0.1f, (float)i );

	    xtickPath.moveTo(i, 0);
 	    xtickPath.lineTo((float)i, 0.1f );

	}

	g2d.setPaint(Color.LIGHT_GRAY);
	g2d.draw(transform.createTransformedShape(gridPath));
 	g2d.draw(transform.createTransformedShape(xtickPath));
 	//g2d.draw(transform.createTransformedShape(ytickPath));
    }

    private void updateBounds() {
	Insets border = getInsets();
	bounds = getBounds();
	bounds.x = border.left;
	bounds.y = border.top;
	bounds.width -= border.left + border.right;
	bounds.height -= border.top + border.bottom;

	if ((_flags & SCALE_X_DPI) == SCALE_X_DPI)
	    scaledBounds.width = bounds.getWidth()/DPI;
	if ((_flags & SCALE_Y_DPI) == SCALE_Y_DPI)
	    scaledBounds.height = bounds.getHeight()/DPI;

	updatePlotWindowTransform();
    }

    public Rectangle getPlotBounds() {
	return bounds;
    }

    public Rectangle2D getScaledPlotBounds() {
	return scaledBounds;
    }

  
}
