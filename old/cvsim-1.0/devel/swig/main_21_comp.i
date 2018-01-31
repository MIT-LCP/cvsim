/* main_21_comp.i */

%module main

%{
#include "../C/21_comp_backend/main/main.h"
#include "../C/21_comp_backend/main/main_java.h"
%}

%include "../C/21_comp_backend/main/main.h"
%include "../C/21_comp_backend/main/main_java.h"


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

   // setters
   double setVec(int i, double value) {
     self->vec[i] = value;
   }
}


/* Functions */
void updatePressure(int i, double value);
void updateGravityVector(int i, double value);
void init_sim(Parameter_vector *a);
void step_sim(output *stepout, Parameter_vector *a, int dataCompressionFactor, 
	      int ABReflexOn, int CPReflexOn, 
	      int tiltTestOn, double tiltStartTime, double tiltStopTime);
void reset_sim();
