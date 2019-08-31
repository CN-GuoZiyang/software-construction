package apis;

import centralobject.AbstractCentralObject;
import circularorbit.AbstractOrbit;
import circularorbit.AbstractOrbitWithPosition;
import circularorbit.CircularOrbit;

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
import physicalobject.AbstractPhysicalObject;
import physicalobject.PhysicalObjectWithSpeed;
import position.AbstractPosition;
import position.AnglePosition;
import track.Track;

/**.
 * the helper class of circular orbit mainly to visualize it
 *
 * @author Guo Ziyang
 */
public class CircularOrbitHelper {

  private static Logger logger = LoggerFactory.getLogger(CircularOrbitHelper.class);

  /**.
   * visualize the circular orbit
   *
   * @param c the target circular orbit
   */
  public static void visualize(
          CircularOrbit<? extends AbstractCentralObject, ? extends AbstractPhysicalObject> c) {
    logger.info("start visualize circular orbit {}", c);
    new Frame(c);
  }

  /**.
   * the frame class
   *
   * @author Guo Ziyang
   */
  static class Frame extends JFrame {

    private static final long serialVersionUID = -7346803537828352995L;

    /**.
     * the width of the frame
     */
    public static final int WIDTH = 800;
    /**.
     * the height of the frame
     */
    public static final int HEIGHT = 600;

    public Frame(
            CircularOrbit<? extends AbstractCentralObject, ? extends AbstractPhysicalObject>
                    circularOrbit) {
      this.setTitle("Visualization");
      this.setSize(WIDTH, HEIGHT);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      Panel panel = new Panel(circularOrbit);
      this.setContentPane(panel);
      this.setVisible(true);
      GuiThreadPool.getThreadPool().execute(panel);
    }

  }

  /**.
   * the panel class of the frame
   *
   * @author Guo Ziyang
   */
  static class Panel extends JPanel implements Runnable {

    private static final long serialVersionUID = 1648970893169287491L;

    /**.
     * the width of the panel
     */
    public static final int WIDTH = 800;

    /**.
     * the height of the panel
     */
    public static final int HEIGHT = 600;

    /**.
     * the default radius of the central object
     */
    public static final Double CENTRAL_OBJECT_RADIUS = 10D;

    /**.
     * the default radius of the physical object
     */
    public static final Double PHYSICAL_OBJECT_RADIUS = 5D;

    /**.
     * the central horizontal of the panel
     */
    public static final double CENTRAL_X = WIDTH / 2;

    /**.
     * the central vertical of the panel
     */
    public static final double CENTRAL_Y = HEIGHT / 2;

    public static final double DEFAULT_MAX_SPEED = 360;

    private double offset = 1;
    private long time = 0;

    private Map<AbstractPhysicalObject, Coordinate> physicalCoordinate = new HashMap<>();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private
        CircularOrbit<? extends AbstractCentralObject, ? extends AbstractPhysicalObject>
            circularOrbit;

    public Panel(
            CircularOrbit<? extends AbstractCentralObject, ? extends AbstractPhysicalObject>
                    circularOrbit) {
      this.circularOrbit = circularOrbit;
    }

    @Override
    public void paint(Graphics g) {
      super.paint(g);
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      drawCentralObject(g2);
      drawOrbits(g2, circularOrbit.getTracks());
      if (circularOrbit instanceof AbstractOrbitWithPosition) {
        drawObjectsWithAngle(g2);
      } else {
        drawObjectsWithoutAngle(g2);
      }
      drawRelations(g2);
      g2.dispose();
    }

    /**.
     * draw the relations between physical objects
     *
     * @param g2 the Graphics2D object
     */
    private void drawRelations(Graphics2D g2) {
      for (Entry<? extends AbstractPhysicalObject, ?> entry :
              circularOrbit.getRelations().entrySet()) {
        AbstractPhysicalObject object1 = entry.getKey();
        Coordinate coordinate1 = physicalCoordinate.get(object1);
        @SuppressWarnings("unchecked")
        Map<AbstractPhysicalObject, Double> relationMap
                = (Map<AbstractPhysicalObject, Double>) entry.getValue();
        for (Map.Entry<AbstractPhysicalObject, Double> entry2 : relationMap.entrySet()) {
          AbstractPhysicalObject object2 = entry2.getKey();
          Coordinate coordinate2 = physicalCoordinate.get(object2);
          g2.drawLine((int) coordinate1.getHorizontal(),
                  (int) coordinate1.getVertical(),
                  (int) coordinate2.getHorizontal(),
                  (int) coordinate2.getVertical());
        }
      }
    }

    /**.
     * draw the default central object
     *
     * @param g2 the Graphics2D object
     */
    private void drawCentralObject(Graphics2D g2) {
      g2.setColor(Color.BLACK);
      Ellipse2D.Double circle = new Ellipse2D.Double(CENTRAL_X - CENTRAL_OBJECT_RADIUS,
              CENTRAL_Y - CENTRAL_OBJECT_RADIUS,
              2 * CENTRAL_OBJECT_RADIUS, 2 * CENTRAL_OBJECT_RADIUS);
      g2.fill(circle);
    }

    /**.
     * draw the physical objects without their own angles
     *
     * @param g2  the Graphics2D object
     */
    private void drawObjectsWithoutAngle(
            Graphics2D g2) {
      Map radiusObjectMap = circularOrbit.getObjectOnTracks();
      for (Map.Entry<Double, Set<AbstractPhysicalObject>> entry : ((Map<Double, Set<AbstractPhysicalObject>>)radiusObjectMap).entrySet()) {
        double singleAngle = 360.0 / entry.getValue().size();
        double radius = entry.getKey() * offset;
        int i = 0;
        for (AbstractPhysicalObject object : entry.getValue()) {
          drawPhysicalObject(g2, radius, singleAngle * i, DEFAULT_MAX_SPEED, true, Color.BLACK,
                  object);
          i++;
        }
      }
    }

    /**.
     * draw the physical objects with their own angles
     *
     * @param g2  the Graphics2D object
     */
    private void drawObjectsWithAngle(Graphics2D g2) {
      Map<? extends AbstractPhysicalObject, AbstractPosition> map = circularOrbit.getPositions();
      for (Map.Entry<? extends AbstractPhysicalObject, AbstractPosition> entry : map.entrySet()) {
        drawPhysicalObject(g2, entry.getValue().getTrack().getRadius() * offset,
                ((AnglePosition) entry.getValue()).getAngle(),
                ((PhysicalObjectWithSpeed) entry.getKey()).getSpeed(),
                ((PhysicalObjectWithSpeed) entry.getKey()).getClockwise(),
                Color.BLACK, entry.getKey());
      }
    }

    /**.
     * draw the orbits
     *
     * @param g2     the Graphics2D object
     * @param tracks the tracks to be drawn
     */
    private void drawOrbits(Graphics2D g2, Set<Track> tracks) {
      double maxRadius = 0;
      for (Track track : tracks) {
        if (maxRadius < track.getRadius()) {
          maxRadius = track.getRadius();
        }
      }
      if (maxRadius != 0) {
        offset = (CENTRAL_Y - 50) / maxRadius;
      }
      for (Track track : tracks) {
        drawOrbit(g2, track.getRadius() * offset, Color.BLACK);
      }
    }

    /**.
     * draw single orbit
     *
     * @param g2    the Graphics2D object
     * @param r     the radius of the orbit
     * @param color the color of the orbit
     */
    private void drawOrbit(Graphics2D g2, double r, Color color) {
      g2.setColor(color);
      g2.drawOval(((int) (CENTRAL_X - r)), ((int) (CENTRAL_Y - r)),
          ((int) (2 * r)), ((int) (2 * r)));
    }

    /**.
     * draw single physical object
     *
     * @param g2             the Graphics2D object
     * @param r              the radius of the track
     * @param angle          the angle of the object on the track
     * @param speed          the speed of the physical object
     * @param clockwise      whether the object move clockwisely
     * @param color          the color of the object
     * @param physicalObject the target physical object
     */
    private void drawPhysicalObject(Graphics2D g2, double r, Double angle,
                                    Double speed, Boolean clockwise,
                                    Color color, AbstractPhysicalObject physicalObject) {
      g2.setColor(color);
      if (circularOrbit instanceof AbstractOrbit) {
        if (clockwise) {
          angle = angle + time * speed / (60 * (r / offset) * (r / offset));
        } else {
          angle = angle - time * speed / (60 * (r / offset) * (r / offset));
        }
      } else {
        if (clockwise) {
          angle = angle + time * speed / 60;
        } else {
          angle = angle - time * speed / 60;
        }
      }
      angle = angle % 360;
      double px = CENTRAL_X + r * Math.sin(angle * Math.PI / 180);
      double py = CENTRAL_Y - r * Math.cos(angle * Math.PI / 180);
      physicalCoordinate.put(physicalObject, new Coordinate(px, py));
      Ellipse2D.Double circle = new Ellipse2D.Double(px - PHYSICAL_OBJECT_RADIUS,
              py - PHYSICAL_OBJECT_RADIUS,
              2 * PHYSICAL_OBJECT_RADIUS, 2 * PHYSICAL_OBJECT_RADIUS);
      g2.fill(circle);
    }

    @Override
    public void run() {
      while (true) {
        try {
          time++;
          Thread.sleep(16);
          repaint();
        } catch (Exception e) {
          logger.error("Thread error", e);
        }
      }
    }
  }
}