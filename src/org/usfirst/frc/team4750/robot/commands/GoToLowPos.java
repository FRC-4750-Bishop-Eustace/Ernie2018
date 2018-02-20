package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GoToLowPos extends Command {
	
	// Speeds
	double downSpeed = -0.3;
	double upSpeed = 0.3;
	
	// Check if finished
	boolean isFinished = false;

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.elevator.getLowSwitch()) {
    		if(Robot.elevator.getPosition() > 1) {
        		Robot.elevator.setElevatorSpeed(downSpeed);
    		}else if(Robot.elevator.getPosition() < 1){
    			Robot.elevator.setElevatorSpeed(upSpeed);
    		}
    	}else {
    		Robot.elevator.stopElevator();
    		isFinished = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
