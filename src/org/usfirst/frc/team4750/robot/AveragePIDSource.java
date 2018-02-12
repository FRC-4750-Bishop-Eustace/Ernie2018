package org.usfirst.frc.team4750.robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

class AveragePIDSource implements PIDSource {
	
	double average;

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		average = (Robot.encoders.getLeftDistanceInches() + Robot.encoders.getRightDistanceInches()) / 2;
		return average;
	}
	
}
