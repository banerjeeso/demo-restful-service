package com.poc.austin.service;

import org.testng.annotations.Test;

import com.poc.austin.service.UniqueWordsService;

public class UniqueWordsServiceTestUT {

	@Test
	public void testGetUniqueWordsWithFrequency() {
		UniqueWordsService service= new UniqueWordsService();
		String words ="this banerjee somnath the\nso abc in lines\nnew lines in the somnath output abc \n".replace("\n"," ");
		service.getUniqueWordsWithFrequency(words);
	}
	
	@Test
	public void testGetUniqueWordsWithFrequency_EMPTY() {
		UniqueWordsService service= new UniqueWordsService();
		String words =" ".replace("\n"," ");
		service.getUniqueWordsWithFrequency(words);
	}

}
