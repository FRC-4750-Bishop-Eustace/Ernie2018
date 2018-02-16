/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot;

import org.usfirst.frc.team4750.robot.commands.Aim;
import org.usfirst.frc.team4750.robot.commands.Seek;
import org.usfirst.frc.team4750.robot.commands.SwitchCameraMode;
import org.usfirst.frc.team4750.robot.commands.SwitchLEDMode;
import org.usfirst.frc.team4750.robot.commands.TurnDegrees;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 */
public class OI {
	
	// Joysticks
	public static Joystick leftDriveStick = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);
	public static Joystick rightDriveStick = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);

	// Buttons
	Button ledButton = new JoystickButton(leftDriveStick, 3);
	Button camButton = new JoystickButton(leftDriveStick, 4);
	Button seekButton = new JoystickButton(rightDriveStick, 3);
	Button aimButton = new JoystickButton(rightDriveStick, 4);
	Button turnButton = new JoystickButton(leftDriveStick, 2);
	
	public OI() {
		ledButton.whenReleased(new SwitchLEDMode());
		camButton.whenReleased(new SwitchCameraMode());
		seekButton.whenReleased(new Seek());
		aimButton.whenReleased(new Aim());
		turnButton.whenReleased(new TurnDegrees(90));
	}
}
