package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command handles the autonomous turning mode, keeping track of previous
 * turns to minimize error
 *
 */
public class TurnToAngle extends Command {

	// Create PID Controller
	public PIDController turnController;
	// Heading to turn to
	private float targetHeading;
	// Check if finished
	private boolean isFinished = false;
	// Cumulative commanded heading
	private static float cumulativeCommandedHeading = 0.0f;
	// Cumulative actual heading
	private static float cumulativeActualHeading = 0.0f;
	// Calculate setpoint
	private float setpoint;

	// PID Values
	static final double P = 0.01; // 0.03
	static final double I = 0.003; // 0.003
	static final double D = 0.1; // 0.05
	static final double F = 0.6; // 0.06

	// Minimum error
	static final double toleranceDegrees = 2.0;

	public TurnToAngle(float targetHeading) {
		// Get heading
		this.targetHeading = targetHeading;
		// Print to SmartDashboard
		SmartDashboard.putNumber("Target Heading", this.targetHeading);

		// System.out.println("TurnToAngle(): Contructor ran!");
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Reset the IMU
		Robot.imu.reset();
		// Set the setpoint to the target heading +/- the past error
		setpoint = (cumulativeCommandedHeading - cumulativeActualHeading) + targetHeading;
		// Add the target heading to the commanded heading
		cumulativeCommandedHeading += targetHeading;
		
//		// If the new target is out of the range of the PID controller, reset the IMU
//		// and cumulativeHeading to match
//		if (cumulativeHeading + targetHeading > 180 || cumulativeHeading + targetHeading < -180) {
//			cumulativeHeading = 0;
//			Robot.imu.reset();
//			System.out.println("TurnToAngle(): Out of bounds!");
//		}
		
		// Output
		SmartDashboard.putNumber("Cumulative Heading", cumulativeCommandedHeading);
		SmartDashboard.putNumber("Setpoint for turn", setpoint);
		// Initialize PID controller
		turnController = new PIDController(P, I, D, F, Robot.imu.ahrs, Robot.driveTrain);
		// Min and max angle to turn to
		turnController.setInputRange(-180.0f, 180.0f);
		// Max motor speed (0.6)
		turnController.setOutputRange(-.9, .9);
		// Max error
		turnController.setAbsoluteTolerance(toleranceDegrees);
		turnController.setContinuous(true);
		// Set PID to turn to setpoint
		turnController.setSetpoint(setpoint);
		// Enable PID controller
		turnController.enable();

		// System.out.println("TurnToAngle(): Initialize ran!");
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
		if (turnController.onTarget()) {
			Timer.delay(.05);
			if (turnController.onTarget()) {
				isFinished = true;
			} else {
				isFinished = false;
			}
		} else {
			isFinished = false;
		}

		// Call drive train
		Robot.driveTrain.pidTurn();

		// Output current error
		SmartDashboard.putNumber("turnController: getError()", turnController.getError());

		// Wait for motor update
		Timer.delay(0.005);

		// System.out.println("TurnToAngle(): Execute");
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
		// Add the actual turn to account for any error
		cumulativeActualHeading += Robot.imu.getHeading();
		// Stop motors
		Robot.driveTrain.brake();
		// Disable PID controller
		turnController.disable();
		turnController.reset();
		System.out.println("TurnToAngle(): Should be at target!");
		Timer.delay(0.03);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		// Disable PID controller
		turnController.disable();
		// System.out.println("TurnToAngle(): Interrupted ran!");
	}
}
