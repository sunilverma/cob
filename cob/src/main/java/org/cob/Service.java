package org.cob;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class Service implements JavaDelegate{

	
	public boolean getServiceResult(){
		
		return true;
	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {
//		System.out.println("\n\n\n\n I am called :::\n\n\n" + execution.getId() + "---- "+ execution.getParentId());
		boolean var = (boolean) execution.getVariable("input");
	    execution.setVariable("input", var);
	    
	}
}
