package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UltrasonicOutput extends Command {

	public UltrasonicOutput() {
		requires(Robot.ultrasonic);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Output range in inches to dashboard
		SmartDashboard.putNumber("Range (inches)", Robot.ultrasonic.getInches());
		// Output range in feet to dashboard
		SmartDashboard.putNumber("Range (feet)", Robot.ultrasonic.getFeet());
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