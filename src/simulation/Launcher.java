package simulation;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import simulation.gui.*;
import simulation.model.*;

public class Launcher {
	private SimulationControl simulation;
	private JFrame window;
	public static final int WIDTH = 1000,HEIGHT = 600,NUMBER_OF_SHEEP = 50;
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.out.println("Warning, Could not set look and feel");
		}
		new Launcher(args);
	}
	
	public Launcher(String[] args) {
		startSimulation();
	}
	private void startSimulation()
	{
		SimulationView view = initWindow();
		Paddock paddock = new Paddock(WIDTH,HEIGHT,NUMBER_OF_SHEEP);
		simulation = paddock;
		paddock.attachView(view);
	}
	
	private SimulationView initWindow()
	{
		window = new JFrame();
		window.setVisible(true);
		window.setLayout(new BorderLayout());
		SimulationView view = new SimulationView();
		window.add(view, BorderLayout.CENTER);
		window.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) {Launcher.this.simulation.stop();}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
		});
		view.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				Launcher.this.simulation.setPredatorCenter(e.getX() - SimulationView.PADDING / 2, e.getY() - SimulationView.PADDING / 2);
				//System.out.printf("X: %d\tY: %d\n",e.getX(),e.getY());
			}
			public void mouseDragged(MouseEvent e) {}
		});

		
		
		JMenuBar menuBar = new JMenuBar();
		{
			JMenu file = new JMenu("File");
			menuBar.add(file);
			{
				JMenuItem pause = new JMenuItem("Pause");
				pause.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Launcher.this.simulation.pause();
					}
				});
				file.add(pause);
			}
			{
				JMenuItem resume = new JMenuItem("Resume");
				resume.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Launcher.this.simulation.resume();
					}
				});
				file.add(resume);
			}
			{
				JMenuItem stop = new JMenuItem("Exit");
				stop.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Launcher.this.simulation.stop();
					}
				});
				file.add(stop);
			}
		}
		{
			JMenu edit = new JMenu("Edit");
			menuBar.add(edit);
			{
				JMenuItem options = new JMenuItem("Options");
				options.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						OptionsWindow.show(Launcher.this.window.getLocation());
					}
				});
				edit.add(options);
			}
		}
		{
			JMenu edit = new JMenu("Help");
			menuBar.add(edit);
			{
				JMenuItem options = new JMenuItem("How about no?");
				edit.add(options);
			}
		}
		
		
		
		window.add(menuBar,BorderLayout.NORTH);
		
		
		window.setResizable(false);
		window.setLocation(new Point(2000,300));
		window.pack();
		window.setTitle("Sheep Herding Simulation");
		//window.setIconImage(window.createImage(128, 128));
		
		return(view);
	}
}
