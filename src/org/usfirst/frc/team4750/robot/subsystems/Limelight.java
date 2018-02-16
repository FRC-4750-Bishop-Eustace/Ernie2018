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
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Limelight extends Subsystem {
	
	//Create network table
	NetworkTable table;
	
	//Create variables
	boolean hasTarget;
	double xOffset, yOffset, area, skew, ledMode, camMode, pipeline;

	public Limelight() {
		table = NetworkTableInstance.getDefault().getTable("limelight");
	}
	
	public boolean getHasTarget() {
		double targetD = table.getEntry("tv").getDouble(0);
		if(targetD == 0) {
			hasTarget = false;
		}else if(targetD == 1) {
			hasTarget = true;
		}
		return hasTarget;
	}
	
	public double getXOffset() {
		xOffset = table.getEntry("tx").getDouble(0);
		return xOffset;
	}
	
	public double getYOffset() {
		yOffset = table.getEntry("ty").getDouble(0);
		return yOffset;
	}
	
	public double getArea() {
		area = table.getEntry("ta").getDouble(0);
		return area;
	}
	
	public double getSkew() {
		skew = table.getEntry("ts").getDouble(0);
		return skew;
	}
	
	public double getLEDMode() {
		ledMode = table.getEntry("ledMode").getDouble(1);
		return ledMode;
	}
	
	public double getCamMode() {
		camMode = table.getEntry("camMode").getDouble(0);
		return camMode;
	}
	
	public double getPipeline() {
		pipeline = table.getEntry("pipeline").getDouble(0);
		return pipeline;
	}
	
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
	
	public void switchCamera() {
		if(getCamMode() == 0) {
			table.getEntry("camMode").setDouble(1);
			SmartDashboard.putString("Camera Mode", "Camera");
		}else if(getCamMode() == 1) {
			table.getEntry("camMode").setDouble(0);
			SmartDashboard.putString("Camera Mode", "Vision");
		}
	}
	
	public void setPipeline(double pipeline) {
		table.getEntry("pipeline").setDouble(pipeline);
		SmartDashboard.putNumber("Camera Mode", pipeline);
	}
	
	public void initDefaultCommand() {
	}
}
