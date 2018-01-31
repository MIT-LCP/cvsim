/* Header file for rk.c which contains the subroutines for the Runge-Kutta 
 * integration routine. We are currently using a 4th order adaptive step-size
 * algorithm, rkqc(), that calls the 4th order Runge-Kutta integrator, rk4(),
 * every time a step is taken.
 *
 * Last modified on January 29th, 2002 by Thomas Heldt
 */

#ifndef INCLUDED_RK
#define INCLUDED_RK

/* Inclusion of the header file to simulator.c which in turn includes the 
 * header file to main.c. Needed for the definitions of the structures 
 * Data_vector (in simulator.h) and Parameter_vector (in main.h). Also,
 * main.h contains the inclusion of the standard C library headers stdio.h,
 * stdlib.h, and math.h.
 */
#ifndef INCLUDED_SIMULATOR
#include "simulator.h"
#endif

Data_vector rk4(Data_vector q, Reflex_vector *r, Parameter_vector c, double h);


#endif
