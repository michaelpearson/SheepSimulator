package simulation.model.behaviour;

import simulation.model.Position;
import simulation.model.Vector;

public abstract class MovementStrategy {
	public static MovementStrategy getDefaultStrategy()
	{
		return(new ReynoldsFlockingMovement());
	}
	public abstract Vector tick(Position position);
}
