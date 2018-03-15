package demo;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * �������
 * @author QI
 * @date 2018-03
 */
public class CreateTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void startProcessInstance() {  
	    // ���̶����key  
	    String processDefinitionKey = "myProcess";
	    // ������ִ�е�����ʵ����ִ�ж�����ص�Service  
	    ProcessInstance pi = processEngine.getRuntimeService()
	            .startProcessInstanceByKey(processDefinitionKey);
	 // ʹ�����̶����key��������ʵ����key��ӦHelloWorld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������  
	    System.out.println("����ʵ��ID:" + pi.getId());  
	    System.out.println("���̶���ID:" + pi.getProcessDefinitionId());  
	}
	
}
