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
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem implements PIDOutput {

	// Motors
	WPI_TalonSRX frontLeftMotor, frontRightMotor, leftMotor, rightMotor, backLeftMotor, backRightMotor;

	// Controller groups
	SpeedControllerGroup leftMotors, rightMotors;

	// Robot drive
	OurRobotDrive robotDrive;

	// Time (s) to ramp motor speeds
	double ramp = 0;//2;

	// Create PID Controller
	PIDController straightDriveController;

	// PID Output
	double output;
	
	// PID Values
	static final double P = 0.01; // 0.03
	static final double I = 0.003; // 0.003
	static final double D = 0.05; // 0.05
	static final double F = 0.0;
	
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
		
		// Configure velocity ramping
		frontLeftMotor.configOpenloopRamp(ramp, 0);
		frontRightMotor.configOpenloopRamp(ramp, 0);
		leftMotor.configOpenloopRamp(ramp, 0);
		rightMotor.configOpenloopRamp(ramp, 0);
		backLeftMotor.configOpenloopRamp(ramp, 0);
		backRightMotor.configOpenloopRamp(ramp, 0);

		// Initialize controller groups
		leftMotors = new SpeedControllerGroup(frontLeftMotor, leftMotor, backLeftMotor);
		rightMotors = new SpeedControllerGroup(frontRightMotor, rightMotor, backRightMotor);

		// Initialize robot drive
		robotDrive = new OurRobotDrive(leftMotors, rightMotors);
		
		// Initialize PID controller
		straightDriveController = new PIDController(P, I, D, F, Robot.imu.ahrs, Robot.relay);
	}

	// Dual joystick drive
	public void dualDrive(Joystick l, Joystick r) {
		if(straightDriveController.isEnabled()) {
			straightDriveController.disable();
		}
		// Set motor speeds to the joystick values (inverted)
		robotDrive.tankDrive(-l.getY(), -r.getY());
	}

	// Single joystick drive
	public void singleDrive(Joystick j) {
		if(straightDriveController.isEnabled()) {
			// Configure velocity ramping
			frontLeftMotor.configOpenloopRamp(0, 0);
			frontRightMotor.configOpenloopRamp(0, 0);
			leftMotor.configOpenloopRamp(0, 0);
			rightMotor.configOpenloopRamp(0, 0);
			backLeftMotor.configOpenloopRamp(0, 0);
			backRightMotor.configOpenloopRamp(0, 0);
			straightDriveController.disable();
		}
		// Set motor speeds to the joystick values (inverted)
		// Y-axis = forward/backward speed
		// Throttle (twist on joystick) = rotate speed
		robotDrive.arcadeDrive(-j.getY(), j.getThrottle());
	}

	// Drive forward at a certain speed
	public void setDriveSpeed(double speed) {
		if(straightDriveController.isEnabled()) {
			straightDriveController.disable();
		}
		robotDrive.arcadeDrive(speed, 0);
		System.out.println("running");
	}

	// Turn with PID
	public void pidTurn() {
		if(straightDriveController.isEnabled()) {
			straightDriveController.disable();
		}
		robotDrive.tankDrive(output, -output);
	}

	// Drive with PID
	public void pidDrive() {
		if(!straightDriveController.isEnabled()) {
			straightDriveController.setSetpoint(Robot.imu.ahrs.getYaw());
			rotateRate = 0;
			straightDriveController.enable();
		}
		
		double leftSpeed = output + rotateRate;
		double rightSpeed = output - rotateRate;
		
		SmartDashboard.putNumber("Left Speed", leftSpeed);
		SmartDashboard.putNumber("Right Speed", rightSpeed);
		
		robotDrive.tankDrive(leftSpeed, rightSpeed);
	}

	// Stop all motors
	public void brake() {
		if(straightDriveController.isEnabled()) {
			straightDriveController.disable();
		}
		robotDrive.tankDrive(0, 0);
	}

	// Set global output variable to the PIDController output
	@Override
	public void pidWrite(double output) {
		this.output = output;
		SmartDashboard.putNumber("Encoder PID", output);
	}
	
	public void pidStraightWrite(double output) {
		this.rotateRate = output;
		SmartDashboard.putNumber("Adjust PID", output);
	}

	// Set the default command for a subsystem here. This will be called whenever
	// the subsystem is not being used.
	public void initDefaultCommand() {
		// Call controller (teleop) drive
		setDefaultCommand(new TankDrive());
	}
}
