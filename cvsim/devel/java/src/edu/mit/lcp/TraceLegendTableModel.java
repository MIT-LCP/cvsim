package edu.mit.lcp;

import java.awt.Color;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

class TraceLegendTableModel extends AbstractTableModel {

    public static final int COL_COLOR   = 0;
    public static final int COL_XVAR    = 1;
    public static final int COL_YVAR    = 2;
    public static final int COL_ENABLED = 3;
    public static final int COL_REMOVE  = 4;

    private static final String[] columnNames = {
	"Color", "X Variable", "Y Variable", "Enabled", ""
    };
    
    private TraceListModel traceList;
    
    public TraceLegendTableModel(TraceListModel listModel) {
	traceList = listModel;

	traceList.addListDataListener( new ListDataListener() {
		// Should use more fine-grained fireXXX methods
		public void intervalAdded(ListDataEvent e){ fireTableDataChanged(); }
		public void intervalRemoved(ListDataEvent e){ fireTableDataChanged(); }
		public void contentsChanged(ListDataEvent e){ fireTableDataChanged(); }
	    });
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return traceList.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        Object value;
	switch (col) {
	case COL_COLOR   : value = traceList.get(row).getColor(); break;
	case COL_XVAR    : value = traceList.get(row).getXDescription(); break;
	case COL_YVAR    : value = traceList.get(row).getYDescription(); break;
	case COL_ENABLED : value = traceList.get(row).isEnabled(); break;
	case COL_REMOVE  : value = "Remove"; break;
	default: value = new String("Invalid Column Lookup"); break;
	}

	return value;
    }

    public Class getColumnClass(int c) {
	if (traceList.isEmpty())
	    return (new Object()).getClass();
	else
	    return getValueAt(0, c).getClass();
    }
    
    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.

	if ( (col == COL_ENABLED) || (col == COL_COLOR) || (col == COL_REMOVE) ) {
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
	if ( col == COL_ENABLED ) 
	    traceList.get(row).setEnabled((Boolean)value);

	if ( col == COL_COLOR )
	    traceList.get(row).setColor((Color)value);

	fireTableCellUpdated(row, col);
    }

}
