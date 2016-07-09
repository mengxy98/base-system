package com.net.base.ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
	
	private static ThreadPoolExecutor pool = null;
	private static ThreadPoolManager poolManager = null;
	
	public ThreadPoolManager() {
		//初始化线程池
		int coreNum = Runtime.getRuntime().availableProcessors();
		pool = new ThreadPoolExecutor(
				coreNum, 
				coreNum*2, 
				4000, 
				TimeUnit.SECONDS, 
				new ArrayBlockingQueue<Runnable>(8), 
				new ThreadPoolExecutor.DiscardOldestPolicy());
	}
	
	public static ThreadPoolExecutor getInstance(){
		if(poolManager == null){
			poolManager = new ThreadPoolManager();
		}
		return pool;
	}
	
}
