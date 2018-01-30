package edu.mit.lcp;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.mit.lcp.C6_comp_backend.Parameter_vector;
import edu.mit.lcp.C6_comp_backend.output;
import edu.mit.lcp.C6_comp_backend.main;
import java.lang.reflect.Array;

public class CSimulation6C extends CSimulation {

    // C structure which holds the results of the last simulation step
    private output output;
    
    // Simulation Parameters for the C code
    private CSimulation6CParameters simParameters;

    public CSimulation6C() {
	super();
	System.err.println("CSimulation6C()");	
	
	// try to import C library
	try {
	    System.err.println("Loading shared C library");
	    System.loadLibrary("C6_comp_backend");
	} catch (UnsatisfiedLinkError e) {
	    System.err.println("C library failed to load" + e);
	    System.exit(1);
	}

	// create a new instance of the output class which provides
	// access to the data structures in the loaded C library
	output = new output();
      
	simParameters = new CSimulation6CParameters();

	// Create output variable list
	varList = new ArrayList<SimulationOutputVariable>();

	varList.add(new SimulationOutputVariable<Double>
		     (0, "TIME", "Time", "s", "Systemic Parameters", "Time",
		      new Range<Double>(0.0, 0.0)) );

	// Left Ventricle
	varList.add(new SimulationOutputVariable<Double>
		     (1, "LVP", "Left Ventricle Pressure", "mmHg", "Left Heart", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X0
	
	varList.add(new SimulationOutputVariable<Double>
		     (2, "LVQ", "Left Ventricle Flow", "mL/s", "Left Heart", "Flow", 
		      new Range<Double>(0.0, 1500.0)) ); // Q0

	varList.add(new SimulationOutputVariable<Double>
		     (3, "LVV", "Left Ventricle Volume", "mL", "Left Heart", "Volume", 
		      new Range<Double>(0.0, 200.0)) ); // V0

	// Arterial 
	varList.add(new SimulationOutputVariable<Double>
		     (4, "AP", "Arterial Pressure", "mmHg", "Systemic Arteries", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X1

	varList.add(new SimulationOutputVariable<Double>
		     (5, "AQ", "Arterial Flow", "mL/s", "Systemic Arteries", "Flow",
		      new Range<Double>(0.0, 150.0)) ); // Q1

	varList.add(new SimulationOutputVariable<Double>
		     (6, "AV",  "Arterial Volume", "mL", "Systemic Arteries", "Volume", 
		      new Range<Double>(0.0, 1250.0)) ); //V1
	
	// Central Venous
	varList.add(new SimulationOutputVariable<Double>
		     (7, "CVP", "Central Venous Pressure", "mmHg", "Systemic Veins", "Pressure", 
		      new Range<Double>(0.0, 200.0)) ); // X2
	
	varList.add(new SimulationOutputVariable<Double>
		     (8, "CVQ", "Central Venous Flow", "mL/s", "Systemic Veins", "Flow", 
		      new Range<Double>(0.0, 250.0)) ); // Q2

	varList.add(new SimulationOutputVariable<Double>
		     (9, "CVV", "Central Venous Volume", "mL", "Systemic Veins", "Volume", 
		      new Range<Double>(0.0, 3500.0)) ); // V2
	
	// Right Ventricle
	varList.add(new SimulationOutputVariable<Double>
		     (10, "RVP", "Right Ventricle Pressure", "mmHg", "Right Heart", "Pressure", 
		      new Range<Double>(0.0, 200.0)) ); // X3
	
	varList.add(new SimulationOutputVariable<Double>
		     (11, "RVQ", "Right Ventricle Flow", "mL/s", "Right Heart", "Flow", 
		      new Range<Double>(0.0, 1000.0)) ); // Q3
	
	varList.add(new SimulationOutputVariable<Double>
		     (12, "RVV", "Right Ventricle Volume", "mL", "Right Heart", "Volume",
		      new Range<Double>(0.0, 200.0)) ); // V3	

	// Pulmonary Arterial
	varList.add(new SimulationOutputVariable<Double>
		     (13, "PAP", "Pulmonary Arterial Pressure", "mmHg", "Pulmonary Arteries", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X4

	varList.add(new SimulationOutputVariable<Double>
		     (14, "PAQ", "Pulmonary Arterial Flow", "mL/s", "Pulmonary Arteries", "Flow", 
		      new Range<Double>(0.0, 225.0)) ); // Q4

	varList.add(new SimulationOutputVariable<Double>
		     (15, "PAV", "Pulmonary Arterial Volume", "mL", "Pulmonary Arteries", "Volume", 
		      new Range<Double>(0.0, 225.0)) ); // V4

	
	// Pulmonary Venous
	varList.add(new SimulationOutputVariable<Double>
		     (16, "PVP", "Pulmonary Venous Pressure", "mmHg", "Pulmonary Veins", "Pressure",
		      new Range<Double>(0.0, 200.0)) ); // X5
	
	varList.add(new SimulationOutputVariable<Double>
		     (17, "PVQ", "Pulmonary Venous Flow", "mL/s", "Pulmonary Veins", "Flow", 
		      new Range<Double>(0.0, 2000.0)) ); // Q5
	
	varList.add(new SimulationOutputVariable<Double>
		     (18, "PVV", "Pulmonary Venous Volume", "mL", "Pulmonary Veins", "Volume",
		      new Range<Double>(0.0, 1000.0)) ); // V5
    

	// Reflex Outputs (Controlled Variables)
	varList.add(new SimulationOutputVariable<Double> 
		    (19, "HR", "Heart Rate", "beats/min", "Systemic Parameters", "Reflex",
		     new Range<Double>(0.0, 100.0)) ); // Heart Rate
	
	varList.add(new SimulationOutputVariable<Double> 
		    (20, "AR", "Arteriolar Resistance", "PRU", "Systemic Arteries", "Reflex",
		     new Range<Double>(0.0, 10.0)) ); // Arterial Resistance
    
	varList.add(new SimulationOutputVariable<Double>
		    (21, "VT", "Venous Tone", "mL", "Systemic Veins", "Reflex",
		     new Range<Double>(0.0, 2500.0)) ); // Venous Tone
	
	varList.add(new SimulationOutputVariable<Double>
		    (22, "RVC", "Right Ventricle Contractility", "mL/mmHg", "Right Heart", "Reflex",
		     new Range<Double>(0.0, 10.0)) );

	varList.add(new SimulationOutputVariable<Double>
		    (23, "LVC", "Left Ventricle Contractility", "mL/mmHg", "Left Heart", "Reflex",
		     new Range<Double>(0.0, 10.0)) );

	// Total Blood Volume 
	varList.add(new SimulationOutputVariable<Double>
		    (24, "TBV", "Total Blood Volume", "mL", "Systemic Parameters", "Volume",
		     new Range<Double>(0.0, 5500.0)) );

	varList.add(new SimulationOutputVariable<Double>
		    (25, "PTH", "Intra-thoracic Pressure", "mmHg", "Systemic Parameters", "Pressure",
		     new Range<Double>(-10.0, 10.0)) );

    }

    public void reset() {
	main.reset_sim();
    }

    // This method steps the simulation
    public void step() {
	    
	// run simulation and get updated model measurements
	main.step_sim(output, simParameters.getVector(),
			       getDataCompressionFactor(),
			       getABReflex_C(), 
			       getCPReflex_C());
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
	    // Left Ventrical
	case 1  : value = output.getX0(); break;   // LVP
	case 2  : value = output.getQ0(); break;   // LVQ
	case 3  : value = output.getV0(); break;   // LVV
	    // Arterial
	case 4  : value = output.getX1(); break;   // AP
	case 5  : value = output.getQ1(); break;   // AQ
	case 6  : value = output.getV1(); break;   // AV
	    // Central Venous
	case 7  : value = output.getX2(); break;   // CVP
	case 8  : value = output.getQ2(); break;   // CVQ
	case 9  : value = output.getV2(); break;   // CVV
	    // Right Ventrical
	case 10 : value = output.getX3(); break;   // RVP
	case 11 : value = output.getQ3(); break;   // RVQ
	case 12 : value = output.getV3(); break;   // RVV
	    // Pulmonary Arterial
	case 13 : value = output.getX4(); break;   // PAP
	case 14 : value = output.getQ4(); break;   // PAQ
	case 15 : value = output.getV4(); break;   // PAV
	    // Pulmonary Venous
	case 16 : value = output.getX5(); break;   // PVP
	case 17 : value = output.getQ5(); break;   // PVQ
	case 18 : value = output.getV5(); break;   // PVV

	case 19 : value = output.getHR(); break; // HR
	case 20 : value = output.getAR(); break; // AR
	case 21 : value = output.getVT(); break; // VT
	case 22 : value = output.getRVC(); break; // RVC
	case 23 : value = output.getLVC(); break; // LVC

	case 24 : value = output.getTBV(); break; // TBV
	case 25 : value = output.getPth(); break; // Pth

	default: value = 0.0; break;
	}
	
	return value;
    }

    public void updatePressure(int i, double d) {
	main.updatePressure(i, d);
    }   

    public List<Parameter> getParameterList() {
	return simParameters.getParameterList();
    }
    public Parameter getParameterByName(String name) {
	return simParameters.getParameterByName(name);
    }

    ///////////////////////////////////////
    ///////////////////////////////////////
    private class CSimulation6CParameters {

	private Parameter_vector pvec;
	private List<Parameter> plist;

	CSimulation6CParameters() {
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

	    // The min and max values are from the cvlib/parameter.db.safe file
	    // in Tim Davis's code.

	    // Is this a terrible way to do things? Absolutely. But
	    // doing things the right way would require rewriting
	    // a significant part of the C code, and there is not enough 
	    // time to do that.

	    List<Parameter> list = new ArrayList<Parameter>();

	    // Compliance
	    list.add(new Parameter6C(pvec, 51, "Left Heart", "Compliance",
				   "Left Ventricle Diastolic Compliance",
				     "mL/mmHg", 0.2, 20.0));
	
	    list.add(new Parameter6C(pvec, 52, "Left Heart", "Compliance",
				   "Left Ventricle Systolic Compliance",
				     "mL/mmHg", 0.1, 20.0));
	
	    list.add(new Parameter6C(pvec, 45, "Right Heart", "Compliance",
				   "Right Ventricle Diastolic Compliance",
				     "mL/mmHg", 0.2, 40.0));
	
	    list.add(new Parameter6C(pvec, 46, "Right Heart", "Compliance",
				   "Right Ventricle Systolic Compliance",
				     "mL/mmHg", 0.2, 40.0));
	
	    list.add(new Parameter6C(pvec, 47, "Pulmonary Arteries", "Compliance",
				   "Pulmonary Arterial Compliance",
				     "mL/mmHg", 0.1, 20.0));
	
	    list.add(new Parameter6C(pvec, 48, "Pulmonary Veins", "Compliance",
				   "Pulmonary Venous Compliance",
				     "mL/mmHg", 0.1, 40.0));
	
	    list.add(new Parameter6C(pvec, 153, "Systemic Arteries", "Compliance",
				     "Arterial Compliance",
				     "mL/mmHg", 0.1, 20.0));
	
	    list.add(new Parameter6C(pvec, 154, "Systemic Veins", "Compliance",
				   "Venous Compliance",
				     "mL/mmHg", 0.1, 500.0));

	    // Resistance
	    list.add(new Parameter6C(pvec, 155, "Left Heart",  "Resistance",
				   "Aortic Valve Resistance", 
				     "PRU", 0.001, 2.0));

	    list.add(new Parameter6C(pvec, 65, "Right Heart",  "Resistance",
				   "Pulmonic Valve Resistance",
				     "PRU", 0.001, 2.0));

	    list.add(new Parameter6C(pvec, 66, "Pulmonary Microcirculation", "Resistance",
				     "Pulmonary Microcirculation Resistance",
				     "PRU", 0.01, 2.0));

	    list.add(new Parameter6C(pvec, 67, "Pulmonary Veins", "Resistance",
				   "Pulmonary Venous Resistance",
				     "PRU", 0.01, 2.0));

	    list.add(new Parameter6C(pvec, 156, "Systemic Microcirculation", "Resistance",
				   "Total Peripheral Resistance",
				     "PRU", 0.01, 10.0));

	    list.add(new Parameter6C(pvec, 157, "Systemic Veins", "Resistance",
				   "Venous Resistance",
				     "PRU", 0.01, 2.0));

	    // Systemic
	    list.add(new Parameter6C(pvec, 31, "Systemic Parameters",  "Pressure",
				   "Intra-thoracic Pressure",
				     "mmHg", -20.0, 20.0));
	    
	    list.add(new Parameter6C(pvec, 70, "Systemic Parameters",  "Volume",
				   "Total Blood Volume",
				     "mL", 100.0, 10000.0));

	    list.add(new Parameter6C(pvec, 75, "Systemic Parameters",  "Volume",
				   "Total Zero-Pressure Filling Volume",
				     "mL", 0, 10000.0));

	    list.add(new Parameter6C(pvec, 90, "Systemic Parameters",  "Heart Rate",
				     "Nominal Heart Rate",
				     "beats/min", 20.0, 250.0));

	    // Control System Parameters

	    // Arterial Baroreflex
	    list.add(new Parameter6C(pvec, 0, "Arterial Baroreflex", "Set Point", 
				     "ABR Set Point",
				     "mmHg", 89.0, 105.0));

	    list.add(new Parameter6C(pvec, 3, "Arterial Baroreflex", "Gain", 
				   "ABR Heart Rate Parasympathetic Gain",
				     "ms/mmHg", 0.005, 0.017));

	    list.add(new Parameter6C(pvec, 2, "Arterial Baroreflex", "Gain", 
				   "ABR Heart Rate Sympathetic Gain",
				     "ms/mmHg", 0.005, 0.017));
	
	    list.add(new Parameter6C(pvec, 160, "Arterial Baroreflex", "Gain", 
				   "ABR Venous Tone Sympathetic Gain",
				   "mL/mmHg"));

	    list.add(new Parameter6C(pvec, 158, "Arterial Baroreflex", "Gain",
				   "ABR Arterial Resistance Sympathetic Gain",
				   "PRU/mmHg"));

	    list.add(new Parameter6C(pvec, 12, "Arterial Baroreflex", "Gain",
				   "ABR Contractility Right Ventricle Sympathetic Gain",
				     "mL/mmHg^2", 0.007, 0.030));
	
	    list.add(new Parameter6C(pvec, 13, "Arterial Baroreflex", "Gain",
				   "ABR Contractility Left Ventricle Sympathetic Gain",
				     "mL/mmHg^2", 0.004, 0.014));

	    // Cardiopulmonary Reflex
	    list.add(new Parameter6C(pvec, 15, "Cardiopulmonary Reflex", "Set Point",
				   "CPR Set Point",
				     "mmHg", 4.0, 10.0));

	    list.add(new Parameter6C(pvec, 161, "Cardiopulmonary Reflex", "Gain",
				   "CPR Venous Tone Sympathetic Gain",
				   "mL/mmHg"));


	    list.add(new Parameter6C(pvec, 159, "Cardiopulmonary Reflex", "Gain",
				   "CPR Arterial Resistance Sympathetic Gain",
				   "PRU/mmHg"));

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
