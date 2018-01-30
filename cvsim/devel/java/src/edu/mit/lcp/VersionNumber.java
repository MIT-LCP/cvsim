package edu.mit.lcp;

public final class VersionNumber {

    private int _majorVerNum;
    private int _minorVerNum;

    public VersionNumber(int major, int minor) 
    {
	_majorVerNum = major;
	_minorVerNum = minor;
    }

    public int getMajorVerNum() { return _majorVerNum; }
    public int getMinorVerNum() { return _minorVerNum; }
    
    public void setMajorVerNum(int i) { _majorVerNum = i; }
    public void setMinorVerNum(int i) { _minorVerNum = i; }
    
    public String toString() {
	return _majorVerNum + "." + _minorVerNum;
    }

}
