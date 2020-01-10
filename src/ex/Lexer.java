package ex;

import java.util.List;
import java.util.ArrayList;

public class Lexer {
	private int startState = 0;
	private static Integer[][] transitions = {
		{0,   1,   2,   100, 101, null},
		{200, 1,   200, 200, 200, null},
		{201, 201, 2,   201, 201, null}
	};
	
	private int getSymbolIndex(Character symbol) {
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

	public List<Token> lexer(String input) throws Exception {
		if(input == null) {return null;}
		
		// Delimits ending identifiers or int values
		
		input += " ";
		
		SourceReader sourceReader = new SourceReader(input);
		
		int state = startState;
		
		List<Token> tokens = new ArrayList<Token>();
		StringBuffer buf = new StringBuffer();
		
		Character c;
		
		while((c = sourceReader.readSymbol()) != null) {
			Integer nextState = transitions[state][getSymbolIndex(c)];
			
			// Error: transition not specified.
			
			if(nextState == null) {
				throw new Exception("Lexical Error: Unexpected symbol " + c);
			}
			
			state = (int)nextState;
			
			if(state > 0) {
				buf.append(c);
			}
			
			// Accept states
			
			if(state >= 100) {
				TokenClass tokenClass = null;
				
				switch(state) {
					case 100: tokenClass = TokenClass.leftBracket; break;
					case 101: tokenClass = TokenClass.rightBracket; break;
					case 200: tokenClass = TokenClass.ident; break;
					case 201: tokenClass = TokenClass.intVal; break;
				}
				
				// "Go-back" states
				
				if(state >= 200) {
					sourceReader.goBack();
					buf.deleteCharAt(buf.length() - 1);
				}
				
				// Creating the token.
				
				Token token;
				
				if(tokenClass != null) {
					token = new Token(tokenClass, buf.toString());
					
					tokens.add(token);
				}
				
				buf = new StringBuffer();
				
				// Returns to the startState.
				
				state = startState;
			}
		}
		
		return tokens;
	}
}
