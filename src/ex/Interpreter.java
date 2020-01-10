package ex;

import javafx.scene.canvas.GraphicsContext;

public class Interpreter {

	final static double INIT_X = 100;
	final static double INIT_Y = 100;
	final static double INIT_DIRECTION = 180;

	double x;
	double y;
	double direction;

	GraphicsContext gc;

	void interpreter(String src, GraphicsContext gc) throws Exception {
		this.gc = gc;
		x = INIT_X;
		y = INIT_Y;
		direction = INIT_DIRECTION;
		
		Lexer lexer = new Lexer();
		Parser parser = new Parser();
		
		execute(parser.parser(lexer.lexer(src)));
	}

	void avance(double longueur) {
		double cibleX = x + Math.sin(direction * Math.PI * 2 / 360) * longueur;
		double cibleY = y + Math.cos(direction * Math.PI * 2 / 360) * longueur;
		gc.strokeLine(x, y, cibleX, cibleY);
		x = cibleX;
		y = cibleY;
	}

	private void execute(NTree tree) throws Exception {
		if(tree != null) {
			String tokenValue = tree.getValue().toUpperCase();
			
			if(tokenValue.equals("REPEAT")) {
				NTree valueTree = tree.getChild(0);
				int count = Integer.parseInt(valueTree.getValue());
				
				// Brackets
				
				NTree innerTree = null;
				
				if(valueTree.size() > 0) {
					innerTree = valueTree.getChild(0).getChild(0);
				}
				
				/* Doesn't work with successive repeat instructions *
				
				// Repeat single instruction following the repeat identifier.
				
				else if(tree.size() > 1) {
					innerTree = new NTree(tree.getChild(1));
					innerTree.add(new NTree(tree.getChild(1).getChild(0)));
					
					// Instruction to repeat, the next instruction will follow it
					
					tree = tree.getChild(1);
				}
				
				/*  */
				
				else {
					// throw new Exception("Error: Nothing to repeat");
					throw new Exception("Error: Repeat requires square brackets");
				}
				
				for(int i = 0; i < count; ++i) {
					execute(innerTree);
				}
			}
			
			else if(tokenValue.equals("FORWARD")) {
				NTree valueTree = tree.getChild(0);
				int length = Integer.parseInt(valueTree.getValue());
				
				if(valueTree.size() > 0) {
					throw new Exception("Error: Unexpected " + valueTree.getChild(0).getValue());
				}
				
				avance(length);
			}
			
			else if(tokenValue.equals("LEFT")) {
				NTree valueTree = tree.getChild(0);
				int angle = Integer.parseInt(valueTree.getValue());

				if(valueTree.size() > 0) {
					throw new Exception("Error: Unexpected " + valueTree.getChild(0).getValue());
				}
				
				direction += angle;
			}
			
			else if(tokenValue.equals("RIGHT")) {
				NTree valueTree = tree.getChild(0);
				int angle = Integer.parseInt(valueTree.getValue());

				if(valueTree.size() > 0) {
					throw new Exception("Error: Unexpected " + valueTree.getChild(0).getValue());
				}
				
				direction -= angle;
			} else if(tree.getTokenClass() == TokenClass.ident) {
				throw new Exception("Error: Unknown instruction " + tree.getValue());
			}
			
			execute(tree.getLastChild());
		}
	}
}
