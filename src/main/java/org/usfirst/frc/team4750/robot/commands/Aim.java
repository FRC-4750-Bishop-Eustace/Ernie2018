/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * An example command. You can replace me with your own command.
 */
public class Aim extends Command {

	// Angle from center on x-axis
	double xOffset;
	// Check if finished
	boolean isFinished = false;
	// If the target is in view
	boolean hasTarget;
	// P value
	double P = 0.1;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Get if the target is in view
		hasTarget = Robot.limelight.getHasTarget();
		// Get current offset on the x-axis
		xOffset = -Robot.limelight.getXOffset();
		
		// If the target is in view, turn to the offset
		if(hasTarget) {
			System.out.println("Aim(): Target found!");
			Robot.driveTrain.setTurnSpeed(xOffset * 0.05);
		}else { // If the target is not in view, seek for it
			System.out.println("Aim(): No target found!"); 
			isFinished = true;
		}
		
		// System.out.println("Aim(): Execute ran!");
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// System.out.println("Aim(): End ran!");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		// 		System.out.println("Aim(): Interrupted ran!");
	}
}