package ex;

public class Token {
	private TokenClass tk;
	private String value;
	
	public Token(TokenClass tk, String value) {
		this.tk = tk;
		this.value = value;
	}
	
	public TokenClass getTokenClass() {
		return tk;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		String string = tk.toString();
		
		if(value != null) {
			string += "(\"" + value + "\")";
		}
		
		return string;
	}
}
