/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot;

import org.usfirst.frc.team4750.robot.commands.Aim;
import org.usfirst.frc.team4750.robot.commands.DriveToDistance;
import org.usfirst.frc.team4750.robot.commands.Seek;
import org.usfirst.frc.team4750.robot.commands.TestFollow;
import org.usfirst.frc.team4750.robot.commands.TurnToAngle;

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
	public static Joystick controlDriveStick = new Joystick(RobotMap.CONTROL_JOYSTICK_PORT);

	// Buttons
	Button turnButton = new JoystickButton(rightDriveStick, 3);
	Button driveButton = new JoystickButton(rightDriveStick, 4);
	Button aimButton = new JoystickButton(leftDriveStick, 3);
	Button seekButton = new JoystickButton(leftDriveStick, 4);
	Button followButton = new JoystickButton(leftDriveStick, 5);

	public OI() {
		turnButton.whenReleased(new TurnToAngle(90));
		driveButton.whenReleased(new DriveToDistance(4, true));
		aimButton.whenReleased(new Aim());
		seekButton.whenReleased(new Seek());
		followButton.whenReleased(new TestFollow());
	}
}
