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
  double q0;
  double q1;
  double q2;
  double q3;
  double q4;
  double q5;
  double v0;
  double v1;
  double v2;
  double v3;
  double v4;
  double v5;
  double HR;  // heart rate (reflex.hr[2])
  double AR;  // arterial resistance (reflex.resistance[0])
  double VT;  // venous tone (reflex.volume[0])
  double RVC; // right ventricle contractility (reflex.compliance[0])
  double LVC; // left ventricle contractility (reflex.compliance[1])
  double TBV; // total blood volume
  double Pth; // intrathoracic pressure
} output;


void init_sim(Parameter_vector *a);
void step_sim(output *o, Parameter_vector *a, int dataCompressionFactor, 
              int ABReflexOn, int CPReflexOn);
void reset_sim();

// Parameter update constraints
void updateTotalBloodVolume(double tbv_new, Parameter_vector *a);
void updateTotalZeroPressureFillingVolume(double zpfv_new, Parameter_vector *a);
void updateIntrathoracicPressure(double Pth_new, Parameter_vector *a);
void updatePulmonaryArterialCompliance(double C_new, Parameter_vector *a);
void updatePulmonaryVenousCompliance(double C_new, Parameter_vector *a);
void updateArterialCompliance(double C_new, Parameter_vector *a);
void updateVenousCompliance(double C_new, Parameter_vector *a);
void updateParameter(double newValue, Parameter_vector *a, int index);
