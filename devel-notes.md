# Content

Version *1.0* is the old stable version released in 2008. It requires Java 1.6 or 1.7. The instructions also suggest downloading and using Java Web Start, and hence provides jar files compiled for each version. Content hosted on Physionet pertaining to version *1.0* is archived in the `old` directory.

From version *1.0.1* (released in Jan 2018) onwards, the `cvsim-x.y.z-platform.tar.gz` archives are created by archiving the `devel/java/classes/` subdirectory, after compiling the same version's code on the relevant platform, and renaming the folder. The `README.txt` files in each tar distribution contain compilation information. There will be no more jar files, and no more recommendation to use Java Web Start. Instead, users should download the JRE and compiled code for their operating system, extract the archived contents, and run the program.


# Changelog Overview

- Version *1.0.1* added by Chen Jan 2018. Fixed array out of bounds in `turning.c`. Distributions compiled using Java 1.8.
