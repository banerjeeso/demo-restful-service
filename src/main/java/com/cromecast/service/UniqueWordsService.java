package com.cromecast.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

@Service
public class UniqueWordsService {

	private List<String> getAllWords(String lines[]) {
		List<String> allWords = new ArrayList<String>();
		List<String> line = Arrays.asList(lines);
		line.forEach(eachLine -> {
			Arrays.asList(eachLine.split(" ")).stream()
					.forEach(word -> allWords.add(word));
		});

		return allWords;
	}

	public Map<String, Integer> getFrequency(String lines[]) {
		Map<String, Integer> sortedwords = new TreeMap<String, Integer>();

		List<String> allWords = getAllWords(lines);

		if (!allWords.isEmpty()) {
			Set<String> uniqueSet = new TreeSet<String>(allWords);
			uniqueSet.forEach(word -> sortedwords.put(word,
					Collections.frequency(allWords, word)));
		}
		return sortedwords;
	}

}
