package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.RobotMap;
import org.usfirst.frc.team4750.robot.commands.RunElevator;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
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
	
	// Controller group
	SpeedControllerGroup elevator;
	
	// Pistons
	Solenoid elevatorPiston;
	Solenoid releasePiston;
	
	// Reed switches
	DigitalInput lowPos;
	DigitalInput midPos;
	DigitalInput highPos;
	
	// Elevator position, 0 = bottom, 1 = low, 2 = mid, 3 = high
	int position = 0;
	
	public Elevator() {
		// Initialize motors
		leftElevator = new VictorSP(RobotMap.LEFT_ELEVATOR_MOTOR_PORT);
		rightElevator = new VictorSP(RobotMap.RIGHT_ELEVATOR_MOTOR_PORT);
		lifter = new WPI_TalonSRX(RobotMap.LIFTER_MOTOR_ID);
		
		// Initialize controller group
		elevator = new SpeedControllerGroup(leftElevator, rightElevator);
		
		// Initialize pistons
		elevatorPiston = new Solenoid(RobotMap.ELEVATOR_PISTON_PORT);
//		releasePiston = new Solenoid(RobotMap.RELEASE_PISTON_PORT);
		
		// Initialize reed switches
//		lowPos = new DigitalInput(RobotMap.LOW_POSITION_REED_PORT);
//		midPos = new DigitalInput(RobotMap.MID_POSITION_REED_PORT);
//		highPos = new DigitalInput(RobotMap.HIGH_POSITION_REED_PORT);
	}
	
	public void setElevatorSpeed(double speed) {
		elevator.set(speed);
	}
	
	public void stopElevator() {
		elevator.set(0);
	}
	
	public void stopLifter() {
		lifter.set(0);
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
		if(elevatorPiston.get()) {
			return "Elevator";
		}else {
			return "Lifter";
		}
	}
	
	public boolean getLowSwitch() {
		return lowPos.get();
	}
	
	public boolean getMidSwitch() {
		return midPos.get();
	}
	
	public boolean getHighSwitch() {
		return highPos.get();
	}
	
	public double getPosition() {
		if(lowPos.get()) {
			position = 1;
		}else if(midPos.get()) {
			position = 2;
		}else if(highPos.get()) {
			position = 3;
		}else {
			position = 0;
		}
		return position;
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new RunElevator());
    }
}

