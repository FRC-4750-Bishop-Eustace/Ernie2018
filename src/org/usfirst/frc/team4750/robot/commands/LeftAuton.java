package org.usfirst.frc.team4750.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftAuton extends CommandGroup {

    public LeftAuton() {
        addSequential(new DriveToDistance(6, true));
        addSequential(new TurnToAngle(-90));
    }
}
