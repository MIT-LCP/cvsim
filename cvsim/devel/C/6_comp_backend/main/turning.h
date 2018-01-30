/* This file contains functions to implement the turning-point data 
 * compression algorithm. By varying the dataCompressionFactor the data 
 * can be compressed before being passed to the GUI. A dataCompressionFactor
 * of 10 means one piece of output data is passed to the GUI for every 10 timesteps. 
 * A dataCompressionFactor of 1 means every piece of data is passed to the GUI.
 * (One piece of data for every timestep.)
 *
 * Catherine Dunn    July 10, 2006
 * Last modified     July 10, 2006
 */

int slope(double pt1, double pt2);
double turning(double d[], int dataCompressionFactor);
