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

		direction -= 45; // Right 45
		avance(20);
		direction -= 25; // Left 45
		avance(20);
		direction -= 25;
		avance(20);
		direction -= 40;
		avance(20);
		direction += 90;
		avance(20);
		direction -= 25;
		avance(20);
		direction -= 40;
		avance(20);
		direction -= 25;
		avance(20);

	}

	void avance(double longueur) {
		System.out.println(direction + " haha");
		double cibleX = x + Math.sin(direction * Math.PI * 2 / 360) * longueur;
		double cibleY = y + Math.cos(direction * Math.PI * 2 / 360) * longueur;
		gc.strokeLine(x, y, cibleX, cibleY);
		x = cibleX;
		y = cibleY;
	}

}
