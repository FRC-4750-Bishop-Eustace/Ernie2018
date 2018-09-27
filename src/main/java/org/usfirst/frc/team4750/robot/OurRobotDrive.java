package org.usfirst.frc.team4750.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This class acts as a custom DifferentialDrive controller to adjust values and
 * allow speed ("gear") shifting
 *
 */
public class OurRobotDrive extends DifferentialDrive {

	public OurRobotDrive(SpeedController leftMotor, SpeedController rightMotor) {
		super(leftMotor, rightMotor);
	}

	public void tankDrive(double leftSpeed, double rightSpeed) {
		super.tankDrive(cube(leftSpeed), cube(rightSpeed));
	}

	public void arcadeDrive(double forwardSpeed, double turnSpeed) {
		super.arcadeDrive(cube(forwardSpeed), cube(turnSpeed));
	}

	private double cube(double value) {
		return value * .8;
	}

}
