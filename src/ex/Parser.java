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
	
	// S → ε | identAS
	// A → intValB
	// B → ε | [S]
	
	// First(S) = {ε, ident}
	// First(A) = {intVal}
	// First(B) = {ε, [}
	
	// Follow(S) = {EOF, ]}
	// Follow(A) = First(S) ∪ Follow(S) = {EOF, ], ident}
	// Follow(B) = Follow(A) = {EOF, ], ident}
	
	//      ident           intVal          [           ]       EOF
	// S    S → identAS                                 S → ε   S → ε
	// A                    A → intValB
	// B    B → ε                           B → [S]     B → ε   B → ε
	
	private NTree S() throws Exception {
		if(getTokenClass() == TokenClass.ident) {// S → identAS
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
		
		else if(getTokenClass() == TokenClass.rightBracket) {// S → ε
			return null;
		}
		
		else if(isEOF()) {// S → ε
			return null;
		}
		
		throw new Exception("Syntax Error: Unexpected token " + getToken().getValue());
	}
	
	private NTree A() throws Exception {
		if(getTokenClass() == TokenClass.intVal) {// A → intValB
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
	
	private NTree B() throws Exception {
		if(getTokenClass() == TokenClass.ident) {// B → ε
			return null;
		}
		
		else if(getTokenClass() == TokenClass.leftBracket) {// B → [S]
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
		
		else if(getTokenClass() == TokenClass.rightBracket) {// B → ε
			return null;
		}
		
		else if(isEOF()) {// B → ε
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
