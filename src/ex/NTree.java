package ex;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class NTree {
	private List<NTree> children = new ArrayList<NTree>();
	private TokenClass tokenClass;
	private String value;
	
	public NTree(Token token) {
		tokenClass = token.getTokenClass();
		value = token.getValue();
	}
	
	public void add(NTree tree) {
		children.add(tree);
	}
	
	public Iterator<NTree> getChildren() {
		return children.iterator();
	}
	
	public TokenClass getTokenClass() {
		return tokenClass;
	}
	
	public String getValue() {
		return value;
	}
	
	private String _toString(String padding) {
		StringBuffer buf = new StringBuffer();
		
		buf.append(tokenClass);
		buf.append("(");
		buf.append(value);
		buf.append(")");
		buf.append("\n");
		
		Iterator<NTree> it = getChildren();
		
		while(it.hasNext()) {
			NTree tree = it.next();
			
			buf.append(padding);
			buf.append("| ");
			buf.append(tree._toString(padding + "  "));
		}
		
		return buf.toString();
	}
	
	public String toString() {
		return _toString("");
	}
	
	public NTree getChild(int index) {
		return this.children.get(index);
	}
	
	public NTree getLastChild() {
		if(this.children.size() > 0) {
			return this.children.get(this.children.size() - 1);
		}
		
		return null;
	}
}
