# root makefile. Delegate to source subdirs.
include ../Makefile.include.root

JAVADOC=$(JDK_HOME)/bin/javadoc

PACKAGE=.

SUBDIRS	= src/edu/mit/lcp/ \
	  src/edu/mit/lcp/C6_comp_backend/ \
	  src/edu/mit/lcp/C21_comp_backend/
all:
	@@for p in $(SUBDIRS); do \
		echo '----- building ' $(PACKAGE)/$$p; \
		make -C $(PACKAGE)/$$p --no-print-directory  all; \
	done

javadoc:
	CLASSPATH=classes \
	$(JAVADOC) -d doc/api -public \
	edu.mit.lcp

clean:
	@@for p in $(SUBDIRS); do \
		echo 'cleaning ' $(PACKAGE)/$$p; \
		make -C $(PACKAGE)/$$p --no-print-directory clean;\
	done

