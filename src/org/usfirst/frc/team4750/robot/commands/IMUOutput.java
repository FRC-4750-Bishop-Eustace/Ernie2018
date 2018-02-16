package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command outputs the data from the IMU to the SmartDashboard
 *
 */
public class IMUOutput extends Command {

	public IMUOutput() {
		requires(Robot.imu);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		requires(Robot.imu);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Output current heading to dashboard
		SmartDashboard.putNumber("IMU", Robot.imu.getHeading());
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
