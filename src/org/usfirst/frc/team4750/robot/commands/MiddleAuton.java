package org.usfirst.frc.team4750.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleAuton extends CommandGroup {

    public MiddleAuton() {
        addSequential(new DriveToDistance(6, true));
    }
}
