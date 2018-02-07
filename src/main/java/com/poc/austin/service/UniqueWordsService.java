package com.poc.austin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

/**
 * This service use to Count frequency of words in a given list and sort by frequency
 * @author Somnath
 *
 */
@Service

public class UniqueWordsService {
	/**
	 * This function take lines that have unique words 
	 * @param lines String array
	 * @return Map of sorted words
	 */
	public Map<String, Integer> getUniqueWordsWithFrequency(String lines) {
		List<String> allWords = new ArrayList<String>();
		Map<String, Integer> sortedwords = new TreeMap<String, Integer>();
		
		Arrays.asList(lines.split(" ")).stream().forEach(word -> allWords.add(word));
		if (!allWords.isEmpty()) {
			Set<String> uniqueSet = new TreeSet<String>(allWords);
			uniqueSet.stream().forEach(word -> sortedwords.put(word,
					Collections.frequency(allWords, word)));
		}
		return sortedwords;
	}
	

}
