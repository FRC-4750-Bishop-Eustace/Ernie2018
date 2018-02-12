package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class EncoderDrive extends Command {

	// Create PID Controller
	PIDController driveController;
	// Heading to turn to
	private float targetDistance;
	// Check if finished
	private boolean isFinished = false;

	// PID Values
	static final double P = 0.03; // 0.03
	static final double I = 0.0; // 0.003
	static final double D = 0.0; // 0.05
	static final double F = 0.55;

	// Minimum error
	static final double tolerance = 0.2;

	public EncoderDrive(float distance, boolean feet) {
		if(!feet) {
			this.targetDistance = distance;
			// Print to SmartDashboard
			SmartDashboard.putNumber("Target Distance (inches)", this.targetDistance);
		}else {
			this.targetDistance = distance * 12;
			// Print to SmartDashboard
			SmartDashboard.putNumber("Target Distance (feet)", this.targetDistance);
		}
		
		// Subsystem dependency
		requires(Robot.driveTrain);

		// System.out.println("ControlledTurn() Initialize");
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.encoders.resetEncoders();
		// Initialize PID controller
		driveController = new PIDController(P, I, D, F, Robot.pidSource, Robot.driveTrain);
		// Max motor speed (0.6)
		driveController.setOutputRange(-0.6, 0.6);
		// Max error
		driveController.setAbsoluteTolerance(tolerance);
		// Set PID to turn to setpoint
		driveController.setSetpoint(targetDistance);
		// Enable PID controller
		driveController.enable();

		// System.out.println("ControlledTurn() Initialize");
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
			Timer.delay(.05);
			SmartDashboard.putNumber("getError()", driveController.getError());
			if (Math.abs(driveController.getError()) < tolerance) {
				isFinished = true;
				System.out.println("EncoderDrive() isFinished");
			} else {
				isFinished = false;
			}
		} else {
			isFinished = false;
		}

		// Call drive train
		Robot.driveTrain.pidDrive();

		// Output current error
		SmartDashboard.putNumber("getError()", driveController.getError());

		// Wait for motor update
		Timer.delay(0.005);

		// System.out.println("ControlledTurn() Execute");
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Should be at target!");
		// Stop motors
		Robot.driveTrain.brake();
		// Disable PID controller
		driveController.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		// System.out.println("ControlledTurn() Interrupted");
	}
}
