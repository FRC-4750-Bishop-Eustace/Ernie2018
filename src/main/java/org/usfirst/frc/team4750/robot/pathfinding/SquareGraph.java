package org.usfirst.frc.team4750.robot.pathfinding;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.usfirst.frc.team4750.robot.Robot;
import org.usfirst.frc.team4750.robot.commands.DriveToDistance;
import org.usfirst.frc.team4750.robot.commands.DriveToSwitch;
import org.usfirst.frc.team4750.robot.commands.GoToHighPos;
import org.usfirst.frc.team4750.robot.commands.GoToMidPos;
import org.usfirst.frc.team4750.robot.commands.RotateGrabberDown;
import org.usfirst.frc.team4750.robot.commands.SwitchGrabberMode;
import org.usfirst.frc.team4750.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.Command;

public class SquareGraph {

	private Node[][] map;
	private Point startPosition;
	private Point targetPosition;
	private Heap<Node> openNodes;
	private Set<Node> closedNodes;
	private boolean allowDiagonal = false;

	public SquareGraph(int mapDimension) {
		map = new Node[mapDimension][mapDimension];
		startPosition = new Point();
		targetPosition = new Point();
		openNodes = new Heap<Node>();
		closedNodes = new HashSet<Node>();
	}

	public Node getMapCell(Point coord) {
		return map[(int) coord.getX()][(int) coord.getY()];
	}

	public Node getNode(int x, int y) {
		return map[x][y];
	}

	public void setMapCell(Point coord, Node n) {
		map[(int) coord.getX()][(int) coord.getY()] = n;
	}

	public Point getStartPosition() {
		return startPosition;
	}

	public Point getTargetPosition() {
		return targetPosition;
	}

	public void setStartPosition(Point coord) {
		startPosition.setLocation(coord);
	}

	public void setTargetPosition(Point coord) {
		targetPosition.setLocation(coord);
	}

	public int getDimension() {
		return map.length;
	}

	public void addToOpenNodes(Node n) {
		n.setOpen();
		openNodes.add(n);
	}

	public Node popBestOpenNode() {
		return openNodes.remove();
	}

	public void addToClosedNodes(Node n) {
		n.setClosed();
		closedNodes.add(n);
	}

	public boolean isPassable(int x, int y) {
		return map[x][y].isNormal();
	}

	public boolean isInsideMap(Point p) {
		return ((p.getX() >= 0) && (p.getX() < getDimension()) && (p.getY() >= 0) && (p.getY() < getDimension()));
	}

	public Set<Node> getNeighbours(Node n) {
		Set<Node> neighbours = new HashSet<Node>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (!allowDiagonal) {
					if (!(Math.abs(i) == Math.abs(j)))
						if (isInsideMap(new Point(n.getX() + i, n.getY() + j))) {
							Node temp = getMapCell(new Point(n.getX() + i, n.getY() + j));
							if (!temp.isObstacle())
								neighbours.add(temp);
						}
				} else {
					if (!(i == 0 && j == 0))
						if (isInsideMap(new Point(n.getX() + i, n.getY() + j))) {
							Node temp = getMapCell(new Point(n.getX() + i, n.getY() + j));
							if (!temp.isObstacle())
								neighbours.add(temp);
						}
				}

			}
		}
		return neighbours;
	}

	double calculateDistance(Point from, Point to) {
		if (allowDiagonal) {
			return Math.max(Math.abs(from.getX() - to.getX()), Math.abs(from.getY() - to.getY()));
		} else {
			return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - from.getY());
		}
	}

	static double calculateDistance(Node from, Node to) {
		return Math.pow(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2), 0.5);
	}

	public ArrayList<Node> reconstructPath(Node target) {
		ArrayList<Node> path = new ArrayList<Node>();
		Node current = target;

		while (current.getParent() != null) {
			path.add(current.getParent());
			current = current.getParent();
		}
		Collections.reverse(path);
		path.add(target);

		// Path compressing code
		ArrayList<Node> remove = new ArrayList<Node>();
		for (int i = 1; i <= path.size() - 2; i++) {
			int initialX = (path.get(i).getX() - path.get(i - 1).getX());
			int initialY = (path.get(i).getY() - path.get(i - 1).getY());
			int finalX = (path.get(i + 1).getX() - path.get(i).getX());
			int finalY = (path.get(i + 1).getY() - path.get(i).getY());
			if (initialX == finalX && initialY == finalY) {
				Node toRemove = path.get(i);
				remove.add(toRemove);
			}
		}
		ArrayList<Node> compressedPath = new ArrayList<Node>();
		compressedPath.addAll(path);
		for (int i = 0; i < compressedPath.size(); i++) {
			for (int j = 0; j < remove.size(); j++) {
				if (compressedPath.get(i).equals(remove.get(j))) {
					compressedPath.remove(i);
				} else {
				}
			}
		}
		path = compressedPath;
		// End of path compressing code

		return path;
	}

	public void printPath(ArrayList<Node> path) {
		for (int i = 0; i < path.size(); i++) {
			Node node = path.get(i);
			System.out.println("node : (" + node.getX() + "," + node.getY() + ")");
		}
	}

	public void printDrivePath(ArrayList<Integer> drivePath) {
		for (int i = 0; i < drivePath.size(); i++) {
			Integer step = drivePath.get(i);
			System.out.println(step);
		}
	}

	public ArrayList<Node> executeAStar() {
		Node start = getMapCell(getStartPosition());
		Node target = getMapCell(getTargetPosition());
		addToOpenNodes(start);

		start.setCostFromStart(0);
		start.setTotalCost(start.getCostFromStart() + calculateDistance(start.getPosition(), target.getPosition()));
		while (!openNodes.isEmpty()) {
			Node current = popBestOpenNode();
			if (current.equals(target)) {
				return reconstructPath(target);
			}

			addToClosedNodes(current);
			Set<Node> neighbours = getNeighbours(current);
			for (Node neighbour : neighbours) {
				if (!neighbour.isClosed()) {
					double tentativeCost = current.getCostFromStart()
							+ calculateDistance(current.getPosition(), neighbour.getPosition());

					if ((!neighbour.isOpen()) || (tentativeCost < neighbour.getCostFromStart())) {
						neighbour.setParent(current);
						neighbour.setCostFromStart(tentativeCost);
						neighbour.setTotalCost(neighbour.getCostFromStart()
								+ calculateDistance(neighbour.getPosition(), start.getPosition()));
						if (!neighbour.isOpen())
							addToOpenNodes(neighbour);
					}
				}

			}
		}
		return null;
	}

	public void translatePath(ArrayList<Node> path) {
		for (int i = 0; i < path.size() - 1; i++) {
			if (i < path.size() - 2) {
				Node first1 = path.get(i);
				Node second1 = path.get(i + 1);
				System.out.println("Drive " + calculateDistance(first1, second1) + " feet");
				if (turnRight(path, i)) {
					System.out.println("Turn left");
				} else {
					System.out.println("Turn right");
				}
			} else {
				Node first2 = path.get(i);
				Node second2 = path.get(i + 1);
				System.out.println("Drive " + calculateDistance(first2, second2) + " feet");
			}
		}
	}

	public ArrayList<Command> makeDrivePath(ArrayList<Node> path) {
		ArrayList<Command> drivePath = new ArrayList<Command>();
		drivePath.add(new RotateGrabberDown());
		drivePath.add(new GoToMidPos());
		for (int i = 0; i < path.size() - 1; i++) {
			if (i < path.size() - 2) {
				Node first1 = path.get(i);
				Node second1 = path.get(i + 1);
				drivePath.add(new DriveToDistance((int) calculateDistance(first1, second1), false));
				if (turnRight(path, i)) {
					drivePath.add(new TurnToAngle(-90));
				} else {
					drivePath.add(new TurnToAngle(90));
				}
			} else {
				Node first2 = path.get(i);
				Node second2 = path.get(i + 1);
				drivePath.add(new DriveToDistance((int) calculateDistance(first2, second2), false));
			}
		}
		String goal = Robot.getGoal();
		if (goal == "fls" || goal == "frs" || goal == "ls" || goal == "rs" || goal == "bls" || goal == "brs") {
			drivePath.add(new DriveToSwitch());
			drivePath.add(new SwitchGrabberMode());
		} else {
			drivePath.add(drivePath.size() - 2, new GoToHighPos());
			drivePath.add(new SwitchGrabberMode());
		}
		return drivePath;
	}

	public int getPathOrientation(ArrayList<Node> path, int nodeNumber) {
		Node node1 = path.get(nodeNumber);
		Node node2 = path.get(nodeNumber + 1);
		int pathOrientation;
		if (node1.getX() == node2.getX()) {
			if (node1.getY() < node2.getY()) {
				pathOrientation = 90;
			} else {
				pathOrientation = 270;
			}
		} else {
			if (node1.getX() < node2.getX()) {
				pathOrientation = 0;
			} else {
				pathOrientation = 180;
			}
		}
		return pathOrientation;
	}

	public boolean turnRight(ArrayList<Node> path, int nodeNumber) {
		int orientation1 = getPathOrientation(path, nodeNumber);
		int orientation2 = getPathOrientation(path, nodeNumber + 1);
		if (90 == Math.abs(orientation1 - orientation2)) {
			if (orientation2 > orientation1) {
				return false;
			} else {
				return true;
			}
		} else {
			if (orientation2 > orientation1) {
				return true;
			} else {
				return false;
			}
		}
	}

}