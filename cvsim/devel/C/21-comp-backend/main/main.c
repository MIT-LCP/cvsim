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
 * Last modified  August 4, 2004
 */
#include "main.h"

#define PI M_PI

int main(int argc, char *argv[])
{
  // Monte Carlo simulation parameters
  //int N_MC = 20;            // Number of Monte Carlo realizations 
  //int R_MC = 5;             // Number of LHS replications

  int N_OUT = 21;           // Number of output variables in the steady-state
                            // analysis
  double *result;           // output vector containing the output variables.
  double *target;

  // Simulator related definitions
  Parameter_vector theta = {{0.0}};
  Output_vector out = {{0.0}};

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
  int tiltTestOn = 0;
  double tiltStartTime = 0.0;
  double tiltStopTime = 0.0;

  // Allocate memory for data structures.
  target = calloc(N_SIGNALS*N_SAMPLES, sizeof(double));
  result = calloc(N_OUT, sizeof(double));

  // Initialize the parameter structures and the parameter vector.
  initial_ptr(&hemo, &cardiac, &micro_r, &system, &reflex, &timing);
  mapping_ptr(&hemo, &cardiac, &micro_r, &system, &reflex, &timing, &theta);

  // Baseline simulation. 
  //  for (i=1;i<10;i++) {
  //    theta.vec[95] += 5.0;
  simulator_ptr(&out, &theta, 0, N_OUT, result, ABReflexOn, CPReflexOn, 
		tiltTestOn, tiltStartTime, tiltStopTime);
  //write_sim_ptr(&out, 0, 0);
    //    printf("Done with simulation %d\n", i);
    //  }

  return 0;
}



/*************************************************************************
 *                      Subroutines for main()                           *
 *************************************************************************/
/* write_sim() vectorizes the simulation output and writes it to file on the
 * master node. The length of the datafile depends on how many signals are
 * desired and how many samples are recorded / signal. The output sequence
 * is as follows: HR, MAP, CVP, SV, SAP, DAP.
 *
 *  out       (input) Output_vector
 *            out contains the simulation output returned from the routine
 *            simulator().
 *
 *  my_rank   (input) integer
 *            my_rank indicates the rank ID of the current process, i.e. it is 
 *            a unique ID associated with each process during function
 *            execution.
 *
 *  k         (input) integer
 *            k determines a numeric part of the file name to distinguish 
 *            multiple files written to the master node using this subroutine.
 *
 * Thomas Heldt   October 12, 2003
 * Last modified  October 12, 2003
 */

void write_sim_ptr(Output_vector *out, int my_rank, int k)
{
  int i = 0;
  char buf[256];
  FILE *matrix;

  if (my_rank == 0)
    {
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
    }

}


/* dispatch_vec(), when called, writes the values containes in the parameter
 * structure to a file on the master node. It is usually called at the end of
 * the parameter estimation routine.
 *
 *  vector     (input) Parameter_vector
 *             structure that contains the parameter values.
 *
 *  N          (input) integer
 *             Number of parameter to be written to file. N >= 0.
 *
 *  my_rank    (input) integer
 *             rank of the current process. my_rank >= 0.
 *
 *  k          (input) integer
 *             suffix for the file name; usually synonymous with the number
 *             of current iteration. k >= 0.
 *
 * Thomas Heldt    October 12, 2003
 * Last modified   November 3, 2003
 */
void dispatch_vec_ptr(Parameter_vector *vector, int N, int my_rank, int k)
{
  int i;
  char buf[256];
  FILE *ofp;

  if (my_rank == 0)
    {
      (void) sprintf(buf, "final%d.dat", k);
      ofp = fopen(buf, "w");

      for (i=0; i<N; i++)
	fprintf(ofp, "%d %e\n", i, vector->vec[i]);
  
      fclose(ofp);
    }

} /* End dispatch_vec */


/* The following subroutine perturbs the nominal set of parameter values by 
 * returning for each element of the parameter vector a realization from a 
 * normal distribution centered at the nominal parameter value with relative
 * standard deviation sigma. The subroutine uses a linear congruential random
 * number generator and the Box-Muller method to generate two iid N(0,1)
 * variates for every two iid Unif(0,1) variates.
 *
 *  N      (input) int
 *         The top N parameters of the parameter vector will be perturbed. 
 *         N > 0.
 *
 *  sigma  (input) double
 *         The standard deviation relative to the mean (i.e. relative to the 
 *         nominal value of the parameter). 0 <= sigma <= 1.0
 *
 *  theta  (input/output) pointer to structure Parameter_vector
 *         theta contains the set of nominal parameter values upon function 
 *         entry. Upon function exit, it contains the respective perturbed 
 *         values.
 *
 * Thomas Heldt   April 25 2003
 * Last modified  April 25 2003
 */
Parameter_vector perturb_theta(int N, double sigma, Parameter_vector theta)
{
  int multiplier = 69069, i = 0;
  unsigned seed  = 0;
  unsigned MOD   = 2147483647;
  double tmp1, tmp2;

  FILE *ifp, *ofp;

  /*
  // Read the seed from the seed file.
  ifp = fopen("data/seed", "r");
  fscanf(ifp, "%u", &seed);
  fclose(ifp);
  */

  seed = 489733871;

  MOD *= 2;

  for (i=0; i<N; i=i+2)
    {
      seed = (multiplier * seed) % MOD;
      tmp1 = (double) seed/MOD;

      seed = (multiplier * seed) % MOD;
      tmp2 = (double) seed/MOD;


      theta.vec[i]   *= 1.0+sigma*sqrt(-2.0*log(1.0-tmp1))*cos(2.0*PI*tmp2);
      theta.vec[i+1] *= 1.0+sigma*sqrt(-2.0*log(1.0-tmp1))*sin(2.0*PI*tmp2);
    }


  theta.vec[91] = 75.0;


  // Write the new seed to the seed file
  ifp = fopen("../data/seed.dat", "w");
  fprintf(ifp, "%u", seed);
  fclose(ifp);


  // Write perturbed parameter vector to file for furture reference.
  ofp = fopen("../data/parameters.init", "w");
  for (i=0; i< N_PARAMETER; i++)
    fprintf(ofp, "theta[%d] = %e\n", i, theta.vec[i]);
  fclose(ofp);

  return theta;

} /* End perturb_theta() */


/*************************************************************************
 *                     End subroutines for main()                        *
 *************************************************************************/
#undef PI
