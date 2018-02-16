package org.usfirst.frc.team4750.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command is a sample autonomous command group
 *
 */
public class RightAuton extends CommandGroup {

    public RightAuton() {
        addSequential(new DriveToDistance(6, true));
        addSequential(new TurnToAngle(90));
    }
}
