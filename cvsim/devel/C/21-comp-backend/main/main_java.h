/* This file was created in order to run the simulation from the Java GUI.
 * The functions in this file combine the main() function in main.c and
 * the simulator() function in simulator.c. The simulation was broken up into
 * into three parts: an init_sim() function to initialize the simulation, 
 * a step_sim() function to advance the simulation one timestep, and a reset_sim() 
 * function to reset variables to their initial values. An output struct was also
 * created to consolidate all the simulation output variables in one place.
 *
 * Catherine Dunn    July 10, 2006
 * Last modified     July 10, 2006
 */

typedef struct {
  double time;
 
  double x0;
  double x1;
  double x2;
  double x3;
  double x4;
  double x5;
  double x6;
  double x7;
  double x8;
  double x9;
  double x10;
  double x11;
  double x12;
  double x13;
  double x14;
  double x15;
  double x16;
  double x17;
  double x18;
  double x19;
  double x20;

  double q0;
  double q1;
  double q2;
  double q3;
  double q4;
  double q5;
  double q6;
  double q7;
  double q8;
  double q9;
  double q10;
  double q11;
  double q12;
  double q13;
  double q14;
  double q15;
  double q16;
  double q17;
  double q18;
  double q19;
  double q20;

  double v0;
  double v1;
  double v2;
  double v3;
  double v4;
  double v5;
  double v6;
  double v7;
  double v8;
  double v9;
  double v10;
  double v11;
  double v12;
  double v13;
  double v14;
  double v15;
  double v16;
  double v17;
  double v18;
  double v19;
  double v20;

  double HR;  // heart rate (reflex.hr[2])
  double AR;  // arterial resistance (reflex.resistance[0])
  double VT;  // venous tone (reflex.volume[0])
  double RVC; // right ventricle contractility (reflex.compliance[0])
  double LVC; // left ventricle contractility (reflex.compliance[1]) 

  double TBV; // total blood volume
  double Pth; // intrathoracic pressure
  
  double tiltAngle; // tilt angle

} output;


void init_sim(Parameter_vector *a);
void step_sim(output *stepout, Parameter_vector *a, int dataCompressionFactor, 
	      int ABReflexOn, int CPReflexOn, 
              int tiltTestOn, double tiltStartTime, double tiltStopTime);
void reset_sim();

// Parameter update constraints
void updateTotalBloodVolume(double tbv_new, Parameter_vector *a);
void updateZeroPressureFillingVolume(double zpfv_new, Parameter_vector *a, int zpfv_index, int c_index, int p_index);
void updateIntrathoracicPressure(double Pth_new, Parameter_vector *a);
void updateComplianceInsideThorax(double C_new, Parameter_vector *a, int c_index, int p_index);
void updateComplianceOutsideThorax(double C_new, Parameter_vector *a, int c_index, int p_index);
void updateParameter(double newValue, Parameter_vector *a, int index);
