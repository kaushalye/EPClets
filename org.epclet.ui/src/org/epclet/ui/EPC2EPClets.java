package org.epclet.ui;

import java.util.ArrayList;

import org.epclet.ePClet.Block;
import org.epclet.ePClet.EPClet;
import org.epclet.ePClet.EPCletFactory;
import org.epclet.ePClet.Script;
import org.epclet.ePClet.impl.EPCletFactoryImpl;
import org.processmining.framework.models.epcpack.ConfigurableEPC;
import org.processmining.framework.models.epcpack.EPCFunction;

import au.edu.swin.ict.serendip.epc.EPCToSerendip;

public class EPC2EPClets {
    public static void EPCToEPClets(ConfigurableEPC epc){
 	ArrayList<EPCFunction> functions = epc.getFunctions();
 	EPCletFactory ePCletFactory = new EPCletFactoryImpl();
 	Block block = ePCletFactory.createBlock();
 	for (int i = 0; i < functions.size(); i++) {
 	    EPCFunction f = functions.get(i);
 	    EPClet elet = ePCletFactory.createEPClet();
 	    elet.setTask(f.getName());
 	    elet.getPre().setValue(  EPCToSerendip.getPreEventPatternOfFunction(f));
 	    elet.getPost().setValue(  EPCToSerendip.getPostEventPatternOfFunction(f));
 	    if(f.getOrgObjects().size()>0){
 		elet.getRole().setRoleId(f.getOrgObject(0).getLabel());
 	    }
	    block.getEpclets().add(elet); 	    
 	}
 	block.getName().setName( epc.getName());
 	
 	Script script = ePCletFactory.createScript();
 	script.getBlock().add(block);
 	
 	//TODO Seralize and see
     }
}
