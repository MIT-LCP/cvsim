package edu.mit.lcp;

public class SimulationOutputVariable<E extends Number & Comparable<E>> {

    // Descriptions of the encapsulated data
    private String _name;
    private String _description;
    private String _units;
    private String _category;
    private String _type;
    private int _outputIndex;
    private Range<E> _typicalRange;
    
    public SimulationOutputVariable(int index, String name, Range<E> range) {
	this(index, name, "", "", "", "", range);
    }

    public SimulationOutputVariable(int index, String name, String description, String units, Range<E> range) {
	this(index, name, description, units, "", "", range);
    }

    public SimulationOutputVariable(int index, String name, String description, String units, String category, String type, Range<E> range) {
	// Names are only set at construction time
	_outputIndex = index;
	_name = name;
	_description = description;
	_units = units;
	_category = category;
	_type = type;

	_typicalRange = range;
    }

    // Override the Object.toString() method to give useful names
    public String toString() {
	return _description;
    }
 
    public int getOutputIndex() { return _outputIndex; }
    public String getName() { return _name; }
    public String getDescription() { return _description; }
    public String getUnits() { return _units; }
    public String getCategory() { return _category; }
    public String getType() { return _type; }

    public Range<E> getTypicalRange() {
	return new Range<E>(_typicalRange);
    }

}
