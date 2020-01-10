package ex;

import java.util.List;

public class Parser {
	private int pos;
	private List<Token> tokens;
	
	private boolean isEOF() {
		return pos == tokens.size();
	}
	
	private TokenClass getTokenClass() {
		if(pos < tokens.size()) {
			return tokens.get(pos).getTokenClass();
		}
		
		return null;
	}
	
	private Token getToken() {
		if(pos < tokens.size()) {
			return tokens.get(pos++);
		}
		
		return null;
	}
	
	public NTree S() throws Exception {
		if(getTokenClass() == TokenClass.ident) {
			Token token = getToken();
			NTree tree = new NTree(token);
			
			NTree stree1 = A();
			
			if(stree1 != null) {
				tree.add(stree1);
			}
			
			NTree stree2 = S();
			
			if(stree2 != null) {
				tree.add(stree2);
			}
			
			return tree;
		}
		
		else if(getTokenClass() == TokenClass.rightBracket) {
			return null;
		}
		
		else if(isEOF()) {
			return null;
		}
		
		throw new Exception("Syntax Error: Unexpected token " + getToken().getValue());
	}
	
	public NTree A() throws Exception {
		if(getTokenClass() == TokenClass.intVal) {
			Token token = getToken();
			NTree tree = new NTree(token);
			
			NTree stree1 = B();
			
			if(stree1 != null) {
				tree.add(stree1);
			}
			
			return tree;
		}
		
		else if(isEOF()) {
			throw new Exception("Syntax Error: Unexpected end of input, expected int value");
		}
		
		throw new Exception("Syntax Error: Unexpected token " + getToken().getValue() + ", expected int value");
	}
	
	public NTree B() throws Exception {
		if(getTokenClass() == TokenClass.ident) {
			return null;
		}
		
		else if(getTokenClass() == TokenClass.leftBracket) {
			Token token = getToken();
			NTree tree = new NTree(token);
			
			NTree stree1 = S();
			
			if(stree1 != null) {
				tree.add(stree1);
			}
			
			if(getTokenClass() == TokenClass.rightBracket) {
				Token token2 = getToken();
				NTree stree2 = new NTree(token2);
				
				tree.add(stree2);
				
				return tree;
			}
			
			else {
				if(isEOF()) {
					throw new Exception("Syntax Error: Unexpected end of input, expected ]");
				}
				
				throw new Exception("Syntax Error: Unexpected token " + getToken().getValue() + ", expected ]");
			}
		}
		
		else if(getTokenClass() == TokenClass.rightBracket) {
			return null;
		}
		
		else if(isEOF()) {
			return null;
		}
		
		throw new Exception("Syntax Error: Unexpected token " + getToken().getValue());
	}
	
	public NTree parser(List<Token> tokens) throws Exception {
		this.tokens = tokens;
		pos = 0;
		
		return S();
	}
}
