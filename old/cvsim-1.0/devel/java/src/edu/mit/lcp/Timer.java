package edu.mit.lcp;

public class Timer {
  // A simple "stopwatch" class with millisecond accuracy

  private long startTime, endTime;

  public void start()   {  startTime = System.currentTimeMillis();       }
  public void stop()    {  endTime   = System.currentTimeMillis();       }
  public long getTime() {  return endTime - startTime;                   }
}
