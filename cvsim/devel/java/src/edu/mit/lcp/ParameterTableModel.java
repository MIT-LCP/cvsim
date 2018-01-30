package edu.mit.lcp;

import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.table.AbstractTableModel;

class ParameterTableModel extends AbstractTableModel {

    private static final int COL_NAME     = 0;
    public static final int COL_VALUE    = 1;
    private static final int COL_UNITS    = 2;
    private static final int COL_SELECTED = 3;

    private static final String[] columnNames = {
	"Name", "Value", "Units", "Select"
    };
    
    private List<Parameter> parameterList;
    private Hashtable<Parameter,Boolean> parametersSelected;
    private List<Parameter> displayList;
    public List<Parameter> highlightList;
    private String _categoryFilter;
    private String _typeFilter;
    private ParameterPanel _parent;
    public int centerRow;

//     public ParameterTableModel() {
// 	this(new ArrayList());
//     }
    
    public ParameterTableModel(List parameters, ParameterPanel parent) {
	_parent = parent;
	parameterList = parameters;
	displayList = new ArrayList(parameterList);
	highlightList = new ArrayList();
	parametersSelected = new Hashtable<Parameter,Boolean>();
	for (Parameter p: parameterList)
	    parametersSelected.put(p,new Boolean(false));
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return displayList.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        Object value;
	switch (col) {
	case COL_NAME    : value = displayList.get(row).getName(); break;  
	case COL_VALUE   : 
	    if ( _parent.getDisplayMode().equals(_parent.DEFAULT) )
		value = (displayList.get(row).getValue()).toString();
	    else 
		value = (displayList.get(row).getPercent()).toString();
	    break;
	case COL_UNITS   : 
	    if ( _parent.getDisplayMode().equals(_parent.DEFAULT) )
		value = displayList.get(row).getUnits();
	    else
		value = "%";
	    break;
	case COL_SELECTED: value = isParameterSelected(displayList.get(row)); break;
	default: value = new String("Invalid Column Lookup"); break;
	}

	return value;
    }

    public Class getColumnClass(int c) {
	return getValueAt(0, c).getClass();
    }
    
    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if ( (col == COL_VALUE) || (col == COL_SELECTED) ) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        if ( col == COL_VALUE ) {
	    try {
		if ( _parent.getDisplayMode().equals(_parent.DEFAULT) )
		    displayList.get(row).setValue(Double.valueOf(value.toString()));
		else if ( _parent.getDisplayMode().equals(_parent.PATIENT) ) {
		    displayList.get(row).setPercent(Double.valueOf(value.toString()));
		}
	    } catch (Exception e) { }	
	}
	if ( col == COL_SELECTED ) 
	    setParameterSelected(displayList.get(row), (Boolean)value);

	fireTableCellUpdated(row, col);
    }

    public void setCategoryFilter(String category) {
	_categoryFilter = category;
	highlightList.clear();
	updateDisplayList();
    }

    public void setTypeFilter(String type) {
	_typeFilter = type;
	highlightList.clear();
	updateDisplayList();
    }

    public void updateDisplayList() {
	System.out.println("updateDisplayList(): category: " + _categoryFilter + " -- type: " + _typeFilter);
	displayList.clear();
	for (Parameter p: parameterList) {		
	    if ( (_categoryFilter == null) || (_categoryFilter.equals(p.getCategory())) ) {
		if ( (_typeFilter == null) || (_typeFilter.equals(p.getType())) ) {
		    displayList.add(p);
		}
	    }
	}
	fireTableDataChanged();
    }
   
    // returns index of last highlighted parameter in displayList()
    public int updateHighlightList(String highlightFilter) {
	centerRow = 1;
	highlightList.clear();
	if ( highlightFilter != null ) {
	    for (Parameter p: parameterList) {		
		if ( highlightFilter.equals(p.getCategory()) ) {
		    highlightList.add(p);
		}
	    }
	    // find the row number corresponding to the middle of the highlighted parameters
	    centerRow = displayList.indexOf( highlightList.get( (int)(highlightList.size()/2)) );
	}
	fireTableDataChanged();
	return centerRow;
    }

    public List<String> getCategoryNames() {
	ArrayList<String> names = new ArrayList<String>();
	String cat;
	for (Parameter p: parameterList) {
	    cat = p.getCategory();
	    if (!names.contains(cat))
		names.add(cat);
	}
	return names;
    }

    public List<String> getParameterTypeNames() {
	return getFilteredParameterTypeNames(null);
    }
    
    public List<String> getFilteredParameterTypeNames() {
	return getFilteredParameterTypeNames(_categoryFilter);
    }
    
    public List<String> getFilteredParameterTypeNames(String filter) {
	ArrayList<String> names = new ArrayList<String>();
	String type;
	for (Parameter p: parameterList) {
	    if ( (filter == null) || (filter.equals(p.getCategory())) ) {
		type = p.getType();
		if (!names.contains(type))
		    names.add(type);
	    }
	}
	return names;
    }
    
    public Boolean isParameterSelected(Parameter p) {
	return parametersSelected.get(p);
    }
    public void setParameterSelected(Parameter p, Boolean sel) {
	parametersSelected.put(p,sel);
	int _idx = displayList.indexOf(p);
	if (_idx >= 0)
	    fireTableCellUpdated(_idx, COL_SELECTED);
    }

    public List<Parameter> getSelectedParameterList() {
	ArrayList<Parameter> _pl= new ArrayList<Parameter>();
	for (Parameter p: parametersSelected.keySet())
	    if (isParameterSelected(p))
		_pl.add(p);

	return _pl;
    }
    public List<Parameter> getParameterList() {
	return parameterList;
    }
    public List<Parameter> getFilteredParameterList() {
	return displayList;
    }
}
