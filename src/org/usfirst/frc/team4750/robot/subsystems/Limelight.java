/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class handles the values of the limelight camera
 * 
 */
public class Limelight extends Subsystem {
	
	//Create network table
	NetworkTable table;
	
	//Create variables
	boolean hasTarget;
	double xOffset, yOffset, area, skew, ledMode, camMode, pipeline;

	public Limelight() {
		// Get the limelight table from the network table
		table = NetworkTableInstance.getDefault().getTable("limelight");
	}
	
	/**
	 * Use this to get if a target is in view
	 * 
	 * @return if the target is in view
	 */
	public boolean getHasTarget() {
		double targetD = table.getEntry("tv").getDouble(0);
		if(targetD == 0) {
			hasTarget = false;
		}else if(targetD == 1) {
			hasTarget = true;
		}
		return hasTarget;
	}
	
	/**
	 * Use this to get the x-offset from the target
	 * 
	 * @return xOffset from the target
	 */
	public double getXOffset() {
		xOffset = table.getEntry("tx").getDouble(0);
		return xOffset;
	}
	
	/**
	 * Use this to get the y-offset from the target
	 * 
	 * @return yOffset from the target
	 */
	public double getYOffset() {
		yOffset = table.getEntry("ty").getDouble(0);
		return yOffset;
	}
	
	/**
	 * Use this to get the area of the target
	 * 
	 * @return area of the target
	 */
	public double getArea() {
		area = table.getEntry("ta").getDouble(0);
		return area;
	}
	
	/**
	 * Use this to get the skew of the target
	 * 
	 * @return skew of the target
	 */
	public double getSkew() {
		skew = table.getEntry("ts").getDouble(0);
		return skew;
	}
	
	/**
	 * Use this to get the current LED mode of the camera
	 * 
	 * @return 0 = on, 1 = off, 2 = blink
	 */
	public double getLEDMode() {
		ledMode = table.getEntry("ledMode").getDouble(0);
		return ledMode;
	}
	
	/**
	 * Use this to get the current mode of the camera
	 * 
	 * @return 0 = thresholded, 1 = raw image
	 */
	public double getCamMode() {
		camMode = table.getEntry("camMode").getDouble(0);
		return camMode;
	}
	/**
	 * Use this to get the current pipeline number
	 * 
	 * @return number of pipeline 0-9
	 */
	public double getPipeline() {
		pipeline = table.getEntry("pipeline").getDouble(0);
		return pipeline;
	}
	
	// Cycle LED modes, set to off if blinking
	public void switchLED() {
		if(getLEDMode() == 0) {
			table.getEntry("ledMode").setDouble(1);
			SmartDashboard.putString("LED Mode", "Off");
		}else if(getLEDMode() == 1) {
			table.getEntry("ledMode").setDouble(0);
			SmartDashboard.putString("LED Mode", "On");
		}else if(getLEDMode() == 2) {
			table.getEntry("ledMode").setDouble(1);
			SmartDashboard.putString("LED Mode", "Off");
		}
	}
	
	// Cycle camera modes
	public void switchCamera() {
		if(getCamMode() == 0) {
			table.getEntry("camMode").setDouble(1);
			SmartDashboard.putString("Camera Mode", "Camera");
		}else if(getCamMode() == 1) {
			table.getEntry("camMode").setDouble(0);
			SmartDashboard.putString("Camera Mode", "Vision");
		}
	}
	
	// Change to a specific pipeline
	public void setPipeline(double pipeline) {
		table.getEntry("pipeline").setDouble(pipeline);
		SmartDashboard.putNumber("Pipeline", pipeline);
	}
	
	public void initDefaultCommand() {
	}
}
