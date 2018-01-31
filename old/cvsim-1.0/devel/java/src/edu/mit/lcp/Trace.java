package edu.mit.lcp;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.geom.Point2D;
import java.util.Iterator;

import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

// Trace.java
// Contains trace data in the form of x,y points
// Also contains the color of the trace, the name of the x and y data,
// and the units of the x and y data

public class Trace<xVarType extends Number & Comparable<xVarType>,
				    yVarType extends Number & Comparable<yVarType>> 
    implements Iterable<Point2D.Double>, Shape {

    public static final String PROP_COLOR = "COLOR";
    public static final String PROP_STROKE = "STROKE";
    public static final String PROP_ENABLED = "ENABLED";
    public static final String PROP_XRANGE = "XRANGE";
    public static final String PROP_YRANGE = "YRANGE";

    private boolean _enabled;
    private Color _color;
    private Stroke _stroke;
    private AffineTransform transform;
    private VariableRecorderInterface<xVarType> xVar;
    private VariableRecorderInterface<yVarType> yVar;
    private Range xRange;
    private Range yRange;
    private PropertyChangeSupport _changes = new PropertyChangeSupport(this);

    public Trace(VariableRecorderInterface xvar, VariableRecorderInterface yvar,
		 Color color) { 
	this(xvar, yvar, color, new BasicStroke());
    }

    public Trace(VariableRecorderInterface xvar, VariableRecorderInterface yvar, 
		 Color color, Stroke stroke) { 
	xVar = xvar;
	yVar = yvar;
	_color = color;
	_stroke = stroke;
	transform = new AffineTransform();
	_enabled = true;

	xRange = new Range<xVarType>( xVar.getTypicalRange() );
	yRange = new Range<yVarType>( yVar.getTypicalRange() );
    }
    
    public void setColor(Color newColor) {
	Color oldColor = _color;
	_color = newColor; 
	_changes.firePropertyChange(PROP_COLOR, oldColor, newColor);
    }
    public Color getColor() { return _color; }
    
    public void setStroke(Stroke newStroke) { 
	Stroke oldStroke = _stroke;
	_stroke = newStroke;
	_changes.firePropertyChange(PROP_STROKE, oldStroke, newStroke);
    }
    public Stroke getStroke() { return _stroke; }

    public void setTransform(AffineTransform at) { transform = at; }
    public AffineTransform getTransform() { return transform; }

    public void setEnabled(boolean b) {
	boolean oldEnabled = _enabled;
	_enabled = b;
	_changes.firePropertyChange(PROP_ENABLED, oldEnabled, b);
    }
    public boolean isEnabled() { return _enabled; }

    public String getXName() { return xVar.getName(); }
    public String getYName() { return yVar.getName(); }    
    public String getXDescription() { return xVar.getDescription(); }
    public String getYDescription() { return yVar.getDescription(); }    
    public String getXUnits() {	return xVar.getUnits(); }
    public String getYUnits() {	return yVar.getUnits(); }

    public Range<xVarType> getXRange() {
	return xRange;
    }
    public Range<yVarType> getYRange() {
	return yRange;
    }
    public Range<xVarType> getXDataRange() {
	return new Range<xVarType>(xVar.getMinVal(),xVar.getMaxVal());
    }
    public Range<yVarType> getYDataRange() {
	return new Range<yVarType>(yVar.getMinVal(),yVar.getMaxVal());
    }

    public void setXRange(Range<xVarType> newRange) {
	Range oldRange = xRange;
	xRange.setBounds(newRange);
	_changes.firePropertyChange(PROP_XRANGE, oldRange, newRange);
    }
    public void setYRange(Range<yVarType> newRange) {
	Range oldRange = yRange;
	yRange.setBounds(newRange);
	_changes.firePropertyChange(PROP_YRANGE, oldRange, newRange);
    }

    public void resetRange() {
	setXRange( xVar.getTypicalRange() );
	setYRange( yVar.getTypicalRange() );
    }

    public VariableRecorderInterface getXVar() { return xVar; }
    public VariableRecorderInterface getYVar() { return yVar; }
    public void setXVar(VariableRecorderInterface newX) { xVar = newX; }
    public void setYVar(VariableRecorderInterface newY) { yVar = newY; }

    public int getNumPoints() {
	// pick the data source with the fewest points
	if (xVar.getSize() < yVar.getSize() )
	    return xVar.getSize();
	else
	    return yVar.getSize();
    }
    
    // Override Object.toString() to report useful information
    public String toString() {
	return xVar + " / " + yVar;
    }
    

    ///////////////////////////////
    // Support for property changes
    /**
     * The specified PropertyChangeListeners propertyChange method will
     * be called each time the value of any bound property is changed.
     * The PropertyListener object is addded to a list of PropertyChangeListeners
     * managed by this button, it can be removed with removePropertyChangeListener.
     * Note: the JavaBeans specification does not require PropertyChangeListeners
     * to run in any particular order.
     *
     * @see #removePropertyChangeListener
     * @param l the PropertyChangeListener
     */      
    public void addPropertyChangeListener(PropertyChangeListener l) {
        _changes.addPropertyChangeListener(l);
    }
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	_changes.addPropertyChangeListener(propertyName, listener);
    }

    /** 
     * Remove this PropertyChangeListener from the buttons internal list.  
     * If the PropertyChangeListener isn't on the list, silently do nothing.
     * 
     * @see #addPropertyChangeListener
     * @param l the PropertyChangeListener
     */      
    public void removePropertyChangeListener(PropertyChangeListener l) {
        _changes.removePropertyChangeListener(l);
    }


    ////////////////////////////////
    // Public Interface for Iterable
    //
    public Iterator<Point2D.Double> iterator() {
	return new PairedIterator<xVarType,yVarType>(xVar.iterator(), yVar.iterator());
    }

    // Here a custom iterator is implemented.  It creates a Point2D
    // based on the two data sources. It allows iterating over both
    // data sources simultaneously. Some type of synchronization
    // should be attempted to ensure that the iterators returned from
    // each data source are pointing to the respective matching data
    // point in the other source. This would need to be done in the
    // VariableRecorderInterface classes and utilized here.
    private class PairedIterator<xVarType extends Number,yVarType extends Number>
	implements Iterator<Point2D.Double> {
	
	private Point2D.Double point;
	private Iterator<xVarType> _xIter;
	private Iterator<yVarType> _yIter;

	public PairedIterator(Iterator<xVarType> xIter, Iterator<yVarType> yIter) {
	    _xIter = xIter;
	    _yIter = yIter;
	    point = new Point2D.Double();
	}
	
	// hasNext() is true only if both have more
	public boolean hasNext() {
	    return ( _xIter.hasNext() && _yIter.hasNext() );
	}

	// Create a Point2D and return the (x,y) pair
	public Point2D.Double next() {
	    point.setLocation(_xIter.next().doubleValue(),_yIter.next().doubleValue());
	    return point;
	}

	// The remove method is not implemented
	public void remove() { throw new UnsupportedOperationException(); }
    }

    ////////////////////////////////
    // Public Interface for Shape
    //
    
    public boolean contains(double x, double y) {
	if ( (xVar.getMinVal().doubleValue() <= x) && 
	     (xVar.getMaxVal().doubleValue() >= x) && 
	     (yVar.getMinVal().doubleValue() <= y) &&
	     (yVar.getMaxVal().doubleValue() >= y) )
	    return true;
	else
	    return false;
    }

    public boolean contains(Point2D p) {
	return contains(p.getX(), p.getY());
    }

    public boolean contains(double x, double y, double w, double h) {
	if ( (xVar.getMinVal().doubleValue() <= x)   &&
	     (xVar.getMinVal().doubleValue() <= x+w) && 
	     (xVar.getMaxVal().doubleValue() >= x)   &&
	     (xVar.getMaxVal().doubleValue() >= x+w) && 
	     (yVar.getMinVal().doubleValue() <= y)   &&
	     (yVar.getMinVal().doubleValue() <= y+h) &&
	     (yVar.getMaxVal().doubleValue() >= y)   && 
	     (yVar.getMaxVal().doubleValue() >= y+h) )
	    return true;
	else
	    return false;
    }

    public boolean contains(Rectangle2D r) {
	int minRelLoc = r.outcode(xVar.getMinVal().doubleValue(),
				  yVar.getMinVal().doubleValue());
	int maxRelLoc = r.outcode(xVar.getMaxVal().doubleValue(), 
				  yVar.getMaxVal().doubleValue());
	return ( ((minRelLoc & (Rectangle2D.OUT_BOTTOM | Rectangle2D.OUT_LEFT )) != 0) &&
		 ((maxRelLoc & (Rectangle2D.OUT_TOP    | Rectangle2D.OUT_RIGHT)) != 0) );
    }

    public Rectangle getBounds() {
	// x,y,w,h
	return new Rectangle( xVar.getMinVal().intValue(), 
			      yVar.getMinVal().intValue(),
			      xVar.getMaxVal().intValue()-xVar.getMinVal().intValue(),
			      yVar.getMaxVal().intValue()-yVar.getMinVal().intValue() );
    }

    public Rectangle2D getBounds2D() {
	// x,y,w,h
	return new Rectangle2D.Double( xVar.getMinVal().doubleValue(), 
				       yVar.getMinVal().doubleValue(),
				       xVar.getMaxVal().doubleValue()-
				       xVar.getMinVal().doubleValue(),
				       yVar.getMaxVal().doubleValue()-
				       yVar.getMinVal().doubleValue() );
    }

    public PathIterator getPathIterator(AffineTransform at) {
	return new TracePathIterator(at);
    }

    public PathIterator getPathIterator(AffineTransform at, double flatness) {
	return getPathIterator(at);
    }

    public boolean intersects(double x, double y, double w, double h) {
	return ( contains(x,y) || contains(x,y+h) || 
		 contains(x+w,y) || contains(x+w,y+h) );
    }
 
    public boolean intersects(Rectangle2D r) {
	// x,y,w,h
	return r.intersects( xVar.getMinVal().doubleValue(), yVar.getMinVal().doubleValue(),
			     xVar.getMaxVal().doubleValue()-xVar.getMinVal().doubleValue(),
			     yVar.getMaxVal().doubleValue()-yVar.getMinVal().doubleValue() );
    }


    private class TracePathIterator implements PathIterator {
	private AffineTransform _localAT;
	private Iterator<Point2D.Double> _traceIterator;
	private Point2D.Double _curPoint;
	private int _curSegType;

	TracePathIterator(AffineTransform at) {
	    // if no default transform, use the supplied one, else
	    // combine them together
	    _localAT = transform;
	    if (_localAT == null) { _localAT = at; }
	    else if (at != null) {
		_localAT = new AffineTransform(transform);
		_localAT.concatenate(at); 
	    }
	    // "qualified" this, referring to enclosing class
	    _traceIterator = Trace.this.iterator(); 
	    _curPoint = _traceIterator.next();
	    _curSegType = PathIterator.SEG_MOVETO;
	}
	
	public int currentSegment(double[] coords) {
	    coords[0] = _curPoint.getX();
	    coords[1] = _curPoint.getY();
	    // srcCoords, srcOffset, dstCoords, dstOffset, numPts
	    if (_localAT != null) _localAT.transform(coords, 0, coords, 0, 1);
	    return _curSegType;
	}
	
	public int currentSegment(float[] coords) {
	    coords[0] = (float)_curPoint.getX();
	    coords[1] = (float)_curPoint.getY();
	    // srcCoords, srcOffset, dstCoords, dstOffset, numPts
	    if (_localAT != null) _localAT.transform(coords, 0, coords, 0, 1);
	    return _curSegType;
	}
	
	public int getWindingRule() {
	    return PathIterator.WIND_NON_ZERO;
	}
	
	public boolean isDone() {
	    return !(_traceIterator.hasNext());
	}
	
	public void next() {
	    _curPoint = _traceIterator.next();
	    _curSegType = PathIterator.SEG_LINETO;
	}
    }
}
