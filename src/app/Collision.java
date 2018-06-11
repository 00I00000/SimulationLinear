package app;

import java.awt.Color;
import java.awt.Graphics;
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
	
	public static Object obj1 = new Object(0, Constants.WINDOW_HEIGHT / 2, 20, 20, 50, 20);
	public static Object obj2 = new Object(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2, 20, 20, 0, 2000);
	
	double timeLastFrame = 0;
	
	//x and y in fill() are starting points right and top
	
	public void draw(double absT) {
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		
		double deltaTime = (absT - timeLastFrame);
		timeLastFrame = absT;
		
		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		bbg.setColor(Color.RED);

		if (obj1.getHitbox().intersects(obj2.getHitbox())) {
			Object.intersected = true;
		}
		if (Object.intersected) {
			obj1.velDelta1(obj2, Object.epsilon, deltaTime);
			obj2.velDelta2(obj1, Object.epsilon, deltaTime);
		} else {
			obj1.vel(deltaTime);
			obj2.vel(deltaTime);
		}
		Object.moveCOM(deltaTime);
		int cast = (int) Object.xCenterOfMass;
		bbg.fillOval(cast - 5, Constants.WINDOW_HEIGHT / 2 + 40, 10, 10);
		bbg.fillOval(obj1.getX(), obj1.getY(), obj1.getWidth(), obj1.getHeight());
		bbg.fillOval(obj2.getX(), obj2.getY(), obj2.getWidth(), obj2.getHeight());
		
		g.drawImage(backBuffer, 0, 0, null);
		
	}

	
	public static void main(String[] args) {
		Collision test = new Collision();
		test.setVisible(true);
		double absT = 0;
		obj1.centerOfMass(obj2);
		Object.epsilon = 1;
		//(m1 * x1 + m2 * x2) / m1 + m2
		Object.xCenterOfMass = ((obj1.getMass() * obj1.getX() + obj2.getMass() * obj2.getX()) / (obj1.getMass() + obj2.getMass())) + obj1.getWidth() / 2;
		System.out.println(obj1.velAfter1(obj2, Object.epsilon));
		System.out.println(Object.centerOfMass);
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
