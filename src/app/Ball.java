package app;

public class Ball {
	
	public int x;
	public int y;
	public int width;
	public int height;
	
	private int vel;
	
	public Ball(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public double centerOfMass(double mass1, double mass2, double velocity1, double velocity2) {
		return (mass1 * velocity1 + mass2 * velocity2) / (mass1 + mass2);
	}
	
	public int velAfter1(Ball ball) {
		
	}

}
