package demo;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class Deploy {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deployProcess(){
		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment()
				.addClasspathResource("MyProcess.bpmn")
				.addClasspathResource("MyProcess.png").deploy();
		System.out.println("ÒÑ²¿Êð"+deployment.getId()+deployment.getName());
	}
}
