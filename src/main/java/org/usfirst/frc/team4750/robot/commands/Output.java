package org.usfirst.frc.team4750.robot.commands;

import org.usfirst.frc.team4750.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command handles all sensor outputs to the smart dashboard
 * 
 */
public class Output extends Command {
	
    public Output() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		// Output movement in inches
		SmartDashboard.putData("Left Encoder", Robot.encoders.leftEncoder);
		SmartDashboard.putData("Right Encoder", Robot.encoders.rightEncoder);
		
		// Output current heading to dashboard
		SmartDashboard.putData("IMU", Robot.imu.ahrs);
		
		// Output range in inches to dashboard
		SmartDashboard.putData("Ultrasonic", Robot.ultrasonic.ultrasonic);
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
