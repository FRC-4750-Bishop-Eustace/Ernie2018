/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.OurRobotDrive;
import org.usfirst.frc.team4750.robot.Robot;
import org.usfirst.frc.team4750.robot.commands.TankDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class contains all the methods to control the drive train in any form
 * 
 */
public class DriveTrain extends Subsystem implements PIDOutput {

	// Motors
	WPI_TalonSRX frontLeftMotor, frontRightMotor, leftMotor, rightMotor, backLeftMotor, backRightMotor;

	// Controller groups
	SpeedControllerGroup leftMotors, rightMotors;

	// Robot drive
	OurRobotDrive robotDrive;

	// Time to ramp motor speeds
	double ramp = 0; // 2
	int rampTimeout = 0;

	// Create PID Controller
	PIDController syncController;

	// PID Output
	double output;

	// PID Values
	static final double P = 0.01; // 0.01
	static final double I = 0.0; // 0.003
	static final double D = 0.05; // 0.05
	static final double F = 0.0; // 0.0

	// Rate to straighten drive train
	double rotateRate;

	public DriveTrain(int frontLeftMotorPort, int frontRightMotorPort, int leftMotorPort, int rightMotorPort,
			int backLeftMotorPort, int backRightMotorPort) {

		// Initialize motors
		frontLeftMotor = new WPI_TalonSRX(frontLeftMotorPort);
		frontRightMotor = new WPI_TalonSRX(frontRightMotorPort);
		leftMotor = new WPI_TalonSRX(leftMotorPort);
		rightMotor = new WPI_TalonSRX(rightMotorPort);
		backLeftMotor = new WPI_TalonSRX(backLeftMotorPort);
		backRightMotor = new WPI_TalonSRX(backRightMotorPort);

		// Initialize controller groups
		leftMotors = new SpeedControllerGroup(frontLeftMotor, leftMotor, backLeftMotor);
		rightMotors = new SpeedControllerGroup(frontRightMotor, rightMotor, backRightMotor);

		// Initialize robot drive
		robotDrive = new OurRobotDrive(leftMotors, rightMotors);
		
		robotDrive.setSafetyEnabled(false);

		// Initialize PID controller
		syncController = new PIDController(P, I, D, F, Robot.imu.ahrs, Robot.straightDrivePIDRelay);
	}

	// Dual joystick drive
	public void dualDrive(Joystick l, Joystick r) {
		// Set motor speeds to the joystick values (inverted)
		robotDrive.tankDrive(-l.getY(), -r.getY());
	}

	// Single joystick drive
	public void singleDrive(Joystick j) {
		// Set motor speeds to the joystick values (inverted)
		// Y-axis = forward/backward speed
		// Throttle (twist on joystick) = rotate speed
		robotDrive.arcadeDrive(-j.getY(), j.getThrottle());
	}
	
	// Turn at a certain speed
	public void setTurnSpeed(double speed) {
		if (syncController.isEnabled()) {
			syncController.disable();
		}
		robotDrive.arcadeDrive(0, speed);
	}

	// Drive forward at a certain speed
	public void setDriveSpeed(double speed) {
		if (syncController.isEnabled()) {
			syncController.disable();
		}
		robotDrive.arcadeDrive(speed, 0);
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed) {
		robotDrive.tankDrive(leftSpeed, rightSpeed);
	}
	
	// Turn with PID
	public void pidTurn() {
		if (syncController.isEnabled()) {
			syncController.disable();
			syncController.reset();
		}
		robotDrive.tankDrive(output, -output);
	}

	// Drive with PID
	public void pidDrive() {
		if (!syncController.isEnabled()) {
			// Set setpoint to the current angle
			syncController.setSetpoint(Robot.imu.getHeading());
			// Enable PID controller
			syncController.enable();
		}

		// Set speeds to DriveToDistance output +/- straightDriveController output
		// (rotateRate)
		double leftSpeed = output + rotateRate;
		double rightSpeed = output - rotateRate;
		
		// Output motor speeds
		SmartDashboard.putNumber("Left Speed", leftSpeed);
		SmartDashboard.putNumber("Right Speed", rightSpeed);

		robotDrive.tankDrive(leftSpeed, rightSpeed);
	}

	// Stop all motors
	public void brake() {
		if (syncController.isEnabled()) {
			syncController.disable();
			syncController.reset();
		}
		robotDrive.tankDrive(0, 0);
	}

	// Set global output variable to the PIDController output (turnController,
	// driveController)
	@Override
	public void pidWrite(double output) {
		this.output = output;
		SmartDashboard.putNumber("Encoder PID", output);
	}

	// Set global output variable to the PIDController output (syncController)
	public void pidStraightWrite(double output) {
		this.rotateRate = output;
		SmartDashboard.putNumber("Adjust PID", output);
	}

	public void initDefaultCommand() {
		// Call controlled (teleop) drive
		setDefaultCommand(new TankDrive());
	}
}
