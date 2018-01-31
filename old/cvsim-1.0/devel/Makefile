# import paths
include Makefile.include.root

6_COMP_SIM_DIR		= C/6_comp_backend
21_COMP_SIM_DIR		= C/21_comp_backend

all:

	$(SWIG) -java -package edu.mit.lcp.C6_comp_backend \
	        -outdir java/src/edu/mit/lcp/C6_comp_backend \
		swig/main_6_comp.i

	$(CC) $(CFLAGS) -c \
	$(6_COMP_SIM_DIR)/sim/equation.c \
	$(6_COMP_SIM_DIR)/sim/reflex.c \
	$(6_COMP_SIM_DIR)/sim/simulator.c \
	$(6_COMP_SIM_DIR)/sim/estimate.c \
	$(6_COMP_SIM_DIR)/sim/rkqc.c \
	$(6_COMP_SIM_DIR)/main/initial.c \
	$(6_COMP_SIM_DIR)/main/main.c \
	$(6_COMP_SIM_DIR)/main/main_java.c \
	$(6_COMP_SIM_DIR)/main/turning.c \
	swig/main_6_comp_wrap.c	

	$(BUILDLIB) -o $(LIB6) \
	equation.o \
	reflex.o \
	simulator.o \
	estimate.o \
	rkqc.o \
	initial.o \
	main.o \
	main_java.o \
	turning.o \
	main_6_comp_wrap.o

	$(SWIG) -java -package edu.mit.lcp.C21_comp_backend \
	        -outdir java/src/edu/mit/lcp/C21_comp_backend \
		swig/main_21_comp.i

	$(CC) $(CFLAGS) -c \
	$(21_COMP_SIM_DIR)/sim/equation.c \
	$(21_COMP_SIM_DIR)/sim/reflex.c \
	$(21_COMP_SIM_DIR)/sim/simulator.c \
	$(21_COMP_SIM_DIR)/sim/estimate.c \
	$(21_COMP_SIM_DIR)/sim/rkqc.c \
	$(21_COMP_SIM_DIR)/main/initial.c \
	$(21_COMP_SIM_DIR)/main/main.c \
	$(21_COMP_SIM_DIR)/main/main_java.c \
	$(21_COMP_SIM_DIR)/main/turning.c \
	swig/main_21_comp_wrap.c


	$(BUILDLIB) -o $(LIB21) \
	equation.o \
	reflex.o \
	simulator.o \
	estimate.o \
	rkqc.o \
	initial.o \
	main.o \
	main_java.o \
	turning.o \
	main_21_comp_wrap.o

	make -C java

clean:
	-rm *.o
	-rm $(LIB6) $(LIB21)
	-rm swig/main_*_comp_wrap.c
	-rm java/src/edu/mit/lcp/C6_comp_backend/*.java
	-rm java/src/edu/mit/lcp/C21_comp_backend/*.java
	-rm -r java/classes/edu
	make -C java clean

.PHONY: all clean
