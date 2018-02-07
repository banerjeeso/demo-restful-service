package com.poc.austin.response;

import java.util.Map;

/**
 * @author Somnath
 *
 */
public class ParagraphResponse {
	Map<String,Integer> uniqueWords;

	public ParagraphResponse(){
		
	}

	public Object[] getUniqueWords() {		
		return uniqueWords.entrySet().toArray();		
	}


	public void setUniqueWords(Map<String, Integer> uniqueWords) {
		this.uniqueWords = uniqueWords;
	}	
	

}