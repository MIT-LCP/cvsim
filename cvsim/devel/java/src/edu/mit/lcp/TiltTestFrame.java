package edu.mit.lcp;

import edu.mit.lcp.C21_comp_backend.main;
import edu.mit.lcp.C21_comp_backend.Parameter_vector;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.AbstractAction;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import java.text.NumberFormat;

public class TiltTestFrame extends JFrame implements PropertyChangeListener {

    public static final String TILTTEST = "TILTTEST";

    private static final int tiltTimeIndex = 92; 
    private static final int tiltAngleIndex = 91; 
    private static final int maxVolumeLossIndex = 74;

    private JFrame frame;
    private JToggleButton tiltTestOnButton;
    private JToggleButton tiltTestOffButton;
    private JFormattedTextField tiltTimeField;
    private JFormattedTextField tiltAngleField;
    private JFormattedTextField maxVolumeLossField;
   
    private CSimulation21C sim = (CSimulation21C)(CVSim.sim);
    private Parameter_vector pvec = sim.getParameterVector();
    
    private double tiltTime;
    private double tiltAngle;
    private double maxVolumeLoss;

    private boolean tiltTest = false;
    private double  tiltStartTime = 0.0;
    private double  tiltStopTime = 0.0;

    public TiltTestFrame() {

	frame = new JFrame("Perform Tilt Test");
	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

	NumberFormat numberFormat = NumberFormat.getNumberInstance();

	// tilt time
	JLabel tiltTimeLabel = new JLabel("Tilt Time:");
	tiltTime = pvec.getVec(tiltTimeIndex);
	tiltTimeField = new JFormattedTextField(numberFormat);
	tiltTimeField.setValue(new Double(tiltTime));
	tiltTimeField.setColumns(3);
	tiltTimeField.addPropertyChangeListener(this);
	
	// tilt angle
	JLabel tiltAngleLabel = new JLabel("Tilt Angle:");
	tiltAngle = pvec.getVec(tiltAngleIndex);
	tiltAngleField = new JFormattedTextField(numberFormat);
	tiltAngleField.setValue(new Double(tiltAngle));
	tiltAngleField.setColumns(3);
	tiltAngleField.addPropertyChangeListener(this);

	// max volume loss
	JLabel maxVolumeLossLabel = new JLabel("Max. Volume Loss:");
	maxVolumeLoss = pvec.getVec(maxVolumeLossIndex);
	maxVolumeLossField = new JFormattedTextField(numberFormat);
	maxVolumeLossField.setValue(new Double(maxVolumeLoss));
	maxVolumeLossField.setColumns(3);
	maxVolumeLossField.addPropertyChangeListener(this);

	tiltTestOnButton = new JToggleButton("Start Tilt");
	tiltTestOnButton.addActionListener(new tiltTestOnAction(this));
	tiltTestOffButton = new JToggleButton("Stop Tilt");
	tiltTestOffButton.addActionListener(new tiltTestOffAction(this));

	JPanel contentPane = new JPanel(new SpringLayout());
	contentPane.add(tiltTimeLabel);
	contentPane.add(tiltTimeField);
	contentPane.add(new JLabel("seconds"));
	contentPane.add(tiltAngleLabel);
	contentPane.add(tiltAngleField);
	contentPane.add(new JLabel("degrees"));
	contentPane.add(maxVolumeLossLabel);
	contentPane.add(maxVolumeLossField);
	contentPane.add(new JLabel("mL"));
	contentPane.add(tiltTestOnButton);
	contentPane.add(tiltTestOffButton);
	contentPane.add(new JLabel(""));
	SpringUtilities.makeCompactGrid(contentPane,
                         4, 3, // rows, cols
                         5, 5, // initialX, initialY
                         5, 5);// xPad, yPad
 	frame.setContentPane(contentPane);
	frame.pack();

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension size = frame.getSize();
	frame.setLocation(screenSize.width/2 - size.width/2,
			  screenSize.height/2 - size.height/2);
    }
    
    public void setTiltTest(boolean b) {
	boolean old = getTiltTest();
	tiltTest = b;
	firePropertyChange(TILTTEST, old, b);
    }
    
    public boolean getTiltTest() { return tiltTest; }
    protected int getTiltTest_C() { return (getTiltTest())?1:0; }

    public double getTiltStartTime() { return tiltStartTime; }
    public void setTiltStartTime() { tiltStartTime = sim.getOutput(0); }
    public double getTiltStopTime() { return tiltStopTime; }
    public void setTiltStopTime() { tiltStopTime = sim.getOutput(0); }

    public void performTiltTest() {
	if ( !(frame.isVisible()) )
	    frame.setVisible(true);
	if ( frame.getExtendedState() == JFrame.ICONIFIED )
	    frame.setExtendedState(JFrame.NORMAL);
    }

    public void propertyChange(PropertyChangeEvent e) {
	Object source = e.getSource();
	if ( source == tiltTimeField ) {
	    tiltTime = ((Number)tiltTimeField.getValue()).doubleValue();
	    main.updateParameter(tiltTime, 
				 pvec, 
				 tiltTimeIndex);
	}
	if ( source == tiltAngleField ) {
	    tiltAngle = ((Number)tiltAngleField.getValue()).doubleValue();
	    main.updateParameter(tiltAngle, 
				 pvec, 
				 tiltAngleIndex);
	}
	if ( source == maxVolumeLossField ) {
	    maxVolumeLoss = ((Number)maxVolumeLossField.getValue()).doubleValue();
	    main.updateParameter(maxVolumeLoss, 
				 pvec, 
				 maxVolumeLossIndex);
	}
    }

    private class tiltTestOnAction extends AbstractAction {
	private Component pc;
	public tiltTestOnAction(Component c) {
	    super("Tilt Test On");
	    pc = c;
	}
	public void actionPerformed(ActionEvent event) {
	    tiltTestOnButton.setSelected(true);
	    tiltTestOffButton.setSelected(false);
	    setTiltStartTime();
	    setTiltTest(true);
	    System.out.println("Tilt Test ON");
	}
    }

    private class tiltTestOffAction extends AbstractAction {
	private Component pc;
	public tiltTestOffAction(Component c) {
	    super("Tilt Test Off");
	    pc = c;
	}
	public void actionPerformed(ActionEvent event) {
	    tiltTestOffButton.setSelected(true);
	    tiltTestOnButton.setSelected(false);
	    setTiltStopTime();
	    setTiltTest(false);
	    System.out.println("Tilt Test OFF");
	}
    }

}
