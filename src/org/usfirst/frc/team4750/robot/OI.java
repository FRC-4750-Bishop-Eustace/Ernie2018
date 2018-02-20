/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot;

import org.usfirst.frc.team4750.robot.commands.GoToHighPos;
import org.usfirst.frc.team4750.robot.commands.GoToLowPos;
import org.usfirst.frc.team4750.robot.commands.GoToMidPos;
import org.usfirst.frc.team4750.robot.commands.ReleasePiston;
import org.usfirst.frc.team4750.robot.commands.SwitchCameraMode;
import org.usfirst.frc.team4750.robot.commands.SwitchElevatorMode;
import org.usfirst.frc.team4750.robot.commands.SwitchLEDMode;
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
	Button turnButton = new JoystickButton(rightDriveStick, 2);
	Button aimButton = new JoystickButton(leftDriveStick, 3);
	Button seekButton = new JoystickButton(leftDriveStick, 4);
	Button followButton = new JoystickButton(leftDriveStick, 5);
	Button camButton = new JoystickButton(rightDriveStick, 3);
	Button ledButton = new JoystickButton(rightDriveStick, 4);
	Button switchElevatorPiston = new JoystickButton(controlDriveStick, 1);
	Button releasePistonButton = new JoystickButton(controlDriveStick, 2);
	Button lowPosButton = new JoystickButton(controlDriveStick, 3);
	Button midPosButton = new JoystickButton(controlDriveStick, 4);
	Button highPosButton = new JoystickButton(controlDriveStick, 5);
	Button switchButton = new JoystickButton(controlDriveStick, 2);

	public OI() {
		turnButton.whenReleased(new TurnToAngle(-90));
//		aimButton.whenReleased(new Aim());
//		seekButton.whenReleased(new Seek());
		followButton.whenReleased(new TestFollow());
		camButton.whenReleased(new SwitchCameraMode());
		ledButton.whenReleased(new SwitchLEDMode());
		switchElevatorPiston.whenReleased(new SwitchElevatorMode());
		releasePistonButton.whenReleased(new ReleasePiston());
		lowPosButton.whenReleased(new GoToLowPos());
		midPosButton.whenReleased(new GoToMidPos());
		highPosButton.whenReleased(new GoToHighPos());
	}
}
