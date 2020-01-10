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

	void interpreter(String src, GraphicsContext gc) {
		System.out.println(src);
		
		this.gc = gc;
		x = INIT_X;
		y = INIT_Y;
		direction = INIT_DIRECTION;
		
		Lexer lexer = new Lexer();
		Parser parser = new Parser();
		
		try {
			execute(parser.parser(lexer.lexer(src)));
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	void avance(double longueur) {
		double cibleX = x + Math.sin(direction * Math.PI * 2 / 360) * longueur;
		double cibleY = y + Math.cos(direction * Math.PI * 2 / 360) * longueur;
		gc.strokeLine(x, y, cibleX, cibleY);
		x = cibleX;
		y = cibleY;
	}

	private void execute(NTree tree) {
		if(tree != null) {
			String tokenValue = tree.getValue().toUpperCase();
			
			if(tokenValue.equals("REPEAT")) {
				NTree valueTree = tree.getChild(0);
				int count = Integer.parseInt(valueTree.getValue());
				NTree innerTree = valueTree.getChild(0).getChild(0);
				
				// System.out.println("REPEAT " + count);
				
				for(int i = 0; i < count; ++i) {
					execute(innerTree);
				}
			}
			
			else if(tokenValue.equals("FORWARD")) {
				NTree valueTree = tree.getChild(0);
				int length = Integer.parseInt(valueTree.getValue());
				
				// System.out.println("FORWARD " + length);
				
				avance(length);
			}
			
			else if(tokenValue.equals("LEFT")) {
				NTree valueTree = tree.getChild(0);
				int angle = Integer.parseInt(valueTree.getValue());
				
				// System.out.println("LEFT " + angle);
				
				direction += angle;
			}

			else if(tokenValue.equals("RIGHT")) {
				NTree valueTree = tree.getChild(0);
				int angle = Integer.parseInt(valueTree.getValue());
				
				// System.out.println("RIGHT " + angle);
				
				direction -= angle;
			}
			
			execute(tree.getLastChild());
		}
	}
}
