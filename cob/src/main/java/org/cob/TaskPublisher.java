package org.cob;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.cob.dao.ATOMSMessageRepository;
import org.cob.model.ATOMSMessage;
import org.cob.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sun.research.ws.wadl.Application;
@Service
public class TaskPublisher implements TaskListener{

	
	private static final long serialVersionUID = 1L;
	@Override
	public void notify(DelegateTask delegateTask) {
		
		Client client= (Client)delegateTask.getVariable("client");
		if(client!=null){
			Gson gson = new Gson();	
	    	String message =gson.toJson(client);
	    	message = message + delegateTask.getName();
	    	ATOMSMessage atomsMessage = new ATOMSMessage();
	    	atomsMessage.setClientName(client.getName());
	    	atomsMessage.setProcessId(delegateTask.getProcessInstanceId());
	    	atomsMessage.setTaskId(delegateTask.getId());
	    	atomsMessage.setTaskName(delegateTask.getName());
	    	// AppCon
	    	//atomsRepo.save(atomsMessage);
	    	((ATOMSMessageRepository)SpringApplicationContext.getBean("atomsMessageRepo")).save(atomsMessage);
	    	message =gson.toJson(atomsMessage);
	    	System.out.println("Sending Task message to ATOMS."+ message);
		}
		else{
			System.out.println("CLient is null for ."+ delegateTask.getName());
		}
			
	}

}
