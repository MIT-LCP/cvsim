/* This file was created in order to run the simulation from the Java GUI.
 * The functions in this file combine the main() function in main.c and
 * the simulator() function in simulator.c. The simulation was broken up into
 * into three parts: an init_sim() function to initialize the simulation, 
 * a step_sim() function to advance the simulation one timestep, and a reset_sim() 
 * function to reset variables to their initial values.
 *
 * Catherine Dunn    July 10, 2006
 * Last modified     July 10, 2006
s */
#include "../sim/simulator.h" 
#include "main_java.h"
#include "turning.h"

int N_OUT = 21;      // Number of output variables in the steady-state
                     // analysis

// Structure handling the flow of data within the simulation.c routine.
// See simulator.h for the definition.
Data_vector pressure = {{0.0}};

// Structure for the reflex variables.
Reflex_vector reflex_vector = {{0.0}};

// Structure for the impulse response functions
Impulse_vector imp = {{0.0}};

// Output structure - defined in main.h
Output_vector out = {{0.0}};

// The following definitions pertain to the adaptive stepsize integration
// routine. See Numerical Recipes in C (p. ???) for details.
double hdid = 0.0;
double htry = 0.00001;
double yscale[] = {1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.};
double hnext = 0.0;

double *result;           // output vector containing the output variables.


// init_sim(): Initializes the simulation
void init_sim(Parameter_vector *a)
{
  
  // Declare parameter value structures.
  Hemo hemo;
  Cardiac cardiac;
  Reflex reflex;
  Micro_r micro_r;
  System_parameters system;
  Timing timing;

  // Allocate memory for data structures.
  result = calloc(N_OUT, sizeof(double));

  // Initialize the parameter structures and the parameter vector.
  initial_ptr(&hemo, &cardiac, &micro_r, &system, &reflex, &timing);
  mapping_ptr(&hemo, &cardiac, &micro_r, &system, &reflex, &timing, a);

  // The following function calls initialize the data vector. Estimate()
  // initializes the pressure.x[] and pressure.time[] arrays. Elastance()
  // computes the cardiac compliances and their derivatives and stores their
  // values in pressure.c[] and pressure.dcdt[], respectively. Finally,
  // eqns() computes the pressure derivatives and stores them in
  // pressure.dxdt[].
  estimate_ptr(&pressure, a, &reflex_vector);      // estimate initial pressures

  // Set up the values for the time-varying capacitance values
  elastance_ptr(&pressure, &reflex_vector, a);
  
  fixvolume_ptr(&pressure, &reflex_vector, a);     // fix the total blood volume

  eqns_ptr(&pressure, a, &reflex_vector); // Initialize pressure derivatives

  makeImp(&imp, a);                    // Set up the impulse resp. arrays

  numerics(&pressure, &reflex_vector, &out, a); // Initialize the ouput array???

}


// This function is needed to update pressure.x[] from the Java code.
// The output struct copies the values in pressure.x and makes them available to 
// the Java code. The output struct is in a sense read-only. If the values in the 
// output struct are changed, the change is not reflected in pressure.x. However, 
// occasionally (as in the case of parameter updates), information must pass from
// the GUI to the simulation variables. This function was created to allow 
// the changes to output.x to be reflected in pressure.x.
void updatePressure(int i, double value) {
  pressure.x[i] = value;
}


// step_sim(): Advances the simulation one timestep
// By varying the dataCompressionFactor the data can be compressed before 
// being passed to the GUI. A dataCompressionFactor of 10 means one piece 
// of output data is passed to the GUI for every 10 timesteps. A 
// dataCompressionFactor of 1 means every piece of data is passed to the GUI.
// (One piece of data for every timestep.)

void step_sim(output *stepout, Parameter_vector *a, int dataCompressionFactor, 
              int ABReflexOn, int CPReflexOn)
{
  double x0[dataCompressionFactor];
  double x1[dataCompressionFactor];
  double x2[dataCompressionFactor];
  double x3[dataCompressionFactor];
  double x4[dataCompressionFactor];
  double x5[dataCompressionFactor];
  double q0[dataCompressionFactor];
  double q1[dataCompressionFactor];
  double q2[dataCompressionFactor];
  double q3[dataCompressionFactor];
  double q4[dataCompressionFactor];
  double q5[dataCompressionFactor];
  double v0[dataCompressionFactor];
  double v1[dataCompressionFactor];
  double v2[dataCompressionFactor];
  double v3[dataCompressionFactor];
  double v4[dataCompressionFactor];
  double v5[dataCompressionFactor];
  double hr[dataCompressionFactor];
  double ar[dataCompressionFactor];
  double vt[dataCompressionFactor];
  double rvc[dataCompressionFactor];
  double lvc[dataCompressionFactor];
  double tbv[dataCompressionFactor];
  double pth[dataCompressionFactor];

  // Calculate output values
  int i;
  for ( i=0; i < dataCompressionFactor; i++ ) 
  {
      rkqc_ptr(&pressure, &reflex_vector, a, htry, 0.001, yscale, &hdid, &hnext);

      pressure.time[1] = sanode(&pressure, &reflex_vector, a, hdid);
      
      elastance_ptr(&pressure, &reflex_vector, a);

      eqns_ptr(&pressure, a, &reflex_vector);

      queue_ptr(&pressure, &imp, &reflex_vector, a, hdid, ABReflexOn, CPReflexOn);

      pressure.time[0] += hdid;

      htry = 0.001;

      fixvolume_ptr(&pressure, &reflex_vector, a);
      
      numerics_new_ptr(&pressure, &reflex_vector, hdid, N_OUT, result);
      
      numerics(&pressure, &reflex_vector, &out, a);

      // simulation time
      stepout->time = pressure.time[0];

      x0[i] = pressure.x[0];
      x1[i] = pressure.x[1];
      x2[i] = pressure.x[2];
      x3[i] = pressure.x[3];
      x4[i] = pressure.x[4];
      x5[i] = pressure.x[5];
  
      q0[i] = pressure.q[0];
      q1[i] = pressure.q[1];
      q2[i] = pressure.q[2];
      q3[i] = pressure.q[3];
      q4[i] = pressure.q[4];
      q5[i] = pressure.q[5];
  
      v0[i] = pressure.v[0];
      v1[i] = pressure.v[1];
      v2[i] = pressure.v[2];
      v3[i] = pressure.v[3];
      v4[i] = pressure.v[4];
      v5[i] = pressure.v[5];

      hr[i]  = reflex_vector.hr[2];
      ar[i]  = reflex_vector.resistance[0];
      vt[i]  = reflex_vector.volume[0];
      rvc[i] = reflex_vector.compliance[0];
      lvc[i] = reflex_vector.compliance[1];
      tbv[i] = a->vec[70];
      pth[i] = a->vec[31];

      // Check TBV
      //double total = 0;
      //int k;
      //for (k=0; k < 25; k++)
      // total += pressure.v[k]; 
      //printf("TBV: %.2f, %.2f\n", a->vec[70], total);

      // Check TZPFV
      //double total = 0;
      //int k;
      //for (k=162; k < 168; k++)
      //  total += a->vec[k];
      //printf("TZPFV: %.2f, %.2f\n", a->vec[75], total);
      
  } // end for
    
  
  // Compress the output by applying turning algorithm
  stepout->x0 = turning(x0, dataCompressionFactor);
  stepout->x1 = turning(x1, dataCompressionFactor);
  stepout->x2 = turning(x2, dataCompressionFactor);
  stepout->x3 = turning(x3, dataCompressionFactor);
  stepout->x4 = turning(x4, dataCompressionFactor);
  stepout->x5 = turning(x5, dataCompressionFactor);

  stepout->q0 = turning(q0, dataCompressionFactor);
  stepout->q1 = turning(q1, dataCompressionFactor);
  stepout->q2 = turning(q2, dataCompressionFactor);
  stepout->q3 = turning(q3, dataCompressionFactor);
  stepout->q4 = turning(q4, dataCompressionFactor);
  stepout->q5 = turning(q5, dataCompressionFactor);

  stepout->v0 = turning(v0, dataCompressionFactor);
  stepout->v1 = turning(v1, dataCompressionFactor);
  stepout->v2 = turning(v2, dataCompressionFactor);
  stepout->v3 = turning(v3, dataCompressionFactor);
  stepout->v4 = turning(v4, dataCompressionFactor);
  stepout->v5 = turning(v5, dataCompressionFactor);
  
  stepout->HR = turning(hr, dataCompressionFactor); 
  stepout->AR = turning(ar, dataCompressionFactor); 
  stepout->VT = turning(vt, dataCompressionFactor); 
  stepout->RVC = turning(rvc, dataCompressionFactor); 
  stepout->LVC = turning(lvc, dataCompressionFactor); 
  stepout->TBV = turning(tbv, dataCompressionFactor);
  stepout->Pth = turning(pth, dataCompressionFactor);
}


void reset_sim()
{
  // The following lines either reset variables to their initial values or
  // call functions that do the same in other files. This resets the entire
  // simulation subroutine to its initial state such that two consecutive
  // simulations start with the same numeric parameters.
  //  numerics_reset();
  queue_reset();
}

// Total blood volume update constraint
// Pv,new = Pv,old + (Vtot,new - Vtot,old) / Cv
void updateTotalBloodVolume(double tbv_new, Parameter_vector *a) {
  // venous compliance
  double Cv = a->vec[154]; 
  // central venous pressure
  double Pv_old = pressure.x[2];
  // old total blood volume 
  double tbv_old = a->vec[70];
  // new total blood volume
  a->vec[70] = tbv_new; 
  // recalculate central venous pressure
  pressure.x[2] = Pv_old + (tbv_new - tbv_old) / Cv;

  printf("Pv,new = Pv,old + (Vtot,new - Vtot,old) / Cv\n");
  printf("Pv,new: %.2f, Pv,old: %.2f, Vtot,new: %.2f, Vtot,old: %.2f, Cv: %.2f\n", pressure.x[2], Pv_old, tbv_new, tbv_old, Cv);
}

// Total zero pressure filling volume update constraint
// Pv,new = Pv,old + (V0,old - V0,new) / Cv
void updateTotalZeroPressureFillingVolume(double zpfv_new, Parameter_vector *a) {
  // venous compliance
  double Cv = a->vec[154];
  // central venous pressure
  double Pv_old = pressure.x[2];
  // old total zero pressure filling volume
  double zpfv_old = a->vec[75];
  // new total zero pressure filling volume
  a->vec[75] = zpfv_new;
  // recalculate central venous pressure
  pressure.x[2] = Pv_old + (zpfv_old - zpfv_new) / Cv;

  printf("Pv,new = Pv,old + (V0,old - V0,new) / Cv\n");
  printf("Pv,new: %.2f, Pv,old: %.2f, V0,old: %.2f, V0,new: %.2f, Cv: %.2f\n", pressure.x[2], Pv_old, zpfv_old, zpfv_new, Cv);
}

// Intra-thoracic pressure update constraint
// For all thoracic compartments.
// P,new = P,old + (Pth,new - Pth,old)
void updateIntrathoracicPressure(double Pth_new, Parameter_vector *a) {
  double P_old;
  printf("P,new = P,old + (Pth,new - Pth,old)\n");  
  
  // old intra-thoracic pressure
  double Pth_old = a->vec[31];
  // new intra-thoracic pressure
  a->vec[31] = Pth_new;
  pressure.x[24] = Pth_new;
  
  // left ventricle pressure
  P_old = pressure.x[0];
  // recalculate left ventricle pressure
  pressure.x[0] = P_old + Pth_new - Pth_old;
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n", pressure.x[0], P_old, Pth_new, Pth_old);

  // right ventricle pressure
  P_old = pressure.x[3];
  // recalculate right ventricle pressure
  pressure.x[3] = P_old + Pth_new - Pth_old;
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n", pressure.x[3], P_old, Pth_new, Pth_old);
  
  // pulmonary arterial pressure
  P_old = pressure.x[4];
  // recalculate pulmonary arterial pressure
  pressure.x[4] = P_old + Pth_new - Pth_old;
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n", pressure.x[4], P_old, Pth_new, Pth_old);

  // pulmonary venous pressure
  P_old = pressure.x[5];
  // recalculate pulmonary venous pressure
  pressure.x[5] = P_old + Pth_new - Pth_old;
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n", pressure.x[5], P_old, Pth_new, Pth_old);
}

// Compliance parameter update constraint
// P,new = P,old * C,old / C,new + Pth * (1 - C,old / C,new)
void updatePulmonaryArterialCompliance(double C_new, Parameter_vector *a) {
  // old pulmonary arterial compliance
  double C_old = a->vec[47];
  // new pulmonary arterial compliance
  a->vec[47] = C_new;
  // old intra-thoracic pressure
  double Pth = a->vec[31];
  // old pulmonary arterial pressure
  double P_old = pressure.x[4];
  // new pulomary arterial compliance
  pressure.x[4] = P_old * C_old / C_new + Pth * (1 - C_old / C_new);
  printf("P,new = P,old * C,old / C,new\n");
  printf("P,new: %.2f, P,old: %.2f, C,old: %.2f, C,new: %.2f\n", pressure.x[4], P_old, C_old, C_new);
 }

// Compliance parameter update constraint
// P,new = P,old * C,old / C,new + Pth * (1 - C,old / C,new)
void updatePulmonaryVenousCompliance(double C_new, Parameter_vector *a) {
  // old pulmonary arterial compliance
  double C_old = a->vec[48];
  // new pulmonary arterial compliance
  a->vec[48] = C_new;
  // old intra-thoracic pressure
  double Pth = a->vec[31];
  // old pulmonary arterial pressure
  double P_old = pressure.x[5];
  // new pulomary arterial compliance
  pressure.x[5] = P_old * C_old / C_new + Pth * (1 - C_old / C_new);
  printf("P,new = P,old * C,old / C,new\n");
  printf("P,new: %.2f, P,old: %.2f, C,old: %.2f, C,new: %.2f\n", pressure.x[5], P_old, C_old, C_new);
}

// Compliance parameter update constraint
// P,new = P,old * C,old / C,new
void updateArterialCompliance(double C_new, Parameter_vector *a) {
  // old pulmonary arterial compliance
  double C_old = a->vec[153];
  // new pulmonary arterial compliance
  a->vec[153] = C_new;
  // old pulmonary arterial pressure
  double P_old = pressure.x[1];
  // new pulomary arterial compliance
  pressure.x[1] = P_old * C_old / C_new;
  printf("P,new = P,old * C,old / C,new\n");
  printf("P,new: %.2f, P,old: %.2f, C,old: %.2f, C,new: %.2f\n", pressure.x[1], P_old, C_old, C_new);
}

// Compliance parameter update constraint
// P,new = P,old * C,old / C,new
void updateVenousCompliance(double C_new, Parameter_vector *a) {
  // old pulmonary arterial compliance
  double C_old = a->vec[154];
  // new pulmonary arterial compliance
  a->vec[154] = C_new;
  // old pulmonary arterial pressure
  double P_old = pressure.x[2];
  // new pulomary arterial compliance
  pressure.x[2] = P_old * C_old / C_new;
  printf("P,new = P,old * C,old / C,new\n");
  printf("P,new: %.2f, P,old: %.2f, C,old: %.2f, C,new: %.2f\n", pressure.x[2], P_old, C_old, C_new);
}

// Update function for parameters with no update constraints
void updateParameter(double newValue, Parameter_vector *a, int index) {
  a->vec[index] = newValue;
}


