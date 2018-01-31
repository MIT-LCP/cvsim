package edu.mit.lcp;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import java.util.List;

public class SimulationOutputVariableListModel extends AbstractListModel implements ComboBoxModel {

    private List<SimulationOutputVariable> _data;
    private Object item;

    public SimulationOutputVariableListModel(List<SimulationOutputVariable> data) {
	_data = data;

	if (_data.isEmpty())
	    item = null;
	else
	    item = _data.get(0);
    }

    // ListModel Interface
    public int getSize() { return _data.size(); }
    public Object getElementAt(int index) { return _data.get(index); }
	
    // combobox crap
    public void setSelectedItem(Object o) { item = o; }
    public Object getSelectedItem() { return item; }
}
