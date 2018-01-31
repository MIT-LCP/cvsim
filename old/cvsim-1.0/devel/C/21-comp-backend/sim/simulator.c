/* Simulator.c contains the top-level source code for the simulation routine.
 * The simulation routine reads the parameter vector and returns the 
 * simulation output (which currently is an array of three arterial blood
 * pressure numerics [SAP, MAP, DAP], heart rate, and stroke volume) to the
 * main routine.
 *
 * Thomas Heldt    January 29th, 2002
 * Last modified:  September 28th, 2006
 */
#include "simulator.h"

void simulator_ptr(Output_vector *out, Parameter_vector *a, int k, int N_OUT, double *result,
		   int ABReflexOn, int CPReflexOn, 
		   int tiltTestOn, double tiltStartTime, double tiltStopTime)
{
  // Structure handling the flow of data within the simulation.c routine.
  // See simulator.h for the definition.
  Data_vector pressure = {{0.0}};

  // Structure for the reflex variables.
  Reflex_vector reflex = {{0.0}};

  // Structure for the impulse response functions
  Impulse_vector imp = {{0.0}};

  double hdid = 0.0;
  double htry = 0.00001;
  double yscale[] = {1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.};
  double hnext = 0.0;

  // The following function calls initialize the data vector. Estimate()
  // initializes the pressure.x[] and pressure.time[] arrays. Elastance()
  // computes the cardiac compliances and their derivatives and stores their
  // values in pressure.c[] and pressure.dcdt[], respectively. Finally,
  // eqns() computes the pressure derivatives and stores them in 
  // pressure.dxdt[].
  estimate_ptr(&pressure, a, &reflex);      // estimate initial pressures

  // Set up the values for the time-varying capacitance values
  elastance_ptr(&pressure, a);

  fixvolume_ptr(&pressure, &reflex, a);     // fix the total blood volume

  // Initialize pressure derivatives
  eqns_ptr(&pressure, a, &reflex, tiltTestOn, tiltStartTime, tiltStopTime); 

  makeImp(&imp, a);                    // Set up the impulse resp. arrays

  numerics(&pressure, &reflex, out, a);

  while (pressure.time[0] < 100.) {
    rkqc_ptr(&pressure, &reflex, a, htry, 0.001, yscale, &hdid, &hnext, 
	     tiltTestOn, tiltStartTime, tiltStopTime);

    pressure.time[1] = sanode(&pressure, &reflex, a, hdid);
    elastance_ptr(&pressure, a);
    eqns_ptr(&pressure, a, &reflex, tiltTestOn, tiltStartTime, tiltStopTime);
    pressure.time[0] += hdid;

    htry = (hnext > 0.001 ? 0.001 : hnext);
    queue_ptr(&pressure, &imp, &reflex, a, hdid, ABReflexOn, CPReflexOn);
    fixvolume_ptr(&pressure, &reflex, a);

    numerics_new_ptr(&pressure, &reflex, hdid, N_OUT, result);
    numerics(&pressure, &reflex, out, a);
  }

  // The following lines either reset variables to their initial values or 
  // call functions that do the same in other files. This resets the entire
  // simulation subroutine to its initial state such that two consecutive 
  // simulations start with the same numeric parameters (as opposed to
  // physiological parameters).  
  //  numerics_reset();
  queue_reset();

}



/* The following subroutine does two things: (1) it extracts the beat-by-beat
 * numerics for systolic, mean, and diastolic pressure, heart rate, CVP, and
 * stroke volume and (2) is samples these numerics every T_SAMP seconds during
 * the orthostatic stress routine for queuing in the output structure. The 
 * numerics are computed on a beat-by-beat basis. Linear interpolation is used
 * to sample values between beats. 
 */
void numerics(Data_vector *p, Reflex_vector *r, Output_vector *out, Parameter_vector *theta)
{
  static int k = 0, l = 0, m = 0, n = 0;

  static double tmp_map = 0.0, tmp_dap = 500.0, tmp_sap = 0.0;
  static double tmp_sv = 0.0, tmp_cvp = 0.0;

  static double sap = 0.0, sap_prev = 0.0;
  static double map = 0.0, map_prev = 0.0;
  static double dap = 0.0, dap_prev = 0.0;
  static double cvp = 0.0, cvp_prev = 0.0;
  static double sv  = 0.0, sv_prev  = 0.0;

  static double sap_time_prev = 0.0, sap_time_next = 0.0, sap_time = 0.0;
  static double map_time_prev = 0.0, map_time_next = 0.0, map_time = 0.0;
  static double dap_time_prev = 0.0, dap_time_next = 0.0, dap_time = 0.0;

  static double prev_time = 0.0, current_time = 0.0;
  static double cum_dt = 0.0, cum_hr = 500.0;

  static double onset = 0.0;
  static double told = 0.0;
  static double tnew = 0.0;
  static double T_old = 0.0;
  static double hr_old = 0.0;

  double dt = 0.0, T_new = 0.0, hr_new = 0.0;

  // Initialize static variables between different calls to simulator.c
  if ((*p).time[0] < 1.0e-6) {
    k = 0, l = 0, m = 0, n = 0;
    T_old = 0.0;
    onset = 0.0;


    sap = sap_prev = tmp_sap = sap_time_prev = sap_time_next = 0.0;
    map = map_prev = tmp_map = map_time_prev = map_time_next = 0.0;
    dap = dap_prev = tmp_dap = dap_time_prev = dap_time_next = 0.0;

    cvp = cvp_prev = tmp_cvp = 0.0;
    sv  = sv_prev  = tmp_sv  = 0.0;
    hr_old = 0.0;

    sap_time = map_time = dap_time = (*theta).vec[93] - T_BASELINE;
    told = tnew = (*theta).vec[93] - T_BASELINE;

    tmp_dap = 500.0;
    cum_hr = 500.0;

    prev_time = current_time = cum_dt = 0.0;
  }

  // Detect onset of a new beat through change in cumulative heart rate signal
  // and compute new instantaneous heart rate signal sampled at 1/T_SAMP Hz.
  if ((*r).hr[1] < cum_hr) {
    T_new = (*p).time[0];
    hr_new = (*r).hr[2];

    if (((*p).time[0] > (*theta).vec[93]-T_BASELINE) && (k < N_SAMPLES))
      while (((T_new - told) > T_SAMP) && (k < N_SAMPLES)) {
	(*out).hr[k] = (hr_new-hr_old)*(tnew-T_old)/(T_new-T_old)+hr_old;
	told = tnew;
	tnew += T_SAMP;
	k++;
      }

    hr_old = hr_new;
    T_old = T_new;
  }

  // Update the local copy of the cummulative heart rate signal.
  cum_hr = (*r).hr[1];


  // The following piece of code computes the beat-by-beat numerics from the
  // pulsatile arterial pressure waveform and the stroke volume. It also re-
  // samples the numerics according to linear interpolation for output in the 
  // output array (*out).
  if ((*p).x[0] > tmp_dap && (*p).x[0] < tmp_sap)
    {
      current_time = (*p).time[0];
      dt = current_time - prev_time;
      
      sap_prev = sap;      map_prev = map;
      dap_prev = dap;      sv_prev = sv;
      cvp_prev = cvp; 

      map = tmp_map / cum_dt;     sap = tmp_sap;  
      dap = tmp_dap;              sv = tmp_sv;
      cvp = tmp_cvp / cum_dt;

      map_time_prev = map_time_next;
      map_time_next = 0.5 * (dap_time_next + dap_time_prev);


      // Compute the systolic pressure numerics with granularity T_SAMP.
      if ((sap_time_next > (*theta).vec[93]-T_BASELINE) && (l < N_SAMPLES))
	{
	  (*out).sap[l] = (sap-sap_prev)*(sap_time-sap_time_prev) /
	    (sap_time_next-sap_time_prev)+sap_prev;

	  while (((sap_time_next - sap_time) > T_SAMP) && ((l+1) < N_SAMPLES))
	    {
	      sap_time += T_SAMP;
	      l++;
	      (*out).sap[l] = (sap-sap_prev)*(sap_time-sap_time_prev) /
		(sap_time_next-sap_time_prev)+sap_prev;
	    }  
	  sap_time += T_SAMP;
	  l++;
	}


      // Compute the diastolic pressure numerics with granularity T_SAMP.
      if ((dap_time_next > (*theta).vec[93]-T_BASELINE) && (m < N_SAMPLES))
	{
	  (*out).dap[m] = (dap-dap_prev)*(dap_time-dap_time_prev) /
	    (dap_time_next-dap_time_prev)+dap_prev;
	  while (((dap_time_next - dap_time) > T_SAMP) && ((m+1) < N_SAMPLES))
	    {
	      dap_time += T_SAMP;
	      m++;
	      (*out).dap[m] = (dap-dap_prev)*(dap_time-dap_time_prev) /
		(dap_time_next-dap_time_prev)+dap_prev;
	    }  
	  dap_time += T_SAMP;
	  m++;
	}


      // Compute the mean pressure and stroke volume numerics with granularity
      // T_SAMP. The value representing each beat is assigned to the midpoint
      // of the beat.
      if ((map_time_next > (*theta).vec[93]-T_BASELINE) && (n < N_SAMPLES))
	{
	  (*out).map[n] = (map - map_prev)*(map_time-map_time_prev) /
	    (map_time_next-map_time_prev)+map_prev;
	  (*out).cvp[n] = (cvp - cvp_prev)*(map_time-map_time_prev) /
	    (map_time_next-map_time_prev)+cvp_prev;
	  (*out).sv[n] = (sv - sv_prev)*(map_time-map_time_prev) /
	    (map_time_next-map_time_prev)+sv_prev;
	  while (((map_time_next - map_time) > T_SAMP) && ((n+1) < N_SAMPLES))
	    {
	      map_time += T_SAMP;
	      n++;
	      (*out).map[n] = (map-map_prev)*(map_time-map_time_prev) /
		(map_time_next-map_time_prev)+map_prev;
	      (*out).cvp[n] = (cvp-cvp_prev)*(map_time-map_time_prev) /
		(map_time_next-map_time_prev)+cvp_prev;
	      (*out).sv[n] = (sv-sv_prev)*(map_time-map_time_prev) /
		(map_time_next-map_time_prev)+sv_prev;
	    }  
	  map_time += T_SAMP;
	  n++;
	}


      // Start search for onset of next beat.
      tmp_map = (*p).x[0]*dt;
      tmp_cvp = ((*p).x[14]-(*theta).vec[31])*dt;
      tmp_sv = (*p).q[0]*dt;

      tmp_dap = 500.0; tmp_sap = 0.0;
      cum_dt = 0.0;
      onset = (*p).time[0];

      dap_time_prev = dap_time_next;
      sap_time_prev = sap_time_next;
    }

  // Integrate the blood pressure and left ventricular outflow signals; also
  // detect highest and lowest values in blood pressure signal for systolic and
  // diastolic pressure detection along with the time of occurrence.
  else
    {
      current_time = (*p).time[0];
      dt = current_time - prev_time;

      tmp_map += (*p).x[0]*dt;
      tmp_cvp += ((*p).x[14]-(*theta).vec[31])*dt;
      tmp_sv  += (*p).q[0]*dt;

      if ((*p).x[0] < tmp_dap) 
	{
	  tmp_dap = (*p).x[0];
	  dap_time_next = current_time;
	}

      if ((*p).x[0] > tmp_sap)
	{
	  tmp_sap = (*p).x[0];
	  sap_time_next = current_time;
	  tmp_dap = 500.0;
	}
    }

  // Update timing variables.
  cum_dt += current_time - prev_time;
  prev_time = current_time;

}






void numerics_new_ptr(Data_vector *p, Reflex_vector *r, double hdid, int N_OUT, double *out_vec)
{
  static double abp_sys  = 0.0, abp_mean = 0.0, abp_dias = 0.0;
  static double lvp_sys  = 0.0, lvp_ed = 0.0, lvp_md = 0.0;
  static double rvp_sys  = 0.0, rvp_ed = 0.0, rvp_md = 0.0;
  static double right_atrium = 0.0, left_atrium = 0.0, periph_vp = 0.0;
  static double pap_sys  = 0.0, pap_mean = 0.0, pap_dias = 0.0;
  static double pvp_max  = 0.0, pvp_mean = 0.0, pvp_min = 0.0;
  static double sv = 0.0;

  static double dtime = 1.0;

  // Initialize static variables between different calls to simulator()
  if (p->time[0] < 1.0e-6) {
    abp_sys = abp_mean = 0.0;
    lvp_sys = lvp_ed = 0.0;
    rvp_sys = rvp_ed = 0.0;
    right_atrium = left_atrium = periph_vp = 0.0;
    pap_sys  = pap_mean = 0.0;
    sv = 0.0;

    abp_dias = lvp_md = rvp_md = pap_dias = 5000.0;
  }

  if (r->step_cnt == 1) {
    out_vec[0] = abp_sys;
    out_vec[1] = abp_mean/dtime;
    out_vec[2] = abp_dias;

    out_vec[3] = lvp_sys;
    out_vec[4] = lvp_ed;
    out_vec[5] = lvp_md;

    out_vec[6] = rvp_sys;
    out_vec[7] = rvp_ed;
    out_vec[8] = rvp_md;

    out_vec[9]  = right_atrium / dtime;
    out_vec[10] = left_atrium / dtime;
    out_vec[11] = periph_vp / dtime;

    out_vec[12] = pap_sys;
    out_vec[13] = pap_mean / dtime;
    out_vec[14] = pap_dias;

    out_vec[15] = sv;
    out_vec[16] = sv*r->hr[2];
    out_vec[17] = r->hr[2];

    out_vec[18] = pvp_max; 
    out_vec[19] = pvp_mean / dtime;
    out_vec[20] = pvp_min;

    pap_sys = pap_mean = 0.0;
    abp_sys = abp_mean = 0.0;
    lvp_sys = lvp_ed = 0.0;
    rvp_sys = rvp_ed = 0.0;
    pvp_max = pvp_mean = 0.0;
    sv = 0.0;
    left_atrium = right_atrium = periph_vp = 0.0;

    abp_dias = lvp_md = rvp_md = pap_dias = pvp_min = 5000.0;
    dtime = 0.0;
  }
  else {
    // Systemic arterial pressure
    if (p->x[0] > abp_sys) abp_sys = p->x[0];
    if (p->x[0] < abp_dias) abp_dias = p->x[0];
    abp_mean += p->x[0]*hdid;

    // Left ventricular pressure
    if (p->x[20] > lvp_sys) lvp_sys = p->x[20];
    if (p->x[20] < lvp_md) lvp_md = p->x[20];
    if ((r->step_cnt < 200) && (p->dxdt[20] < 20.0))
      lvp_ed = p->x[20];

    // Right ventricular pressure
    if (p->x[16] > rvp_sys) rvp_sys = p->x[16];
    if (p->x[16] < rvp_md) rvp_md = p->x[16];
    if ((r->step_cnt < 200) && (p->dxdt[16] < 20.0))
      rvp_ed = p->x[16];

    // Mean right and left atrial pressures and mean peripheral venous pressure
    right_atrium += p->x[15]*hdid;
    left_atrium  += p->x[19]*hdid;
    periph_vp    += p->x[3]*hdid;

    // Pulmonary artery pressure
    if (p->x[17] > pap_sys) pap_sys = p->x[17];
    if (p->x[17] < pap_dias) pap_dias = p->x[17];
    pap_mean += p->x[17]*hdid;

    // Pulmonary artery pressure
    if (p->x[18] > pvp_max) pvp_max = p->x[18];
    if (p->x[18] < pvp_min) pvp_min = p->x[18];
    pvp_mean += p->x[18]*hdid;

    // Stroke volume
    sv += p->q[23]*hdid;

    dtime += hdid;
  }
}
