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
 * Last modified   November 2, 2002
 */
#include "equation.h"

#define PI M_PI

void elastance_ptr(Data_vector *p, Parameter_vector *theta)
{
  double Ela = 0.0, Era = 0.0, Elv = 0.0, Erv = 0.0;
  double dEla = 0.0, dEra = 0.0, dElv = 0.0, dErv = 0.0;

  double a_time   = p->time[1];
  //double PR_delay = p->time[2];  // unused
  double Tasys    = p->time[3];
  double Tvsys    = p->time[4];

  double Cradias = theta->vec[43];
  double Crasys  = theta->vec[44];
  double Crdias  = theta->vec[45];
  double sigCr = p->c[4];

  double Cladias = theta->vec[49];
  double Clasys  = theta->vec[50];
  double Cldias  = theta->vec[51];
  double sigCl = p->c[5];

  double v_time = p->time[6];

  // Atrial contraction.
  if (a_time <= Tasys) {
    Ela = 0.5*(1/Clasys-1/Cladias)*(1-cos(PI*a_time/Tasys))+1/Cladias;
    Era = 0.5*(1/Crasys-1/Cradias)*(1-cos(PI*a_time/Tasys))+1/Cradias;
    dEla = 0.5*PI*(1/Clasys-1/Cladias)*sin(PI*a_time/Tasys)/Tasys;
    dEra = 0.5*PI*(1/Crasys-1/Cradias)*sin(PI*a_time/Tasys)/Tasys;
  }
  // Early atrial relaxation.
  else if ((Tasys < a_time) && (a_time <= 1.5*Tasys)) {
    Ela = 0.5*(1/Clasys-1/Cladias)*(1+cos(2.0*PI*(a_time-Tasys)/Tasys))+
      1/Cladias;
    Era = 0.5*(1/Crasys-1/Cradias)*(1+cos(2.0*PI*(a_time-Tasys)/Tasys))+
      1/Cradias;
    dEla = -1.0*PI*(1/Clasys-1/Cladias)*sin(2.0*PI*(a_time-Tasys)/Tasys)/Tasys;
    dEra = -1.0*PI*(1/Crasys-1/Cradias)*sin(2.0*PI*(a_time-Tasys)/Tasys)/Tasys;
  }
  // Atrial diastole.
  else if (1.5*Tasys < a_time) {
    Ela = 1/Cladias;
    Era = 1/Cradias;
    dEla = 0.0;
    dEra = 0.0;
  }

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

  p->c[0] = 1/Era;
  p->c[1] = 1/Erv;
  p->c[2] = 1/Ela;
  p->c[3] = 1/Elv;

  p->dcdt[0] = -1.0/(Era*Era)*dEra;
  p->dcdt[1] = -1.0/(Erv*Erv)*dErv;
  p->dcdt[2] = -1.0/(Ela*Ela)*dEla;
  p->dcdt[3] = -1.0/(Elv*Elv)*dElv;

}


/* The following routine computes the flows and and the pressure derivatives.
 * Currently implemented features include the non-linear pressure-volume
 * relation for the splanchnic, the legs, and the abdominal venous
 * compartments. This routine calls the orthostatic stress routines [lbnp() or
 * tilt()] which update the leakage flows and blood volume reductions.
 */
void eqns_ptr(Data_vector *p, Parameter_vector *theta, Reflex_vector *r, 
              int tiltTestOn, double tiltStartTime, double tiltStopTime)
{
  double Csp = 0.0, Cll = 0.0, Cab = 0.0;   // non-linear compliances
  double Vll = 0.0, Vsp = 0.0, Vab = 0.0;
  double con = 0.0;                         // temporary variable
  Tilt_vector t = {{0.0}};

  // call tilt function
  if (tiltTestOn)
    tilt(p, theta, &t, tiltStartTime, tiltStopTime);

  // The following lines compute the non-linear venous compliance values and 
  // the corresponding vascular volumes.
  con = PI*theta->vec[38] / 2.0 / theta->vec[71];
  Csp = theta->vec[38] / (1.0 + con*con*(p->x[10]-p->x[21])*(p->x[10]-p->x[21]));
  Vsp = 2.0*theta->vec[71]*atan(con*(p->x[10]-p->x[21])) / PI;

  if ((p->x[12]-p->x[22]) > 0.0) {
    con = PI*theta->vec[39] / 2.0 / theta->vec[72];
    Cll = theta->vec[39] / (1.0 + con*con*(p->x[12]-p->x[22])*(p->x[12]-p->x[22]));
    Vll = 2.0*theta->vec[72]*atan(con*(p->x[12]-p->x[22])) / PI;
  }
  else {
    con = PI*theta->vec[39]/ 2.0 / theta->vec[72];
    Cll = theta->vec[39] / (1.0 + con*con*(p->x[12]-p->x[22])*(p->x[12]-p->x[22]));
  }

  con = PI*theta->vec[40] / 2.0 / theta->vec[73];
  Cab = theta->vec[40] / (1.0 + con*con*(p->x[13]-p->x[21])*(p->x[13]-p->x[21]));
  Vab = 2.0*theta->vec[73]*atan(con*(p->x[13]-p->x[21])) / PI;

  // Computing the flows in the system based on the pressures at the current
  // time step->
  if (p->x[20] > (p->x[0] + p->grav[0]))
    p->q[0] = (p->x[20] - p->x[0] - p->grav[0]) / theta->vec[69];
  else p->q[0] = 0.0;                        // left ventricular outflow

  p->q[1] = (p->x[0] - p->x[1] - p->grav[1]) / theta->vec[105];
  p->q[2] = (p->x[1] - p->x[2] - p->grav[2]) / theta->vec[106];
  p->q[3] = (p->x[2] - p->x[3]) / r->resistance[0];

  // Starling resistor defines the flow into the superior vena cava.
  if ((p->x[3]+p->grav[3] > p->x[4]) && (p->x[4] > p->x[24]))
    p->q[4] = (p->x[3] - p->x[4] + p->grav[3]) / theta->vec[54];
  else if ((p->x[3]+p->grav[3] > p->x[24]) && (p->x[24] > p->x[4]))
    p->q[4] = (p->x[3] - p->x[24] + p->grav[3]) / theta->vec[54];
  else if (p->x[24] > p->x[3]+p->grav[3])
    p->q[4] = 0.0;

  p->q[5] = (p->x[4] - p->x[15] + p->grav[4]) / theta->vec[63];
  p->q[6] = (p->x[0] - p->x[5] + p->grav[5]) / theta->vec[107];
  p->q[7] = (p->x[5] - p->x[6] + p->grav[6]) / theta->vec[108];
  p->q[8] = (p->x[6] - p->x[7] + p->grav[7]) / theta->vec[109];
  p->q[9] = (p->x[7] - p->x[8]) / r->resistance[1];
  p->q[10] = (p->x[8] - p->x[13] - p->grav[8]) / theta->vec[56];
  p->q[11] = (p->x[6] - p->x[9] + p->grav[9]) / theta->vec[110];
  p->q[12] = (p->x[9] - p->x[10]) / r->resistance[2];
  p->q[13] = (p->x[10] - p->x[13] - p->grav[10]) / theta->vec[58];
  p->q[14] = (p->x[6] - p->x[11] + p->grav[11]) / theta->vec[111];
  p->q[15] = (p->x[11] - p->x[12]) / r->resistance[3];

  if (p->x[12] > (p->x[13] + p->grav[12])) 
    p->q[16] = (p->x[12] - p->x[13] - p->grav[12]) / theta->vec[60];
  else p->q[16] = 0.0;

  p->q[17] = (p->x[13] - p->x[14] - p->grav[13]) / theta->vec[61];
  p->q[18] = (p->x[14] - p->x[15] - p->grav[14]) / theta->vec[62];

  if (p->x[15] > p->x[16]) p->q[19] = (p->x[15] - p->x[16]) / theta->vec[64];
  else p->q[19] = 0.0;

  if (p->x[16] > p->x[17]) p->q[20] = (p->x[16] - p->x[17]) / theta->vec[65];
  else p->q[20] = 0.0;

  p->q[21] = (p->x[17] - p->x[18]) / theta->vec[66];
  p->q[22] = (p->x[18] - p->x[19]) / theta->vec[67];

  if (p->x[19] > p->x[20]) p->q[23] = (p->x[19] - p->x[20]) / theta->vec[68];
  else p->q[23] = 0.0;


  // Computing the pressure derivatives based on the flows and compliance
  // values at the current time step->
  p->dxdt[0]  = (p->q[0] - p->q[1] - p->q[6]) / theta->vec[97] + p->dxdt[24];
  p->dxdt[1]  = (p->q[1] - p->q[2]) / theta->vec[98] + p->dxdt[24];
  p->dxdt[2]  = (p->q[2] - p->q[3]) / theta->vec[100];
  p->dxdt[3]  = (p->q[3] - p->q[4]) / theta->vec[36];
  p->dxdt[4]  = (p->q[4] - p->q[5]) / theta->vec[42] + p->dxdt[24];
  p->dxdt[5]  = (p->q[6] - p->q[7]) / theta->vec[99] + p->dxdt[24];
  p->dxdt[6]  = (p->q[7] - p->q[8] - p->q[11] - p->q[14])/theta->vec[101]+p->dxdt[21];
  p->dxdt[7]  = (p->q[8] - p->q[9]) / theta->vec[102] + p->dxdt[21];
  p->dxdt[8]  = (p->q[9] - p->q[10]) / theta->vec[37] + p->dxdt[21];
  p->dxdt[9]  = (p->q[11] - p->q[12]) / theta->vec[103] + p->dxdt[21];
  p->dxdt[10] = (p->q[12] - p->q[13] - t.flow[0]) / Csp + p->dxdt[21];
  p->dxdt[11] = (p->q[14] - p->q[15]) / theta->vec[104] + p->dxdt[22];
  p->dxdt[12] = (p->q[15] - p->q[16] - t.flow[1]) / Cll + p->dxdt[22];
  p->dxdt[13] = (p->q[10]+p->q[13]+p->q[16]-p->q[17]-t.flow[2])/Cab+p->dxdt[21];
  p->dxdt[14] = (p->q[17] - p->q[18]) / theta->vec[41] + p->dxdt[24];
  p->dxdt[15] = ((p->x[24] - p->x[15]) * p->dcdt[0] + p->q[5] + p->q[18] - p->q[19])
    / p->c[0] + p->dxdt[24];
  p->dxdt[16] = ((p->x[24] - p->x[16]) * p->dcdt[1] + p->q[19] - p->q[20]) 
    / p->c[1] + p->dxdt[24];
  p->dxdt[17] = (p->q[20] - p->q[21]) / theta->vec[47] + p->dxdt[24];
  p->dxdt[18] = (p->q[21] - p->q[22]) / theta->vec[48] + p->dxdt[24];
  p->dxdt[19] = ((p->x[24] - p->x[19]) * p->dcdt[2] + p->q[22] - p->q[23])
    / p->c[2] + p->dxdt[24];
  p->dxdt[20] = ((p->x[24] - p->x[20]) * p->dcdt[3] + p->q[23] - p->q[0])
    / p->c[3] + p->dxdt[24];

  // Computing the compartmental volumes.
  p->v[0]  = (p->x[0] - p->x[24]) * theta->vec[97] + theta->vec[136];
  p->v[1]  = (p->x[1] - p->x[24]) * theta->vec[98] + theta->vec[137];
  p->v[2]  =  p->x[2] * theta->vec[100] + theta->vec[139];
  p->v[3]  =  p->x[3] * theta->vec[36] + r->volume[0];
  p->v[4]  = (p->x[4] - p->x[24]) * theta->vec[42] + theta->vec[83];
  p->v[5]  = (p->x[5] - p->x[24]) * theta->vec[99] + theta->vec[138];
  p->v[6]  = (p->x[6] - p->x[21]) * theta->vec[101] + theta->vec[140];
  p->v[7]  = (p->x[7] - p->x[21]) * theta->vec[102] + theta->vec[141];
  p->v[8]  = (p->x[8] - p->x[21]) * theta->vec[37] + r->volume[1];
  p->v[9]  = (p->x[9] - p->x[21]) * theta->vec[103] + theta->vec[142];
  p->v[10] = Vsp + r->volume[2];
  p->v[11] = (p->x[11] - p->x[22]) * theta->vec[104] + theta->vec[143];
  p->v[12] = Vll + r->volume[3];
  p->v[13] = Vab + theta->vec[81];
  p->v[14] = (p->x[14] - p->x[24]) * theta->vec[41] + theta->vec[82];
  p->v[15] = (p->x[15] - p->x[24]) * p->c[0] + theta->vec[84];
  p->v[16] = (p->x[16] - p->x[24]) * p->c[1] + theta->vec[85];
  p->v[17] = (p->x[17] - p->x[24]) * theta->vec[47] + theta->vec[86];
  p->v[18] = (p->x[18] - p->x[24]) * theta->vec[48] + theta->vec[87];
  p->v[19] = (p->x[19] - p->x[24]) * p->c[2] + theta->vec[88];
  p->v[20] = (p->x[20] - p->x[24]) * p->c[3] + theta->vec[89];

}



/* The following routine ensures blood volume conservation after every
 * integration step. It computes the blood volume stored in each capacitor,
 * the total zero pressure filling volume, and the total blood volume loss
 * during an othostatic stress intervention and compares the sum of these 
 * three to the total blood volume constant in the parameter vector. Any
 * difference between the two will be corrected for at the inferior vena cava.
 */
void fixvolume_ptr(Data_vector *p, Reflex_vector *ref, Parameter_vector *theta)
{
  double diff = 0.0, con = 0.0;           // temporary variables
  double Vsp = 0.0, Vll = 0.0, Vab = 0.0; // non-linear p-v relations
  double Cll = 0.0;
  static int i = 0;

  con = PI*theta->vec[38] / 2.0 / theta->vec[71];
  Vsp = 2.0*theta->vec[71]*atan(con*((*p).x[10]-(*p).x[21])) / PI;

  if (((*p).x[12]-(*p).x[22]) > 0.0) {
    con = PI*theta->vec[39] / 2.0 / theta->vec[72];
    Cll = theta->vec[39] / (1.0 + con*con*((*p).x[12]-(*p).x[22])*((*p).x[12]-(*p).x[22]));
    Vll = 2.0*theta->vec[72]*atan(con*((*p).x[12]-(*p).x[22])) / PI;
  }
  else {
    con = PI*theta->vec[39]/ 2.0 / theta->vec[72];
    Cll = theta->vec[39]/ (1.0 + con*con*((*p).x[12]-(*p).x[22])*((*p).x[12]-(*p).x[22]));
    Vll = 2.0*theta->vec[72]*atan(con*((*p).x[12]-(*p).x[22])) / PI;
  }

  con = PI*theta->vec[40] / 2.0 / theta->vec[73];
  Vab = 2.0*theta->vec[73]*atan(con*((*p).x[13]-(*p).x[21])) / PI;

  diff = theta->vec[70] - theta->vec[81] - theta->vec[82] - theta->vec[83] -
    theta->vec[84] - theta->vec[85] - theta->vec[86] -
    theta->vec[87] - theta->vec[88] - theta->vec[89] - (*ref).volume[0] -
    theta->vec[136] - theta->vec[137] - theta->vec[138] - theta->vec[139] - 
    theta->vec[140] - theta->vec[141] - theta->vec[142] - theta->vec[143] - 
    (*ref).volume[1] - (*ref).volume[2] - (*ref).volume[3] - (*p).tilt[1] -
    (((*p).x[0] - (*p).x[24])*theta->vec[97] + 
     ((*p).x[1] - (*p).x[24])*theta->vec[98] + 
     ((*p).x[14] - (*p).x[24])*theta->vec[41] + 
     ((*p).x[15] - (*p).x[24])*(*p).c[0] +
     ((*p).x[16] - (*p).x[24])*(*p).c[1] +
     ((*p).x[17] - (*p).x[24])*theta->vec[47] +
     ((*p).x[18] - (*p).x[24])*theta->vec[48] + 
     ((*p).x[19] - (*p).x[24])*(*p).c[2] + 
     ((*p).x[20] - (*p).x[24])*(*p).c[3] + 
     ((*p).x[4] - (*p).x[24])*theta->vec[42] +
     ((*p).x[5] - (*p).x[24])*theta->vec[99] + 
     ((*p).x[6] - (*p).x[21])*theta->vec[101] + Vsp + Vll + Vab + 
     ((*p).x[7] - (*p).x[21])*theta->vec[102] + 
     ((*p).x[8] - (*p).x[21])*theta->vec[37] +
     ((*p).x[9] - (*p).x[21])*theta->vec[103] + 
     (*p).x[2]*theta->vec[100] +
     (*p).x[3]*theta->vec[36] + 
     ((*p).x[11] - (*p).x[22])*theta->vec[104]);


  // All volumes
  //  if ((i % 10) == 0)
  //    printf("%e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e\n", (*p).time[0], diff, ((*p).x[0]-(*p).x[24])*theta->vec[97], ((*p).x[1] - (*p).x[24])*theta->vec[98], (*p).x[2]*theta->vec[100], (*p).x[3]*theta->vec[36], ((*p).x[4]-(*p).x[24])*theta->vec[42], ((*p).x[5]-(*p).x[24])*theta->vec[99], (*p).x[6]*theta->vec[101], (*p).x[7]*theta->vec[102], (*p).x[8]*theta->vec[37], (*p).x[9]*theta->vec[103], Vsp, (*p).x[11]*theta->vec[104], Vll, Vab, (*p).x[14]*theta->vec[41], ((*p).x[15] - (*p).x[24])*(*p).c[0], ((*p).x[16] - (*p).x[24])*(*p).c[1], ((*p).x[17] - (*p).x[24])*theta->vec[47], ((*p).x[18] - (*p).x[24])*theta->vec[48], ((*p).x[19] - (*p).x[24])*(*p).c[2], ((*p).x[20] - (*p).x[24])*(*p).c[3], (*p).tilt[1]);  

  // All transmural pressures
  //  if ((i % 10) == 0)
  //    printf("%e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e\n", (*p).time[0], diff, (*p).x[0]-(*p).x[24], (*p).x[1]-(*p).x[24], (*p).x[2], (*p).x[3], (*p).x[4]-(*p).x[24], (*p).x[5]-(*p).x[24], (*p).x[6]-(*p).x[21], (*p).x[7]-(*p).x[21], (*p).x[8]-(*p).x[21], (*p).x[9]-(*p).x[21], (*p).x[10]-(*p).x[21], (*p).x[11]-(*p).x[22], (*p).x[12]-(*p).x[22], (*p).x[13]-(*p).x[21], (*p).x[14]-(*p).x[24], (*p).x[15]-(*p).x[24], (*p).x[16]-(*p).x[24], (*p).x[17]-(*p).x[24], (*p).x[18]-(*p).x[24], (*p).x[19]-(*p).x[24], (*p).x[20]-(*p).x[24]);

  // All flows
  //  printf("%e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e\n", (*p).time[0], diff, (*p).q[0], (*p).q[1], (*p).q[2], (*p).q[3], (*p).q[4], (*p).q[5], (*p).q[6], (*p).q[7], (*p).q[8], (*p).q[9], (*p).q[10], (*p).q[11], (*p).q[12], (*p).q[13], (*p).q[14], (*p).q[15], (*p).q[16], (*p).q[17], (*p).q[18], (*p).q[19], (*p).q[20], (*p).q[21], (*p).q[22], (*p).q[23]);

  // All luminal pressures (wrt atmospheric pressure).
  //  if ((i % 10) == 0)
  //    printf("%e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e\n", (*p).time[0], diff, (*p).x[0], (*p).x[1], (*p).x[2], (*p).x[3], (*p).x[4], (*p).x[5], (*p).x[6], (*p).x[7], (*p).x[8], (*p).x[9], (*p).x[10], (*p).x[11], (*p).x[12], (*p).x[13], (*p).x[14], (*p).x[15], (*p).x[16], (*p).x[17], (*p).x[18], (*p).x[19], (*p).x[20], (*p).x[21], (*p).x[22], (*p).x[23], (*p).x[24], Vll);

  // All gravitational pressure components
  //  printf("%e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e %e\n", (*p).time[0], diff, (*p).grav[0], (*p).grav[1], (*p).grav[2], (*p).grav[3], (*p).grav[4], (*p).grav[5], (*p).grav[6], (*p).grav[7], (*p).grav[8], (*p).grav[9], (*p).grav[10], (*p).grav[11], (*p).grav[12], (*p).grav[13], (*p).grav[14]);


  //  if ((i % 10) == 0)
  //    printf("%e %e %e %e %e %e %e %e %e %e\n", (*p).time[0], diff, (*p).x[0], (*ref).hr[2], (*ref).volume[0]+(*ref).volume[1]+(*ref).volume[2]+(*ref).volume[3], 1./(1./(*ref).resistance[0]+1./(*ref).resistance[1]+1./(*ref).resistance[2]+1./(*ref).resistance[3]), (*p).tilt[0], (*p).x[14]-(*p).x[24], (*p).x[0]-(*p).x[24], (*p).x[2]);
  //    printf("%e %e %e %e %e %e %e %e %e %e %e %e\n", (*p).time[0], diff, (*p).x[0], (*ref).hr[2], (*ref).volume[0]+(*ref).volume[1]+(*ref).volume[2]+(*ref).volume[3], 1./(1./(*ref).resistance[0]+1./(*ref).resistance[1]+1./(*ref).resistance[2]+1./(*ref).resistance[3]), (*p).x[10], (*p).x[12], (*p).x[14], (*p).x[14]-(*p).x[24], (*p).x[15]-(*p).x[24], (*p).c[3]);

  /*
  if (((*p).time[0] > 190.0) && ((*p).time[0] < 440.0))
    if ((i % 100) == 0)
      printf("%e %e %e %e %e %e %e %e\n", (*p).time[0], diff, (*p).x[0], (*ref).hr[2], (*ref).volume[0]+(*ref).volume[1]+(*ref).volume[2]+(*ref).volume[3], 1./(1./(*ref).resistance[0]+1./(*ref).resistance[1]+1./(*ref).resistance[2]+1./(*ref).resistance[3]), (*p).tilt[0], (*p).x[15] - (*p).x[24]);
  */

  //  if ((*p).time[0] > 190.)
  //  printf("%e %e %e %e %e %e %e\n", (*p).time[0], diff, (*p).c[0], (*p).dcdt[0], (*p).c[1], (*p).dcdt[1], (*ref).hr[2]);
  // printf("%e %e\n", (*p).time[0], diff);
  //  printf("\n");

  //  if (fabs(diff) >= 0.01) printf("Volume correction error = %e\n", diff);

  // Correct for any difference in blood volume at the inferior vena cava
  // compartment.
  //  p -> x[12] += diff/Cll;

  //  printf("%e %e %e %e %e\n", (*p).time[0], (*p).time[1], (*p).c[3], (*p).c[2], diff);

  i++;
}

/* Three different orthostatic stresses (short radius centrifugation, tilt, 
 * and lower body negative pressure) were implemented in the following three
 * subroutines. Each subroutine is called from eqns() and modifies selected
 * elements of the Data_vector and the Stress_vector which are supplied to
 * them. 
 */

/* In the following subroutine the tilt simulation is implemented.
 */
int tilt(Data_vector *p, Parameter_vector *theta, Tilt_vector *tilt, 
         double tiltStartTime, double tiltStopTime)
{
  double alpha = 0.0;               // tilt angle converted to rads

  double act = 0.0;                 // activation function for intra-thoracic
                                    // and intra-abdominal pressures.
  double act_dt = 0.0;


  double gravity = 0.0;             // activation function of stress
                                    // onset and offset
  double gravity_dt = 0.0;

  double tilt_time = 0.0;          // time
  double tilt_angle = 0.0;         // instantaneous tilt angle in degrees

  // The following variables are introduced for computation of the blood 
  // volume sequestration into the interstitial fluid compartment.
  double V_loss = 0.0;                       // total blood volume loss as a
                                             // function of time

  double qsp_loss = 0.0, qll_loss = 0.0;     // leakage currents 
  double qab_loss = 0.0, q_loss = 0.0;
  double q_not = theta->vec[74]/theta->vec[92]; // 
  double tau = 276.0;                               // time constant of inter-
                                                    // stitial fluid shifts

  double con = 0.0, con1 = -3.5/0.738, con2 = 0.0;
  double con3 = 0.0;

  // Define certain dummy variables.
  alpha = theta->vec[91] * PI / 180.0;
  q_not *= sin(alpha)/sin(85.0*PI/180.0);
  con1 *= sin(alpha);


  // Tilt from horizontal to head-up position.
  if ( (p->time[5] >= tiltStartTime) && 
       (p->time[5] <= (tiltStartTime+theta->vec[92])) )
    {
      tilt_time = (p->time[5] - tiltStartTime);
      
      // calculate tilt angle in radians
      tilt_angle = alpha*(1.0-cos(PI*tilt_time/theta->vec[92]))/2.0;
      gravity = 0.738*sin(tilt_angle);
      gravity_dt = 0.738*cos(tilt_angle) * alpha/2.0 * sin(PI*tilt_time/theta->vec[92])*PI/theta->vec[92];


      p->x[24] = theta->vec[31] + con1 * gravity;
      p->dxdt[24] = con1 * gravity_dt;

      p->grav[0]  = theta->vec[121]/2.0 * gravity;  // ascending aorta 
      p->grav[1]  = theta->vec[122]/2.0 * gravity;  // brachiocephal.
      p->grav[2]  = theta->vec[123]/2.0 * gravity;  // upper body
      p->grav[3]  = theta->vec[124]/2.0 * gravity;  // upper body veins
      p->grav[4]  = theta->vec[125]/2.0 * gravity;  // SVC
      p->grav[5]  = theta->vec[126]/2.0 * gravity;  // descending aorta
      p->grav[6]  = theta->vec[127]/3.0 * gravity;  // abdominal aorta
      p->grav[7]  = theta->vec[128]/2.0 * gravity;  // renal artieries
      p->grav[8]  = theta->vec[129]/2.0 * gravity;  // renal veins
      p->grav[9]  = theta->vec[130]/2.0 * gravity;  // splanchnic arteries
      p->grav[10] = theta->vec[131]/2.0 * gravity;  // splanchnic veins
      p->grav[11] = theta->vec[132]/3.0  * gravity;  // leg arteries
      p->grav[12] = theta->vec[133]/3.0  * gravity;  // leg veins
      p->grav[13] = theta->vec[134]/3.0 * gravity;  // abdominal IVC
      p->grav[14] = theta->vec[135]/2.0 * gravity;  // thoracic IVC

      q_loss = q_not*(1.0-exp(-tilt_time/tau));
      qsp_loss = 7.0/(63.0) * q_loss;
      qll_loss = 40.0/(63.0) * q_loss;
      qab_loss = 16/(63.0) * q_loss;

      V_loss = q_not * (tilt_time - tau*(1.0-exp(-tilt_time/tau)));
    }

  // Head-up position
  else if ( (p->time[5] > (theta->vec[92]+tiltStartTime)) &&
	   (p->time[5] <= (theta->vec[92]+tiltStartTime +
			     theta->vec[94])))
    {
      tilt_time = (p->time[5]-tiltStartTime-theta->vec[92]);
      tilt_angle = alpha;
      gravity = 0.738*sin(alpha);
      gravity_dt = 0.0;

      p->x[24] = theta->vec[31] + con1 * gravity;
      p->dxdt[24] = con1 * gravity_dt;

      p->grav[0]  = theta->vec[121]/2.0 * gravity;  // ascending aorta
      p->grav[1]  = theta->vec[122]/2.0 * gravity;  // brachiocephal.
      p->grav[2]  = theta->vec[123]/2.0 * gravity;  // upper body
      p->grav[3]  = theta->vec[124]/2.0 * gravity;  // upper body veins
      p->grav[4]  = theta->vec[125]/2.0 * gravity;  // SVC
      p->grav[5]  = theta->vec[126]/2.0 * gravity;  // descending aorta
      p->grav[6]  = theta->vec[127]/3.0 * gravity;  // abdominal aorta
      p->grav[7]  = theta->vec[128]/2.0 * gravity;  // renal artieries
      p->grav[8]  = theta->vec[129]/2.0 * gravity;  // renal veins
      p->grav[9]  = theta->vec[130]/2.0 * gravity;  // splanchnic arteries
      p->grav[10] = theta->vec[131]/2.0 * gravity;  // splanchnic veins
      p->grav[11] = theta->vec[132]/3.0  * gravity;  // leg arteries
      p->grav[12] = theta->vec[133]/3.0  * gravity;  // leg veins
      p->grav[13] = theta->vec[134]/3.0 * gravity;  // abdominal IVC
      p->grav[14] = theta->vec[135]/2.0 * gravity;  // thoracic IVC

      q_loss = q_not*(1.0-exp(-theta->vec[92]/tau))*exp(-tilt_time/tau);
      qsp_loss = 7.0/(63.0) * q_loss;
      qll_loss = 40.0/(63.0) * q_loss;
      qab_loss = 16/(63.0) * q_loss;

      V_loss = q_not*theta->vec[92] *
	(1.0-tau*(1.0-exp(-theta->vec[92]/tau)) *
	 exp(-tilt_time/tau)/theta->vec[92]);
    }

  // Tilt back to the supine position
  else if ((p->time[5] >= tiltStopTime) &&
           (p->time[5] < (tiltStopTime+theta->vec[92])))
    {
      tilt_time = (p->time[5]-tiltStartTime-theta->vec[92]-
		   (tiltStopTime - tiltStartTime));

      tilt_angle = alpha*(1.0-cos(PI*(1-tilt_time/theta->vec[92])))/2.0;
      gravity = 0.738*sin(tilt_angle);
      gravity_dt = 0.738*cos(tilt_angle)*alpha/2.0*sin(PI*(1.0-tilt_time/theta->vec[92]))*PI/theta->vec[92]*(-1.0);

      p->x[24] = theta->vec[31] + con1 * gravity;
      p->dxdt[24] = con1 * gravity_dt;

      p->grav[0]  = theta->vec[121]/2.0 * gravity;  // ascending aorta
      p->grav[1]  = theta->vec[122]/2.0 * gravity;  // brachiocephal.
      p->grav[2]  = theta->vec[123]/2.0 * gravity;  // upper body
      p->grav[3]  = theta->vec[124]/2.0 * gravity;  // upper body veins
      p->grav[4]  = theta->vec[125]/2.0 * gravity;  // SVC
      p->grav[5]  = theta->vec[126]/2.0 * gravity;  // descending aorta
      p->grav[6]  = theta->vec[127]/3.0 * gravity;  // abdominal aorta
      p->grav[7]  = theta->vec[128]/2.0 * gravity;  // renal artieries
      p->grav[8]  = theta->vec[129]/2.0 * gravity;  // renal veins
      p->grav[9]  = theta->vec[130]/2.0 * gravity;  // splanchnic arteries
      p->grav[10] = theta->vec[131]/2.0 * gravity;  // splanchnic veins
      p->grav[11] = theta->vec[132]/3.0 * gravity;  // leg arteries
      p->grav[12] = theta->vec[133]/3.0 * gravity;  // leg veins
      p->grav[13] = theta->vec[134]/3.0 * gravity;  // abdominal IVC
      p->grav[14] = theta->vec[135]/2.0 * gravity;  // thoracic IVC


      q_loss = q_not * (1.0 + (1.0-exp(-theta->vec[92]/tau)) *
		exp(-theta->vec[94]/tau))*exp(-tilt_time/tau) - q_not;
      qsp_loss = 7.0/(63.0) * q_loss;
      qll_loss = 40.0/(63.0) * q_loss;
      qab_loss = 16/(63.0) * q_loss;

      V_loss=(1.0+(1.0-exp(-theta->vec[92]/tau))*exp(-theta->vec[94]/tau))*
	(1.0-exp(-tilt_time/tau)) * q_not * tau -
	q_not*tau*(1.0-exp(-theta->vec[92]/tau))*exp(-theta->vec[94]/tau) +
	q_not*theta->vec[92]*(1.0-tilt_time/theta->vec[92]);
    }

  // Supine position
  else if (p->time[5] > (tiltStopTime+theta->vec[92]))
    {
      tilt_time = (p->time[5]-tiltStartTime-2.0*theta->vec[92]-
		   (tiltStopTime - tiltStartTime));

      gravity = 0.0;
      gravity_dt = 0.0;
      tilt_angle = 0.0;
      p->x[24] = theta->vec[31] + con1 * gravity;
      p->dxdt[24] = con1 * gravity_dt;

      p->grav[0]  = theta->vec[121]/2.0 * gravity;  // ascending aorta
      p->grav[1]  = theta->vec[122]/2.0 * gravity;  // brachiocephal.
      p->grav[2]  = theta->vec[123]/2.0 * gravity;  // upper body
      p->grav[3]  = theta->vec[124]/2.0 * gravity;  // upper body veins
      p->grav[4]  = theta->vec[125]/2.0 * gravity;  // SVC
      p->grav[5]  = theta->vec[126]/2.0 * gravity;  // descending aorta
      p->grav[6]  = theta->vec[127]/3.0 * gravity;  // abdominal aorta
      p->grav[7]  = theta->vec[128]/2.0 * gravity;  // renal artieries
      p->grav[8]  = theta->vec[129]/2.0 * gravity;  // renal veins
      p->grav[9]  = theta->vec[130]/2.0 * gravity;  // splanchnic arteries
      p->grav[10] = theta->vec[131]/2.0 * gravity;  // splanchnic veins
      p->grav[11] = theta->vec[132]/3.0 * gravity;  // leg arteries
      p->grav[12] = theta->vec[133]/3.0 * gravity;  // leg veins
      p->grav[13] = theta->vec[134]/3.0 * gravity;  // abdominal IVC
      p->grav[14] = theta->vec[135]/2.0 * gravity;  // thoracic IVC

      q_loss = -q_not * (1.0-exp(-theta->vec[92]/tau))*exp(-tilt_time/tau) *
	(1.0-exp(-(theta->vec[94]+theta->vec[92])/tau));
      qsp_loss = 7.0/(63.0) * q_loss;
      qll_loss = 40.0/(63.0) * q_loss;
      qab_loss = 16/(63.0) * q_loss;

      V_loss = q_not*tau*(1.0-exp(-theta->vec[92]/tau))*exp(-tilt_time/tau)*
	(1.0-exp(-(theta->vec[94]+theta->vec[92])/tau));
    }

  // Write the computed values for the flows, the volume loss, and the carotid
  // sinus offset pressure to the respective structures so they can be passed
  // across subroutine boundaries.
  tilt -> flow[0] = qsp_loss;
  tilt -> flow[1] = qll_loss;
  tilt -> flow[2] = qab_loss;

  p -> tilt[0] = theta->vec[14] * gravity;
  p -> tilt[1] = V_loss;

/*   int i; */
/*   for (i=0; i < 15; i++)  */
/*     if ( p->grav[i] != 0 ) */
/*       printf("time: %.4f   p.grav[%d]: %.4f\n", p->time[0], i, p->grav[i]); */

  // convert tilt angle to degrees
  p->tilt_angle = tilt_angle * 180.0 / PI;

  return 0;
}

#undef PI
