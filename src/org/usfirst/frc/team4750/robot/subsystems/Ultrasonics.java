package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.RobotMap;
import org.usfirst.frc.team4750.robot.commands.UltrasonicOutput;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Ultrasonics extends Subsystem {

	// Sensors
	Ultrasonic ultrasonic;
	
	public Ultrasonics() {
		// Create an ultrasonic sensor with pingChannel = trigger, echoChannel = echo
		ultrasonic = new Ultrasonic(RobotMap.ULTRASONIC_A, RobotMap.ULTRASONIC_B);
		// Automatically send and receive pulses
		ultrasonic.setAutomaticMode(true);
	}
	
	/**
	 * Use this to get the range in inches
	 * 
	 * @return inches from object
	 */
	public double getInches() {
		return ultrasonic.getRangeInches();
	}
	
	/**
	 * Use this to get the range in feet
	 * 
	 * @return feet from object
	 */
	public double getFeet() {
		return ultrasonic.getRangeInches() / 12;
	}
	
    public void initDefaultCommand() {
    	// Call output command
    	setDefaultCommand(new UltrasonicOutput());
    }
}
