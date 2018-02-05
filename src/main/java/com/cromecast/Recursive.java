package com.cromecast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Recursive {

	List<Integer> answerList = new ArrayList<Integer>();
	private static BigInteger[] answers;

	public static void main(String[] args) {
		Recursive r = new Recursive();
		r.generateFibonacci(9);
	}

	public List<BigInteger> generateFibonacci(int n) {

		// Initializing answers
		answers = new BigInteger[n + 1];
		answers[0] = new BigInteger("1");
		answers[1] = new BigInteger("1");
		for (int i = 2; i < n + 1; i++)
			answers[i] = new BigInteger("0");
		fastFibonacci(n + 1);
		return Arrays.asList(answers);
	}

	public BigInteger fastFibonacci(int n) {
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
}
