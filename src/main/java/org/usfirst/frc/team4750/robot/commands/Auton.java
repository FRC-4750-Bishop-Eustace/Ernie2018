package org.usfirst.frc.team4750.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton extends CommandGroup {

    public Auton() {
    	addSequential(new DriveToDistance(106, false));
    	System.out.println("Drop cube...");
    	addSequential(new DriveToDistance(-1, true));
    	addSequential(new TurnToAngle(-90));
    	addSequential(new DriveToDistance(5, true));
    	addSequential(new TurnToAngle(90));
    	addSequential(new DriveToDistance(10, true));
    	addSequential(new TurnToAngle(90));
    	addSequential(new DriveToDistance(16, true));
    	addSequential(new TurnToAngle(-90));
    	addSequential(new DriveToDistance(4, true));
    	System.out.println("Drop cube...");
    }
}
