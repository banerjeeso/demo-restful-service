package com.poc.austin.util;

import static org.testng.AssertJUnit.assertTrue;
import static com.poc.austin.util.PojoTestHelper.testClass;

import org.testng.annotations.Test;

import com.poc.austin.domain.Product;
import com.poc.austin.model.Fibonacci;
import com.poc.austin.model.Paragraph;
import com.poc.austin.response.FibonacciResponse;
import com.poc.austin.response.ParagraphResponse;
import com.poc.austin.response.ProductResult;
import com.poc.austin.response.User;



public class PojoTestsUT {
	
	@Test
	public void testBasePojo() {
		assertTrue(true);
	}

	
	@Test
	public void testShippingChargeModel() throws Exception {
		testClass(Fibonacci.class);
		testClass(Paragraph.class);
		testClass(Product.class);
		testClass(User.class);	
		testClass(ProductResult.class);
	
	}

}