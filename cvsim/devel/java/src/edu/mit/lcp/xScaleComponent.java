package edu.mit.lcp;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.border.*;
import java.text.NumberFormat;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class xScaleComponent extends JComponent {
    private TraceListModel traceList;
    private List<Scale> _scaleList;
    private int _padding;
    private int _numTicks;
    private PlotPanelXYChart _plotPanel;
    private PlotComponent _plot;
    private yScaleComponent _yScale;
    private double min = 0;
    private double max = 0;
    public PropertyChangeSupport _changes = new PropertyChangeSupport(this);

    private int width = 600;
    private int height = 50;

    public static final String PROP_MULTISCALES = "MULTISCALES";

    public xScaleComponent(TraceListModel listModel, int pad, int numTicks, 
			   PlotPanelXYChart plotPanel) {
	_scaleList = new ArrayList<Scale>();
	traceList = listModel;
	_padding = pad;
	_numTicks = numTicks;
	_plotPanel = plotPanel;
	_plot = plotPanel.getPlot();
	_yScale = plotPanel.getYScale();

	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	traceList.addListDataListener( new ListDataListener() {
		public void intervalAdded(ListDataEvent e) {
		    Trace t = traceList.getElementAt(e.getIndex0());
		    // update global min and max
		    double traceMin =  t.getXRange().lower.doubleValue();
		    double traceMax =  t.getXRange().upper.doubleValue();
		    if ( traceMin <  min)
			min = traceMin;
		    if ( traceMax > max) 
			max = traceMax;
		    // if using multiple scales or if this is the first trace,
		    // add a new scale.
		    if ( _plotPanel.getMultipleXScales() || (_scaleList.size() == 0) ) {
			Scale s = new Scale(t);
			_scaleList.add(s);
			add(s);
		    } else {
			// update ranges but don't add a new scale
			for (Trace trace: traceList) {
			    trace.setXRange(new Range(min, max));
			} 
		    }
		}
		public void intervalRemoved(ListDataEvent e) {
		    // if using multiple scales or 
		    // using single scale but removing last trace,
		    // remove scale
		    if ( ( _plotPanel.getMultipleXScales() ) ||
			 ( !_plotPanel.getMultipleXScales() && (traceList.size() == 0) ) ) {
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
			// remove all scales but one
			Scale s0 = _scaleList.get(0);
			while ( _scaleList.size() > 1 ) {
			    Scale scale = _scaleList.get(_scaleList.size()-1);
			    // update scale range
			    double scaleMin = scale.getRange().lower.doubleValue();
			    double scaleMax = scale.getRange().upper.doubleValue();
			    if ( scaleMin < min )
				min = scaleMin;
			    if ( scaleMax > max )
				max = scaleMax;
			    // update remaining scale range
			    s0.setRange(new Range(min, max));
			    // remove old scale
			    scale.setVisible(false);			
			    _scaleList.remove(scale);
			}
			// set color to black
			s0.setColor(Color.BLACK);
			//update trace range
			for (Trace trace: traceList) {
			    trace.setXRange(new Range(min, max));
			} 
		    }
		}
	    });
    }
    
    public Dimension getMinimumSize() {
      	int y = 0;
	int x = width;
	Insets border = getInsets();

	for (Scale scale: _scaleList) {
	    y += scale.getPreferredSize().height;
	}
	y += border.bottom;
	return new Dimension(x,y);
    }

    public Dimension getPreferredSize() {
	return getMinimumSize();
    }


    ///////////////////////////////////
    private class Scale extends JComponent {

	private Trace trace;
	private Color color;
	private List<JFormattedTextField> textfields;
	private JFormattedTextField minField;
	private JFormattedTextField maxField;
	private int maxScaleLabelWidth;
	private int maxScaleLabelHeight;
	private Rectangle _bounds;
	private double scaleMin;
	private double scaleMax;

	private NumberFormat doubleFormat = NumberFormat.getNumberInstance();
	private Font rangeFont = new Font("Serif", Font.PLAIN, 10);

	//public PropertyChangeSupport _changes = new PropertyChangeSupport(this);
	public static final String VALUE = "value";
	
	private int width = 600;
	private int height = 10;

	public Scale(Trace t) {
	    trace = t;
	    textfields = new ArrayList<JFormattedTextField>();
	    if ( _plotPanel.getMultipleXScales() )
		color = trace.getColor();
	    else
		color = Color.BLACK;

	    scaleMin = trace.getXRange().lower.doubleValue();
	    scaleMax = trace.getXRange().upper.doubleValue();
	    setRange(scaleMin, scaleMax);

	    // minimum 
	    minField = new JFormattedTextField(doubleFormat);
	    minField.setValue(scaleMin);
	    minField.setColumns(3);
	    minField.setFont(rangeFont);
	    minField.setForeground(color);
	    minField.setHorizontalAlignment(SwingConstants.RIGHT);
	    add(minField);
	    textfields.add(minField);

	    // mid-range
	    double span = scaleMax - scaleMin;
	    double delta = span / _numTicks;
	    Border emptyBorder = BorderFactory.createEmptyBorder(0,0,0,0);
	    for (int i=1; i < _numTicks; i++) {
		JFormattedTextField tf = new JFormattedTextField();
		tf.setFont(rangeFont);
		tf.setForeground(color);
		tf.setHorizontalAlignment(SwingConstants.RIGHT);
		tf.setValue(scaleMin + delta*i);
		tf.setColumns(3);
		tf.setEditable(false);
		tf.setBorder(emptyBorder);
		add(tf);
		textfields.add(tf);
	    }

	    // maximum
	    maxField = new JFormattedTextField(doubleFormat);
	    maxField.setValue(scaleMax);
	    maxField.setColumns(3);
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
			if ( _plotPanel.getMultipleXScales() )
			    trace.setXRange(new Range(scaleMin, scaleMax));
			else { // if using single scale, adjust range on all traces
			    for (Trace t: traceList)
				t.setXRange(new Range(scaleMin, t.getXRange().upper.doubleValue()));
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
			if ( _plotPanel.getMultipleXScales() ) {
			    trace.setXRange(new Range(scaleMin, scaleMax));
			} else { // if using single scale, adjust range on all traces
			    for (Trace t: traceList)
				t.setXRange(new Range(t.getXRange().lower.doubleValue(), scaleMax));
			}
			updateComponent();
		    }
		});

	    trace.addPropertyChangeListener(Trace.PROP_XRANGE, new PropertyChangeListener() {
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

	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    updateComponent();
	}

	public Dimension getMinimumSize() {
	    JLabel label = new JLabel();
	    int SCALE_LABEL_SPACING = 2;
	    Dimension dim = new Dimension(2*label.getPreferredSize().width, 0);	
	    if (isVisible()) {
		dim.height = SCALE_LABEL_SPACING + maxScaleLabelHeight + getInsets().top + getInsets().bottom;
	    }
	    return dim;
	}
	
	// The preferred size is the maximum size
	public Dimension getPreferredSize() {
	    return getMaximumSize();
	}
	
	// The maximum size is the maximum width and the minimum height
	public Dimension getMaximumSize() {
	    return new Dimension(Integer.MAX_VALUE, getMinimumSize().height);
	}

	private void updateComponent() {
	    updateBounds();
	    updateLabels();
	    positionScaleLabels();
	}

	private void updateMaxScaleLabelWidth() {
	    int _max = 0;
	    int _size = 0;
	    
	    for (JFormattedTextField tf: textfields) {
		_size = tf.getPreferredSize().width;
		if (_size > _max) 
		    _max = _size;
	    }
	    maxScaleLabelWidth = _max;
	}

	private void updateMaxScaleLabelHeight() {
	    int _max = 0;
	    int _size = 0;
	    
	    for (JFormattedTextField tf: textfields) {
		_size = tf.getPreferredSize().height;
		if (_size > _max) 
		    _max = _size;
	    }
	    maxScaleLabelHeight = _max;
	}
		
	private void updateLabels() {
	    double span = scaleMax - scaleMin;
	    double delta = span / _numTicks;
	    for (int i=0; i < _numTicks+1; i++) {
		JFormattedTextField tf = textfields.get(i);
		tf.setValue(scaleMin + delta*i);
	    }
	    updateMaxScaleLabelWidth();
	    updateMaxScaleLabelHeight();
	}
	
	private void positionScaleLabels() {
	    Dimension size;
	    Insets border = getInsets();
	    double delta = _plot.getDeltaX();
	    int offset = 15;
	    
	    for (int i=0; i < textfields.size(); i++) {
		JFormattedTextField tf = textfields.get(i);
		size = tf.getPreferredSize();
		// don't want the last label to line up with the last tick 
		// mark. It will run off the page.
		if (i == (textfields.size()-1))
		    offset += 10;
		tf.setBounds(_bounds.x + _yScale.getPreferredSize().width + (int)(i*delta) - offset,
			     _bounds.y,
			     size.width, 
			     maxScaleLabelHeight);
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

	public void setColor(Color c) {
	    color = c;
	    minField.setForeground(color);
	    maxField.setForeground(color);
	    for (JFormattedTextField tf: textfields) 
		tf.setForeground(color);
	    repaint();
	}

	public void setScaleMin(double min) { scaleMin = min; }
	public void setScaleMax(double max) { scaleMax = max; }

    } // end Scale() inner class

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
