package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.RobotMap;
import org.usfirst.frc.team4750.robot.commands.EncoderOutput;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Encoders extends Subsystem implements PIDSource {

	// Encoders
	public Encoder leftEncoder;
	Encoder rightEncoder;
	
	// Variables
	double WHEEL_RADIUS = 3;
	double CIRCUMFRENCE = 2 * Math.PI * WHEEL_RADIUS;
	double PULSES_PER_REVOLUTION = 356.23;
	double distancePerPulse = CIRCUMFRENCE / PULSES_PER_REVOLUTION;
	
	public Encoders() {
		leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B, true, EncodingType.k2X);
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
	
	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public double getLeftCount() {
		return leftEncoder.get();
	}
	
	public double getRightCount() {
		return rightEncoder.get();
	}
	
	public double getLeftDistanceInches() {
		return leftEncoder.getDistance();
	}
	
	public double getRightDistanceInches() {
		return rightEncoder.getDistance();
	}
	
	public double getLeftDistanceFeet() {
		return leftEncoder.getDistance() / 12;
	}
	
	public double getRightDistanceFeet() {
		return rightEncoder.getDistance() / 12;
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new EncoderOutput());
    }

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return 0;
	}
}

