package edu.mit.lcp;

import java.util.Timer;
import java.util.TimerTask;

// Class SimulationThread is a wrapper for the Simulation class so it
// can run independently at a fixed rate in a separate thread.

public class SimulationThread {
    private Timer _timer;
    private long _period;
    private String _name;
    private boolean _timerRunning;

    public CSimulation _sim;
    private TimerTask _simTask;

    // Constructor instantiates the actual simulation
    public SimulationThread (CSimulation sim) {
	_sim = sim;
	// default
	_period = 0;
	_timerRunning = false;
    }

    public String getName() { return _name; };    

    public void setPeriod (long period) { 
	_period = period;
	System.out.println("Simulation Period set to " + _period);
	if (_timerRunning) {
	    // restart the timer to use new period
	    stop();
	    start();
	}
    }
	    
    public long getPeriod () { return _period; }
    public boolean isRunning() { return _timerRunning; }

    // schedule the simulation to step after predefined number of
    // milliseconds.  this also starts the simulation
    public void start() {
   	if ( (_period > 0) && !(_timerRunning) ) {
	    System.out.println("SimulationThread.start(): " + _period + "ms");
	    
	    // the actual timer object which is used to execute a TimerTask
	    _timer = new Timer();
	    // a new TimerTask is created, wrapping the step() method of
	    // the simulation to the run() method that is executed by the
	    // Timer. A new TimerTask is required for every new Timer
	    // because a cancelled Timer prevents the associated TimerTask
	    // from being reassigned to a new Timer.  Kinda a dirty hack.
		
	    _simTask = new TimerTask() { public void run() { _sim.step(); } };

	    // scheduleAtFixedRate is preferred over schedule as it
	    // executes every x milliseconds, instead of executing x
	    // milliseconds after the last execution event
	    _timer.scheduleAtFixedRate(_simTask,  // TimerTask
				       0,         // initial delay
				       _period);  //subsequent rate
	    _timerRunning = true;
	}
    }
    
    // The only way to stop a Timer is to cancel it completely, but
    // this does not reset/kill the simlation
    public void stop () { 
	if (_timerRunning) {
	    System.out.println("SimulationThread.stop(): " + _period + "ms");
	    _timer.cancel();
	    _timerRunning = false;
	}
    }

} // end class SimulationThread
