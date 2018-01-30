package edu.mit.lcp;


import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JColorChooser;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TraceLegendTableComponent extends JComponent {
    JTable table;
    PlotWindow parentPlotWindow;
    TraceListModel traceModel;

    TraceLegendTableComponent(PlotWindow parent, TraceListModel model) {
	traceModel = model;
	parentPlotWindow = parent;
	this.setLayout(new BorderLayout());
	this.setBorder(LineBorder.createGrayLineBorder());
	
	// Create the table
	table = new JTable(new TraceLegendTableModel(traceModel));

	// Set table Renderer/Editor for Color 
	table.setDefaultRenderer(Color.class, new ColorRenderer(true));
        table.setDefaultEditor(Color.class, new ColorEditor());

	// Set table Renderer/Editor for Remove
	table.getColumnModel().getColumn(TraceLegendTableModel.COL_REMOVE).setCellEditor(new RemoveButtonCellEditor());
	table.getColumnModel().getColumn(TraceLegendTableModel.COL_REMOVE).setCellRenderer(new RemoveButtonCellRenderer());

	// Set selection method
	table.setCellSelectionEnabled(false);

	// Add the table and its header to the tablePanel
	add(table.getTableHeader(), BorderLayout.PAGE_START);
 	add(table, BorderLayout.CENTER);

    }


    ///////////////////////////////
    // Custom Renderers and Editors

    // Renders a Button in a table cell
    public class RemoveButtonCellRenderer extends JButton implements TableCellRenderer {
	public Component getTableCellRendererComponent
	    (JTable table, Object text, boolean isSelected, boolean hasFocus, int row, int column) {
	    setText((String)text);
	    return this;
	}
    }

    // Acts as a button (performs and action) as a cell editor
    public class RemoveButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
	JButton button;
	int tableRow;
	public RemoveButtonCellEditor() {
	    button = new JButton(new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
			Trace t = traceModel.getElementAt(tableRow);
			parentPlotWindow.getPlot().removeTrace(t);
			fireEditingStopped();
		    }
		});
	}

	public Object getCellEditorValue() {
	    return new Boolean(true);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	    tableRow = row;
	    return button;
	}
    }



    ///
    /// These classes were taken wholesale from Java tutorials
    ///

    /* 
     * ColorEditor.java (compiles with releases 1.3 and 1.4) is used by 
     * TableDialogEditDemo.java.
     */
    public class ColorEditor extends AbstractCellEditor	implements TableCellEditor, ActionListener {
	Color currentColor;
	JButton button;
	JColorChooser colorChooser;
	JDialog dialog;
	protected static final String EDIT = "edit";

	public ColorEditor() {
	    button = new JButton();
	    button.setActionCommand(EDIT);
	    button.addActionListener(this);
	    button.setBorderPainted(false);

	    //Set up the dialog that the button brings up.
	    colorChooser = new JColorChooser();
	    colorChooser.setPreviewPanel(new JPanel());
	    dialog = JColorChooser.createDialog(button,
						"Pick a Color",
						true,  //modal
						colorChooser,
						this,  //OK button handler
						null); //no CANCEL button handler
	}

	public void actionPerformed(ActionEvent e) {
	    if (EDIT.equals(e.getActionCommand())) {
		//The user has clicked the cell, so
		//bring up the dialog.
		button.setBackground(currentColor);
		colorChooser.setColor(currentColor);
		dialog.setVisible(true);

		fireEditingStopped(); //Make the renderer reappear.

	    } else { //User pressed dialog's "OK" button.
		currentColor = colorChooser.getColor();
	    }
	}

	//Implement the one CellEditor method that AbstractCellEditor doesn't.
	public Object getCellEditorValue() {
	    return currentColor;
	}

	//Implement the one method defined by TableCellEditor.
	public Component getTableCellEditorComponent(JTable table,
						     Object value,
						     boolean isSelected,
						     int row,
						     int column) {
	    currentColor = (Color)value;
	    return button;
	}
    }

    /* 
     * ColorRenderer.java (compiles with releases 1.2, 1.3, and 1.4) is used by 
     * TableDialogEditDemo.java.
     */
    public class ColorRenderer extends JLabel implements TableCellRenderer {
	Border unselectedBorder = null;
	Border selectedBorder = null;
	boolean isBordered = true;

	public ColorRenderer(boolean isBordered) {
	    this.isBordered = isBordered;
	    setOpaque(true); //MUST do this for background to show up.
	}

	public Component getTableCellRendererComponent
	    (JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
	    
	    Color newColor = (Color)color;
	    setBackground(newColor);
	    if (isBordered) {
		if (isSelected) {
		    if (selectedBorder == null) {
			selectedBorder = BorderFactory.createMatteBorder
			    (2,5,2,5, table.getSelectionBackground());
		    }
		    setBorder(selectedBorder);
		} else {
		    if (unselectedBorder == null) {
			unselectedBorder = BorderFactory.createMatteBorder
			    (2,5,2,5, table.getBackground());
		    }
		    setBorder(unselectedBorder);
		}
	    }
        
	    setToolTipText("RGB value: " + newColor.getRed() + ", "
			   + newColor.getGreen() + ", "
			   + newColor.getBlue());
	    return this;
	}
    }


}
