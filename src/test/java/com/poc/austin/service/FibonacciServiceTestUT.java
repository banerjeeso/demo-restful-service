package com.poc.austin.service;



import java.math.BigInteger;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.poc.austin.service.FibonacciService;

public class FibonacciServiceTestUT {

	@Test
	public void testRecursiveFibonacci() {
		FibonacciService fibonacciService= new FibonacciService();
		List<BigInteger> result=fibonacciService.recursiveFibonacci(3);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testRecursiveFibonacci_1() {
		FibonacciService fibonacciService= new FibonacciService();
		List<BigInteger> result=fibonacciService.recursiveFibonacci(1);
		Assert.assertNotNull(result);
	}

	@Test
	public void testGenerateFibonacci() {
		FibonacciService fibonacciService= new FibonacciService();
		List<Integer> result=fibonacciService.generateFibonacci(5);
		Assert.assertNotNull(result);
	}

	

}
