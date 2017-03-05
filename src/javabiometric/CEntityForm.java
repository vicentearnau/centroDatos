package javabiometric;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.lang.Exception ;
import java.awt.Color;
import java.awt.event.*;



public class CEntityForm extends JFrame {

  class BJPanel extends JPanel 
  {
    public BufferedImage bi;
    public BJPanel (){
        this.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent m){
        JOptionPane.showMessageDialog (null,"("+Integer.toString(m.getPoint().x)+";"+Integer.toString(m.getPoint().y)+")","Point",JOptionPane.PLAIN_MESSAGE);
        }
        });
    }
    public BJPanel (BufferedImage bi){
      this.bi = bi;
      setPreferredSize(new Dimension(bi.getWidth(),bi.getHeight())) ;
    }
    public void setBufferedImage(BufferedImage bi)
    {
      this.bi = bi;
      setPreferredSize(new Dimension(bi.getWidth(),bi.getHeight())) ;
      this.repaint();
    }
    public void paintComponent(Graphics g)
    {
      g.drawImage(bi,0,0,this) ;
    }
  }

  private JToolBar jtool = new JToolBar();
  private JPanel jimage = new JPanel();
  private JButton jButtonStep1 = new JButton("Calculation");
  private JButton jButtonStep2 = new JButton("Image Processing");
  private JButton jButtonStep3 = new JButton("1 to 1 Match");
  private JButton jButtonStep4 = new JButton("1 to m Match");
  private JTextField jTextField1 = new JTextField();
  private JTextField jTextField2 = new JTextField();
  
  //uses our finger print libery
  private CFingerPrint m_finger1 = new CFingerPrint();
  private CFingerPrint m_finger2 = new CFingerPrint();
  private CFingerPrintGraphics m_fingergfx = new CFingerPrintGraphics();
  private BJPanel m_panel1 = new BJPanel();
  private BJPanel m_panel2 = new BJPanel();
  private BufferedImage m_bimage1 = new BufferedImage(m_finger1.FP_IMAGE_WIDTH ,m_finger1.FP_IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB );
  private BufferedImage m_bimage2 = new BufferedImage(m_finger2.FP_IMAGE_WIDTH ,m_finger2.FP_IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB );
  private double finger1[] = new double[m_finger1.FP_TEMPLATE_MAX_SIZE];
  private double finger2[] = new double[m_finger2.FP_TEMPLATE_MAX_SIZE];
  
  
  public CEntityForm() {
   jButtonStep1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonStep1_actionPerformed(e);
      }
    });
    jButtonStep2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonStep2_actionPerformed(e);
      }
    });
    jButtonStep3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonStep3_actionPerformed(e);
      }
    });
  
    jtool.setLayout(new GridLayout(4,1));
    jtool.add(jButtonStep1);
    jtool.add(jButtonStep2);
    jtool.add(jButtonStep3);
    jtool.add(jTextField1);
    jtool.add(jTextField2);
      
    this.getContentPane().setLayout(new GridLayout(2,2));
    this.getContentPane().add(m_panel1);
    this.getContentPane().add(m_panel2);
    this.getContentPane().add(jtool);
   
    this.setTitle("Entity");
    this.setSize(new Dimension(900, 700)) ;
  }

  private void jButtonStep1_actionPerformed(ActionEvent e)
  {
	  try
	    {        
	    //picture1
	    //Set picture new
	    m_bimage1=ImageIO.read(new File(new java.io.File("").getAbsolutePath()+"/ProcessedSample1.bmp")) ;
	    m_panel1.setBufferedImage(m_bimage1);
	    //Send image for skeletinization
	    m_finger1.setFingerPrintImage(m_bimage1) ;
	    finger1=m_finger1.getFingerPrintTemplate();
	     //See what skeletinized image looks like
	    m_bimage1 = m_finger1.getFingerPrintImageDetail();
	    m_panel1.setBufferedImage(m_bimage1);
	    jTextField1.setText(m_finger1.ConvertFingerPrintTemplateDoubleToString(finger1));
	        
	    //picture2
	    //Set picture new
	    m_bimage2=ImageIO.read(new File(new java.io.File("").getAbsolutePath()+"/ProcessedSample2.bmp")) ;
	    m_panel2.setBufferedImage(m_bimage2);
	    //Send image for skeletinization
	    m_finger2.setFingerPrintImage(m_bimage2) ;
	    finger2=m_finger2.getFingerPrintTemplate();
	    //See what skeletinized image looks like
	    m_bimage2 = m_finger2.getFingerPrintImageDetail();
	    m_panel2.setBufferedImage(m_bimage2);
	    jTextField2.setText(m_finger2.ConvertFingerPrintTemplateDoubleToString(finger2));
	         
	      
	    
	    }
	    catch (Exception ex)
	    {
	    JOptionPane.showMessageDialog (null,ex.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
	    }        

  }

  private void jButtonStep2_actionPerformed(ActionEvent e)
  {
      try
      {
    	  m_bimage1=ImageIO.read(new File(new java.io.File("").getAbsolutePath()+"/ProcessedSample1.bmp")) ;
    	    m_panel1.setBufferedImage(m_bimage1);
    	    
    	    //Send image for skeletinization
    	    m_finger1.setFingerPrintImage(m_bimage1) ;
    	    finger1=m_finger1.getFingerPrintTemplate();
    	     
    	    //See what skeletinized image looks like
    	    m_bimage1 = m_finger1.getFingerPrintImageDetail();
    	    //Print skeletinized fingerprint
    	    m_panel2.setBufferedImage(m_bimage1);

    	    //m_panel1.setBufferedImage(m_bimage1);
    	    jTextField1.setText(m_finger1.ConvertFingerPrintTemplateDoubleToString(finger1));
    	    jTextField2.setText("");

       }
      catch (Exception ex)
      {
         JOptionPane.showMessageDialog (null,ex.getMessage() ,"Error Message",JOptionPane.PLAIN_MESSAGE);
      }
  }

  private void jButtonStep3_actionPerformed(ActionEvent e)
  {
      //match one print
     try
      {
        JOptionPane.showMessageDialog (null,Double.toString(m_finger1.Match(finger1 , finger2,65,false)),"Match %",JOptionPane.PLAIN_MESSAGE);
      }
      catch (Exception ex)
      {
      JOptionPane.showMessageDialog (null,ex.getMessage() ,"Error Message",JOptionPane.PLAIN_MESSAGE);
      }
  }



}//End Class entity