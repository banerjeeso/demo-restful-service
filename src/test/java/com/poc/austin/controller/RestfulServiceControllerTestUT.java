package com.poc.austin.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




import java.util.Map;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.austin.repositories.ProductRepository;
import com.poc.austin.service.FibonacciService;
import com.poc.austin.service.RestTemplateService;
import com.poc.austin.service.ServiceDeadlock;
import com.poc.austin.service.UniqueWordsService;

@RunWith(SpringRunner.class) 
@SpringBootTest 

public class RestfulServiceControllerTestUT {
	
	protected MockMvc mockMvc;
	
	@Value("${external.service.url}")
	private String serviceUrl;
	@Mock
	UniqueWordsService wordservice;

	@Mock
	FibonacciService fibonacciService;

	@Mock
	RestTemplateService restservice;

	@Mock
	ProductRepository productRepository;
	
	@Mock
	ServiceDeadlock serviceDeadlock;
	
	@InjectMocks
	protected RestfulServiceController restfulServiceController;
	

	

	@BeforeMethod
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(restfulServiceController).build();
		//mapper = new ObjectMapper();		
	}
	

	@Test
	public void contextLoads() throws Exception { 
		Map<String, Integer> lines= Mockito.mock(Map.class);
		
		Mockito.when(wordservice.getUniqueWordsWithFrequency(Mockito.anyString())).thenReturn(lines);
		String jsonRequest= "{\"paragraphText\": \"This is a test to find words this words would be paragraph test this words frecuency\"}";
	        mockMvc.perform(post("/findUniqueWords")
	                .content(jsonRequest)
	                .accept(MediaType.APPLICATION_JSON)
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andDo(MockMvcResultHandlers.print());       
	
	} 

	

	protected String getUrl(String url) {
		return "http://localhost:8080" + url;
	}

}
