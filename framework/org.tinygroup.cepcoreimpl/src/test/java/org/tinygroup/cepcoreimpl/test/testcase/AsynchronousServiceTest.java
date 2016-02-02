package org.tinygroup.cepcoreimpl.test.testcase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.event.Event;

/**
 * 采用的是默认的线程池，默认线程池是最大50，队列100，所以应该只会执行150个
 * 
 * @author chenjiao
 * 
 */
public class AsynchronousServiceTest extends CEPCoreBaseTestCase {

	private final int LENGTH = 1;
	private final int THREAD = 200;
	private long start = 0;
	private List<Long> sucess = new ArrayList<Long>();
	private List<Long> fail = new ArrayList<Long>();

	public void setUp() {
		super.setUp();
	}

	public void testService() {
		start = System.currentTimeMillis();
		for (int i = 0; i < THREAD; i++) {
			Thread t = new Thread(new SimpleServiceThread(getCore()));
			t.start();
		}
		try {
			Thread.sleep(10000);
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

	private void stop() {
		int total = sucess.size() + fail.size();
		if (total == LENGTH * THREAD) {
			print("fail", fail);
			print("sucess", sucess);
			System.out.println("sucess:" + sucess.size());
			assertEquals(150, sucess.size());
			assertEquals(50, fail.size());
			System.out.println("fail:" + fail.size());
			System.out.println(System.currentTimeMillis() - start);
			getCore().stop();
		}

	}

	private void print(String s, List<Long> list) {
		for (Long time : list) {
			Date d = new Date(time);
			System.out.println(s + ":" + d.toGMTString());
		}

	}

	public void sendService() {
		Event e = getEvent(SERVICE_ID);
		e.setMode(Event.EVENT_MODE_ASYNCHRONOUS);
		try {
			getCore().process(e);
			addSuccess(System.currentTimeMillis());
		} catch (Exception e2) {
			addException(System.currentTimeMillis());
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
