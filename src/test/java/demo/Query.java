package demo;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class Query {

	@Test
	public void startProcess(){
		String assignee = "CHENGJIE";
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<Task> tasks = processEngine.getTaskService().createTaskQuery()
				.taskAssignee(assignee).list();
		for (Task task : tasks) {
			System.out.println("taskid:"+task.getId()+",taskName:"+task.getName());
		}
	}
}
