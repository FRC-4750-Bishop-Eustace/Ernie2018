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
	PIDController turnController;
	// Heading to turn to
	private float targetDistance;
	// Check if finished
	private boolean isFinished = false;

	// PID Values
	static final double P = 0.03; // 0.03
	static final double I = 0.0; // 0.003
	static final double D = 0.0; // 0.05
	static final double F = 0.33;

	// Minimum error
	static final double tolerance = 0.5;

	public EncoderDrive(float targetDistanceInches) {
		// Get heading
		this.targetDistance = targetDistanceInches;
		// Print to SmartDashboard
		SmartDashboard.putNumber("Target Distance Inches", this.targetDistance);
		// Subsystem dependency
		requires(Robot.driveTrain);

		// System.out.println("ControlledTurn() Initialize");
	}
	
	public EncoderDrive(float targetDistanceFeet, boolean feet) {
		// Get heading
		this.targetDistance = targetDistanceFeet * 12;
		// Print to SmartDashboard
		SmartDashboard.putNumber("Target Distance Feet", this.targetDistance);
		// Subsystem dependency
		requires(Robot.driveTrain);

		// System.out.println("ControlledTurn() Initialize");
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.encoders.resetEncoders();
		// Initialize PID controller
		turnController = new PIDController(P, I, D, F, Robot.encoders.leftEncoder, Robot.driveTrain);
		// Max motor speed (0.6)
		turnController.setOutputRange(-.6, .6);
		// Max error
		turnController.setAbsoluteTolerance(tolerance);
		// Set PID to turn to setpoint
		turnController.setSetpoint(targetDistance);
		// Enable PID controller
		turnController.enable();

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
		if (Math.abs(turnController.getError()) < tolerance) {
			Timer.delay(.05);
			if (Math.abs(turnController.getError()) < tolerance) {
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
		SmartDashboard.putNumber("getError()", turnController.getError());

		// Wait for motor update
		Timer.delay(0.005);

		// System.out.println("ControlledTurn() Execute");
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// System.out.println("ControlledTurn() isFinished");
		return isFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Should be at target!");
		// Stop motors
		Robot.driveTrain.brake();
		// Disable PID controller
		turnController.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		// System.out.println("ControlledTurn() Interrupted");
	}
}
