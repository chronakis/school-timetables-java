package net.chronakis.adrian.ttgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.chronakis.adrian.ttgen.TTQuestion.Sign;

public class TTSheet {
	private final String title;
	private final List<Integer> numbers;
	private final List<TTSentence> sentences;
	private final List<TTQuestion> questions;
	private final double divisionProb;
	private final double equationProb;
	private final int range;
	private final int count;
	
	public TTSheet(Integer [] numbers, int count, double divisionProb, double equationProb) {
		this(numbers, 12, count, divisionProb, equationProb);
	}
	
	public TTSheet(List<Integer> numbers, int count, double divisionProb, double equationProb) {
		this(numbers, 12, count, divisionProb, equationProb);
	}

	public TTSheet(Integer [] numbers, int range, int count, double divisionProb, double equationProb) {
		this(Arrays.asList(numbers), count, divisionProb, equationProb);
	}
	
	public TTSheet(List<Integer> numbers, int range, int count, double divisionProb, double equationProb) {
		this.numbers = numbers;
		this.range = range;
		this.count = count;
		this.divisionProb = divisionProb;
		this.equationProb = equationProb;
		this.sentences = generateSentences(numbers , range, count);
		this.questions = generateQuestions(sentences, divisionProb, equationProb);
		this.title = computeTitle();
	}
	
	private String computeTitle() {
		int size = numbers.size();
		String title = "";
		if (size == 0)
			return title;
		else if (size == 1) {
			title = numbers.get(0).toString();
		}
		else if (size == 2)
			title = numbers.get(0).toString() + " & " + numbers.get(1).toString();
		else {
			title = numbers.subList(0, size - 1).stream().map(Object::toString).collect(Collectors.joining(", "))
					+ " & " + numbers.get(size - 1);
		}
		String suffix = size > 1 ? "s" : "";
		return title + " timetable" + suffix;
	}

	public static List<TTQuestion> generateQuestions(List<TTSentence> sentences, double divisionProb, double equationProb) {
		List<TTQuestion> questions = new ArrayList<>();
		for (TTSentence s : sentences) {
			boolean equation = Math.random() < equationProb;
			Sign sign;
			Integer left;
			Integer right;
			Integer result;
    		if (Math.random() < divisionProb) {
    			sign = Sign.DIVIDE;
				left = s.result;
				right = equation ? null : s.primary;
				result = equation ? s.other : null;
    		}
    		else {
    			sign = Sign.MULTIPLY;	    			
    			left = s.primary;
    			right = equation ? null : s.other;
    			result = equation ? s.result : null;
    		}
    		questions.add(new TTQuestion(left, right, sign, result));
		}
		return questions;
	}
	
	
	
	public String getTitle() {
		return title;
	}

	public List<TTSentence> getSentences() {
		return sentences;
	}

	public List<TTQuestion> getQuestions() {
		return questions;
	}

	public double getDivisionProb() {
		return divisionProb;
	}

	public double getEquationProb() {
		return equationProb;
	}

	public int getRange() {
		return range;
	}

	public int getCount() {
		return count;
	}

	public static List<TTSentence> generateSentences(int number, int range, int count)  {
    	List<TTSentence> sentences = new ArrayList<>();
    	
    	for (int i = 0 ; i < count ; i++) {
    		int other = (int)(Math.random() * range + 1);
    		int result = number * other;

    		sentences.add(new TTSentence(number, other, result));
    	}
    	return sentences;
    }
	
	public static List<TTSentence> generateSentences(List<Integer> numbers, int range, int count)  {
    	List<TTSentence> sentences = new ArrayList<>();
    	
    	for (int i = 0 ; i < count ; i++) {
    		int other = (int)(Math.random() * range + 1);
    		int idx = (int)(Math.random() * numbers.size());
    		int number = numbers.get(idx);
    		int result = number * other;

    		sentences.add(new TTSentence(number, other, result));
    	}
    	return sentences;
    }
}
