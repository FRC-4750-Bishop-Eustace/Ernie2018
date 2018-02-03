package org.usfirst.frc.team4750.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class OurRobotDrive extends DifferentialDrive {
	
	double constant = 0.1;

	public OurRobotDrive(SpeedController leftMotor, SpeedController rightMotor) {
		super(leftMotor, rightMotor);
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed) {
		super.tankDrive(cube(leftSpeed), cube(rightSpeed));
	}
	
	
	private double cube(double value) {
		return (value * constant) + (1 - constant) * (value * value * value);
		
	}
	
}
