package demo;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class Create {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void startProcessInstance() {  
	    // 流程定义的key  
	    String processDefinitionKey = "myProcess";  
	    ProcessInstance pi = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的Service  
	            .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应HelloWorld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动  
	    System.out.println("流程实例ID:" + pi.getId());  
	    System.out.println("流程定义ID:" + pi.getProcessDefinitionId());  
	}
	
}
