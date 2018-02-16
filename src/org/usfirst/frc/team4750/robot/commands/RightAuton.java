package org.usfirst.frc.team4750.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightAuton extends CommandGroup {

    public RightAuton() {
        addSequential(new DriveToDistance(6, true));
        addSequential(new TurnToAngle(90));
    }
}
