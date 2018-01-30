package edu.mit.lcp;

import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import java.util.Collections;

// Implements a class which holds data (of type double) and the
// description of the dataset.

public class SimulationOutputVariableBuffer<E extends Number & Comparable<E>>
    implements VariableRecorderInterface<E> {

    // Flags to describe data
    public static final int DISABLE_MIN_MAX_UPDATES   = 1 << 0;
    public static final int DATA_MONOTONIC_INCREASING = 1 << 1;
    public static final int DATA_MONOTONIC_DECREASING = 1 << 2;

    // Descriptions of the encapsulated data
    private E _minVal;
    private E _maxVal;
    private int _flags;

    private SimulationOutputVariable<E> var;
    
    // Locally enforced limit on the size of buffer
    private int _sizeLimit;

    // Index of next value to be written into the buffer
    private int _nextWriteLoc;

    // Unique serial number to identify the last update 
    private long lastUpdate;
    private E lastDatum;

    // Actual data
    private Vector<E> _data;
    
    public SimulationOutputVariableBuffer(int newSize, SimulationOutputVariableBuffer<E> oldBuffer) {
	this(newSize, oldBuffer.getVar(), oldBuffer.iterator().next(), oldBuffer.getFlags());

 	// copy out the old data
	for (E val: oldBuffer) {
 	    _data.setElementAt(val, _nextWriteLoc);
	    incrementWriteLoc();
	}
	// find min and max the hard way
	_minVal = (E)java.util.Collections.min(_data); 
	_maxVal = (E)java.util.Collections.max(_data); 

    }

    public SimulationOutputVariableBuffer(SimulationOutputVariable variable, E initializer) {
	this(1, variable, initializer, 0);
    }

    public SimulationOutputVariableBuffer(int sizeLimit, SimulationOutputVariable variable, E initializer) {
	this(sizeLimit, variable, initializer, 0);
    }

    public SimulationOutputVariableBuffer(int sizeLimit, SimulationOutputVariable variable, E initializer, int flags) {
	_sizeLimit = sizeLimit;
	var = variable;
	_flags = flags;
	
	// Data is held in a Vector because the Vector class is
	// synchronized internally, AND provided its structure doesn't
	// change, it can be iterated over while being written to.
	// Changes in structure are avoided by replacing elements in
	// the Vector instead of adding the new and removing the old.
	_data = new Vector<E>(_sizeLimit);

	// the Vector is initialized with data at creation so that all
	// replacements have something to replace.  an Initializer
	// variable is required because a generic type cannot be
	// created at runtime, it's type is only known at compile
	// time.
	for (int i=0; i<_sizeLimit;i++)
	    _data.add(initializer);

	// set initial min/max
	_minVal = initializer;
	_maxVal = initializer;
      
       lastUpdate = -1;
       lastDatum = initializer;

	_nextWriteLoc = 0;
    }

    private void incrementWriteLoc() {
	// set the index location of the next location to write, and
	// wrap around if it reaches the end
	_nextWriteLoc++;
	if (_nextWriteLoc >= _sizeLimit)
	    _nextWriteLoc = 0;
    }

    public void clearData() {
	// Clear all data values from the buffer
	_data.clear();
    }
    
    // Override the Object.toString() method to give useful names
    public String toString() {
	return var.getDescription();
    }
    
    /////////////////////////////////////////////////
    // Public Interface for VariableRecorderInterface
    //
    public int getOutputIndex() { return var.getOutputIndex(); }
    public String getName() { return var.getName(); }
    public String getDescription() { return var.getDescription(); }
    public String getUnits() { return var.getUnits(); }
    public int getSize() { return _data.size(); }
    public String getCategory() { return var.getCategory(); }
    public String getType() { return var.getType(); }
    public SimulationOutputVariable getVar() { return var; }
    public int getFlags() { return _flags; }
    public void setFlags(int newFlags) { _flags = newFlags; }
    public Range<E> getTypicalRange() {
	return new Range<E>(var.getTypicalRange());
    }

    public E getMinVal() { return _minVal; }
    public E getMaxVal() { return _maxVal; }


    //    public void setSizeLimit(int sizeLimit) { _sizeLimit = sizeLimit; }    
    //    public int getSizeLimit() { return _sizeLimit; }

    public long getLastMarker() {
	return lastUpdate;
    }
    
    public E getLastDatum() {
	return lastDatum;
    }

    public void addDatum(E d) {	addMarkedDatum(d, 0); }
    public void addMarkedDatum(E d, long serialNumber) {
	// Save value that is being replaced for min/max checking
	E oldVal = _data.get(_nextWriteLoc);
	// Overwrite with new datum
	_data.setElementAt(d, _nextWriteLoc);
	// Set the lastUpdate value
	lastUpdate = serialNumber;
	lastDatum = d;

	// update the location for the next write, also needed before
	// the min/max calculations so that the next value to be
	// overwritten is known (for monotonic data)
	incrementWriteLoc();

	// set min/max values used for PlotVariableInterface, but only
	// if its action hasn't been explicitly disabled
	if ((~_flags & DISABLE_MIN_MAX_UPDATES) == DISABLE_MIN_MAX_UPDATES) {
	    // Set new minVal
	    if ( d.compareTo(_minVal) < 0 ) {
		// new value is the new minVal
		_minVal = d;

	    } else if (oldVal.compareTo(_minVal) == 0) {
		// replaced data point was the old minVal
		
		if ((_flags & DATA_MONOTONIC_INCREASING) == DATA_MONOTONIC_INCREASING) {
		    // if monotonic increasing, new minVal is the next
		    // to be overwritten
		    _minVal = _data.get(_nextWriteLoc);
	        } else {
		    // find new minVal the long way
		    _minVal = (E)java.util.Collections.min(_data);
		    // System.out.println("SimulationOutputVariableBuffer ("+_name+"): Found new minVal the hard way");
		}
	    }
	    // else do nothing because the minVal is still valid
	    if ( d.compareTo(_maxVal) > 0 ) {
		// new value is the new maxVal
		_maxVal = d;
	    
	    } else if (oldVal.compareTo(_maxVal) == 0) {
		// replaced data point was the old maxVal
		
		if ((_flags & DATA_MONOTONIC_DECREASING) == DATA_MONOTONIC_DECREASING) {
		    // if monotonic decreasing, new maxVal is the next
		    // to be overwritten
		    _maxVal = _data.get(_nextWriteLoc);
		} else {
		    // find new maxVal the long way
		    _maxVal = (E)java.util.Collections.max(_data); 
		    // System.out.println("SimulationOutputVariableBuffer ("+_name+"): Found new maxVal the hard way");
		}
	    }
	    // else do nothing because the maxVal is still valid

	} // end if !DISABLE_MIN_MAX_UPDATES
    } // end addMarkedDatum(...)

    public int getIteratorStartIndex() {
       // Return the vector index of the start of the iterator
       return _nextWriteLoc;
    }

    ////////////////////////////////
    // Public Interface for Iterable
    //
    public Iterator<E> iterator() {
	return new WrappingIterator<E>(_data, getIteratorStartIndex());
    }

    // Here a custom iterator is implemented.  This allows iterating
    // over the complete list even if the start location is not at the
    // first element
    private class WrappingIterator<E extends Number & Comparable<E>> implements Iterator<E> {
	private List<E> _theList; // could also use a Vector<E>
	private int _startLoc;
	private int _endLoc;
	private int _curLoc;
	private int _size;

	public WrappingIterator(List<E> theList, int startLoc) {
	    // setup the iteration based on initial List state
	    _theList = theList;
	    _size = _theList.size();
	    _startLoc = startLoc;
	    _curLoc = startLoc;
	    
	    // Set the last location to be one index down from the
	    // start, or the last element if it was started at the
	    // first
	    if (_startLoc == 0)
		_endLoc = _size-1;
	    else
		_endLoc = startLoc-1;
	}
	
	// This list has more elements to be processed if we haven't
	// reached the end location
	public boolean hasNext() {
	    return (_curLoc != _endLoc);
	}

	// Return the element at the current location
	public E next() {
	    //_curLoc++;
	    _curLoc = ++_curLoc % _size;
	    
	    //if (_curLoc >= _size) _curLoc = 0;

	    return _theList.get(_curLoc);
	}

	// The remove method is not implemented
	public void remove() { throw new UnsupportedOperationException(); }
    }

}
