/* Header file for the file simulator.c which is the top-level file for the 
 * simulation module.
 * 
 * Thomas Heldt   March 2nd, 2002
 * Last modified  April 11th, 2002
 */
#ifndef INCLUDED_SIMULATOR
#define INCLUDED_SIMULATOR


/* Inclusion of the header file to main.c. Main.h contains the inclusion of
 * the standard C library headers stdio.h, stdlib.h, and math.h. Furthermore,
 * the structures of the parameter vector and the output vector are defined in
 * main.h.
 */
#ifndef INCLUDED_MAIN
#include "../main/main.h"
#endif


/* N_X is the number of relevant pressures in the cardiovascular system. It
 * consists of the 14 pressure nodes of the hemodynamic model plus the three
 * external bias pressures. N_X determines the size of the pressure vector x
 * and the vector of the pressure derivatives dxdt. N_C is the number of
 * contracting cardiac chambers in the model. It determines the size of the
 * arrays c and dcdt which contain the values of the time-varying compliances
 * and their derivatives, respectively. N_T determines the size of the time
 * vector. It currently consists of 6 entries. Absolute time, cardiac time
 * (i.e. time elapsed since the onset of the last atrial contraction), are the
 * first two entries. The next three entries are PR_delay, Tasys, and Tvsys. 
 * The last entry is "absolute modified time" which is used in the tilt() and 
 * lbnp() routines (see those routines for the definition of this time
 * variable). The tilt array stores the blood volume loss in the 0 component
 * and the carotid sinus offset pressure in the 1 component.
 */
#define N_X 25
#define N_Q 24
#define N_C 7
#define N_TIME 7
#define N_TILT 2
#define N_GRAV 15

/* Definition of the data structre. It contains the pressure vector, x[],
 * the vector of pressure derivatives, dxdt[], the time-varying compliance 
 * vector, c[], the vector of the compliance derivatives, dcdt[], and finally
 * the time vector, time[].
 */
typedef struct {
  double x[N_X];
  double dxdt[N_X];
  double q[N_Q];
  double v[N_X];
  double c[N_C];
  double dcdt[N_C];
  double time[N_TIME];
  double time_new[N_TIME];
  double tilt[N_TILT];
  double grav[N_GRAV];
} Data_vector;



/* N_COMP defines the number of cardiac chambers affected by the contractility
 * feedback loop; N_RES and N_VOL are the number of compartments affected by
 * the resistance and venous tone feedback loops, respectively. N_HR has three
 * entries: the first one is for storage of the afferent (reflex) instantaneous
 * heart rate signal, the second stores the cummulative heart rate signal 
 * in a beat (intra-cardiac cycle time), and the third one stores the actual
 * beat-by-beat heart rate value.
 */
#define N_HR 4
#define N_COMP 2
#define N_RES 4
#define N_VOL 4


/* Definition of the reflex data structure. This structure contains the values
 * of tscending aorta compartmenhe parameters affected by the reflex response,
 * namely heart rate, right and left ventricular end-systolic compliances,
 * arteriolar resistances, and four systemic zero pressure filling volumes.
 */
typedef struct {
  double hr[N_HR];
  double compliance[N_COMP];
  double resistance[N_RES];
  double volume[N_VOL];
  int step_cnt;
} Reflex_vector;


/* N_SLENGTH defines the number of entries in the vectors containing the
 * sympathetic and parasympathetic impulse response functions.
 */
#define N_SLENGTH 960


/* Definition of the reflex impulse response function structure. 
 */
typedef struct {
  double p[N_SLENGTH];
  double s[N_SLENGTH];
  double a[N_SLENGTH];
  double v[N_SLENGTH];
  double cpa[N_SLENGTH];
  double cpv[N_SLENGTH];
} Impulse_vector;


/* N_FLOWS defines the number of leakage flows in the tilt routine. It is
 * currently set to three for the splanchnic, leg, and abdominal compartments.
 */
#define N_FLOWS 3

/* Definition of the tilt structure. It contains the leakage flow variables
 * that need to be passed from the tilt() or lbnp() routines to the eqns()
 * routine.
 */
typedef struct {
  double flow[N_FLOWS];
} Tilt_vector;



/* Function definitions for the subroutines used in the simulator.c file.
 */
void estimate_ptr(Data_vector*, Parameter_vector*, Reflex_vector *);
void elastance_ptr(Data_vector*, Reflex_vector*, Parameter_vector*);
void eqns_ptr(Data_vector*, Parameter_vector*, Reflex_vector*);
int fixvolume_ptr(Data_vector *, Reflex_vector *, Parameter_vector*);
double sanode(Data_vector *, Reflex_vector *, Parameter_vector *, double);
int rkqc_ptr(Data_vector *, Reflex_vector*, Parameter_vector*, double,
		 double, double *, double *, double *);
void queue_ptr(Data_vector*, Impulse_vector *, Reflex_vector *, Parameter_vector *, double, int, int);
int queue_reset(void);
void makeImp(Impulse_vector *, Parameter_vector *);
int numerics(Data_vector *, Reflex_vector *, Output_vector *, Parameter_vector *);
void rk4_ptr(Data_vector*, Reflex_vector *, Parameter_vector*, double);
int numerics_new_ptr(Data_vector*, Reflex_vector*, double, int, double *);

#endif








