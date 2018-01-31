package edu.mit.lcp;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Insets;
import java.awt.event.*;
import java.util.*;
import java.lang.Math;
import java.awt.geom.Point2D;
import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.image.VolatileImage;
import javax.swing.SwingConstants;
import javax.swing.border.*;
import java.text.NumberFormat;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JCheckBox;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class yScaleComponent extends JComponent {
    private TraceListModel traceList;
    private List<Scale> _scaleList;
    private int _padding;
    private int _numTicks;
    private PlotPanel _plotPanel;
    private PlotComponent _plot;
    private double min = 0;
    private double max = 0;
    public static final String PROP_MULTISCALES = "MULTISCALES";
    public PropertyChangeSupport _changes = new PropertyChangeSupport(this);
    
    public yScaleComponent(TraceListModel listModel, int pad, int numTicks, PlotPanel plotPanel) {
	//System.out.println("yScaleComponent()");
	_scaleList = new ArrayList<Scale>();
	traceList = listModel;
	_padding = pad;
	_numTicks = numTicks;
	_plotPanel = plotPanel;
	_plot = plotPanel.getPlot();

	this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

	// populate scale list with existing traces
	for (Trace trace: traceList) {
	    Scale s = new Scale(trace);
	    _scaleList.add(s);
	    s.setBorder(new LineBorder(Color.RED, 1));
	    add(s);
	}

	traceList.addListDataListener( new ListDataListener() {
		public void intervalAdded(ListDataEvent e) {	
		    Trace t = traceList.getElementAt(e.getIndex0());
		    // update global min and max
		    double traceMin =  t.getYRange().lower.doubleValue();
		    double traceMax =  t.getYRange().upper.doubleValue();
		    if ( traceMin <  min)
			min = traceMin;
		    if ( traceMax > max) 
			max = traceMax;
		    // if using multiple scales or if this is the first trace,
		    // add a new scale.
		    if ( _plotPanel.getMultipleYScales() || (_scaleList.size() == 0) ) {
			Scale s = new Scale(t);
			_scaleList.add(s);
			add(s);
		    } else {
			// update ranges but don't add a new scale
			_scaleList.get(0).setRange(new Range(min, max));	
			for (Trace trace: traceList) {
			    trace.setYRange(new Range(min, max));
			} 
		    }
		}		

		public void intervalRemoved(ListDataEvent e) {
		    // if using multiple scales or 
		    // using single scale but removing last trace,
		    // remove scale
		    if ( ( _plotPanel.getMultipleYScales() ) ||
			 ( !_plotPanel.getMultipleYScales() && (traceList.size() == 0) ) ) {
			Scale s = _scaleList.get(e.getIndex0());
			_scaleList.remove(s);
			remove(s);
		    }
		}
		public void contentsChanged(ListDataEvent e){}
	    });    
				       
	this.addPropertyChangeListener(PROP_MULTISCALES, new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent pce) {
		    // switch to multiple scales 
		    if ( (Boolean)pce.getNewValue() ) {
			// remove existing scale then re-add
			Scale s0 = _scaleList.get(0);
			s0.setVisible(false);
			_scaleList.remove(s0);
			
			for (Trace t: traceList) {
			    Scale s = new Scale(t);
			    _scaleList.add(s);
			    add(s);
			    s.repaint();
			}
		    } else { // switch to single scale
			Scale s0 = _scaleList.get(0);
			// remove all scales but one
			while ( _scaleList.size() > 1 ) {
			    Scale scale = _scaleList.get(_scaleList.size()-1);
			    // update range
			    double scaleMin = scale.getRange().lower.doubleValue();
			    double scaleMax = scale.getRange().upper.doubleValue();
			    if ( scaleMin < min )
				min = scaleMin;
			    if ( scaleMax > max )
				max = scaleMax;
			    s0.setRange(new Range(min, max));
			    // remove
			    scale.setVisible(false);			
			    _scaleList.remove(scale);
			}
			s0.setColor(Color.BLACK);
			// update trace ranges
			for (Trace trace: traceList) {
			    trace.setYRange(new Range(min, max));
			} // for
		    } // else
		} // propertyChange()
	    });
    }
	
    public Dimension getPreferredSize() {
	return getMinimumSize();
    }
    public Dimension getMinimumSize() {
	int x=0;
	int y=Integer.MAX_VALUE;
	Insets border = getInsets();

	for (Scale scale: _scaleList) {
	    x += scale.getPreferredSize().width;
	}
	x += border.left + border.right;
	return new Dimension(x,y);
    }
 

    //////////////////////////////
    private class Scale extends JComponent {
	private int SCALE_WIDTH = 10;
	private int SCALE_LABEL_SPACING = 2;
	private int MAJOR_TICK_WIDTH = 10;
	private int MINOR_TICK_WIDTH = 5;

	private JLabel ylabel;
	
	private Font rangeFont = new Font("Serif", Font.PLAIN, 10);
	private Font labelFont = new Font("Serif", Font.PLAIN, 12);
	private JFormattedTextField minField;
	private JFormattedTextField maxField;

	private Color color;
	private List<JFormattedTextField> textfields;
	private int maxScaleLabelWidth;
	private Rectangle _bounds;
	private Trace trace;

	private double scaleMin;
	private double scaleMax;

	NumberFormat doubleFormat = NumberFormat.getNumberInstance();
	
	// Add Support for Property Changes
	private PropertyChangeSupport _changes = new PropertyChangeSupport(this);
	public static final String VALUE = "value";

	Scale(Trace t) {
	    trace = t;
	    textfields = new ArrayList<JFormattedTextField>();
	    if ( _plotPanel.getMultipleYScales() )
		color = trace.getColor();
	    else
		color = Color.BLACK;
	    scaleMin = trace.getYRange().lower.doubleValue();
	    scaleMax = trace.getYRange().upper.doubleValue();
	    setRange(scaleMin, scaleMax);
	    
	    ylabel = new JLabel();
	    Border empty = BorderFactory.createEmptyBorder(5,5,5,5);

	    minField = new JFormattedTextField(doubleFormat);
	    minField.setValue(scaleMin);
	    minField.setColumns(2);
	    minField.setFont(rangeFont);
	    minField.setForeground(color);
	    minField.setHorizontalAlignment(SwingConstants.RIGHT);
	    add(minField);
	    textfields.add(minField);
 	    
	    // remaining labels
	    double span = scaleMax - scaleMin;
	    double delta = span / _numTicks;
	    for (int i=1; i < _numTicks; i++) {
		JFormattedTextField tf = new JFormattedTextField();
		tf.setFont(rangeFont);
		tf.setForeground(color);
		tf.setHorizontalAlignment(SwingConstants.RIGHT);
		tf.setValue(scaleMin + delta*i);
		tf.setColumns(2);
		tf.setEditable(false);
		tf.setBorder(empty);
		add(tf);
		textfields.add(tf);
	    }

	    maxField = new JFormattedTextField(doubleFormat);
	    maxField.setValue(scaleMax);
	    maxField.setColumns(2);
	    maxField.setFont(rangeFont);
	    maxField.setForeground(color);
	    maxField.setHorizontalAlignment(SwingConstants.RIGHT);
	    add(maxField);
	    textfields.add(maxField);

	    minField.addPropertyChangeListener(VALUE, new PropertyChangeListener() {
		    public void propertyChange(PropertyChangeEvent pce) {
			scaleMin = ((Number)minField.getValue()).doubleValue();
			// Can't do this because we want the smallest scale min now, not the smallest 
			// scale min ever. For instance, if someone wants to enter a min of -100
			// but mistakenly types -1000 first, the -1000 will be stored as the global min
			//if (scaleMin < min)
			//    min = scaleMin;
			min = scaleMin;
			for (Scale scale: _scaleList) {
			    if ( scale.getRange().lower.doubleValue() < scaleMin )
				min = scale.getRange().lower.doubleValue();
			}		
			// if using multiple scales, adjust range on associated trace only
			if ( _plotPanel.getMultipleYScales() )
			    trace.setYRange(new Range(scaleMin, scaleMax));
			else { // if using single scale, adjust range on all traces
			    for (Trace t: traceList)
				t.setYRange(new Range(scaleMin, t.getYRange().upper.doubleValue()));
			}
			updateComponent();
		    }
		});

	    maxField.addPropertyChangeListener(VALUE, new PropertyChangeListener() {
		    public void propertyChange(PropertyChangeEvent pce) {
			scaleMax = ((Number)maxField.getValue()).doubleValue();
			// Can't do this because we want the largest scale max now, not the largest 
			// scale max ever. For instance, if someone wants to enter a max of 100
			// but mistakenly types 1000 first, the 1000 will be stored as the global max
			//if (scaleMax > max) 
			//    max = scaleMax;
			max = scaleMax;
			for (Scale scale: _scaleList) {
			    if ( scale.getRange().upper.doubleValue() > scaleMax )
				max = scale.getRange().upper.doubleValue();
			}
			// if using multiple scales, adjust range on associated trace only
			if ( _plotPanel.getMultipleYScales() ) {
			    trace.setYRange(new Range(scaleMin, scaleMax));
			} else { // if using single scale, adjust range on all traces
			    for (Trace t: traceList)
				t.setYRange(new Range(t.getYRange().lower.doubleValue(), scaleMax));
			}
			updateComponent();
		    }
		});


	    trace.addPropertyChangeListener(Trace.PROP_YRANGE, new PropertyChangeListener() { 
		    public void propertyChange(PropertyChangeEvent pce) {
			Range range = (Range)pce.getNewValue();
			setRange(range);
			scaleMin = range.lower.doubleValue();
			scaleMax = range.upper.doubleValue();
			if (scaleMin < min)
			    min = scaleMin;
			if (scaleMax > max) 
			    max = scaleMax;
			updateComponent();
		    }
		});

	    trace.addPropertyChangeListener(Trace.PROP_COLOR, new PropertyChangeListener() {
		    public void propertyChange(PropertyChangeEvent pce) {
			color = (Color)pce.getNewValue();
			//updateComponent();
			for (int i=0; i < _numTicks+1; i++) {
			    JFormattedTextField tf = textfields.get(i);
			    tf.setForeground(color);
			}
			repaint();
		    }
		});
	    
	    trace.addPropertyChangeListener(Trace.PROP_ENABLED, new PropertyChangeListener() {
		    public void propertyChange(PropertyChangeEvent pce) {
			setVisible((Boolean)pce.getNewValue());
			updateComponent();
		    }
		});

	    // register handler for resize events
	    this.addComponentListener( new ComponentAdapter() {
		    public void componentResized(ComponentEvent e) {
			updateComponent();
		    }
		});
	    
	    // need to call updateComponent; Otherwise, the scale won't be the proper width at first
	    updateComponent();

	}


	public void setRange(Range range) {
	    setRange(range.lower.doubleValue(), range.upper.doubleValue());
	}
	public void setRange(double min, double max) {
	    scaleMin = min;
	    scaleMax = max;
	}
	public Range getRange() {
	    return new Range(scaleMin, scaleMax);
	}

	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    updateComponent();
	    Graphics2D g2d = (Graphics2D)g.create();
	    g2d.setColor(color);
// 	    g2d.drawLine(_bounds.x + _bounds.width - MAJOR_TICK_WIDTH,
// 			 _bounds.y,
// 			 _bounds.x + _bounds.width - MAJOR_TICK_WIDTH,
// 			 _bounds.y + _bounds.height);
// 	    for (int y=(_bounds.y+ _bounds.height); y>_bounds.y; y=y-5)
// 		g2d.drawLine(_bounds.x+_bounds.width-MAJOR_TICK_WIDTH, y,
// 			     _bounds.x+_bounds.width-MAJOR_TICK_WIDTH+MINOR_TICK_WIDTH, y);

// 	    if ((_bounds.height%5) != 0)
// 		g2d.drawLine(_bounds.x+_bounds.width-MAJOR_TICK_WIDTH, _bounds.y,
// 			     _bounds.x+_bounds.width-MAJOR_TICK_WIDTH+MINOR_TICK_WIDTH, _bounds.y);

	}

	public Dimension getMinimumSize() {
	    Dimension dim = new Dimension(0,2*ylabel.getPreferredSize().height);
	    if (isVisible()) {
		dim.width = SCALE_WIDTH + SCALE_LABEL_SPACING + maxScaleLabelWidth + ylabel.getPreferredSize().width +
		    getInsets().left + getInsets().right;
	    }
	    return dim;
	}
	public Dimension getMaximumSize() {
	    return new Dimension(getMinimumSize().width, Integer.MAX_VALUE);
	}
	public Dimension getPreferredSize() {
	    return getMaximumSize();
	}

	private void updateComponent() {
	    //System.out.println("+Scale.updateComponent");
	    updateBounds();
	    updateLabels();
	    positionScaleLabels();
	    //System.out.println("-Scale.updateComponent");
	}

	private void updateMaxScaleLabelWidth() {
	    int _max = 0;
	    int _tmp = 0;
	    
	    for (JFormattedTextField tf: textfields) {
		_tmp = tf.getPreferredSize().width;
		if (_tmp > _max) _max = _tmp;
	    }
	    maxScaleLabelWidth = _max;
	}

	private void updateLabels() {
	    double span = scaleMax - scaleMin;
	    double delta = span / _numTicks;
	    for (int i=0; i < _numTicks+1; i++) {
		JFormattedTextField tf = textfields.get(i);
		tf.setValue(scaleMin + delta*i);
	    }
	    updateMaxScaleLabelWidth();
	}

	private void positionScaleLabels() {
	    Dimension size;
	    Insets border = getInsets();

	    double delta = _plot.getDeltaY();
	    int offset = 12;

	    for (int i=0; i < textfields.size(); i++) {
		JFormattedTextField tf = textfields.get(i);
		size = tf.getPreferredSize();
		tf.setBounds(_bounds.x,
			    (int)(_bounds.y+_bounds.height-(double)size.height/2-i*delta)-offset,
			    maxScaleLabelWidth, 
			    size.height);
	    }
	
	}

	private void updateBounds() {
	    Insets border = getInsets();
	    _bounds = getBounds();
	    _bounds.x = border.left;
	    _bounds.y = border.top;
	    _bounds.width -= border.left + border.right + 1;
	    _bounds.height -= border.top + border.bottom + 1;
	}

	public void setColor(Color c) {
	    color = c;
	    minField.setForeground(color);
	    maxField.setForeground(color);
	    for (JFormattedTextField tf: textfields) 
		tf.setForeground(color);
	    repaint();
	}

    } // end class Scale


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
    
}
