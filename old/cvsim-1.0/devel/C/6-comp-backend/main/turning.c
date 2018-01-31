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
#include "turning.h"

// Simple function to determine whether the slope between two points
// is positive, negative, or zero.
int slope(double pt1, double pt2) {

  // if pt2 > pt1 return 1
  if (pt2 > pt1) 
    return 1;

  // if pt1 > pt2 return -1
  else if (pt1 > pt2) 
    return -1;
  
  // if pt1 = pt2, return 0 
  else if (pt1 == pt2) 
    return 0;

}


// Function to implement the turning-point data compression algorithm
double turning(double d[], int dataCompressionFactor) {
  
  double pt1, pt2;
  int slopes[dataCompressionFactor-1];
  int i;

  // Determine if the slope between each pair of points 
  // is positive, negative, or zero
  for ( i=0; i < dataCompressionFactor-1; i++ ) { 
    pt1 = d[i];
    pt2 = d[i+1];
    slopes[i] = slope(pt1, pt2);
  }
  
  // if all the slopes are positive, take the last point
  // if all the slopes are negative, take the last point
  // if there is peak, take the extremum
  for ( i=0; i < dataCompressionFactor-1; i++ ) {
    if ( (slopes[i] - slopes[i+1]) != 0 )
      return d[i+1];
  }  
  
  return d[dataCompressionFactor-1];
  
}
