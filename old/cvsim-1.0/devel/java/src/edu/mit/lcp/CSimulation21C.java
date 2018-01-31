package edu.mit.lcp;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import edu.mit.lcp.C21_comp_backend.Parameter_vector;
import edu.mit.lcp.C21_comp_backend.output;
import edu.mit.lcp.C21_comp_backend.main;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.Arrays;

public class CSimulation21C extends CSimulation {
    
    // C structure which holds the results of the last simulation step
    public output output;
    
    // Simulation Parameters for the C code
    public CSimulation21CParameters simParameters;

    public CSimulation21C() {
	super();
	System.err.println("CSimulation21C()");

	// try to import C library
	try {
	    System.err.println("Loading shared C library");
	    System.loadLibrary("C21_comp_backend");
	} catch (UnsatisfiedLinkError e) {
	    System.err.println("C library failed to load" + e);
	    System.exit(1);
	}

	// create a new instance of the output class which provides
	// access to the data structures in the loaded C library
	output = new output();
       
	simParameters = new CSimulation21CParameters();

	// Create output variable list
	varList = new ArrayList<SimulationOutputVariable>();

	varList.add(new SimulationOutputVariable<Double>
		     (0, "TIME", "Time", "s", "Other", "Time",
		      new Range<Double>(0.0, 0.0)) );

	// Ascending Aorta
	varList.add(new SimulationOutputVariable<Double>
		    (1 , "AAP",  "Ascending Aortic Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X0
	
	varList.add(new SimulationOutputVariable<Double>
		    (2 , "AAF", "Ascending Aortic Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q0
	
	varList.add(new SimulationOutputVariable<Double>
		    (3 , "AAV", "Ascending Aortic Volume",   "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V0

	// Brachiocephalic Arteries
	varList.add(new SimulationOutputVariable<Double>
		    (4 , "BAP", "Brachiocephalic Arterial Pressure",   "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X1
	
	varList.add(new SimulationOutputVariable<Double>
		    (5 , "BAF", "Brachiocephalic Arterial Flow",   "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q1
	
	varList.add(new SimulationOutputVariable<Double>
		    (6 , "BAV", "Brachiocephalic Arterial Volume",   "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V1

	// Upper Body Arteries 
	varList.add(new SimulationOutputVariable<Double>
		    (7 , "UBAP", "Upper Body Arterial Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X2
	
	varList.add(new SimulationOutputVariable<Double>
		    (8 , "UBAF", "Upper Body Arterial Flow",   "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q2
	
	varList.add(new SimulationOutputVariable<Double>
		    (9 , "UBAV", "Upper Body Arterial Volume",   "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V2

	
	// Lower Body Arteries
	varList.add(new SimulationOutputVariable<Double>
		    (10 , "UBVP", "Upper Body Venous Pressure",   "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X3
	
	varList.add(new SimulationOutputVariable<Double>
		    (11 , "UBVF", "Upper Body Venous Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q3
	
	varList.add(new SimulationOutputVariable<Double>
		    (12 , "UBVV", "Upper Body Venous Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V3
	

	// Superior Vena Cava 
	varList.add(new SimulationOutputVariable<Double>
		    (13 , "SVCP", "Superior Vena Cava Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X4
	
	varList.add(new SimulationOutputVariable<Double>
		    (14 , "SVCF", "Superior Vena Cava Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q4
	
	varList.add(new SimulationOutputVariable<Double>
		    (15 , "SVCV", "Superior Vena Cava Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V4


	// Thoracic Aorta
	varList.add(new SimulationOutputVariable<Double>
		    (16 , "TAP", "Thoracic Aortic Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X5
	
	varList.add(new SimulationOutputVariable<Double>
		    (17 , "TAF", "Thoracic Aortic Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q5
	
	varList.add(new SimulationOutputVariable<Double>
		    (18 , "TAV", "Thoracic Aortic Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V5


	// Abdominal Aorta
	varList.add(new SimulationOutputVariable<Double>
		    (19 , "AAP", "Abdominal Aortic Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X6
	
	varList.add(new SimulationOutputVariable<Double>
		    (20 , "AAF", "Abdominal Aortic Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q6
	
	varList.add(new SimulationOutputVariable<Double>
		    (21 , "AAV", "Abdominal Aortic Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V6


	// Renal Arteries
	varList.add(new SimulationOutputVariable<Double>
		    (22 , "RAP", "Renal Arterial Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X7
	
	varList.add(new SimulationOutputVariable<Double>
		    (23 , "RAF", "Renal Arterial Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q7
	
	varList.add(new SimulationOutputVariable<Double>
		    (24 , "RAV", "Renal Arterial Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V7


	// Renal Veins
	varList.add(new SimulationOutputVariable<Double>
		    (25 , "RVP", "Renal Venous Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X8
	
	varList.add(new SimulationOutputVariable<Double>
		    (26 , "RVF", "Renal Venous Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q8
	
	varList.add(new SimulationOutputVariable<Double>
		    (27 , "RVV", "Renal Venous Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V8


	// Splanchnic Arteries
	varList.add(new SimulationOutputVariable<Double>
		    (28 , "SAP", "Splanchnic Arterial Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X9
	
	varList.add(new SimulationOutputVariable<Double>
		    (29 , "SAF", "Splanchnic Arterial Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q9
	
	varList.add(new SimulationOutputVariable<Double>
		    (30 , "SAV", "Splanchnic Arterial Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V9


	// Splanchnic Veins
	varList.add(new SimulationOutputVariable<Double>
		    (31 , "SVP", "Splanchnic Venous Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X10
	
	varList.add(new SimulationOutputVariable<Double>
		    (32 , "SVF", "Splanchnic Venous Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q10
	
	varList.add(new SimulationOutputVariable<Double>
		    (33 , "SVV", "Splanchnic Venous Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V10


	// Lower Body Arteries
	varList.add(new SimulationOutputVariable<Double>
		    (34 , "LBAP", "Lower Body Arterial Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X11
	
	varList.add(new SimulationOutputVariable<Double>
		    (35 , "LBAF", "Lower Body Arterial Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q11
	
	varList.add(new SimulationOutputVariable<Double>
		    (36 , "LBAV", "Lower Body Arterial Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V11

	// Lower Body Veins
	varList.add(new SimulationOutputVariable<Double>
		    (37 , "LBVP", "Lower Body Venous Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X12
	
	varList.add(new SimulationOutputVariable<Double>
		    (38 , "LBVF", "Lower Body Venous Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q12
	
	varList.add(new SimulationOutputVariable<Double>
		    (39 , "LBVV", "Lower Body Venous Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V12


	// Abdominal Veins
	varList.add(new SimulationOutputVariable<Double>
		    (40 , "AVP", "Abdominal Venous Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X13
	
	varList.add(new SimulationOutputVariable<Double>
		    (41 , "AVF", "Abdominal Venous Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q13
	
	varList.add(new SimulationOutputVariable<Double>
		    (42 , "AVV", "Abdominal Venous Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V13


	// Inferior Vena Cava
	varList.add(new SimulationOutputVariable<Double>
		    (43 , "IVCP", "Inferior Vena Cava Pressure", "mmHg", "Peripheral", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X14
	
	varList.add(new SimulationOutputVariable<Double>
		    (44 , "IVCF", "Inferior Vena Cava Flow", "mL/s", "Peripheral", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q14
	
	varList.add(new SimulationOutputVariable<Double>
		    (45 , "IVCV", "Inferior Vena Cava Volume", "mL", "Peripheral", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V14

	// Right Atrium
	varList.add(new SimulationOutputVariable<Double>
		    (46 , "RAP", "Right Atrial Pressure", "mmHg", "Cardiac", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X15
	
	varList.add(new SimulationOutputVariable<Double>
		    (47 , "RAF", "Right Atrial Flow", "mL/s", "Cardiac", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q15
	
	varList.add(new SimulationOutputVariable<Double>
		    (48 , "RAV", "Right Atrial Volume", "mL", "Cardiac", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V15

	
	// Right Ventricle
	varList.add(new SimulationOutputVariable<Double>
		    (49 , "RVP", "Right Ventricular Pressure", "mmHg", "Cardiac", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X16
	
	varList.add(new SimulationOutputVariable<Double>
		    (50 , "RVF", "Right Ventricular Flow", "mL/s", "Cardiac", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q16
	
	varList.add(new SimulationOutputVariable<Double>
		    (51 , "RVV", "Right Ventricular Volume", "mL", "Cardiac", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V16

	
	// Pulmonary Arteries
	varList.add(new SimulationOutputVariable<Double>
		    (52 , "PAP", "Pulmonary Arterial Pressure", "mmHg", "Pulmonary", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X17
	
	varList.add(new SimulationOutputVariable<Double>
		    (53 , "PAF", "Pulmonary Arterial Flow", "mL/s", "Pulmonary", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q17
	
	varList.add(new SimulationOutputVariable<Double>
		    (54 , "PAV", "Pulmonary Arterial Volume", "mL", "Pulmonary", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V17


	// Pulmonary Veins
	varList.add(new SimulationOutputVariable<Double>
		    (55 , "PVP", "Pulmonary Venous Pressure", "mmHg", "Pulmonary", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X18
	
	varList.add(new SimulationOutputVariable<Double>
		    (56 , "PVF", "Pulmonary Venous Flow", "mL/s", "Pulmonary", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q18
	
	varList.add(new SimulationOutputVariable<Double>
		    (57 , "PVV", "Pulmonary Venous Volume", "mL", "Pulmonary", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V18

	
	// Left Atrium
	varList.add(new SimulationOutputVariable<Double>
		    (58 , "LAP", "Left Atrial Pressure", "mmHg", "Cardiac", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X19
	
	varList.add(new SimulationOutputVariable<Double>
		    (59 , "LAF", "Left Atrial Flow", "mL/s", "Cardiac", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q19
	
	varList.add(new SimulationOutputVariable<Double>
		    (60 , "LAV", "Left Atrial Volume", "mL", "Cardiac", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V19

	// Left Ventricle
	varList.add(new SimulationOutputVariable<Double>
		    (61 , "LVP", "Left Ventricular Pressure", "mmHg", "Cardiac", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X20
	
	varList.add(new SimulationOutputVariable<Double>
		    (62 , "LVF", "Left Ventricular Flow", "mL/s", "Cardiac", "Flow",
		      new Range<Double>(0.0, 200.0)) ); // Q20
	
	varList.add(new SimulationOutputVariable<Double>
		    (63 , "LVV", "Left Ventricular Volume", "mL", "Cardiac", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V20

	// Reflex Outputs (Controlled Variables)
	varList.add(new SimulationOutputVariable<Double> 
		    (64, "HR", "Heart Rate", "beats/min", "Systemic", "Reflex",
		     new Range<Double>(0.0, 100.0)) ); // Heart Rate
	
	varList.add(new SimulationOutputVariable<Double> 
		    (65, "AR", "Arteriolar Resistance", "PRU", "Systemic", "Reflex",
		     new Range<Double>(0.0, 10.0)) ); // Arterial Resistance
    
	varList.add(new SimulationOutputVariable<Double>
		    (66, "VT", "Venous Tone", "mL", "Systemic", "Reflex",
		     new Range<Double>(0.0, 2500.0)) ); // Venous Tone
	
	varList.add(new SimulationOutputVariable<Double>
		    (67, "RVC", "Right Ventricle Contractility", "mL/mmHg", "Systemic", "Reflex",
		     new Range<Double>(0.0, 10.0)) );

	varList.add(new SimulationOutputVariable<Double>
		    (68, "LVC", "Left Ventricle Contractility", "mL/mmHg", "Systemic", "Reflex",
		     new Range<Double>(0.0, 10.0)) );

	varList.add(new SimulationOutputVariable<Double>
		    (69, "TBV", "Total Blood Volume", "mL", "Systemic", "Volume",
		     new Range<Double>(0.0, 5500.0)) );

	varList.add(new SimulationOutputVariable<Double>
		    (70, "PTH", "Intra-thoracic Pressure", "mmHg", "Systemic", "Pressure",
		     new Range<Double>(-10.0, 10.0)) );

	varList.add(new SimulationOutputVariable<Double>
		    (71, "TA", "Tilt Angle", "Degrees", "Experiment", "Angle",
		     new Range<Double>(0.0, 90.0)) );

    }

    public void reset() {
	main.reset_sim();
    }

    // This method steps the simulation
    public void step() {
	    
	// run simulation and get updated model measurements
	main.step_sim(output, 
		      simParameters.getVector(),
		      getDataCompressionFactor(),
		      getABReflex_C(), 
		      getCPReflex_C(),
		      CVSim.gui.tiltTestFrame.getTiltTest_C(),
		      CVSim.gui.tiltTestFrame.getTiltStartTime(),
		      CVSim.gui.tiltTestFrame.getTiltStopTime());
	
	// increment the step count
	steps++;

	// Save the new data
	updateRecorders();
	
	// Flag that data has changed
	dataChanged();

    } // end step

    public double getOutput(int index) {
	double value;
	switch (index) {
	case 0  : value = output.getTime(); break; // Time 
	    // 
	case 1  : value = output.getX0(); break;   // 
	case 2  : value = output.getQ0(); break;   // 
	case 3  : value = output.getV0(); break;   // 
	    // 
	case 4  : value = output.getX1(); break;   // 
	case 5  : value = output.getQ1(); break;   // 
	case 6  : value = output.getV1(); break;   // 
	    // 
	case 7  : value = output.getX2(); break;   // 
	case 8  : value = output.getQ2(); break;   // 
	case 9  : value = output.getV2(); break;   // 
	    // 
	case 10 : value = output.getX3(); break;   // 
	case 11 : value = output.getQ3(); break;   // 
	case 12 : value = output.getV3(); break;   // 
	    // 
	case 13 : value = output.getX4(); break;   // 
	case 14 : value = output.getQ4(); break;   // 
	case 15 : value = output.getV4(); break;   // 
	    // 
	case 16 : value = output.getX5(); break;   // 
	case 17 : value = output.getQ5(); break;   // 
	case 18 : value = output.getV5(); break;   // 
	    // 
	case 19 : value = output.getX6(); break;   // 
	case 20 : value = output.getQ6(); break;   // 
	case 21 : value = output.getV6(); break;   // 
	    // 
	case 22 : value = output.getX7(); break;   // 
	case 23 : value = output.getQ7(); break;   // 
	case 24 : value = output.getV7(); break;   // 
	    // 
	case 25 : value = output.getX8(); break;   // 
	case 26 : value = output.getQ8(); break;   // 
	case 27 : value = output.getV8(); break;   // 
	    // 
	case 28 : value = output.getX9(); break;   // 
	case 29 : value = output.getQ9(); break;   // 
	case 30 : value = output.getV9(); break;   // 
 	    // 
	case 31 : value = output.getX10(); break;   // 
	case 32 : value = output.getQ10(); break;   // 
	case 33 : value = output.getV10(); break;   // 
 	    // 
	case 34 : value = output.getX11(); break;   // 
	case 35 : value = output.getQ11(); break;   // 
	case 36 : value = output.getV11(); break;   // 
 	    // 
	case 37 : value = output.getX12(); break;   // 
	case 38 : value = output.getQ12(); break;   // 
	case 39 : value = output.getV12(); break;   // 
 	    // 
	case 40 : value = output.getX13(); break;   // 
	case 41 : value = output.getQ13(); break;   // 
	case 42 : value = output.getV13(); break;   // 
 	    // 
	case 43 : value = output.getX14(); break;   // 
	case 44 : value = output.getQ14(); break;   // 
	case 45 : value = output.getV14(); break;   // 
 	    // 
	case 46 : value = output.getX15(); break;   // 
	case 47 : value = output.getQ15(); break;   // 
	case 48 : value = output.getV15(); break;   // 
 	    // 
	case 49 : value = output.getX16(); break;   // 
	case 50 : value = output.getQ16(); break;   // 
	case 51 : value = output.getV16(); break;   // 
 	    // 
	case 52 : value = output.getX17(); break;   // 
	case 53 : value = output.getQ17(); break;   // 
	case 54 : value = output.getV17(); break;   // 
 	    // 
	case 55 : value = output.getX18(); break;   // 
	case 56 : value = output.getQ18(); break;   // 
	case 57 : value = output.getV18(); break;   // 
 	    // 
	case 58 : value = output.getX19(); break;   // 
	case 59 : value = output.getQ19(); break;   // 
	case 60 : value = output.getV19(); break;   // 
 	    // 
	case 61 : value = output.getX20(); break;   // 
	case 62 : value = output.getQ20(); break;   // 
	case 63 : value = output.getV20(); break;   // 
	    
	case 64 : value = output.getHR(); break; // HR
	case 65 : value = output.getAR(); break; // AR
	case 66 : value = output.getVT(); break; // VT
	case 67 : value = output.getRVC(); break; // RVC
	case 68 : value = output.getLVC(); break; // LVC

	case 69 : value = output.getTBV(); break; // TBV 
	case 70 : value = output.getPth(); break; // Pth 

	case 71:  value = output.getTiltAngle(); break; // tilt angle    

	default: value = 0.0; break;
	}
	
	return value;
    }

    public void updatePressure(int i, double d) {
	main.updatePressure(i, d);
    }

    public void updateGravityVector(int i, double d) {
	main.updateGravityVector(i,d);
    }

    public List<Parameter> getParameterList() {
	return simParameters.getParameterList();
    }
    public Parameter getParameterByName(String name) {
	return simParameters.getParameterByName(name);
    }
    public Parameter_vector getParameterVector() {
	return simParameters.getVector();
    }
    

    ///////////////////////////////////////
    ///////////////////////////////////////
    private class CSimulation21CParameters {

	private Parameter_vector pvec;
	private List<Parameter> plist;

	CSimulation21CParameters() {
	    pvec = new Parameter_vector();
	    // initialize the simulation with the variables
	    main.init_sim(pvec);

	    plist = createParameterList();
	}

	public Parameter_vector getVector() {
	    return pvec;
	}
	public List<Parameter> getParameterList() {
	    return plist;
	}
// 	public Parameter getParameterByIndex(int index) {
// 	    for (Parameter p: plist) {
// 		if (p.getIndex() == index)
// 		    return p;
// 	    }
// 	    return null;
// 	}
	public Parameter getParameterByName(String name) {
	    for (Parameter p: plist) {
		if (p.getName().equals(name))
		    return p;
	    }
	    return null;
	}

	// creates and initializes parameters
	private List<Parameter> createParameterList() {
	    List<Parameter> list = new ArrayList<Parameter>();
	    
	    // Hello, magic numbers!  
	    
            // The index of each parameter is its location in the 
	    // tmp->vec structure in the mapping() function in initial.c. 
	    // Use the comments in mapping() to locate each parameter.

	    // For example, if you want to know the index of the
	    // pulmonary arterial compliance parameter, look for 
	    // something resembling that description in the comments in the 
	    // mapping function. In this case you will find:

	    // tmp -> vec[47] = (*hemo)[8].c[0][0]; // C pul. arteries

	    // Therefore, 47 is the index for pulmonary arterial compliance.

	    // To find the minimum and maximum values for each parameter, 
	    // cross-reference the right side of the assignment statements 
	    // in mapping() with the declarations in the initial(). The minimum
	    // is located in the second column of each multi-dimensional array. The
	    // maximum is located in the third column. Again, using pulmonary arterial
	    // compliance as an example:

	    // tmp -> vec[47] = (*hemo)[8].c[0][0]; // C pul. arteries
	    
	    // In this case, you would need to find the entry for 
	    // (*hemo)[8].c[0][2] (the minimum) and  (*hemo)[8].c[0][3]
	    // (the maximum) in initial.c.

	    // (*hemo)[8].c[0][2] = 1.5;   (*hemo)[8].c[0][3] = 7.2;


	    // Is this a terrible way to do things? Absolutely. But
	    // doing things the right way would require rewriting
	    // a significant part of the C code, and there is not enough 
	    // time to do that.
	
	    // Cardiac
	    list.add(new Parameter21C(pvec, 49, "Left Heart", "Compliance", "Left Atrium Diastolic Compliance", 
				      "mL/mmHg", 1.0, 4.3));
	    list.add(new Parameter21C(pvec, 50, "Left Heart", "Compliance", "Left Atrium Systolic Compliance", 
				      "mL/mmHg", 0.8, 3.2));
	    list.add(new Parameter21C(pvec, 68, "Left Heart", "Resistance", "Mitral Valve Resistance", 
				      "PRU", 0.007, 0.013));
	    list.add(new Parameter21C(pvec, 88, "Left Heart", "Volume", "Left Atrium Zero-Pressure Filling Volume", 
				      "mL", 10.0, 38.0));

	    list.add(new Parameter21C(pvec, 43, "Right Heart", "Compliance", "Right Atrium Diastolic Compliance", 
				      "mL/mmHg", 1.5, 6.0));
	    list.add(new Parameter21C(pvec, 44, "Right Heart", "Compliance", "Right Atrium Systolic Compliance", 
				      "mL/mmHg", 0.6, 2.7));
	    list.add(new Parameter21C(pvec, 64, "Right Heart", "Resistance", "Tricuspid Valve Resistance", 
				      "PRU", 0.0, 0.015));
	    list.add(new Parameter21C(pvec, 84, "Right Heart", "Volume", "Right Atrium Zero-Pressure Filling Volume", 
				      "mL", 10.0, 18.0));

	    list.add(new Parameter21C(pvec, 51, "Left Heart", "Compliance", "Left Ventricle Diastolic Compliance", 
				      "mL/mmHg", 3.88, 15.11));
	    list.add(new Parameter21C(pvec, 52, "Left Heart", "Compliance", "Left Ventricle Systolic Compliance", 
				      "mL/mmHg", 0.2, 0.77));
	    list.add(new Parameter21C(pvec, 69, "Left Heart", "Resistance", "Aortic Valve Resistance", 
				      "PRU", 0.0, 0.013));
	    list.add(new Parameter21C(pvec, 89, "Left Heart", "Volume", "Left Ventricle Zero-Pressure Filling Volume", 
				      "mL", 25.0, 85.0));

	    list.add(new Parameter21C(pvec, 45, "Right Heart", "Compliance", "Right Ventricle Diastolic Compliance", 
				      "mL/mmHg", 7.0, 29.0));
	    list.add(new Parameter21C(pvec, 46, "Right Heart", "Compliance", "Right Ventricle Systolic Compliance", 
				      "mL/mmHg", 0.3, 2.0));
	    list.add(new Parameter21C(pvec, 65, "Right Heart", "Resistance", "Pulmonic Valve Resistance", 
				      "PRU", 0.0, 0.015));
	    list.add(new Parameter21C(pvec, 85, "Right Heart", "Volume", "Right Ventricle Zero-Pressure Filling Volume", 
				      "mL", 10.0, 82.0));
	
	    // Peripheral Circulation
	    list.add(new Parameter21C(pvec, 101, "Abdominal Aorta", "Compliance", "Abdominal Aorta Compliance", 
				      "mL/mmHg", 0.07, 0.13));
	    list.add(new Parameter21C(pvec, 108, "Abdominal Aorta", "Resistance", "Abdominal Aorta Resistance", 
				      "PRU", 0.0, 0.02));
	    list.add(new Parameter21C(pvec, 140, "Abdominal Aorta", "Volume", "Abdominal Aorta Zero-Pressure Filling Volume", 
				      "mL", 7.0, 13.0));

	    list.add(new Parameter21C(pvec, 40, "Abdominal Veins", "Compliance", "Abdominal Veins Compliance", 
				      "mL/mmHg", 1.0, 1.6));
	    list.add(new Parameter21C(pvec, 61, "Abdominal Veins", "Resistance", "Abdominal Veins Resistance", 
				      "PRU", 0.0, 0.04));
	    list.add(new Parameter21C(pvec, 81, "Abdominal Veins", "Volume", "Abdominal Veins Zero-Pressure Filling Volume", 
				      "mL", 49.0, 109.0));

	    list.add(new Parameter21C(pvec, 97, "Ascending Aorta", "Compliance", "Ascending Aorta Compliance", 
				      "mL/mmHg", 0.16, 0.4));
	    list.add(new Parameter21C(pvec, 136, "Ascending Aorta", "Volume", "Ascending Aorta Zero-Pressure Filling Volume", 
				      "mL", 10.0, 32.0));

	    list.add(new Parameter21C(pvec, 98, "Brachiocephalic Arteries", "Compliance", "Brachiocephalic Arteries Compliance", 
				      "mL/mmHg", 0.07, 0.2));
	    list.add(new Parameter21C(pvec, 105, "Brachiocephalic Arteries", "Resistance", "Brachiocephalic Arteries Resistance", 
				      "PRU", 0.002, 0.026));
	    list.add(new Parameter21C(pvec, 137, "Brachiocephalic Arteries", "Volume", "Brachiocephalic Arteries Zero-Pressure Filling Volume", 
				      "PRU", 2.0, 8.0));

	    list.add(new Parameter21C(pvec, 41, "Inferior Vena Cava", "Compliance", "Inferior Vena Cava Compliance", 
				      "mL/mmHg", 0.2, 0.8));
	    list.add(new Parameter21C(pvec, 62, "Inferior Vena Cava", "Resistance", "Inferior Vena Cava Resistance", 
				      "PRU", 0.0, 0.017));
	    list.add(new Parameter21C(pvec, 82, "Inferior Vena Cava", "Volume", "Inferior Vena Cava Zero-Pressure Filling Volume", 
				      "mL", 21.0, 45.0));

	    list.add(new Parameter21C(pvec, 104, "Lower Body Arteries", "Compliance", "Lower Body Arteries Compliance", 
				      "mL/mmHg", 0.1, 0.7));
	    list.add(new Parameter21C(pvec, 59, "Lower Body Microcirculation", "Resistance", "Lower Body Microcirculation Resistance", 
				      "PRU", 4.0, 10.3));
	    list.add(new Parameter21C(pvec, 143, "Lower Body Arteries", "Volume", "Lower Body Arteries Zero-Pressure Filling Volume", 
				      "mL", 140.0, 260.0));

	    list.add(new Parameter21C(pvec, 39, "Lower Body Veins", "Compliance", "Lower Body Veins Compliance", 
				      "mL/mmHg", 11.0, 29.0));
	    list.add(new Parameter21C(pvec, 60, "Lower Body Veins", "Resistance", "Lower Body Veins Resistance", 
				      "PRU", 0.0, 0.25));
	    list.add(new Parameter21C(pvec, 80, "Lower Body Veins", "Volume", "Lower Body Veins Zero-Pressure Filling Volume", 
				      "mL", 666.0, 866.0));

	    list.add(new Parameter21C(pvec, 102, "Renal Arteries", "Compliance", "Renal Arteries Compliance", 
				      "mL/mmHg", 0.1, 0.3));
	    list.add(new Parameter21C(pvec, 109, "Renal Arteries", "Resistance", "Renal Arteries Resistance", 
				      "PRU", 0.0, 0.25));
	    list.add(new Parameter21C(pvec, 141, "Renal Arteries", "Volume", "Renal Arteries Zero-Pressure Filling Volume", 
				      "mL", 5.0, 35.0));

	    list.add(new Parameter21C(pvec, 37, "Renal Veins", "Compliance", "Renal Veins Compliance", 
				      "mL/mmHg", 2.0, 8.0));
	    list.add(new Parameter21C(pvec, 56, "Renal Veins", "Resistance", "Renal Veins Resistance", 
				      "PRU", 0.0, 0.26));
	    list.add(new Parameter21C(pvec, 78, "Renal Veins", "Volume", "Renal Veins Zero-Pressure Filling Volume", 
				      "mL", 10.0, 60.0));

	    list.add(new Parameter21C(pvec, 103, "Splanchnic Arteries", "Compliance", "Splanchnic Arteries Compliance", 
				      "mL/mmHg", 0.10, 0.70));
	    list.add(new Parameter21C(pvec, 110, "Splanchnic Arteries", "Resistance", "Splanchnic Arteries Resistance", 
				      "PRU", 0.0, 0.19));
	    list.add(new Parameter21C(pvec, 142, "Splanchnic Arteries", "Volume", "Splanchnic Arteries Zero-Pressure Filling Volume", 
				      "mL", 150.0, 450.0));

	    list.add(new Parameter21C(pvec, 38, "Splanchnic Veins", "Compliance", "Splanchnic Veins Compliance", 
				      "mL/mmHg", 27.5, 72.5));
	    list.add(new Parameter21C(pvec, 58, "Splanchnic Veins", "Resistance", "Splanchnic Veins Resistance", 
				      "PRU", 0.0, 0.19));
	    list.add(new Parameter21C(pvec, 79, "Splanchnic Veins", "Volume", "Splanchnic Veins Zero-Pressure Filling Volume", 
				      "mL", 850.0, 1450.0));

	    list.add(new Parameter21C(pvec, 42, "Superior Vena Cava", "Compliance", "Superior Vena Cava Compliance", 
				      "mL/mmHg", 1.0, 1.6));
	    list.add(new Parameter21C(pvec, 63, "Superior Vena Cava", "Resistance", "Superior Vena Cava Resistance", 
				      "PRU", 0.0, 0.056));
	    list.add(new Parameter21C(pvec, 83, "Superior Vena Cava", "Volume", "Superior Vena Cava Zero-Pressure Filling Volume", 
				      "mL", 4.0, 28.0));

	    list.add(new Parameter21C(pvec, 99, "Thoracic Aorta", "Compliance", "Thoracic Aorta Compliance", 
				      "mL/mmHg", 0.05, 0.30));
	    list.add(new Parameter21C(pvec, 107, "Thoracic Aorta", "Resistance", "Thoracic Aorta Resistance", 
				      "PRU", 0.005, 0.017));
	    list.add(new Parameter21C(pvec, 138, "Thoracic Aorta", "Volume", "Thoracic Aorta Zero-Pressure Filling Volume", 
				      "mL", 80.0, 320.0));

	    list.add(new Parameter21C(pvec, 100, "Upper Body Arteries", "Compliance", "Upper Body Arteries Compliance", 
				      "mL/mmHg", 0.1, 0.7));
	    list.add(new Parameter21C(pvec, 106, "Upper Body Arteries", "Resistance", "Upper Body Arteries Resistance", 
				      "PRU", 3.3, 6.5));
	    list.add(new Parameter21C(pvec, 139, "Upper Body Arteries", "Volume", "Upper Body Arteries Zero-Pressure Filling Volume", 
				      "mL", 10.0, 32.0));

	    list.add(new Parameter21C(pvec, 36, "Upper Body Veins", "Compliance", "Upper Body Veins Compliance", 
				      "mL/mmHg", 1.0, 13.0));
	    list.add(new Parameter21C(pvec, 54, "Upper Body Veins", "Resistance", "Upper Body Veins Resistance", 
				      "PRU", 0.0, 0.26));
	    list.add(new Parameter21C(pvec, 77, "Upper Body Veins", "Volume", "Upper Body Veins Zero-Pressure Filling Volume", 
				      "mL", 425.0, 765.0));

	    // Pulmonary 
	    list.add(new Parameter21C(pvec, 47, "Pulmonary Arteries", "Compliance", "Pulmonary Arterial Compliance", 
				      "mL/mmHg", 1.5, 7.2));
	    list.add(new Parameter21C(pvec, 86, "Pulmonary Arteries", "Volume", "Pulmonary Arterial Zero-Pressure Filling Volume", 
				      "mL", 100.0, 220.0));

	    list.add(new Parameter21C(pvec, 48, "Pulmonary Veins", "Compliance", "Pulmonary Venous Compliance", 
				      "mL/mmHg", 5.3, 12.7));
	    list.add(new Parameter21C(pvec, 67, "Pulmonary Veins", "Resistance", "Pulmonary Venous Resistance", 
				      "PRU", 0.0, 0.015));
	    list.add(new Parameter21C(pvec, 87, "Pulmonary Veins", "Volume", "Pulmonary Venous Zero-Pressure Filling Volume", 
				      "mL", 180.0, 580.0));

	    // Microvascular Resistance
	    list.add(new Parameter21C(pvec, 53, "Upper Body Microcirculation", "Resistance", "Upper Body Microcirculation Resistance", 
				      "PRU", 3.3, 6.5));
	    list.add(new Parameter21C(pvec, 57, "Splanchnic Microcirculation", "Resistance", "Splanchnic Microcirculation Resistance", 
				      "PRU", 2.3, 4.3));
	    list.add(new Parameter21C(pvec, 55, "Renal Microcirculation", "Resistance", "Renal Microcirculation Resistance", 
				      "PRU", 3.2, 6.2));
	    list.add(new Parameter21C(pvec, 59, "Lower Body Microcirculation", "Resistance", "Lower Body Microcirculation Resistance", 
				      "PRU", 4.0, 10.3));
	    list.add(new Parameter21C(pvec, 66, "Pulmonary Microcirculation", "Resistance", "Pulmonary Microcirculation Resistance", 
				      "PRU", 0.0, 0.19));
	    

	    // System Parameters
	    list.add(new Parameter21C(pvec, 31, "System Parameters", "Pressure", "Intra-thoracic Pressure", 
				      "mmHg", -20.0, 40.0));
	    list.add(new Parameter21C(pvec, 90, "System Parameters", "Heart Rate", "Nominal Heart Rate", 
				      "beats/min", 50.0, 85.0));
	    list.add(new Parameter21C(pvec, 70, "System Parameters", "Volume", "Total Blood Volume", 
				      "mL", 4041.0, 6460.0));
	
	    // Control System Parameters    

	    // Arterial Baroreflex 
	    list.add(new Parameter21C(pvec, 0, "Arterial Baroreflex", "Set Point", 
				      "ABR Set Point", "mmHg", 89.0, 105.0));

	    list.add(new Parameter21C(pvec, 2, "Arterial Baroreflex", "Gain",
				      "ABR Heart Rate Sympathetic Gain", "ms/mmHg", 0.005, 0.017));

	    list.add(new Parameter21C(pvec, 3, "Arterial Baroreflex", "Gain", 
				      "ABR Heart Rate Parasympathetic Gain", "ms/mmHg", 0.005, 0.017));

	    list.add(new Parameter21C(pvec, 8, "Arterial Baroreflex", "Gain", 
				      "ABR Venous Tone Sympathetic Gain to Upper Body", "ms/mmHg", 3.6, 6.15));
	    
	    list.add(new Parameter21C(pvec, 9, "Arterial Baroreflex", "Gain", 
				      "ABR Venous Tone Sympathetic Gain to Kidney", "ms/mmHg", 0.7, 2.0));
	
	    list.add(new Parameter21C(pvec, 10, "Arterial Baroreflex", "Gain", 
				      "ABR Venous Tone Sympathetic Gain to Splanchnic", "ms/mmHg", 9.0, 17.6));

	    list.add(new Parameter21C(pvec, 11, "Arterial Baroreflex", "Gain", 
				      "ABR Venous Tone Sympathetic Gain to Lower Body", "ms/mmHg", 4.5, 9.0));

	    list.add(new Parameter21C(pvec, 4, "Arterial Baroreflex", "Gain", 
				      "ABR Arterial Resistance Sympathetic Gain to Upper Body", "mL/mmHg^2", -0.15, -0.05));

	    list.add(new Parameter21C(pvec, 5, "Arterial Baroreflex", "Gain", 
				      "ABR Arterial Resistance Sympathetic Gain to Kidney", "mL/mmHg^2", -0.15, -0.05));
	    
	    list.add(new Parameter21C(pvec, 6, "Arterial Baroreflex", "Gain", 
				      "ABR Arterial Resistance Sympathetic Gain to Splanchnic", "mL/mmHg^2", -0.15, -0.05));

	    list.add(new Parameter21C(pvec, 7, "Arterial Baroreflex", "Gain", 
				      "ABR Arterial Resistance Sympathetic Gain to Lower Body", "mL/mmHg^2", -0.15, -0.05));

	    list.add(new Parameter21C(pvec, 12, "Arterial Baroreflex", "Gain", 
				      "ABR Contractility Right Ventricle Sympathetic Gain", "mL/mmHg^2", 0.007, 0.03));
	    list.add(new Parameter21C(pvec, 13, "Arterial Baroreflex", "Gain",
				      "ABR Contractility Left Ventricle Sympathetic Gain", "mL/mmHg^2", 0.004, 0.014));
	    
	    // Cardiopulmonary Reflex
	    list.add(new Parameter21C(pvec, 15, "Cardiopulmonary Reflex", "Set Point",
				      "CPR Set Point", "mmHg", 4.0, 10.0));
	    list.add(new Parameter21C(pvec, 21, "Cardiopulmonary Reflex", "Gain",
				      "CPR Venous Tone Sympathetic Gain to Upper Body", "mL/mmHg^2", 8.1, 19.0));
	    list.add(new Parameter21C(pvec, 22, "Cardiopulmonary Reflex", "Gain",
				      "CPR Venous Tone Sympathetic Gain to Kidney", "mL/mmHg^2", 2.2, 3.2));
	    list.add(new Parameter21C(pvec, 23, "Cardiopulmonary Reflex", "Gain",
				      "CPR Venous Tone Sympathetic Gain to Splanchnic", "mL/mmHg^2", 38.4, 90.0));
	    list.add(new Parameter21C(pvec, 24, "Cardiopulmonary Reflex", "Gain",
				      "CPR Venous Tone Sympathetic Gain to Lower Body", "mL/mmHg^2", 18.0, 42.0));
	    list.add(new Parameter21C(pvec, 17, "Cardiopulmonary Reflex", "Gain",
				      "CPR Arterial Resistance Sympathetic Gain to Upper Body", "mL/mmHg^2", -0.4, -0.2));
	    list.add(new Parameter21C(pvec, 18, "Cardiopulmonary Reflex", "Gain",
				      "CPR Arterial Resistance Sympathetic Gain to Kidney", "mL/mmHg^2", -0.4, -0.2));
	    list.add(new Parameter21C(pvec, 19, "Cardiopulmonary Reflex", "Gain",
				      "CPR Arterial Resistance Sympathetic Gain to Splanchnic", "mL/mmHg^2", -0.4, -0.2));
	    list.add(new Parameter21C(pvec, 20, "Cardiopulmonary Reflex", "Gain",
				      "CPR Arterial Resistance Sympathetic Gain to Lower Body", "mL/mmHg^2", -0.4, -0.2));	  

	    // register listeners for each parameter in the list, so that
	    // we can notify our listeners of parameter changes.
	    for (Parameter p: list) {
		p.addPropertyChangeListener(SimulationParameterChangeListener);
	    }

	    // alphabetize list by Parameter name	    
	    Parameter[] array = new Parameter[list.size()];
	    ComparatorX c = new ComparatorX();
	    list.toArray(array);
	    Arrays.sort(array, c);
	    list = Arrays.asList(array);

	    return list;
	}
    }
}
