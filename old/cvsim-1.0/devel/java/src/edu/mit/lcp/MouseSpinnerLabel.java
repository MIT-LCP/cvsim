package edu.mit.lcp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;

public class MouseSpinnerLabel extends JLabel
    implements MouseListener, MouseMotionListener, ChangeListener  {

    SpinnerNumberModel dataModel;
    double mousedownValue;
    Rectangle mouseenterDim;
    LineBorder highlight;

    public MouseSpinnerLabel(String text) {
	super(text);
	dataModel = new SpinnerNumberModel();
	dataModel.addChangeListener(this);
	addMouseListener(this);
	addMouseMotionListener(this);
	highlight = new LineBorder(Color.BLACK, 1);
    }

    public SpinnerModel getModel() {
	return dataModel;
    }

    public void mouseClicked( MouseEvent event ) {
	//System.out.println( "Clicked @ x" + event.getX() + " y" + event.getY() );
    }

    public void mousePressed( MouseEvent event) {
	mousedownValue = event.getPoint().y+Double.parseDouble(getText());
	//System.out.println( "Pressed @ x" + event.getX() + " y" + event.getY() );
    }

    public void mouseReleased( MouseEvent event ) {
	mousedownValue = Double.parseDouble(getText());
	//System.out.println( "Released @ x" + event.getX() + " y" + event.getY() );
    }

    public void mouseEntered( MouseEvent event ) {
	mouseenterDim = getBounds();
	setBounds(mouseenterDim.x-1, mouseenterDim.y-1,
		  mouseenterDim.width+2, mouseenterDim.height+2);
	setBorder(highlight);
	//System.out.println( "Mouse Entered @ x" + event.getX() + " y" + event.getY() );
    }

    public void mouseExited( MouseEvent event ) {
	setBorder(null);
	setBounds(mouseenterDim);
	//System.out.println( "Mouse Exited @ x" + event.getX() + " y" + event.getY() );
    }

    public void mouseDragged( MouseEvent event ) {
	dataModel.setValue(mousedownValue-event.getY());
	//System.out.println( "Mouse Dragged @ x" + event.getX() + " y" + event.getY() );
    }

    public void mouseMoved( MouseEvent event ) {
	//System.out.println( "Mouse Moved @ x" + event.getX() + " y" + event.getY() );
    }

    public void stateChanged(ChangeEvent e) {
	SpinnerNumberModel model = (SpinnerNumberModel)(e.getSource());
	setText(model.getNumber().toString());
    }

}
