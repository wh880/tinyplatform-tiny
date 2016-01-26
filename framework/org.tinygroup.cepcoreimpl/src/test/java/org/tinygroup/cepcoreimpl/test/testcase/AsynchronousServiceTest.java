package org.tinygroup.cepcoreimpl.test.testcase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcoreimpl.test.AsynchronousEventProcessorForTest;
import org.tinygroup.event.Event;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class AsynchronousServiceTest extends CEPCoreBaseTestCase {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AsynchronousServiceTest.class);
	private final int LENGTH = 1;
	private final int THREAD = 200;
	private long start = 0;
	private List<Long> sucess = new ArrayList<Long>();
	private List<Long> fail  = new ArrayList<Long>();
	public void setUp() {
		super.setUp();
		init();
	}

	private void init() {
		EventProcessor eventProcessor = new AsynchronousEventProcessorForTest();
		eventProcessor.getServiceInfos().add(initServiceInfo("a"));
		getCore().registerEventProcessor(eventProcessor);
	}

	public void testService() {
		start = System.currentTimeMillis();
		for (int i = 0; i < THREAD; i++) {
			Thread t = new Thread(new SimpleServiceThread(getCore()));
			t.start();
		}
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {

		}
	}

	private synchronized void addSuccess(long time) {
		sucess.add(time);
		stop();
	}

	private synchronized void addException(long time) {
		fail.add(time);
		stop();
	}
	
	private void stop(){
		int total = sucess.size()+fail.size();
		if(total == LENGTH*THREAD){
			print("fail", fail);
			print("sucess", sucess);
			System.out.println("sucess:"+sucess.size());
			System.out.println("fail:"+fail.size());
			System.out.println(System.currentTimeMillis()-start);
		}
	}
	private void print(String s,List<Long> list){
		for(Long time:list){
			Date d = new Date(time);
			System.out.println(s+":"+d.toGMTString());
		}
		
	}

	public void sendService() {
		Event e = getEvent("a");
		e.setMode(Event.EVENT_MODE_ASYNCHRONOUS);
		try {
			getCore().process(e);
			assertTrue(true);
			addSuccess(System.currentTimeMillis());
		} catch (Exception e2) {
			addException(System.currentTimeMillis());
//			LOGGER.errorMessage("服务异常 {}", e2, System.currentTimeMillis());
		}
	}

	class SimpleServiceThread implements Runnable {
		CEPCore cep;

		public SimpleServiceThread(CEPCore cep) {
			this.cep = cep;
		}

		public void run() {
			int i = 0;
			while (i < LENGTH) {
				sendService();
				i++;
			}
		}
	}

}
