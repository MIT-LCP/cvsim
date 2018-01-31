package edu.mit.lcp;

import java.util.List;
import java.awt.Color;
import java.util.Iterator;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.event.EventListenerList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class TraceListModel extends AbstractListModel implements Iterable<Trace> {
    // This list used to hold the traces
    private List<Trace> _traceList = new CopyOnWriteArrayList<Trace>();
    public List<Color> colorList = new ArrayList<Color>();

    private PropertyChangeListener tracePropertyChangeListener = new PropertyChangeListener() {
	    public void propertyChange(PropertyChangeEvent pce) {
		Trace t = (Trace)pce.getSource();
		fireContentsChanged(this, _traceList.indexOf(t), _traceList.indexOf(t));
	    }
	};

    TraceListModel() {
	colorList.add(Color.RED);
	colorList.add(Color.BLUE);
	colorList.add(Color.GREEN);
	colorList.add(Color.CYAN);
	colorList.add(Color.MAGENTA);
	colorList.add(Color.ORANGE);
    }

    public void add(Trace<?,?> trace) { 
	_traceList.add(trace); 
	// should use: fireIntervalAdded(this, int index0, int index1);
	fireIntervalAdded(this, _traceList.size()-1, _traceList.size()-1) ;
	trace.addPropertyChangeListener(tracePropertyChangeListener);
    }
    public void remove(Trace<?,?> trace) {
	int oldSize = _traceList.size();
	int idx = _traceList.indexOf(trace);
	if (idx >= 0) {
	    _traceList.remove(trace); 
	    // should use: fireIntervalRemoved(this, int index0, int index1);
	    fireIntervalRemoved(this, idx, idx);
	    trace.removePropertyChangeListener(tracePropertyChangeListener);
	}
    }

    public void clear() {
	int oldSize = _traceList.size();
	_traceList.clear();
	// should use: fireIntervalRemoved(this, int index0, int index1);
	fireIntervalRemoved(this, 0, oldSize-1);
    }

    public Trace<?,?> getElementAt(int index) {
	return _traceList.get(index);
    }

    public Trace<?,?> get(int index) {
	return getElementAt(index);
    }

    public int indexOf(Trace t) {
	return _traceList.indexOf(t);
    }

    public boolean isEmpty() {
	return _traceList.isEmpty();
    }
	
    public int getSize() {
	return _traceList.size();
    }

    public int size() {
	return getSize();
    }

    public Color getNextColor() {
	List<Color> remainingColors = new ArrayList<Color>(colorList);
	for (Trace trace: _traceList) {
	    remainingColors.remove(trace.getColor());
	}
	if (remainingColors.isEmpty()) {
	    return Color.BLACK;
	} else {
	    return remainingColors.get(0);
	}
    }
    
    ////////////////////////////////
    // Public Interface for Iterable
    public Iterator<Trace> iterator() {
	// Pass the iterator from the list as our iterator
	return _traceList.iterator();
    }

} // end class TraceListModel
