package simulation.model;

import simulation.model.behaviour.MovementStrategy;

public class Sheep {
	private Position position;
	private Vector velocity;
	MovementStrategy strategy;
	
	public Position getPosition() 
	{
		return(position);
	}
	public Vector getVelocity()
	{
		return(velocity);
	}
	public Sheep(int x,int y,double theta,MovementStrategy strategy) 
	{
		this.position = new Position(x,y,theta);
		this.strategy = strategy;
	}
	public void tick()
	{
		velocity = strategy.tick(position);
		position = new Position(velocity.getX() + position.getX(),velocity.getY() + position.getY(),velocity.getAngle());
	}
	
}
