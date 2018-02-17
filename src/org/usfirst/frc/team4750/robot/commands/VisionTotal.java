package org.usfirst.frc.team4750.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionTotal extends CommandGroup {

    public VisionTotal() {
    	addSequential(new Aim());
    	addSequential(new TestFollow());
    	addSequential(new DriveToDistance(4, true));
    }
}
