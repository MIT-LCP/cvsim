package edu.mit.lcp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class LogPanel extends JPanel {

    private JLabel label;
    private JButton onButton;
    private JButton offButton;
    private JLabel logFileLabel;
    private Action startLoggingAction;
    private Action stopLoggingAction;
    private JToolBar toolBar;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private List<String> logList;
    private List<Parameter> parameterList;
    private List<SimulationOutputVariable> outputList;
    private OutputFile outputFile;

    public LogPanel() {
	
	super();
	label = new JLabel("Logging: ");
	startLoggingAction = new StartLoggingSelectedVariablesToFileAction(this);
	onButton = new JButton(startLoggingAction);
	stopLoggingAction = new StopLoggingSelectedVariablesToFileAction();
	offButton = new JButton(stopLoggingAction);
	logFileLabel = new JLabel("");;
	logFileLabel.setFont(new Font("monospaced", Font.PLAIN, 10));
	
	toolBar = new JToolBar();
	toolBar.setFloatable(false);
	toolBar.setRollover(true);
	toolBar.add(label);
	toolBar.add(onButton);
	toolBar.add(offButton);
	toolBar.add(logFileLabel);
	
	logList = new ArrayList();
	textArea = new JTextArea(5, 20);
	textArea.setEditable(false);
	scrollPane = new JScrollPane(textArea);

	setLayout(new BorderLayout());
	add(toolBar, BorderLayout.PAGE_START);
	add(scrollPane, BorderLayout.CENTER);
    }

    private class StartLoggingSelectedVariablesToFileAction extends AbstractAction {
	private JFileChooser fc;
	private Component pc;

	public StartLoggingSelectedVariablesToFileAction(Component c) {
	    super("Start");
	    pc = c;
	}

	public void actionPerformed(ActionEvent e) {

	    textArea.setText(null);

	    List<Parameter> reorderedParameterList = new ArrayList();
	    List<SimulationOutputVariable> reorderedOutputList = new ArrayList();

	    parameterList = CVSim.gui.parameterPanel.model.getSelectedParameterList();
	    outputList = CVSim.gui.outputPanel.model.getSelectedOutputList();

	    // reorder output list to match order of output table
	    for (SimulationOutputVariable o: CVSim.sim.getOutputVariables() ) {
		if (outputList.contains(o)) {
		    reorderedOutputList.add(o);
		}
	    }
	    
	    // reorder parameter list to match order of parameter table
	    for (Parameter p: CVSim.sim.getParameterList() ) {
		if (parameterList.contains(p)) {
		    reorderedParameterList.add(p);
		}
	    }
	    
	    // Create default filename based on date and time
	    Date date = new Date();
	    SimpleDateFormat df = new SimpleDateFormat("MMddyyyy_HHmmss");
	    String defaultFilename = String.format("cvsim_output_" + df.format(date) + ".txt");
	    File defaultFile = new File(defaultFilename);
	    
	    File file = null;
	    fc = new JFileChooser();
	    fc.setSelectedFile(defaultFile);
	    
	    if(fc.showSaveDialog(pc) == JFileChooser.APPROVE_OPTION) {
		file = fc.getSelectedFile();
		try { 
		    if (file.createNewFile()) {
			if (file.canWrite()) {
			    // then the file is valid
			} else {
			    JOptionPane.showMessageDialog
				(pc, "Cannot write to file " + file.toString(), 
				 "File Error", JOptionPane.ERROR_MESSAGE);
			    file = null;
			} 
		    } else {
			int ans = JOptionPane.showConfirmDialog
			    (pc, file.toString() + 
			     " Already Exists. Do you want to overwrite the file?", 
			     "Overwrite File?", JOptionPane.YES_NO_OPTION,
			     JOptionPane.QUESTION_MESSAGE); 
			if (ans == JOptionPane.YES_OPTION) {
			    if (file.canWrite()) {
				// then the file is valid
			    } else {
				JOptionPane.showMessageDialog
				    (pc, "Cannot write to file " + file.toString(), 
				     "File Error", JOptionPane.ERROR_MESSAGE);
				file = null;
			    } 
			}
		    }
		} catch (Exception ex) { 
		    JOptionPane.showMessageDialog
			(pc, "Cannot write to file " + file.toString(), 
			 "File Error", JOptionPane.ERROR_MESSAGE);
		    file = null;
		}

		if (file != null) {
		    setEnabled(false);
		    stopLoggingAction.setEnabled(true);
		    logFileLabel.setText(file.toString());
		    
		    outputFile = new OutputFile(reorderedParameterList, reorderedOutputList, file);
		    outputFile.startLogging();

		    // display list of variables being logged in scrollPane
		    textArea.append("Logging the following variables:\n\n");
		    if (reorderedOutputList.size() > 0) {
			textArea.append("Outputs:\n");
			for (SimulationOutputVariable o: reorderedOutputList) {
			    textArea.append(o.getDescription() + "\n");
			}
		    }

		    if (reorderedParameterList.size() > 0) {
			textArea.append("\nParameters:\n");
			for (Parameter p: reorderedParameterList) {
			    textArea.append(p.getName() + "\n");
			}
		    }
		}
	    }
	}
    }
    
    private class StopLoggingSelectedVariablesToFileAction extends AbstractAction {
	StopLoggingSelectedVariablesToFileAction() {
	    super("Stop");
	}

	public void actionPerformed(ActionEvent e) {
	    setEnabled(false);
	    startLoggingAction.setEnabled(true);
	    logFileLabel.setText("");

	    outputFile.stopLogging();
	    outputFile = null;
	}
    }

}
