package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class EncoderOutput extends Command {

    public EncoderOutput() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.encoders);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	requires(Robot.encoders);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Left Encoder", Robot.encoders.getLeftCount());
    	SmartDashboard.putNumber("Right Encoder", Robot.encoders.getRightCount());
    	SmartDashboard.putNumber("Left Encoder Distance Inches", Robot.encoders.getLeftDistanceInches());
    	SmartDashboard.putNumber("Right Encoder Distance Inches", Robot.encoders.getRightDistanceInches());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
