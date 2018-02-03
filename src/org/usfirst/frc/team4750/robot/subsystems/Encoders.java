package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.RobotMap;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Encoders extends Subsystem {

	// Encoders
	Encoder leftEncoder;
	Encoder rightEncoder;
	
	// Variables
	double WHEEL_RADIUS = 3;
	double CIRCUMFRENCE = 2 * Math.PI * WHEEL_RADIUS;
	double PULSES_PER_REVOLUTION = 2000;
	double distancePerPulse = CIRCUMFRENCE / PULSES_PER_REVOLUTION;
	
	public Encoders() {
		leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B, false, EncodingType.k2X);
		rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B, false, EncodingType.k2X);
		leftEncoder.setDistancePerPulse(distancePerPulse);
		rightEncoder.setDistancePerPulse(distancePerPulse);
	}
	
	public void resetLeftEncoder() {
		leftEncoder.reset();
	}
	
	public void resetRightEncoder() {
		rightEncoder.reset();
	}
	
	public double getLeftDistance() {
		return leftEncoder.getDistance();
	}
	
	public double getRightDistance() {
		return rightEncoder.getDistance();
	}
	
    public void initDefaultCommand() {
    }
}

