package ex;

public class SourceReader {
	private int index = 0;
	private String input;
	
	public SourceReader(String input) {
		this.input = input;
	}
	
	public void goBack() {
		--index;
	}
	
	public Character readSymbol() {
		if(index >= input.length()) {
			return null;
		} else {
			Character symbol = input.charAt(index);
			++index;
			return symbol;
		}
	}
}
