package APIs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import centralObject.CentralObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbitWithPosition;
import circularOrbit.ConcreteCircularOrbitWithoutPosition;
import physicalObject.PhysicalObject;
import physicalObject.PhysicalObjectWithSpeed;
import position.AnglePosition;
import position.Position;
import track.Track;

/**
 * the helper class of circular orbit mainly to visualize it
 * 
 * @author Guo Ziyang
 */
public class CircularOrbitHelper {
	
	/**
	 * visualize the circular orbit
	 * 
	 * @param c the target circular orbit
	 */
	public static void visualize(CircularOrbit<? extends CentralObject, ? extends PhysicalObject> c) {
		new Frame(c);
	}
	
	/**
	 * the frame class
	 * 
	 * @author Guo Ziyang
	 */
	static class Frame extends JFrame{
		
		private static final long serialVersionUID = -7346803537828352995L;
		
		/**
		 * the width of the frame
		 */
		public static final int WIDTH = 800;
		/**
		 * the height of the frame
		 */
		public static final int HEIGHT = 600;
		
		private Panel panel;
		
		public Frame(CircularOrbit<? extends CentralObject, ? extends PhysicalObject> circularOrbit) {
			this.setTitle("Visualization");
			this.setSize(WIDTH, HEIGHT);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			panel = new Panel(circularOrbit);
			this.setContentPane(panel);
			this.setVisible(true);
			new Thread(panel).start();
		}
		
	}
	
	/**
	 * the panel class of the frame
	 * 
	 * @author Guo Ziyang
	 */
	static class Panel extends JPanel implements Runnable{
		
		private static final long serialVersionUID = 1648970893169287491L;
		
		/**
		 * the width of the panel
		 */
		public static final int WIDTH = 800;
		/**
		 * the height of the panel
		 */
		public static final int HEIGHT = 600;
		
		/**
		 * the default radius of the central object
		 */
		public static final Double CENTRAL_OBJECT_RADIUS = 10D;
		/**
		 * the default radius of the physical object
		 */
		public static final Double PHYSICAL_OBJECT_RADIUS = 5D;
		
		/**
		 * the central x of the panel
		 */
		public static final double CENTRAL_X = WIDTH/2;
		/**
		 * the central y of the panel
		 */
		public static final double CENTRAL_Y = HEIGHT/2;
		
		private double offset = 1;
		private int time = 0;
		private double defaultMaxSpeed = 360;
		
		private Map<PhysicalObject, Coordinate> physicalCoordinate = new HashMap<>();
		
		protected Logger logger = LoggerFactory.getLogger(this.getClass());
		
//		private Random random = new Random();
		
		private CircularOrbit<? extends CentralObject, ? extends PhysicalObject> circularOrbit;
		
		public Panel(CircularOrbit<? extends CentralObject, ? extends PhysicalObject> circularOrbit) {
			this.circularOrbit = circularOrbit;
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D)g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			drawCentralObject(g2);
			drawOrbits(g2, circularOrbit.getTracks());
			if(circularOrbit instanceof ConcreteCircularOrbitWithPosition) {
				drawObjectsWithAngle(g2, circularOrbit.getPositions());
			} else {
				drawObjectsWithoutAngle(g2, circularOrbit.getPositions());
			}
			drawRelations(g2);
			g2.dispose();
		}
		
		/**
		 * draw the relations between physical objects
		 * 
		 * @param g2 the Graphics2D object
		 */
		private void drawRelations(Graphics2D g2) {
			for(Entry<? extends PhysicalObject, ?> entry : circularOrbit.getRelations().entrySet()) {
				PhysicalObject object1 = entry.getKey();
				Coordinate coordinate1 = physicalCoordinate.get(object1);
				@SuppressWarnings("unchecked")
				Map<PhysicalObject, Double> relationMap = (Map<PhysicalObject, Double>) entry.getValue();
				for(Map.Entry<PhysicalObject, Double> entry2 : relationMap.entrySet()) {
					PhysicalObject object2 = entry2.getKey();
					Coordinate coordinate2 = physicalCoordinate.get(object2);
					g2.drawLine((int)coordinate1.getX(), (int)coordinate1.getY(), (int)coordinate2.getX(), (int)coordinate2.getY());
				}
			}
		}
		
		/**
		 * draw the default central object
		 * 
		 * @param g2 the Graphics2D object
		 */
		public void drawCentralObject(Graphics2D g2) {
//			g2.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
			g2.setColor(Color.BLACK);
			Ellipse2D.Double circle = new Ellipse2D.Double(CENTRAL_X - CENTRAL_OBJECT_RADIUS, CENTRAL_Y - CENTRAL_OBJECT_RADIUS, 2 * CENTRAL_OBJECT_RADIUS, 2 * CENTRAL_OBJECT_RADIUS);
			g2.fill(circle);
		}
		
		/**
		 * draw the physical objects without their own angles
		 * 
		 * @param g2 the Graphics2D object
		 * @param map the map of the Physical object and their position
		 */
		public void drawObjectsWithoutAngle(Graphics2D g2, Map<? extends PhysicalObject, Position> map) {
			Map<Track, List<PhysicalObject>> radiusObjectMap = new HashMap<>();
			for(Entry<? extends PhysicalObject, Position> entry : map.entrySet()) {
				if(!radiusObjectMap.containsKey(entry.getValue().getTrack())) {
					radiusObjectMap.put(entry.getValue().getTrack(), new ArrayList<>());
				}
				radiusObjectMap.get(entry.getValue().getTrack()).add(entry.getKey());
			}
			for(Map.Entry<Track, List<PhysicalObject>> entry : radiusObjectMap.entrySet()) {
				double singleAngle = 360 / entry.getValue().size();
				double radius = entry.getKey().getRadius() * offset;
				for(int i = 0; i < entry.getValue().size(); i ++) {
					drawPhysicalObject(g2, radius, singleAngle * i, defaultMaxSpeed, true, Color.BLACK, entry.getValue().get(i));
				}
			}
		}
		
		/**
		 * draw the physical objects with their own angles
		 * 
		 * @param g2 the Graphics2D object
		 * @param map the map of the Physical object and their position
		 */
		public void drawObjectsWithAngle(Graphics2D g2, Map<? extends PhysicalObject, Position> map) {
			for(Map.Entry<? extends PhysicalObject, Position> entry : map.entrySet()) {
				drawPhysicalObject(g2, entry.getValue().getTrack().getRadius() * offset, ((AnglePosition)entry.getValue()).getAngle(), ((PhysicalObjectWithSpeed)entry.getKey()).getSpeed(), ((PhysicalObjectWithSpeed)entry.getKey()).getClockwise(), Color.BLACK, entry.getKey());
			}
		}
		
		/**
		 * draw the orbits
		 * 
		 * @param g2 the Graphics2D object
		 * @param tracks the tracks to be drawn
		 */
		public void drawOrbits(Graphics2D g2, Set<Track> tracks) {
			double maxRadius = 0;
			for(Track track : tracks) {
				if(maxRadius < track.getRadius()) {
					maxRadius = track.getRadius();
				}
			}
			if(maxRadius != 0) {
				offset = (CENTRAL_Y - 50) / maxRadius;
			}
			for(Track track : tracks) {
//				drawOrbit(g2, track.getRadius() * offset, new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
				drawOrbit(g2, track.getRadius() * offset, Color.BLACK);
			}
		}
		
		/**
		 * draw single orbit
		 * 
		 * @param g2 the Graphics2D object
		 * @param r the radius of the orbit
		 * @param color the color of the orbit
		 */
		public void drawOrbit(Graphics2D g2, double r, Color color) {
			g2.setColor(color);
			g2.drawOval(((int)(CENTRAL_X - r)), ((int)(CENTRAL_Y - r)), ((int)(2 * r)), ((int)(2 * r)));
		}
		
		/**
		 * draww single physical object
		 * 
		 * @param g2 the Graphics2D object
		 * @param r the radius of the track
		 * @param angle the angle of the object on the track
		 * @param speed the speed of the physical object
		 * @param clockwise whether the object move clockwisely
		 * @param color the color of the object
		 * @param physicalObject the target physical object
		 */
		public void drawPhysicalObject(Graphics2D g2, double r, Double angle, Double speed, Boolean clockwise, Color color, PhysicalObject physicalObject) {
			g2.setColor(color);
			if(circularOrbit instanceof ConcreteCircularOrbitWithoutPosition) {
				if(clockwise) {
					angle = angle + time * speed / (24 * (r/offset) * (r/offset));
				} else {
					angle = angle - time * speed / (24 * (r/offset) * (r/offset));
				}
			} else {
				if(clockwise) {
					angle = angle + time * speed / 24;
				} else {
					angle = angle - time * speed / 24;
				}
			}
//			System.out.println(angle);
			double px = CENTRAL_X + r * Math.sin(angle * Math.PI / 180);
			double py = CENTRAL_Y - r * Math.cos(angle * Math.PI / 180);
			physicalCoordinate.put(physicalObject, new Coordinate(px, py));
			Ellipse2D.Double circle = new Ellipse2D.Double(px - PHYSICAL_OBJECT_RADIUS, py - PHYSICAL_OBJECT_RADIUS, 2 * PHYSICAL_OBJECT_RADIUS, 2 * PHYSICAL_OBJECT_RADIUS);
			g2.fill(circle);
		}

		@Override
		public void run() {
			while(true) {
				try {
					time ++;
					Thread.sleep(41);
					repaint();
				} catch (Exception e) {
					logger.error("Thread error", e);
				}
			}
		}
	}
}

/**
 * the coordinate of a point
 * 
 * @author Guo Ziyang
 */
class Coordinate {
	double x;
	double y;
	
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * get the x of the coordinate
	 * 
	 * @return the x of the coordinate
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * get the y of the coordinate
	 * 
	 * @return the y of the coordinate
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * set the x of the coordinate
	 * 
	 * @param x the x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * set the y of the coordinate
	 * 
	 * @param y the y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
}
