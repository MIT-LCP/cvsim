package edu.mit.lcp;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;


public class PlotWindow extends JPanel {

    public static final int PARAMETRIC = 1;
    public static final int STRIPCHART = 2;

    private PlotPanel plot;
    private TraceLegendTableComponent legend;
    private TraceListModel traceList; 

    public PlotWindow(TraceListModel listModel, int plotType) {
	super();
	traceList = listModel;
	this.setLayout(new BorderLayout());

	// Setup PlotPanel
	switch (plotType) {
	case 1: plot = new PlotPanelXYChart(traceList); break;
	case 2: plot = new PlotPanelStripChart(traceList); break;
	default: throw new UnsupportedOperationException();
	}
	plot.setBorder(new EmptyBorder(5,5,5,5));
	
	// Setup Legend Table
	legend = new TraceLegendTableComponent(this, traceList);

	// Add components to window
	this.add(plot, BorderLayout.CENTER);
	this.add(legend, BorderLayout.PAGE_END);

	this.setVisible(true);
    }

    public PlotPanel getPlot() { return plot; }

}
