package org.cob;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskStartListener implements TaskListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("Process Id: "+ delegateTask.getProcessInstanceId() +" Execution Id: "+ delegateTask.getExecutionId());
		
	}

}
