package edu.mit.lcp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;


public class Patient {

    private String _name;
    private String _history;
    private List<Parameter>  parameterList;
    private List<Double> valueList;
    private JFrame frame;

    public Patient(String name, String history) {
	_name = name;
	_history = history;
	parameterList = new ArrayList();
	valueList = new ArrayList();
	frame = createPatientHistoryFrame();
    }

    private JFrame createPatientHistoryFrame() {
	JTextPane textPane = new JTextPane();
	textPane.setEditable(false);
	textPane.setText(_history);
	JScrollPane scrollPane = new JScrollPane(textPane);
	
	JFrame frame = new JFrame("Patient History: " + _name);
	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	
	JPanel contentPane = new JPanel(new BorderLayout());
	contentPane.add(scrollPane, BorderLayout.CENTER);
	contentPane.setOpaque(true);
	frame.setContentPane(contentPane);

	int width = 300;
	int height = 300;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	frame.setSize(width, height);
	frame.setLocation(screenSize.width/2 - width/2,
			  screenSize.height/2 - height/2);
	return frame;
    }

    public void addParameterSetting(String name, Double value) {
	Parameter p = CVSim.sim.getParameterByName(name);
	if ( p != null ) { 
	    parameterList.add(p);
	    valueList.add(value);
	}
	else {
	    System.out.print("Could not load " + name + " from patient data.\n");
	}
    }

    public void loadParameterSettings() {

	// reset all parameters
	CVSim.gui.parameterPanel.resetParameters(CVSim.sim.getParameterList());	    

	// Set parameter table model display mode to PATIENT
	CVSim.gui.parameterPanel.setDisplayMode(CVSim.gui.parameterPanel.PATIENT);

	// load patient parameter settings
	for (int i=0; i < parameterList.size(); i++) {
	    (parameterList.get(i)).setValue(valueList.get(i));
	    (parameterList.get(i)).setPtModeDefaultValue(valueList.get(i));
	}
    }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }
    public String getHistory() { return _history; }
    public void setHistory(String history) { _history = history; }
    public JFrame getFrame() { return frame; }
}
