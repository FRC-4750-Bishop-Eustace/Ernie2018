/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4750.robot.Robot;

/**
 * This command finds a vision target if there are none
 * 
 */
public class Seek extends Command {

	// Is a target in view
	boolean hasTarget;
	// Speed to turn at
	double speed = 0.6;
	// Check if finished
	boolean isFinished = false;
	// Aim command
	Command aim;

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		aim = new Aim();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Get if the target is in view
		hasTarget = Robot.limelight.getHasTarget();

		if (!hasTarget) { // If the target is not in view, set the motors to turn the robot
			Robot.driveTrain.setTurnSpeed(speed);
		} else { // If the target is in view, finish
			Robot.driveTrain.brake();
			isFinished = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		aim.start();
		System.out.println("Seek(): Target found!");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
