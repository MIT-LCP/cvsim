/* main_6_comp.i */

%module main

%{
#include "../C/6_comp_backend/main/main.h"
#include "../C/6_comp_backend/main/main_java.h"
%}

%include "../C/6_comp_backend/main/main.h"
%include "../C/6_comp_backend/main/main_java.h"


/* Write getter and setter for Parameter_vector, a struct 
 * containing an array of simulation input parameters. SWIG 
 * can't  automatically generate getters and setters for 
 * arrays within structs.
 */	
%extend Parameter_vector {
  
   // getters
   double getVec(int i) {
      return self->vec[i];
   }

}


/* Functions */
void updatePressure(int i, double value);
void init_sim(Parameter_vector *a);
void step_sim(output *stepout, Parameter_vector *a, int dataCompressionFactor, 
		int ABReflexOn, int CPReflexOn);
void reset_sim();

/* Parameter update constraint functions */
void updateTotalBloodVolume(double tbv_new, Parameter_vector *a);
void updateTotalZeroPressureFillingVolume(double zpfv_new, Parameter_vector *a);
void updateIntrathoracicPressure(double Pth_new, Parameter_vector *a);
void updatePulmonaryArterialCompliance(double C_new, Parameter_vector *a);
void updatePulmonaryVenousCompliance(double C_new, Parameter_vector *a);
void updateArterialCompliance(double C_new, Parameter_vector *a);
void updateVenousCompliance(double C_new, Parameter_vector *a);
void updateParameter(double newValue, Parameter_vector *a, int index);
