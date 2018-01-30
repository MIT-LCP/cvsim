/* This files contains the subroutines for the reflex model as well as the 
 * IPFM model of the cardiac pacemaker. Sanode() computes the onset time
 * of the next cardiac contraction based on the history of the autonomic 
 * efferent (instantaneous) heart rate signal which is taken to be a measure
 * of autonomic activity. MakeImp() sets up the sympathetic and parasympathetic
 * impulse response functions. This routine is called once at the beginning of 
 * the simulator routine. Queue() is the main reflex routine. Here the blood
 * pressure variables are averaged and stored, the convolution integrals are
 * computed and the effector variables are computed and updated. Queue_reset()
 * resets the variables of queue() that have been defined as static variables.
 *
 * Thomas Heldt    March 13th, 2002
 * Last modified   June 14, 2006
 */
#include "reflex.h"

#define PI M_PI
#define I_LENGTH 960   // Signal history array size = 60 s / 0.0625 s
#define S_GRAN 0.0625  // Signal history bin size
#define S_INT  0.25    // Averaging interval chosen such that 4 bins fit into
                       // one interval of size S_INT
#define S_LENGTH 4     // Number of bins of size S_GRAN to fit into averaging
                       // interval of length S_INT

/* The following subroutine is the implementation of the Integral Pulse 
 * Frequency Modulation (IPFM) model of the cardiac pacemaker (also known as
 * Integrate To Threshold (ITT) model). Sanode() reads various timing
 * information from the data vector and updates the variable cardiac_time.
 * The history of the autonomic input to the pacemaker is stored in r.hr[2] of
 * the reflex vector.
 */
double sanode(Data_vector *p, Reflex_vector *r, Parameter_vector *theta, double dt) 
{
  double cum_hr = (*r).hr[1];
  double a_time = (*p).time[1];
  double v_time = (*p).time[6];

  double a_time_old = (*p).time_new[0];
  double v_time_new = (*p).time_new[5];

  double PR_new = (*p).time_new[2];
  double PR_old = (*p).time[2];
  double Tv_old = (*p).time[4];


  double f_old = 0.0, f_new = 0.0;       // temp storage for IPFM integrants
  double t_onset = 0.0;                  // stores RR-interval (cardiac time)
  double hr = (*r).hr[0];                // afferent HR signal from
                                         // baroreceptors
  int n = (*r).step_cnt;                 // number of integration steps

  // The following two lines define the values of the IPFM integral before
  // (f_old) and after (f_new) a time step of size dt is taken.
  f_old = cum_hr*a_time / n;    
  f_new = (cum_hr + hr) * (a_time + dt) / (n+1);

  //  printf("%e %e %e %e %d\n", (*p).time[0], f_old, f_new, t_onset, n);

  // The following IF-statement determines whether or not a new beat should
  // be initiated. It does so only if the current value of the IPFM integral
  // exceeds the value of 60 AND IF the absolute refractory period of 1.2
  // times the ventricular contraction time has passed since the onset of 
  // the ventricular cardiac contraction.
  if (f_new >= 60.0) {
    // The following lines compute the time in the cardiac cycle at 
    // which the IPFM integral hits the threshold value of 60.0.
    if (f_old <= 60.0)
      t_onset = a_time + dt * (60.0 - f_old) / (f_new - f_old);
    else t_onset = a_time + dt;

    // Update the cardiac timing variables for the next beat to be taken.
    p -> time_new[2] = (*theta).vec[118]*sqrt(t_onset);  // PR-interval
    p -> time_new[3] = (*theta).vec[119]*sqrt(t_onset);  // atrial systole
    p -> time_new[4] = (*theta).vec[120]*sqrt(t_onset);  // ventricular systole
  }

  // Determine whether a new atrial or ventricular contraction should be
  // started. A new atrial contraction is initiated by re-setting the time in
  // the cardiac cycle, setting the number of time steps to 1, and re-setting
  // the cummulative heart rate signal. A new ventricular contraction is
  // initiated by re-setting the ventricular time.

  // Check whether atrial and ventricular contractions are completed.
  if ((f_new >= 60.0) && ((a_time - PR_old) > 1.5*Tv_old)) {
    a_time = t_onset-a_time;
    a_time_old = t_onset-a_time;
    v_time_new = v_time = a_time - PR_new;

    // Update the cardiac timing variables for the next beat to be taken.
    p -> time[2] = (*theta).vec[118]*sqrt(t_onset);  // PR-interval
    p -> time[3] = (*theta).vec[119]*sqrt(t_onset);  // atrial systole
    p -> time[4] = (*theta).vec[120]*sqrt(t_onset);  // ventricular systole

    // Update the contractility feedback.
    p -> c[4] = (*r).compliance[0];
    p -> c[5] = (*r).compliance[1];

    r -> hr[2] = 60./t_onset;
    cum_hr = 60./t_onset;
    n = 1;
  }
  // Check whether ventricular contraction is sufficiently completed for a 
  // new atrial contraction to be initiated.
  else if ((f_new >= 60.0) && ((a_time - PR_old + PR_new) > 1.5*Tv_old)) {
    a_time = t_onset-a_time;
    v_time_new = a_time - PR_new;
    a_time_old += dt;
    v_time += dt;

    // Update the atrial systolic time interval.
    p -> time[3] = (*p).time_new[3];  // atrial systole

    r -> hr[2] = 60./t_onset;
    cum_hr = 60./t_onset;
    n = 1;
  }

  // Update the timing information if no new beat is to be initiated.
  else {
    a_time_old += dt;
    a_time += dt;
    v_time_new += dt;

    if ((a_time_old - PR_old) > 1.5*Tv_old) {
      v_time = v_time_new;


      // Update the PR delay, the ventricular systolic interval, and the
      // contractility feedback.
      p -> time[2] = (*p).time_new[2];  // PR-interval
      p -> time[4] = (*p).time_new[4];  // ventricular systole
      p -> c[4] = (*r).compliance[0];
      p -> c[5] = (*r).compliance[1];
      a_time_old = a_time;
    }
    else
      v_time += dt;

    // Updates cummulative heart rate signal, time step counter, and cardiac
    // time if no new beat is initiated.
    cum_hr += (*r).hr[0];
    n++;              
  }

  //  printf("%e %e %e %e %e\n", (*p).time[0], a_time, a_time_old, v_time, v_time_new);

  p -> time_new[0] = a_time_old;
  p -> time_new[5] = v_time_new;
  p -> time[6] = v_time;
  p -> time[5] = (*p).time[0] + dt;
  r -> hr[1] = cum_hr;                // Write new cummulative heart rate and 
  r -> step_cnt = n;                  // step counter to reflex vector.

  return a_time;
} /* End sanode() */


/* makeImp reads the parameter vector and returns the coefficients of the
 * impulse response function in the structure imp. It is called once at the 
 * beginning of the simulator.c routine. The impulse response functions are
 * implemented an a time-advanced manner by 3.0*S_Gran to take care of time
 * delays encountered by the signal averaging procedure (2.0*S_GRAN; see below)
 * and to allow for linear interpolation between successive convolutions 
 * (1.0*S_GRAN).
 */
void makeImp(Impulse_vector *imp, Parameter_vector *theta)
{
  int i = 0;
  int start = 0, peak = 0, end = 0;   // derived indices for start, peak,
                                      // and end of impulse response functions
  double sum = 0.0;                   // local normalization variable


  for (i=0; i < I_LENGTH; i++)  // initialize the imp. response arrays to zero
    (*imp).s[i] = (*imp).p[i] = (*imp).a[i] = (*imp).v[i] = 0.0;
  
  // Set up parasympathetic impulse response function
  start = (int) rint(((*theta).vec[25] - 3.0*S_GRAN) / S_GRAN);  
  peak  = (int) rint(((*theta).vec[26] - 3.0*S_GRAN) / S_GRAN);
  end   = (int) rint(((*theta).vec[27] - 3.0*S_GRAN) / S_GRAN);

  for (i = start; i < peak; i++) 
    sum += (*imp).p[i] = (double) (i - start) / (peak-start);
  for (i = peak; i <= end; i++) 
    sum += (*imp).p[i] = (double) (end - i)/(end - peak);
  for (i = start; i <= end; i++)
    (*imp).p[i] /= sum;    // normalizing to required total sum 
                           // (analogous to area in continuous time)


  // Do the same for the beta-sympathetic impulse response function.
  start = (int) rint(((*theta).vec[28] - 3.0*S_GRAN) / S_GRAN);  
  peak  = (int) rint(((*theta).vec[29] - 3.0*S_GRAN) / S_GRAN);
  end   = (int) rint(((*theta).vec[30] - 3.0*S_GRAN) / S_GRAN);

  sum   = 0.0;
  for (i = start; i < peak; i++) 
    sum += (*imp).s[i] = (double) (i-start) / (peak-start);
  for (i = peak; i <= end; i++) 
    sum += (*imp).s[i] = (double) (end-i) / (end-peak);
  for (i = start; i <= end; i++)
    (*imp).s[i] /= sum;    // normalizing to required total sum 
                           // (analogous to area in continuous time)


  // Do the same for the venous alpha-sympathetic impulse response function.
  start = (int) rint(((*theta).vec[112] - 3.0*S_GRAN) / S_GRAN);  
  peak  = (int) rint(((*theta).vec[113] - 3.0*S_GRAN) / S_GRAN);
  end   = (int) rint(((*theta).vec[114] - 3.0*S_GRAN) / S_GRAN);

  sum   = 0.0;
  for (i = start; i < peak; i++) 
    sum += (*imp).v[i] = (double) (i-start) / (peak-start);
  for (i = peak; i <= end; i++) 
    sum += (*imp).v[i] = (double) (end-i) / (end-peak);
  for (i = start; i <= end; i++)
    (*imp).v[i] /= sum;    // normalizing to required total sum 
                           // (analogous to area in continuous time)


  // Do the same for the arterial alpha-sympathetic impulse response function.
  start = (int) rint(((*theta).vec[115] - 3.0*S_GRAN) / S_GRAN);  
  peak  = (int) rint(((*theta).vec[116] - 3.0*S_GRAN) / S_GRAN);
  end   = (int) rint(((*theta).vec[117] - 3.0*S_GRAN) / S_GRAN);

  sum   = 0.0;
  for (i = start; i < peak; i++) 
    sum += (*imp).a[i] = (double) (i-start) / (peak-start);
  for (i = peak; i <= end; i++) 
    sum += (*imp).a[i] = (double) (end-i) / (end-peak);
  for (i = start; i <= end; i++)
    (*imp).a[i] /= sum;    // normalizing to required total sum 
                           // (analogous to area in continuous time)

  // Do the same for the cardiopulmonary to venous tone alpha-sympathetic
  // impulse response function.
  start = (int) rint(((*theta).vec[147] - 3.0*S_GRAN) / S_GRAN);  
  peak  = (int) rint(((*theta).vec[148] - 3.0*S_GRAN) / S_GRAN);
  end   = (int) rint(((*theta).vec[149] - 3.0*S_GRAN) / S_GRAN);

  sum   = 0.0;
  for (i = start; i < peak; i++) 
    sum += (*imp).cpv[i] = (double) (i-start) / (peak-start);
  for (i = peak; i <= end; i++) 
    sum += (*imp).cpv[i] = (double) (end-i) / (end-peak);
  for (i = start; i <= end; i++)
    (*imp).cpv[i] /= sum;    // normalizing to required total sum 
                             // (analogous to area in continuous time)


  // Do the same for the cardiopulmonary to arterial resistance
  // alpha-sympathetic impulse response function.
  start = (int) rint(((*theta).vec[150] - 3.0*S_GRAN) / S_GRAN);  
  peak  = (int) rint(((*theta).vec[151] - 3.0*S_GRAN) / S_GRAN);
  end   = (int) rint(((*theta).vec[152] - 3.0*S_GRAN) / S_GRAN);

  sum   = 0.0;
  for (i = start; i < peak; i++) 
    sum += (*imp).cpa[i] = (double) (i-start) / (peak-start);
  for (i = peak; i <= end; i++) 
    sum += (*imp).cpa[i] = (double) (end-i) / (end-peak);
  for (i = start; i <= end; i++)
    (*imp).cpa[i] /= sum;    // normalizing to required total sum 
                             // (analogous to area in continuous time)

}


/* The following variables are defined here as static variables to confine
 * their scope to the remainder of this file. They are defined outside the 
 * queue() routine such that the routine queue_reset() can be used to 
 * reset them to their initial values upon exit of the simulator routine.
 */
static double S_cum_dt = 0.0;               // signal time integral  
static double I_cum_dt = 0.0;               // convolution time integral  
static double S_cum_abp = 0.0;              // arterial pressure integral
static double S_cum_rap = 0.0;              // rap integral
static double I_cum_abp = 0.0;              // average arterial pressure
static double I_cum_rap = 0.0;              // average rap integral
static int S_top = 0;                       // blood pressure queue index
static int I_top = 0;                       // blood pressure queue index
static double abp_hist[I_LENGTH] = {0.0};   // abp and rap signal queues
static double rap_hist[I_LENGTH] = {0.0};
static double abp_bins[S_LENGTH] = {0.0};   // abp and rap bins
static double rap_bins[S_LENGTH] = {0.0};

static double alpha_resp_new = 0.0, alpha_resp_old = 0.0;
static double alphav_resp_new = 0.0, alphav_resp_old = 0.0;
static double para_resp_new = 0.0, para_resp_old = 0.0;
static double beta_resp_new = 0.0, beta_resp_old = 0.0;

static double alpha_respv_new = 0.0, alpha_respv_old = 0.0;
static double alphav_respv_new = 0.0, alphav_respv_old = 0.0;


/* queue() is the main reflex routine. It computes the time-averaged pressures,
 * performes the convolutions, and updates the effector variables. It does so
 * by first averaging the pressure signals in bins of size S_GRAN. A running
 * average over S_LENGTH bins returns the final averaged pressure values which
 * are then used from computation of the convolution integral. The effector 
 * variables are updated at every integration step through linear interpolation
 * bewteen the respective values determined by the convolution integral.
 */
void queue_ptr(Data_vector *p, Impulse_vector *imp, Reflex_vector *r,
		  Parameter_vector *theta, double dt, int ABReflexOn,
		  int CPReflexOn)
{
  double s_abp = 0.0, s_rap = 0.0;             // sensed pressures

  // variables for the arterial and cardiopulmonary convolution integrals
  double alpha_resp = 0.0, alphav_resp = 0.0;
  double para_resp = 0.0, beta_resp = 0.0;
  double alpha_respv = 0.0, alphav_respv = 0.0;

  // variables for the effector mechanisms (venous tone and heart rate)
  double Vold = 0.0;
  double I = 0.0;

  int i = 0;

  // Compute the running integral of arterial and right atrial pressures by
  // Backward Euler over an interval of length S_GRAN. 
  // Compute the average over the first S_GRAN seconds of simulation time.
  if (p->time[0] < S_INT) {
    if ((S_cum_dt+dt) < S_GRAN) {
      S_cum_dt  += dt;

      S_cum_rap += (p->x[2]-p->x[24])*dt;
      S_cum_abp += p->x[1]*dt;
    }
    else {
      // Account for the fraction of the last time step that fell within
      // the averaging interval - again: Backward Euler integration.

      S_cum_rap += (p->x[2]-p->x[24])*(S_GRAN - S_cum_dt);
      S_cum_abp +=  p->x[1]*(S_GRAN - S_cum_dt);

      // Update the averaging bins by wrap-around modulus arithmetic.
      S_top = (S_top + S_LENGTH - 1) % S_LENGTH;
      abp_bins[S_top] = S_cum_abp/S_GRAN;
      rap_bins[S_top] = S_cum_rap/S_GRAN;

      I_cum_abp = I_cum_rap = 0.0;
      for (i=0; i < S_LENGTH; i++) {
	I_cum_abp += abp_bins[i];
	I_cum_rap += rap_bins[i];
      }

      I_cum_abp /= S_LENGTH;
      I_cum_rap /= S_LENGTH;

      s_abp = atan((I_cum_abp-(*theta).vec[0]) / (*theta).vec[1])
	* (*theta).vec[1];
      s_rap = atan((I_cum_rap-(*theta).vec[2]) / (*theta).vec[16])
	* (*theta).vec[16];

      // Wrap-around modulus arithmetic to update the pressure histories.
      I_top = (I_top + I_LENGTH - 1) % I_LENGTH;
      abp_hist[I_top] = s_abp;
      rap_hist[I_top] = s_rap;

      // Account for the fraction of the last time step that fell outside
      // the previous averaging interval - again: Backward Euler
      // integration.
      S_cum_dt = S_cum_dt + dt - S_GRAN;

      S_cum_rap = (p->x[2]-p->x[24])*S_cum_dt;
      S_cum_abp =  p->x[1]*S_cum_dt;
    }
  }

  // Compute the reflex response for the remainder of the simulation time.
  else {
    if ((S_cum_dt+dt) < S_GRAN) {
      S_cum_dt  += dt;

      S_cum_rap += (p->x[2]-p->x[24])*dt;
      S_cum_abp +=  p->x[1]*dt;
    }
    else {
      // Account for the fraction of the last time step that fell within
      // the averaging interval - again: Backward Euler integration.

      S_cum_rap += (p->x[2]-p->x[24])*(S_GRAN - S_cum_dt);
      S_cum_abp +=  p->x[1]*(S_GRAN - S_cum_dt);

      // Update the averaging bins by wrap-around modulus arithmetic.
      S_top = (S_top + S_LENGTH - 1) % S_LENGTH;
      abp_bins[S_top] = S_cum_abp/S_GRAN;
      rap_bins[S_top] = S_cum_rap/S_GRAN;

      I_cum_abp = I_cum_rap = 0.0;
      for (i=0; i < S_LENGTH; i++) {
	I_cum_abp += abp_bins[i];
	I_cum_rap += rap_bins[i];
      }

      I_cum_abp /= S_LENGTH;
      I_cum_rap /= S_LENGTH;

      s_abp = atan((I_cum_abp-(*theta).vec[0]) / (*theta).vec[1])
	* (*theta).vec[1];
      s_rap = atan((I_cum_rap-(*theta).vec[2]) / (*theta).vec[16])
	* (*theta).vec[16];

      // Wrap-around modulus arithmetic to update the pressure histories.
      I_top = (I_top + I_LENGTH - 1) % I_LENGTH;
      abp_hist[I_top] = s_abp;
      rap_hist[I_top] = s_rap;

      // Do the convolution integrals
      for (i=0; i<I_LENGTH; i++) {
	para_resp    += (*imp).p[i] * abp_hist[(I_top+i) % I_LENGTH];
	beta_resp    += (*imp).s[i] * abp_hist[(I_top+i) % I_LENGTH];
	alpha_resp   += (*imp).a[i] * abp_hist[(I_top+i) % I_LENGTH];
	alpha_respv  += (*imp).v[i] * abp_hist[(I_top+i) % I_LENGTH];
	alphav_resp  += (*imp).cpa[i] * rap_hist[(I_top+i) % I_LENGTH];
	alphav_respv += (*imp).cpv[i] * rap_hist[(I_top+i) % I_LENGTH];
      }

      para_resp_old = para_resp_new; para_resp_new = para_resp;
      beta_resp_old = beta_resp_new; beta_resp_new = beta_resp;
      alpha_resp_old = alpha_resp_new; alpha_resp_new = alpha_resp;
      alphav_resp_old = alphav_resp_new; alphav_resp_new = alphav_resp;
	  
      alpha_respv_old = alpha_respv_new; alpha_respv_new = alpha_respv;
      alphav_respv_old = alphav_respv_new; alphav_respv_new = alphav_respv;

      // Account for the fraction of the last time step that fell outside
      // the previous averaging interval - again: Backward Euler
      // integration.
      S_cum_dt = S_cum_dt + dt - S_GRAN;

      S_cum_rap = (p->x[2]-p->x[24])*S_cum_dt;
      S_cum_abp =  p->x[1]*S_cum_dt;
    }
  }

  // Linear interpolation between successive reflex system updates.

  para_resp = 0.0;
  beta_resp = 0.0;
  alpha_resp = 0.0;
  alphav_resp = 0.0;
  alpha_respv = 0.0;
  alphav_respv = 0.0;
  
  // if the arterial baroreflex control system is on...
  if (ABReflexOn) {
    para_resp = (para_resp_new-para_resp_old)*S_cum_dt/S_GRAN+para_resp_old;
    beta_resp = (beta_resp_new-beta_resp_old)*S_cum_dt/S_GRAN+beta_resp_old;
    alpha_resp = (alpha_resp_new-alpha_resp_old)*S_cum_dt/S_GRAN+alpha_resp_old;
    alpha_respv = (alpha_respv_new-alpha_respv_old)*S_cum_dt/S_GRAN + alpha_respv_old;
  }

  // if the cardiopulmonary reflex control system is on...
  if (CPReflexOn) {
    alphav_resp = (alphav_resp_new-alphav_resp_old)*S_cum_dt/S_GRAN + alphav_resp_old;
    alphav_respv = (alphav_respv_new-alphav_respv_old)*S_cum_dt/S_GRAN + alphav_respv_old;
  }
  

  // Heart rate control
  I = 60./(*theta).vec[90] + 
    ((*theta).vec[2] * beta_resp + (*theta).vec[3] * para_resp);
  r -> hr[0] = 60. / I;

  // Contractility feedback. Limit contractility feedback so end-systolic
  // ventricular elastances do not become too large during severe stress.
  // Index-0 represents right ventricle; index-1 is the left ventricle.
  (*r).compliance[0] = (*theta).vec[46] + (*theta).vec[12]*beta_resp;
  (*r).compliance[0] = ((*r).compliance[0] > 0.3 ? (*r).compliance[0] : 0.3);

  (*r).compliance[1] = (*theta).vec[52] + (*theta).vec[13]*beta_resp; 
  (*r).compliance[1] = ((*r).compliance[1] > 0.01 ? (*r).compliance[1] : 0.01);

  // Peripheral resistance feedback
  (*r).resistance[0]  = (*theta).vec[156] + (*theta).vec[158]*alpha_resp + 
    (*theta).vec[159]*alphav_resp;

  // Venous tone feedback implementation. First, we compute the amount of 
  // blood stored in the non-linear compartments, then we compute the 
  // new zero pressure filling volumes and update the pressures
  // accordingly.
  Vold = (*r).volume[0];

  (*r).volume[0] = (*theta).vec[78] +(*theta).vec[79] +(*theta).vec[80] +
    (*theta).vec[81] + (*theta).vec[82] + (*theta).vec[83] +
    (*theta).vec[160]*alpha_respv + (*theta).vec[161]*alphav_respv;

  p->x[2] += (Vold - (*r).volume[0]) / (*theta).vec[154];
}


/* The following routine resets some of the static variables in the queue
 * routine to their initial values. This is required when multiple simulations
 * are being run in sequence - as is the case with the computation of the 
 * gradient matrix.
 */
int queue_reset(void)
{
  int i = 0;

  S_cum_dt = 0.0; 
  I_cum_dt = 0.0; 
  S_cum_abp = 0.0;
  S_cum_rap = 0.0;
  I_cum_abp = 0.0;
  I_cum_rap = 0.0;
  S_top = 0;
  I_top = 0;

  alpha_resp_new = alpha_resp_old = 0.0;
  alphav_resp_new = alphav_resp_old = 0.0;
  beta_resp_new = beta_resp_old = 0.0;
  para_resp_new = para_resp_old = 0.0;
  alpha_respv_new = alpha_respv_old = 0.0;
  alphav_respv_new = alphav_respv_old = 0.0;


  for (i=0; i<I_LENGTH; i++) {
    abp_hist[i] = 0.0;
    rap_hist[i] = 0.0;
  }

  for (i=0; i<S_LENGTH; i++) {
    abp_bins[i] = 0.0;
    rap_bins[i] = 0.0;
  }

  return 0;
}

#undef PI
#undef S_GRAN
#undef I_LENGTH
#undef S_LENGTH
#undef S_INT
