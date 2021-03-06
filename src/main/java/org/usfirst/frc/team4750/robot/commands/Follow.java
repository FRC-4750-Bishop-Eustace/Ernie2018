/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example command. You can replace me with your own command.
 */
public class Follow extends Command {

	// Angle from center on x-axis
	double yOffset;
	// Check if finished
	boolean isFinished = false;
	// Turn command
	Command drive;
	// If the target is in view
	boolean hasTarget;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Get if the target is in view
		hasTarget = Robot.limelight.getHasTarget();
		// Get current offset on the x-axis
		yOffset = -Robot.limelight.getXOffset();
		SmartDashboard.putNumber("Follow yOffset", yOffset);
		
		// If the target is in view, turn to the offset
		if(hasTarget) {
			System.out.println("Follow(): Target found!"); 
			drive = new DriveToDistance((float) yOffset, true);
			drive.start();
			isFinished = true;
		}else { // If the target is not in view, seek for it
			System.out.println("Follow(): No target found!"); 
			isFinished = true;
		}
		
		// System.out.println("Follow(): Execute ran!");
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// System.out.println("Follow(): End ran!");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		// 		System.out.println("Follow(): Interrupted ran!");
	}
}