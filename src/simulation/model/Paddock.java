package simulation.model;


import java.util.ArrayList;
import java.util.Random;

import simulation.gui.SimulationViewObserver;
import simulation.model.behaviour.MovementStrategy;

public class Paddock implements SimulationControl{
	private boolean isPaused = false;
	private SimulationThread simulation;
	private ArrayList<SimulationViewObserver> views = new ArrayList<SimulationViewObserver>();
	private enum ThreadControl {UNPAUSED};
	private ThreadControl threadCommand;
	
	private static ArrayList<Sheep> sheeps = new ArrayList<Sheep>();
	private static int width,height;
	private static Position repultionCenter = new Position(0, 0);
	private static Position averagePosition = new Position(0,0);
	
	public Paddock(int width,int height,int sheepCount) {
		Paddock.width = width;
		Paddock.height = height;
		Random rand = new Random();
		for(int a = 0;a<sheepCount;a++)
		{
			
			int x = rand.nextInt(width());
			int y = rand.nextInt(height());
			double theta = rand.nextDouble() * Math.PI * 2;
			
			
			sheeps.add(new Sheep(x, y, theta, MovementStrategy.getDefaultStrategy()));
		}
		simulation = new SimulationThread();
		simulation.start();
	}
	public void attachView(SimulationViewObserver view){views.add(view);}

	//Getters 'n' setters 'n' shit
	public static int width(){return(width);}
	public static int height(){return(height);}
	public static ArrayList<Sheep> getSheep(){return(sheeps);} //Much grammar. Wow.
	private synchronized boolean isPaused(){return(isPaused);}
	
	//Simulation controls
	public synchronized void pause() {isPaused = true;}
	public void resume() {isPaused = false;	threadCommand = ThreadControl.UNPAUSED; simulation.interrupt();}
	public void stop() {System.exit(0);}
	
	
	private class SimulationThread extends Thread
	{
		private long cycleTime;
		//This code will block while simulation is "paused" probably not ideal but not too bad
		private void waitUntilActive()
		{
			if(Paddock.this.isPaused())
			{
				while(Paddock.this.isPaused())
				{
					try {
						sleep(9223372036854775807l);
					} catch (InterruptedException e) {
						switch(Paddock.this.threadCommand)
						{
							case UNPAUSED:
								break;
							default:
								continue;
						}
					}
				}
				cycleTime = System.currentTimeMillis();
			}
		}
		//Calculate the desired frame rate & delay if we have some free time
		public void delay()
		{
			 int dt = (int)(System.currentTimeMillis() - cycleTime);
			 cycleTime = System.currentTimeMillis();
			 int pauseTime = 16 - dt;
			 if(pauseTime > 0)
			 {
				 try {
					sleep(pauseTime);
				} catch (InterruptedException e) {}
			 }
		}
		public void run() {
			cycleTime = System.currentTimeMillis();
			while(true)
			{
				waitUntilActive();
				Paddock.this.tick();
				delay();
			}
		}
	}
	private void tick()
	{
		
		{
			double x = 0;
			double y = 0;
			for(Sheep s : sheeps)
			{
				x += s.getPosition().getX();
				y += s.getPosition().getY();
			}
			x = x / (double)sheeps.size();
			y = y / (double)sheeps.size();
			averagePosition = new Position(x,y);
		}
		
		for(Sheep s : sheeps)
		{
			s.tick();
		}
		for(SimulationViewObserver v : views)
		{
			v.render(this);
		}
	}
	@Override
	public void setPredatorCenter(int x, int y) {
		repultionCenter = new Position(x, y);
	}
	public static Position getPredatorCenter()
	{
		return(repultionCenter);
	}
	public static Position getFlockCenter()
	{
		return(averagePosition);
	}
}
