/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.OurRobotDrive;
import org.usfirst.frc.team4750.robot.commands.TankDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	// Motors
	WPI_TalonSRX frontLeftMotor, frontRightMotor, leftMotor, rightMotor, backLeftMotor, backRightMotor;

	// Controller groups
	SpeedControllerGroup leftMotors, rightMotors;

	// Robot drive
	OurRobotDrive robotDrive;

	public DriveTrain(int frontLeftMotorPort, int frontRightMotorPort, int leftMotorPort, int rightMotorPort, int backLeftMotorPort, int backRightMotorPort) {

		// Initialize motors
		frontLeftMotor = new WPI_TalonSRX(frontLeftMotorPort);
		frontRightMotor = new WPI_TalonSRX(frontRightMotorPort);
		leftMotor = new WPI_TalonSRX(leftMotorPort);
		rightMotor = new WPI_TalonSRX(rightMotorPort);
		backLeftMotor = new WPI_TalonSRX(backLeftMotorPort);
		backRightMotor = new WPI_TalonSRX(backRightMotorPort);

		// initialize controller groups
		leftMotors = new SpeedControllerGroup(frontLeftMotor, leftMotor, backLeftMotor);
		rightMotors = new SpeedControllerGroup(frontRightMotor, rightMotor, backRightMotor);

		// initialize robot drive
		robotDrive = new OurRobotDrive(leftMotors, rightMotors);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new TankDrive());
	}

	// Joystick (teleop) drive
	public void controllerDrive(Joystick l, Joystick r) {
		// Set motor speeds to the joystick values (inverted)
		robotDrive.tankDrive(-l.getY(), -r.getY());
	}
}
