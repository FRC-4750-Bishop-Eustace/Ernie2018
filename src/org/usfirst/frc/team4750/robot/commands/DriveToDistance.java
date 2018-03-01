package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command is a PID controlled drive-to-distance command using encoders
 *
 */
public class DriveToDistance extends Command {

	// Create PID Controller
	PIDController driveController;
	// Heading to turn to
	private float targetDistance;
	// Check if finished
	private boolean isFinished = false;

	// PID Values
	static final double P = 0.06; // 0.06
	static double I = 0.0001; // 0.0001
	static final double D = 0.01; // 0.01
	static final double F = 0.55; // 0.55

	// Minimum error
	static final double tolerance = 1.0;

	// Takes in distance in inches or feet
	public DriveToDistance(float distance, boolean feet) {
		// If feet is false, then the input is in inches. If not, convert feet to
		// inches.
		if (!feet) {
			this.targetDistance = distance;

			// Change I value depending on the distance being traveled
			if (targetDistance > 72) {
				I = 0.004;
			}
			// Print to SmartDashboard
			SmartDashboard.putNumber("Target Distance (inches)", this.targetDistance);
		} else {
			// Convert feet to inches
			this.targetDistance = distance * 12;

			// Change I value depending on the distance being traveled
			if (targetDistance > 6) {
				I = 0.004;
			}
			// Print to SmartDashboard
			SmartDashboard.putNumber("Target Distance (feet)", distance);
		}

		// System.out.println("DriveToDistance(): Constructor ran!");
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Reset encoders before driving
		Robot.encoders.reset();
		// Initialize PID controller // TODO Change to AveragePID
		driveController = new PIDController(P, I, D, F, Robot.encoders.rightEncoder, Robot.driveTrain);
		// Max motor speed
		driveController.setOutputRange(-0.7, 0.7); // 0.6
		// Max error
		driveController.setAbsoluteTolerance(tolerance);
		// Set PID to turn to setpoint
		driveController.setSetpoint(targetDistance);
		// Enable PID controller
		driveController.enable();

		System.out.println("DriveToDistance(): Initialize ran!");
	}

	// Called repeatedly when this Command is scheduled to run
	/**
	 * read the current heading from the IMU store in a temporary variable compare
	 * target heading with what is compared in the new variable if target heading is
	 * < value in the new variable, make drive train turn left if target heading is
	 * > value in new variable, make drive train turn right
	 */
	protected void execute() {
		// If the error is less than the tolerance, wait to make sure we aren't still
		// moving, then finish
		if (Math.abs(driveController.getError()) < tolerance) {
			Timer.delay(.1);
			SmartDashboard.putNumber("DriveToDistance Error", driveController.getError());
			if (Math.abs(driveController.getError()) < tolerance) {
				isFinished = true;
			} else {
				isFinished = false;
			}
		} else {
			isFinished = false;
		}

		// Call drive train
		Robot.driveTrain.pidDrive();

		// Output current error
		SmartDashboard.putNumber("DriveToDistance Error", driveController.getError());

		// Wait for motor update
		Timer.delay(0.005);

		// System.out.println("DriveToDistance(): Execute ran!");
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
		// Stop motors
		Robot.driveTrain.brake();
		// Disable PID controller
		driveController.disable();
		System.out.println("DriveToDistance(): Should be at target!");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		// Disable PID controller
		driveController.disable();
		System.out.println("DriveToDistance(): Interrupted ran!");
	}
}
