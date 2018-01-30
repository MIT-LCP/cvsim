/* This is the top-level header file for the entire program. Here we include
 * the standard libraries and define the global structures Parameter_vector
 * and Output_vector which are passed between the simulation and the estimation
 * modules. All other header files in this program include this header file.
 *
 * Thomas Heldt    January 25th, 2002
 * Last modified   March 27th, 2004
 */
#ifndef INCLUDED_MAIN
#define INCLUDED_MAIN


/* Inclusion of C library header files surrounded by redundant include guards 
 * to assert that standard libraries and other header files don't get included
 * more than once.
 */
#ifndef INCLUDED_STDIO
#define INCLUDED_STDIO
#include <stdio.h>
#endif

#ifndef INCLUDED_STDLIB
#define INCLUDED_STDLIB
#include <stdlib.h>
#endif

#ifndef INCLUDED_MATH
#define INCLUDED_MATH
#include <math.h>
#endif

#ifndef INCLUDED_ASSERT
#define INCLUDED_ASSERT
#include <assert.h>
#endif

/* N_PARAMETER is the number of parameters of the model. It determines the 
 * length of the parameter vector defined in the structure Parameter_vector.
 */
#define N_PARAMETER 153

/* The simulation subroutine generates observables (pressures, flows, etc.)
 * over hundreds of seconds at a granularity of the integration stepsize. The 
 * following definitions pertain to the sampling of these high-resolution data
 * streams and the storage of these samples in the output structure (which is
 * defined below).
 * 
 * T_BASELINE is the amount of time (in seconds) of supine baseline included in
 * the output stream prior to initiation of the orthostatic stress. T_TRANSIENT
 * is the amount of time (in seconds) after onset of orthostatic stress
 * included in the output stream. T_SAMP is the sampling period with which the 
 * observables are being sampled. Finally, N_SAMPLES is the  number of samples
 * in the output stream. It is given by (T_BASELINE+T_TRANSIENT)/T_SAMP.
 * Currently, we sample at 2 Hz so we get 261 samples for 130 seconds of data
 * (including the initial point corresponding to time zero).
 */
#define T_BASELINE 60.0
#define T_TRANSIENT 240.0
#define T_SAMP 0.5
#define N_SAMPLES (int) ((T_BASELINE + T_TRANSIENT)/T_SAMP)


/* Definition of the parameter vector as a structure such that it can be
 * passed to and returned from functions.
 */
typedef struct {
  double vec[N_PARAMETER];
} Parameter_vector;


/* Definition of the compartment structure which is used to store the nominal
 * parameter values and their variances for the four physical parameters 
 * describing each compartment: compliance, volume, resistance, and length.
 * Entries v[2][.] contain the volume limits and their variances for the
 * non-linear compartments; otherwise they are set to zero.
 */
typedef struct hemo {
  double c[1][4];
  double v[2][4];
  double r[1][4];
  double h[1][4];
} Hemo[17];

typedef struct cardiac {
  double c_sys[2][4];
  double c_dias[2][4];
  double v[2][4];
  double r[1][4];
} Cardiac[2];

/* Structure for the microvascular resistance structure. Nominal values for 
 * the microvascular resistances are stored in the first entry while their
 * respective variances are stored in the second column;
 */
typedef struct {
  double r[5][4];
} Micro_r;

/* Structure containing the system parameters blood volume, heart rate, and 
 * intra-thoracic pressure.
 */
typedef struct {
  double bv[1][4];
  double hr[1][4];
  double pth[1][4];
  double h[1][4];
  double w[1][4];
  double bsa[1][4];
  double T[3][4];
} System_parameters;

/* Structures containing the reflex parameters. Reflex contains the set-point 
 * values and static gain values. Timing contains the information needed to 
 * set up the impulse response functions.
 */
typedef struct reflex {
  double set[2][4];
  double rr[2][4];
  double res[4][4];
  double vt[4][4];
  double c[2][4];
} Reflex[2];

typedef struct {
  double para[3][4];
  double beta[3][4];
  double alpha_r[3][4];
  double alpha_v[3][4];
  double alpha_cpr[3][4];
  double alpha_cpv[3][4];
} Timing;


/* SIGNALS defines the number of signals of interest in the output stream. 
 * The following is a key on how the value of SIGNALS maps to cardiovascular
 * variables in the output structure. Each variable contains N_SAMPLES of data
 * points to the length of the entire output vector is therefore
 * N_SAMPLES*SIGNALS.
 *
 *                1     HR
 *                2     HR, MAP
 *                3     HR, MAP, SV
 *                4     HR, MAP, SV, CVP
 *                5     HR, MAP, SV, CVP, SAP
 *                6     HR, MAP, SV, CVP, SAP, DAP
 */
#define N_SIGNALS 6


/* N_REP defines the number of (random) initializations of the estimation 
 * routine, i.e. the estimation algorithm attempts to estimate parameters 
 * starting from N_REP different points in parameter space.
 */
#define N_REP 1

/* N_SAMPLES defines the number of samples we want our output matrix to have. 
 * Currently, we sample at 2 Hz so we get 261 samples for 130 seconds of data
 * (including the initial point corresponding to time zero).
 */
//#define N_SAMPLES 460

/* Definition of the output vector structure to be returned as the final output
 * from the simulation routine. It currently contains heart rate, systolic,
 * mean, and diastolic arterial pressures, and stroke volume.
 */
typedef struct {
  double hr[N_SAMPLES];
  double sap[N_SAMPLES];
  double map[N_SAMPLES];
  double dap[N_SAMPLES];
  double cvp[N_SAMPLES];
  double sv[N_SAMPLES];
} Output_vector;



/* Declaration of the parameter initialization module. Reads a pointer to 
 * type Parameter_vector. Writes to the address of its argument the values
 * of the parameters of the hemodynamic and reflex models.
 */
//int initialization(Parameter_vector *p);
void simulator_ptr(Output_vector*, Parameter_vector*, int, int, double *, int, int, 
                   int, double, double);
//Parameter_vector estimation(Parameter_vector, Output_vector, double *, double *, int, int);
//int perturb(int, double, double *, int);
void write_sim_ptr(Output_vector*, int, int);
void dispatch_vec_ptr(Parameter_vector*, int, int, int);
//int load_target(double *, int);
Parameter_vector perturb_theta(int, double, Parameter_vector);


// for now:
//int monte_carlo(int, int, Hemo, Cardiac, Micro_r, System_parameters, Reflex, Timing, int, int);
void initial_ptr(Hemo*, Cardiac*, Micro_r *, System_parameters *, Reflex*, Timing *);
void mapping_ptr (Hemo*, Cardiac*, Micro_r*, System_parameters*, Reflex*, Timing*, Parameter_vector *);
void map_sigma_ptr(Hemo*, Cardiac*, Micro_r*, System_parameters*, Reflex*, Timing*, Parameter_vector *);

#endif


