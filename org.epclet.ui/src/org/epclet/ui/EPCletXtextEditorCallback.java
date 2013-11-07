package org.epclet.ui;

import java.awt.Frame;
import java.net.URI;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.AbstractDirtyStateAwareEditorCallback;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.resource.XtextResourceSetProvider;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.epclet.ePClet.Block;
import org.epclet.ePClet.EPClet;
import org.epclet.ePClet.Script;

import com.google.inject.Injector;

import au.edu.swin.ict.serendip.tool.gui.*;
import org.epclet.ui.internal.EPCletActivator; 
import org.eclipse.xtext.resource.XtextResource;
public class EPCletXtextEditorCallback extends AbstractDirtyStateAwareEditorCallback {
    private JFrame frame = null; 

    @Override
	public void afterSave(XtextEditor editor) {
		System.out.println("Refresh visualization!");
		
		this.visualize(editor );
	}
    
    private Script readModel(XtextEditor editor){
	Script script =null ;
	
	String path = editor.getResource().getFullPath().toString();
	ResourceSet rs = new ResourceSetImpl();
	Resource r = rs.getResource(org.eclipse.emf.common.util.URI.createURI(path), true);
	if(null == r){
	    System.out.println("resource is null");
	}else{
	    System.out.println("resource found "+path);
	}
	List<EObject> fileContent = r.getContents();
	 
	   script = (Script)fileContent.get(0);
	   if(null == script){
	       System.out.println("No script found");
	   }else{
	       	//debug
//        	   for(Block b: script.getBlock()){
//        	       System.out.println("Found block"+b.getName().getName());
//        	       for(EPClet e: b.getCommands()){
//        		   System.out.println(e.getPre().getValue() +"->"+e.getTask()+" -> "+e.getPost().getValue());
//        	       }
//        	   }
	   }
	 
	return script; 
    }
    private void visualize(XtextEditor editor ){
	IXtextDocument doc = editor.getDocument();
	 
	Script script =readModel(editor);
	
	//visualize
	for(Block b: script.getBlock()){
	    EPCWorkbenchView epcView = (EPCWorkbenchView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("org.epclet.epcView.epcview");
	    
	    epcView.updateView(editor.getResource().getLocation().toString(), b);
	}
	
	
	
	
 
    }
}
