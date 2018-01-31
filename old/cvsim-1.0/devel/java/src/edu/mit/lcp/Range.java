package edu.mit.lcp;

public class Range<E extends Number & Comparable<E>> {
    public E lower;
    public E upper;

    Range(E lowerBound, E upperBound) {
	lower = lowerBound;
	upper = upperBound;
    }

    Range(Range<E> copyRange) {
	this(copyRange.getLower(), copyRange.getUpper());
    }

    public E getLower() {
	return lower;
    }

    public void setLower(E lowerBound) {
	lower = lowerBound;
    }

    public E getUpper() {
	return upper;
    }

    public void setUpper(E upperBound) {
	upper = upperBound;
    }
    
    public void setBounds(E lowerBound, E upperBound) {
	lower = lowerBound;
	upper = upperBound;
    }

    public void setBounds(Range<E> b) {
	lower = b.getLower();
	upper = b.getUpper();
    }
}


