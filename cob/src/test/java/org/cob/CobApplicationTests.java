package org.cob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CobApplication.class)
public class CobApplicationTests {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Test
	public void contextLoads() {
		ProcessInstance processInstance =runtimeService.startProcessInstanceByKey("myProcess");
		System.out.println(processInstance.getId());
		
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println(">>>>  "+task.isSuspended());
		List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .orderByTaskName().asc()
                .list();
		System.out.println(tasks.size()+" >> "+tasks.get(0).getName());
		System.out.println(taskService.getTaskEvents(tasks.get(0).getId()));
		taskService.complete(tasks.get(0).getId());
		System.out.println( taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .orderByTaskName().asc()
                .list());
		
	}

}
