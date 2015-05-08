package org.tinygroup.cepcoreremoteimpl.test.multiplesc.stop;
import java.util.ArrayList;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcoreremoteimpl.sc.ScOperator;
import org.tinygroup.tinyrunner.Runner;

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
