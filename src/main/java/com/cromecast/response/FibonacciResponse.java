package com.cromecast.response;

import java.util.List;

public class FibonacciResponse {
	List<Integer> fibonacciResult;

	public FibonacciResponse() {

	}

	public Object[] getFibonacciResult() {
		return fibonacciResult.toArray();
	}

	public void setFibonacciResult(List<Integer> fibonacciResult) {
		this.fibonacciResult = fibonacciResult;
	}

}