package simulation.gui;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simulation.model.behaviour.ReynoldsFlockingMovement;

public class OptionsWindow {
	private static boolean open = false;
	private static OptionsWindow window = null;
	
	private static JFrame optionsWindow;
	
	
	public static OptionsWindow show()
	{
		if(open)
			return(window);
		if(window == null)
		{
			window = new OptionsWindow();
			open = true;
		}
		else
		{
			optionsWindow.setVisible(true);
		}
		return(window);
	}
	public static OptionsWindow show(Point p)
	{
		if(open)
			return(window);
		show();
		optionsWindow.setLocation(p);
		return(window);
	}
	private OptionsWindow() {
		optionsWindow = new JFrame("Flock Options");
		optionsWindow.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				OptionsWindow.open = false;
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		});
		optionsWindow.setLayout(new GridLayout(0, 3,10,10));
		optionsWindow.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
		
		{
			JLabel label = new JLabel("Option Description");
			optionsWindow.add(label);
		}
		{
			JLabel label = new JLabel("Low Fear");
			optionsWindow.add(label);
		}
		{
			JLabel label = new JLabel("High Fear");
			optionsWindow.add(label);
		}
		{
			ChangeListener[] l = {
					new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							ReynoldsFlockingMovement.setGains(0, ((JSlider)e.getSource()).getValue() / 1000.0);
						}
					},
					new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							ReynoldsFlockingMovement.setGains(1, ((JSlider)e.getSource()).getValue() / 10.0);
						}
					}
			};
			int[] values = {(int) (ReynoldsFlockingMovement.getGain(0) * 1000),(int)(ReynoldsFlockingMovement.getGain(1) * 10)};
			addSlider(optionsWindow,"Cohesion:",0,100,values,2,2,l);
		}
		{
			ChangeListener[] l = {
					new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							ReynoldsFlockingMovement.setGains(2, ((JSlider)e.getSource()).getValue() / 10.0);
						}
					},
					new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							ReynoldsFlockingMovement.setGains(3, ((JSlider)e.getSource()).getValue() / 10.0);
						}
					}
			};
			int[] values = {(int) (ReynoldsFlockingMovement.getGain(2) * 10),(int)(ReynoldsFlockingMovement.getGain(3) * 10)};
			addSlider(optionsWindow,"Separation:",0,100,values,2,2,l);
		}
		{
			ChangeListener[] l = {
					new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							ReynoldsFlockingMovement.setGains(4, ((JSlider)e.getSource()).getValue() / 100.0);
						}
					},
					new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							ReynoldsFlockingMovement.setGains(5, ((JSlider)e.getSource()).getValue() / 100.0);
						}
					}
			};
			int[] values = {(int) (ReynoldsFlockingMovement.getGain(4) * 100),(int)(ReynoldsFlockingMovement.getGain(5) * 100)};
			addSlider(optionsWindow,"Alignment:",0,100,values,2,2,l);
		}
		{
			ChangeListener[] l = {
					new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							ReynoldsFlockingMovement.setGains(6, ((JSlider)e.getSource()).getValue() / 1.0);
						}
					}
			};
			int[] values = {(int) (ReynoldsFlockingMovement.getGain(6))};
			addSlider(optionsWindow,"Escape:",0,100,values,1,2,l);
		}
		{
			ChangeListener[] l = {
					new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							ReynoldsFlockingMovement.setGains(7, ((JSlider)e.getSource()).getValue() / 50.0);
						}
					}
			};
			int[] values = {(int) (ReynoldsFlockingMovement.getGain(7) * 50)};
			addSlider(optionsWindow,"Max Speed:",0,100,values,1,2,l);
		}
		
		optionsWindow.setResizable(false);
		optionsWindow.pack();
		optionsWindow.setVisible(true);
	}
	private void addSlider(JFrame window,String description,int min,int max,int[] values,int sliderCount,int colSpan,ChangeListener[] l)
	{
		JLabel label = new JLabel(description);
		window.add(label);
		int a;
		for(a = 0;a<sliderCount;a++)
		{
			JSlider slider = new JSlider(min, max);
			slider.setMajorTickSpacing(10);
			slider.setPaintTicks(true);
			slider.setMinorTickSpacing(5);
			slider.addChangeListener(l[a]);
			slider.setValue(values[a]);
			window.add(slider);
		}
		for(;a < colSpan;a++)
		{
			window.add(new JLabel());
		}
	}
}
