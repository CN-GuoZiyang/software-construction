package apis;

import centralobject.AbstractCentralObject;
import circularorbit.AbstractOrbit;
import circularorbit.AtomStructure;
import circularorbit.CircularOrbit;
import circularorbit.PersonalAppEcosystem;
import circularorbit.StellarSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import physicalobject.AbstractPhysicalObject;
import physicalobject.PhysicalObjectWithSpeed;
import position.AbstractPosition;
import track.Track;

/**.
 * the tool class for circular orbit
 *
 * @param <L> the type of the central object
 * @param <E> the type of the physical object
 * @author Guo Ziyang
 */
public class CircularOrbitApis<L, E> {

  /**.
   * calculate the entropy of a circular orbit
   *
   * @param circularOrbit the target circular orbit
   * @return the entropy of the target circular orbit
   */
  public double getObjectDistributionEntropy(CircularOrbit<L, E> circularOrbit) {
    Map<Double, Set<E>> trackMap = circularOrbit.getObjectOnTracks();
    int total = 0;
    for (Entry<Double, Set<E>> entry : trackMap.entrySet()) {
      total += entry.getValue().size();
    }
    double entropy = 0;
    for (Entry<Double, Set<E>> entry : trackMap.entrySet()) {
      entropy += (entry.getValue().size() / (double) total)
              * (Math.log(entry.getValue().size() / (double) total));
    }
    return -entropy;
  }

  /**.
   * calculate the logical distance of two physical object in a circular orbit
   *
   * @param c  the target circular orbit
   * @param e1 one physical object
   * @param e2 the other physical object
   * @return the logical distance
   */
  @SuppressWarnings("unchecked")
  public int getLogicalDistance(
          CircularOrbit<? extends AbstractCentralObject, ? extends AbstractPhysicalObject> c,
          E e1, E e2) {
    Map<? extends AbstractPhysicalObject, ?> relations = c.getRelations();
    int dis = 1;
    int num = 0;
    List<E> queue = new ArrayList<>();
    Set<E> isVisited = new HashSet<>();
    Map<E, Double> map = (Map<E, Double>) relations.get(e1);
    isVisited.add(e1);
    if (map != null && map.size() > 0) {
      for (Entry<E, Double> entry : map.entrySet()) {
        if (entry.getKey().equals(e2)) {
          return dis;
        } else {
          queue.add(entry.getKey());
          num++;
          isVisited.add(entry.getKey());
        }
      }
    } else {
      return Integer.MAX_VALUE;
    }
    int last = num;
    int right = num;
    dis++;
    int first = 0;
    int length = c.getPhysicalObjects().size();
    while (first < length && !queue.isEmpty()) {
      E temp = queue.get(0);
      queue.remove(0);
      first++;
      Map<E, Double> tempMap = (Map<E, Double>) relations.get(temp);
      if (!tempMap.isEmpty()) {
        if (tempMap.containsKey(e2)) {
          return dis;
        }
        for (Entry<E, Double> entry : tempMap.entrySet()) {
          if (!isVisited.contains(entry.getKey())) {
            queue.add(entry.getKey());
            num++;
            right++;
          }
        }
      }
      if (first == last) {
        dis += 1.0;
        last = right;
      }
    }
    return Integer.MAX_VALUE;
  }

  /**.
   * calculate the physical distance of two physical object in a circular orbit
   *
   * @param c  the target circular orbit
   * @param e1 one physical object
   * @param e2 the other physical object
   * @return the physical distance
   */
  public Double getPhysicalDistance(CircularOrbit<L, E> c, E e1, E e2) {
    if (c instanceof AbstractOrbit) {
      Track track1 = c.getTrack(e1);
      Track track2 = c.getTrack(e2);
      return Math.abs(track1.getRadius() - track2.getRadius());
    } else {
      PhysicalObjectWithSpeed ee1 = (PhysicalObjectWithSpeed) e1;
      PhysicalObjectWithSpeed ee2 = (PhysicalObjectWithSpeed) e2;
      double x1 = ee1.getTrackRadius() * Math.cos(ee1.getStartAngle() * Math.PI / 180);
      double y1 = ee1.getTrackRadius() * Math.sin(ee1.getStartAngle() * Math.PI / 180);
      double x2 = ee2.getTrackRadius() * Math.cos(ee2.getStartAngle() * Math.PI / 180);
      double y2 = ee2.getTrackRadius() * Math.sin(ee2.getStartAngle() * Math.PI / 180);
      return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
  }

  /**.
   * get the difference of two circular orbits
   *
   * @param c1 one circular orbit
   * @param c2 the other circular orbit
   * @return the difference object of two circular orbits
   */
  public Difference getDifference(
          CircularOrbit<? extends AbstractCentralObject, ? extends AbstractPhysicalObject> c1,
          CircularOrbit<? extends AbstractCentralObject, ? extends AbstractPhysicalObject> c2) {
    int trackNumber = c1.getTracks().size() - c2.getTracks().size();
    if (c1 instanceof AtomStructure && c2 instanceof AtomStructure) {
      Difference difference = new Difference(trackNumber);
      Map<Double, Integer> physicalObjectNumberDifference
              = difference.getPhysicalObjectNumberDifference();
      for (int i = 1;
           i <= ((trackNumber < 0) ? c2.getTracks().size() : c1.getTracks().size());
           i++) {
        Track track1 = c1.getTrack((double) i);
        Track track2 = c2.getTrack((double) i);
        if (track1 == null) {
          physicalObjectNumberDifference.put((double) i, -c2.getPhysicalObjects(track2).size());
        } else if (track2 == null) {
          physicalObjectNumberDifference.put((double) i, c1.getPhysicalObjects(track1).size());
        } else {
          physicalObjectNumberDifference.put((double) i,
                  c1.getPhysicalObjects(track1).size() - c2.getPhysicalObjects(track2).size());
        }
      }
      return difference;
    } else if ((c1 instanceof PersonalAppEcosystem && c2 instanceof PersonalAppEcosystem)
            || (c1 instanceof StellarSystem && c2 instanceof StellarSystem)) {
      DifferenceWithObject difference = new DifferenceWithObject(trackNumber);
      Map<Double, Integer> physicalObjectNumberDifference
              = difference.getPhysicalObjectNumberDifference();
      Map<Double, List<Set<AbstractPhysicalObject>>> objectDifference
              = difference.getPhysicalObjectDifference();
      Map c1Map = c1.getObjectOnTracks();
      Map c2Map = c2.getObjectOnTracks();
      for (int i = 1;
           i <= ((trackNumber < 0) ? c2.getTracks().size() : c1.getTracks().size());
           i++) {
        List<Set<AbstractPhysicalObject>> list = new ArrayList<>();
        list.add(new HashSet<>());
        list.add(new HashSet<>());
        objectDifference.put((double) i, list);
        Track track1 = c1.getTrack((double) i);
        Track track2 = c2.getTrack((double) i);
        if (track1 == null) {
          physicalObjectNumberDifference.put((double) i, -((Set)c2Map.get((double)i)).size());
          list.get(1).addAll((Set)c2Map.get((double)i));
        } else if (track2 == null) {
          physicalObjectNumberDifference.put((double) i, ((Set)c1Map.get((double)i)).size());
          list.get(0).addAll((Set)c1Map.get((double)i));
        } else {
          physicalObjectNumberDifference.put((double) i,
                  ((Set)c1Map.get((double)i)).size() - ((Set)c2Map.get((double)i)).size());
          for (AbstractPhysicalObject physicalObject : ((Set<AbstractPhysicalObject>)c1Map.get((double)i))) {
            if (!((Set)c2Map.get((double)i)).contains(physicalObject)) {
              list.get(0).add(physicalObject);
            }
          }
          for (AbstractPhysicalObject physicalObject : ((Set<AbstractPhysicalObject>)c2Map.get((double)i))) {
            if (!((Set)c1Map.get((double)i)).contains(physicalObject)) {
              list.get(1).add(physicalObject);
            }
          }
        }
      }
      return difference;
    } else {
      return null;
    }
  }

}
