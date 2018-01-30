#!/bin/sh

# Script to compile the GUI
# Compiles the C, SWIG, and Java code

JAVA_CLASS_DIR=java/classes
LIB_DIR=lib
SWIG_DIR=swig

JAVA_SRC_DIR=java/src/edu/mit/lcp/C6_comp_backend
C_DIR=C/6_comp_backend/
SWIG_FILE=$SWIG_DIR/main_6_comp.i


/opt/swig-1.3.29/bin/swig -java -package edu.mit.lcp.C6_comp_backend -outdir $JAVA_SRC_DIR $SWIG_FILE

gcc -O2 -fno-strict-aliasing -Wall -c -fno-common $C_DIR/sim/equation.c $C_DIR/sim/reflex.c $C_DIR/sim/simulator.c $C_DIR/sim/estimate.c $C_DIR/sim/rkqc.c $C_DIR/main/initial.c $C_DIR/main/main.c $C_DIR/main/main_java.c $C_DIR/main/turning.c $SWIG_DIR/main_6_comp_wrap.c -I/System/Library/Frameworks/JavaVM.framework/Headers/

gcc -O2 -fno-strict-aliasing -Wall -dynamiclib -framework JavaVM equation.o reflex.o simulator.o estimate.o rkqc.o initial.o main.o main_6_comp_wrap.o main_java.o turning.o -o $LIB_DIR/libC6_comp_backend.jnilib


JAVA_SRC_DIR=java/src/edu/mit/lcp/C21_comp_backend
C_DIR=C/21_comp_backend_2
SWIG_FILE=$SWIG_DIR/main_21_comp.i

/opt/swig-1.3.29/bin/swig -java -package edu.mit.lcp.C21_comp_backend -outdir $JAVA_SRC_DIR $SWIG_FILE

gcc -O2 -fno-strict-aliasing -Wall -c -fno-common $C_DIR/sim/equation.c $C_DIR/sim/reflex.c $C_DIR/sim/simulator.c $C_DIR/sim/estimate.c $C_DIR/sim/rkqc.c $C_DIR/main/initial.c $C_DIR/main/main.c $C_DIR/main/main_java.c $C_DIR/main/turning.c $SWIG_DIR/main_21_comp_wrap.c -I/System/Library/Frameworks/JavaVM.framework/Headers/

gcc -O2 -fno-strict-aliasing -dynamiclib -framework JavaVM equation.o reflex.o simulator.o estimate.o rkqc.o initial.o main.o main_21_comp_wrap.o main_java.o turning.o -o $LIB_DIR/libC21_comp_backend.jnilib


make -C java/
