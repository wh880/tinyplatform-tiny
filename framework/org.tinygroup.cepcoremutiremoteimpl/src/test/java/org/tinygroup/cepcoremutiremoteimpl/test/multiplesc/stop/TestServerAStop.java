package org.tinygroup.cepcoremutiremoteimpl.test.multiplesc.stop;
import java.util.ArrayList;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcoremutiremoteimpl.sc.ScOperator;
import org.tinygroup.tinyrunner.Runner;
/**
 * 顺序无关启动TestClientA\TestClientC\TestServerAStop
 * 如果jvm能在stop后停止，则无问题
 * @author chenjiao
 *
 */
public class TestServerAStop {
	public static void main(String[] args) throws Exception {
		Runner.init("applicationserver.xml", new ArrayList<String>());
		
		Thread.sleep(20000);
		ScOperator operator = BeanContainerFactory.getBeanContainer(
				TestClientAStop.class.getClassLoader()).getBean("sc");
		CEPCore cep = BeanContainerFactory.getBeanContainer(
				TestClientAStop.class.getClassLoader()).getBean(
				CEPCore.CEP_CORE_BEAN);
		operator.stopCEPCore(cep);
	}
}
