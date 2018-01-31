package edu.mit.lcp;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class PlotPoints{

    private boolean enabled = false;
    private List<String> stringList = new ArrayList<String>();
    List<Color> colorList = new ArrayList<Color>();
    int x;
    int y;
    
    public PlotPoints(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public void add(String str, Color color) {
	stringList.add(str);
	colorList.add(color);
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
     
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    

    public Color getColor(int i) { return colorList.get(i); }

    public String getString(int i) { return stringList.get(i);}

    public int getSize() { return colorList.size(); }
    
    public void setEnabled(boolean b) { enabled = b; }
    
    public boolean isEnabled() { return enabled; }

}
