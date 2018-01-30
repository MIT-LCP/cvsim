package edu.mit.lcp;

import java.io.*;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class OutputFile {
    
    File dataFile;
    List<SimulationOutputVariable> outputList;
    List<Parameter> parameterList;
    List<VariableRecorderInterface> recList;
    PrintStream dataPrintStream;
    private ChangeListener sourceDataChanged;


    public OutputFile(List<Parameter> plist, List<SimulationOutputVariable> olist, File file) {
	parameterList = new ArrayList<Parameter>(plist);
	outputList = new ArrayList<SimulationOutputVariable>(olist);
	dataFile = file;

	recList = new ArrayList<VariableRecorderInterface>(outputList.size());

	sourceDataChanged = new ChangeListener() {
		public void stateChanged(ChangeEvent event) {
		    writeData();
		}
	    };
    }

    public void startLogging() {
	
	// create the recorders to attach to simulation
	for (SimulationOutputVariable v: outputList ) {
	    recList.add(new SimulationOutputVariableBuffer(v, new Double(0.0)));
	}

	// attach the recorders to the simulation
	for (VariableRecorderInterface r : recList)
	    CVSim.sim.addVariableRecorder(r);

	// prepare the output file
	openFile();
	writeHeader();

	// begin receiving changes
	CVSim.sim.addChangeListener(sourceDataChanged);
    }

    public void stopLogging() {
	// stop receiving changes
	CVSim.sim.removeChangeListener(sourceDataChanged);

	// remove the recorders from the simulation
	for (VariableRecorderInterface r : recList)
	    CVSim.sim.removeVariableRecorder(r);

	// close the output file
	closeFile();
    }

    private void openFile() {
	try {
	    dataPrintStream = new PrintStream(dataFile);
	} catch (Exception e) {
	    System.err.println ("Error opening file " + dataFile);
	    System.err.println (e);
	}
    }

    private void closeFile() {
	try {
	    dataPrintStream.close();
	}  catch (Exception e) {
	    System.err.println ("Error closing file " + dataFile);
	    System.err.println (e);
	}
    }

    private void writeData() {
	// write outputs first then parameters
	for (VariableRecorderInterface r : recList) {
	    dataPrintStream.printf("%f\t", r.getLastDatum());
	}
	
	for (Parameter p: parameterList) {
	    dataPrintStream.printf("%f\t", p.getValue());
	}

	dataPrintStream.printf("\n");
    }

    public void writeHeader() {
	// create header filename based on the data filename
	// example: datafile: outputs.txt; headerfile: outputs_header.txt 
	String filename = dataFile.getName();
	String headerFileName = filename.substring(0, filename.indexOf(".")) + ".hea";
	File headerFile = new File(dataFile.getParent(), headerFileName);

	try {
	    PrintStream headerPrintStream = new PrintStream(headerFile);
	    headerPrintStream.printf("MIT Cardiovascular Simulator Output Header File\n");
	    headerPrintStream.printf("This file describes the data in " 
				     + dataFile.getName() + ".\n\n");
	    int colNum = 1;
	    for (VariableRecorderInterface r: recList) {
		headerPrintStream.printf("Column %d:\t" + r.getDescription() + " (" 
					 + r.getUnits() + ")\n", colNum);
		colNum++;
	    }
	    for (Parameter p: parameterList) {
		headerPrintStream.printf("Column %d:\t" + p.getName() + " (" 
					 + p.getUnits() + ")\n", colNum);
		colNum++;
	    }

	    headerPrintStream.close();
	} catch (Exception e) {
	    System.err.println ("Error writing file header to " + headerFile);
	    System.err.println (e);
	}
    }	
}
