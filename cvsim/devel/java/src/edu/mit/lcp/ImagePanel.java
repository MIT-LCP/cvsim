package edu.mit.lcp;

import javax.swing.JPanel;

public abstract class ImagePanel extends JPanel {

    public void highlightParameterTable(String category) {
	if ( !(CVSim.gui.parameterPanel.getHighlightingOff()) ) 
	    if ( !(CVSim.gui.parameterPanel.categoryComboBox.getSelectedItem().equals(category)) )
		CVSim.gui.parameterPanel.recenterTable(CVSim.gui.parameterPanel.model.updateHighlightList(category));
    }

    public void filterParameterTable(String category) {
	CVSim.gui.parameterPanel.categoryComboBox.setSelectedItem(category);
	CVSim.gui.parameterPanel.typeComboBox.setSelectedItem("ALL");
	CVSim.gui.parameterPanel.model.updateHighlightList(null);
    }
    
}
