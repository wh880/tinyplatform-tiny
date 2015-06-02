package org.tinygroup.cepcoremutiremoteimpl.test.testcase.counter;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.event.Event;
import org.tinygroup.threadgroup.AbstractProcessor;

/**
 * @author chenjiao
 *
 * 此线程是请求线程,会不停的向cepcore发送add服务请求，该服务会在执行一个对静态变量的+1操作.
 * 在此服务调用次数计数统计之中.
 * 服务调用方为TestClientCounter，服务提供方(执行方)为TestClient2Counter，TestClient3Counter.
 * 启动TestServer(SC).
 * 启动TestClient2Counter或者TestClient3Counter.
 * 启动TestClientCounter.
 * 在TestClient2Counter级TestClient3Counter上会启动打印线程.
 * 该线程会打印每秒时间内，该静态变量值的变化情况.
 * 由于每次服务调用都会对变量值+1，所以该值变化及该单位时间内所发生的服务调用次数.
 * 
 */
public class CounterDealProcessor extends AbstractProcessor {
	int count;
	CEPCore cep;
	public CounterDealProcessor(CEPCore cep, String name) {
		super(name);
		this.cep = cep;
	}

	protected void action() throws Exception {
		while(true){
			try {
				Event t = getEvent();
				cep.process(t);
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
	}
	
	public static Event getEvent() {
		Context c = new ContextImpl();
		c.put("name", "name");
		Event e = Event.createEvent("add", c);
		return e;
	}

}
