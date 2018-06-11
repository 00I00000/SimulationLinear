package app;

import java.awt.Rectangle;

public class Object {
	
	public static double centerOfMass;
	public static double xCenterOfMass;
	public static boolean intersected = false;
	public static double epsilon = 0;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private double exactX;
	
	private double vel;
	private double mass;
	
	private Rectangle hitbox;
	
	public Object(int x, int y, int width, int height, double vel, double mass) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.vel = vel;
		this.mass = mass;
		exactX = x;
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public void centerOfMass(Object obj) {
		centerOfMass = (mass * vel + obj.mass * obj.vel) / (mass + obj.mass);
	}
	
	public static void moveCOM(double deltaTime) {
		xCenterOfMass += centerOfMass * deltaTime;
	}
	
	public void vel(double deltaTime) {
		exactX += vel * deltaTime;
		x = (int) exactX;
		hitbox.x = x;
	}
	
	/*public double velAfter1(Object obj, double epsilon) {
		return centerOfMass(obj) - epsilon * obj.vel / (mass + obj.mass) * (obj.vel - vel);
	}
	
	public double velAfter2(Object obj, double epsilon) {
		return centerOfMass(obj) + epsilon * obj.vel / (mass + obj.mass) * (obj.vel - vel);
	}*/
	
	public double velAfter1(Object obj, double epsilon) {
		return centerOfMass + epsilon * obj.mass / (mass + obj.mass) * (obj.vel - vel);
	}
	
	public double velAfter2(Object obj, double epsilon) {
		return centerOfMass - epsilon * obj.mass / (obj.mass + mass) * (vel - obj.vel);
	}
	
	public void velDelta1(Object obj, double epsilon, double deltaTime) {
		exactX += velAfter1(obj, epsilon) * deltaTime;;
		x = (int) exactX;
		hitbox.x = x;
	}
	
	public void velDelta2(Object obj, double epsilon, double deltaTime) {
		exactX += velAfter2(obj, epsilon) * deltaTime;;
		x = (int) exactX;
		hitbox.x = x;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getExactX() {
		return exactX;
	}

	public double getVel() {
		return vel;
	}

	public double getMass() {
		return mass;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	

}
