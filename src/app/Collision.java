package app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Collision extends JFrame {
	
	private BufferedImage backBuffer;
	
	public Collision() {
		setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		backBuffer = new BufferedImage(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	public boolean intersected = false;
	
	public int yC = Constants.WINDOW_HEIGHT / 2;
	public int xC1 = 0;
	public int xC2 = Constants.WINDOW_WIDTH / 2;
	public int yVelocity = 0;
	public double xVel1 = 50.0;
	public double xVel2 = 0.0;
	public double trueXCoord1 = xC1;
	public double trueXCoord2 = xC2;
	
	public double xCenterOfMass = (xC1 + xC2) / 2;
	
	double timeLastFrame = 0;
	
	public double centerOfMass(double mass1, double mass2, double velocity1, double velocity2) {
		return (mass1 * velocity1 + mass2 * velocity2) / (mass1 + mass2);
	}
	
	public double velAfter1(double mass1, double mass2, double vel1, double vel2, double epsilon) {
		return centerOfMass(mass1, mass2, vel1, vel2) - epsilon * mass2 / (mass1 + mass2)*(vel2 - vel1);
	}
	
	public double velAfter2(double mass1, double mass2, double vel1, double vel2, double epsilon) {
		return centerOfMass(mass1, mass2, vel1, vel2) + epsilon * mass2 / (mass1 + mass2)*(vel2 - vel1);
	}
	
	//x and y in fill() are starting points right and top
	
	public void draw(double absT) {
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		
		Rectangle hitbox1 = new Rectangle(xC1, yC, 20, 20);
		Rectangle hitbox2 = new Rectangle(xC2, yC, 20, 20);
		
		double deltaTime = (absT - timeLastFrame);
		timeLastFrame = absT;
		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		bbg.setColor(Color.RED);
		double velocityAfter1 = velAfter1(20.0, 20.0, xVel1, xVel2, 0);
		// x = x(0) + v * t;
		double velocityDelta1 = velocityAfter1 * deltaTime;
		double velocityAfter2 = velAfter2(20.0, 20.0, xVel1, xVel2, 0);
		double velocityDelta2 = velocityAfter2 * deltaTime;
		if (hitbox1.intersects(hitbox2)) {
			intersected = true;
		}
		if (intersected) {
			trueXCoord1 += velocityDelta1;
			trueXCoord2 += velocityDelta2;
			xC1 = (int) trueXCoord1;
			hitbox1.x = xC1;
			xC2 = (int) trueXCoord2;
			hitbox2.x = xC2;
		} else {
			trueXCoord1 += xVel1 * deltaTime;
			xC1 = (int) trueXCoord1;
			hitbox1.x = xC1;
			trueXCoord2 += xVel2 * deltaTime;
			xC2 = (int) trueXCoord2;
			hitbox2.x = xC2;
		}
		xCenterOfMass += (centerOfMass(20.0, 20.0, xVel1, xVel2) * deltaTime);
		System.out.println(xCenterOfMass);
		bbg.fillOval(((int) (xCenterOfMass)) - 5, Constants.WINDOW_HEIGHT / 2 + 40, 10, 10);
		bbg.fillOval(xC1, yC, 20, 20);
		bbg.fillOval(xC2, yC, 20, 20);
		
		g.drawImage(backBuffer, 0, 0, null);
		
	}

	
	public static void main(String[] args) {
		Collision test = new Collision();
		
		test.setVisible(true);
		double absT = 0;
		while (true) {
			test.draw(absT);
			try {
				Thread.sleep((int) (Constants.TPF * 1000));
				absT += Constants.TPF;
			} catch (InterruptedException e) {
				
			}
		}
	}
}
