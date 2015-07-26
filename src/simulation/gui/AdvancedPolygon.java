package simulation.gui;

import java.awt.Polygon;

public class AdvancedPolygon extends Polygon{
	private static final long serialVersionUID = 1L;
	public AdvancedPolygon(int[] pointsX,int[] pointsY,int nPoints) {
		super(pointsX,pointsY,nPoints);
	}
	@Override
	protected AdvancedPolygon clone() {
		return(new AdvancedPolygon(xpoints.clone(),ypoints.clone(),npoints));
	}
	public void rotate(double angle)
	{
		angle = angle % (Math.PI * 2);
		double s = Math.sin(angle);
		double c = Math.cos(angle);
		for(int a = 0;a < npoints;a++)
		{
			int x = (int)(xpoints[a] * s - ypoints[a] * c);
			int y = (int)(xpoints[a] * c + ypoints[a] * s);
			xpoints[a] = x;
			ypoints[a] = y;
		}
	}
}
