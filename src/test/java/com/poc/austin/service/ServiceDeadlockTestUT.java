package com.poc.austin.service;

import static org.junit.Assert.*;

import org.testng.annotations.Test;



public class ServiceDeadlockTestUT {

	ServiceDeadlock serviceDeadlock= new ServiceDeadlock();
	@Test
	public void testCheckDeadLock() {
		serviceDeadlock.startThreadsDeadLock(); 
	}

	@Test
	public void testStartThreadsDeadLock() {
		serviceDeadlock.checkDeadLock(); 
	}

}
