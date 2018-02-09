package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.commands.IMUOutput;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class is the interface to the IMU hardware
 *
 */
public class IMU extends Subsystem implements PIDSource {

	public AHRS ahrs;

	public IMU() {
		try {
			// Initialize IMU
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch (Exception e) {
			System.out.println("IMU failed to connect");
		}
	}

	// Set the default command for a subsystem here. This will be called whenever
	// the subsystem is not being used.
	public void initDefaultCommand() {
		// Call output command
		setDefaultCommand(new IMUOutput());
	}

	/**
	 * Call this to reset the IMU
	 * 
	 */
	public void reset() {
		ahrs.reset();
		System.out.println("IMU Reset!");
	}

	/**
	 * Use this to get the current heading
	 * 
	 * @return the degrees - is left + is right
	 */

	public float getHeading() {
		return (float) ahrs.getAngle();
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
