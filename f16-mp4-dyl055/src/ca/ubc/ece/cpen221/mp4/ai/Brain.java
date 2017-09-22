package ca.ubc.ece.cpen221.mp4.ai;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class Brain {
	public Command previousCommand = new WaitCommand();
	public Location previousLocation = null;

	public boolean feeding = false;
	public Item target = null;
	public boolean running = false;
	public boolean movingTowardsCentral = false;
	public boolean starving = false;

	
	public Brain(){
		
	}
	
	
	

}
