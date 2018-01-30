package edu.mit.lcp;

import edu.mit.lcp.C21_comp_backend.main;
import edu.mit.lcp.C21_comp_backend.Parameter_vector;
import javax.swing.JOptionPane;

public class Parameter21C extends Parameter {
    private Parameter_vector paramVec;

    public Parameter21C(Parameter_vector pvec, int index, String category, String name, 
			String units) {
	this(pvec, index, category, category, name, units);
    }

    public Parameter21C(Parameter_vector pvec, int index, String category, String type, 
			String name, String units) {
	super(index, category, type, name, units);
	paramVec = pvec;
	setDefaultValue();
    }

    public Parameter21C(Parameter_vector pvec, int index, String category, String type, 
			String name, String units, double min, double max) {
	super(index, category, type, name, units, min, max);
	paramVec = pvec;
	setDefaultValue();
    }

    public void setValue(Double value) {
	Double oldVal = getValue();
	double tzpfv = 0;

	// Relational Constraints
	
	// Total Zero-Pressure Filling Volume cannot be greater than Total Blood Volume
 	if ( getName().contains("Zero-Pressure Filling Volume") ) {
	    tzpfv = CVSim.sim.calculateTotalZeroPressureFillingVolume();
 	    if ( (value - oldVal + tzpfv) > CVSim.sim.getParameterByName("Total Blood Volume").getValue() ) {
		String msg = String.format("The Total Zero-Pressure Filling Volume cannot be greater than the Total Blood Volume.");
		JOptionPane.showMessageDialog(CVSim.gui.frame, msg);
		return;
	    }
	}
	if ( getName().equals("Total Blood Volume") ) {
	    tzpfv = CVSim.sim.calculateTotalZeroPressureFillingVolume();
	    if ( value < tzpfv ) {
		String msg = String.format("The Total Blood Volume cannot be less than the Total Zero-Pressure Filling Volume.");
		JOptionPane.showMessageDialog(CVSim.gui.frame, msg);
		return;
	    }
	}

	// LV Systolic Compliance cannot be greater than LV Diastolic Compliance
	if ( getName().equals("Left Ventricle Systolic Compliance") )
	    if ( value > CVSim.sim.getParameterByName("Left Ventricle Diastolic Compliance").getValue() ) {
		String msg = String.format("The Left Ventricle Systolic Compliance cannot be greater than the Left Ventricle Diastolic Compliance.");
		JOptionPane.showMessageDialog(CVSim.gui.frame, msg);
		return;
	    }
	if ( getName().equals("Left Ventricle Diastolic Compliance") )
	    if ( value < CVSim.sim.getParameterByName("Left Ventricle Systolic Compliance").getValue() ) {
		String msg = String.format("The Left Ventricle Diastolic Compliance cannot be less than the Left Ventricle Systolic Compliance.");
		JOptionPane.showMessageDialog(CVSim.gui.frame, msg);
		return;
	    }

	// RV Systolic Compliance cannot be greater than RV Diastolic Compliance
	if ( getName().equals("Right Ventricle Systolic Compliance") )
	    if ( value > CVSim.sim.getParameterByName("Right Ventricle Diastolic Compliance").getValue() ) {
		String msg = String.format("The Right Ventricle Systolic Compliance cannot be greater than the Right Ventricle Diastolic Compliance.");
		JOptionPane.showMessageDialog(CVSim.gui.frame, msg);
		return;
	    }
	if ( getName().equals("Right Ventricle Diastolic Compliance") )
	    if ( value < CVSim.sim.getParameterByName("Right Ventricle Systolic Compliance").getValue() ) {
		String msg = String.format("The Right Ventricle Diastolic Compliance cannot be less than the Right Ventricle Systolic Compliance.");
		JOptionPane.showMessageDialog(CVSim.gui.frame, msg);
		return;
	    }


	// Check that value entered by user is not outside the valid parameter range
	if ( (CVSim.gui.parameterPanel.getDisplayMode()).equals(CVSim.gui.parameterPanel.DEFAULT) ) {
	    if ( value < getMin() ) {
		String msg = String.format("The value you entered for " + getName() + 
					   " is outside \nthe allowed range. Please enter a value between %.3f and %.3f.",
					   getMin(), getMax());
		JOptionPane.showMessageDialog(CVSim.gui.frame, msg);
		value = getMin();
	    } else if ( value > getMax() ) {
		String msg = String.format("The value you entered for " + getName() + 
					   " is outside \nthe allowed range. Please enter a value between %.3f and %.3f.",
					   getMin(), getMax());
		JOptionPane.showMessageDialog(CVSim.gui.frame, msg);
		value = getMax();
	    }
	}

	// Parameter Updates

	// Compliances inside the thorax
	if ( getName().equals("Brachiocephalic Arteries Compliance") )
	    main.updateComplianceInsideThorax(value, paramVec, 98, 1);
	else if ( getName().equals("Superior Vena Cava Compliance") )
	    main.updateComplianceInsideThorax(value, paramVec, 42, 4);
	else if ( getName().equals("Inferior Vena Cava Compliance") )
	    main.updateComplianceInsideThorax(value, paramVec, 41, 14);
	else if ( getName().equals("Thoracic Aorta Compliance") )
	    main.updateComplianceInsideThorax(value, paramVec, 99, 5);
	else if ( getName().equals("Ascending Aorta Compliance") )
	    main.updateComplianceInsideThorax(value, paramVec, 97, 0);
	else if ( getName().equals("Pulmonary Arterial Compliance") )
	    main.updateComplianceInsideThorax(value, paramVec, 47, 17);
	else if ( getName().equals("Pulmonary Venous Compliance") )
	    main.updateComplianceInsideThorax(value, paramVec, 48, 18);

	// Compliances outside the thorax
       else if ( getName().equals("Abdominal Aorta Compliance") )
	   main.updateComplianceOutsideThorax(value, paramVec, 101, 6);
       else if ( getName().equals("Abdominal Veins Compliance") )
	   main.updateComplianceOutsideThorax(value, paramVec, 40, 13);
       else if ( getName().equals("Lower Body Arteries Compliance") )
	   main.updateComplianceOutsideThorax(value, paramVec, 104, 11);
       else if ( getName().equals("Lower Body Veins Compliance") )
	   main.updateComplianceOutsideThorax(value, paramVec, 39, 12);
	else if ( getName().equals("Renal Arteries Compliance") )
	    main.updateComplianceOutsideThorax(value, paramVec, 102, 7);
       else if ( getName().equals("Renal Veins Compliance") )
	   main.updateComplianceOutsideThorax(value, paramVec, 37, 8);	
       else if ( getName().equals("Splanchnic Arteries Compliance") )
	   main.updateComplianceOutsideThorax(value, paramVec, 103, 9);
       else if ( getName().equals("Splanchnic Veins Compliance") )
	   main.updateComplianceOutsideThorax(value, paramVec, 38, 10);
       else if ( getName().equals("Upper Body Arteries Compliance") )
	   main.updateComplianceOutsideThorax(value, paramVec, 100, 2);
       else if ( getName().equals("Upper Body Veins Compliance") )
	   main.updateComplianceOutsideThorax(value, paramVec, 36, 3);

	// Total Blood Volume
	else if ( getName().equals("Total Blood Volume") )
	    main.updateTotalBloodVolume(value, paramVec);

 	// Intra-thoracic Pressure
	else if ( getName().equals("Intra-thoracic Pressure") )
 	    main.updateIntrathoracicPressure(value, paramVec);

	// Zero-Pressure Filling Volume
	else if ( getName().equals("Abdominal Aorta Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 140, 101, 6);
	else if ( getName().equals("Abdominal Veins Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 81, 40, 13);
	else if ( getName().equals("Ascending Aorta Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 136, 97, 0);
	else if ( getName().equals("Brachiocephalic Arteries Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 137, 98, 1);
	else if ( getName().equals("Inferior Vena Cava Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 82, 41, 14);
	else if ( getName().equals("Lower Body Arteries Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 143, 104, 11);
	else if ( getName().equals("Lower Body Veins Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 80, 39, 12);
	else if ( getName().equals("Renal Arteries Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 141, 102, 7);	
	else if ( getName().equals("Renal Veins Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 78, 37, 8);	
	else if ( getName().equals("Splanchnic Arteries Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 142, 103, 9);	
	else if ( getName().equals("Splanchnic Veins Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 79, 38, 10);	
	else if ( getName().equals("Superior Vena Cava Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 83, 42, 4);		
	else if ( getName().equals("Thoracic Aorta Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 138, 99, 5);
	else if ( getName().equals("Upper Body Arteries Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 139, 100, 2);				
	else if ( getName().equals("Upper Body Veins Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 77, 36, 3);		
	else if ( getName().equals("Pulmonary Arterial Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 86, 47, 17);		
	else if ( getName().equals("Pulmonary Venous Zero-Pressure Filling Volume") )
	    main.updateZeroPressureFillingVolume(value, paramVec, 87, 48, 18);		

	// Everything else
	else
	    main.updateParameter(value, paramVec, getIndex());

	firePropertyChange("VALUE", oldVal, value);
	
	String str = String.format(getName() + " changed from %.3f to %.3f.", 
				   oldVal, getValue());
	System.out.println(str);	
    }
	
    public Double getValue() {
	return paramVec.getVec(getIndex());
    }
}
