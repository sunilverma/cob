package org.cob.config;

import javax.sql.DataSource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class Config {

	@Bean 
 	public DataSourceTransactionManager txManager(DataSource dataSource){
 		return new DataSourceTransactionManager(dataSource);
 	}
	

 	@Bean
    public CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService) {

        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                System.out.println(">>>>>>>>>>>>>\n\n\n\nNumber of process definitions : "
                	+ repositoryService.createProcessDefinitionQuery().list());
                System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
                runtimeService.startProcessInstanceByKey("myProcess");
                System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
            }
        };

    }
}
