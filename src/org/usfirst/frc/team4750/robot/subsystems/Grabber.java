package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.RobotMap;
import org.usfirst.frc.team4750.robot.commands.RunGrabber;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Grabber extends Subsystem {
	
	// Motors
	VictorSP leftArm;
	VictorSP rightArm;
	VictorSP rotate;
	
	// Arm drive
	DifferentialDrive armDrive;
	
	public Grabber() {
		// Initialize motors
		leftArm = new VictorSP(RobotMap.LEFT_ARM_MOTOR_PORT);
		rightArm = new VictorSP(RobotMap.RIGHT_ARM_MOTOR_PORT);
		rotate = new VictorSP(RobotMap.ROTATE_PORT);
		
		// Initialize arm drive
		armDrive = new DifferentialDrive(leftArm, rightArm);
	}
	
	public void armDrive(double speed, double rotateSpeed) {
		armDrive.arcadeDrive(speed, rotateSpeed);
	}

    public void initDefaultCommand() {
        setDefaultCommand(new RunGrabber());
    }
}

