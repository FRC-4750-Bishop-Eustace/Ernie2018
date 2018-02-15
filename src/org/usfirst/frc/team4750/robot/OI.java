/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot;


import org.usfirst.frc.team4750.robot.commands.TurnToAngle;
import org.usfirst.frc.team4750.robot.commands.DriveToDistance;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// Joysticks
	public static Joystick leftDriveStick = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);
	public static Joystick rightDriveStick = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);

	// Buttons
	Button driveButton = new JoystickButton(leftDriveStick, 2);
	Button driveBackButton = new JoystickButton(rightDriveStick, 2);
	Button turnLeftButton = new JoystickButton(leftDriveStick, 3);
	Button turnRightButton = new JoystickButton(rightDriveStick, 4);
	
	public OI() {
		driveButton.whenReleased(new DriveToDistance(1, true));
		driveBackButton.whenReleased(new DriveToDistance(-1, true));
		turnLeftButton.whenReleased(new TurnToAngle(-90));
		turnRightButton.whenReleased(new TurnToAngle(90));
	}
}
