package simulation.model;

public interface SimulationControl {
	public void stop();
	public void pause();
	public void resume();
	public void setPredatorCenter(int x,int y);
}
