package org.tinygroup.tinypc.test;

import java.util.ArrayList;
import java.util.List;

public class Test2 {
	public static void main(String[] args) {
		//准备work
		MyWork worka = new MyWork("a");
		
		MyWork workb = new MyWork("b");
		worka.setNextWork(workb);
		
		MyWork workc = new MyWork("c");
		workb.setNextWork(workc);
		
		workb.getSubWorks().add(new MyWork("b1"));
		workb.getSubWorks().add(new MyWork("b2"));
		
		
		//准备worker
		List<MyWorker> workers = new ArrayList<MyWorker>();
		addWorkers("a",workers,1);
		addWorkers("b",workers,2);
		addWorkers("b1",workers,3);
		addWorkers("b2",workers,4);
		addWorkers("c",workers,5);
		dealWork(worka, workers);
		
	}
	
	private static void dealWork(MyWork work,List<MyWorker> workers){
		List<MyWorker> mineWorker = findMineWorker(work, workers);
		doWork(work, mineWorker);
		if(work.getSubWorks().size()!=0){
			for(MyWork subWork:work.getSubWorks()){
				dealWork(subWork, workers);
			}
		}
		if(work.getNextWork()!=null){
			dealWork(work.getNextWork(), workers);
		}
	}
	
	/**
	 * 本函数就是最终要替换成用工头去做
	 * @param work
	 * @param workers
	 */
	private static void doWork(MyWork work,List<MyWorker> workers){
		for(MyWorker worker:workers){
			worker.deal(work);
		}
	}
	
	private static List<MyWorker> findMineWorker(MyWork work,List<MyWorker> workers){
		List<MyWorker> mineWorkers = new ArrayList<MyWorker>();
		String workType = work.getType();
		for(MyWorker worker:workers){
			if(workType.equals(worker.getType())){
				//workers.remove(worker);
				mineWorkers.add(worker);
			}
		}
		return mineWorkers;
	}
	
	private static void addWorkers(String type,List<MyWorker> workers,int num){
		for(int i = 0 ; i < num ; i ++ ){
			MyWorker worker = new MyWorker(type);
			workers.add(worker);
		}
	}
}
