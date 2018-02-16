/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Joystick ports
	public static final int LEFT_JOYSTICK_PORT = 0;
	public static final int RIGHT_JOYSTICK_PORT = 1;

	// Drive motor IDs
	public static final int FRONT_LEFT_MOTOR_ID = 1;
	public static final int FRONT_RIGHT_MOTOR_ID = 2;
	public static final int LEFT_MOTOR_ID = 3;
	public static final int RIGHT_MOTOR_ID = 4;
	public static final int BACK_LEFT_MOTOR_ID = 5;
	public static final int BACK_RIGHT_MOTOR_ID = 6;

	// Digital (DIO) Ports
	public static final int ULTRASONIC_TRIGGER = 1;
	public static final int ULTRASONIC_ECHO = 0;
	public static final int LEFT_ENCODER_A = 5;
	public static final int LEFT_ENCODER_B = 6;
	public static final int RIGHT_ENCODER_A = 7;
	public static final int RIGHT_ENCODER_B = 8;
}
