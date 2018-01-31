package edu.mit.lcp;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;


// implements a JTable that honors the isEnabled() for rendering of components.
public class DisableableJTable extends JTable {
    DisableableJTable(TableModel model) {
	super(model);
    }

    public TableCellRenderer getCellRenderer(int row, int column) {
	return new RespectEnabledTableCellRendererWrapper(super.getCellRenderer(row, column));
    }

    /**
     * This is a wrapper for TableCellRenderer that will cause it to respect the 
     * JTable's enabled property.
     * 
     * Workaround for Sun BugID 4795987 - "DefaultTableCellRenderer does not honor 'isEnabled'"
     * 
     * To work around the bug, override JTable.getCellRenderer() as follows:
     * 
     * 		@Override
     *		public TableCellRenderer getCellRenderer(int row, int column)
     *		{
     *			return new RespectEnabledTableCellRendererWrapper(super.getCellRenderer(row, column));
     *		}
     * 
     * @author Ken Roe
     */
    public class RespectEnabledTableCellRendererWrapper implements TableCellRenderer
    {
	private TableCellRenderer renderer; 
	
	public RespectEnabledTableCellRendererWrapper(TableCellRenderer renderer)
	{
	    this.renderer = renderer;
	}
	
	public Component getTableCellRendererComponent
	    (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
	    Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    if (component.isEnabled() != table.isEnabled())
		{
		    component.setEnabled(table.isEnabled());			
		}

	    return component;
	}
    }
}
