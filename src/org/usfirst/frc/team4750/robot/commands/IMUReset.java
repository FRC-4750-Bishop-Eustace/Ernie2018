package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is used for resetting the IMU
 *
 */
public class IMUReset extends Command {

	// Check if finished
	boolean isFinished = false;

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Resets the encoders
		Robot.imu.reset();
		isFinished = true;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
