package org.cob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.cob.dao.ClientJpaRepository;
import org.cob.model.Client;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;







import com.google.gson.Gson;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	 @Autowired
	    private RuntimeService runtimeService;
	 	
	 	@Autowired
		private TaskService taskService;
	    
	 	@Autowired
	 	private ClientJpaRepository clientRepository;
	    @RequestMapping(value = "/startProcess", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
	    public ProcessId startProcess(@RequestBody Map<String, String> data) {
	 		Map<String,Object> varibales = new HashMap<String,Object>();
	 		varibales.put("input", false);
	 		Client client = new Client(data.get("name"),data.get("country"));
	 		clientRepository.save(client);
	 		varibales.put("client", client);
	 		ProcessInstance processInstance =runtimeService.startProcessInstanceByKey("myProcess",varibales);
	 		System.out.println("processInstance.id: "+processInstance.getId());
	 		return new ProcessId(processInstance.getId());
	    }
	    
	    @ResponseStatus(value = HttpStatus.OK)
	    @RequestMapping(value = "/listTask", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Map<String,String>> listTask(@RequestParam(value="processInstanceId") String processInstanceId) {
	    	List<Map<String,String>> reponse=new ArrayList<Map<String,String>>();
	    	List<Task> tasks= taskService.createTaskQuery()
	                .processInstanceId(processInstanceId)
	                .orderByTaskName().asc()
	                .list();
	    	
	    	for(Task task:tasks){
	    		Map<String,String> taskNameList= new HashMap<String, String>();
	    		taskNameList.put("taskId", task.getId());
	    		taskNameList.put("taskName", task.getName());
	    		reponse.add(taskNameList);
	    	}

	    	return reponse;
	    }
	    
	    @ResponseStatus(value = HttpStatus.OK)
	    @RequestMapping(value = "/setupNewClientFlow", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    public String setupNewClientFlow(){
	    	
	    	
	    	return "Success";
	    }
	    @ResponseStatus(value = HttpStatus.OK)
	    @RequestMapping(value = "/alertSignal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public void invokeSignal(){
	    	runtimeService.signalEventReceived("alert");
	    	List<Execution> executions = runtimeService.createExecutionQuery().signalEventSubscriptionName("alert").list();
	    	for(Execution exec: executions){
	    		System.out.println(exec.getProcessInstanceId());
	    		System.out.println(exec.getParentId());
	    	}
	    }
	    
	    @ResponseStatus(value = HttpStatus.OK)
	    @RequestMapping(value = "/completeSystemConfTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    public String completeTask(@RequestBody Map<String, Object> data) {
	    	taskService.complete(data.get("taskId").toString(),data);
	    	return "Task Id: "+data.get("taskId")+" completed!!!.";
	    }
	    
	    @ResponseStatus(value = HttpStatus.OK)
	    @RequestMapping(value = "/completeTask", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public String completeTask(@RequestParam(value="taskId") String taskId) {
	    	taskService.complete(taskId);
	    	return "Task Id: "+taskId+" completed!!!.";
	    }

	    @Autowired
	    private RepositoryService repositoryService;
	    @RequestMapping(value = "/listProcess", method = RequestMethod.GET)
	    public List<ProcessDefinition> listProcess(){
	    	System.out.println(">>>>>>>>>>>>>\n\n\n\nNumber of process definitions : "
                	+ repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionId().desc().list());
	    	//String joined = StringUtils.join( ,"+");
	    	//return new Gson().toJson(repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionId().desc().list());
	    	return repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionId().desc().list();
	    }
}
