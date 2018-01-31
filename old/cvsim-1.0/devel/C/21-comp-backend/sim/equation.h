/* Header file for equation.c which contains the subroutines elastance(),
 * eqns(), and fixvolume().
 *
 * Last modified on January 29th, 2002 by Thomas Heldt
 */

#ifndef INCLUDED_EQUATION
#define INCLUDED_EQUATION

/* Inclusion of the header file to simulator.c which in turn includes the 
 * header file to main.c. Needed for the definitions of the structures 
 * Data_vector (in simulator.h) and Parameter_vector (in main.h). Also,
 * main.h contains the inclusion of the standard C library headers stdio.h,
 * stdlib.h, and math.h.
 */
#ifndef INCLUDED_SIMULATOR
#include "simulator.h"
#endif

int tilt(Data_vector *, Parameter_vector *, Tilt_vector *, double, double);
int src(Data_vector *, Parameter_vector *, Tilt_vector *);
int lbnp(Data_vector *, Parameter_vector *, Tilt_vector *);
int valsalva(Data_vector *, Parameter_vector *);
#endif
