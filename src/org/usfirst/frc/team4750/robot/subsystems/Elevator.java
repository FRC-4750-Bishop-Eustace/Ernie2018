package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.RobotMap;
import org.usfirst.frc.team4750.robot.commands.RunElevator;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	
	// Motors
	VictorSP leftElevator;
	VictorSP rightElevator;
	WPI_TalonSRX lifter;
	
	// Pistons
	Solenoid elevatorPiston;
	Solenoid releasePiston;
	
	// Controller group
	SpeedControllerGroup elevator;
	
	// Control elevator vs. lifting
	boolean mode = false;
	
	public Elevator() {
		// Initialize motors
		leftElevator = new VictorSP(RobotMap.LEFT_ELEVATOR_MOTOR_PORT);
		rightElevator = new VictorSP(RobotMap.RIGHT_ELEVATOR_MOTOR_PORT);
		lifter = new WPI_TalonSRX(RobotMap.LIFTER_MOTOR_ID);
		
		// Initialize pistons
		elevatorPiston = new Solenoid(RobotMap.ELEVATOR_PISTON_PORT);
		releasePiston = new Solenoid(RobotMap.RELEASE_PISTON_PORT);
		
		// Initialize controller group
		elevator = new SpeedControllerGroup(leftElevator, rightElevator);
	}
	
	public void setElevatorSpeed(double speed) {
		elevator.set(speed);
	}
	
	public void setLifterSpeed(double speed) {
		lifter.set(speed);
	}
	
	public void switchElevatorPiston() {
		if(!elevatorPiston.get()) {
			elevatorPiston.set(true);
		}else {
			elevatorPiston.set(false);
		}
	}
	
	public void releasePiston() {
		releasePiston.set(false);
	}
	
	public String getMode() {
		if(!mode) {
			return "Elevator";
		}else {
			return "Lifter";
		}
	}
	
	public void switchMode() {
		if(!mode) {
			mode = true;
		}else {
			mode = false;
		}
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new RunElevator());
    }
}

