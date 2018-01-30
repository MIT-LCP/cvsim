#!/bin/sh

# Get rid of all the SWIG-generated files and class files

SWIG_DIR=/home/cldunn/cvsim/trunk/devel/swig

JAVA_CLASS_DIR=/home/cldunn/cvsim/trunk/devel/java/classes

LIB_DIR=/home/cldunn/cvsim/trunk/devel/lib

rm -f $JAVA_CLASS_DIR/*.class
rm -f $JAVA_CLASS_DIR/edu/mit/lcp/*.class
rm -f $LIB_DIR/libmain.so
rm -f $SWIG_DIR/main_wrap.c
rm -f *.o
rm -f SWIG*
rm -f main.java
rm -f mainJNI.java
rm -f Parameter_vector.java
rm -f mainConstants.java
rm -f Hemo*
rm -f Cardiac*
rm -f Micro_r.java
rm -f System_parameters.java
rm -f Reflex.java
rm -f Timing.java
rm -f Output_vector.java
rm -f Impulse_vector.java
rm -f Data_vector.java
rm -f Tilt_vector.java
rm -f sim_output.java
rm -f Track_data.java
rm -f *~
rm -f output.java
rm -f reflex.java
rm -f cardiac.java
rm -f hemo.java
rm -rf State_vector.java
rm -rf Reflex_vector.java
rm -rf cStructs.java
rm -rf Outputs.java
