/* Header file for estimate.c which contains the subroutines to estimate
 * the initial pressures given the parameter vector.
 *
 * Last modified: 01/27/2002 by Thomas Heldt
 */ 
#ifndef INCLUDED_ESTIMATE
#define INCLUDED_ESTIMATE

/* Inclusion of the header file to simulator.c which in turn includes the 
 * header file to main.c. Needed for the definitions of the structures 
 * Data_vector (in simulator.h) and Parameter_vector (in main.h). Also,
 * main.h contains the inclusion of the standard C library headers stdio.h,
 * stdlib.h, and math.h.
 */
#ifndef INCLUDED_SIMULATOR
#include "simulator.h"
#endif

// Moved definition of ARRAY_SIZE from estimate.c to here
// Needed for function definitions below
// Modified by C. Dunn

#define ARRAY_SIZE 23           // Array size for the coefficient matrix a and
                                // lhs vector b. Array size = # of pressure
                                // nodes in the hemodynamic system + 2

// Previously
// int lineqs(double a[][], double b[], int N);
// Arrays of incomplete element type generate an error 
// in GCC versions 4.0 and higher
// Modified by C. Dunn


int lineqs(double (*a)[ARRAY_SIZE][ARRAY_SIZE], double b[ARRAY_SIZE], int N);
double mnewt_ptr(Parameter_vector *theta, double *b);
#endif
