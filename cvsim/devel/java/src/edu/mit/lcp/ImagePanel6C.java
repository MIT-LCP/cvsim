package edu.mit.lcp;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JSplitPane;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JToolBar;
import javax.swing.JCheckBox;

public class ImagePanel6C extends ImagePanel {

    private javax.swing.JTabbedPane GraphicsTab;
    private javax.swing.JPanel anatomicpanel;
    private javax.swing.JLabel circtitle;
    private javax.swing.JLabel circuit;
    private javax.swing.JLabel circuitmodel;
    private javax.swing.JPanel circuitpanel;
    private javax.swing.JTextArea desc;
    private javax.swing.JScrollPane descpane;
    private javax.swing.JLabel desctitle;
    private javax.swing.JLabel flow;
    private javax.swing.JLabel flowleft;
    private javax.swing.JLabel flowright;
    private javax.swing.JLabel hspace;
    private javax.swing.JLabel hspace2;
    private javax.swing.JLabel leftheart;
    private javax.swing.JLabel llung;
    private javax.swing.JLabel periphartery2;
    private javax.swing.JLabel periphartey1;
    private javax.swing.JLabel periphmicro;
    private javax.swing.JLabel periphveins;
    private javax.swing.JLabel periphveins2;
    private javax.swing.JLabel periphviens3;
    private javax.swing.JLabel pulartery;
    private javax.swing.JLabel pulartery3;
    private javax.swing.JLabel pulartey2;
    private javax.swing.JLabel pulmicro;
    private javax.swing.JLabel pulspace;
    private javax.swing.JLabel pulveins;
    private javax.swing.JLabel rightheart;
    private javax.swing.JLabel rlung1;
    private javax.swing.JLabel toplung;
    
    public ImagePanel6C() {
	GraphicsTab = new javax.swing.JTabbedPane();
        anatomicpanel = new javax.swing.JPanel();
        rlung1 = new javax.swing.JLabel();
        toplung = new javax.swing.JLabel();
        pulartery = new javax.swing.JLabel();
        pulmicro = new javax.swing.JLabel();
        pulspace = new javax.swing.JLabel();
        periphveins = new javax.swing.JLabel();
        periphveins2 = new javax.swing.JLabel();
        pulartey2 = new javax.swing.JLabel();
        periphviens3 = new javax.swing.JLabel();
        pulartery3 = new javax.swing.JLabel();
        hspace = new javax.swing.JLabel();
        periphartey1 = new javax.swing.JLabel();
        rightheart = new javax.swing.JLabel();
        pulveins = new javax.swing.JLabel();
        leftheart = new javax.swing.JLabel();
        hspace2 = new javax.swing.JLabel();
        llung = new javax.swing.JLabel();
        periphartery2 = new javax.swing.JLabel();
        periphmicro = new javax.swing.JLabel();
        descpane = new javax.swing.JScrollPane();
        desc = new javax.swing.JTextArea();
	desc.setEditable(false);
        circuit = new javax.swing.JLabel();
        circtitle = new javax.swing.JLabel();
        desctitle = new javax.swing.JLabel();
        flowright = new javax.swing.JLabel();
        flow = new javax.swing.JLabel();
        flowleft = new javax.swing.JLabel();
        circuitpanel = new javax.swing.JPanel();
        circuitmodel = new javax.swing.JLabel();

	GraphicsTab.setPreferredSize(new java.awt.Dimension(595, 520));
        anatomicpanel.setBackground(new java.awt.Color(255, 255, 255));
        anatomicpanel.setMaximumSize(new java.awt.Dimension(600, 520));
        anatomicpanel.setPreferredSize(new java.awt.Dimension(590, 515));
        rlung1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/1.jpg")));

        toplung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/2.jpg")));

        pulartery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/3.jpg")));
        pulartery.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Arteries");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    pularteryMouseEntered(evt);
		    highlightParameterTable("Pulmonary Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    pularteryMouseExited(evt);
		}
	    });

        pulmicro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/4.jpg")));
        pulmicro.addMouseListener(new java.awt.event.MouseAdapter() {
            	public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Microcirculation");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    pulmicroMouseEntered(evt);
		    highlightParameterTable("Pulmonary Microcirculation");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    pulmicroMouseExited(evt);
		}
	    });

        pulspace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/5.jpg")));

        periphveins.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/8.jpg")));
        periphveins.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Systemic Veins");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    periphveinsMouseEntered(evt);
		    highlightParameterTable("Systemic Veins");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    periphveinsMouseExited(evt);
		}
	    });

        periphveins2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/9.jpg")));
        periphveins2.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Systemic Veins");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    periphveins2MouseEntered(evt);
		    highlightParameterTable("Systemic Veins");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    periphveins2MouseExited(evt);
		}
	    });

        pulartey2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/10a.jpg")));
        pulartey2.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Arteries");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    pulartey2MouseEntered(evt);
		    highlightParameterTable("Pulmonary Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    pulartey2MouseExited(evt);
		}
	    });

        periphviens3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/10d.jpg")));
        periphviens3.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Veins");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    periphviens3MouseEntered(evt);
		    highlightParameterTable("Pulmonary Veins");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    periphviens3MouseExited(evt);
		}
	    });

        pulartery3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/10c.jpg")));
        pulartery3.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Arteries");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    pulartery3MouseEntered(evt);
		    highlightParameterTable("Pulmonary Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    pulartery3MouseExited(evt);
		}
	    });

        hspace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/11.jpg")));

        periphartey1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/5new.jpg")));
        periphartey1.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Systemic Arteries");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    periphartey1MouseEntered(evt);
		    highlightParameterTable("Systemic Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    periphartey1MouseExited(evt);
		}
	    });

        rightheart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/13.jpg")));
        rightheart.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Right Heart");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    rightheartMouseEntered(evt);
		    highlightParameterTable("Right Heart");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    rightheartMouseExited(evt);
		}
	    });

        pulveins.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/6.jpg")));
        pulveins.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Veins");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    pulveinsMouseEntered(evt);
		    highlightParameterTable("Pulmonary Veins");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    pulveinsMouseExited(evt);
		}
	    });

        leftheart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/14.jpg")));
        leftheart.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Left Heart");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    leftheartMouseEntered(evt);
		    highlightParameterTable("Left Heart");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    leftheartMouseExited(evt);
		}
	    });

        hspace2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/17.jpg")));

        llung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/15.jpg")));

        periphartery2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/16.jpg")));
        periphartery2.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Systemic Arteries");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    periphartery2MouseEntered(evt);
		    highlightParameterTable("Systemic Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    periphartery2MouseExited(evt);
		}
	    });

        periphmicro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/18.jpg")));
        periphmicro.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    if (CVSim.simulationModelName.equals(CVSim.MODEL_6C))
			filterParameterTable("Systemic Microcirculation");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    periphmicroMouseEntered(evt);
		    if (CVSim.simulationModelName.equals(CVSim.MODEL_6C))
			highlightParameterTable("Systemic Microcirculation");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    periphmicroMouseExited(evt);
		}
	    });

        descpane.setBorder(null);
        desc.setColumns(20);
        desc.setLineWrap(true);
        desc.setRows(5);
        desc.setWrapStyleWord(true);
        descpane.setViewportView(desc);

        circtitle.setFont(new java.awt.Font("Tahoma", 1, 12));

        desctitle.setFont(new java.awt.Font("Tahoma", 1, 12));

        flow.setFont(new java.awt.Font("Tahoma", 0, 10));

        org.jdesktop.layout.GroupLayout anatomicpanelLayout = new org.jdesktop.layout.GroupLayout(anatomicpanel);
        anatomicpanel.setLayout(anatomicpanelLayout);
        anatomicpanelLayout.setHorizontalGroup(
            anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(anatomicpanelLayout.createSequentialGroup()
                .add(14, 14, 14)
                .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(anatomicpanelLayout.createSequentialGroup()
                        .add(0, 0, 0)
                        .add(periphmicro))
                    .add(anatomicpanelLayout.createSequentialGroup()
                        .add(rlung1)
                        .add(0, 0, 0)
                        .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(anatomicpanelLayout.createSequentialGroup()
                                .add(toplung)
                                .add(0, 0, 0)
                                .add(llung))
                            .add(anatomicpanelLayout.createSequentialGroup()
                                .add(pulartery)
                                .add(0, 0, 0)
                                .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(pulmicro)
                                    .add(pulspace))
                                .add(0, 0, 0)
                                .add(pulveins)
                                .add(0, 0, 0)
                                .add(periphartery2))))
                    .add(anatomicpanelLayout.createSequentialGroup()
                        .add(periphveins)
                        .add(0, 0, 0)
                        .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(anatomicpanelLayout.createSequentialGroup()
                                .add(periphveins2)
                                .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(anatomicpanelLayout.createSequentialGroup()
                                        .add(pulartey2)
                                        .add(0, 0, 0)
                                        .add(hspace)
                                        .add(0, 0, 0)
                                        .add(periphartey1))
                                    .add(anatomicpanelLayout.createSequentialGroup()
                                        .add(0, 0, 0)
                                        .add(periphviens3)
                                        .add(0, 0, 0)
                                        .add(pulartery3))))
                            .add(anatomicpanelLayout.createSequentialGroup()
                                .add(rightheart)
                                .add(0, 0, 0)
                                .add(leftheart))
                            .add(hspace2))))
                .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(anatomicpanelLayout.createSequentialGroup()
                        .add(19, 19, 19)
                        .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(anatomicpanelLayout.createSequentialGroup()
                                .add(flowleft)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(flow)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(flowright))
                            .add(circtitle)
                            .add(anatomicpanelLayout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(circuit))))
                    .add(anatomicpanelLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(descpane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 210, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(anatomicpanelLayout.createSequentialGroup()
                        .add(17, 17, 17)
                        .add(desctitle)))
                .add(4, 4, 4))
        );
        anatomicpanelLayout.setVerticalGroup(
            anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(anatomicpanelLayout.createSequentialGroup()
                .add(0, 0, 0)
                .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(anatomicpanelLayout.createSequentialGroup()
                        .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(anatomicpanelLayout.createSequentialGroup()
                                .add(rlung1)
                                .add(0, 0, 0)
                                .add(periphveins))
                            .add(anatomicpanelLayout.createSequentialGroup()
                                .add(toplung)
                                .add(0, 0, 0)
                                .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(anatomicpanelLayout.createSequentialGroup()
                                        .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(pulartery)
                                            .add(anatomicpanelLayout.createSequentialGroup()
                                                .add(pulmicro)
                                                .add(0, 0, 0)
                                                .add(pulspace)))
                                        .add(0, 0, 0)
                                        .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(periphveins2)
                                            .add(anatomicpanelLayout.createSequentialGroup()
                                                .add(pulartey2)
                                                .add(0, 0, 0)
                                                .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                                    .add(periphviens3)
                                                    .add(pulartery3)))
                                            .add(hspace)
                                            .add(periphartey1)))
                                    .add(pulveins))
                                .add(0, 0, 0)
                                .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(rightheart)
                                    .add(leftheart))
                                .add(0, 0, 0)
                                .add(hspace2))
                            .add(anatomicpanelLayout.createSequentialGroup()
                                .add(llung)
                                .add(0, 0, 0)
                                .add(periphartery2)))
                        .add(0, 0, 0)
                        .add(periphmicro)
                        .addContainerGap(487, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, anatomicpanelLayout.createSequentialGroup()
                        .add(desctitle)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(descpane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 255, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(20, 20, 20)
                        .add(circtitle)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(circuit)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(anatomicpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(flow)
                            .add(flowleft)
                            .add(flowright))
                        .add(28, 28, 28))))
        );
        GraphicsTab.addTab("Anatomical Overview", anatomicpanel);

        circuitpanel.setBackground(new java.awt.Color(255, 255, 255));
        circuitmodel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/cvsim_35.gif")));

        org.jdesktop.layout.GroupLayout circuitpanelLayout = new org.jdesktop.layout.GroupLayout(circuitpanel);
        circuitpanel.setLayout(circuitpanelLayout);
        circuitpanelLayout.setHorizontalGroup(
            circuitpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(circuitpanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(circuitmodel)
                .addContainerGap(568, Short.MAX_VALUE))
        );
        circuitpanelLayout.setVerticalGroup(
            circuitpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(circuitpanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(circuitmodel)
                .addContainerGap(476, Short.MAX_VALUE))
        );
        GraphicsTab.addTab("Circuit Overview", circuitpanel);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(GraphicsTab, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 583, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(GraphicsTab, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 512, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

    } // end constructor
    
    private void pulmicroMouseExited(java.awt.event.MouseEvent evt) {                                     
        circuit.setIcon(null);
        desctitle.setText(null);
        circtitle.setText(null);
        desc.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                    

    private void periphveins2MouseExited(java.awt.event.MouseEvent evt) {                                         
        circuit.setIcon(null);
        desctitle.setText(null);
        circtitle.setText(null);
        desc.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                        

    private void periphartey1MouseExited(java.awt.event.MouseEvent evt) {                                         
        circuit.setIcon(null);
        desctitle.setText(null);
        circtitle.setText(null);
        desc.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                        

    private void periphmicroMouseExited(java.awt.event.MouseEvent evt) {                                        
        circuit.setIcon(null);
        desctitle.setText(null);
        desc.setText(null);
        circtitle.setText(null);
        flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                       

    private void periphartery2MouseExited(java.awt.event.MouseEvent evt) {                                          
        circuit.setIcon(null);
        desctitle.setText(null);
        circtitle.setText(null);
        desc.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                         

    private void pulveinsMouseExited(java.awt.event.MouseEvent evt) {                                     
        circuit.setIcon(null);
        desctitle.setText(null);
        circtitle.setText(null);
        desc.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                    

    private void periphviens3MouseExited(java.awt.event.MouseEvent evt) {                                         
        circuit.setIcon(null);
        desctitle.setText(null);
        circtitle.setText(null);
        desc.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                        

    private void pulartery3MouseExited(java.awt.event.MouseEvent evt) {                                       
        circuit.setIcon(null);
        desctitle.setText(null);
        circtitle.setText(null);
        desc.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                      

    private void pulartey2MouseExited(java.awt.event.MouseEvent evt) {                                      
        circuit.setIcon(null);
        desctitle.setText(null);
        desc.setText(null);
        circtitle.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                     

    private void pularteryMouseExited(java.awt.event.MouseEvent evt) {                                      
        circuit.setIcon(null);
        desctitle.setText(null);
        desc.setText(null);
        circtitle.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                     

    private void periphveinsMouseExited(java.awt.event.MouseEvent evt) {                                        
        circuit.setIcon(null);
        desctitle.setText(null);
        circtitle.setText(null);
        desc.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                       

    private void rightheartMouseExited(java.awt.event.MouseEvent evt) {                                       
        circuit.setIcon(null);
        circtitle.setText(null);
        desctitle.setText(null);
        desc.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                      

    private void leftheartMouseExited(java.awt.event.MouseEvent evt) {                                      
        circuit.setIcon(null);
        desctitle.setText(null);
        desc.setText(null);
        circtitle.setText(null);
         flowright.setIcon(null);
        flowleft.setIcon(null);
        flow.setText(null);
    }                                     

    private void pulveinsMouseEntered(java.awt.event.MouseEvent evt) {                                      
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/pulV.gif")));
        desctitle.setText("Pulmonary Veins");
        desc.setText("The pulmonary veins deliver oxygenated blood to the left heart.");
        circtitle.setText("Circuit Representation");
        flowright.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/oxyright.jpg")));
        flow.setText("Blood Flow");
    }                                     

    private void pulmicroMouseEntered(java.awt.event.MouseEvent evt) {                                      
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/resistor.gif")));
        desctitle.setText("Pulmonary Microcirculation");
        circtitle.setText("Circuit Representation");
        desc.setText("In the pulmonary microcirculation oxygen exchange takes place across the membrane separating pulmonary alveoli from the pulmonary capillary network. The pulmonary microcirculation thus receives de-oxygenated blood from the pulmonary arteries and returns oxygenated blood to the pulmonary veins.");
        flowright.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/oxyrightde.jpg")));
        flow.setText("Blood Flow");
    }                                     

    private void pularteryMouseEntered(java.awt.event.MouseEvent evt) {                                       
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/pulC.gif")));
        desctitle.setText("Pulmonary Arteries");
        circtitle.setText("Circuit Representation");
        desc.setText("The pulmonary arteries carry de-oxygenated blood from the right heart to the pulmonary microcirculation.");
         flowright.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/deoxyright.jpg")));
        flow.setText("Blood Flow");
    }                                      

    private void pulartey2MouseEntered(java.awt.event.MouseEvent evt) {                                       
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/pulC.gif")));
        desctitle.setText("Pulmonary Arteries");
        circtitle.setText("Circuit Representation");
        desc.setText("The pulmonary arteries carry de-oxygenated blood from the right heart to the pulmonary microcirculation.");
        flowright.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/deoxyright.jpg")));
        flow.setText("Blood Flow");
    }                                      

    private void pulartery3MouseEntered(java.awt.event.MouseEvent evt) {                                        
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/pulC.gif")));
        desctitle.setText("Pulmonary Arteries");
        circtitle.setText("Circuit Representation");
        desc.setText("The pulmonary arteries carry de-oxygenated blood from the right heart to the pulmonary microcirculation.");
        flowright.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/deoxyright.jpg")));
        flow.setText("Blood Flow");
    }                                       

    private void leftheartMouseEntered(java.awt.event.MouseEvent evt) {                                       
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/leftheart.gif")));
        desctitle.setText("Left Heart");
        circtitle.setText("Circuit Representation");
        desc.setText("During diastole, the left heart receives oxygenated blood from the pulmonary veins; during systole, it generates the required pressure to propel the blood into the systemic arteries.");
        flowright.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/oxyright.jpg")));
        flow.setText("Blood Flow");
    }                                      

    private void rightheartMouseEntered(java.awt.event.MouseEvent evt) {                                        
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/rightheart.gif")));
        desctitle.setText("Right Heart");
        circtitle.setText("Circuit Representation");
        desc.setText("During diastole, the right heart receives de-oxygenated blood from the systemic veins; during systole, it generates the required pressure to propel the blood into the pulmonary arteries.");
        flowright.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/deoxyright.jpg")));
        flow.setText("Blood Flow");
    }                                       

    private void periphmicroMouseEntered(java.awt.event.MouseEvent evt) {                                         
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/resistor.gif")));
        desctitle.setText("Systemic Microcirculation");
        circtitle.setText("Circuit Representation");
        desc.setText("The systemic microcirculation represents the capillary network of all end-organs in the body with the exception of the lung. It receives oxygenated blood from the systemic arteries and returns de-oxygenated blood to the systemic veins.");
        flowleft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/deoxyleftoxy.jpg")));
        flow.setText("Blood Flow");
    }                                        

    private void periphartery2MouseEntered(java.awt.event.MouseEvent evt) {                                           
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/pcapacitor.gif")));
        desctitle.setText("Systemic Arteries");
        circtitle.setText("Circuit Representation");
        desc.setText("The systemic arteries carry oxygenated blood from the left heart to the systemic microcirculation.");
        flowleft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/oxyleft.jpg")));
        flow.setText("Blood Flow");
    }                                          

    private void periphartey1MouseEntered(java.awt.event.MouseEvent evt) {                                          
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/pcapacitor.gif")));
        desctitle.setText("Systemic Arteries");
        circtitle.setText("Circuit Representation");
        desc.setText("The systemic arteries carry oxygenated blood from the left heart to the systemic microcirculation.");
        flowleft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/oxyleft.jpg")));
        flow.setText("Blood Flow");
    }                                         

    private void periphviens3MouseEntered(java.awt.event.MouseEvent evt) {                                          
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/pcapres.gif")));
        desctitle.setText("Systemic Veins");
        circtitle.setText("Circuit Representation");
        desc.setText("The systemic veins return de-oxygenated blood to the right heart.");
        flowleft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/deoxyleft.jpg")));
        flow.setText("Blood Flow");
    }                                         

    private void periphveins2MouseEntered(java.awt.event.MouseEvent evt) {                                          
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/pcapres.gif")));
        desctitle.setText("Systemic Veins");
        circtitle.setText("Circuit Representation");
        desc.setText("The systemic veins return de-oxygenated blood to the right heart.");
        flowleft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/deoxyleft.jpg")));
        flow.setText("Blood Flow");
    }                                         

    private void periphveinsMouseEntered(java.awt.event.MouseEvent evt) {                                         
        circuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/pcapres.gif")));
        desctitle.setText("Systemic Veins");
        circtitle.setText("Circuit Representation");
        desc.setText("The systemic veins return de-oxygenated blood to the right heart.");
        flowleft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images6C/circuits/deoxyleft.jpg")));
        flow.setText("Blood Flow");
    }                                        

}
