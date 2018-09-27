package org.usfirst.frc.team4750.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveForward extends CommandGroup {

    public DriveForward() {    	
    	addSequential(new DriveToDistance(10, true));
    }
}
