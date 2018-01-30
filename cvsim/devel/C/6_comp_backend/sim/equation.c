/* This file contains the subroutines necessary to evaluate the time-varying
 * capacitance values, their derivatives, the flows in the hemodynamic 
 * system, and the pressure derivatives. Furthermore, it contains the volume 
 * corrention routine.
 *
 * ALTHOUGH ELASTANCE() AND EQNS() ONLY REQUIRE A SMALL SUBSET OF ALL 
 * PARAMETERS, CURRENTLY THE ENTIRE PARAMETER VECTOR IS PASSED TO THESE 
 * ROUTINES AS ARGUMENTS. THIS CERTAINLY COMPROMISES PERFORMANCE AND 
 * SHOULD BE RECONSIDERED IN FUTURE ITERATIONS OF THE PROGRAM.
 *
 *
 * Thomas Heldt    March 30th, 2002
 * Last modified   October 19, 2005
 */
#include "equation.h"

#define PI M_PI

// Modifies ONLY Data_vector
void elastance_ptr(Data_vector *p, Reflex_vector *r, Parameter_vector *theta)
{
  double Elv  = 0.0, Erv  = 0.0;
  double dElv = 0.0, dErv = 0.0;

  // ventricular timing variables
  double Tvsys   = p->time[4];
  double v_time  = p->time[6];

  // right and left ventricular diastolic and systolic compliances.
  double Crdias  = theta->vec[45];
  double sigCr   = p->c[4];

  double Cldias  = theta->vec[51];
  double sigCl   = p->c[5];



  // Ventricular contraction. PR-interval has not yet passed.
  if (v_time <= 0.0) {
    Elv = 1/Cldias;
    Erv = 1/Crdias;
    dElv = 0.0;
    dErv = 0.0;
  }
  // Ventricular contraction.
  else if ((0 < v_time) && (v_time <= Tvsys)) {
    Elv = 0.5*(1/sigCl-1/Cldias)*(1-cos(PI*v_time/Tvsys))+1/Cldias;
    Erv = 0.5*(1/sigCr-1/Crdias)*(1-cos(PI*v_time/Tvsys))+1/Crdias;
    dElv = 0.5*PI*(1/sigCl-1/Cldias)*sin(PI*v_time/Tvsys)/Tvsys;
    dErv = 0.5*PI*(1/sigCr-1/Crdias)*sin(PI*v_time/Tvsys)/Tvsys;
  }
  // Early ventricular relaxation.
  else if ((Tvsys < v_time) && (v_time <= 1.5*Tvsys)) {
    Elv = 0.5*(1/sigCl-1/Cldias)*(1+cos(2.0*PI*(v_time-Tvsys)/Tvsys)) + 
      1/Cldias;
    Erv = 0.5*(1/sigCr-1/Crdias)*(1+cos(2.0*PI*(v_time-Tvsys)/Tvsys)) + 
      1/Crdias;
    dElv = -1.0*PI*(1/sigCl-1/Cldias)*sin(2.0*PI*(v_time-Tvsys)/Tvsys)/Tvsys;
    dErv = -1.0*PI*(1/sigCr-1/Crdias)*sin(2.0*PI*(v_time-Tvsys)/Tvsys)/Tvsys;
  }
  // Ventricular diastole.
  else if (v_time > 1.5*Tvsys) {
    Elv = 1/Cldias;
    Erv = 1/Crdias;
    dElv = 0.0;
    dErv = 0.0;
  }

  p->c[1] = 1/Erv;
  p->c[3] = 1/Elv;

  p->dcdt[1] = -1.0/(Erv*Erv)*dErv;
  p->dcdt[3] = -1.0/(Elv*Elv)*dElv;
}


/* The following routine computes the flows and the pressure derivatives;
 * it is called from the Runge-Kutta integration routine.
 */
// Modifies ONLY Data_vector
void eqns_ptr(Data_vector *p, Parameter_vector *theta, Reflex_vector *r)
{

  // Computing the flows in the system based on the pressures at the current
  // time step.
  // Left ventricular outflow
  if (p->x[0] > p->x[1]) p->q[0] = (p->x[0] - p->x[1]) / theta->vec[155];
  else p->q[0] = 0.0;                  

  // Systemic bloodflow 
  p->q[1] = (p->x[1] - p->x[2]) / r->resistance[0];

  // Right ventricular inflow
  if (p->x[2] > p->x[3]) p->q[2] = (p->x[2] - p->x[3]) / theta->vec[157];
  else p->q[2] = 0.0;

  // Right ventricular outflow
  if (p->x[3] > p->x[4]) p->q[3] = (p->x[3] - p->x[4]) / theta->vec[65];
  else p->q[3] = 0.0;

  // Pulmonary bloodflow
  p->q[4] = (p->x[4] - p->x[5]) / theta->vec[66];

  // Left ventricular inflow
  if (p->x[5] > p->x[0]) p->q[5] = (p->x[5] - p->x[0]) / theta->vec[67];
  else p->q[5] = 0.0;


  // Computing the pressure derivatives based on the flows and compliance
  // values at the current time step->
  p->dxdt[0] = ((p->x[24] - p->x[0]) * p->dcdt[3] + p->q[5] - p->q[0])
    / p->c[3] + p->dxdt[24];
  p->dxdt[1]  = (p->q[0] - p->q[1]) / theta->vec[153];
  p->dxdt[2]  = (p->q[1] - p->q[2]) / theta->vec[154];
  p->dxdt[3] = ((p->x[24] - p->x[3]) * p->dcdt[1] + p->q[2] - p->q[3])
    / p->c[1] + p->dxdt[24];
  p->dxdt[4]  = (p->q[3] - p->q[4]) / theta->vec[47] + p->dxdt[24];
  p->dxdt[5]  = (p->q[4] - p->q[5]) / theta->vec[48] + p->dxdt[24];


  // Computing the compartmental volumes.
  p->v[0] = (p->x[0] - p->x[24]) * p->c[3] + theta->vec[162];
  p->v[1] = p->x[1] * theta->vec[153] + theta->vec[163];
  p->v[2] = p->x[2] * theta->vec[154] + theta->vec[164];
  p->v[3] = (p->x[3] - p->x[24]) * p->c[1] + theta->vec[165];
  p->v[4] = (p->x[4] - p->x[24]) * theta->vec[47] + theta->vec[166];
  p->v[5] = (p->x[5] - p->x[24]) * theta->vec[48] + theta->vec[167];
}

/* The following routine ensures blood volume conservation after every
 * integration step. It computes the blood volume stored in each capacitor,
 * the total zero pressure filling volume, and the total blood volume loss
 * during an othostatic stress intervention and compares the sum of these 
 * three to the total blood volume constant in the parameter vector. Any
 * difference between the two will be corrected for at the inferior vena cava.
 */
// THIS FUNCTION DOESN'T EVEN DO ANYTHING!!
int fixvolume_ptr(Data_vector *p, Reflex_vector *ref, Parameter_vector *theta)
{

  // This function doesn't do anthing, so just return 0 right away!
  return 0;

  double diff = 0.0;

  diff = (((*p).x[0]-(*p).x[24])*(*p).c[3] + ((*p).x[3]-(*p).x[24])*(*p).c[1] +
	  ((*p).x[4]-(*p).x[24])*theta->vec[47] + 
	  ((*p).x[5]-(*p).x[24])*theta->vec[48] + 
	  (*p).x[1]*theta->vec[153] + (*p).x[2]*theta->vec[154]) +
    theta->vec[75] - theta->vec[70];

  // Correct for any difference in blood volume at the inferior vena cava
  // compartment.
  //  p -> x[12] -= diff/theta.vec[154];

  //  printf("%e %e %e %e %e\n", (*p).time[0], (*p).time[1], (*p).c[3], (*p).c[1], diff);

  return 0;
}

#undef PI
