package org.tinygroup.cepcoreremoteimpl.test.multiplesc.stop;

import java.util.ArrayList;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcoreremoteimpl.node.NodeOperator;
import org.tinygroup.tinyrunner.Runner;

/**
 * 顺序无关启动TestServerA\TestClientC\TestClientAStop
 * 如果jvm能在stop后停止，则无问题
 * @author chenjiao
 *
 */
public class TestClientAStop {
	public static void main(String[] args) throws Exception {

		Runner.init("applicationa.xml", new ArrayList<String>());
		Thread.sleep(20000);
		NodeOperator operator = BeanContainerFactory.getBeanContainer(
				TestClientAStop.class.getClassLoader()).getBean("nodea");
		CEPCore cep = BeanContainerFactory.getBeanContainer(
				TestClientAStop.class.getClassLoader()).getBean(
				CEPCore.CEP_CORE_BEAN);
		operator.stopCEPCore(cep);
	}
}
