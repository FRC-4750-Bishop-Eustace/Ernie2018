package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestFollow extends Command {

	boolean isFinished;
	double yOffset;
	double xOffset;
	double turnAdjust;
	double driveAdjust;
	double leftSpeed;
	double rightSpeed;

	public TestFollow() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		yOffset = -Robot.limelight.getYOffset();
		xOffset = -Robot.limelight.getXOffset();
		turnAdjust = .08 * xOffset;

		if (xOffset > 0.2) {
			turnAdjust += .095;
		} else if (xOffset < -0.2) {
			turnAdjust -= .095;
		}

		driveAdjust = .3 * yOffset;

		leftSpeed = driveAdjust - turnAdjust;
		rightSpeed = driveAdjust + turnAdjust;

		System.out.println("yOffset: " + yOffset + "    xOffset: " + xOffset + "     turnAdjust: " + turnAdjust
				+ "     driveAdjust: " + driveAdjust + "     leftSpeed: " + leftSpeed + "     rightSpeed: "
				+ rightSpeed);
		Robot.driveTrain.tankDrive(leftSpeed, rightSpeed);
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
