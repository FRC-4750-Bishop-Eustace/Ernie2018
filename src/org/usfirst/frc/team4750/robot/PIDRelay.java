package org.usfirst.frc.team4750.robot;

import edu.wpi.first.wpilibj.PIDOutput;

class PIDRelay implements PIDOutput {

	@Override
	public void pidWrite(double output) {
		Robot.driveTrain.pidStraightWrite(output);
	}
}
