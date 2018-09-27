package org.usfirst.frc.team4750.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AStarAuton extends CommandGroup {

    public AStarAuton(ArrayList<Command> drivePath) {    	
    	for(Command c : drivePath) {
    		if(c.equals(new GoToHighPos()) || c.equals(new GoToMidPos())) {
    			addParallel(c);
    		}else {
        		addSequential(c);	
    		}
    	}
    }
}
