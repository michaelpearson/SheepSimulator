package simulation.model.behaviour;

import java.util.ArrayList;

import simulation.model.Paddock;
import simulation.model.Position;
import simulation.model.Sheep;
import simulation.model.Vector;
/**
 * Based on Martin Barksten & David Rydbergs paper
 * http://www.csc.kth.se/utbildning/kth/kurser/DD143X/dkand13/Group9Petter/report/Martin.Barksten.David.Rydberg.report.pdf
 * @author Michael
 */
public class ReynoldsFlockingMovement extends MovementStrategy{
	private static double[] GAINS = {0.036,10.0,9.0,2.0,0.08,0.13,100.0,1.3};
	public static void setGains(int index,double value){GAINS[index] = value;}
	public static double getGain(int index){return(GAINS[index]);}
	@Override
	public Vector tick(Position position) {
		ArrayList<Sheep> sheeps = Paddock.getSheep();
		Position predatorPosition = Paddock.getPredatorCenter();
		Position averagePosition = Paddock.getFlockCenter();
		
		Vector cohesion = cohesion(position, averagePosition);
		Vector seperation = seperation(position, sheeps);
		Vector alignment = alignment(position, sheeps);
		Vector escape = escape(position, predatorPosition);
		
		
		double distance = Math.sqrt(Math.pow(position.getX() - predatorPosition.getX(),2) + Math.pow(position.getY() - predatorPosition.getY(), 2));

		cohesion.scale(GAINS[0] * (1 + gainFunction(distance) * GAINS[1]));
		seperation.scale(GAINS[2] * (1 + gainFunction(distance) * GAINS[3]));
		alignment.scale(GAINS[4] * (1 + gainFunction(distance) * GAINS[5]));
		escape.scale(GAINS[6]);
		
		Vector total = new Vector(0,0);
		
		if(!cohesion.isNaN())
			total.add(cohesion);
		else
			System.out.println("C NAN");
		if(!seperation.isNaN())
			total.add(seperation);
		else
			System.out.println("S NAN");
		if(!alignment.isNaN())
			total.add(alignment);
		else
			System.out.println("A NAN");
		if(!escape.isNaN())
			total.add(escape);
		else
			System.out.println("E NAN");
		
		double speed = total.getMagnitude();
		if(speed > GAINS[7])
		{
			total.NormalizeVector();
			total.scale(GAINS[7]);
		}
		
		
		for(double d : GAINS)
		{
			System.out.print(d);
			System.out.print("\t");
		}
		System.out.println();
		return(total);
		
	}

	
	

	private Vector cohesion(Position myPosition,Position averagePosition)
	{
		double x = averagePosition.getX() - myPosition.getX();
		double y = averagePosition.getY() - myPosition.getY();
		Vector v = new Vector(x,y);
		v.scale(1.0/v.getMagnitude());
		return(v);
	}
	private Vector seperation(Position myPosition,ArrayList<Sheep> sheeps)
	{
		Vector total = new Vector(0,0);
		for(Sheep s : sheeps)
		{
			Vector v = new Vector(myPosition,s.getPosition());
			double magnitude = v.getMagnitude();
			v.scale(inverseSquare(magnitude, 1) / magnitude);
			if(v.isNaN())
				continue;
			total.add(v);
		}
		return(total);
	}
	private Vector alignment(Position myPosition,ArrayList<Sheep> sheeps)
	{
		Vector total = new Vector(0,0);
		int a = 0;
		for(Sheep s : sheeps)
		{
			Vector v = new Vector(myPosition,s.getPosition());
			if(v.getMagnitude() > 100 || myPosition == s.getPosition())
				continue;
			total.add(v);
			a++;
		}
		if(a != 0)
			total.scale(1/a); //TODO: review this. I have taken a little liberty with the interpretation of the escape rule.
		return(total);
	}
	private Vector escape(Position myPosition,Position predator)
	{
		Vector v = new Vector(myPosition,predator);
		double distance = v.getMagnitude();
		v.scale(inverseSquare(distance, 10) / distance);
		return(v);
	}
	
	private double inverseSquare(double distance,double softness)
	{
		return(Math.pow((distance / softness) + 0.001,-2)); //TODO: protect against nan ( division by 0 )
	}
	private double gainFunction(double distance)
	{
		return((1/Math.PI) * Math.atan((300 - distance) / 20) + 0.5); //TODO: check the value of r, 300 was sorta a guess. Paper suggests 300?
	}
}
