package app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class BackbufferTest_2018_03_14 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage backBuffer;

	public BackbufferTest_2018_03_14() {
		setTitle("2D Demo der Wirkung von Zwischenpuffern");
		setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		backBuffer = new BufferedImage(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);

		// Denkbar ist die Verwendung von RGBA-Bildern
	}

	public static void main(String[] args) {
		BackbufferTest_2018_03_14 testObjekt = new BackbufferTest_2018_03_14();

		testObjekt.setVisible(true);
		double absT = 0;
		while (true) {
			testObjekt.draw(absT);
			try {
				Thread.sleep((int) (Constants.TPF * 1000));
				absT += Constants.TPF;
			} catch (InterruptedException e) {
			}
		}

	}

	public int xVelocity = 0;
	public int yVelocity = 10;

	int xC = Constants.WINDOW_WIDTH / 2;
	int yC = 50;

	double timeStart = 0;

	void draw(double absT) {
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();

		int deltaTime = (int) ((absT - timeStart) * 50);
		timeStart = absT;
		yC += (int) (deltaTime * yVelocity);
		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		bbg.setColor(Color.RED);
		if (yC >= Constants.WINDOW_HEIGHT || yC <= 0) {
			yVelocity = -yVelocity;
		}
		bbg.fillOval(xC, yC, 50, 50);
		/*
		 * for (int i = 0; i < 150; i++) { bbg.setColor(Color.RED);
		 * bbg.fillOval((int) (i * 30 + absT * 100), (int) (i * 20 + absT * 50),
		 * 50, 30); }
		 */
		g.drawImage(backBuffer, 0, 0, null);
		// g.drawImage(testBild, 0, 0, null);

	}
	
	// v1 = (1 - Epsilon) / 2 * v1 
	// v2 = (1 + Epsilon) / 2 * v2

}
