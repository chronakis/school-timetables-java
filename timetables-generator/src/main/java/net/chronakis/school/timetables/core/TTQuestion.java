package net.chronakis.school.timetables.core;

public class TTQuestion {
	public enum Sign {
		MULTIPLY("*"),
		DIVIDE(":");

		private String val;
		
		private Sign(String val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return val;
		}
	}
	
	public Integer left;
	public Integer right;
	public Sign sign;
	public Integer result;

	public TTQuestion(Integer left, Integer right, Sign sign, Integer result) {
		super();
		this.left = left;
		this.right = right;
		this.sign = sign;
		this.result = result;
	}
	
	public String dump() {
		return dump("__", "x", ":");
	}
	
	public String dump(String empty, String multi, String div) {
		String l = left != null ? left.toString() : empty; 
		String r = right != null ? right.toString() : empty; 
		String res = result != null ? result.toString() : empty;
		String s = sign.equals(Sign.MULTIPLY) ? multi : div;
		
		return String.format("%3s %s %3s = %3s", l, s, r, res);
	}

}