package org.usfirst.frc.team4750.robot.pathfinding;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.usfirst.frc.team4750.robot.commands.AStarAuton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AStar {
	
	public AStar() {
		
	}

	public CommandGroup getPath(Point startPoint, Point targetPoint)
			throws InvalidLetterException, FileNotFoundException, IOException, HeapException {

		InputHandler handler = new InputHandler();
		SquareGraph graph = handler.readMap(startPoint, targetPoint);

		ArrayList<Node> path = graph.executeAStar();
		ArrayList<Command> drivePath = graph.makeDrivePath(path);

		if (path == null) {
			System.out.println("There is no path to target");
		} else {
			System.out.println("--- Path to target ---");
			graph.printPath(path);
			System.out.println("");
			System.out.println("--- Translated path ---");
			graph.translatePath(path);
			System.out.println("");
		}
		AStarAuton autonGroup = new AStarAuton(drivePath);
		return autonGroup;
	}

}