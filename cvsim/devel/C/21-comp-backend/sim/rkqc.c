/* This file contains the two routines needed to advance the solution of the 
 * differential equations by one step. We are currently using a fourth-order
 * Runge-Kutta adaptive stepsize integration routine adapted from the Numerical
 * Recipes in C.
 * 
 * Thomas Heldt   January 31st, 2002
 * Last modified  July 13th, 2002
 */
#include "rkqc.h"

#define PGROW -0.20
#define PSHRNK -0.25
#define SAFETY 0.9
#define ERRCON 6.0e-4

/* rk4() is a fourth-order Runge Kutta integrator routine that is based on a 
 * similar code in the Numerical Recipies for C. When the function is called, 
 * on fourth-order step is taken and the values of the dependent variables are 
 * returned upon function exit.
 *
 * On entry:
 *  
 *  q      Data_vector
 *         q is the data structure that contains the dependent variables as
 *         the q.x array, the time index in the q.time array, and the
 *         derivative information in the q.dxdt array.
 *
 *  r      Reflex_vector
 *         r contains the information about the state of the reflex system. In
 *         particular, it contains the information about the cummulative heart
 *         rate and therefore the onset time for the next cardiac beat. 
 *
 *  c      Parameter_vector
 *         c contains the values of the parameters which are needed when
 *         calling the subroutines eqns() and elastance().
 *
 *  h      double
 *         h is the size of the step to be taken by the integrator.
 *
 *
 * On return:
 *
 *  q      the values of the dependent variables in the q.x array have been
 *         replaced by the values at the time t_0+h where t_0 was the
 *         simulation time before the rk4 routine was called.
 *
 *  r      the cummulative heart rate entry and the step count entry of the
 *         reflex vector are updated upon function exit.
 *
 * Thomas Heldt    January 20, 2002
 * Last modified   July 13th, 2002
 */
void rk4_ptr(Data_vector *q, Reflex_vector *r, Parameter_vector *c,  double h, 
             int tiltTestOn, double tiltStartTime, double tiltStopTime)
{
  Reflex_vector t = (*r), s = (*r);       // temporary reflex vectors
  Data_vector q_local = (*q),  p_local = (*q);  // temporary data vectors


  double hh = h*0.5, h6 = h/6.0;          // see NRC for explanation of fourth-
                                          // order RK integration algorithm

  //  printf("Entering rk4\n");
  //  printf("%e %e %e %e %e\n", q.time[0], q_local.time[1], p_local.time[1], q_local.c[1], p_local.c[1]);

  // Take first step of size hh to the mid-point of the interval.
  q_local.x[0]  += hh*q->dxdt[0];
  q_local.x[1]  += hh*q->dxdt[1];
  q_local.x[2]  += hh*q->dxdt[2];
  q_local.x[3]  += hh*q->dxdt[3];
  q_local.x[4]  += hh*q->dxdt[4];
  q_local.x[5]  += hh*q->dxdt[5];
  q_local.x[6]  += hh*q->dxdt[6];
  q_local.x[7]  += hh*q->dxdt[7];
  q_local.x[8]  += hh*q->dxdt[8];
  q_local.x[9]  += hh*q->dxdt[9];
  q_local.x[10] += hh*q->dxdt[10];
  q_local.x[11] += hh*q->dxdt[11];
  q_local.x[12] += hh*q->dxdt[12];
  q_local.x[13] += hh*q->dxdt[13];
  q_local.x[14] += hh*q->dxdt[14];
  q_local.x[15] += hh*q->dxdt[15];
  q_local.x[16] += hh*q->dxdt[16];
  q_local.x[17] += hh*q->dxdt[17];
  q_local.x[18] += hh*q->dxdt[18];
  q_local.x[19] += hh*q->dxdt[19];
  q_local.x[20] += hh*q->dxdt[20];

  // Check whether a new beat should be initiated given that we have propagated
  // the pressure vector over the time interval hh.
  q_local.time[1] = sanode(&q_local, &s, c, hh);

  // Update the time-varying elastance values and their derivatives.
  elastance_ptr(&q_local, c);

  // Update the derivatives for the current copy of the pressure vector.
  eqns_ptr(&q_local, c, &s, tiltTestOn, tiltStartTime, tiltStopTime);

  //  printf("%e %e %e %e %e\n", q->time[0], q_local.time[1], p_local.time[1], q_local.c[1], p_local.c[1]);

  // Take second step (also of size hh) to the mid-point of the interval.
  q_local.x[0]  = q->x[0]  + hh*q_local.dxdt[0];
  q_local.x[1]  = q->x[1]  + hh*q_local.dxdt[1];
  q_local.x[2]  = q->x[2]  + hh*q_local.dxdt[2];
  q_local.x[3]  = q->x[3]  + hh*q_local.dxdt[3];
  q_local.x[4]  = q->x[4]  + hh*q_local.dxdt[4];
  q_local.x[5]  = q->x[5]  + hh*q_local.dxdt[5];
  q_local.x[6]  = q->x[6]  + hh*q_local.dxdt[6];
  q_local.x[7]  = q->x[7]  + hh*q_local.dxdt[7];
  q_local.x[8]  = q->x[8]  + hh*q_local.dxdt[8];
  q_local.x[9]  = q->x[9]  + hh*q_local.dxdt[9];
  q_local.x[10] = q->x[10] + hh*q_local.dxdt[10];
  q_local.x[11] = q->x[11] + hh*q_local.dxdt[11];
  q_local.x[12] = q->x[12] + hh*q_local.dxdt[12];
  q_local.x[13] = q->x[13] + hh*q_local.dxdt[13];
  q_local.x[14] = q->x[14] + hh*q_local.dxdt[14];
  q_local.x[15] = q->x[15] + hh*q_local.dxdt[15];
  q_local.x[16] = q->x[16] + hh*q_local.dxdt[16];
  q_local.x[17] = q->x[17] + hh*q_local.dxdt[17];
  q_local.x[18] = q->x[18] + hh*q_local.dxdt[18];
  q_local.x[19] = q->x[19] + hh*q_local.dxdt[19];
  q_local.x[20] = q->x[20] + hh*q_local.dxdt[20];


  // Compute the derivatives.
  p_local = q_local;
  eqns_ptr(&p_local, c, &s, tiltTestOn, tiltStartTime, tiltStopTime);
  p_local.time[1] = q->time[1];
  p_local.time[6] = q->time[6];
  p_local.time_new[0] = q->time_new[0];
  p_local.time_new[5] = q->time_new[5];

  //  printf("%e %e %e %e %e\n", q->time[0], q_local.time[1], p_local.time[1], q_local.c[1], p_local.c[1]);

  // Take step of size h to the end of the interval.
  p_local.x[0]  = q->x[0]  + h*p_local.dxdt[0];
  p_local.x[1]  = q->x[1]  + h*p_local.dxdt[1];
  p_local.x[2]  = q->x[2]  + h*p_local.dxdt[2];
  p_local.x[3]  = q->x[3]  + h*p_local.dxdt[3];
  p_local.x[4]  = q->x[4]  + h*p_local.dxdt[4];
  p_local.x[5]  = q->x[5]  + h*p_local.dxdt[5];
  p_local.x[6]  = q->x[6]  + h*p_local.dxdt[6];
  p_local.x[7]  = q->x[7]  + h*p_local.dxdt[7];
  p_local.x[8]  = q->x[8]  + h*p_local.dxdt[8];
  p_local.x[9]  = q->x[9]  + h*p_local.dxdt[9];
  p_local.x[10] = q->x[10] + h*p_local.dxdt[10];
  p_local.x[11] = q->x[11] + h*p_local.dxdt[11];
  p_local.x[12] = q->x[12] + h*p_local.dxdt[12];
  p_local.x[13] = q->x[13] + h*p_local.dxdt[13];
  p_local.x[14] = q->x[14] + h*p_local.dxdt[14];
  p_local.x[15] = q->x[15] + h*p_local.dxdt[15];
  p_local.x[16] = q->x[16] + h*p_local.dxdt[16];
  p_local.x[17] = q->x[17] + h*p_local.dxdt[17];
  p_local.x[18] = q->x[18] + h*p_local.dxdt[18];
  p_local.x[19] = q->x[19] + h*p_local.dxdt[19];
  p_local.x[20] = q->x[20] + h*p_local.dxdt[20];

  q_local.dxdt[0]  += p_local.dxdt[0];
  q_local.dxdt[1]  += p_local.dxdt[1];
  q_local.dxdt[2]  += p_local.dxdt[2];
  q_local.dxdt[3]  += p_local.dxdt[3];
  q_local.dxdt[4]  += p_local.dxdt[4];
  q_local.dxdt[5]  += p_local.dxdt[5];
  q_local.dxdt[6]  += p_local.dxdt[6];
  q_local.dxdt[7]  += p_local.dxdt[7];
  q_local.dxdt[8]  += p_local.dxdt[8];
  q_local.dxdt[9]  += p_local.dxdt[9];
  q_local.dxdt[10] += p_local.dxdt[10];
  q_local.dxdt[11] += p_local.dxdt[11];
  q_local.dxdt[12] += p_local.dxdt[12];
  q_local.dxdt[13] += p_local.dxdt[13];
  q_local.dxdt[14] += p_local.dxdt[14];
  q_local.dxdt[15] += p_local.dxdt[15];
  q_local.dxdt[16] += p_local.dxdt[16];
  q_local.dxdt[17] += p_local.dxdt[17];
  q_local.dxdt[18] += p_local.dxdt[18];
  q_local.dxdt[19] += p_local.dxdt[19];
  q_local.dxdt[20] += p_local.dxdt[20];


  p_local.time[1] = sanode(&p_local, &t, c, h);
  elastance_ptr(&p_local, c);
  eqns_ptr(&p_local, c, &t, tiltTestOn, tiltStartTime, tiltStopTime);

  //  printf("%e %e %e %e %e\n", q->time[0], q_local.time[1], p_local.time[1], q_local.c[1], p_local.c[1]);


  p_local.x[0]  = q->x[0]  + h6*(q->dxdt[0]+p_local.dxdt[0]+2.0*q_local.dxdt[0]);
  p_local.x[1]  = q->x[1]  + h6*(q->dxdt[1]+p_local.dxdt[1]+2.0*q_local.dxdt[1]);
  p_local.x[2]  = q->x[2]  + h6*(q->dxdt[2]+p_local.dxdt[2]+2.0*q_local.dxdt[2]);
  p_local.x[3]  = q->x[3]  + h6*(q->dxdt[3]+p_local.dxdt[3]+2.0*q_local.dxdt[3]);
  p_local.x[4]  = q->x[4]  + h6*(q->dxdt[4]+p_local.dxdt[4]+2.0*q_local.dxdt[4]);
  p_local.x[5]  = q->x[5]  + h6*(q->dxdt[5]+p_local.dxdt[5]+2.0*q_local.dxdt[5]);
  p_local.x[6]  = q->x[6]  + h6*(q->dxdt[6]+p_local.dxdt[6]+2.0*q_local.dxdt[6]);
  p_local.x[7]  = q->x[7]  + h6*(q->dxdt[7]+p_local.dxdt[7]+2.0*q_local.dxdt[7]);
  p_local.x[8]  = q->x[8]  + h6*(q->dxdt[8]+p_local.dxdt[8]+2.0*q_local.dxdt[8]);
  p_local.x[9]  = q->x[9]  + h6*(q->dxdt[9]+p_local.dxdt[9]+2.0*q_local.dxdt[9]);
  p_local.x[10] = q->x[10] + h6*(q->dxdt[10]+p_local.dxdt[10]+2.0*q_local.dxdt[10]);
  p_local.x[11] = q->x[11] + h6*(q->dxdt[11]+p_local.dxdt[11]+2.0*q_local.dxdt[11]);
  p_local.x[12] = q->x[12] + h6*(q->dxdt[12]+p_local.dxdt[12]+2.0*q_local.dxdt[12]);
  p_local.x[13] = q->x[13] + h6*(q->dxdt[13]+p_local.dxdt[13]+2.0*q_local.dxdt[13]);
  p_local.x[14] = q->x[14] + h6*(q->dxdt[14]+p_local.dxdt[14]+2.0*q_local.dxdt[14]);
  p_local.x[15] = q->x[15] + h6*(q->dxdt[15]+p_local.dxdt[15]+2.0*q_local.dxdt[15]);
  p_local.x[16] = q->x[16] + h6*(q->dxdt[16]+p_local.dxdt[16]+2.0*q_local.dxdt[16]);
  p_local.x[17] = q->x[17] + h6*(q->dxdt[17]+p_local.dxdt[17]+2.0*q_local.dxdt[17]);
  p_local.x[18] = q->x[18] + h6*(q->dxdt[18]+p_local.dxdt[18]+2.0*q_local.dxdt[18]);
  p_local.x[19] = q->x[19] + h6*(q->dxdt[19]+p_local.dxdt[19]+2.0*q_local.dxdt[19]);
  p_local.x[20] = q->x[20] + h6*(q->dxdt[20]+p_local.dxdt[20]+2.0*q_local.dxdt[20]);


  eqns_ptr(&p_local, c, &t, tiltTestOn, tiltStartTime, tiltStopTime);
  p_local.time[0] += h;

  //  printf("%e %e %e %e %e\n\n", q->time[0], q_local.time[1], p_local.time[1], q_local.c[1], p_local.c[1]);

  r -> hr[1] = t.hr[1];
  r -> step_cnt = t.step_cnt;

  *q = p_local;
}


void rkqc_ptr(Data_vector *pres, Reflex_vector *r, Parameter_vector *theta,
              double htry, double eps, double yscal[], double *hdid,
              double *hnext, int tiltTestOn, double tiltStartTime, double tiltStopTime)
{
  Data_vector p   = {{0.0}};
  Data_vector q   = {{0.0}};
  Reflex_vector s = {{0.0}};
  Reflex_vector t = {{0.0}};

  double xsav = 0.0;          // dummy variable to save the initial time
  double hh = 0.0, h = 0.0;   // variables for step-sizes
  double temp = 0.0;          // dummy variable (see below) 
  double errmax = 0.0;        // dummy variable for maximal difference between
                              // single step and double step results

  xsav = (*pres).time[1];
  h = htry;

  // Updated dxdt vector needed because of time-varying volume. 
  // Does not affect model performace when bv is constant.
  eqns_ptr(pres, theta, r, tiltTestOn, tiltStartTime, tiltStopTime); 
                                                       

  for (;;) {
    p = *pres; q = *pres;
    s = *r; t = *r;
    hh=0.5*h;

    //    printf("Taking first step of size: %f\n", hh);
    // Take first step of size h/2.
    rk4_ptr(&p, &s, theta, hh, tiltTestOn, tiltStartTime, tiltStopTime);    
    //    printf("Taking second step of size: %f\n", hh);
    // Take second setp of size hh.
    rk4_ptr(&p, &s, theta, hh, tiltTestOn, tiltStartTime, tiltStopTime);    

    if (p.time[1] == xsav)
      break;

    //    printf("Taking step of size: %f\n", h);
    // Take one full step of size h
    rk4_ptr(&q, &t, theta, h, tiltTestOn, tiltStartTime, tiltStopTime);     
    errmax=0.0;

    q.x[0] -= p.x[0];
    if (errmax < (temp=fabs(q.x[0]/yscal[0]))) errmax=temp;
    q.x[1] -= p.x[1];
    if (errmax < (temp=fabs(q.x[1]/yscal[1]))) errmax=temp;
    q.x[2] -= p.x[2];
    if (errmax < (temp=fabs(q.x[2]/yscal[2]))) errmax=temp;
    q.x[3] -= p.x[3];
    if (errmax < (temp=fabs(q.x[3]/yscal[3]))) errmax=temp;
    q.x[4] -= p.x[4];
    if (errmax < (temp=fabs(q.x[4]/yscal[4]))) errmax=temp;
    q.x[5] -= p.x[5];
    if (errmax < (temp=fabs(q.x[5]/yscal[5]))) errmax=temp;
    q.x[6] -= p.x[6];
    if (errmax < (temp=fabs(q.x[6]/yscal[6]))) errmax=temp;
    q.x[7] -= p.x[7];
    if (errmax < (temp=fabs(q.x[7]/yscal[7]))) errmax=temp;
    q.x[8] -= p.x[8];
    if (errmax < (temp=fabs(q.x[8]/yscal[8]))) errmax=temp;
    q.x[9] -= p.x[9];
    if (errmax < (temp=fabs(q.x[9]/yscal[9]))) errmax=temp;
    q.x[10] -= p.x[10];
    if (errmax < (temp=fabs(q.x[10]/yscal[10]))) errmax=temp;
    q.x[11] -= p.x[11];
    if (errmax < (temp=fabs(q.x[11]/yscal[11]))) errmax=temp;
    q.x[12] -= p.x[12];
    if (errmax < (temp=fabs(q.x[12]/yscal[12]))) errmax=temp;
    q.x[13] -= p.x[13];
    if (errmax < (temp=fabs(q.x[13]/yscal[13]))) errmax=temp;
    q.x[14] -= p.x[14];
    if (errmax < (temp=fabs(q.x[14]/yscal[14]))) errmax=temp;
    q.x[15] -= p.x[15];
    if (errmax < (temp=fabs(q.x[15]/yscal[15]))) errmax=temp;
    q.x[16] -= p.x[16];
    if (errmax < (temp=fabs(q.x[16]/yscal[16]))) errmax=temp;
    q.x[17] -= p.x[17];
    if (errmax < (temp=fabs(q.x[17]/yscal[17]))) errmax=temp;
    q.x[18] -= p.x[18];
    if (errmax < (temp=fabs(q.x[18]/yscal[18]))) errmax=temp;
    q.x[19] -= p.x[19];
    if (errmax < (temp=fabs(q.x[19]/yscal[19]))) errmax=temp;
    q.x[20] -= p.x[20];
    if (errmax < (temp=fabs(q.x[20]/yscal[20]))) errmax=temp;

    if ((errmax /= eps) <= 1.0) {
      *hdid=h;
      *hnext=(errmax > ERRCON ? SAFETY*h*pow(errmax, PGROW) : 4.0*h);
      break;
    }
    h=SAFETY*h*exp(PSHRNK*log(errmax));
    //    printf("Reducing stepsize to: %f\n\n", h);
  }

  pres -> x[0]  = p.x[0]  += q.x[0]/15.0;
  pres -> x[1]  = p.x[1]  += q.x[1]/15.0;
  pres -> x[2]  = p.x[2]  += q.x[2]/15.0;
  pres -> x[3]  = p.x[3]  += q.x[3]/15.0;
  pres -> x[4]  = p.x[4]  += q.x[4]/15.0;
  pres -> x[5]  = p.x[5]  += q.x[5]/15.0;
  pres -> x[6]  = p.x[6]  += q.x[6]/15.0;
  pres -> x[7]  = p.x[7]  += q.x[7]/15.0;
  pres -> x[8]  = p.x[8]  += q.x[8]/15.0;
  pres -> x[9]  = p.x[9]  += q.x[9]/15.0;
  pres -> x[10] = p.x[10] += q.x[10]/15.0;
  pres -> x[11] = p.x[11] += q.x[11]/15.0;
  pres -> x[12] = p.x[12] += q.x[12]/15.0;
  pres -> x[13] = p.x[13] += q.x[13]/15.0;
  pres -> x[14] = p.x[14] += q.x[14]/15.0;
  pres -> x[15] = p.x[15] += q.x[15]/15.0;
  pres -> x[16] = p.x[16] += q.x[16]/15.0;
  pres -> x[17] = p.x[17] += q.x[17]/15.0;
  pres -> x[18] = p.x[18] += q.x[18]/15.0;
  pres -> x[19] = p.x[19] += q.x[19]/15.0;
  pres -> x[20] = p.x[20] += q.x[20]/15.0;

}
#undef PGROW
#undef PSHRNK
#undef SAFETY
#undef ERRCON

