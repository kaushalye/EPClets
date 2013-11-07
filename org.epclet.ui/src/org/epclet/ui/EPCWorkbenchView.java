package org.epclet.ui;

 

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Timestamp;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*; 

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.epclet.ePClet.Block;
import org.epclet.ePClet.EPClet;
import org.epclet.ePClet.Script;
import org.processmining.framework.models.ModelGraphPanel;
import org.processmining.framework.models.epcpack.ConfigurableEPC;

import au.edu.swin.ict.serendip.core.SerendipException;
import au.edu.swin.ict.serendip.epc.EPMLWriter;
import au.edu.swin.ict.serendip.epc.MergeBehavior;
import au.edu.swin.ict.serendip.epc.PatternToEPC;
import au.edu.swin.ict.serendip.tool.gui.SerendipEPCView;

public class EPCWorkbenchView extends ViewPart implements ActionListener{
    private Composite  composite = null;
    private EPMLWriter epmlWriter = null;
    private JPanel mainPanel, viewPanel, controlPanel;
   
    private JLabel errorLabel = new JLabel("");
   // private JCheckBox cbCFOnly = new JCheckBox("Control Flow Only", true);
    private JButton refreshBtn = new JButton("Refresh");
  //  private JButton exportEPMLBtn = new JButton("EPML");
    private String sourceFileLocation = null;
    private Block block = null;
    public EPCWorkbenchView (){
	super();
    }
    @Override
    public void setFocus() {
	composite.setFocus();	
    }

    @Override
    public void createPartControl(Composite parent) {
	// TODO read (positioning): http://www.eclipse.org/articles/using-perspectives/PerspectiveArticle.html
	composite = new Composite(parent, SWT.EMBEDDED);
	java.awt.Frame frame = SWT_AWT.new_Frame( composite );
	this.mainPanel = new javax.swing.JPanel( );
	this.mainPanel.setLayout(new BorderLayout());
	this.viewPanel = new JPanel(new BorderLayout());
	
	//Control Panel
	this.controlPanel = new JPanel(new FlowLayout());
	//this.controlPanel.add(this.cbCFOnly); 
	//this.controlPanel.add(this.exportEPMLBtn);
	this.controlPanel.add(this.refreshBtn);
	//this.cbCFOnly.addActionListener(this);
	//this.exportEPMLBtn.addActionListener(this);
	this.refreshBtn.addActionListener(this);
	
	//main Panel
	//mainPanel.add(this.controlPanel, BorderLayout.NORTH);
	mainPanel.add(this.viewPanel, BorderLayout.CENTER);
	mainPanel.add(this.errorLabel, BorderLayout.SOUTH);
	frame.add(mainPanel);
 
    }
    
    public void updateView(String sourceFileLocation, Block block){
	if(block == null){return;}
	
	this.block = block;
	this.sourceFileLocation = sourceFileLocation;
	//this.label.setText(txt+ "Update "+ new Timestamp(new java.util.Date().getTime()));
	 
	    ConfigurableEPC result = null;
	    for(EPClet e: block.getEpclets()){
		//System.out.println(e.getPre().getValue() +"->"+e.getRole().getRoleId()+"."+e.getTask()+" -> "+e.getPost().getValue()); 
		try { 	
		    String preep=null, postep=null, taskid=null, roleid = null;
		    preep =  (e.getPre()!=null)? e.getPre().getValue(): null;
		    postep =  (e.getPost()!=null)? e.getPost().getValue(): null;
		    taskid =  (e.getTask()!=null)? e.getTask(): null;
		    roleid =  (e.getRole()!=null)? e.getRole().getRoleId(): null;
		    
		    result = MergeBehavior.mergeEPC(result, PatternToEPC.convertToEPC(preep, taskid, postep,  roleid, null, null));
		     
		} catch (Exception e1) {
		    // TODO Auto-generated catch block
		    this.errorLabel.setText(e1.getMessage());
		    e1.printStackTrace();
		}
	    }
	    
	   // SerendipEPCView serendipEPCView = new SerendipEPCView("test", result);
	    this.epmlWriter = new EPMLWriter(result, true);	 
	    ModelGraphPanel mgp = epmlWriter.getModelGraphPanel();
	    mgp.setScaleToFit(true);
	    
	    this.viewPanel.removeAll();
	    this.viewPanel.add(mgp, BorderLayout.CENTER);
	    this.viewPanel.repaint();
	    
	    //export the epml
	    try {
		this.epmlWriter.writeToFile(sourceFileLocation+"."+block.getName().getName()+".epml");
	    } catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	    this.errorLabel.setText("EPC graph "+block.getName().getName()+"constructed - "+    new Timestamp(new java.util.Date().getTime()));
	    this.setFocus();
	 
	
    }
    public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource().equals(this.refreshBtn)){
	    this.updateView(this.sourceFileLocation, this.block);
	}
    }
    
 


}
