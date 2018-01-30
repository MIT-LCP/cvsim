/* This file was created in order to run the simulation from the Java GUI.
 * The functions in this file combine the main() function in main.c and
 * the simulator() function in simulator.c. The simulation was broken up into
 * into three parts: an init_sim() function to initialize the simulation, 
 * a step_sim() function to advance the simulation one timestep, and a reset_sim() 
 * function to reset variables to their initial values.
 *
 * Catherine Dunn    July 10, 2006
 * Last modified     July 10, 2006
 */
#include "../sim/simulator.h" 
#include "main_java.h"
#include "turning.h"

#define PI M_PI

int N_OUT = 21;           // Number of output variables in the steady-state
                            // analysis
double *result;           // output vector containing the output variables.
double *target;

// Structure handling the flow of data within the simulation.c routine.
// See simulator.h for the definition.
Data_vector pressure = {{0.0}};

// Structure for the reflex variables.
Reflex_vector reflex_vector = {{0.0}};

// Structure for the impulse response functions
Impulse_vector imp = {{0.0}};

// Simulator related definitions
Output_vector out = {{0.0}};

// The following definitions pertain to the adaptive stepsize integration
// routine. See Numerical Recipes in C (p. ???) for details.
double hdid = 0.0;
double htry = 0.00001;
double yscale[] = {1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.};
double hnext = 0.0;


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
  target = calloc(N_SIGNALS*N_SAMPLES, sizeof(double));
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
  elastance_ptr(&pressure, a);

  fixvolume_ptr(&pressure, &reflex_vector, a);     // fix the total blood volume

  eqns_ptr(&pressure, a, &reflex_vector, 0, 0, 0); // Initialize pressure derivatives

  makeImp(&imp, a);                    // Set up the impulse resp. arrays

  numerics(&pressure, &reflex_vector, &out, a);

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

// This function is needed to update pressure.grav[] from the Java code.
void updateGravityVector(int i, double value) {
  pressure.grav[i] = value;
}

// step_sim(): Advances the simulation one timestep
// By varying the dataCompressionFactor the data can be compressed before 
// being passed to the GUI. A dataCompressionFactor of 10 means one piece 
// of output data is passed to the GUI for every 10 timesteps. A 
// dataCompressionFactor of 1 means every piece of data is passed to the GUI.
// (One piece of data for every timestep.)

void step_sim(output *stepout, Parameter_vector *a, int dataCompressionFactor, 
              int ABReflexOn, int CPReflexOn, 
              int tiltTestOn, double tiltStartTime, double tiltStopTime)
{  
  double x0[dataCompressionFactor];
  double x1[dataCompressionFactor];
  double x2[dataCompressionFactor];
  double x3[dataCompressionFactor];  
  double x4[dataCompressionFactor];
  double x5[dataCompressionFactor];
  double x6[dataCompressionFactor];
  double x7[dataCompressionFactor];
  double x8[dataCompressionFactor];
  double x9[dataCompressionFactor];  
  double x10[dataCompressionFactor];
  double x11[dataCompressionFactor];
  double x12[dataCompressionFactor];
  double x13[dataCompressionFactor];
  double x14[dataCompressionFactor];
  double x15[dataCompressionFactor];  
  double x16[dataCompressionFactor];
  double x17[dataCompressionFactor];
  double x18[dataCompressionFactor];
  double x19[dataCompressionFactor];
  double x20[dataCompressionFactor];

  double q0[dataCompressionFactor];
  double q1[dataCompressionFactor];
  double q2[dataCompressionFactor];
  double q3[dataCompressionFactor];  
  double q4[dataCompressionFactor];
  double q5[dataCompressionFactor];
  double q6[dataCompressionFactor];
  double q7[dataCompressionFactor];
  double q8[dataCompressionFactor];
  double q9[dataCompressionFactor];  
  double q10[dataCompressionFactor];
  double q11[dataCompressionFactor];
  double q12[dataCompressionFactor];
  double q13[dataCompressionFactor];
  double q14[dataCompressionFactor];
  double q15[dataCompressionFactor];  
  double q16[dataCompressionFactor];
  double q17[dataCompressionFactor];
  double q18[dataCompressionFactor];
  double q19[dataCompressionFactor];
  double q20[dataCompressionFactor];

  double v0[dataCompressionFactor];
  double v1[dataCompressionFactor];
  double v2[dataCompressionFactor];
  double v3[dataCompressionFactor];  
  double v4[dataCompressionFactor];
  double v5[dataCompressionFactor];
  double v6[dataCompressionFactor];
  double v7[dataCompressionFactor];
  double v8[dataCompressionFactor];
  double v9[dataCompressionFactor];  
  double v10[dataCompressionFactor];
  double v11[dataCompressionFactor];
  double v12[dataCompressionFactor];
  double v13[dataCompressionFactor];
  double v14[dataCompressionFactor];
  double v15[dataCompressionFactor];  
  double v16[dataCompressionFactor];
  double v17[dataCompressionFactor];
  double v18[dataCompressionFactor];
  double v19[dataCompressionFactor];
  double v20[dataCompressionFactor];
 
  double hr[dataCompressionFactor];
  double ar[dataCompressionFactor];
  double vt[dataCompressionFactor];
  double rvc[dataCompressionFactor];
  double lvc[dataCompressionFactor];

  double tbv[dataCompressionFactor];
  double pth[dataCompressionFactor];

  double tiltAngle[dataCompressionFactor];
  
  // Calculate output values
  int i;
  for ( i=0; i < dataCompressionFactor; i++ ) 
  {

    rkqc_ptr(&pressure, &reflex_vector, a, htry, 0.001, yscale, &hdid, &hnext, 
             tiltTestOn, tiltStartTime, tiltStopTime);
    
    pressure.time[1] = sanode(&pressure, &reflex_vector, a, hdid);
    elastance_ptr(&pressure, a);
    eqns_ptr(&pressure, a, &reflex_vector, tiltTestOn, tiltStartTime, tiltStopTime);
    pressure.time[0] += hdid;
    
    //htry = (hnext > 0.001 ? 0.001 : hnext);
    htry = 0.001;
    queue_ptr(&pressure, &imp, &reflex_vector, a, hdid, ABReflexOn, CPReflexOn);
    fixvolume_ptr(&pressure, &reflex_vector, a);

    numerics_new_ptr(&pressure, &reflex_vector, hdid, N_OUT, result);
    numerics(&pressure, &reflex_vector, &out, a);

    // simulation time
    stepout->time = pressure.time[0];
    
    x0[i]  = pressure.x[0];
    x1[i]  = pressure.x[1];
    x2[i]  = pressure.x[2];
    x3[i]  = pressure.x[3];
    x4[i]  = pressure.x[4];
    x5[i]  = pressure.x[5];
    x6[i]  = pressure.x[6];
    x7[i]  = pressure.x[7];
    x8[i]  = pressure.x[8];
    x9[i]  = pressure.x[9];
    x10[i] = pressure.x[10];
    x11[i] = pressure.x[11];
    x12[i] = pressure.x[12];
    x13[i] = pressure.x[13];
    x14[i] = pressure.x[14];
    x15[i] = pressure.x[15];
    x16[i] = pressure.x[16];
    x17[i] = pressure.x[17];
    x18[i] = pressure.x[18];
    x19[i] = pressure.x[19];
    x20[i] = pressure.x[20];
    
    q0[i]  = pressure.q[0];
    q1[i]  = pressure.q[1];
    q2[i]  = pressure.q[2];
    q3[i]  = pressure.q[3];
    q4[i]  = pressure.q[4];
    q5[i]  = pressure.q[5];
    q6[i]  = pressure.q[6];
    q7[i]  = pressure.q[7];
    q8[i]  = pressure.q[8];
    q9[i]  = pressure.q[9];
    q10[i] = pressure.q[10];
    q11[i] = pressure.q[11];
    q12[i] = pressure.q[12];
    q13[i] = pressure.q[13];
    q14[i] = pressure.q[14];
    q15[i] = pressure.q[15];
    q16[i] = pressure.q[16];
    q17[i] = pressure.q[17];
    q18[i] = pressure.q[18];
    q19[i] = pressure.q[19];
    q20[i] = pressure.q[20];  

    v0[i]  = pressure.v[0];
    v1[i]  = pressure.v[1];
    v2[i]  = pressure.v[2];
    v3[i]  = pressure.v[3];
    v4[i]  = pressure.v[4];
    v5[i]  = pressure.v[5];
    v6[i]  = pressure.v[6];
    v7[i]  = pressure.v[7];
    v8[i]  = pressure.v[8];
    v9[i]  = pressure.v[9];
    v10[i] = pressure.v[10];
    v11[i] = pressure.v[11];
    v12[i] = pressure.v[12];
    v13[i] = pressure.v[13];
    v14[i] = pressure.v[14];
    v15[i] = pressure.v[15];
    v16[i] = pressure.v[16];
    v17[i] = pressure.v[17];
    v18[i] = pressure.v[18];
    v19[i] = pressure.v[19];
    v20[i] = pressure.v[20];
    
    hr[i]  = reflex_vector.hr[2];
    ar[i]  = reflex_vector.resistance[0];
    vt[i]  = reflex_vector.volume[0];
    rvc[i] = reflex_vector.compliance[0];
    lvc[i] = reflex_vector.compliance[1];
    
    tbv[i] = a->vec[70];
    pth[i] = a->vec[31];

    tiltAngle[i] = pressure.tilt_angle;

  } // end for
    
  
  // Compress the output by applying turning algorithm
  stepout->x0  = turning(x0, dataCompressionFactor);
  stepout->x1  = turning(x1, dataCompressionFactor);
  stepout->x2  = turning(x2, dataCompressionFactor);
  stepout->x3  = turning(x3, dataCompressionFactor);
  stepout->x4  = turning(x4, dataCompressionFactor);
  stepout->x5  = turning(x5, dataCompressionFactor);
  stepout->x6  = turning(x6, dataCompressionFactor);
  stepout->x7  = turning(x7, dataCompressionFactor);
  stepout->x8  = turning(x8, dataCompressionFactor);
  stepout->x9  = turning(x9, dataCompressionFactor);
  stepout->x10 = turning(x10, dataCompressionFactor);
  stepout->x11 = turning(x11, dataCompressionFactor);
  stepout->x12 = turning(x12, dataCompressionFactor);
  stepout->x13 = turning(x13, dataCompressionFactor);
  stepout->x14 = turning(x14, dataCompressionFactor);
  stepout->x15 = turning(x15, dataCompressionFactor);
  stepout->x16 = turning(x16, dataCompressionFactor);
  stepout->x17 = turning(x17, dataCompressionFactor);
  stepout->x18 = turning(x18, dataCompressionFactor);
  stepout->x19 = turning(x19, dataCompressionFactor);
  stepout->x20 = turning(x20, dataCompressionFactor);
  
  stepout->q0  = turning(q0, dataCompressionFactor);
  stepout->q1  = turning(q1, dataCompressionFactor);
  stepout->q2  = turning(q2, dataCompressionFactor);
  stepout->q3  = turning(q3, dataCompressionFactor);
  stepout->q4  = turning(q4, dataCompressionFactor);
  stepout->q5  = turning(q5, dataCompressionFactor);
  stepout->q6  = turning(q6, dataCompressionFactor);
  stepout->q7  = turning(q7, dataCompressionFactor);
  stepout->q8  = turning(q8, dataCompressionFactor);
  stepout->q9  = turning(q9, dataCompressionFactor);
  stepout->q10 = turning(q10, dataCompressionFactor);
  stepout->q11 = turning(q11, dataCompressionFactor);
  stepout->q12 = turning(q12, dataCompressionFactor);
  stepout->q13 = turning(q13, dataCompressionFactor);
  stepout->q14 = turning(q14, dataCompressionFactor);
  stepout->q15 = turning(q15, dataCompressionFactor);
  stepout->q16 = turning(q16, dataCompressionFactor);
  stepout->q17 = turning(q17, dataCompressionFactor);
  stepout->q18 = turning(q18, dataCompressionFactor);
  stepout->q19 = turning(q19, dataCompressionFactor);
  stepout->q20 = turning(q20, dataCompressionFactor);

  stepout->v0  = turning(v0, dataCompressionFactor);
  stepout->v1  = turning(v1, dataCompressionFactor);
  stepout->v2  = turning(v2, dataCompressionFactor);
  stepout->v3  = turning(v3, dataCompressionFactor);
  stepout->v4  = turning(v4, dataCompressionFactor);
  stepout->v5  = turning(v5, dataCompressionFactor);
  stepout->v6  = turning(v6, dataCompressionFactor);
  stepout->v7  = turning(v7, dataCompressionFactor);
  stepout->v8  = turning(v8, dataCompressionFactor);
  stepout->v9  = turning(v9, dataCompressionFactor);
  stepout->v10 = turning(v10, dataCompressionFactor);
  stepout->v11 = turning(v11, dataCompressionFactor);
  stepout->v12 = turning(v12, dataCompressionFactor);
  stepout->v13 = turning(v13, dataCompressionFactor);
  stepout->v14 = turning(v14, dataCompressionFactor);
  stepout->v15 = turning(v15, dataCompressionFactor);
  stepout->v16 = turning(v16, dataCompressionFactor);
  stepout->v17 = turning(v17, dataCompressionFactor);
  stepout->v18 = turning(v18, dataCompressionFactor);
  stepout->v19 = turning(v19, dataCompressionFactor);
  stepout->v20 = turning(v20, dataCompressionFactor);

  stepout->HR = turning(hr, dataCompressionFactor);
  stepout->AR = turning(ar, dataCompressionFactor);
  stepout->VT = turning(vt, dataCompressionFactor);
  stepout->RVC = turning(rvc, dataCompressionFactor);
  stepout->LVC = turning(lvc, dataCompressionFactor);

  stepout->TBV = turning(tbv, dataCompressionFactor);
  stepout->Pth = turning(pth, dataCompressionFactor);

  stepout->tiltAngle = turning(tiltAngle, dataCompressionFactor);

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

// Total blood volume update equation
// Delta P * C_splanchnicVenous = Delta V
// where Delta P = tan ( Delta V * pi/2*V_max ) * (2*V_Max / pi * C_0 )
void updateTotalBloodVolume(double tbv_new, Parameter_vector *a) {
  // splanchnic venous compliance
  double C0 = a->vec[38]; 
  // old total blood volume 
  double tbv_old = a->vec[70];
  // new total blood volume
  a->vec[70] = tbv_new; 
  // delta v
  double deltaV = tbv_new - tbv_old;
  // Vmax 
  double Vmax = a->vec[71];
  // delta p
  double deltaP = tan( (deltaV*PI)/(2*Vmax) ) * (2*Vmax)/(PI*C0);
  // splanchnic pressure
  double P_old = pressure.x[10];
  // update splanchnic pressure
  pressure.x[10] = P_old + deltaP;
}

// Zero pressure filling volume update equation
// P,new = P,old + (V0,old - V0,new) / C
void updateZeroPressureFillingVolume(double zpfv_new, Parameter_vector *a, int zpfv_index, 
                                     int c_index, int p_index) {
  // compliance
  double C = a->vec[c_index];
  // pressure
  double P_old = pressure.x[p_index];
  // old zero pressure filling volume
  double zpfv_old = a->vec[zpfv_index];
  // new zero pressure filling volume
  a->vec[zpfv_index] = zpfv_new;
  // recalculate pressure
  pressure.x[p_index] = P_old + (zpfv_old - zpfv_new) / C;

  printf("P,new = P,old + (V0,old - V0,new) / C\n");
  printf("P,new: %.2f, P,old: %.2f, V0,old: %.2f, V0,new: %.2f, C: %.2f\n", 
         pressure.x[p_index], P_old, zpfv_old, zpfv_new, C);
}

// Intrathoracic pressure update equation
// P,new = P,old + (Pth,new - Pth,old)
void updateIntrathoracicPressure(double Pth_new, Parameter_vector *a) {
  double P_old;
  printf("updateIntrathoracicPressure()\n");  
  printf("P,new = P,old + (Pth,new - Pth,old)\n");  
  
  // old intra-thoracic pressure
  double Pth_old = a->vec[31];
  // new intra-thoracic pressure
  a->vec[31] = Pth_new;
  pressure.x[24] = Pth_new;
  
  // update pressure for all compartments inside the thorax

  // ascending aorta
  P_old = pressure.x[0];
  pressure.x[0] = P_old + Pth_new - Pth_old;
  printf("Update ascending aortic pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[0], P_old, Pth_new, Pth_old);

  // brachiocephalic
  P_old = pressure.x[1];
  pressure.x[1] = P_old + Pth_new - Pth_old;
  printf("Update brachiocephalic arterial pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[1], P_old, Pth_new, Pth_old);
  
  // superior vena cava
  P_old = pressure.x[4];
  pressure.x[4] = P_old + Pth_new - Pth_old;
  printf("Update superior vena cava pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[4], P_old, Pth_new, Pth_old);

  // thoracic aorta
  P_old = pressure.x[5];
  pressure.x[5] = P_old + Pth_new - Pth_old;
  printf("Update thoracic aortic pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[5], P_old, Pth_new, Pth_old);

  // inferior vena cava
  P_old = pressure.x[14];
  pressure.x[14] = P_old + Pth_new - Pth_old;
  printf("Update inferior vena cava pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[14], P_old, Pth_new, Pth_old);

  // right atrial
  P_old = pressure.x[15];
  pressure.x[15] = P_old + Pth_new - Pth_old;
  printf("Update right atrial pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[15], P_old, Pth_new, Pth_old);

  // right ventricular
  P_old = pressure.x[16];
  pressure.x[16] = P_old + Pth_new - Pth_old;
  printf("Update right ventricular pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[16], P_old, Pth_new, Pth_old);

  // pulmonary arterial
  P_old = pressure.x[17];
  pressure.x[17] = P_old + Pth_new - Pth_old;
  printf("Update pulmonary arterial pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[17], P_old, Pth_new, Pth_old);

  // pulmonary venous
  P_old = pressure.x[18];
  pressure.x[18] = P_old + Pth_new - Pth_old;
  printf("Update pulmonary venous pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[18], P_old, Pth_new, Pth_old);

  // left atrial
  P_old = pressure.x[19];
  pressure.x[19] = P_old + Pth_new - Pth_old;
  printf("Update left atrial pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[19], P_old, Pth_new, Pth_old);

  // left ventricular
  P_old = pressure.x[20];
  pressure.x[20] = P_old + Pth_new - Pth_old;
  printf("Update left ventricular pressure\n");
  printf("P,new: %.2f, P,old: %.2f, Pth,new: %.2f, Pth,old: %.2f\n",
         pressure.x[20], P_old, Pth_new, Pth_old);

}

// Compliance update constraint for compartments inside the thorax
// P,new = P,old * C,old / C,new + Pth * (1 - C,old / C,new)
void updateComplianceInsideThorax(double C_new, Parameter_vector *a, int c_index, int p_index) {
  // old compliance
  double C_old = a->vec[c_index];
  // new compliance
  a->vec[c_index] = C_new;
  // intra-thoracic pressure
  double Pth = a->vec[31];
  // old pressure
  double P_old = pressure.x[p_index];
  // new pressure
  pressure.x[p_index] = P_old * C_old / C_new + Pth * (1 - C_old / C_new);
  printf("updateComplianceInsideThorax()\n");
  printf("P,new = P,old * C,old / C,new + Pth * (1 - C,old / C,new)\n");
  printf("P,new: %.2f, P,old: %.2f, C,old: %.2f, C,new: %.2f, Pth: %.2f\n", 
	 pressure.x[p_index], P_old, C_old, C_new, Pth);
 }

// Compliance update constraint for compartments outside the thorax
// P,new = P,old * C,old / C,new
void updateComplianceOutsideThorax(double C_new, Parameter_vector *a, int c_index, int p_index) {
  // old compliance
  double C_old = a->vec[c_index];
  // new compliance
  a->vec[c_index] = C_new;
  // old pressure
  double P_old = pressure.x[p_index];
  // new pressure
  pressure.x[p_index] = P_old * C_old / C_new;
  printf("updateComplianceOutsideThorax()\n");
  printf("P,new = P,old * C,old / C,new\n");
  printf("P,new: %.2f, P,old: %.2f, C,old: %.2f, C,new: %.2f\n", 
         pressure.x[p_index], P_old, C_old, C_new);
}

// Update function for parameters with no update constraints
void updateParameter(double newValue, Parameter_vector *a, int index) {
  a->vec[index] = newValue;
}
