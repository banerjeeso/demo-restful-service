package com.poc.austin.service;

import java.io.IOException;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.poc.austin.service.RestTemplateService;

public class RestTemplateServiceTestUT {

	@Test
	public void testExternalWebServiceCall() throws Exception {
		RestTemplateService restTemplateService= new RestTemplateService();
		restTemplateService.init();
		restTemplateService.externalWebServiceCall("https://jsonplaceholder.typicode.com/posts");
	}
	
	
	
}
