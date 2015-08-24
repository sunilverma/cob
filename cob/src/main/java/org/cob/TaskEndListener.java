package org.cob;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;

public class TaskEndListener  implements TaskListener{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		//System.out.println("\n\n TaskEndListener::: I am called :::\n\n\n" + delegateTask.getVariables());
		boolean var = (boolean) delegateTask.getVariable("input");
		var=true;
		delegateTask.setVariable("input", var);		
	}

}
