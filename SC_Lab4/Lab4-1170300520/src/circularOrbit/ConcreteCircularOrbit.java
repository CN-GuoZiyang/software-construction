package circularOrbit;

import factory.TrackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import position.AnglePosition;
import position.Position;
import track.Track;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * the abstraction of the concrete circular orbit with some implements of the
 * common methods between position-sensitive and non-position-sensitive orbit
 *
 * @param <L> the type of central object
 * @param <E> the type of the physical object around the central object
 * @author Guo Ziyang
 */
public abstract class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {

	protected L centralObject;
	protected Set<Track> tracks;
	protected Map<E, Position> positions;
	protected Map<E, Map<E, Double>> physicalRelations;
	protected Map<E, Double> centralRelations;

	protected TrackFactory trackFactory;

	private Logger logger = LoggerFactory.getLogger(ConcreteCircularOrbit.class);

	public ConcreteCircularOrbit() {
		tracks = new HashSet<>();
		positions = new HashMap<>();
		physicalRelations = new HashMap<>();
		centralRelations = new HashMap<>();
		trackFactory = new TrackFactory();
	}

	public void checkRep() {
		assert tracks != null;
		assert positions != null;
		assert physicalRelations != null;
		assert centralRelations != null;
	}

	@Override
	public void addTrack(Double radius) {
		checkRep();
		Track track = getTrack(radius);
		if (track != null) {
			logger.error("Trying to add a duplicate track!");
			return;
		}
		tracks.add(trackFactory.build(radius));
		logger.info("add a track with radius {}", radius);
	}

	@Override
	public void removeTrack(Double radius) {
		checkRep();
		Track track = getTrack(radius);
		if (track == null) {
			System.out.println("半径为" + radius + "的轨道不存在！");
			logger.error("Trying to remove a non-existent track!");
			return;
		}
		tracks.remove(track);
		List<E> physicalObjectToRemove = new ArrayList<>();
		for (Entry<E, Position> entry : positions.entrySet()) {
			if (entry.getValue().getTrack().getRadius().equals(radius)) {
				physicalObjectToRemove.add(entry.getKey());
			}
		}
		for (E physicalObject : physicalObjectToRemove) {
			removePhysicalObject(physicalObject);
		}
		logger.info("remove the track {}", track);
	}

	@Override
	public void addCentralObject(L object) {
		checkRep();
		if (centralObject != null) {
			logger.error("Trying to add central objects repeatedly");
			return;
		}
		centralObject = object;
		logger.info("add the central object {}", object);
	}

	@Override
	public void addCentralRelation(E object, Double value) {
		checkRep();
		if (centralObject == null) {
			logger.error("Trying to add relation with a non-existent central object!");
			return;
		}
		centralRelations.put(object, value);
		logger.info("add central relation with {}", object);
	}

	@Override
	public void addRelation(E object1, E object2, Double value) {
		checkRep();
		if (!positions.containsKey(object1)) {
			logger.error("cannot find object {}", object1);
			return;
		}
		if (!positions.containsKey(object2)) {
			logger.error("cannot find object {}", object2);
			return;
		}
		physicalRelations.get(object1).put(object2, value);
		physicalRelations.get(object2).put(object1, value);
		logger.info("add physical relation between {} and {} with value {}", object1, object2, value);
	}

	@Override
	public abstract void readFromFile(File file) throws IOException;

	@Override
	public Track getTrack(E object) {
		checkRep();
		if (positions.containsKey(object)) {
			return positions.get(object).getTrack();
		}
		logger.warn("cannot find track with object {}", object);
		return null;
	}

	@Override
	public Track getTrack(Double radius) {
		checkRep();
		for (Track track : tracks) {
			if (track.getRadius().equals(radius)) {
				return track;
			}
		}
		logger.warn("cannot find track with radius {}", radius);
		return null;
	}

	@Override
	public CircularOrbitIterator<E> iterator() {
		checkRep();
		List<E> resultList = new ArrayList<>();
		List<Track> trackList = new ArrayList<>(tracks);
		trackList.sort(Comparator.comparing(Track::getRadius));
		for (Track track : trackList) {
			resultList.addAll(getPhysicalObjects(track));
		}
		logger.info("successfully build the iterator of {}", this);
		return new CircularOrbitIterator<>(resultList);
	}

	@Override
	public Set<Track> getTracks() {
		return tracks;
	}

	@Override
	public L getCentralObject() {
		return centralObject;
	}

	@Override
	public void removePhysicalObject(E physicalObject) {
		checkRep();
		positions.remove(physicalObject);
		physicalRelations.remove(physicalObject);
		for (Map.Entry<E, Map<E, Double>> entry : physicalRelations.entrySet()) {
			entry.getValue().remove(physicalObject);
		}
		centralRelations.remove(physicalObject);
		logger.info("remove a physical object {}", physicalObject);
	}

	@Override
	public List<E> getPhysicalObjects(Track track) {
		checkRep();
		List<Entry<E, Position>> entryList = new ArrayList<>();
		boolean isAngel = false;
		for (Entry<E, Position> entry : positions.entrySet()) {
			if (entry.getValue().getTrack().getRadius().equals(track.getRadius())) {
				if (entry.getValue() instanceof AnglePosition) {
					isAngel = true;
				}
				entryList.add(entry);
			}
		}
		if (isAngel) {
			entryList.sort(Comparator.comparing(o -> ((AnglePosition) o.getValue()).getAngle()));
			return entryList.stream().map(Entry::getKey).collect(Collectors.toList());
		} else {
			return entryList.stream().map(Entry::getKey).collect(Collectors.toList());
		}
	}

	@Override
	public Map<E, Position> getPositions() {
		return new HashMap<>(positions);
	}

	@Override
	public Map<E, Map<E, Double>> getRelations() {
		return new HashMap<>(physicalRelations);
	}

	@Override
	public Set<E> getPhysicalObjects() {
		checkRep();
		Set<E> set = new HashSet<>();
		for (Track track : getTracks()) {
			set.addAll(getPhysicalObjects(track));
		}
		return set;
	}

	@Override
	public abstract E getObject(String name);

	@Override
	public Map<E, Double> getCentralRelations() {
		checkRep();
		return new HashMap<>(centralRelations);
	}

}
