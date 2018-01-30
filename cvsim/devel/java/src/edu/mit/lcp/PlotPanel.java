package edu.mit.lcp;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import javax.swing.JCheckBox;

public abstract class PlotPanel extends JPanel {

    public boolean multipleYScales = false;
    public JCheckBox multipleYScalesCheckBox;
    public yScaleComponent yScale;
    ChangeListener sourceDataChanged;
    
    PlotPanel() {
 	this.setLayout(null);
	multipleYScalesCheckBox = new JCheckBox("Multiple Y Scales");
	multipleYScalesCheckBox.addItemListener(MultipleYScalesListener);
    }

    public boolean getMultipleYScales() { return multipleYScales; } 
    
    public abstract void setMultipleYScales(boolean newValue); 
      
    private ItemListener MultipleYScalesListener = new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if ( ((JCheckBox)e.getSource()).isSelected() ) 
		    setMultipleYScales(true);
		else {
		    setMultipleYScales(false);
		}
	    }
	};

    public abstract PlotComponent getPlot(); 
    
    public Point2D getScaledPoint(AffineTransform at, Point2D pt) 
	throws NoninvertibleTransformException {
	AffineTransform it = at.createInverse();
	Point2D scaledPt = it.transform(pt, null); 
	return scaledPt;
    }

    public abstract void removeTrace(Trace<?,?> t);
    public abstract void removeAllTraces();
    public abstract void addTrace(Trace<?,?> t);

} // end class PlotPanel
