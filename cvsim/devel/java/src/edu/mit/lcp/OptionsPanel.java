package edu.mit.lcp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptionsPanel extends JPanel {

    private JSlider _speedSlider;

    public OptionsPanel() {

	// Create slider for simulation speed
	_speedSlider = new JSlider(1,10,1); // min,max,default
	_speedSlider.setMajorTickSpacing(1);
	_speedSlider.setMinorTickSpacing(1);
	_speedSlider.setSnapToTicks(true);
	_speedSlider.setPaintTicks(true);
	_speedSlider.setPaintLabels(true);
	_speedSlider.addChangeListener(speedSliderListener);
	_speedSlider.setBorder(
	   BorderFactory.createCompoundBorder(
	      BorderFactory.createTitledBorder("Simulation Speed"),
	      BorderFactory.createEmptyBorder(10,10,10,10)));

	this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
	this.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.insets = new Insets(0,0,0,0);
	this.add(_speedSlider, c);
    }

    // Listener
    ChangeListener speedSliderListener = new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
		if (!_speedSlider.getValueIsAdjusting()) {
		    long period = 10;
		    int speed = (int)_speedSlider.getValue();
		    int comp = (int)((double)(period*speed));	
		    CVSim.sim.setDataCompressionFactor(comp);
		    CVSim.simThread.setPeriod(period);
		} 
	    } 
	}; 
    
}
