package com.poc.austin.response;

/**
 * @author Somnath
 *
 */
import java.math.BigInteger;
import java.util.List;

public class FibonacciResponse {
	List<BigInteger> fibonacciResult;

	public FibonacciResponse() {}

	public Object[] getFibonacciResult() {
		return fibonacciResult.toArray();
	}

	public void setFibonacciResult(List<BigInteger> fibonacciResult) {
		this.fibonacciResult = fibonacciResult;
	}

}