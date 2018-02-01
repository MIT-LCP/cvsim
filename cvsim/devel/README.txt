# Quick start for compiling CVSim:


1. Install prerequisites if not already installed:

   - Install JDK 1.8.

   - Linux and OSX: Install gcc and swig. 

   - Windows only: Install Cygwin, including the optional *gcc and swig packages.
     Step-by-step instructions are at: http://physionet.org/physiotools/cygwin/
     
     *'mingw64-x86_64-gcc-core' is the gcc you should install if you want to
       compile the system without the output being tied to the cygwin libraries.
       The default makefile has this set as the compiler.
  
   
2. Open a terminal (Cygwin in windows) and navigate to this directory (devel).
   Choose the correct template for your platform from:

   	  Makefile.include.root.linux
      Makefile.include.root.osx
      Makefile.include.root.windows

   and rename it Makefile.include.root. Check the locations of the gcc and
   swig programs, and of the top-level JDK directory (JAVA_HOME). Correct
   these items if necessary.


3. While still in this directory, type

     	make

   This step compiles platform-specific binaries for both the 6- and
   21-compartment models for using gcc, creates platform-specific
   "glue" to link the models with the GUI using swig, and compiles the GUI
   using javac (from the JDK).

   The compiled content will be located in the
   devel/java/classes subdirectory.


See the CVSIM Developer's Guide in the doc directory for more details if you
need them. Navigate to devel/java/classes and follow the instructions in
README.txt to run CVSim.
