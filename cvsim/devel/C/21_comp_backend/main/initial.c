/* New initialization routine */
#include "initial.h"

void initial_ptr(Hemo *hemo, Cardiac *cardiac, Micro_r *micro_r, System_parameters *system, Reflex *reflex, Timing *timing)
{
  float alpha = 1.;
  float beta = 1.;

  // Ascending aorta compartment
  (*hemo)[0].c[0][0] = 0.28;  (*hemo)[0].c[0][1] = 0.04;
  (*hemo)[0].c[0][2] = 0.16;   (*hemo)[0].c[0][3] = 0.40;

  (*hemo)[0].v[0][0] = 21.0;  (*hemo)[0].v[0][1] = 3.0;
  (*hemo)[0].v[0][2] = 10.0;  (*hemo)[0].v[0][3] = 32.0;

  (*hemo)[0].r[0][0] = 0.007; (*hemo)[0].r[0][1] = 0.002;
  (*hemo)[0].r[0][2] = 0.0;   (*hemo)[0].r[0][3] = 0.013;

  (*hemo)[0].h[0][0] = 10.0;  (*hemo)[0].h[0][1] = 0.5;
  (*hemo)[0].h[0][2] = 8.0;   (*hemo)[0].h[0][3] = 12.0;


  // Brachiocephalic arterial compartment
  (*hemo)[1].c[0][0] = 0.13;  (*hemo)[1].c[0][1] = 0.02;
  (*hemo)[1].c[0][2] = 0.07;  (*hemo)[1].c[0][3] = 0.20;

  (*hemo)[1].v[0][0] = 5.0;   (*hemo)[1].v[0][1] = 1.0;
  (*hemo)[1].v[0][2] = 2.0;   (*hemo)[1].v[0][3] = 8.0;

  (*hemo)[1].r[0][0] = 0.003; (*hemo)[1].r[0][1] = 0.001;
  (*hemo)[1].r[0][2] = 0.0;   (*hemo)[1].r[0][3] = 0.006;

  (*hemo)[1].h[0][0] = 4.5;   (*hemo)[1].h[0][1] = 0.5;
  (*hemo)[1].h[0][2] = 3.0;   (*hemo)[1].h[0][3] = 6.0;


  // Upper body arterial compartment
  (*hemo)[2].c[0][0] = 0.2;  (*hemo)[2].c[0][1] = 0.1;
  (*hemo)[2].c[0][2] = 0.1;   (*hemo)[2].c[0][3] = 0.7;

  (*hemo)[2].v[0][0] = 200.0; (*hemo)[2].v[0][1] = 40.0;
  (*hemo)[2].v[0][2] = 80.0;  (*hemo)[2].v[0][3] = 320.0;

  (*hemo)[2].r[0][0] = 0.014; (*hemo)[2].r[0][1] = 0.004;
  (*hemo)[2].r[0][2] = 0.002; (*hemo)[2].r[0][3] = 0.026;

  (*hemo)[2].h[0][0] = 20.0;  (*hemo)[2].h[0][1] = 1.0;
  (*hemo)[2].h[0][2] = 17.0;  (*hemo)[2].h[0][3] = 23.0;


  // Descending aorta compartment
  (*hemo)[3].c[0][0] = 0.1;  (*hemo)[3].c[0][1] = 0.03;
  (*hemo)[3].c[0][2] = 0.05;  (*hemo)[3].c[0][3] = 0.30;

  (*hemo)[3].v[0][0] = 16.0;  (*hemo)[3].v[0][1] = 2.0;
  (*hemo)[3].v[0][2] = 10.0;  (*hemo)[3].v[0][3] = 32.0;

  (*hemo)[3].r[0][0] = 0.011; (*hemo)[3].r[0][1] = 0.002;
  (*hemo)[3].r[0][2] = 0.005; (*hemo)[3].r[0][3] = 0.017;

  (*hemo)[3].h[0][0] = 16.0;  (*hemo)[3].h[0][1] = 0.8;
  (*hemo)[3].h[0][2] = 13.4;  (*hemo)[3].h[0][3] = 18.6;


  // Abdominal aorta compartment
  (*hemo)[4].c[0][0] = 0.10;  (*hemo)[4].c[0][1] = 0.01;
  (*hemo)[4].c[0][2] = 0.07;  (*hemo)[4].c[0][3] = 0.13;

  (*hemo)[4].v[0][0] = 10.0;  (*hemo)[4].v[0][1] = 1.0;
  (*hemo)[4].v[0][2] =  7.0;  (*hemo)[4].v[0][3] = 13.0;

  (*hemo)[4].r[0][0] = 0.01;  (*hemo)[4].r[0][1] = 0.003;
  (*hemo)[4].r[0][2] =  0.0;  (*hemo)[4].r[0][3] = 0.02;

  (*hemo)[4].h[0][0] = 14.5;  (*hemo)[4].h[0][1] = 0.5;
  (*hemo)[4].h[0][2] = 13.0;  (*hemo)[4].h[0][3] = 16.0;


  // Renal arterial compartment
  (*hemo)[5].c[0][0] = 0.21;  (*hemo)[5].c[0][1] = 0.05;
  (*hemo)[5].c[0][2] = 0.10;  (*hemo)[5].c[0][3] = 0.30;

  (*hemo)[5].v[0][0] = 20.0;  (*hemo)[5].v[0][1] = 5.0;
  (*hemo)[5].v[0][2] = 5.0;   (*hemo)[5].v[0][3] = 35.0;

  (*hemo)[5].r[0][0] = 0.10;  (*hemo)[5].r[0][1] = 0.05;
  (*hemo)[5].r[0][2] = 0.0;   (*hemo)[5].r[0][3] = 0.25;

  (*hemo)[5].h[0][0] =  0.0;  (*hemo)[5].h[0][1] = 0.0;
  (*hemo)[5].h[0][2] =  0.0;  (*hemo)[5].h[0][3] = 0.0;


  // Splanchnic arterial compartment
  (*hemo)[6].c[0][0] = 0.2;  (*hemo)[6].c[0][1] = 0.10;
  (*hemo)[6].c[0][2] = 0.10;  (*hemo)[6].c[0][3] = 0.70;

  (*hemo)[6].v[0][0] = 300.0; (*hemo)[6].v[0][1] =  50.0;
  (*hemo)[6].v[0][2] = 150.0; (*hemo)[6].v[0][3] = 450.0;

  (*hemo)[6].r[0][0] = 0.07;  (*hemo)[6].r[0][1] = 0.04;
  (*hemo)[6].r[0][2] =  0.0;  (*hemo)[6].r[0][3] = 0.19;

  (*hemo)[6].h[0][0] =  5.0;  (*hemo)[6].h[0][1] =  0.5;
  (*hemo)[6].h[0][2] =  8.5;  (*hemo)[6].h[0][3] = 11.5;


  // Leg arterial compartment
  (*hemo)[7].c[0][0] = 0.2;  (*hemo)[7].c[0][1] = 0.10;
  (*hemo)[7].c[0][2] = 0.1;   (*hemo)[7].c[0][3] = 0.70;

  (*hemo)[7].v[0][0] = 200.0; (*hemo)[7].v[0][1] =  20.0;
  (*hemo)[7].v[0][2] = 140.0; (*hemo)[7].v[0][3] = 260.0;

  (*hemo)[7].r[0][0] = 0.09;  (*hemo)[7].r[0][1] = 0.05;
  (*hemo)[7].r[0][2] = 0.0;   (*hemo)[7].r[0][3] = 0.24;

  (*hemo)[7].h[0][0] = 106.0; (*hemo)[7].h[0][1] =   6.0;
  (*hemo)[7].h[0][2] =  88.0; (*hemo)[7].h[0][3] = 124.0;


  // Pulmonary arterial compartment
  (*hemo)[8].c[0][0] = 3.4;   (*hemo)[8].c[0][1] = 1.8;
  (*hemo)[8].c[0][2] = 1.5;   (*hemo)[8].c[0][3] = 7.2;

  (*hemo)[8].v[0][0] = 160.0; (*hemo)[8].v[0][1] =  20.0;
  (*hemo)[8].v[0][2] = 100.0; (*hemo)[8].v[0][3] = 220.0;

  (*hemo)[8].r[0][0] = 0.006; (*hemo)[8].r[0][1] = 0.003;
  (*hemo)[8].r[0][2] = 0.0;   (*hemo)[8].r[0][3] = 0.015;

  (*hemo)[8].h[0][0] = 0.0;   (*hemo)[8].h[0][1] = 0.0;
  (*hemo)[8].h[0][2] = 0.0;   (*hemo)[8].h[0][3] = 0.0;



  // Upper body venous compartment
  (*hemo)[9].c[0][0] = 7.0;   (*hemo)[9].c[0][1] =  2.0;
  (*hemo)[9].c[0][2] = 1.0;   (*hemo)[9].c[0][3] = 13.0;

  (*hemo)[9].v[0][0] = 645.0; (*hemo)[9].v[0][1] =  40.0;
  (*hemo)[9].v[0][2] = 425.0; (*hemo)[9].v[0][3] = 765.0;

  (*hemo)[9].r[0][0] = 0.11;  (*hemo)[9].r[0][1] = 0.05;
  (*hemo)[9].r[0][2] = 0.0;   (*hemo)[9].r[0][3] = 0.26;

  (*hemo)[9].h[0][0] = 20.0;  (*hemo)[9].h[0][1] =  1.0;
  (*hemo)[9].h[0][2] = 18.5;  (*hemo)[9].h[0][3] = 21.5;


  // Superior vena cava compartment
  (*hemo)[10].c[0][0] = 1.3;   (*hemo)[10].c[0][1] = 0.1;
  (*hemo)[10].c[0][2] = 1.0;   (*hemo)[10].c[0][3] = 1.6;

  (*hemo)[10].v[0][0] = 16.0;  (*hemo)[10].v[0][1] =  4.0;
  (*hemo)[10].v[0][2] =  4.0;  (*hemo)[10].v[0][3] = 28.0;

  (*hemo)[10].r[0][0] = 0.028; (*hemo)[10].r[0][1] = 0.014;
  (*hemo)[10].r[0][2] = 0.0;   (*hemo)[10].r[0][3] = 0.056;

  (*hemo)[10].h[0][0] = 14.5;   (*hemo)[10].h[0][1] = 0.5;
  (*hemo)[10].h[0][2] = 3.0;   (*hemo)[10].h[0][3] = 6.0;


  // Renal venous compartment
  (*hemo)[11].c[0][0] = 5.0;   (*hemo)[11].c[0][1] = 1.0;
  (*hemo)[11].c[0][2] = 2.0;   (*hemo)[11].c[0][3] = 8.0;

  (*hemo)[11].v[0][0] = 30.0;  (*hemo)[11].v[0][1] = 10.0;
  (*hemo)[11].v[0][2] = 10.0;  (*hemo)[11].v[0][3] = 60.0;

  (*hemo)[11].r[0][0] = 0.11;  (*hemo)[11].r[0][1] = 0.05;
  (*hemo)[11].r[0][2] = 0.0;   (*hemo)[11].r[0][3] = 0.26;

  (*hemo)[11].h[0][0] = 0.0;   (*hemo)[11].h[0][1] = 0.0;
  (*hemo)[11].h[0][2] = 0.0;   (*hemo)[11].h[0][3] = 0.0;


  // Splanchnic venous compartment
  (*hemo)[12].c[0][0] = 60.0;   (*hemo)[12].c[0][1] =  7.5;
  (*hemo)[12].c[0][2] = 27.5;   (*hemo)[12].c[0][3] = 72.5;

  (*hemo)[12].v[0][0] = 1146.0; (*hemo)[12].v[0][1] =  100.0;
  (*hemo)[12].v[0][2] =  850.0; (*hemo)[12].v[0][3] = 1450.0;

  (*hemo)[12].r[0][0] = 0.07;   (*hemo)[12].r[0][1] = 0.04;
  (*hemo)[12].r[0][2] = 0.0;    (*hemo)[12].r[0][3] = 0.19;

  (*hemo)[12].h[0][0] =  5.0;    (*hemo)[12].h[0][1] =  0.5;
  (*hemo)[12].h[0][2] =  8.5;    (*hemo)[12].h[0][3] = 11.5;


  // Leg venous compartment
  (*hemo)[13].c[0][0] = 20.0;  (*hemo)[13].c[0][1] = 3.0;
  (*hemo)[13].c[0][2] = 11.0;  (*hemo)[13].c[0][3] = 29.0;

  (*hemo)[13].v[0][0] = 716.0; (*hemo)[13].v[0][1] =  50.0;
  (*hemo)[13].v[0][2] = 666.0; (*hemo)[13].v[0][3] = 866.0;

  (*hemo)[13].r[0][0] = 0.10;  (*hemo)[13].r[0][1] = 0.05;
  (*hemo)[13].r[0][2] =  0.0;  (*hemo)[13].r[0][3] = 0.25;

  (*hemo)[13].h[0][0] = 106.0; (*hemo)[13].h[0][1] =   6.0;
  (*hemo)[13].h[0][2] =  88.0; (*hemo)[13].h[0][3] = 124.0;


  // Abdominal venous compartment
  (*hemo)[14].c[0][0] = 1.3;   (*hemo)[14].c[0][1] = 0.1;
  (*hemo)[14].c[0][2] = 1.0;   (*hemo)[14].c[0][3] = 1.6;

  (*hemo)[14].v[0][0] = 79.0;  (*hemo)[14].v[0][1] =  10.0;
  (*hemo)[14].v[0][2] = 49.0;  (*hemo)[14].v[0][3] = 109.0;

  (*hemo)[14].r[0][0] = 0.019; (*hemo)[14].r[0][1] = 0.007;
  (*hemo)[14].r[0][2] = 0.0;   (*hemo)[14].r[0][3] = 0.040;

  (*hemo)[14].h[0][0] =  14.5; (*hemo)[14].h[0][1] =  1.5;
  (*hemo)[14].h[0][2] =  10.0; (*hemo)[14].h[0][3] = 19.0;


  // Inferior vena cava compartment
  (*hemo)[15].c[0][0] = 0.5;   (*hemo)[15].c[0][1] = 0.1;
  (*hemo)[15].c[0][2] = 0.2;   (*hemo)[15].c[0][3] = 0.8;

  (*hemo)[15].v[0][0] = 33.0;  (*hemo)[15].v[0][1] =  4.0;
  (*hemo)[15].v[0][2] = 21.0;  (*hemo)[15].v[0][3] = 45.0;

  (*hemo)[15].r[0][0] = 0.008; (*hemo)[15].r[0][1] = 0.003;
  (*hemo)[15].r[0][2] = 0.0;   (*hemo)[15].r[0][3] = 0.017;

  (*hemo)[15].h[0][0] = 6.0;   (*hemo)[15].h[0][1] = 0.5;
  (*hemo)[15].h[0][2] = 4.5;   (*hemo)[15].h[0][3] = 7.5;


  // Pulmonary venous compartment
  (*hemo)[16].c[0][0] = 9.0;   (*hemo)[16].c[0][1] =  3.7;
  (*hemo)[16].c[0][2] = 5.3;   (*hemo)[16].c[0][3] = 12.7;

  (*hemo)[16].v[0][0] = 430.0; (*hemo)[16].v[0][1] =  50.0;
  (*hemo)[16].v[0][2] = 180.0; (*hemo)[16].v[0][3] = 580.0;

  (*hemo)[16].r[0][0] = 0.006; (*hemo)[16].r[0][1] = 0.003;
  (*hemo)[16].r[0][2] = 0.0;   (*hemo)[16].r[0][3] = 0.015;

  (*hemo)[16].h[0][0] = 0.0;   (*hemo)[16].h[0][1] = 0.0;
  (*hemo)[16].h[0][2] = 0.0;   (*hemo)[16].h[0][3] = 0.0;



  // Microvascular resistances
  (*micro_r).r[0][0] = 4.9;  (*micro_r).r[0][1] = 1.6;
  (*micro_r).r[0][2] = 3.3;  (*micro_r).r[0][3] = 6.5;

  (*micro_r).r[1][0] = 4.1;  (*micro_r).r[1][1] = 1.0;
  (*micro_r).r[1][2] = 3.2;  (*micro_r).r[1][3] = 6.2;

  (*micro_r).r[2][0] = 3.0;  (*micro_r).r[2][1] = 1.0;
  (*micro_r).r[2][2] = 2.3;  (*micro_r).r[2][3] = 4.3;

  (*micro_r).r[3][0] = 4.5;  (*micro_r).r[3][1] = 0.5;
  (*micro_r).r[3][2] = 4.0;  (*micro_r).r[3][3] = 10.3;

  (*micro_r).r[4][0] = 0.07;  (*micro_r).r[4][1] = 0.04;
  (*micro_r).r[4][2] =  0.0;  (*micro_r).r[4][3] = 0.19;


  // Cardiac parameters
  (*cardiac)[0].c_sys[0][0] = 1.35;  (*cardiac)[0].c_sys[0][1] = 0.18;
  (*cardiac)[0].c_sys[0][2] = 0.60;  (*cardiac)[0].c_sys[0][3] = 2.7;

  (*cardiac)[0].c_sys[1][0] = 1.30;  (*cardiac)[0].c_sys[1][1] = 0.47;
  (*cardiac)[0].c_sys[1][2] = 0.30;  (*cardiac)[0].c_sys[1][3] = 2.0;

  (*cardiac)[1].c_sys[0][0] = 1.64;  (*cardiac)[1].c_sys[0][1] = 0.19;
  (*cardiac)[1].c_sys[0][2] = 0.80;  (*cardiac)[1].c_sys[0][3] = 3.20;

  (*cardiac)[1].c_sys[1][0] = 0.40;  (*cardiac)[1].c_sys[1][1] = 0.10;
  (*cardiac)[1].c_sys[1][2] = 0.20;  (*cardiac)[1].c_sys[1][3] = 0.77;


  (*cardiac)[0].c_dias[0][0] = 3.33;   (*cardiac)[0].c_dias[0][1] = 0.56;
  (*cardiac)[0].c_dias[0][2] = 1.50;   (*cardiac)[0].c_dias[0][3] = 6.0;

  (*cardiac)[0].c_dias[1][0] = 19.29;  (*cardiac)[0].c_dias[1][1] = 5.0;
  (*cardiac)[0].c_dias[1][2] =  7.0;   (*cardiac)[0].c_dias[1][3] = 29.0;

  (*cardiac)[1].c_dias[0][0] = 2.0;    (*cardiac)[1].c_dias[0][1] = 0.4;
  (*cardiac)[1].c_dias[0][2] = 1.0;    (*cardiac)[1].c_dias[0][3] = 4.3;

  (*cardiac)[1].c_dias[1][0] = 9.69;   (*cardiac)[1].c_dias[1][1] = 1.18;
  (*cardiac)[1].c_dias[1][2] = 3.88;   (*cardiac)[1].c_dias[1][3] = 15.11;


  (*cardiac)[0].v[0][0] = 14.0;  (*cardiac)[0].v[0][1] =  1.0;
  (*cardiac)[0].v[0][2] = 10.0;  (*cardiac)[0].v[0][3] = 18.0;

  (*cardiac)[0].v[1][0] = 46.0;  (*cardiac)[0].v[1][1] = 21.0;
  (*cardiac)[0].v[1][2] = 10.0;  (*cardiac)[0].v[1][3] = 82.0;

  (*cardiac)[1].v[0][0] = 24.0;  (*cardiac)[1].v[0][1] = 13.0;
  (*cardiac)[1].v[0][2] = 10.0;  (*cardiac)[1].v[0][3] = 38.0;

  (*cardiac)[1].v[1][0] = 55.0;  (*cardiac)[1].v[1][1] = 10.0;
  (*cardiac)[1].v[1][2] = 25.0;  (*cardiac)[1].v[1][3] = 85.0;


  (*cardiac)[0].r[0][0] = 0.006;   (*cardiac)[0].r[0][1] = 0.003;
  (*cardiac)[0].r[0][2] = 0.0;     (*cardiac)[0].r[0][3] = 0.015;

  (*cardiac)[1].r[0][0] = 0.01;    (*cardiac)[1].r[0][1] = 0.001;
  (*cardiac)[1].r[0][2] = 0.007;   (*cardiac)[1].r[0][3] = 0.013;



  // System-level parameters
  (*system).bv[0][0]  = 5150.0;  (*system).bv[0][1]  = 203.0;
  (*system).bv[0][2]  = 4041.0;  (*system).bv[0][3]  = 6460.0;

  (*system).hr[0][0]  = 70.0;    (*system).hr[0][1]  =  3.3;
  (*system).hr[0][2]  = 50.0;    (*system).hr[0][3]  = 85.0;

  (*system).pth[0][0] = -4.0;    (*system).pth[0][1] =  1.0;
  (*system).pth[0][2] = -6.0;    (*system).pth[0][3] = -2.0;

  (*system).h[0][0]   = 169.3;   (*system).h[0][1]   =   1.5;
  (*system).h[0][2]   = 164.8;   (*system).h[0][3]   = 173.8;

  (*system).w[0][0]   = 60.2;    (*system).w[0][1]   =  2.1;
  (*system).w[0][2]   = 63.9;    (*system).w[0][3]   = 76.5;

  (*system).bsa[0][0] = 1.83;    (*system).bsa[0][1] = 0.02;
  (*system).bsa[0][2] = 1.77;    (*system).bsa[0][3] = 1.89;


  (*system).T[0][0] = 0.2;      (*system).T[0][1] = 0.02;
  (*system).T[0][2] = 0.18;      (*system).T[0][3] = 0.30;

  (*system).T[1][0] = 0.3;      (*system).T[1][1] = 0.01;
  (*system).T[1][2] = 0.30;      (*system).T[1][3] = 0.40;

  (*system).T[2][0] = 0.12;      (*system).T[2][1] = 0.002;
  (*system).T[2][2] = 0.17;      (*system).T[2][3] = 0.20;



  // Reflex system parameters
  (*reflex)[0].set[0][0] = 91.0;  (*reflex)[0].set[0][1] =   3.0;
  (*reflex)[0].set[0][2] = 89.0;  (*reflex)[0].set[0][3] = 105.0;

  (*reflex)[0].set[1][0] = 18.0;  (*reflex)[0].set[1][1] = 0.0;
  (*reflex)[0].set[1][2] = 18.0;  (*reflex)[0].set[1][3] = 18.0;

  (*reflex)[1].set[0][0] =  8.0;  (*reflex)[1].set[0][1] =  1.0;
  (*reflex)[1].set[0][2] =  4.0;  (*reflex)[1].set[0][3] = 10.0;

  (*reflex)[1].set[1][0] =  5.0;  (*reflex)[1].set[1][1] = 0.0;
  (*reflex)[1].set[1][2] =  5.0;  (*reflex)[1].set[1][3] = 5.0;


  (*reflex)[0].rr[0][0] = 0.012;  (*reflex)[0].rr[0][1] = 0.004;
  (*reflex)[0].rr[0][2] = 0.005;  (*reflex)[0].rr[0][3] = 0.017;

  (*reflex)[0].rr[1][0] = 0.009;  (*reflex)[0].rr[1][1] = 0.004;
  (*reflex)[0].rr[1][2] = 0.005;  (*reflex)[0].rr[1][3] = 0.017;

  (*reflex)[1].rr[0][0] = 0.0;    (*reflex)[1].rr[0][1] = 0.0;
  (*reflex)[1].rr[0][2] = 0.0;    (*reflex)[1].rr[0][3] = 0.0;

  (*reflex)[1].rr[1][0] = 0.0;    (*reflex)[1].rr[1][1] = 0.0;
  (*reflex)[1].rr[1][2] = 0.0;    (*reflex)[1].rr[1][3] = 0.0;


  (*reflex)[0].res[0][0] = -0.13; (*reflex)[0].res[0][1] =  0.05;
  (*reflex)[0].res[0][2] = -0.15; (*reflex)[0].res[0][3] = -0.05;

  (*reflex)[0].res[1][0] = -0.13; (*reflex)[0].res[1][1] =  0.05;
  (*reflex)[0].res[1][2] = -0.15; (*reflex)[0].res[1][3] = -0.05;

  (*reflex)[0].res[2][0] = -0.13; (*reflex)[0].res[2][1] =  0.05;
  (*reflex)[0].res[2][2] = -0.15; (*reflex)[0].res[2][3] = -0.05;

  (*reflex)[0].res[3][0] = -0.13; (*reflex)[0].res[3][1] =  0.05;
  (*reflex)[0].res[3][2] = -0.15; (*reflex)[0].res[3][3] = -0.05;

  (*reflex)[1].res[0][0] = -0.3; (*reflex)[1].res[0][1] =  0.05;
  (*reflex)[1].res[0][2] = -0.4; (*reflex)[1].res[0][3] = -0.2;

  (*reflex)[1].res[1][0] = -0.3; (*reflex)[1].res[1][1] =  0.05;
  (*reflex)[1].res[1][2] = -0.4; (*reflex)[1].res[1][3] = -0.2;

  (*reflex)[1].res[2][0] = -0.3; (*reflex)[1].res[2][1] =  0.05;
  (*reflex)[1].res[2][2] = -0.4; (*reflex)[1].res[2][3] = -0.2;

  (*reflex)[1].res[3][0] = -0.3; (*reflex)[1].res[3][1] =  0.05;
  (*reflex)[1].res[3][2] = -0.4; (*reflex)[1].res[3][3] = -0.2;

  (*reflex)[0].vt[0][0] =  5.3*alpha;   (*reflex)[0].vt[0][1] = 0.85*alpha;
  (*reflex)[0].vt[0][2] =  3.6*alpha;   (*reflex)[0].vt[0][3] = 6.15*alpha;

  (*reflex)[0].vt[1][0] =  1.3*alpha;   (*reflex)[0].vt[1][1] = 0.2;
  (*reflex)[0].vt[1][2] =  0.7*alpha;   (*reflex)[0].vt[1][3] = 2.0*alpha;

  (*reflex)[0].vt[2][0] = 13.3*alpha;   (*reflex)[0].vt[2][1] =  2.1;
  (*reflex)[0].vt[2][2] =  9.0*alpha;   (*reflex)[0].vt[2][3] = 17.6*alpha;

  (*reflex)[0].vt[3][0] =  6.7*alpha;   (*reflex)[0].vt[3][1] = 1.1;
  (*reflex)[0].vt[3][2] =  4.5*alpha;   (*reflex)[0].vt[3][3] = 9.0*alpha;

  (*reflex)[1].vt[0][0] = 13.5*beta;   (*reflex)[1].vt[0][1] =  2.7;
  (*reflex)[1].vt[0][2] =  8.1*beta;   (*reflex)[1].vt[0][3] = 19.0*beta;

  (*reflex)[1].vt[1][0] =  2.7*beta;   (*reflex)[1].vt[1][1] = 0.5;
  (*reflex)[1].vt[1][2] =  2.2*beta;   (*reflex)[1].vt[1][3] = 3.2*beta;

  (*reflex)[1].vt[2][0] = 64.0*beta;   (*reflex)[1].vt[2][1] = 12.8;
  (*reflex)[1].vt[2][2] = 38.4*beta;   (*reflex)[1].vt[2][3] = 90.0*beta;

  (*reflex)[1].vt[3][0] = 30.0*beta;   (*reflex)[1].vt[3][1] =  6.0;
  (*reflex)[1].vt[3][2] = 18.0*beta;   (*reflex)[1].vt[3][3] = 42.0*beta;


  (*reflex)[0].c[0][0] = 0.021;  (*reflex)[0].c[0][1] = 0.003;
  (*reflex)[0].c[0][2] = 0.007;  (*reflex)[0].c[0][3] = 0.030;

  (*reflex)[0].c[1][0] = 0.014;  (*reflex)[0].c[1][1] = 0.001;
  (*reflex)[0].c[1][2] = 0.004;  (*reflex)[0].c[1][3] = 0.014;

  (*reflex)[1].c[0][0] = 0.0;    (*reflex)[1].c[0][1] = 0.0;
  (*reflex)[1].c[0][2] = 0.0;    (*reflex)[1].c[0][3] = 0.0;

  (*reflex)[1].c[1][0] = 0.0;    (*reflex)[1].c[1][1] = 0.0;
  (*reflex)[1].c[1][2] = 0.0;    (*reflex)[1].c[1][3] = 0.0;



  // Timing parameters
  (*timing).para[0][0] = 0.59;  (*timing).para[0][1] = 0.25;
  (*timing).para[0][2] = 0.34;  (*timing).para[0][3] = 0.84;

  (*timing).para[1][0] = 0.70;  (*timing).para[1][1] = 0.25;
  (*timing).para[1][2] = 0.45;  (*timing).para[1][3] = 0.95;

  (*timing).para[2][0] = 1.00;  (*timing).para[2][1] = 0.25;
  (*timing).para[2][2] = 0.75;  (*timing).para[2][3] = 1.50;


  (*timing).beta[0][0] = 2.5;   (*timing).beta[0][1] = 1.0;
  (*timing).beta[0][2] = 1.0;   (*timing).beta[0][3] = 4.5;

  (*timing).beta[1][0] = 3.5;   (*timing).beta[1][1] = 1.0;
  (*timing).beta[1][2] = 2.0;   (*timing).beta[1][3] = 5.0;

  (*timing).beta[2][0] = 15.0;  (*timing).beta[2][1] =  5.0;
  (*timing).beta[2][2] = 10.0;  (*timing).beta[2][3] = 20.0;


  (*timing).alpha_r[0][0] = 2.5;   (*timing).alpha_r[0][1] = 1.0;
  (*timing).alpha_r[0][2] = 1.0;   (*timing).alpha_r[0][3] = 4.5;

  (*timing).alpha_r[1][0] = 3.5;   (*timing).alpha_r[1][1] = 1.0;
  (*timing).alpha_r[1][2] = 3.0;   (*timing).alpha_r[1][3] = 6.0;

  (*timing).alpha_r[2][0] = 30.0;  (*timing).alpha_r[2][1] =  5.0;
  (*timing).alpha_r[2][2] = 25.0;  (*timing).alpha_r[2][3] = 35.0;


  (*timing).alpha_v[0][0] = 5.0;   (*timing).alpha_v[0][1] = 1.5;
  (*timing).alpha_v[0][2] = 2.7;   (*timing).alpha_v[0][3] = 7.3;

  (*timing).alpha_v[1][0] = 10.0;   (*timing).alpha_v[1][1] = 1.5;
  (*timing).alpha_v[1][2] = 4.7;   (*timing).alpha_v[1][3] = 9.3;

  (*timing).alpha_v[2][0] = 42.0;  (*timing).alpha_v[2][1] =  5.0;
  (*timing).alpha_v[2][2] = 32.5;  (*timing).alpha_v[2][3] = 47.5;


  (*timing).alpha_cpr[0][0] = 2.5;   (*timing).alpha_cpr[0][1] = 1.0;
  (*timing).alpha_cpr[0][2] = 1.0;   (*timing).alpha_cpr[0][3] = 4.5;

  (*timing).alpha_cpr[1][0] = 5.5;   (*timing).alpha_cpr[1][1] = 1.0;
  (*timing).alpha_cpr[1][2] = 3.0;   (*timing).alpha_cpr[1][3] = 6.0;

  (*timing).alpha_cpr[2][0] = 35.0;  (*timing).alpha_cpr[2][1] =  5.0;
  (*timing).alpha_cpr[2][2] = 25.0;  (*timing).alpha_cpr[2][3] = 35.0;


  (*timing).alpha_cpv[0][0] = 5.0;   (*timing).alpha_cpv[0][1] = 1.5;
  (*timing).alpha_cpv[0][2] = 2.7;   (*timing).alpha_cpv[0][3] = 7.3;

  (*timing).alpha_cpv[1][0] = 9.0;   (*timing).alpha_cpv[1][1] = 1.5;
  (*timing).alpha_cpv[1][2] = 4.7;   (*timing).alpha_cpv[1][3] = 9.3;

  (*timing).alpha_cpv[2][0] = 40.0;  (*timing).alpha_cpv[2][1] =  5.0;
  (*timing).alpha_cpv[2][2] = 32.5;  (*timing).alpha_cpv[2][3] = 47.5;

}

void mapping_ptr(Hemo *hemo, Cardiac *cardiac, Micro_r *micro_r, System_parameters *system, Reflex *reflex, Timing *timing, Parameter_vector *tmp)
{

  tmp -> vec[0] =   (*reflex)[0].set[0][0];     // Arterial set-point
  tmp -> vec[1] =   (*reflex)[0].set[1][0];     // ABR scaling factor
  tmp -> vec[2] =   (*reflex)[0].rr[0][0];      // RR-symp. gain
  tmp -> vec[3] =   (*reflex)[0].rr[1][0];      // RR-parasymp. gain 
  tmp -> vec[4] =   (*reflex)[0].res[0][0];     // ABR R gain to up compartment
  tmp -> vec[5] =   (*reflex)[0].res[1][0];     // ABR R gain to k compartment
  tmp -> vec[6] =   (*reflex)[0].res[2][0];     // ABR R gain to sp compartment
  tmp -> vec[7] =   (*reflex)[0].res[3][0];     // ABR R gain to ll compartment
  tmp -> vec[8] =   (*reflex)[0].vt[0][0];      // ABR VT gain to up compartment
  tmp -> vec[9] =   (*reflex)[0].vt[1][0];      // ABR VT gain to k compartment
  tmp -> vec[10] =  (*reflex)[0].vt[2][0];      // ABR VT gain to sp compartment 
  tmp -> vec[11] =  (*reflex)[0].vt[3][0];      // ABR VT gain to ll compartment
  tmp -> vec[12] =  (*reflex)[0].c[0][0];       // ABR RV contractility gain
  tmp -> vec[13] =  (*reflex)[0].c[1][0];       // ABR LV contractility gain
  tmp -> vec[14] =   -25.0;      // Sensed pressure off-set during tilt
  tmp -> vec[15] =  (*reflex)[1].set[0][0];     // Cardio-pulmonary set-point
  tmp -> vec[16] =  (*reflex)[1].set[1][0];     // CPR scaling factor
  tmp -> vec[17] =  (*reflex)[1].res[0][0];     // CPR R gain to up compartment   
  tmp -> vec[18] =  (*reflex)[1].res[1][0];     // CPR R gain to k compartment
  tmp -> vec[19] =  (*reflex)[1].res[2][0];     // CPR R gain to sp compartment 
  tmp -> vec[20] =  (*reflex)[1].res[3][0];     // CPR R gain to ll compartment
  tmp -> vec[21] =  (*reflex)[1].vt[0][0];      // CPR VT gain to up compartment
  tmp -> vec[22] =  (*reflex)[1].vt[1][0];      // CPR VT gain to k compartment
  tmp -> vec[23] =  (*reflex)[1].vt[2][0];      // CPR VT gain to sp compartment
  tmp -> vec[24] =  (*reflex)[1].vt[3][0];      // CPR VT gain to ll compartment
  tmp -> vec[25] =  timing->para[0][0];       // Delay parasymp.
  tmp -> vec[26] =  timing->para[1][0];       // Peak parasymp.
  tmp -> vec[27] =  timing->para[2][0];       // End parasymp.
  tmp -> vec[28] =  timing->beta[0][0];       // Delay beta-sympathetic
  tmp -> vec[29] =  timing->beta[1][0];       // Peak beta- sympathetic
  tmp -> vec[30] =  timing->beta[2][0];       // End beat-sympathetic
  tmp -> vec[31] =  system->pth[0][0];        // Intra-thoracic pressure
  tmp -> vec[32] =     7.0;      // Pbias_max at sp compartment
  tmp -> vec[33] =    40.0;      // Pbias_max at ll compartment
  tmp -> vec[34] =     5.0;      // Pbias_max at ab compartment
  tmp -> vec[35] =     2.0;      // Arterial compliance
  tmp -> vec[36] =  (*hemo)[9].c[0][0];         // C upper body veins
  tmp -> vec[37] =  (*hemo)[11].c[0][0];        // C renal veins
  tmp -> vec[38] =  (*hemo)[12].c[0][0];        // C splanchnic veins
  tmp -> vec[39] =  (*hemo)[13].c[0][0];        // C lower body veins
  tmp -> vec[40] =  (*hemo)[14].c[0][0];        // C abdominal veins
  tmp -> vec[41] =  (*hemo)[15].c[0][0];        // C inferior vena cava
  tmp -> vec[42] =  (*hemo)[10].c[0][0];        // C superior vena cava
  tmp -> vec[43] =  (*cardiac)[0].c_dias[0][0]; // RA diastolic compliance
  tmp -> vec[44] =  (*cardiac)[0].c_sys[0][0];  // RA systolic compliance
  tmp -> vec[45] =  (*cardiac)[0].c_dias[1][0]; // RV diastolic compliance
  tmp -> vec[46] =  (*cardiac)[0].c_sys[1][0];  // RV systolic compliance
  tmp -> vec[47] =  (*hemo)[8].c[0][0];         // C pul. arteries
  tmp -> vec[48] =  (*hemo)[16].c[0][0];        // C pul. veins
  tmp -> vec[49] =  (*cardiac)[1].c_dias[0][0]; // LA diastolic compliance
  tmp -> vec[50] =  (*cardiac)[1].c_sys[0][0];  // LA systolic compliance
  tmp -> vec[51] =  (*cardiac)[1].c_dias[1][0]; // LV diastolic compliance
  tmp -> vec[52] =  (*cardiac)[1].c_sys[1][0];  // LV systolic compliance
  tmp -> vec[53] =  micro_r->r[0][0];         // R upper body
  tmp -> vec[54] =  (*hemo)[9].r[0][0];         // R upper body outflow
  tmp -> vec[55] =  micro_r->r[1][0];         // R kidney compartment
  tmp -> vec[56] =  (*hemo)[11].r[0][0];        // R kidney outflow
  tmp -> vec[57] =  micro_r->r[2][0];         // R splanchnic compartment
  tmp -> vec[58] =  (*hemo)[12].r[0][0];        // R splanchnic outflow
  tmp -> vec[59] =  micro_r->r[3][0];         // R lower body compartment
  tmp -> vec[60] =  (*hemo)[13].r[0][0];        // R lower body outflow
  tmp -> vec[61] =  (*hemo)[14].r[0][0];        // R abdominal venous compartment
  tmp -> vec[62] =  (*hemo)[15].r[0][0];        // R inferior vena cava comp.
  tmp -> vec[63] =  (*hemo)[10].r[0][0];        // R superior vena cava comp.
  tmp -> vec[64] =  (*cardiac)[0].r[0][0];      // R tricuspid valve
  tmp -> vec[65] =  (*hemo)[8].r[0][0];         // R right ventricular outflow
  tmp -> vec[66] =  micro_r->r[4][0];         // R pulmonary microcirculation
  tmp -> vec[67] =  (*hemo)[16].r[0][0];        // R pulmonary veins / LV inflow
  tmp -> vec[68] =  (*cardiac)[1].r[0][0];      // R mitral valve
  tmp -> vec[69] =  (*hemo)[0].r[0][0];         // R left ventricular outflow
  tmp -> vec[70] =  system->bv[0][0];         // Total blood volume   
  tmp -> vec[71] =  1500.0;      // Maximal increase in sp distending vol.
  tmp -> vec[72] =  1000.0;      // Maximal increase in ll distending vol.
  tmp -> vec[73] =   650.0;      // Maximal increase in ab distending vol.
  tmp -> vec[74] =   300.0;      // Maximal blood volume loss during tilt
  tmp -> vec[75] =  4166.0;      // Total zero pressure filling volume
  tmp -> vec[76] =   715.0;      // Volume of arterial compartment
  tmp -> vec[77] =   (*hemo)[9].v[0][0];        // ZPFV of upper body compartment
  tmp -> vec[78] =   (*hemo)[11].v[0][0];       // ZPFV of kidney compartment
  tmp -> vec[79] =   (*hemo)[12].v[0][0];       // ZPFV of splanchnic compartment
  tmp -> vec[80] =   (*hemo)[13].v[0][0];       // ZPFV of lower body compartment
  tmp -> vec[81] =   (*hemo)[14].v[0][0];       // ZPFV of abdominal veins
  tmp -> vec[82] =   (*hemo)[15].v[0][0];       // ZPFV of inferior vena cava
  tmp -> vec[83] =   (*hemo)[10].v[0][0];       // ZPFV of superior vena cava
  tmp -> vec[84] =   (*cardiac)[0].v[0][0];     // ZPFV of right atrium
  tmp -> vec[85] =   (*cardiac)[0].v[1][0];     // ZPFV of right ventricle
  tmp -> vec[86] =   (*hemo)[8].v[0][0];        // ZPFV of pulmonary arteries
  tmp -> vec[87] =   (*hemo)[16].v[0][0];       // ZPFV of pulmonary veins
  tmp -> vec[88] =   (*cardiac)[1].v[0][0];     // ZPFV of left atrium
  tmp -> vec[89] =   (*cardiac)[1].v[1][0];     // ZPFV of left ventricle
  tmp -> vec[90] =   system->hr[0][0];        // Nominal heart rate
  tmp -> vec[91] =     0.0;      // Tilt angle
  tmp -> vec[92] =     2.0;      // Time to max. tilt angle (i.e. tilt time)
  tmp -> vec[93] =   200.0;      // Tilt onset time
  tmp -> vec[94] =   240.0;      // Duration in upright posture
  tmp -> vec[95] =     0.0;      // Maximal external negative pressure
  tmp -> vec[96] =   500.0;      // Volume loss during LBNP
  tmp -> vec[97] =   (*hemo)[0].c[0][0];        // C aortic arch
  tmp -> vec[98] =   (*hemo)[1].c[0][0];        // C upper thoracic aorta
  tmp -> vec[99] =   (*hemo)[3].c[0][0];        // C lower thoracic aorta
  tmp -> vec[100] =  (*hemo)[2].c[0][0];        // C upper body arteries
  tmp -> vec[101] =  (*hemo)[4].c[0][0];        // C abdominal aorta
  tmp -> vec[102] =  (*hemo)[5].c[0][0];        // C renal arteries
  tmp -> vec[103] =  (*hemo)[6].c[0][0];        // C splanchnic arteries
  tmp -> vec[104] =  (*hemo)[7].c[0][0];        // C leg arteries and arterioles
  tmp -> vec[105] =  (*hemo)[1].r[0][0];        // R upper thoracic aorta
  tmp -> vec[106] =  (*hemo)[2].r[0][0];        // R head arteries
  tmp -> vec[107] =  (*hemo)[3].r[0][0];        // R lower thoracic aorta
  tmp -> vec[108] =  (*hemo)[4].r[0][0];        // R abdominal aorta
  tmp -> vec[109] =  (*hemo)[5].r[0][0];        // R renal arteries 
  tmp -> vec[110] =  (*hemo)[6].r[0][0];        // R splanchnic arteries
  tmp -> vec[111] =  (*hemo)[7].r[0][0];        // R leg arteries
  tmp -> vec[112] =  timing->alpha_v[0][0];   // Delay alpha-symp. to veins
  tmp -> vec[113] =  timing->alpha_v[1][0];   // Peak alpha-symp. to veins
  tmp -> vec[114] =  timing->alpha_v[2][0];   // End alpha-symp. to veins
  tmp -> vec[115] =  timing->alpha_r[0][0];   // Delay alpha-symp. to arteries
  tmp -> vec[116] =  timing->alpha_r[1][0];   // Peak alpha-symp. to arteries
  tmp -> vec[117] =  timing->alpha_r[2][0];   // End alpha-symp. to arteries
  tmp -> vec[118] =  system->T[2][0];         // PR-interval
  tmp -> vec[119] =  system->T[0][0];         // Atrial systole
  tmp -> vec[120] =  system->T[1][0];         // Ventricular systole
  tmp -> vec[121] =  (*hemo)[0].h[0][0];
  tmp -> vec[122] =  (*hemo)[1].h[0][0];
  tmp -> vec[123] =  (*hemo)[2].h[0][0];
  tmp -> vec[124] =  (*hemo)[9].h[0][0];
  tmp -> vec[125] =  (*hemo)[10].h[0][0];
  tmp -> vec[126] =  (*hemo)[3].h[0][0];
  tmp -> vec[127] =  (*hemo)[4].h[0][0];
  tmp -> vec[128] =  (*hemo)[5].h[0][0];
  tmp -> vec[129] =  (*hemo)[11].h[0][0];
  tmp -> vec[130] =  (*hemo)[6].h[0][0];
  tmp -> vec[131] =  (*hemo)[12].h[0][0];
  tmp -> vec[132] =  (*hemo)[7].h[0][0];
  tmp -> vec[133] =  (*hemo)[13].h[0][0];
  tmp -> vec[134] =  (*hemo)[14].h[0][0];
  tmp -> vec[135] =  (*hemo)[15].h[0][0];
  tmp -> vec[136] =  (*hemo)[0].v[0][0];       // V aortic arch
  tmp -> vec[137] =  (*hemo)[1].v[0][0];       // V upper thoracic aorta
  tmp -> vec[138] =  (*hemo)[2].v[0][0];       // V lower thoracic aorta
  tmp -> vec[139] =  (*hemo)[3].v[0][0];       // V upper body arteries
  tmp -> vec[140] =  (*hemo)[4].v[0][0];       // V abdominal aorta
  tmp -> vec[141] =  (*hemo)[5].v[0][0];       // V renal arteries
  tmp -> vec[142] =  (*hemo)[6].v[0][0];       // V splanchnic arteries
  tmp -> vec[143] =  (*hemo)[7].v[0][0];       // V leg arteries
  tmp -> vec[144] =  system->h[0][0];
  tmp -> vec[145] =  system->w[0][0];
  tmp -> vec[146] =  system->bsa[0][0];
  tmp -> vec[147] =  timing->alpha_cpv[0][0];   // Delay alpha-symp. to veins
  tmp -> vec[148] =  timing->alpha_cpv[1][0];   // Peak alpha-symp. to veins
  tmp -> vec[149] =  timing->alpha_cpv[2][0];   // End alpha-symp. to veins
  tmp -> vec[150] =  timing->alpha_cpr[0][0];   // Delay alpha-symp. to arteries
  tmp -> vec[151] =  timing->alpha_cpr[1][0];   // Peak alpha-symp. to arteries
  tmp -> vec[152] =  timing->alpha_cpr[2][0];   // End alpha-symp. to arteries

}


/* The following subroutine maps the standard error for each variable to the 
 * array tmp.
 */

void map_sigma_ptr(Hemo *hemo, Cardiac *cardiac, Micro_r *micro_r, System_parameters *system, Reflex *reflex, Timing *timing, Parameter_vector *tmp)
{

  tmp -> vec[0] =   (*reflex)[0].set[0][1];     // Arterial set-point
  tmp -> vec[1] =   (*reflex)[0].set[1][1];     // ABR scaling factor
  tmp -> vec[2] =   (*reflex)[0].rr[0][1];      // RR-symp. gain
  tmp -> vec[3] =   (*reflex)[0].rr[1][1];      // RR-parasymp. gain 
  tmp -> vec[4] =   (*reflex)[0].res[0][1];     // ABR R gain to up compartment
  tmp -> vec[5] =   (*reflex)[0].res[1][1];     // ABR R gain to k compartment
  tmp -> vec[6] =   (*reflex)[0].res[2][1];     // ABR R gain to sp compartment
  tmp -> vec[7] =   (*reflex)[0].res[3][1];     // ABR R gain to ll compartment
  tmp -> vec[8] =   (*reflex)[0].vt[0][1];      // ABR VT gain to up compartment
  tmp -> vec[9] =   (*reflex)[0].vt[1][1];      // ABR VT gain to k compartment
  tmp -> vec[10] =  (*reflex)[0].vt[2][1];      // ABR VT gain to sp compartment 
  tmp -> vec[11] =  (*reflex)[0].vt[3][1];      // ABR VT gain to ll compartment
  tmp -> vec[12] =  (*reflex)[0].c[0][1];       // ABR RV contractility gain
  tmp -> vec[13] =  (*reflex)[0].c[1][1];       // ABR LV contractility gain
  tmp -> vec[14] =   -2.5;              // Sensed pressure off-set during tilt
  tmp -> vec[15] =  (*reflex)[1].set[0][1];     // Cardio-pulmonary set-point
  tmp -> vec[16] =  (*reflex)[1].set[1][1];     // CPR scaling factor
  tmp -> vec[17] =  (*reflex)[1].res[0][1];     // CPR R gain to up compartment   
  tmp -> vec[18] =  (*reflex)[1].res[1][1];     // CPR R gain to k compartment
  tmp -> vec[19] =  (*reflex)[1].res[2][1];     // CPR R gain to sp compartment 
  tmp -> vec[20] =  (*reflex)[1].res[3][1];     // CPR R gain to ll compartment
  tmp -> vec[21] =  (*reflex)[1].vt[0][1];      // CPR VT gain to up compartment
  tmp -> vec[22] =  (*reflex)[1].vt[1][1];      // CPR VT gain to k compartment
  tmp -> vec[23] =  (*reflex)[1].vt[2][1];      // CPR VT gain to sp compartment
  tmp -> vec[24] =  (*reflex)[1].vt[3][1];      // CPR VT gain to ll compartment
  tmp -> vec[25] =  timing->para[0][1];       // Delay parasymp.
  tmp -> vec[26] =  timing->para[1][1];       // Peak parasymp.
  tmp -> vec[27] =  timing->para[2][1];       // End parasymp.
  tmp -> vec[28] =  timing->beta[0][1];       // Delay beta-sympathetic
  tmp -> vec[29] =  timing->beta[1][1];       // Peak beta- sympathetic
  tmp -> vec[30] =  timing->beta[2][1];       // End beat-sympathetic
  tmp -> vec[31] =  system->pth[0][1];        // Intra-thoracic pressure
  tmp -> vec[32] =     0.0;      // Pbias_max at sp compartment
  tmp -> vec[33] =     0.0;      // Pbias_max at ll compartment
  tmp -> vec[34] =     0.0;      // Pbias_max at ab compartment
  tmp -> vec[35] =     0.0;      // Arterial compliance
  tmp -> vec[36] =  (*hemo)[9].c[0][1];         // C upper body veins
  tmp -> vec[37] =  (*hemo)[11].c[0][1];        // C renal veins
  tmp -> vec[38] =  (*hemo)[12].c[0][1];        // C splanchnic veins
  tmp -> vec[39] =  (*hemo)[13].c[0][1];        // C lower body veins
  tmp -> vec[40] =  (*hemo)[14].c[0][1];        // C abdominal veins
  tmp -> vec[41] =  (*hemo)[15].c[0][1];        // C inferior vena cava
  tmp -> vec[42] =  (*hemo)[10].c[0][1];        // C superior vena cava
  tmp -> vec[43] =  (*cardiac)[0].c_dias[0][1]; // RA diastolic compliance
  tmp -> vec[44] =  (*cardiac)[0].c_sys[0][1];  // RA systolic compliance
  tmp -> vec[45] =  (*cardiac)[0].c_dias[1][1]; // RV diastolic compliance
  tmp -> vec[46] =  (*cardiac)[0].c_sys[1][1];  // RV systolic compliance
  tmp -> vec[47] =  (*hemo)[8].c[0][1];         // C pul. arteries
  tmp -> vec[48] =  (*hemo)[16].c[0][1];        // C pul. veins
  tmp -> vec[49] =  (*cardiac)[1].c_dias[0][1]; // LA diastolic compliance
  tmp -> vec[50] =  (*cardiac)[1].c_sys[0][1];  // LA systolic compliance
  tmp -> vec[51] =  (*cardiac)[1].c_dias[1][1]; // LV diastolic compliance
  tmp -> vec[52] =  (*cardiac)[1].c_sys[1][1];  // LV systolic compliance
  tmp -> vec[53] =  micro_r->r[0][1];         // R upper body
  tmp -> vec[54] =  (*hemo)[9].r[0][1];         // R upper body outflow
  tmp -> vec[55] =  micro_r->r[1][1];         // R kidney compartment
  tmp -> vec[56] =  (*hemo)[11].r[0][1];        // R kidney outflow
  tmp -> vec[57] =  micro_r->r[2][1];         // R splanchnic compartment
  tmp -> vec[58] =  (*hemo)[12].r[0][1];        // R splanchnic outflow
  tmp -> vec[59] =  micro_r->r[3][1];         // R lower body compartment
  tmp -> vec[60] =  (*hemo)[13].r[0][1];        // R lower body outflow
  tmp -> vec[61] =  (*hemo)[14].r[0][1];        // R abdominal venous compartment
  tmp -> vec[62] =  (*hemo)[15].r[0][1];        // R inferior vena cava comp.
  tmp -> vec[63] =  (*hemo)[10].r[0][1];        // R superior vena cava comp.
  tmp -> vec[64] =  (*cardiac)[0].r[0][1];      // R tricuspid valve
  tmp -> vec[65] =  (*hemo)[8].r[0][1];         // R right ventricular outflow
  tmp -> vec[66] =  micro_r->r[4][1];         // R pulmonary microcirculation
  tmp -> vec[67] =  (*hemo)[16].r[0][1];        // R pulmonary veins / LV inflow
  tmp -> vec[68] =  (*cardiac)[1].r[0][1];      // R mitral valve
  tmp -> vec[69] =  (*hemo)[0].r[0][1];         // R left ventricular outflow
  tmp -> vec[70] =  system->bv[0][1];         // Total blood volume   
  tmp -> vec[71] =   200.0;      // Maximal increase in sp distending vol.
  tmp -> vec[72] =   100.0;      // Maximal increase in ll distending vol.
  tmp -> vec[73] =    60.0;      // Maximal increase in ab distending vol.
  tmp -> vec[74] =    50.0;      // Maximal blood volume loss during tilt
  tmp -> vec[75] =     0.0;      // Total zero pressure filling volume
  tmp -> vec[76] =     0.0;      // Volume of arterial compartment
  tmp -> vec[77] =   (*hemo)[9].v[0][1];        // ZPFV of upper body compartment
  tmp -> vec[78] =   (*hemo)[11].v[0][1];       // ZPFV of kidney compartment
  tmp -> vec[79] =   (*hemo)[12].v[0][1];       // ZPFV of splanchnic compartment
  tmp -> vec[80] =   (*hemo)[13].v[0][1];       // ZPFV of lower body compartment
  tmp -> vec[81] =   (*hemo)[14].v[0][1];       // ZPFV of abdominal veins
  tmp -> vec[82] =   (*hemo)[15].v[0][1];       // ZPFV of inferior vena cava
  tmp -> vec[83] =   (*hemo)[10].v[0][1];       // ZPFV of superior vena cava
  tmp -> vec[84] =   (*cardiac)[0].v[0][1];     // ZPFV of right atrium
  tmp -> vec[85] =   (*cardiac)[0].v[1][1];     // ZPFV of right ventricle
  tmp -> vec[86] =   (*hemo)[8].v[0][1];        // ZPFV of pulmonary arteries
  tmp -> vec[87] =   (*hemo)[16].v[0][1];       // ZPFV of pulmonary veins
  tmp -> vec[88] =   (*cardiac)[1].v[0][1];     // ZPFV of left atrium
  tmp -> vec[89] =   (*cardiac)[1].v[1][1];     // ZPFV of left ventricle
  tmp -> vec[90] =   system->hr[0][1];        // Nominal heart rate
  tmp -> vec[91] =     0.0;      // Tilt angle
  tmp -> vec[92] =     0.0;      // Time to max. tilt angle (i.e. tilt time)
  tmp -> vec[93] =     0.0;      // Tilt onset time
  tmp -> vec[94] =     0.0;      // Duration in upright posture
  tmp -> vec[95] =     0.0;      // Maximal external negative pressure
  tmp -> vec[96] =     0.0;      // Volume loss during LBNP
  tmp -> vec[97] =   (*hemo)[0].c[0][1];        // C aortic arch
  tmp -> vec[98] =   (*hemo)[1].c[0][1];        // C upper thoracic aorta
  tmp -> vec[99] =   (*hemo)[3].c[0][1];        // C lower thoracic aorta
  tmp -> vec[100] =  (*hemo)[2].c[0][1];        // C upper body arteries
  tmp -> vec[101] =  (*hemo)[4].c[0][1];        // C abdominal aorta
  tmp -> vec[102] =  (*hemo)[5].c[0][1];        // C renal arteries
  tmp -> vec[103] =  (*hemo)[6].c[0][1];        // C splanchnic arteries
  tmp -> vec[104] =  (*hemo)[7].c[0][1];        // C leg arteries and arterioles
  tmp -> vec[105] =  (*hemo)[1].r[0][1];        // R upper thoracic aorta
  tmp -> vec[106] =  (*hemo)[2].r[0][1];        // R head arteries
  tmp -> vec[107] =  (*hemo)[3].r[0][1];        // R lower thoracic aorta
  tmp -> vec[108] =  (*hemo)[4].r[0][1];        // R abdominal aorta
  tmp -> vec[109] =  (*hemo)[5].r[0][1];        // R renal arteries 
  tmp -> vec[110] =  (*hemo)[6].r[0][1];        // R splanchnic arteries
  tmp -> vec[111] =  (*hemo)[7].r[0][1];        // R leg arteries
  tmp -> vec[112] =  timing->alpha_v[0][1];   // Delay alpha-symp. to veins
  tmp -> vec[113] =  timing->alpha_v[1][1];   // Peak alpha-symp. to veins
  tmp -> vec[114] =  timing->alpha_v[2][1];   // End alpha-symp. to veins
  tmp -> vec[115] =  timing->alpha_r[0][1];   // Delay alpha-symp. to arteries
  tmp -> vec[116] =  timing->alpha_r[1][1];   // Peak alpha-symp. to arteries
  tmp -> vec[117] =  timing->alpha_r[2][1];   // End alpha-symp. to arteries
  tmp -> vec[118] =  system->T[2][1];         // PR-interval
  tmp -> vec[119] =  system->T[0][1];         // Atrial systole
  tmp -> vec[120] =  system->T[1][1];         // Ventricular systole
  tmp -> vec[121] =  (*hemo)[0].h[0][1];
  tmp -> vec[122] =  (*hemo)[1].h[0][1];
  tmp -> vec[123] =  (*hemo)[2].h[0][1];
  tmp -> vec[124] =  (*hemo)[9].h[0][1];
  tmp -> vec[125] =  (*hemo)[10].h[0][1];
  tmp -> vec[126] =  (*hemo)[3].h[0][1];
  tmp -> vec[127] =  (*hemo)[4].h[0][1];
  tmp -> vec[128] =  (*hemo)[5].h[0][1];
  tmp -> vec[129] =  (*hemo)[11].h[0][1];
  tmp -> vec[130] =  (*hemo)[6].h[0][1];
  tmp -> vec[131] =  (*hemo)[12].h[0][1];
  tmp -> vec[132] =  (*hemo)[7].h[0][1];
  tmp -> vec[133] =  (*hemo)[13].h[0][1];
  tmp -> vec[134] =  (*hemo)[14].h[0][1];
  tmp -> vec[135] =  (*hemo)[15].h[0][1];
  tmp -> vec[136] =  (*hemo)[0].v[0][1];       // V aortic arch
  tmp -> vec[137] =  (*hemo)[1].v[0][1];       // V upper thoracic aorta
  tmp -> vec[138] =  (*hemo)[2].v[0][1];       // V lower thoracic aorta
  tmp -> vec[139] =  (*hemo)[3].v[0][1];       // V upper body arteries
  tmp -> vec[140] =  (*hemo)[4].v[0][1];       // V abdominal aorta
  tmp -> vec[141] =  (*hemo)[5].v[0][1];       // V renal arteries
  tmp -> vec[142] =  (*hemo)[6].v[0][1];       // V splanchnic arteries
  tmp -> vec[143] =  (*hemo)[7].v[0][1];       // V leg arteries
  tmp -> vec[144] =  system->h[0][1];
  tmp -> vec[145] =  system->w[0][1];
  tmp -> vec[146] =  system->bsa[0][1];
  tmp -> vec[147] =  timing->alpha_cpv[0][1];   // Delay alpha-symp. to veins
  tmp -> vec[148] =  timing->alpha_cpv[1][1];   // Peak alpha-symp. to veins
  tmp -> vec[149] =  timing->alpha_cpv[2][1];   // End alpha-symp. to veins
  tmp -> vec[150] =  timing->alpha_cpr[0][1];   // Delay alpha-symp. to arteries
  tmp -> vec[151] =  timing->alpha_cpr[1][1];   // Peak alpha-symp. to arteries
  tmp -> vec[152] =  timing->alpha_cpr[2][1];   // End alpha-symp. to arteries

}
