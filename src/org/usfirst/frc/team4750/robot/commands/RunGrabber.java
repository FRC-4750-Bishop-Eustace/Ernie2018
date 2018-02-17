package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.OI;
import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunGrabber extends Command {

    public RunGrabber() {
        requires(Robot.grabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	requires(Robot.grabber);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.grabber.armDrive(OI.controlDriveStick.getThrottle(), OI.controlDriveStick.getX());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
