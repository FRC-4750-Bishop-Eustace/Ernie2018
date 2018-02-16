/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.OI;
import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command calls the drive train to run the default joystick drive
 * When button two on the left drive stick is held down, it will switch from
 * single stick arcade drive to dual stick tank drive
 * 
 */
public class TankDrive extends Command {
	
	// Create button
	Button dualDriveButton = new JoystickButton(OI.leftDriveStick, 2);
	
	public TankDrive() {
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		requires(Robot.driveTrain);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if(dualDriveButton.get()) {
			// Call dual joystick drive and pass in both joysticks
			Robot.driveTrain.dualDrive(OI.leftDriveStick, OI.rightDriveStick);
		}else {
			// Call single joystick drive and pass in the right joystick
			Robot.driveTrain.singleDrive(OI.rightDriveStick);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
