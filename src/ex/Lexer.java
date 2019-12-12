package ex;

import java.util.List;
import java.util.ArrayList;

public class Lexer {
	private int startState = 0;
	private static Integer[][] transitions = {
		{0,   1,   2,   102, 103, null},
		{100, 1,   100, 100, 100, null},
		{101, 101, 2,   101, 101, null}
	};
	
	public int getSymbolIndex(Character symbol) {
		if(symbol == null) {
			return 0;
		}
		
		else if(Character.isWhitespace(symbol)) {
			return 0;
		}
		
		else if(Character.isLetter(symbol)) {
			return 1;
		}
		
		else if(Character.isDigit(symbol)) {
			return 2;
		}
		
		else switch(symbol) {
			case '[': return 3;
			case ']': return 4;
			default: return 5;
		}
	}
}
