package edu.mit.lcp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeEvent;

public abstract class  Parameter {
    private PropertyChangeSupport _propChangeListeners = new PropertyChangeSupport(this);

    private String _category;
    private String _type;
    private String _name;
    private String _units;
    private int _index; // location of the value in the C Parameter_vector structure
    private Double _defaultValue;
    private Double _ptModeDefaultValue;
    private double _percent;
    private double _max = Double.POSITIVE_INFINITY;  // max normal physiological range
    private double _min = Double.NEGATIVE_INFINITY;  // min normal physiological range 

    public Parameter(int index, String category, String type, String name, String units) {
	_index = index;
	_category = category;
	_type = type;
	_name = name;
	_units = units;
	_defaultValue = new Double(0.0);
	_ptModeDefaultValue = new Double(0.0);
	_percent = 100;
    }

    public Parameter(int index, String category, String type, String name, String units,
		     double min, double max) {
	_index = index;
	_category = category;
	_type = type;
	_name = name;
	_units = units;
	_defaultValue = new Double(0.0);
	_ptModeDefaultValue = new Double(0.0);
	_percent = 100;
	_min = min;
	_max = max;
    }
	
    public int getIndex() {
	return _index;
    }

    public String getCategory() {
	return _category;
    }

    public String getType() {
	return _type;
    }

    public String getName() {
	return _name;
    }

    public abstract void setValue(Double value);
    public abstract Double getValue();
    
    public void setPercent(Double percent)
    {
	Double oldPercent = getPercent();
	_percent = percent;
	
	if ( (CVSim.gui.parameterPanel.getDisplayMode()).equals(CVSim.gui.parameterPanel.DEFAULT) )
	    setValue(percent/100*getDefaultValue());
	else if ( (CVSim.gui.parameterPanel.getDisplayMode()).equals(CVSim.gui.parameterPanel.PATIENT) )
	    setValue(percent/100*getPtModeDefaultValue());

	String str = String.format(getName() + ": Changing percent from " + oldPercent.toString() 
				   + " to " + percent.toString() + "\n" 
				   + getName() + ": Changing value from default of " 
				   + getDefaultValue().toString() 
				   + " to " + getValue().toString() );
	System.out.println(str);
    }

    public Double getPercent() { return _percent; }

    public Double getDefaultValue() {
	return _defaultValue;
    }
    public void setDefaultValue(Double val) {
	_defaultValue = val;
    }
    public void setDefaultValue() {
	setDefaultValue(getValue());
    }
    public Double getPtModeDefaultValue() {
	return _ptModeDefaultValue;
    }
    public void setPtModeDefaultValue(Double val) {
	_ptModeDefaultValue = val;
    }
    public String getUnits() {
	return _units;
    }

    public double getMin() { return _min; }
    public double getMax() { return _max; }
	
    public String toString() {
	return getName();
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
        _propChangeListeners.addPropertyChangeListener(l);
    }
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	_propChangeListeners.addPropertyChangeListener(propertyName, listener);
    }

    /** 
     * Remove this PropertyChangeListener from the buttons internal list.  
     * If the PropertyChangeListener isn't on the list, silently do nothing.
     * 
     * @see #addPropertyChangeListener
     * @param l the PropertyChangeListener
     */      
    public void removePropertyChangeListener(PropertyChangeListener l) {
        _propChangeListeners.removePropertyChangeListener(l);
    }

    protected void firePropertyChange(String property, Object old, Object current) {
	_propChangeListeners.firePropertyChange(property, old, current);
    }

    protected void firePropertyChange(PropertyChangeEvent e) {
	_propChangeListeners.firePropertyChange(e);
    }
}

