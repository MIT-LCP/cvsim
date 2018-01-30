package edu.mit.lcp;

public class ImagePanel21C extends ImagePanel {

    private javax.swing.JPanel AnatomicalPanel;
    private javax.swing.JLabel AscendingA;
    private javax.swing.JLabel AscendingAb;
    private javax.swing.JLabel AscendingAc;
    private javax.swing.JLabel Brachioceph;
    private javax.swing.JLabel Brachiocepha;
    private javax.swing.JLabel CircuitOverview;
    private javax.swing.JPanel CircuitPanel;
    private javax.swing.JLabel IVCa;
    private javax.swing.JLabel IVCb;
    private javax.swing.JTabbedPane IllustrationTabs;
    private javax.swing.JLabel LBMicro;
    private javax.swing.JLabel LBVeins;
    private javax.swing.JLabel LBarteries;
    private javax.swing.JLabel LTHeart;
    private javax.swing.JLabel LTHeartb;
    private javax.swing.JLabel PulMcirc;
    private javax.swing.JLabel Pularterya;
    private javax.swing.JLabel Pularteryc;
    private javax.swing.JLabel Pularteryd;
    private javax.swing.JLabel Pulmarteryb;
    private javax.swing.JLabel Pulveinb;
    private javax.swing.JLabel Pulveinsa;
    private javax.swing.JLabel RTHeart;
    private javax.swing.JLabel RenalMicroa;
    private javax.swing.JLabel RenalMicrob;
    private javax.swing.JLabel SVC;
    private javax.swing.JLabel SVCb;
    private javax.swing.JLabel SVCc;
    private javax.swing.JLabel SVCd;
    private javax.swing.JLabel SplanchnicArteries;
    private javax.swing.JLabel SplanchnicMicroa;
    private javax.swing.JLabel SplanchnicMicrob;
    private javax.swing.JLabel ThoracicAorta;
    private javax.swing.JLabel ThoracicAortab;
    private javax.swing.JLabel UBArtery;
    private javax.swing.JLabel UBMicro;
    private javax.swing.JLabel UBVeins;
    private javax.swing.JLabel abdomaorta;
    private javax.swing.JLabel abdomveins;
    private javax.swing.JLabel circtitle;
    private javax.swing.JLabel circuitimage;
    private javax.swing.JTextArea descarea;
    private javax.swing.JScrollPane descpane;
    private javax.swing.JLabel desctitle;
    private javax.swing.JLabel flow;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel renalVeins;
    private javax.swing.JLabel renalarteries;
    private javax.swing.JLabel splanchnicVeins;

    public ImagePanel21C() {
        IllustrationTabs = new javax.swing.JTabbedPane();
        AnatomicalPanel = new javax.swing.JPanel();
        UBMicro = new javax.swing.JLabel();
        UBVeins = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        UBArtery = new javax.swing.JLabel();
        SVC = new javax.swing.JLabel();
        Pularteryd = new javax.swing.JLabel();
        PulMcirc = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Pulveinsa = new javax.swing.JLabel();
        Brachioceph = new javax.swing.JLabel();
        Pularteryc = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Pulmarteryb = new javax.swing.JLabel();
        AscendingAb = new javax.swing.JLabel();
        Pulveinb = new javax.swing.JLabel();
        AscendingAc = new javax.swing.JLabel();
        Brachiocepha = new javax.swing.JLabel();
        ThoracicAortab = new javax.swing.JLabel();
        IVCb = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        SVCc = new javax.swing.JLabel();
        Pularterya = new javax.swing.JLabel();
        AscendingA = new javax.swing.JLabel();
        LTHeartb = new javax.swing.JLabel();
        RTHeart = new javax.swing.JLabel();
        LTHeart = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        ThoracicAorta = new javax.swing.JLabel();
        abdomveins = new javax.swing.JLabel();
        splanchnicVeins = new javax.swing.JLabel();
        renalVeins = new javax.swing.JLabel();
        SplanchnicMicroa = new javax.swing.JLabel();
        SplanchnicMicrob = new javax.swing.JLabel();
        RenalMicroa = new javax.swing.JLabel();
        SplanchnicArteries = new javax.swing.JLabel();
        LBVeins = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        RenalMicrob = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        renalarteries = new javax.swing.JLabel();
        abdomaorta = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        LBarteries = new javax.swing.JLabel();
        LBMicro = new javax.swing.JLabel();
        descpane = new javax.swing.JScrollPane();
        descarea = new javax.swing.JTextArea();
        desctitle = new javax.swing.JLabel();
        circtitle = new javax.swing.JLabel();
        circuitimage = new javax.swing.JLabel();
        SVCd = new javax.swing.JLabel();
        IVCa = new javax.swing.JLabel();
        SVCb = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        flow = new javax.swing.JLabel();
        CircuitPanel = new javax.swing.JPanel();
        CircuitOverview = new javax.swing.JLabel();

        AnatomicalPanel.setBackground(new java.awt.Color(255, 255, 255));
        UBMicro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/uppermicro.jpg")));
        UBMicro.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Upper Body Microcirculation");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    UBMicroMouseEntered(evt);
		    highlightParameterTable("Upper Body Microcirculation");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    UBMicroMouseExited(evt);
		}
	    });
	
        UBVeins.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/uppveins.jpg")));
        UBVeins.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Upper Body Veins");
		}            
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    UBVeinsMouseEntered(evt);
		    highlightParameterTable("Upper Body Veins");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    UBVeinsMouseExited(evt);
		}
	    });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/uppspace.jpg")));

        UBArtery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/upperarteries.jpg")));
        UBArtery.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Upper Body Arteries");
		}      
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    UBArteryMouseEntered(evt);
		    highlightParameterTable("Upper Body Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    UBArteryMouseExited(evt);
		}
	    });

        SVC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/superiorVC.jpg")));
        SVC.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Superior Vena Cava");
		}      
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    SVCMouseEntered(evt);
		    highlightParameterTable("Superior Vena Cava");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    SVCMouseExited(evt);
		}
	    });

        Pularteryd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmarteryA.jpg")));
        Pularteryd.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Arteries");
		}      
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    PularterydMouseEntered(evt);
		    highlightParameterTable("Pulmonary Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    PularterydMouseExited(evt);
		}
	    });

        PulMcirc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmicro.jpg")));
        PulMcirc.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Microcirculation");
		}      
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    PulMcircMouseEntered(evt);
		    highlightParameterTable("Pulmonary Microcirculation");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    PulMcircMouseExited(evt);
		}
	    });
	
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmspace.jpg")));

        Pulveinsa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmvein.jpg")));
        Pulveinsa.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Veins");
		}      
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    PulveinsaMouseEntered(evt);
		    highlightParameterTable("Pulmonary Veins");
		}
 		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    PulveinsaMouseExited(evt);
	 	}
	    });

        Brachioceph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/brachiocephalicA.jpg")));
        Brachioceph.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Brachiocephalic Arteries");
		}      
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    BrachiocephMouseEntered(evt);
		    highlightParameterTable("Brachiocephalic Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    BrachiocephMouseExited(evt);
		}
	    });
	
        Pularteryc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmarteryB.jpg")));
        Pularteryc.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Arteries");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    PularterycMouseEntered(evt);
		    highlightParameterTable("Pulmonary Arteries");
		}
 		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
	 	    PularterycMouseExited(evt);
		}
	    });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/leftheartA.jpg")));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Left Heart");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    jLabel13MouseEntered(evt);
		    highlightParameterTable("Left Heart");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    jLabel13MouseExited(evt);
		}
	    });

        Pulmarteryb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmarteryC.jpg")));
        Pulmarteryb.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Arteries");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    PulmarterybMouseEntered(evt);
		    highlightParameterTable("Pulmonary Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    PulmarterybMouseExited(evt);
		}
	    });

        AscendingAb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/ascendingaorta.jpg")));
        AscendingAb.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Ascending Aorta");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    AscendingAbMouseEntered(evt);
		    highlightParameterTable("Ascending Aorta");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    AscendingAbMouseExited(evt);
		}
	    });

        Pulveinb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmveinB.jpg")));
        Pulveinb.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Veins");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    PulveinbMouseEntered(evt);
		    highlightParameterTable("Pulmonary Veins");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    PulveinbMouseExited(evt);
		}
	    });

        AscendingAc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/ascendingaortaB.jpg")));
        AscendingAc.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Ascending Aorta");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    AscendingAcMouseEntered(evt);
		    filterParameterTable("Ascending Aorta");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    AscendingAcMouseExited(evt);
		}
	    });

        Brachiocepha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/brachiocephalicB.jpg")));
        Brachiocepha.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Brachiocephalic Arteries");
		} 	
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    BrachiocephaMouseEntered(evt);
		    highlightParameterTable("Brachiocephalic Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    BrachiocephaMouseExited(evt);
		}
	    });

        ThoracicAortab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/thoracicaortaA.jpg")));
        ThoracicAortab.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Thoracic Aorta");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    ThoracicAortabMouseEntered(evt);
		    highlightParameterTable("Thoracic Aorta");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    ThoracicAortabMouseExited(evt);
		}
	    });

        IVCb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/inferiorVCB.jpg")));
        IVCb.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Inferior Vena Cava");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    IVCbMouseEntered(evt);
		    highlightParameterTable("Inferior Vena Cava");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    IVCbMouseExited(evt);
		}
	    });

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/leftheartspaceA.jpg")));

        SVCc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/leftheartB.jpg")));
        SVCc.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Superior Vena Cava");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    SVCcMouseEntered(evt);
		    highlightParameterTable("Superior Vena Cava");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    SVCcMouseExited(evt);
		}
	    });

        Pularterya.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmarteryD.jpg")));
        Pularterya.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Arteries");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    PularteryaMouseEntered(evt);
		    highlightParameterTable("Pulmonary Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    PularteryaMouseExited(evt);		    
		}
	    });

        AscendingA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/ascendingaortaC.jpg")));
        AscendingA.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Pulmonary Arteries");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    AscendingAMouseEntered(evt);
		    highlightParameterTable("Pulmonary Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    AscendingAMouseExited(evt);
		}
	    });

        LTHeartb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/rightheartA.jpg")));
        LTHeartb.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Left Heart");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    LTHeartbMouseEntered(evt);
		    highlightParameterTable("Left Heart");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    LTHeartbMouseExited(evt);
		}
	    });

        RTHeart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/leftheartC.jpg")));
        RTHeart.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Right Heart");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    RTHeartMouseEntered(evt);
		    highlightParameterTable("Right Heart");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    RTHeartMouseExited(evt);
		}
	    });

        LTHeart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/rightheartB.jpg")));
        LTHeart.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Left Heart");
		} 
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    LTHeartMouseEntered(evt);
		    highlightParameterTable("Left Heart");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    LTHeartMouseExited(evt);
		}
	    });

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/heartspace.jpg")));

        ThoracicAorta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/thoracicaortaB.jpg")));
        ThoracicAorta.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Thoracic Aorta");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    ThoracicAortaMouseEntered(evt);
		    highlightParameterTable("Thoracic Aorta");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    ThoracicAortaMouseExited(evt);
		}
	    });

        abdomveins.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/abdominalveins.jpg")));
        abdomveins.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Abdominal Veins");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    abdomveinsMouseEntered(evt);
		    highlightParameterTable("Abdominal Veins");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    abdomveinsMouseExited(evt);
		}
	    });
	
        splanchnicVeins.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/splanchnicveins.jpg")));
        splanchnicVeins.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Splanchnic Veins");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    splanchnicVeinsMouseEntered(evt);
		    highlightParameterTable("Splanchnic Veins");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    splanchnicVeinsMouseExited(evt);
		}
	    });

        renalVeins.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/renalveins.jpg")));
        renalVeins.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Renal Veins");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    renalVeinsMouseEntered(evt);
		    highlightParameterTable("Renal Veins");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    renalVeinsMouseExited(evt);
		}
	    });

        SplanchnicMicroa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/spalchnicmicro.jpg")));
        SplanchnicMicroa.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Splanchnic Microcirculation");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    SplanchnicMicroaMouseEntered(evt);
		    highlightParameterTable("Splanchnic Microcirculation");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    SplanchnicMicroaMouseExited(evt);
		}
	    });

        SplanchnicMicrob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/spalchnicmicroB.jpg")));
        SplanchnicMicrob.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Splanchnic Microcirculation");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    SplanchnicMicrobMouseEntered(evt);
		    highlightParameterTable("Splanchnic Microcirculation");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    SplanchnicMicrobMouseExited(evt);
		}
	    });

        RenalMicroa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/renalmicro.jpg")));
        RenalMicroa.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Renal Microcirculation");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    RenalMicroaMouseEntered(evt);
		    highlightParameterTable("Renal Microcirculation");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    RenalMicroaMouseExited(evt);
		}
	    });

        SplanchnicArteries.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/splanchnicartery.jpg")));
        SplanchnicArteries.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Splanchnic Arteries");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    SplanchnicArteriesMouseEntered(evt);
		    highlightParameterTable("Splanchnic Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    SplanchnicArteriesMouseExited(evt);
		}
	    });

        LBVeins.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/lowerbodyveins.jpg")));
        LBVeins.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Lower Body Veins");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    LBVeinsMouseEntered(evt);
		    highlightParameterTable("Lower Body Veins");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    LBVeinsMouseExited(evt);
		}
	    });

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/lbspabceA.jpg")));

        RenalMicrob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/renalmicroB.jpg")));
        RenalMicrob.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Renal Microcirculation");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    RenalMicrobMouseEntered(evt);
		    highlightParameterTable("Renal Microcirculation");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    RenalMicrobMouseExited(evt);
		}
	    });

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/lbspabceB.jpg")));

        renalarteries.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/renalartery.jpg")));
        renalarteries.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Renal Arteries");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    renalarteriesMouseEntered(evt);
		    highlightParameterTable("Renal Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    renalarteriesMouseExited(evt);
		}
	    });

        abdomaorta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/abdominalaorta.jpg")));
        abdomaorta.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Abdominal Aorta");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    abdomaortaMouseEntered(evt);
		    highlightParameterTable("Abdominal Aorta");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    abdomaortaMouseExited(evt);
		}
	    });

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/lbspabceC.jpg")));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/lbspabceD.jpg")));

        LBarteries.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/lowerbodyartery.jpg")));
        LBarteries.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Lower Body Arteries");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    LBarteriesMouseEntered(evt);
		    highlightParameterTable("Lower Body Arteries");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    LBarteriesMouseExited(evt);
		}
	    });

        LBMicro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/lowerbodymicro.jpg")));
        LBMicro.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Lower Body Microcirculation");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    LBMicroMouseEntered(evt);
		    highlightParameterTable("Lower Body Microcirculation");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    LBMicroMouseExited(evt);
		}
	    });

        descpane.setBorder(null);
        descarea.setColumns(20);
        descarea.setLineWrap(true);
        descarea.setRows(5);
        descarea.setWrapStyleWord(true);
        descarea.setBorder(null);
        descpane.setViewportView(descarea);

        SVCd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/SVCb.jpg")));
        SVCd.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Superior Vena Cava");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    SVCdMouseEntered(evt);
		    highlightParameterTable("Superior Vena Cava");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    SVCdMouseExited(evt);
		}
	    });

        IVCa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/IVCa.jpg")));
        IVCa.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Inferior Vena Cava");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    IVCaMouseEntered(evt);
		    highlightParameterTable("Inferior Vena Cava");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    IVCaMouseExited(evt);
		}
	    });

        SVCb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/SVCa.jpg")));
        SVCb.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    filterParameterTable("Superior Vena Cava");
		}
		public void mouseEntered(java.awt.event.MouseEvent evt) {
		    circtitle.setText("Circuit Representation");
		    flow.setText("Blood Flow");
		    SVCbMouseEntered(evt);
		    highlightParameterTable("Superior Vena Cava");
		}
		public void mouseExited(java.awt.event.MouseEvent evt) {
		    circtitle.setText(null);
		    desctitle.setText(null);
		    flow.setText(null);
		    SVCbMouseExited(evt);
		}
	    });
	
       org.jdesktop.layout.GroupLayout AnatomicalPanelLayout = new org.jdesktop.layout.GroupLayout(AnatomicalPanel);
        AnatomicalPanel.setLayout(AnatomicalPanelLayout);
        AnatomicalPanelLayout.setHorizontalGroup(
            AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(AnatomicalPanelLayout.createSequentialGroup()
                .add(0, 0, 0)
                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(LBMicro)
                    .add(AnatomicalPanelLayout.createSequentialGroup()
                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(UBVeins)
                                .add(0, 0, 0)
                                .add(jLabel3)
                                .add(0, 0, 0)
                                .add(UBArtery))
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(IVCb)
                                .add(0, 0, 0)
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(AnatomicalPanelLayout.createSequentialGroup()
                                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                                .add(jLabel21)
                                                .add(0, 0, 0)
                                                .add(SVCc)
                                                .add(0, 0, 0)
                                                .add(Pularterya)
                                                .add(0, 0, 0)
                                                .add(AscendingA)
                                                .add(0, 0, 0)
                                                .add(LTHeartb))
                                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                                .add(RTHeart)
                                                .add(0, 0, 0)
                                                .add(LTHeart)))
                                        .add(0, 0, 0)
                                        .add(ThoracicAorta))
                                    .add(jLabel28)))
                            .add(UBMicro)
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(abdomveins)
                                .add(0, 0, 0)
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(AnatomicalPanelLayout.createSequentialGroup()
                                        .add(splanchnicVeins)
                                        .add(0, 0, 0)
                                        .add(SplanchnicMicroa)
                                        .add(0, 0, 0)
                                        .add(SplanchnicArteries)
                                        .add(0, 0, 0)
                                        .add(abdomaorta))
                                    .add(AnatomicalPanelLayout.createSequentialGroup()
                                        .add(renalVeins)
                                        .add(0, 0, 0)
                                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(SplanchnicMicrob)
                                            .add(RenalMicroa))
                                        .add(0, 0, 0)
                                        .add(renalarteries))))
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(0, 0, 0)
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                        .add(SVCd)
                                        .add(SVC))
                                    .add(IVCa))
                                .add(0, 0, 0)
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(AnatomicalPanelLayout.createSequentialGroup()
                                        .add(Pularteryd)
                                        .add(0, 0, 0)
                                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                                .add(PulMcirc)
                                                .add(0, 0, 0)
                                                .add(Pulveinsa)
                                                .add(0, 0, 0)
                                                .add(Brachioceph))
                                            .add(jLabel8)))
                                    .add(AnatomicalPanelLayout.createSequentialGroup()
                                        .add(SVCb)
                                        .add(0, 0, 0)
                                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(Pularteryc)
                                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                                .add(jLabel13)
                                                .add(0, 0, 0)
                                                .add(Pulmarteryb)))
                                        .add(0, 0, 0)
                                        .add(AscendingAb)
                                        .add(0, 0, 0)
                                        .add(Pulveinb)
                                        .add(0, 0, 0)
                                        .add(AscendingAc)
                                        .add(0, 0, 0)
                                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                                .add(0, 0, 0)
                                                .add(ThoracicAortab))
                                            .add(Brachiocepha)))))
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(LBVeins)
                                .add(0, 0, 0)
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(AnatomicalPanelLayout.createSequentialGroup()
                                        .add(jLabel40)
                                        .add(0, 0, 0)
                                        .add(RenalMicrob)
                                        .add(0, 0, 0)
                                        .add(jLabel43)
                                        .add(0, 0, 0)
                                        .add(LBarteries))
                                    .add(AnatomicalPanelLayout.createSequentialGroup()
                                        .add(jLabel41)
                                        .add(0, 0, 0)
                                        .add(jLabel44)))))
                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(60, 60, 60)
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(AnatomicalPanelLayout.createSequentialGroup()
                                        .add(10, 10, 10)
                                        .add(flow))
                                    .add(jLabel5)))
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(25, 25, 25)
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(descpane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 251, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(circuitimage)
                                    .add(circtitle)
                                    .add(desctitle))))
                        .add(org.jdesktop.layout.LayoutStyle.RELATED)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        AnatomicalPanelLayout.setVerticalGroup(
            AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(AnatomicalPanelLayout.createSequentialGroup()
                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(AnatomicalPanelLayout.createSequentialGroup()
                        .add(0, 0, 0)
                        .add(UBMicro)
                        .add(0, 0, 0)
                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(UBVeins)
                            .add(jLabel3)
                            .add(UBArtery))
                        .add(0, 0, 0)
                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(SVC)
                            .add(Pularteryd)
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(PulMcirc)
                                .add(0, 0, 0)
                                .add(jLabel8))
                            .add(Pulveinsa)
                            .add(Brachioceph))
                        .add(0, 0, 0)
                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(Pularteryc)
                                .add(0, 0, 0)
				      .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel13)
                                    .add(Pulmarteryb)))
                            .add(AscendingAb)
                            .add(Pulveinb)
                            .add(AscendingAc)
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(Brachiocepha)
                                .add(0, 0, 0)
                                .add(ThoracicAortab))
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(SVCd)
                                .add(0, 0, 0)
                                .add(IVCa))
                            .add(SVCb))
                        .add(0, 0, 0)
                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(IVCb)
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel21)
                                    .add(SVCc)
                                    .add(Pularterya))
                                .add(0, 0, 0)
                                .add(RTHeart)
                                .add(0, 0, 0)
                                .add(jLabel28))
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(AscendingA)
                                    .add(LTHeartb))
                                .add(0, 0, 0)
                                .add(LTHeart))
                            .add(ThoracicAorta)))
                    .add(AnatomicalPanelLayout.createSequentialGroup()
                        .add(19, 19, 19)
                        .add(desctitle)
                        .add(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(descpane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(AnatomicalPanelLayout.createSequentialGroup()
                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(abdomveins)
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(splanchnicVeins)
                                .add(0, 0, 0)
                                .add(renalVeins))
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(SplanchnicMicroa)
                                .add(0, 0, 0)
                                .add(SplanchnicMicrob)
                                .add(0, 0, 0)
                                .add(RenalMicroa))
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(SplanchnicArteries)
                                .add(0, 0, 0)
                                .add(renalarteries))
                            .add(abdomaorta))
                        .add(0, 0, 0)
                        .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(LBVeins)
                            .add(AnatomicalPanelLayout.createSequentialGroup()
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel40)
                                    .add(AnatomicalPanelLayout.createSequentialGroup()
                                        .add(0, 0, 0)
                                        .add(RenalMicrob))
                                    .add(AnatomicalPanelLayout.createSequentialGroup()
                                        .add(0, 0, 0)
                                        .add(jLabel43)))
                                .add(0, 0, 0)
                                .add(AnatomicalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel41)
                                    .add(jLabel44)))
                            .add(LBarteries))
                        .add(0, 0, 0)
                        .add(LBMicro))
                    .add(AnatomicalPanelLayout.createSequentialGroup()
                        .add(circtitle)
                        .add(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(circuitimage)
                        .add(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel5)
                        .add(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(flow)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        IllustrationTabs.addTab("Anatomical Overview", AnatomicalPanel);

        CircuitPanel.setBackground(new java.awt.Color(255, 255, 255));
        CircuitOverview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/Circuit.gif")));

        org.jdesktop.layout.GroupLayout CircuitPanelLayout = new org.jdesktop.layout.GroupLayout(CircuitPanel);
        CircuitPanel.setLayout(CircuitPanelLayout);
        CircuitPanelLayout.setHorizontalGroup(
            CircuitPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(CircuitPanelLayout.createSequentialGroup()
                .add(CircuitOverview)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CircuitPanelLayout.setVerticalGroup(
            CircuitPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(CircuitPanelLayout.createSequentialGroup()
                .add(CircuitOverview)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        IllustrationTabs.addTab("Circuit Overview", CircuitPanel);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
	layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(IllustrationTabs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 508, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(IllustrationTabs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );

    } // end constructor
 

    private void PularterycMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PularterycMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_PularterycMouseExited

    private void BrachiocephaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BrachiocephaMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_BrachiocephaMouseExited

    private void ThoracicAortabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThoracicAortabMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_ThoracicAortabMouseExited

    private void SVCcMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SVCcMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_SVCcMouseExited

    private void PulveinbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PulveinbMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_PulveinbMouseExited

    private void PulveinsaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PulveinsaMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_PulveinsaMouseExited

    private void PulMcircMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PulMcircMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_PulMcircMouseExited

    private void PularterydMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PularterydMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_PularterydMouseExited

    private void PularteryaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PularteryaMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_PularteryaMouseExited

    private void PulmarterybMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PulmarterybMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_PulmarterybMouseExited

    private void BrachiocephaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BrachiocephaMouseEntered
        desctitle.setText("Brachiocephalic Arteries");
        descarea.setText("These vessels supply oxygenated blood to the upper body arteries.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/3.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyleft.jpg")));
    }//GEN-LAST:event_BrachiocephaMouseEntered

    private void SVCcMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SVCcMouseEntered
        desctitle.setText("Superior Vena Cava");
        descarea.setText("This intra-thoracic vein carries de-oxygenated blood from the upper body veins to the right atrium.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/5.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_SVCcMouseEntered

    private void ThoracicAortabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThoracicAortabMouseEntered
        desctitle.setText("Thoracic Aorta:");
        descarea.setText("This section of the aorta is within the thorax and serves as a conduit of blood to the arteries of the viscera and lower extremities.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/6.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyright.jpg")));
    }//GEN-LAST:event_ThoracicAortabMouseEntered

    private void PulveinbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PulveinbMouseEntered
        desctitle.setText("Pulmonary Vein:");
        descarea.setText("The pulmonary veins deliver oxygenated blood to the left heart.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmonaryveins.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyright.jpg")));
    }//GEN-LAST:event_PulveinbMouseEntered

    private void PulveinsaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PulveinsaMouseEntered
        desctitle.setText("Pulmonary Vein:");
        descarea.setText("The pulmonary veins deliver oxygenated blood to the left heart.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmonaryveins.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyright.jpg")));
    }//GEN-LAST:event_PulveinsaMouseEntered

    private void PulMcircMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PulMcircMouseEntered
        desctitle.setText("Pulmonary Microcirculation:");
        descarea.setText("In the pulmonary microcirculation oxygen exchange takes place across the membranes separating pulmonary alveoli from the pulmonary capillary network. The pulmonary microcirculation thus receives de-oxygenated blood from the pulmonary arteries and returns oxygenated blood to the pulmonary veins.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/Resistor.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyrightde.jpg")));
    }//GEN-LAST:event_PulMcircMouseEntered

    private void PularterydMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PularterydMouseEntered
        desctitle.setText("Pulmonary Artery:");
        descarea.setText("The pulmonary arteries carry de-oxygenated blood from the right heart to the pulmonary microcirculation.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmonaryartery.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_PularterydMouseEntered

    private void PularterycMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PularterycMouseEntered
        desctitle.setText("Pulmonary Artery:");
        descarea.setText("The pulmonary arteries carry de-oxygenated blood from the right heart to the pulmonary microcirculation.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmonaryartery.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_PularterycMouseEntered

    private void PulmarterybMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PulmarterybMouseEntered
        desctitle.setText("Pulmonary Artery:");
        descarea.setText("The pulmonary arteries carry de-oxygenated blood from the right heart to the pulmonary microcirculation.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmonaryartery.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_PulmarterybMouseEntered

    private void PularteryaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PularteryaMouseEntered
        desctitle.setText("Pulmonary Artery:");
        descarea.setText("The pulmonary arteries carry de-oxygenated blood from the right heart to the pulmonary microcirculation.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/pulmonaryartery.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_PularteryaMouseEntered

    private void jLabel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseExited
        resetUIfunctions();
    }//GEN-LAST:event_jLabel13MouseExited

    private void jLabel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseEntered
        desctitle.setText("Superior Vena Cava");
        descarea.setText("The systemic upper body arteries carry oxygenated blood from the left heart to the upper body microcirculation.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/5.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_jLabel13MouseEntered

    private void AscendingAMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AscendingAMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_AscendingAMouseExited

    private void AscendingAMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AscendingAMouseEntered
        desctitle.setText("Ascending Aorta:");
        descarea.setText("The ascending aorta receives oxygenated blood directly from the left ventricle and delivers it to the major branches of the systemic arterial system.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/ascendingaorta.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyright.jpg")));
    }//GEN-LAST:event_AscendingAMouseEntered

    private void SVCbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SVCbMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_SVCbMouseExited

    private void SVCbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SVCbMouseEntered
        desctitle.setText("Superior Vena Cava");
        descarea.setText("This intra-thoracic vein carries de-oxygenated blood from the upper body veins to the right atrium.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/5.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_SVCbMouseEntered

    private void SVCdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SVCdMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_SVCdMouseExited

    private void SVCdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SVCdMouseEntered
        desctitle.setText("Superior Vena Cava");
        descarea.setText("This intra-thoracic vein carries de-oxygenated blood from the upper body veins to the right atrium.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/5.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_SVCdMouseEntered

    private void IVCaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IVCaMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_IVCaMouseExited

    private void IVCaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IVCaMouseEntered
        desctitle.setText("Inferior Vena Cava:");
        descarea.setText("Delivers de-oxygenated blood from the abdominal inferior vena cava to the hearts right atrium");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/15.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_IVCaMouseEntered

    private void RenalMicrobMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RenalMicrobMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_RenalMicrobMouseExited

    private void RenalMicroaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RenalMicroaMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_RenalMicroaMouseExited

    private void SplanchnicMicrobMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SplanchnicMicrobMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_SplanchnicMicrobMouseExited

    private void SplanchnicMicroaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SplanchnicMicroaMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_SplanchnicMicroaMouseExited

    private void RenalMicrobMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RenalMicrobMouseEntered
        desctitle.setText("Renal Microcirculation:");
        descarea.setText("The renal microcirculation represents the arterioles and capillary networks of the kidneys.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/Resistor.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyleftoxy.jpg")));
    }//GEN-LAST:event_RenalMicrobMouseEntered

    private void RenalMicroaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RenalMicroaMouseEntered
        desctitle.setText("Renal Microcirculation:");
        descarea.setText("The renal microcirculation represents the arterioles and capillary networks of the kidneys.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/Resistor.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyleftoxy.jpg")));
    }//GEN-LAST:event_RenalMicroaMouseEntered

    private void SplanchnicMicrobMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SplanchnicMicrobMouseEntered
        desctitle.setText("Splanchnic Microcirculation:");
        descarea.setText("The splanchnic microcirculation represents the arterioles and capillary networks of the abdominal viscera. ");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/Resistor.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyleftoxy.jpg")));
    }//GEN-LAST:event_SplanchnicMicrobMouseEntered

    private void SplanchnicMicroaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SplanchnicMicroaMouseEntered
        desctitle.setText("Splanchnic Microcirculation:");
        descarea.setText("The splanchnic microcirculation represents the arterioles and capillary networks of the abdominal viscera. ");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/Resistor.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyleftoxy.jpg")));
    }//GEN-LAST:event_SplanchnicMicroaMouseEntered

    private void RTHeartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RTHeartMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_RTHeartMouseExited

    private void RTHeartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RTHeartMouseEntered
        desctitle.setText("Right Heart:");
        descarea.setText("During diastole, the right heart receives de-oxygenated blood from the superior and inferior vena cavae; during systole, it generates the required pressure to propel the blood into the pulmonary arteries. The right heart is comprised of the right atrium and the right ventricle.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/heart.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_RTHeartMouseEntered

    private void LTHeartbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LTHeartbMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_LTHeartbMouseExited

    private void LTHeartbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LTHeartbMouseEntered
        desctitle.setText("Left Heart:");
        descarea.setText("During diastole, the left heart receives oxygenated blood from the pulmonary veins; during systole, it generates the required pressure to propel the blood into the ascending aorta. The left heart is comprised of the left atrium and the left ventricle.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/heart.gif")));
         flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyright.jpg")));
    }//GEN-LAST:event_LTHeartbMouseEntered

    private void LTHeartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LTHeartMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_LTHeartMouseExited

    private void LTHeartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LTHeartMouseEntered
        desctitle.setText("Left Heart:");
        descarea.setText("During diastole, the left heart receives oxygenated blood from the pulmonary veins; during systole, it generates the required pressure to propel the blood into the ascending aorta. The left heart is comprised of the left atrium and the left ventricle.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/heart.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyright.jpg")));
    }//GEN-LAST:event_LTHeartMouseEntered

    private void AscendingAcMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AscendingAcMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_AscendingAcMouseExited

    private void AscendingAcMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AscendingAcMouseEntered
        desctitle.setText("Ascending Aorta:");
        descarea.setText("The ascending aorta receives oxygenated blood directly form the left ventricle and delivers it to the major branches of the systemic arterial system.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/ascendingaorta.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyright.jpg")));
    }//GEN-LAST:event_AscendingAcMouseEntered

    private void AscendingAbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AscendingAbMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_AscendingAbMouseExited

    private void AscendingAbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AscendingAbMouseEntered
        desctitle.setText("Ascending Aorta:");
        descarea.setText("The ascending aorta receives oxygenated blood directly from the left ventricle and delivers it to the major branches of the systemic arterial system.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/ascendingaorta.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyright.jpg")));
    }//GEN-LAST:event_AscendingAbMouseEntered

    private void splanchnicVeinsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_splanchnicVeinsMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_splanchnicVeinsMouseExited

    private void splanchnicVeinsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_splanchnicVeinsMouseEntered
        desctitle.setText("Splanchnic Veins:");
        descarea.setText("Returns de-oxygenated blood from the intra-abdominal viscera to the abdominal inferior vena cava.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/911.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyleft.jpg")));
    }//GEN-LAST:event_splanchnicVeinsMouseEntered

    private void SplanchnicArteriesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SplanchnicArteriesMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_SplanchnicArteriesMouseExited

    private void SplanchnicArteriesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SplanchnicArteriesMouseEntered
        desctitle.setText("Splanchnic Arteries:");
        descarea.setText("Branching off the abdominal aorta, the splanchnic arteries supply oxygenated blood to the internal abdominal viscera, including the GI tract, liver, spleen and pancreas.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/810.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyleft.jpg")));
    }//GEN-LAST:event_SplanchnicArteriesMouseEntered

    private void renalVeinsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_renalVeinsMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_renalVeinsMouseExited

    private void renalVeinsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_renalVeinsMouseEntered
        desctitle.setText("Renal Veins:");
        descarea.setText("Return de-oxygenated blood from the kidneys to the abdominal vein.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/911.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyleft.jpg")));
    }//GEN-LAST:event_renalVeinsMouseEntered

    private void renalarteriesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_renalarteriesMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_renalarteriesMouseExited

    private void renalarteriesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_renalarteriesMouseEntered
        desctitle.setText("Renal Artery:");
        descarea.setText("Branching off the abdominal aorta, the renal artery supplies oxygenated blood to the kidneys.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/810.jpg")));
         flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyleft.jpg")));
    }//GEN-LAST:event_renalarteriesMouseEntered

    private void IVCbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IVCbMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_IVCbMouseExited

    private void IVCbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IVCbMouseEntered
        desctitle.setText("Inferior Vena Cava:");
        descarea.setText("Delivers de-oxygenated blood from the abdominal inferior vena cava to the hearts right atrium");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/15.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_IVCbMouseEntered

    private void ThoracicAortaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThoracicAortaMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_ThoracicAortaMouseExited

    private void ThoracicAortaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThoracicAortaMouseEntered
        desctitle.setText("Thoracic Aorta:");
        descarea.setText("This section of the aorta is within the thorax and serves as a conduit of blood to the arteries of the viscera and lower extremities.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/6.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyright.jpg")));
    }//GEN-LAST:event_ThoracicAortaMouseEntered

    private void abdomveinsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abdomveinsMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_abdomveinsMouseExited

    private void abdomveinsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abdomveinsMouseEntered
        desctitle.setText("Abdominal Veins:");
        descarea.setText("The abdominal portion of the inferior vena cava that returns de-oxygenated blood from the abdominal viscera and lower body circulation to the thoracic portion of inferior vena cava. ");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/14.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_abdomveinsMouseEntered

    private void abdomaortaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abdomaortaMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_abdomaortaMouseExited

    private void abdomaortaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abdomaortaMouseEntered
        desctitle.setText("Abdominal Aorta:");
        descarea.setText("A continuation of the thoracic aorta, the abdominal aorta carries oxygenated blood to the splanchnic, renal, and lower body arterial branches.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/7.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyright.jpg")));
    }//GEN-LAST:event_abdomaortaMouseEntered

    private void SVCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SVCMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_SVCMouseExited

    private void SVCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SVCMouseEntered
        desctitle.setText("Superior Vena Cava");
        descarea.setText("This intra-thoracic vein carries de-oxygenated blood from the upper body veins to the right atrium.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/5.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyright.jpg")));
    }//GEN-LAST:event_SVCMouseEntered

    private void BrachiocephMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BrachiocephMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_BrachiocephMouseExited

    private void BrachiocephMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BrachiocephMouseEntered
        desctitle.setText("Brachiocephalic Arteries:");
        descarea.setText("These vessels supply oxygenated blood to the upper body arteries.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/2.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyleft.jpg")));
    }//GEN-LAST:event_BrachiocephMouseEntered

    private void LBVeinsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LBVeinsMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_LBVeinsMouseExited

    private void LBVeinsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LBVeinsMouseEntered
        desctitle.setText("Lower Body Veins:");
        descarea.setText("The lower body veins return de-oxygenated blood to the abdominal inferior vena cava.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/13.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyleft.jpg")));
    }//GEN-LAST:event_LBVeinsMouseEntered

    private void LBarteriesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LBarteriesMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_LBarteriesMouseExited

    private void LBarteriesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LBarteriesMouseEntered
        desctitle.setText("Lower Body Arteries:");
        descarea.setText("The lower body arteries carry oxygenated blood from the abdominal aorta to the lower body microcirculation.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/12.jpg")));
         flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyleft.jpg")));
    }//GEN-LAST:event_LBarteriesMouseEntered

    private void LBMicroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LBMicroMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_LBMicroMouseExited

    private void LBMicroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LBMicroMouseEntered
        desctitle.setText("Lower Body Microcirculation:");
        descarea.setText("The lower body microcirculation represents the arterioles and capillary network of all blood vessels in the lower body. It receives oxygenated blood from the systemic lower body arteries and returns de-oxygenated blood to the systemic lower body veins.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/Resistor.gif")));
         flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyleftoxy.jpg")));
    }//GEN-LAST:event_LBMicroMouseEntered

    private void UBVeinsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UBVeinsMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_UBVeinsMouseExited

    private void UBVeinsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UBVeinsMouseEntered
        desctitle.setText("Upper Body Veins:");
        descarea.setText("The upper body veins receive de-oxygenated blood from the upper body capillary networks and pass it to the superior vena cava.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/4.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyleft.jpg")));
    }//GEN-LAST:event_UBVeinsMouseEntered

    private void resetUIfunctions(){
        descarea.setText(null);
        circuitimage.setIcon(null);
        flow.setIcon(null);
    }
    
    private void UBArteryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UBArteryMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_UBArteryMouseExited

    private void UBArteryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UBArteryMouseEntered
        desctitle.setText("Upper Body Arteries:");
        descarea.setText("The upper body arteries carry oxygenated blood from the aortic branches to the upper body microcirculation.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/3.jpg")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/oxyleft.jpg")));
    }//GEN-LAST:event_UBArteryMouseEntered

    private void UBMicroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UBMicroMouseExited
        resetUIfunctions();
    }//GEN-LAST:event_UBMicroMouseExited

    private void UBMicroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UBMicroMouseEntered
        desctitle.setText("Upper Body Microcirculation:");
        descarea.setText("The upper body microcirculation represents the arterioles and capillary networks of all blood vessels in the upper body. It receives oxygenated blood from the upper body arteries and returns de-oxygenated blood to the upper body veins.");
        circuitimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/Resistor.gif")));
        flow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images21C/deoxyleftoxy.jpg")));
    }//GEN-LAST:event_UBMicroMouseEntered
    
   
}
