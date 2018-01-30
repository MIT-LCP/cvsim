/* This file contains the top-level main routine for the entire program. 
 * The simulation and the estimation modules are called from here. Also, global
 * program-wide I/O is managed from here.
 *
 * The following subroutines reside in this file:
 *
 *   int write_sim(Output_vector, int, int)
 *   Parameter_vector perturb(int, double, Parameter_vector)
 *   int load_target(double *, int)
 *   dispatch_vec(Parameter_vector, int, int, int)
 *
 * Thomas Heldt   January 25, 2002
 * Last modified  October 17, 2005
 */
#include "main.h"

int main(int argc, char *argv[])
{
  int N_OUT = 21;           // Number of output variables in the steady-state
                            // analysis

  double *result;           // output vector containing the output variables.

  // Simulator-related definitions
  Parameter_vector theta = {{0.0}};
  Output_vector out = {{0.0}};

  // Declare parameter value structures.
  Hemo hemo;
  Cardiac cardiac;
  Reflex reflex;
  Micro_r micro_r;
  System_parameters system;
  Timing timing;

  // Switches to turn the cardiopulmonary reflex and the arterial baroreflex 
  // on and off
  int ABReflexOn = 0;
  int CPReflexOn = 0;

  // Allocate memory for data structures.
  result = calloc(N_OUT, sizeof(double));

  // Initialize the parameter structures and the parameter vector.
  initial_ptr(&hemo, &cardiac, &micro_r, &system, &reflex, &timing);
  mapping_ptr(&hemo, &cardiac, &micro_r, &system, &reflex, &timing, &theta);


  // Baseline simulation. 
  simulator_ptr(&out, &theta, 0, N_OUT, result, ABReflexOn, CPReflexOn);
  write_sim_ptr(&out, 0);

  return 0;
}


/*****************************************************************************
 *                         Subroutines for main()                            *
 *****************************************************************************/
/* write_sim() vectorizes the simulation output and writes it to file. The
 * length of the datafile depends on how many signals are desired and how many
 * samples are recorded per signal. The output sequence is as follows: HR, MAP,
 * CVP, SV, SAP, DAP.
 *
 *  out       (input) Output_vector
 *            out contains the simulation output returned from the routine
 *            simulator().
 *
 *  k         (input) integer
 *            k determines a numeric part of the file name to distinguish 
 *            multiple files written to the master node using this subroutine.
 *
 * Thomas Heldt   October 12, 2003
 * Last modified  July 1, 2003
 */

int write_sim_ptr(Output_vector *out, int k)
{
  int i = 0;
  char buf[256];
  FILE *matrix;

  (void) sprintf(buf, "sim_%d.dat", k);
  matrix = fopen(buf, "w");

  if (N_SIGNALS > 0)
    for (i=0; i < N_SAMPLES; i++)
      fprintf(matrix, "%e\n", out->hr[i]);
  if (N_SIGNALS > 1)
    for (i=0; i < N_SAMPLES; i++)
      fprintf(matrix, "%e\n", out->map[i]);
  if (N_SIGNALS > 2)
    for (i=0; i < N_SAMPLES; i++)
      fprintf(matrix, "%e\n", out->sv[i]);
  if (N_SIGNALS > 3)
    for (i=0; i < N_SAMPLES; i++)
      fprintf(matrix, "%e\n", out->cvp[i]);
  if (N_SIGNALS > 4)
    for (i=0; i < N_SAMPLES; i++)
      fprintf(matrix, "%e\n", out->sap[i]);
  if (N_SIGNALS > 5)
    for (i=0; i < N_SAMPLES; i++)
      fprintf(matrix, "%e\n", out->dap[i]);

  fclose(matrix);

  return 0;
} /* end write_sim() */

/*****************************************************************************
 *                      End of subroutines for main()                        *
 *****************************************************************************/

