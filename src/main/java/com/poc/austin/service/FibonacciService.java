package com.poc.austin.service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * A service to calculate fibonacci
 * @author Somnath
 *
 */
@Service
public class FibonacciService {
	Logger log = LoggerFactory.getLogger(FibonacciService.class);		
	private static BigInteger[] answers;	

	public List<BigInteger> recursiveFibonacci(int n) {

		// Initializing answers
		answers = new BigInteger[n + 1];
		answers[0] = new BigInteger("1");
		answers[1] = new BigInteger("1");
		for (int i = 2; i < n + 1; i++)
			answers[i] = new BigInteger("0");
		fastFibonacci(n + 1);		
		return Arrays.asList(answers);
	}

	private BigInteger fastFibonacci(int n) {
		// answers[0] is initialized to 1.
		if ((n == 1) || (n == 2))
			return answers[0];

		// If answers[n-1] is non-zero, then the answer has already been
		// computed, so just return the value in this slot; no need to
		// make a recursive call.
		if (answers[n - 1].compareTo(BigInteger.ZERO) != 0)
			return answers[n - 1];

		// Otherwise, find fibonacci(n-1), either by
		// looking it up in answers[n-2] or computing it for the first time
		if (answers[n - 2].compareTo(BigInteger.ZERO) == 0)
			answers[n - 2] = fastFibonacci(n - 1);

		// Find fibonacci(n-2), either by
		// looking it up in answers[n-3] or computing it for the first time
		if (answers[n - 3].compareTo(BigInteger.ZERO) == 0)
			answers[n - 3] = fastFibonacci(n - 2);

		return answers[n - 2].add(answers[n - 3]);
	}
	
	

	/**
	 * This method is fastest response to calculate Fibonacci series using Java 8 Stream iterater
	 * @param series int
	 * @return List of integer
	 */
	public List<Integer> generateFibonacci(int series) {
		return Stream
				.iterate(new int[] { 0, 1 },
						s -> new int[] { s[1], s[0] + s[1] }).limit(series)
				.map(n -> n[0]).collect(toList());
	}
}
