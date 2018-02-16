/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot;

import org.usfirst.frc.team4750.robot.commands.EncoderReset;
import org.usfirst.frc.team4750.robot.commands.LeftAuton;
import org.usfirst.frc.team4750.robot.commands.MiddleAuton;
import org.usfirst.frc.team4750.robot.commands.RightAuton;
import org.usfirst.frc.team4750.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4750.robot.subsystems.Encoders;
import org.usfirst.frc.team4750.robot.subsystems.IMU;
import org.usfirst.frc.team4750.robot.subsystems.Ultrasonics;
import org.usfirst.frc.team4750.robot.subsystems.Limelight;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	public static final AveragePIDSource pidSource = new AveragePIDSource();

	public static final PIDRelay relay = new PIDRelay();

	public static final IMU imu = new IMU();

	public static final DriveTrain driveTrain = new DriveTrain(RobotMap.FRONT_LEFT_MOTOR_ID,
			RobotMap.FRONT_RIGHT_MOTOR_ID, RobotMap.LEFT_MOTOR_ID, RobotMap.RIGHT_MOTOR_ID, RobotMap.BACK_LEFT_MOTOR_ID,
			RobotMap.BACK_RIGHT_MOTOR_ID);

	public static final Ultrasonics ultrasonic = new Ultrasonics();
	
	public static final Limelight limelight = new Limelight();

	public static final Encoders encoders = new Encoders();

	public static OI oi;

	String gameData;
	Command autonomousCommand;
	SendableChooser<String> chooser = new SendableChooser<>();
	Command reset;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		reset = new EncoderReset();
		chooser.addDefault("Middle", "M");
		chooser.addObject("Left", "L");
		chooser.addObject("Right", "R");
		SmartDashboard.putData("Auto mode", chooser);
		encoders.resetLeftEncoder();
		encoders.resetRightEncoder();
		imu.reset();
		SmartDashboard.putData("Reset Encoders", reset);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		switch(chooser.getSelected()) {
		case "M": 
			switch(gameData.charAt(0)) {
			case 'L':
				autonomousCommand = new LeftAuton();
				break;
			case 'R':
				autonomousCommand = new RightAuton();
				break;
			}
			break;
		case "L":
			switch(gameData.charAt(0)) {
			case 'L':
				autonomousCommand = new MiddleAuton();
				break;
			case 'R':
				autonomousCommand = new RightAuton();
				break;
			}
			break;
		case "R":
			switch(gameData.charAt(0)) {
			case 'L':
				autonomousCommand = new LeftAuton();
				break;
			case 'R':
				autonomousCommand = new MiddleAuton();
				break;
			}
			break;
		}

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Z axis", ((OI.rightDriveStick.getZ() - 1) / -2));
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
