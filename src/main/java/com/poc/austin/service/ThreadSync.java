package com.poc.austin.service;

import org.apache.log4j.Logger;
/**
 *  Run and Synchronized the threads
 * @author Somnath
 *
 */
class ThreadSync implements Runnable {
	
	private Logger log = Logger.getLogger(ThreadSync.class);
	
	private Object obj1;
	private Object obj2;

	public ThreadSync(Object o1, Object o2) {
		this.obj1 = o1;
		this.obj2 = o2;
	}

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		log.info(threadName + " acquiring lock on " + obj1);
		
		synchronized (obj1) {
			log.info(threadName + " acquired lock on " + obj1);
			work();
			log.info(threadName + " acquiring lock on " + obj2);
			synchronized (obj2) {
				log.info(threadName + " acquired lock on " + obj2);
				work();
			}
			log.debug(threadName + " released lock on " + obj2);
		}
		
		log.info(threadName + " released lock on " + obj1);
		log.info(threadName + " finished execution.");
	}

	private void work() {
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}