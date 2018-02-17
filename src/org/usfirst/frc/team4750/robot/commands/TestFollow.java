package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestFollow extends Command {

	boolean isFinished;
	double yOffset;
	double xOffset;
	
    public TestFollow() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	yOffset = Robot.limelight.getYOffset();
    	xOffset = Robot.limelight.getXOffset();
    		if(yOffset > 0.2) {
    			Robot.driveTrain.setDriveSpeed(.4 * yOffset);
    		}else if(yOffset < -0.2) {
    			Robot.driveTrain.setDriveSpeed(.4 * yOffset);
    		}
//    		}else {
//    			isFinished = true;
//    		}
    	
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
