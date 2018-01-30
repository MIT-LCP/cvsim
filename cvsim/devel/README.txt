# Quick start for compiling CVSim:


1. Install prerequisites if not already installed:

   Install JDK 1.8.

   (Windows only) Install Cygwin, including the optional gcc and swig packages.
   If you need them, step-by-step instructions are at

	http://physionet.org/physiotools/cygwin/
  
   (Other platforms) Install gcc and swig. 

   These packages are all freely available;  try a Google search to find
   versions for your platform.

   
2. Navigate to this directory (devel).  Choose the correct template for
   your platform from:

   	Makefile.include.root.linux
      	Makefile.include.root.osx
      	Makefile.include.root.windows

   and rename it Makefile.include.root . Check the locations of the gcc and
   swig programs, and of the top-level JDK directory (JAVA_HOME). Correct
   these locations in Makefile.include.root if necessary.


3. While still in this directory, type

     	make
  
   This step compiles platform-specific binaries for both the 6- and
   21-compartment models for using gcc, creates platform-specific
   "glue" to link the models with the GUI using swig, and compiles the GUI
   using javac (from the JDK).


See the CVSIM Developer's Guide in the doc directory for more details if you
need them. Navigate to devel/java/classes and follow the instructions in
README.txt to run CVSim.
