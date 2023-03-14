package net.chronakis.school.timetables.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TTSheetTest {

	@Test
	public void testGenerateSentences() {
		List<Integer> numbers = Arrays.asList(3, 4, 6, 7);
		int OTHER = 12;
		int ITER = 10000; // Needs at least 10000 to distribute evenly
		
		Map<Integer, Integer> numberMap = new HashMap<>();
		Map<Integer, Integer> countMap = new HashMap<>();
		List<TTSentence> sentences = TTSheet.generateSentences(numbers, OTHER, ITER);
		
		sentences.forEach(s -> {
			assertTrue(s.dump(), s.other > 0 && s.other <= OTHER);
			int count = countMap.containsKey(s.other) ? countMap.get(s.other) : 0;
			countMap.put(s.other, count + 1);
			
			assertTrue(s.dump(), numbers.contains(s.primary));
			count = numberMap.containsKey(s.primary) ? numberMap.get(s.primary) : 0;
			numberMap.put(s.primary, count + 1);

		});
		
		System.out.println("Primary numbers distribution of " + String.join(",", numbers.stream().map(Object::toString).collect(Collectors.toList())));
		double expected = ITER / numbers.size();
		for (Integer key : numberMap.keySet()) {
			assertEquals(expected, numberMap.get(key), expected/10);
			System.out.println(String.format("  %2d: %d", key, numberMap.get(key)));
		}
		
		System.out.println("Other numbers distribution of " + OTHER);
		expected = ITER / OTHER; 
		for (Integer key : countMap.keySet()) {
			assertEquals(expected, countMap.get(key), expected/10);
			System.out.println(String.format("  %2d: %d", key, countMap.get(key)));
		}

//		List<TTQuestion> questions = TTSheet.generateQuestions(sentences, 0.25, 0.25);
//		questions.forEach(q -> {
//			System.out.println(q.dump());
//		});
	}
	
}
