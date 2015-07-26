package simulation.model;


public class Vector {
	private double x,y;
	public Vector() {
		this(0,0);
	}
	public Vector(double x,double y) {
		this.x = x;
		this.y = y;
	}
	public Vector(int x, int y) {
		this((double)x,(double)y);
	}
	public Vector(Position p1,Position p2) {
		this(p1.getX() - p2.getX(),p1.getY() - p2.getY());
	}
	
	public void fromPositions(Position p1,Position p2) {
		x = p1.getX() - p2.getX();
		y = p1.getY() - p2.getY();
	}
	public void NormalizeVector()
	{
		double mag = getMagnitude();
		x = x / mag;
		y = y / mag;
	}
	public double getMagnitude()
	{
		return(Math.sqrt(Math.pow(x, 2) + Math.pow(y,2)));
	}
	public double getX()
	{
		return(x);
	}
	public double getY()
	{
		return(y);
	}
	public void add(Vector v)
	{
		x += v.getX();
		y += v.getY();
	}
	public void subtract(Vector v)
	{
		x -= v.getX();
		y -= v.getY();
	}
	public void scale(double k)
	{
		x *= k;
		y *= k;
	}
	public void reset()
	{
		x = 0;
		y = 0;
	}
	public double getAngle()
	{
		return(-Math.atan2(y,x) + (Math.PI*2));
	}
	@Override
	public Vector clone() {
		return(new Vector(x,y));
	}
	@Override
	public String toString() {
		return(String.format("Vector: %1.2f \t%1.2f",getX(),getY()));
	}
	public boolean isNaN()
	{
		if(Double.isNaN(x)|| Double.isNaN(y))
		{
			return(true);
		}
		return(false);
	}
}
