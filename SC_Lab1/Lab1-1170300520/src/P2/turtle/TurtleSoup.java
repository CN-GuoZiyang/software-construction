/* Copyright (c) 1007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle     the turtle context
	 * @param sideLength length of each side
	 */
	public static void drawSquare(Turtle turtle, int sideLength) {
		for (int i = 0; i <= 4; i++) {
			turtle.forward(sideLength);
			turtle.turn(90);
		}
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon; you
	 * should derive it and use it here.
	 * 
	 * @param sides number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 */
	public static double calculateRegularPolygonAngle(int sides) {
		return (sides - 2d) * 180d / sides;
	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior angles.
	 * 
	 * @param angle size of interior angles in degrees, where 0 < angle < 180
	 * @return the integer number of sides
	 */
	public static int calculatePolygonSidesFromAngle(double angle) {
		return (int) (360 / (180 - angle) + 0.5);
	}

	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
	 * draw.
	 * 
	 * @param turtle     the turtle context
	 * @param sides      number of sides of the polygon to draw
	 * @param sideLength length of each side
	 */
	public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
		for (int i = 0; i < sides; i++) {
			turtle.forward(sideLength);
			turtle.turn(180d - calculateRegularPolygonAngle(sides));
		}
	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the Bearing towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle in
	 * the direction of the target point (targetX,targetY), given that the turtle is
	 * already at the point (currentX,currentY) and is facing at angle
	 * currentBearing. The angle must be expressed in degrees, where 0 <= angle <
	 * 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
	 * 
	 * @param currentBearing current direction as clockwise from north
	 * @param currentX       current location x-coordinate
	 * @param currentY       current location y-coordinate
	 * @param targetX        target point x-coordinate
	 * @param targetY        target point y-coordinate
	 * @return adjustment to Bearing (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360
	 */
	public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX,
			int targetY) {
		if (currentX == targetX) {
			if (currentY == targetY) {
				return 0;
			} else if (currentY < targetY) {
				if (currentBearing == 0) {
					return 0;
				}
				return 360 - currentBearing;
			} else {
				return ((180 - currentBearing) + 360) % 360;
			}
		} else if (currentX < targetX) {
			if (currentY == targetY) {
				return ((90 - currentBearing) + 360) % 360;
			} else if (currentY < targetY) {
				return ((Math.toDegrees(Math.atan2(targetX - currentX, targetY - currentY)) - currentBearing) + 360)
						% 360;
			} else {
				return ((180 - Math.toDegrees(Math.atan2(targetX - currentX, currentY - targetY)) - currentBearing)
						+ 360) % 360;
			}
		} else {
			if (currentY == targetY) {
				return ((270 - currentBearing) + 360) % 360;
			} else if (currentY < targetY) {
				return ((360 - Math.toDegrees(Math.atan2(currentX - targetX, targetY - currentY)) - currentBearing)
						+ 360) % 360;
			} else {
				return ((180 + Math.toDegrees(Math.atan2(currentX - targetX, currentY - targetY)) - currentBearing)
						+ 360) % 360;
			}
		}
	}
	
	/**
	 * The double version of calculateBearingToPoint() method
	 * 
	 * @param currentBearing current direction as clockwise from north
	 * @param currentX       current location x-coordinate
	 * @param currentY       current location y-coordinate
	 * @param targetX        target point x-coordinate
	 * @param targetY        target point y-coordinate
	 * @return adjustment to Bearing (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360
	 */
	public static double calculateBearingToPointInDouble(double currentBearing, double currentX, double currentY, double targetX,
			double targetY) {
		if (currentX == targetX) {
			if (currentY == targetY) {
				return 0;
			} else if (currentY < targetY) {
				if (currentBearing == 0) {
					return 0;
				}
				return 360 - currentBearing;
			} else {
				return ((180 - currentBearing) + 360) % 360;
			}
		} else if (currentX < targetX) {
			if (currentY == targetY) {
				return ((90 - currentBearing) + 360) % 360;
			} else if (currentY < targetY) {
				return ((Math.toDegrees(Math.atan2(targetX - currentX, targetY - currentY)) - currentBearing) + 360)
						% 360;
			} else {
				return ((180 - Math.toDegrees(Math.atan2(targetX - currentX, currentY - targetY)) - currentBearing)
						+ 360) % 360;
			}
		} else {
			if (currentY == targetY) {
				return ((270 - currentBearing) + 360) % 360;
			} else if (currentY < targetY) {
				return ((360 - Math.toDegrees(Math.atan2(currentX - targetX, targetY - currentY)) - currentBearing)
						+ 360) % 360;
			} else {
				return ((180 + Math.toDegrees(Math.atan2(currentX - targetX, currentY - targetY)) - currentBearing)
						+ 360) % 360;
			}
		}
	}

	/**
	 * Given a sequence of points, calculate the Bearing adjustments needed to get
	 * from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e. 0
	 * degrees). For each subsequent point, assumes that the turtle is still facing
	 * in the direction it was facing when it moved to the previous point. You
	 * should use calculateBearingToPoint() to implement this function.
	 * 
	 * @param xCoords list of x-coordinates (must be same length as yCoords)
	 * @param yCoords list of y-coordinates (must be same length as xCoords)
	 * @return list of Bearing adjustments between points, of size 0 if (# of
	 *         points) == 0, otherwise of size (# of points) - 1
	 */
	public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
		List<Double> results = new ArrayList<>();
		double currentBearing = 0;
		for (int i = 0; i < xCoords.size() - 1; i++) {
			if (i != 0) {
				currentBearing = currentBearing + results.get(i - 1);
				if(currentBearing >= 360) {
					currentBearing = 360 - currentBearing;
				}
			}
			results.add(calculateBearingToPoint(currentBearing, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1),
					yCoords.get(i + 1)));
		}
		return results;
	}

	/**
	 * Given a set of points, compute the convex hull, the smallest convex set that
	 * contains all the points in a set of input points. The gift-wrapping algorithm
	 * is one simple approach to this problem, and there are other algorithms too.
	 * 
	 * @param points a set of points with xCoords and yCoords. It might be empty,
	 *               contain only 1 point, two points or more.
	 * @return minimal subset of the input points that form the vertices of the
	 *         perimeter of the convex hull
	 */
	public static Set<Point> convexHull(Set<Point> points) {
		if (points.size() <= 3) {
			return points;
		}
		Point currentPoint;
		List<Point> startPoints = new ArrayList<>();
		double leastX = points.iterator().next().x();
		for(Point point : points) {
			if(point.x() < leastX) {
				leastX = point.x();
				startPoints.clear();
				startPoints.add(point);
			} else if(point.x() == leastX) {
				startPoints.add(point);
			}
		}
		currentPoint = startPoints.get(0);
		if(startPoints.size() != 1) {
			for(Point point : startPoints) {
				if(point.y() > currentPoint.y()) {
					currentPoint = point;
				}
			}
		}
		Set<Point> resultSet = new HashSet<>();
		resultSet.add(currentPoint);
		
		double currentAngle = 0;
		double tempLeastAngle = 0;
		List<Point> tempLeastPoints = new ArrayList<>();
		List<Point> pointsList = new ArrayList<>(points);
		Point tempLeastPoint = null;
		Point startPoint = currentPoint;
		while(true) {
			tempLeastPoints.clear();
			tempLeastAngle = 360;
			for(int i = 0; i < pointsList.size(); i ++) {
				if(pointsList.get(i) == currentPoint) continue;
				double turnAngle = calculateBearingToPointInDouble(currentAngle, currentPoint.x(), currentPoint.y(), pointsList.get(i).x(), pointsList.get(i).y());
				if(turnAngle < tempLeastAngle) {
					tempLeastPoints.clear();
					tempLeastPoints.add(pointsList.get(i));
					tempLeastAngle = turnAngle;
				} else if(turnAngle == tempLeastAngle) {
					tempLeastPoints.add(pointsList.get(i));
				}
			}
			if(tempLeastPoints.size() == 1) {
				tempLeastPoint = tempLeastPoints.get(0);
			} else {
				tempLeastPoint = findFarthestPoint(currentPoint, tempLeastPoints);
			}
			if(tempLeastPoint == startPoint) {
				return resultSet;
			} else {
				resultSet.add(tempLeastPoint);
				currentPoint = tempLeastPoint;
				currentAngle = currentAngle + tempLeastAngle;
			}
		}
	}
	
	private static Point findFarthestPoint(Point currentPoint, List<Point> points) {
		Point resPoint = points.get(0);
		double mostLength = calculateLength(currentPoint, resPoint);
		for(int i = 0; i < points.size(); i ++) {
			double length = calculateLength(currentPoint, points.get(i));
			if(length > mostLength) {
				mostLength = length;
				resPoint = points.get(i);
			}
		}
		return resPoint;
	}
	
	private static double calculateLength(Point point1, Point point2) {
		if(point1.x() == point2.x()) return Math.abs(point1.y() - point2.y());
		if(point1.y() == point2.y()) return Math.abs(point1.x() - point2.x());
		return Math.sqrt(Math.pow(point1.x() - point2.x(), 2) + Math.pow(point1.y() - point2.y(), 2));
	}


	/**
	 * 判断数组中元素是否全部大于等于0或者小于等于0
	 * 
	 * @param array 待判断数组
	 * @return 判断结果
	 */
	public static boolean judgeArray(double[] array) {
		boolean judge = false;
		int len1 = 0, len2 = 0;

		for (int i = 0; i < array.length; i++) {
			if (array[i] >= 0)
				len1++;
		}
		for (int j = 0; j < array.length; j++) {
			if (array[j] <= 0)
				len2++;
		}

		if (len1 == array.length || len2 == array.length)
			judge = true;
		return judge;
	}

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can be
	 * as little or as much as you want.
	 * 
	 * @param turtle the turtle context
	 */
	public static void drawPersonalArt(Turtle turtle) {
		for(int i = 0; i < 120; i ++) {
			turtle.forward(100);
			turtle.turn(61);
			turtle.forward(100);
			turtle.turn(61);
			turtle.forward(100);
			turtle.turn(61);
			turtle.forward(100);
			turtle.turn(61);
			turtle.forward(100);
			turtle.turn(61);
			turtle.forward(100);
			turtle.turn(61);
			turtle.turn(11.1111);
		}
	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 * 
	 * @param args unused
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

		// drawSquare(turtle, 40);
		drawPersonalArt(turtle);

		// draw the window
		turtle.draw();
	}

}
