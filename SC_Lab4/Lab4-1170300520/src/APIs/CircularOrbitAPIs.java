package APIs;

import centralObject.CentralObject;
import circularOrbit.*;
import physicalObject.PhysicalObject;
import physicalObject.PhysicalObjectWithSpeed;
import position.Position;
import track.Track;

import java.util.*;
import java.util.Map.Entry;

/**
 * the tool class for circular orbit
 *
 * @param <L> the type of the central object
 * @param <E> the type of the physical object
 * @author Guo Ziyang
 */
public class CircularOrbitAPIs<L, E> {

	/**
	 * calculate the entropy of a circular orbit
	 *
	 * @param circularOrbit the target circular orbit
	 * @return the entropy of the target circular orbit
	 */
	public double getObjectDistributionEntropy(CircularOrbit<L, E> circularOrbit) {
		Map<E, Position> positions = circularOrbit.getPositions();
		Map<Track, Set<E>> trackMap = new HashMap<>();
		for (Map.Entry<E, Position> entry : positions.entrySet()) {
			Track track = entry.getValue().getTrack();
			E object = entry.getKey();
			if (!trackMap.containsKey(track)) {
				trackMap.put(track, new HashSet<>());
			}
			trackMap.get(track).add(object);
		}
		int total = 0;
		for (Entry<Track, Set<E>> entry : trackMap.entrySet()) {
			total += entry.getValue().size();
		}
		double entropy = 0;
		for (Entry<Track, Set<E>> entry : trackMap.entrySet()) {
			entropy += (entry.getValue().size() / (double) total)
					* (Math.log(entry.getValue().size() / (double) total));
		}
		return -entropy;
	}

	/**
	 * calculate the logical distance of two physical object in a circular orbit
	 *
	 * @param c  the target circular orbit
	 * @param e1 one physical object
	 * @param e2 the other physical object
	 * @return the logical distance
	 */
	@SuppressWarnings("unchecked")
	public int getLogicalDistance(CircularOrbit<? extends CentralObject, ? extends PhysicalObject> c, E e1, E e2) {
		Map<? extends PhysicalObject, ?> relations = c.getRelations();
		int dis = 1;
		int num = 0, first = 0, last, right;
		int length = c.getPhysicalObjects().size();
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
		last = num;
		right = num;
		dis++;
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

	/**
	 * calculate the physical distance of two physical object in a circular orbit
	 *
	 * @param c  the target circular orbit
	 * @param e1 one physical object
	 * @param e2 the other physical object
	 * @return the physical distance
	 */
	public Double getPhysicalDistance(CircularOrbit<L, E> c, E e1, E e2) {
		if (c instanceof OrbitWithoutPosition) {
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

	/**
	 * get the difference of two circular orbits
	 *
	 * @param c1 one circular orbit
	 * @param c2 the other circular orbit
	 * @return the difference object of two circular orbits
	 */
	public Difference getDifference(CircularOrbit<? extends CentralObject, ? extends PhysicalObject> c1,
			CircularOrbit<? extends CentralObject, ? extends PhysicalObject> c2) {
		int trackNumber = c1.getTracks().size() - c2.getTracks().size();
		if (c1 instanceof AtomStructure && c2 instanceof AtomStructure) {
			Difference difference = new Difference(trackNumber);
			Map<Double, Integer> physicalObjectNumberDifference = difference.getPhysicalObjectNumberDifference();
			for (int i = 1; i <= ((trackNumber < 0) ? c2.getTracks().size() : c1.getTracks().size()); i++) {
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
			Map<Double, Integer> physicalObjectNumberDifference = difference.getPhysicalObjectNumberDifference();
			Map<Double, List<Set<PhysicalObject>>> objectDifference = difference.getPhysicalObjectDifference();
			for (int i = 1; i <= ((trackNumber < 0) ? c2.getTracks().size() : c1.getTracks().size()); i++) {
				Track track1 = c1.getTrack((double) i);
				Track track2 = c2.getTrack((double) i);
				List<Set<PhysicalObject>> list = new ArrayList<>();
				list.add(new HashSet<>());
				list.add(new HashSet<>());
				objectDifference.put((double) i, list);
				if (track1 == null) {
					physicalObjectNumberDifference.put((double) i, -c2.getPhysicalObjects(track2).size());
					list.get(1).addAll(c2.getPhysicalObjects(track2));
				} else if (track2 == null) {
					physicalObjectNumberDifference.put((double) i, c1.getPhysicalObjects(track1).size());
					list.get(0).addAll(c1.getPhysicalObjects(track1));
				} else {
					physicalObjectNumberDifference.put((double) i,
							c1.getPhysicalObjects(track1).size() - c2.getPhysicalObjects(track2).size());
					for (PhysicalObject physicalObject : c1.getPhysicalObjects(track1)) {
						if (!c2.getPhysicalObjects(track2).contains(physicalObject)) {
							list.get(0).add(physicalObject);
						}
					}
					for (PhysicalObject physicalObject : c2.getPhysicalObjects(track2)) {
						if (!c1.getPhysicalObjects(track1).contains(physicalObject)) {
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
