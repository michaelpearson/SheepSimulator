package simulation.model;

public class Position {
	private double x, y;
	private double theta;

	public Position(int x, int y) {
		this((double)x,(double)y);
	}
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Position(int x, int y, double theta) {
		this((double)x,(double)y,theta);
	}
	public Position(double x, double y, double theta) {
		this(x, y);
		this.theta = theta;
	}

	public double getX() {
		return (x);
	}

	public double getY() {
		return (y);
	}

	public double getTheta() {
		return (theta);
	}

	/*public void setX(int x) {
		setX((double)x);
	}

	public void setY(int y) {
		setY((double)y);
	}
	public void setX(double x)
	{
		this.x = x;
	}
	public void setY(double y)
	{
		this.y = y;
	}
	public void setTheta(double theta) {
		this.theta = theta;
	}*/
}