package net.chronakis.adrian.ttgen;

/**
 * Represents a number sentence
 * The primary is the TT number, e.g. 5 times table
 * The other is the one that we multiply with
 *
 */
public class TTSentence {
	Integer primary;
	Integer other;
	Integer result;

	public TTSentence(int primary, int other, int result) {
		super();
		this.primary = primary;
		this.other = other;
		this.result = result;
	}
	
	public String dump() {
		return String.format("%2d x %2d = %2d", primary, other, result);
	}
}