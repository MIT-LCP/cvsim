package edu.mit.lcp;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JInternalFrame;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.Scanner;

public class ParameterPanel extends JPanel {
    
    private static final String CHOICE_ALL = "ALL";
    public static final String DEFAULT = "Default";
    public static final String PATIENT = "Patient";
    public static String displayMode = DEFAULT;
    public ParameterTableModel model;
    public JTable table;
    private boolean highlightingOff = false;
    private JScrollPane scrollPane;
    private JMenu actionsMenu;

    // top toolbar
    private JLabel categoryComboBoxLabel;
    public  JComboBox categoryComboBox;
    private JLabel typeComboBoxLabel;
    public  JComboBox typeComboBox;
    
    // middle toolbar
    private JButton selectButton;
    private JMenu selectButtonMenu;
    private JMenu selectMenu;
    private JButton loadButton;
    private JMenuItem loadMenuItem;
    private JButton saveButton;
    private JMenuItem saveMenuItem;
    private JButton resetButton;
    private JMenuItem resetMenuItem;

    // bottom toolbar
    private JButton clearHighlightingButton;
    private JMenuItem clearHighlightingMenuItem;
    private JCheckBox highlightingOffCheckBox;
    private JCheckBoxMenuItem highlightingOffCheckBoxMenuItem;
    private JButton exitPatientModeButton;
    private JMenuItem exitPatientModeMenuItem;

    public ParameterPanel() {
	super();
	this.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	
	model = new ParameterTableModel(CVSim.sim.getParameterList(), this);

	actionsMenu = new JMenu("Parameters");

	// top toolbar
	JToolBar topToolBar = new JToolBar();
	topToolBar.setFloatable(false);
	topToolBar.setRollover(true);

	JLabel locationFilterLabel = new JLabel("Show Location:");
	locationFilterLabel.setBorder(new EmptyBorder(0, 0, 0, 7));
	topToolBar.add(locationFilterLabel);
	categoryComboBox = new JComboBox(getModelCategoryChoices());
	categoryComboBox.addActionListener(CategoryComboBoxListener);
	topToolBar.add(categoryComboBox);

	JLabel typeFilterLabel = new JLabel("Show Type:");
	typeFilterLabel.setBorder(new EmptyBorder(0, 7, 0, 7));
	topToolBar.add(typeFilterLabel);
	typeComboBox = new JComboBox(getModelParameterTypeChoices());
	typeComboBox.addActionListener(TypeComboBoxListener);
	topToolBar.add(typeComboBox);

	// middle toolbar
	JToolBar middleToolBar = new JToolBar();
	middleToolBar.setFloatable(false);
	middleToolBar.setRollover(true);

	selectButtonMenu = new JMenu("Select");
	JMenuItem selectButtonAllMenuItem = new JMenuItem("Select All");
	selectButtonAllMenuItem.addActionListener(new SelectAllParametersAction(this));
	JMenuItem selectButtonVisibleMenuItem = new JMenuItem("Select Visible");
	selectButtonVisibleMenuItem.addActionListener(new SelectVisibleParametersAction(this));
	JMenuItem selectButtonNoneMenuItem = new JMenuItem("Select None");
	selectButtonNoneMenuItem.addActionListener(new SelectNoneParametersAction(this));
	selectButtonMenu.add(selectButtonAllMenuItem);
	selectButtonMenu.add(selectButtonVisibleMenuItem);
	selectButtonMenu.add(selectButtonNoneMenuItem);
	selectButton = new DropDownToolbarButton(selectButtonMenu);
	middleToolBar.add(selectButton);
	// Need duplicate menu items for menubar
	selectMenu = new JMenu("Select");
	JMenuItem selectAllMenuItem = new JMenuItem("Select All");
	selectAllMenuItem.addActionListener(new SelectAllParametersAction(this));
	JMenuItem selectVisibleMenuItem = new JMenuItem("Select Visible");
	selectVisibleMenuItem.addActionListener(new SelectVisibleParametersAction(this));
	JMenuItem selectNoneMenuItem = new JMenuItem("Select None");
	selectNoneMenuItem.addActionListener(new SelectNoneParametersAction(this));
	selectMenu.add(selectAllMenuItem);
	selectMenu.add(selectVisibleMenuItem);
	selectMenu.add(selectNoneMenuItem);
	actionsMenu.add(selectMenu);

	loadButton = new JButton(new LoadParametersFromFileAction(this, "Selected"));
	middleToolBar.add(loadButton);
	loadMenuItem = new JMenuItem("Load");
	loadMenuItem.addActionListener(new LoadParametersFromFileAction(this, "Selected"));
	actionsMenu.add(loadMenuItem);

	saveButton = new JButton(new SaveParametersToFileAction(this, "Selected"));
	middleToolBar.add(saveButton);
	saveMenuItem = new JMenuItem("Snapshot");
	saveMenuItem.addActionListener(new SaveParametersToFileAction(this, "Selected"));
	actionsMenu.add(saveMenuItem);

	resetButton = new JButton(new ResetParametersAction(resetMenuItem, "Selected"));
	middleToolBar.add(resetButton);
	resetMenuItem = new JMenuItem("Restore Default Values");
	resetMenuItem.addActionListener(new ResetParametersAction(resetMenuItem, "Selected"));
	actionsMenu.add(resetMenuItem);

	// bottom toolbar 
	JToolBar bottomToolBar = new JToolBar();
	bottomToolBar.setFloatable(false);
	bottomToolBar.setRollover(true);

	clearHighlightingButton = new JButton("Clear Highlighting");
	clearHighlightingButton.addActionListener(ClearHighlightingListener);
	bottomToolBar.add(clearHighlightingButton);
	clearHighlightingMenuItem = new JMenuItem("Clear Highlighting");
	clearHighlightingMenuItem.addActionListener(ClearHighlightingListener);
	actionsMenu.add(clearHighlightingMenuItem);

	highlightingOffCheckBox = new JCheckBox("Highlighting Off");
	highlightingOffCheckBox.addItemListener(HighlightingOffListener);
	bottomToolBar.add(highlightingOffCheckBox);
	highlightingOffCheckBoxMenuItem = new JCheckBoxMenuItem("Highlighting Off");
	highlightingOffCheckBoxMenuItem.addItemListener(HighlightingOffListener);
	actionsMenu.add(highlightingOffCheckBoxMenuItem);

	// only in 6C model
	if ( CVSim.getSimulationModelName().equals(CVSim.MODEL_6C) ) {
	    exitPatientModeButton = new JButton("Exit Patient Mode");
	    exitPatientModeButton.addActionListener(new ExitPatientModeAction(this));
	    exitPatientModeButton.setEnabled(false);
	    bottomToolBar.add(exitPatientModeButton);
	    exitPatientModeMenuItem = new JMenuItem("Exit Patient Mode");
	    exitPatientModeMenuItem.addActionListener(new ExitPatientModeAction(this));
	    exitPatientModeMenuItem.setEnabled(false);
	    actionsMenu.add(exitPatientModeMenuItem);
	}

	CVSim.sim.addPropertyChangeListener(new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent e) {
		    model.fireTableDataChanged();
		}
	    });

	table = new JTable(model) {
		// In order to highlight the boolean (checkbox) column, you need to override
		// prepareRenderer(); If you try to do this by extending the DefaultCellRenderer
		// class, the checkbox is displayed as a String
		public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		    
		    Component c = super.prepareRenderer(renderer, row, column);
		    Parameter p = (Parameter)(model.getFilteredParameterList().get(row));
	    	    
		    // Highlight row
		    if ( model.highlightList.contains(p) )
			c.setBackground(new Color(250, 250, 126)); //light yellow
		    else
			c.setBackground(Color.WHITE);
		    
		    return c;
		}
	    };

	table.setCellSelectionEnabled(true);

	// set column widths
	table.getColumnModel().getColumn(0).setPreferredWidth(250);
	table.getColumnModel().getColumn(1).setPreferredWidth(10);
	table.getColumnModel().getColumn(2).setPreferredWidth(10);
	table.getColumnModel().getColumn(3).setPreferredWidth(10);
	
	// use custom renderer
	ParameterValueCellRenderer customRenderer = new ParameterValueCellRenderer();
	table.setDefaultRenderer(String.class, customRenderer);
	
	// use custom editor for value column
	// when a user double-clicks on an editable cell and starts typing, 
	// the new value should overwrite the old value, not be appended to it
	table.getColumnModel().getColumn(1).setCellEditor(new ParameterValueCellEditor(new JTextField()));
	scrollPane = new JScrollPane(table);
		
	c.gridx = 0;
	c.gridy = 0;
	c.weightx = 0.5;
	c.weighty = 0.0;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	this.add(topToolBar, c);

	c.gridx = 0;
	c.gridy = 1;
	c.weightx = 0.5;
	c.weighty = 0.0;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	this.add(middleToolBar, c);
	
	c.gridx = 0;
	c.gridy = 2;
	c.weightx = 0.5;
	c.weighty = 0.0;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	this.add(bottomToolBar, c);
	
	c.gridx = 0;
	c.gridy = 3;
	c.weightx = 0.5;
	c.weighty = 1.0;
	c.fill = GridBagConstraints.BOTH;
	this.add(scrollPane, c);
    }

    public String getDisplayMode() { return displayMode; }

    public void setDisplayMode(String mode) {
	
	// deselect table; otherwise, selected values will not be overwritten
 	if (table.getCellEditor() != null)
 	    table.getCellEditor().stopCellEditing();

	if ( mode.equals(DEFAULT) ) {
	    saveButton.setEnabled(true);
	    selectButton.setEnabled(true);
	    loadButton.setEnabled(true);
	    resetButton.setEnabled(true);
	    exitPatientModeButton.setEnabled(false);
	    exitPatientModeMenuItem.setEnabled(false);
	    saveMenuItem.setEnabled(true);
	    selectMenu.setEnabled(true);
	    loadMenuItem.setEnabled(true);
	    resetMenuItem.setEnabled(true);
	    CVSim.gui.toolbar.enableControlSystem(true);
	}
	else if (mode.equals(PATIENT) ) {
	    saveButton.setEnabled(false);
	    selectButton.setEnabled(false);
	    loadButton.setEnabled(false);
	    resetButton.setEnabled(false);
	    exitPatientModeButton.setEnabled(true);
	    exitPatientModeMenuItem.setEnabled(true);
	    saveMenuItem.setEnabled(false);
	    selectMenu.setEnabled(false);
	    loadMenuItem.setEnabled(false);
	    resetMenuItem.setEnabled(false);
	    CVSim.gui.toolbar.enableControlSystem(false);
	}

	displayMode = mode;    
    }
	
    public JMenu getMenuOfActions() {
	return actionsMenu;
    }

    public boolean getHighlightingOff() { return highlightingOff; }

    private ItemListener HighlightingOffListener = new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if ( e.getSource() == highlightingOffCheckBox ) {
		    if ( ((JCheckBox)e.getSource()).isSelected() ) {
			highlightingOff = true;
			highlightingOffCheckBoxMenuItem.setSelected(true);
			// clear existing highlighting
			model.updateHighlightList(null);
		    }
		    else {
			highlightingOff = false;
			highlightingOffCheckBoxMenuItem.setSelected(false);
		    }
		} else if ( e.getSource() == highlightingOffCheckBoxMenuItem ) { 	
		    if ( ((JCheckBoxMenuItem)e.getSource()).isSelected() ) {
			highlightingOff = true;
			highlightingOffCheckBox.setSelected(true);
			// clear existing highlighting
			model.updateHighlightList(null);
		    }
		    else {
			highlightingOff = false;
			highlightingOffCheckBox.setSelected(false);
		    }
		}
	    }
	};

    private ActionListener ClearHighlightingListener = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		model.updateHighlightList(null);
	    }
	};

    private class ExitPatientModeAction extends AbstractAction {
	private Component pc;
	public ExitPatientModeAction(Component c) {
	    super("Exit Patient Mode");
	    pc = c;
	}
	public void actionPerformed(ActionEvent e) {
	    // exit patient mode and reset parameters to default values
	    setDisplayMode(DEFAULT);
	    resetParameters(model.getParameterList());
	}
    }

    
    private class SelectAllParametersAction extends AbstractAction {
	private Component pc;
	public SelectAllParametersAction(Component c) {
	    super("Select All");
	    pc = c;
	}
	public void actionPerformed(ActionEvent e) {
	    selectAllParameters();
	}
    }
    private class SelectVisibleParametersAction extends AbstractAction {
	private Component pc;
	public SelectVisibleParametersAction(Component c) {
	    super("Select Visible");
	    pc = c;
	}
	public void actionPerformed(ActionEvent e) {
	    for (Parameter p: model.getFilteredParameterList())
		model.setParameterSelected(p, true);
	}
    }
    private class SelectNoneParametersAction extends AbstractAction {
	private Component pc;
	public SelectNoneParametersAction(Component c) {
	    super("Select None");
	    pc = c;
	}
	public void actionPerformed(ActionEvent e) {
	    for (Parameter p: model.getParameterList())
		model.setParameterSelected(p, false);
	}
    }

    public void resetParameters(List<Parameter> list) {
	Double defaultValue;
	Double oldValue;

	for (Parameter p: list) {
	    defaultValue = p.getDefaultValue();
	    oldValue = p.getValue();
	    
	    if ( (!oldValue.equals(defaultValue)) ) {
		p.setValue(p.getDefaultValue());
		p.setPercent(100.0);
		System.out.println(p.getName() + " reset to " + defaultValue + " (was " + oldValue + ")");
	    }
	    
	    model.fireTableDataChanged();
	}
	deselectAllParameters();
    }

    private void selectAllParameters() {
	for (Parameter p: model.getParameterList())
	    model.setParameterSelected(p, true);
    }

    private void deselectAllParameters() {
	for (Parameter p: model.getParameterList())
	    model.setParameterSelected(p, false);
    }

    private class ResetParametersAction extends AbstractAction {
	private Component pc;
	private String subAction;

	public ResetParametersAction(Component c, String action) {
	    subAction = action;
	    putValue(NAME, "Restore");
	    pc = c;
	}
	public void actionPerformed(ActionEvent e) {
	    
	    // if the user is trying to reset selected parameters but hasn't selected any,
	    // display error message
	    if ( subAction.equals("Selected") && (model.getSelectedParameterList().size() == 0) ) {
		JOptionPane.showMessageDialog(pc, 
					      "You have not selected any parameters.", 
					      "Error",
					      JOptionPane.ERROR_MESSAGE);
	    } else {
		int ans = JOptionPane.showConfirmDialog
		    (pc, "Do you really want to restore the " + subAction.toLowerCase() + " parameters?",
		     "Restore Parameters?", JOptionPane.YES_NO_OPTION);
		if (ans == JOptionPane.YES_OPTION) {
		    if (subAction.equals("All")) {
			setDisplayMode(DEFAULT);
			resetParameters(model.getParameterList());
		    } else if (subAction.equals("Selected"))
			resetParameters(model.getSelectedParameterList());
		    else
			throw new UnsupportedOperationException();
		    
		}
	    }
	}
    }

    private void WriteParameterFile(List<Parameter> list, File file) throws Exception {

	Properties prop = new Properties();
	for (Parameter p: list) {
	    prop.setProperty(p.getName(), p.getValue().toString());
	}

	FileOutputStream fos = new FileOutputStream(file);
	prop.storeToXML(fos, "CVSIM Simulation Parameter File. (Written by Version: " + 
			CVSim.versionNumber + ", Model: " + CVSim.simulationModelName + ")");
	fos.close();
    
    }

    private class SaveParametersToFileAction extends AbstractAction {
	private JFileChooser fc;
	private File file;
	private Component pc;
	private String subAction;

	public SaveParametersToFileAction(Component c, String action) {
	    subAction = action;
	    putValue(NAME, "Snapshot");
	    pc = c;
	}
	public void actionPerformed(ActionEvent e) {
	    fc = new JFileChooser();

	    // if the user is trying to save selected parameters but hasn't selected any,
	    // display error message
	    if ( subAction.equals("Selected") && (model.getSelectedParameterList().size() == 0) ) {
		JOptionPane.showMessageDialog(pc, 
					      "You have not selected any parameters.", 
					      "Error",
					      JOptionPane.ERROR_MESSAGE);
	    } else {
		if (fc.showSaveDialog(pc) == JFileChooser.APPROVE_OPTION) {
		    File file = fc.getSelectedFile();
		    System.out.println("Saving parameters to: " + file);
		    try {
			if (subAction.equals("All"))
			    WriteParameterFile(model.getParameterList(), file);
			else if (subAction.equals("Selected"))
			    WriteParameterFile(model.getSelectedParameterList(), file);
			else
			    throw new UnsupportedOperationException();
			
			deselectAllParameters();
			
		    } catch (Exception ex) {
			System.out.println("Error saving parameters. " + ex);
			JOptionPane.showMessageDialog
			    (pc, "Error Saving Parameters to " + file.toString() + 
			     "\n-----\nException Says: " + ex, 
			     "File Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
	    }
	}
    }

    private void ReadParameterFile(List<Parameter> list, File file) throws Exception {
	double value;
	double oldvalue;
	boolean found = false;
	String prop;

	Properties props = new Properties();
	FileInputStream fis = new FileInputStream(file);

	props.loadFromXML(fis); // throws exception

	for (Enumeration propNames = props.propertyNames(); propNames.hasMoreElements() ;) {
	    prop = (String)propNames.nextElement();
	    for (Parameter param: list) {
		if (param.getName().equals(prop)) {
		    found = true;
		    oldvalue = param.getValue();
		    value = Double.valueOf(props.getProperty(prop)); // throws exception
		    if (oldvalue != value) {
			param.setValue(value);
			System.out.println("Parameter \"" + param + "\" set to " + value + " (was " + oldvalue + ")");
		    }
		}
	    }
	    if (!found)
		System.out.println("\"" + prop + "\" did not match parameter");
	}
    }

    private class LoadParametersFromFileAction extends AbstractAction {
	private JFileChooser fc;
	private File file;
	private Component pc;
	private String subAction;

	public LoadParametersFromFileAction(Component c, String action) {
	    subAction = action;
	    putValue(NAME, "Load");
	    pc = c;
	}
	public void actionPerformed(ActionEvent e) {
	    fc = new JFileChooser();

	    // if the user is trying to save selected parameters but hasn't selected any,
	    // display error message
	    if ( subAction.equals("Selected") && (model.getSelectedParameterList().size() == 0) ) {
		JOptionPane.showMessageDialog(pc, 
					      "You have not selected any parameters.", 
					      "Error",
					      JOptionPane.ERROR_MESSAGE);
	    } else {
		if (fc.showOpenDialog(pc) == JFileChooser.APPROVE_OPTION) {
		    File file = fc.getSelectedFile();
		    System.out.println("Loading Parameters From: " + file);
		    try{
			if (subAction.equals("All"))
			    ReadParameterFile(model.getParameterList(), file);
			else if (subAction.equals("Selected"))
			    ReadParameterFile(model.getSelectedParameterList(), file);
			else 
			    throw new UnsupportedOperationException();
			
			deselectAllParameters();

		    } catch (Exception ex) {
			System.out.println("Error loading parameters. Some parameter values may have changed." + ex);
			JOptionPane.showMessageDialog
			    (pc, "Error Loading Parameters from " + file.toString() + 
			     "\n-----\nException Says: " + ex, 
			     "File Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
	    }
	}
    }

    private String[] getModelCategoryChoices() {
	List<String> choices = model.getCategoryNames();
	choices.add(0,CHOICE_ALL);
	String[] choicesArray = new String[choices.size()];
	choices.toArray(choicesArray);
	// alphabetize
	Arrays.sort(choicesArray);
	return choicesArray;
    }

    private String[] getModelParameterTypeChoices() {
	List<String> choices = model.getFilteredParameterTypeNames();
	choices.add(0,CHOICE_ALL);
	String[] choicesArray = new String[choices.size()];
	choices.toArray(choicesArray);
	// alphabetize
	Arrays.sort(choicesArray);
	return choicesArray;
    }

    private ActionListener CategoryComboBoxListener = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String selection = (String)((JComboBox)e.getSource()).getSelectedItem();
		setCategoryComboBoxSelection(selection);
	    }
	};

    public void setCategoryComboBoxSelection(String selection) {
	if (selection.equals(CHOICE_ALL)) 
	    selection = null;
	model.setCategoryFilter(selection);

	String oldTypeSelection = (String)typeComboBox.getSelectedItem();
	typeComboBox.setModel(new DefaultComboBoxModel(getModelParameterTypeChoices()));
	typeComboBox.setSelectedItem(oldTypeSelection);
	if (!((String)typeComboBox.getSelectedItem()).equals(oldTypeSelection))
	    typeComboBox.setSelectedItem(CHOICE_ALL);
    }
    

    private ActionListener TypeComboBoxListener = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String selection = (String)((JComboBox)e.getSource()).getSelectedItem();
		setTypeComboBoxSelection(selection);
	    }};

    public void setTypeComboBoxSelection(String selection) {
	if (selection.equals(CHOICE_ALL)) 
	    selection = null;
	model.setTypeFilter(selection);
    }

    public void recenterTable(int centerRow) {
	// get scrollpane viewport
 	JViewport viewport = scrollPane.getViewport();
	// Get x,y location of the upper left corner of the viewport 
	Point startingPoint = viewport.getViewPosition();
	// Convert x,y location to row number
	int oldTopRow = table.rowAtPoint(startingPoint);
	// Get the size of the viewport
	Dimension size = viewport.getExtentSize();
	// Given the size of the viewport, find out how many rows can be displayed
	int numRows = (int)(size.getHeight()/table.getRowHeight());
	// Need to calculate which row will be at the top of the table once
	// the table is recentered
	int newTopRow = (centerRow - (int)(numRows/2));
	if (newTopRow < 0) 
	    newTopRow = 0;
	// Convert row number to y location
	int newY = startingPoint.y + (newTopRow-oldTopRow)*table.getRowHeight();
	// Set the view to the new y position
	viewport.setViewPosition(new Point(startingPoint.x, newY));
    }

    ///////
    ///////
    private class DropDownToolbarButton extends JButton {
	
	private JPopupMenu buttonMenu;
	
	public DropDownToolbarButton(JMenu menu) {
	    super(menu.getText());	  
	    buttonMenu = menu.getPopupMenu();
        
	    this.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			Component c = (Component)e.getSource();
			buttonMenu.show(c, 0, c.getHeight());
		    }
		});
	}

	public void addMenuItem(JMenuItem mitem) {
	    buttonMenu.add(mitem);
	}
    }


    // custom renderer
    // show non-default parameter values in red
    public class ParameterValueCellRenderer extends DefaultTableCellRenderer
    {
	public Component getTableCellRendererComponent(JTable table, Object value, 
						       boolean isSelected, boolean hasFocus, 
						       int row, int column)
	{
	    Component cell = super.getTableCellRendererComponent(table, value, isSelected, 
								 hasFocus, row, column);
	    Parameter p = (Parameter)(model.getFilteredParameterList().get(row));
	    
	    // set default colors
	    cell.setForeground( Color.BLACK );
	    cell.setBackground(Color.WHITE);

	    // for all columns
	    // Highlight row
	    if ( model.highlightList.contains(p) ) 
	        cell.setBackground(new Color(250, 250, 126)); //light yellow
	    
	    // only for value column
	    if (column == model.COL_VALUE) {
		// right justify
		setHorizontalAlignment(SwingConstants.RIGHT);

		// Set tooltip
		String str = String.format("Allowed range: %.2f to %.2f", p.getMin(), p.getMax());
		setToolTipText(str);

		// for DEFAULT mode: if the current value is not the default value, 
		// display the value in red
		// for PATIENT mode: if the current value is not 100%, 
		//display the value in red
		Double defaultValue = p.getDefaultValue();
		Double currentValue = Double.valueOf(value.toString());
		Double percentage = p.getPercent();
		
		if ( displayMode.equals(DEFAULT) )  {
		    if ( !currentValue.equals(defaultValue) ) 
			cell.setForeground( Color.RED );
		} else if ( displayMode.equals(PATIENT) ) {
		    if (!(percentage ==  100))  
			cell.setForeground( Color.RED );
		}    
	    }
	    else // if not Value col 
  		setHorizontalAlignment(SwingConstants.LEFT);
	    
	    return this;
	}
    }


    // custom cell editor
    // when a user double-clicks on an editable cell and starts typing, the
    // new value should overwrite the old value, not be appended to it
    private class ParameterValueCellEditor extends DefaultCellEditor implements FocusListener {
	
	JTextField tf;
	
	public ParameterValueCellEditor(JTextField tf)
	{
	    super(tf);
	    this.tf = tf;
	    tf.setHorizontalAlignment(JTextField.RIGHT);
	    tf.addFocusListener(this);
	    clickCountToStart = 1;
	}

	// DefaultCellEditor methods
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	    tf.setText(value.toString());
	    return tf;
	}

	// FocusListener methods
	public void focusGained(FocusEvent e) {
	    tf.selectAll();
	}
	
	public void focusLost(FocusEvent e) {}
    }
}


