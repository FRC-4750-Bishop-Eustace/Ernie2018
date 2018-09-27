package org.usfirst.frc.team4750.robot;

import java.awt.Point;
import java.util.HashMap;

import org.usfirst.frc.team4750.robot.commands.DriveForward;
import org.usfirst.frc.team4750.robot.commands.Reset;
import org.usfirst.frc.team4750.robot.pathfinding.AStar;
import org.usfirst.frc.team4750.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4750.robot.subsystems.Encoders;
import org.usfirst.frc.team4750.robot.subsystems.IMU;
import org.usfirst.frc.team4750.robot.subsystems.Limelight;
import org.usfirst.frc.team4750.robot.subsystems.Ultrasonics;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	// Initialize subsystems
	public static final StraightDriveSource averageEncoderPIDSource = new StraightDriveSource();
	public static final StraightDrivePIDRelay straightDrivePIDRelay = new StraightDrivePIDRelay();
	public static final IMU imu = new IMU();
	public static final DriveTrain driveTrain = new DriveTrain(RobotMap.FRONT_LEFT_MOTOR_ID,
			RobotMap.FRONT_RIGHT_MOTOR_ID, RobotMap.LEFT_MOTOR_ID, RobotMap.RIGHT_MOTOR_ID, RobotMap.BACK_LEFT_MOTOR_ID,
			RobotMap.BACK_RIGHT_MOTOR_ID);
	public static final Ultrasonics ultrasonic = new Ultrasonics();
	public static final Encoders encoders = new Encoders();
	public static final Limelight limelight = new Limelight();
	public static OI oi;

	// Autonomous data
	static String gameData;
	Command autonomousCommand;

	// Autonomous choosers
	SendableChooser<String> startPositionChooser = new SendableChooser<>();
	static ObjectiveChooser objectiveLL = new ObjectiveChooser();
	static ObjectiveChooser objectiveLR = new ObjectiveChooser();
	static ObjectiveChooser objectiveRL = new ObjectiveChooser();
	static ObjectiveChooser objectiveRR = new ObjectiveChooser();
	static OrientationChooser orientationLL = new OrientationChooser();
	static OrientationChooser orientationLR = new OrientationChooser();
	static OrientationChooser orientationRL = new OrientationChooser();
	static OrientationChooser orientationRR = new OrientationChooser();

	// Hash maps for starting and objective points
	public static HashMap<String, Point> startPositions;
	public static HashMap<String, Point> autonPositions;

	Point finalPosition;

	// Reset command
	Command reset = new Reset();
	
	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();

		// Starting point hash map
		startPositions = new HashMap<String, Point>();
		startPositions.put("m", new Point(14, 2));
		startPositions.put("l", new Point(24, 2));
		startPositions.put("r", new Point(4, 2));

		// Auton hash map
		autonPositions = new HashMap<String, Point>();
		autonPositions.put("fls", new Point(20, 12));
		autonPositions.put("frs", new Point(8, 12));
		autonPositions.put("ls", new Point(21, 15));
		autonPositions.put("rs", new Point(7, 15));
		autonPositions.put("bls", new Point(20, 19));
		autonPositions.put("brs", new Point(8, 19));
		autonPositions.put("flsc", new Point(23, 25));
		autonPositions.put("frsc", new Point(5, 25));
		autonPositions.put("lsc", new Point(23, 27));
		autonPositions.put("rsc", new Point(7, 27));

		// Start position chooser
		startPositionChooser.addDefault("Middle", "m");
		startPositionChooser.addObject("Left", "l");
		startPositionChooser.addObject("Right", "r");
		SmartDashboard.putData("Start Position", startPositionChooser);

		// Objective choosers
		SmartDashboard.putData("LL Objective", objectiveLL.getChooser());
		SmartDashboard.putData("LR Objective", objectiveLR.getChooser());
		SmartDashboard.putData("RL Objective", objectiveRL.getChooser());
		SmartDashboard.putData("RR Objective", objectiveRR.getChooser());

		// Orientation choosers
		SmartDashboard.putData("LL Orientation", orientationLL.getChooser());
		SmartDashboard.putData("LR Orientation", orientationLR.getChooser());
		SmartDashboard.putData("RL Orientation", orientationRL.getChooser());
		SmartDashboard.putData("RR Orientation", orientationRR.getChooser());

		// Reset sensors
		reset.start();
		// Put reset command on dashboard
		SmartDashboard.putData("Reset", reset);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		// Get plate randomization while disabled
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		// Set limelight mode to vision for cube tracking
		limelight.setCameraMode("Vision");
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		 // Create planner
		 AStar pathPlanner = new AStar();
		 try {// Try to get a route
		 autonomousCommand = pathPlanner.getPath(getStartPoint(getStart()),
		 getGoalPoint(getGoal()));
		 } catch (Exception e) { // Failed route, drive to baseline
		 System.out.println("PathPlanning failed: ");
		 e.printStackTrace();
		 autonomousCommand = new DriveForward();
		 }
//		if (startPositionChooser.getSelected().equalsIgnoreCase("m")) {
//			if (gameData.substring(0, 1).equalsIgnoreCase("R")) {
//				autonomousCommand = new CenterSwitchAuton();
//			} else {
//				autonomousCommand = new SwitchBaseline();
//			}
//		} else if (startPositionChooser.getSelected().equalsIgnoreCase("l")) {
//			if (gameData.substring(0, 1).equalsIgnoreCase("R")) {
//				autonomousCommand = new DriveToScale();
//			} else {
//				autonomousCommand = new LeftSwitchAuton();
//			}
//		} else {
//			if (gameData.substring(0, 1).equalsIgnoreCase("R")) {
//				autonomousCommand = new RightSwitchAuton();
//			} else {
//				autonomousCommand = new DriveToScale();
//			}
//		}

		// Schedule the autonomous command
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// Set limelight mode to vision for cube tracking
		limelight.setCameraMode("Vision");

		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	/**
	 * This method returns the selected objective for whatever order the plates are
	 * in
	 * 
	 * @return goal position
	 */
	public static String getGoal() {
		if (gameData == null) {
			return null;
		}
		char switchPos = gameData.charAt(0);
		System.out.println(switchPos);
		char scalePos = gameData.charAt(1);
		System.out.println(scalePos);

		if (switchPos == 'L' && scalePos == 'L') {
			return objectiveLL.getSelected();
		} else if (switchPos == 'L' && scalePos == 'R') {
			return objectiveLR.getSelected();
		} else if (switchPos == 'R' && scalePos == 'L') {
			return objectiveRL.getSelected();
		} else if (switchPos == 'R' && scalePos == 'R') {
			return objectiveRR.getSelected();
		}else {
			System.out.println("Returning null...");
			return null;
		}
	}

	/**
	 * This method searches the hash map for the position to go to and returns the
	 * point of that position
	 * 
	 * @param position
	 *            - normally gotten from the getGoal() method
	 * @return goal position point
	 */
	public Point getGoalPoint(String position) {
		return autonPositions.get(position);
	}

	/**
	 * This method returns the currently selected start position
	 * 
	 * @return start position
	 */
	public String getStart() {
		return startPositionChooser.getSelected();
	}

	/**
	 * This method searches the hash map for the starting position to and returns
	 * the point of that position
	 * 
	 * @param position
	 *            - normally gotten from the getStart() method
	 * @return start position point
	 */
	public Point getStartPoint(String position) {
		return startPositions.get(position);
	}

	/**
	 * This method returns the selected orientation for whatever order the plates
	 * are in
	 * 
	 * @return goal orientation
	 */
	public String getGoalOrientation() {
		if (gameData == null) {
			return null;
		}

		String switchPos = gameData.substring(0, 1);
		String scalePos = gameData.substring(1, 2);

		if (switchPos == "L" && scalePos == "L") {
			return orientationLL.getSelected();
		} else if (switchPos == "L" && scalePos == "R") {
			return orientationLR.getSelected();
		} else if (switchPos == "R" && scalePos == "L") {
			return orientationRL.getSelected();
		} else if (switchPos == "R" && scalePos == "R") {
			return orientationRR.getSelected();
		}
		return null;
	}
}
