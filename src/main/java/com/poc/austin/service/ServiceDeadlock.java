package com.poc.austin.service;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * REST service to monitor the multiple threads and detect the deadlock
 * @author Somnath
 *
 */

@Service
public class ServiceDeadlock {

	private Logger log = Logger.getLogger(ServiceDeadlock.class);

	public String checkDeadLock() {
		Map<Thread, StackTraceElement[]> allThreads = Thread
				.getAllStackTraces();
		Iterator<Thread> iterator = allThreads.keySet().iterator();
		StringBuffer stringBuffer = new StringBuffer();
		while (iterator.hasNext()) {
			Thread key = iterator.next();
			StackTraceElement[] trace = (StackTraceElement[]) allThreads
					.get(key);
			stringBuffer.append(key + "\r\n");
			for (int i = 0; i < trace.length; i++) {
				stringBuffer.append(" " + trace[i] + "\r\n");
			}
			stringBuffer.append("");
		}
		String dump = stringBuffer.toString();
		log.info("Dumping Thread : " + dump);
		return dump;
	}
	
	public void startThreadsDeadLock() {

		Object observe1 = new Object();
		Object observe2 = new Object();
		Object observe3 = new Object();

		Thread thread1 = new Thread(new ThreadSync(observe1, observe2), "thread1");
		Thread thread2 = new Thread(new ThreadSync(observe2, observe3), "thread2");
		Thread thread3 = new Thread(new ThreadSync(observe3, observe1), "thread3");
		
		thread1.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			log.warn("thread1 thread got exception : " + e.toString());
		}		
		thread2.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			log.warn("thread2 thread got exception : " + e.toString());
		}		
		thread3.start();
	}

}
