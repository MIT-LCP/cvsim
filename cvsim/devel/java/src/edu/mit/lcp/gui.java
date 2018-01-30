package edu.mit.lcp;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;
import javax.swing.BorderFactory; 
import javax.swing.border.EtchedBorder; 
import javax.swing.border.Border;
import net.infonode.docking.*;
import net.infonode.docking.util.*;
import net.infonode.util.*;
import net.infonode.tabbedpanel.*;

// gui.java
// create main GUI window and show it

public class gui {

    static JFrame frame;
    public ParameterPanel parameterPanel;
    public OutputPanel outputPanel;
    public java.util.List<PlotWindow> plotWindows = new ArrayList<PlotWindow>();
    public List<View> stripChartViews = new ArrayList<View>();
    public List<View> XYPlotViews = new ArrayList<View>();
    private List<Patient> patientList;
    public ControlToolBar toolbar;
    ViewMap viewMap;
    TabWindow stripChartTabWindow;
    TabWindow XYPlotTabWindow;
    TiltTestFrame tiltTestFrame;
    private ImagePanel imagePanel;
   
    public gui () {
	frame = new JFrame("CVSim Version " + CVSim.versionNumber.toString() +
			   " -- Model: " + CVSim.simulationModelName);
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public void createAndShowGUI() {
	// window decorations.
 	try {
 	    UIManager.setLookAndFeel(
 	      UIManager.getCrossPlatformLookAndFeelClassName());		     
 	} catch (Exception e) { }
	
	// frame settings
	int insets = 50;
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	Dimension size = new Dimension(screenSize.width-insets*2, screenSize.height-insets*2);
	frame.setBounds(insets, insets, size.width, size.height);
	frame.setSize(size);

	frame.addWindowListener(new FrameSynchListener());

	// View map
	viewMap = new ViewMap();

	// Default strip chart
	PlotWindow stripChart = new PlotWindow(new TraceListModel(), PlotWindow.STRIPCHART);	  
	View stripChartDefaultView = new View("Strip Chart 1", null, stripChart);
	stripChartDefaultView.getWindowProperties().setCloseEnabled(false);
	stripChartDefaultView.addListener(new DockingWindowCleanupListener());
	stripChartViews.add(stripChartDefaultView);
	viewMap.addView(1, stripChartDefaultView);
	PlotPanelStripChart stripChartPlot = (PlotPanelStripChart)(stripChart.getPlot());
	CVSim.sim.addChangeListener(stripChartPlot.sourceDataChanged); 	
	stripChartPlot.createNewTrace(CVSim.sim.getOutputVariable("LVP"));

	// Default XY plot
	PlotWindow parametric = new PlotWindow(new TraceListModel(), PlotWindow.PARAMETRIC);	  
	View plotDefaultView = new View("Plot 1", null, parametric); 
	plotDefaultView.getWindowProperties().setCloseEnabled(false);
	XYPlotViews.add(plotDefaultView);
	viewMap.addView(2, plotDefaultView);
	PlotPanelXYChart parametricPlot = (PlotPanelXYChart)(parametric.getPlot());
	CVSim.sim.addChangeListener(parametricPlot.sourceDataChanged); 
	parametricPlot.createNewTrace(CVSim.sim.getOutputVariable("LVV"), CVSim.sim.getOutputVariable("LVP"));
	
	// Root window
	RootWindow rootWindow = DockingUtil.createRootWindow(viewMap, true);
	
	// Tab window for strip charts
	stripChartTabWindow = new TabWindow(stripChartDefaultView);
	stripChartTabWindow.getWindowProperties().setCloseEnabled(false);
	
	// Tab window for XY plots
	XYPlotTabWindow = new TabWindow(plotDefaultView);
	XYPlotTabWindow.getWindowProperties().setCloseEnabled(false);

	SplitWindow splitWindow = new SplitWindow(true, stripChartTabWindow, XYPlotTabWindow);
	rootWindow.setWindow(splitWindow);

	// Patient List
	patientList = new ArrayList();

	// Parameter and Output Tables
	parameterPanel = new ParameterPanel();
	outputPanel = new OutputPanel();
	JTabbedPane tabbedPane = new JTabbedPane();
	tabbedPane.addTab("Simulation Variables", parameterPanel);
	tabbedPane.addTab("Simulation Outputs", outputPanel);
	Border compoundBorder = BorderFactory.createCompoundBorder(
								   BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
								   BorderFactory.createEmptyBorder(5,5,5,5));
	tabbedPane.setBorder(compoundBorder);

	// Put back via menu items	
//	JPanel logListPanel = new LogPanel();
// 	optionsPanel = new OptionsPanel();

	if ( CVSim.getSimulationModelName().equals(CVSim.MODEL_6C) )
	    imagePanel = new ImagePanel6C();
	else
	    imagePanel = new ImagePanel21C();
	imagePanel.setBorder(compoundBorder);
	JScrollPane scrollPane = new JScrollPane(imagePanel);
 	// get scrollpane viewport
  	JViewport viewport = scrollPane.getViewport();
 	// Get x,y location of the upper left corner of the viewport 
 	Point startingPoint = viewport.getViewPosition();
 	// Set the view to the new y position
 	viewport.setViewPosition(new Point(startingPoint.x, startingPoint.y+35));
 
 	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane, scrollPane);
         splitPane.setDividerLocation((int)(size.width*.45));

	// create toolbar and menubar
	toolbar = new ControlToolBar();
	frame.setJMenuBar(createMenuBar());

	// content pane
	JPanel contentPane = new JPanel();
	contentPane.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();

	c.gridx = 0;
	c.gridy = 0;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	c.insets = new Insets(0, 2, 0, 0);
	contentPane.add(toolbar, c);
	
	c.gridx = 0;
	c.gridy = 1;
	c.weightx = 1.0;
	c.weighty = 0.50;
	c.fill = GridBagConstraints.BOTH;
	c.anchor = GridBagConstraints.CENTER;
	c.insets = new Insets(0, 2, 0, 0);
	contentPane.add(splitPane, c);

	c.gridx = 0;
	c.gridy = 2;
	c.weightx = 1.0;
	c.weighty = 0.50;
	c.gridwidth = 2;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(0, 2, 0, 2);
	contentPane.add(rootWindow, c);
	
	frame.setContentPane(contentPane);
	frame.setVisible(true);
	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // create menubar
    public JMenuBar createMenuBar() {
	JMenuBar menuBar = new JMenuBar();
	
	// file menu
	JMenu fileMenu = new JMenu("File");
	menuBar.add(fileMenu);
	fileMenu.add(new ExitAction());
	menuBar.add(toolbar.createSimulationMenu());

	// plot menu
	JMenu plotMenu = new JMenu("Plot");
	menuBar.add(plotMenu);
	// plot -> new submenu
	plotMenu.add(new CreateStripChartWindowAction());
	plotMenu.add(new CreateXYPlotWindowAction());

	// parameter menu
	menuBar.add(parameterPanel.getMenuOfActions());
	
	// output menu
	menuBar.add(outputPanel.getMenuOfActions());

	// experiment menu - only in 21C model
	if ( CVSim.getSimulationModelName().equals(CVSim.MODEL_21C) ) {
	    tiltTestFrame = new TiltTestFrame();
	    JMenu experimentMenu = new JMenu("Experiments");
	    menuBar.add(experimentMenu);
	    experimentMenu.add(new PerformTiltTestAction());
	}
	
	// patient menu - only in 6C model
	if ( CVSim.getSimulationModelName().equals(CVSim.MODEL_6C) ) {
	    createPatients();
	    JMenu patientMenu = new JMenu("Patients");
	    menuBar.add(patientMenu);
	    for (Patient pt: patientList)
		patientMenu.add(new DisplayPatientHistoryAction(pt));
	}

	// help menu
	menuBar.add(new helpMenu());
	
	return menuBar;
    }

    private class ExitAction extends AbstractAction {
	public ExitAction() {
	    putValue(Action.NAME, "Exit");
	    putValue(Action.SHORT_DESCRIPTION, "Exit");
	}
	
	public void actionPerformed(ActionEvent e) {
	    frame.dispose();
	    System.exit(0);
	}
    }

    private class PerformTiltTestAction extends AbstractAction {
	public PerformTiltTestAction() {
	    putValue(Action.NAME, "Perform Tilt Test");
	    putValue(Action.SHORT_DESCRIPTION, "Perform Tilt Test");
	}
	public void actionPerformed(ActionEvent e) {
	    tiltTestFrame.performTiltTest();
     	}
    }

    private class CreateStripChartWindowAction extends AbstractAction {
	public CreateStripChartWindowAction() {
	    putValue(Action.NAME, "New Strip Chart");
	    putValue(Action.SHORT_DESCRIPTION, "Create a new strip chart");
	}
 	public void actionPerformed(ActionEvent e) {
	    int count = stripChartTabWindow.getChildWindowCount() + 1;
  	    PlotWindow plotWindow = new PlotWindow(new TraceListModel(), PlotWindow.STRIPCHART);	  
	    View view = new View("Strip Chart " + count, null, plotWindow); 
	    stripChartViews.add(view);
	    viewMap.addView(count, view);
	    stripChartTabWindow.addTab(view);
 	    CVSim.sim.addChangeListener(plotWindow.getPlot().sourceDataChanged);
	}
    }

    private class CreateXYPlotWindowAction extends AbstractAction {
	public CreateXYPlotWindowAction() {
	    putValue(Action.NAME, "New Plot");
	    putValue(Action.SHORT_DESCRIPTION, "Create a new plot");
	}
	public void actionPerformed(ActionEvent e) {
	    int count = XYPlotTabWindow.getChildWindowCount() + 1;
  	    PlotWindow plotWindow = new PlotWindow(new TraceListModel(), PlotWindow.PARAMETRIC);	  
	    View view = new View("Plot " + count, null, plotWindow); 
	    XYPlotViews.add(view);
	    viewMap.addView(count, view);
	    XYPlotTabWindow.addTab(view);
 	    CVSim.sim.addChangeListener(plotWindow.getPlot().sourceDataChanged);
	}
    }

    private class FrameSynchListener implements WindowListener {
	public void windowClosing(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { 
	    for (Patient pt: patientList) {
		pt.getFrame().setExtendedState(JFrame.ICONIFIED);
	    }
	}
	public void windowDeiconified(WindowEvent e) { }
	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
    }

    private class DockingWindowCleanupListener implements DockingWindowListener {
	
	public void viewFocusChanged(View previouslyFocusedView, View focusedView) {}
	public void windowAdded(DockingWindow addedToWindow, DockingWindow addedWindow) {}
	public void windowClosed(DockingWindow window) {}
	public void windowClosing(DockingWindow window) {
 	    System.out.println("Caught windowClosing() from DockingWindowCleanupListener, cleaning up.");
	    PlotWindow plotWindow = (PlotWindow)(((View)window).getComponent());
	    PlotPanel plotPanel = plotWindow.getPlot();
 	    plotPanel.removeAllTraces();
	}
	public void windowDocked(DockingWindow window) {}
	public void windowDocking(DockingWindow window) {}
	public void windowHidden(DockingWindow window) {}
	public void windowMaximized(DockingWindow window) {}
	public void windowMaximizing(DockingWindow window) {}
	public void windowMinimized(DockingWindow window) {}
	public void windowMinimizing(DockingWindow window) {}
	public void windowRemoved(DockingWindow removedFromWindow, DockingWindow removedWindow) {}
	public void windowRestored(DockingWindow window) {}
	public void windowRestoring(DockingWindow window) {}
	public void windowShown(DockingWindow window) {}
	public void windowUndocked(DockingWindow window) {}
	public void windowUndocking(DockingWindow window) {}
    }

   public void createPatients() {

	// Patient 1
	String pt1Name = "Patient 1";
	String pt1History = String.format("This patient is 40 years old, is well developed, and presents with dyspnea and chest pain on exertion, and frequent ventricular arrhythmias. He has had several unexplained syncopal attacks, from which he has recovered without incident. Physical exam revealed a prominent LV impulse, a palpable fourth heart sound, a biphasic carotid pulse with a sharp rise time, and a systolic ejection murmur which increased in intensity with isuprel.");
	Patient pt1 = new Patient(pt1Name, pt1History);
	pt1.addParameterSetting("Total Zero-Pressure Filling Volume", 2000.0);
	pt1.addParameterSetting("Total Blood Volume", 6000.0);
	pt1.addParameterSetting("Aortic Valve Resistance", 0.1);
	pt1.addParameterSetting("Left Ventricle Diastolic Compliance", 3.0);
	pt1.addParameterSetting("Left Ventricle Systolic Compliance", 0.1);
	pt1.addParameterSetting("Right Ventricle Diastolic Compliance", 4.0);
	pt1.addParameterSetting("Nominal Heart Rate", 100.0);
	patientList.add(pt1);

	// Patient 2
	String pt2Name = "Patient 2";
	String pt2History = String.format("This patient is a 24 year old woman who presents with a long history of severe dyspnea and now requires constant oxygen supplements. She also has had a long history of peripheral edema. Physical exam reveals elevated jugular venous pressure, a prominent cardiac impulse in the subxyphoid area, and a loud second heart sound which was not split. She had obvious cyanosis and clubbing of the fingers.");
	Patient pt2 = new Patient(pt2Name, pt2History);
	pt2.addParameterSetting("Pulmonary Arterial Compliance", 2.5);
	pt2.addParameterSetting("Total Blood Volume", 6000.0);
	pt2.addParameterSetting("Pulmonary Microcirculation Resistance", 1.0);
	pt2.addParameterSetting("Right Ventricle Diastolic Compliance", 10.0);
	pt2.addParameterSetting("Right Ventricle Systolic Compliance", 0.4);
	pt2.addParameterSetting("Nominal Heart Rate", 120.0);
	patientList.add(pt2);
    
	// Patient 3
	String pt3Name = "Patient 3";
	String pt3History = String.format("This patient is a 67 year old man who complained of awareness of a rapid pulse rate and a forceful heart beat. In addition, he reported that his exercise tolerance had decreased, and that he would get short of breath and fatigued more easily than ever before. He had no previous history of heart disease. A previous physical examination done a year ago had discovered a pulsatile abdominal mass, with an audible bruit in its vicinity. He had good femoral pulses bilaterally at that time. Examination now confirms the presence of the pulsatile mass and bruit. The cardiac exam reveals a rapid heart rate with a very forceful apical impulse. There was a grade 2/6 systolic murmur heard at the apex and over the second intercostal space to the left of the sternum. No diastolic murmurs were heard. Prominent capillary pulsations were noted in the nail beds and under the tongue. Femoral pulses were present bilaterally, athough the left was weaker than the right. The patient was taken to the catheterization lab for confirmation of the diagnosis.");
	Patient pt3 = new Patient(pt3Name, pt3History);
	pt3.addParameterSetting("Total Peripheral Resistance", 0.1);
	pt3.addParameterSetting("Venous Compliance", 70.0);
	pt3.addParameterSetting("Total Zero-Pressure Filling Volume", 1500.0);
	pt3.addParameterSetting("Pulmonary Microcirculation Resistance", 0.04);
	pt3.addParameterSetting("Left Ventricle Systolic Compliance", 0.2);
	pt3.addParameterSetting("Right Ventricle Systolic Compliance", 0.6);
	pt3.addParameterSetting("Total Blood Volume", 5500.0);
	pt3.addParameterSetting("Nominal Heart Rate", 120.0);
	patientList.add(pt3);

	// Patient 4
	String pt4Name = "Patient 4";
	String pt4History = String.format("This patient is a 41 year old mother of three children who was well (except for the usual colds, etc.) until last year when she began to experience shortness of breath on even minor exercise, and episodes of shortness of breath during the night which were relieved by standing up. She denied any chest pain, chest infections, palpitations; but did report some mild swelling of the ankles. Her physical activities have become severely limited because of the shortness of breath. She has been taking several medications at the request of her physician. She comes to you for hemodynamic evaluation and assessment. Her exam reveals an enlarged heart, regular rhythm, an S-3 gallop, no murmurs, and fine rales at both lung bases. There was 2+ ankle and pretibial edema.");
	Patient pt4 = new Patient(pt4Name, pt4History);
	pt4.addParameterSetting("Total Peripheral Resistance", 0.9);
	pt4.addParameterSetting("Arterial Compliance", 1.0);
	pt4.addParameterSetting("Total Zero-Pressure Filling Volume", 2300.0);
	pt4.addParameterSetting("Pulmonary Arterial Compliance", 2.5);
	pt4.addParameterSetting("Pulmonary Microcirculation Resistance", 0.14);
	pt4.addParameterSetting("Left Ventricle Diastolic Compliance", 5.0);
	pt4.addParameterSetting("Left Ventricle Systolic Compliance", 1.2);
	pt4.addParameterSetting("Right Ventricle Diastolic Compliance", 11.0);
	pt4.addParameterSetting("Right Ventricle Systolic Compliance", 3.6);
	pt4.addParameterSetting("Total Blood Volume", 6300.0);
	pt4.addParameterSetting("Nominal Heart Rate", 125.0);
	patientList.add(pt4);

	// Patient 5
	String pt5Name = "Patient 5";
	String pt5History = String.format("The patient is a 60 year old man who entered the hospital with severe crushing substernal chest pain of two hours duration. He appears short of breath. He is diaphoretic, has cold clammy skin, and his nail-beds are cyanotic. He is very lethargic and seems somewhat confused. He has low blood pressure, elevated jugular venous pressure, a regular cardiac rhythm with an S-3 gallop, and examination of the lungs reveals bibasilar fine rales. He has no edema. His urine output seems to be very scanty.");
	Patient pt5 = new Patient(pt5Name, pt5History);
	pt5.addParameterSetting("Total Peripheral Resistance", 1.4);
	pt5.addParameterSetting("Arterial Compliance", 0.5);
	pt5.addParameterSetting("Total Zero-Pressure Filling Volume", 1850.0);
	pt5.addParameterSetting("Pulmonary Arterial Compliance", 2.0);
	pt5.addParameterSetting("Left Ventricle Diastolic Compliance", 8.0);
	pt5.addParameterSetting("Left Ventricle Systolic Compliance", 2.5);
	pt5.addParameterSetting("Right Ventricle Diastolic Compliance", 15.0);
	pt5.addParameterSetting("Right Ventricle Systolic Compliance", 4.0);
	pt5.addParameterSetting("Nominal Heart Rate", 110.0);
	patientList.add(pt5);

	// Patient 6
	String pt6Name = "Patient 6";
	String pt6History = String.format("This 55 year old male bank vice-president had been in excellent health until one month ago when he began to notice unusual shortness of breath and substernal pressure which was associated with physical exercise. The symptoms would be brought on quite predictably after carrying a heavy briefcase up to the third floor of the parking garage, for example. Rest would always relieve the discomfort after a few minutes. He had a known heart murmur which was first noted on a routine physical examination ten years ago, but at the time it had been considered to be an \"innocent\" murmur. On the day of admission he had been carrying a bag of cement to his backyard when he suddenly became lightheaded and then lost conciousness.");
	Patient pt6 = new Patient(pt6Name, pt6History);
	pt6.addParameterSetting("Arterial Compliance", 0.8);
	pt6.addParameterSetting("Aortic Valve Resistance", 0.1);
	pt6.addParameterSetting("Left Ventricle Diastolic Compliance", 8.0);
	pt6.addParameterSetting("Left Ventricle Systolic Compliance", 0.3);
	pt6.addParameterSetting("Total Blood Volume", 5100.0);
	pt6.addParameterSetting("Nominal Heart Rate", 60.0);
	patientList.add(pt6);
    }

    private class DisplayPatientHistoryAction extends AbstractAction {
	Patient pt;
	public DisplayPatientHistoryAction(Patient patient) {
	    pt = patient;
	    putValue(Action.NAME, "Load " + pt.getName());
	    putValue(Action.SHORT_DESCRIPTION, String.format("Display " + pt.getName() + " History") );
	}
	public void actionPerformed(ActionEvent e) {
	    pt.loadParameterSettings();
	    if ( !(pt.getFrame().isVisible()) )
		pt.getFrame().setVisible(true);
	    if ( pt.getFrame().getExtendedState() == JFrame.ICONIFIED )
		pt.getFrame().setExtendedState(JFrame.NORMAL);
	    System.out.println("Loading patient history for " + pt.getName() + ".");
	}
    }

    // Doesn't work with Java Web Start
//     private String loadPatientHistory(File file) {
// 	String history = "";

// 	try {
// 	    BufferedReader reader = new BufferedReader(new FileReader(file));
// 	    String line = null;
// 	    while ( (line = reader.readLine()) != null ) {
// 		history += line;
// 	    }	
// 	} catch (Exception ex) {
// 	    System.out.println("ERROR: Can't read patient history file");
// 	    ex.printStackTrace();
// 	}

// 	return history;
//     } 


}






    

