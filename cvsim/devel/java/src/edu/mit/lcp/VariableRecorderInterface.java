package edu.mit.lcp;

import java.util.Iterator;

public interface VariableRecorderInterface<DataType extends Number & Comparable<DataType>>
    extends Iterable<DataType> {

    public int getOutputIndex();
    public String getName();
    public String getDescription();
    public String getUnits();
    public int getSize();
    public String getCategory();
    public String getType();

    public void addDatum(DataType d);
    public void addMarkedDatum(DataType d, long serialNumber);

    public DataType getLastDatum();
    public long getLastMarker();

    public DataType getMinVal();
    public DataType getMaxVal();

    public Range<DataType> getTypicalRange();

}
