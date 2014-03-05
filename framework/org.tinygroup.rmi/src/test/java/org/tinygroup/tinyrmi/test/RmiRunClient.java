package org.tinygroup.tinyrmi.test;

import java.rmi.RemoteException;

import org.tinygroup.tinyrmi.RmiServer;
import org.tinygroup.tinyrmi.impl.RmiServerRemote;

public class RmiRunClient {
	private static String SERVERIP = "192.168.84.52";
//	private static String SERVERIP2 = "192.168.154.73";
//	private static Logger logger = LoggerFactory.getLogger(RmiRunClient.class);

	public static void main(String[] args) {
		RmiServer remoteServer = new RmiServerRemote(SERVERIP, 8888);
		try {
			remoteServer.registerRemoteObject(new HelloImpl(), "hello1");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RmiRunClient c = new RmiRunClient(remoteServer);
		c.run();

	}

	private RmiServer remoteServer;

	public RmiRunClient(RmiServer remoteServer) {
		this.remoteServer = remoteServer;
	}
	
	public void run(){
		MyThread t = new MyThread();
		t.run();
	}

	class MyThread extends Thread {
		boolean end = false;
		public void run() {
			while(!end){
				hello();
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		public void hello(){
			Hello hello = null;
			try {
				hello = remoteServer.getRemoteObject("hello");
			} catch (Exception e) {
				e.printStackTrace();
				//throw new RuntimeException("获取对象失败"+e.getMessage());
			}
			
			try {
				String info = hello.sayHello("abc");
				System.out.println(info);
				if (!"Hello,abc".equals(info)) {
					throw new RuntimeException("执行结果的字符串不匹配");
				}
			} catch (Exception e) {
				e.printStackTrace();
				//throw new RuntimeException("执行方法失败:"+e.getMessage());
			}
		}
	}
}
