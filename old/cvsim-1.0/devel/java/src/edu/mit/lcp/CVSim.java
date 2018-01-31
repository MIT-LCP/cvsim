package edu.mit.lcp;

// CVSim.java
// Contains the main() method
// Creates and shows the GUI on the event-dispatching thread

// TO DO: This class and the gui class should probably be combined

import javax.swing.JOptionPane;

public class CVSim {

    public static final VersionNumber versionNumber = new VersionNumber(1, 16);

    public static final String MODEL_21C = "21 Compartment";
    public static final String MODEL_6C = "6 Compartment";

    // Create the simulation as static to allow it to be accessed in a
    // global manner.  it still needs to be scheduled/started later.

    public static SimulationThread simThread;
    public static CSimulation sim;

    public static gui gui;

    public static String simulationModelName;

    public static void main(String[] args) {
	boolean speedTest = false;

	System.out.println("CVSim.main(...)");

	simulationModelName = null;
        for (String s: args) {
	    if (s.equals("-m6"))
		simulationModelName = MODEL_6C;
	    if (s.equals("-m21"))
		simulationModelName = MODEL_21C;
	    if (s.equals("-speed"))
		speedTest = true;
        }

	if (simulationModelName == null) {
	    Object[] possibilities = {MODEL_6C, MODEL_21C};
	    simulationModelName = (String)JOptionPane.showInputDialog
		(null, "Please select a simulation model:",	"Select Model",
		 JOptionPane.PLAIN_MESSAGE,	null, possibilities, MODEL_6C);
	}

	if (simulationModelName != null) {
	    if (simulationModelName.equals(MODEL_6C)) {
		sim = new CSimulation6C();
	    } else if (simulationModelName.equals(MODEL_21C)) {
		sim = new CSimulation21C();
	    } else {
		System.exit(1);
	    }
	} else {
	    System.exit(1);
	}

	if (speedTest) {
	    int [] dcf = {1, 1, 5, 10, 20, 50, 100, 1000};
	    int simTime = 500;
	    System.out.println("Testing " + simulationModelName + " Model backend speed.");
	    System.out.print("Simulating " + simTime + " seconds using DataCompressionFactors: ");
	    for (int i: dcf) System.out.print(i+ " ");
	    System.out.print("\n");

	    for (int i: dcf) {
		//System.out.print("DataCompressionFactor = " + i + ", ");
		sim.setDataCompressionFactor(i);
		long startTime = System.currentTimeMillis();
		for (int s=0; s<((simTime*1000)/i); s++) {
		    sim.step();
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime-startTime;
		System.out.println("Wall Clock Time Elapsed: " + (double)duration/1000 + 
				   "sec -- (" + (double)simTime/((double)duration/1000) + "x realtime)");
		sim.reset();
	    }
	} else {
	    simThread = new SimulationThread(sim);
	    
	    gui = new gui();
	    
	    // Setup GUI to run in the event-dispatching-thread by using
	    // invokeLater method.
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			gui.createAndShowGUI();
		    }
		});
	}
    }

    public static String getSimulationModelName() { return simulationModelName; }
} 

