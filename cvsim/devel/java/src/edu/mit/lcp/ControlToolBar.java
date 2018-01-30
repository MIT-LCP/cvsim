package edu.mit.lcp;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class ControlToolBar extends JToolBar {

    private JToggleButton _startButton;
    private JToggleButton _stopButton;
    private JToggleButton _abReflexOnButton; 
    private JToggleButton _abReflexOffButton;
    private JToggleButton _cpReflexOnButton;
    private JToggleButton _cpReflexOffButton;
    
    public ControlToolBar() {

	setRollover(true);
	setFloatable(false);
        
	ImageIcon startIcon = new ImageIcon(getClass().getResource("/icons/go.gif"));
        _startButton = new JToggleButton(startIcon);
        _startButton.addActionListener(new StartSimulationAction(this));
	_startButton.setToolTipText("Start Simulation");

	ImageIcon stopIcon = new ImageIcon(getClass().getResource("/icons/stop.gif"));
        _stopButton = new JToggleButton(stopIcon);
        _stopButton.addActionListener(new StopSimulationAction(this));
	_stopButton.setToolTipText("Stop Simulation");

	ImageIcon ABOnIcon = new ImageIcon(getClass().getResource("/icons/ABgo.gif"));
        _abReflexOnButton = new JToggleButton(ABOnIcon);
        _abReflexOnButton.addActionListener(new ABReflexOnAction(this));
	_abReflexOnButton.setToolTipText("Arterial Baroreflex Control System On");
        
	ImageIcon ABOffIcon = new ImageIcon(getClass().getResource("/icons/ABstop.gif"));
        _abReflexOffButton = new JToggleButton(ABOffIcon);
        _abReflexOffButton.addActionListener(new ABReflexOffAction(this));
	_abReflexOffButton.setToolTipText("Arterial Baroreflex Control System Off");

	ImageIcon CPOnIcon = new ImageIcon(getClass().getResource("/icons/CPgo.gif"));
        _cpReflexOnButton = new JToggleButton(CPOnIcon);
        _cpReflexOnButton.addActionListener(new CPReflexOnAction(this));
	_cpReflexOnButton.setToolTipText("Cardiopulmonary Reflex Control System On");

	ImageIcon CPOffIcon = new ImageIcon(getClass().getResource("/icons/CPstop.gif"));
        _cpReflexOffButton = new JToggleButton(CPOffIcon);
        _cpReflexOffButton.addActionListener(new CPReflexOffAction(this));
	_cpReflexOffButton.setToolTipText("Cardiopulmonary Reflex Control System Off");

	add(_startButton);
	add(_stopButton);
	add(_abReflexOnButton);
	add(_abReflexOffButton);
	add(_cpReflexOnButton);
	add(_cpReflexOffButton);
    }

    public JMenu createSimulationMenu() {
	JMenu simMenu = new JMenu("Simulation");
	simMenu.add(new StartSimulationAction(this));
	simMenu.add(new StopSimulationAction(this));
	simMenu.add(new ABReflexOnAction(this));
	simMenu.add(new ABReflexOffAction(this));
	simMenu.add(new CPReflexOnAction(this));
	simMenu.add(new CPReflexOffAction(this));
	return simMenu;
    }

    public void enableControlSystem(boolean b) {
	_abReflexOnButton.setEnabled(b);
	_abReflexOffButton.setEnabled(b);
	_cpReflexOnButton.setEnabled(b);
	_cpReflexOffButton.setEnabled(b);
    }

    // Actions
    private class StartSimulationAction extends AbstractAction {
	private Component pc;
	public StartSimulationAction(Component c) {
	    super("Start Simulation");
	    pc = c;
	}
	public void actionPerformed(ActionEvent e) {
	    _startButton.setSelected(true);
	    _stopButton.setSelected(false);
	    CVSim.simThread.setPeriod(10);
	    CVSim.simThread.start();
	    System.out.println("Start");
	}
    }    

    private class StopSimulationAction extends AbstractAction {
	private Component pc;
	public StopSimulationAction(Component c) {
	    super("Stop Simulation");
	    pc = c;
	}
	public void actionPerformed(ActionEvent event) {
	    _stopButton.setSelected(true);
	    _startButton.setSelected(false);
	    CVSim.simThread.stop();
	    System.out.println("Stop");
	}	
    }
    
    private class ABReflexOnAction extends AbstractAction {
	private Component pc;
	public ABReflexOnAction(Component c) {
	    super("Arterial Baroreflex Control System ON");
	    pc = c;
	}
	public void actionPerformed(ActionEvent event) {
	    _abReflexOnButton.setSelected(true);
	    _abReflexOffButton.setSelected(false);
	    CVSim.sim.setABReflex(true);
	    System.out.println("AB Reflex ON");
	}
    }

    private class ABReflexOffAction extends AbstractAction {
	private Component pc;
	public ABReflexOffAction(Component c) {
	    super("Arterial Baroreflex Control System OFF");
	    pc = c;
	}
	public void actionPerformed(ActionEvent event) {
	    _abReflexOffButton.setSelected(true);
	    _abReflexOnButton.setSelected(false);
	    CVSim.sim.setABReflex(false);
	    System.out.println("AB Reflex OFF");
	}
    }
    
    private class CPReflexOnAction extends AbstractAction {
	private Component pc;
	public CPReflexOnAction(Component c) {
	    super("Cardiopulmonary Reflex Control System ON");
	    pc = c;
	}
	public void actionPerformed(ActionEvent event) {
	    _cpReflexOnButton.setSelected(true);
	    _cpReflexOffButton.setSelected(false);
	    CVSim.sim.setCPReflex(true);
	    System.out.println("CP Reflex ON");
	}
    }

    private class CPReflexOffAction extends AbstractAction {
	private Component pc;
	public CPReflexOffAction(Component c) {
	    super("Cardiopulmonary Reflex Control System OFF");
	    pc = c;
	}
	public void actionPerformed(ActionEvent event) {
	    _cpReflexOffButton.setSelected(true);
	    _cpReflexOnButton.setSelected(false);
	    CVSim.sim.setCPReflex(false);
	    System.out.println("CP Reflex OFF");
	}
    }

}
