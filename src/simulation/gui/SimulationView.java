package simulation.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import simulation.Launcher;
import simulation.model.*;

import javax.swing.JPanel;

public class SimulationView extends JPanel implements SimulationViewObserver {
	
	private static final long serialVersionUID = 1L;
	public static final int PADDING = 50;
	private static final int SHEEPX[] = {-5,5,5,-5};
	private static final int SHEEPY[] = {-10,-10,10,10};
	private static final int SHEEPHEADX[] = {-2,2,2,-2};
	private static final int SHEEPHEADY[] = {-12,-12,-8,-8};
	private static AdvancedPolygon sheep;
	private static AdvancedPolygon sheepHead;
	private Paddock paddock;
	
	//Expert design skills follow
	private static enum Colors
	{
		BACKGROUND (Color.GREEN),
		SHEEP (Color.WHITE),
		FENCE (Color.GRAY),
		SHEEPHEAD (Color.BLACK);
		private Color color;
		private Colors(int r,int g,int b) {this.color = new Color(r,g,b);}
		private Colors(Color c){this.color = c;}
		public void setColor(Graphics g){g.setColor(color);}
	}
	//Stuff that seems pointless

	private Position transformPosition(int x,int y){return(new Position(x + getPaddockLeft(), y + getPaddockTop()));}
	private int getPaddockLeft(){return(PADDING / 2);}
	private int getPaddockTop(){return(PADDING / 2);}
	private int getPaddockHeight(){return(paddock.height());}
	private int getPaddockWidth(){return(paddock.width());}
	
	public Dimension getSize() {
		if(paddock != null)
			return(new Dimension(paddock.width() + PADDING,paddock.height() + PADDING));
		else
			return(new Dimension(Launcher.WIDTH + PADDING,Launcher.HEIGHT + PADDING));
	}
	public Dimension getPreferredSize() {return(getSize());}
	
	public SimulationView() {
		super(true); //Double buffer this shit 
		sheep = new AdvancedPolygon(SHEEPX, SHEEPY, SHEEPX.length);
		sheepHead = new AdvancedPolygon(SHEEPHEADX, SHEEPHEADY, SHEEPHEADX.length);
	}
	
	@Override
	public void paint(Graphics g) {
		Colors.BACKGROUND.setColor(g);
		g.fillRect(0,0,getWidth(),getHeight());
		if(paddock == null)
			return;
		drawFence(g);
		drawSheeps(g,paddock.getSheep());
	}
	
	private void drawFence(Graphics g)
	{
		Colors.FENCE.setColor(g);
		g.drawRect(getPaddockLeft(), getPaddockTop(), getPaddockWidth(), getPaddockHeight());
	}
	
	private void drawSheeps(Graphics g, ArrayList<Sheep> sheeps)
	{
		for(Sheep sheep : sheeps){
			Position position = sheep.getPosition();
			drawSheep(g,transformPosition((int)position.getX(), (int)position.getY()),position.getTheta());
		}
	}
	//This method is only gonna draw stuff. NO model stuff here. Pure transformed coordinates.
	private void drawSheep(Graphics g, Position position, double rotation)
	{
		
		AdvancedPolygon newSheep = sheep.clone();
		AdvancedPolygon newSheepHead = sheepHead.clone();
		newSheep.rotate(rotation);
		newSheep.translate((int)position.getX(), (int)position.getY());
		newSheepHead.rotate(rotation);
		newSheepHead.translate((int)position.getX(), (int)position.getY());
		
		Colors.SHEEP.setColor(g);
		g.fillPolygon(newSheep);
		Colors.SHEEPHEAD.setColor(g);
		g.fillPolygon(newSheepHead);
	}
	
	@Override
	public void render(Paddock paddock) {
		this.paddock = paddock;
		repaint();
	}
	
	
}
