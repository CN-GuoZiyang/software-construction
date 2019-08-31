package circularOrbit;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import factory.TrackFactory;
import position.AnglePosition;
import position.Position;
import track.Track;

/**
 * the abstraction of the concrete circular orbit with some implements of the common methods between position-sensitive and non-position-sensitive orbit
 * 
 * @author Guo Ziyang
 *
 * @param <L> the type of central object
 * @param <E> the type of the physical object around the central object
 */
public abstract class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {
	
	protected L centralObject;
	protected Set<Track> tracks;
	protected Map<E, Position> positions;
	protected Map<E, Map<E, Double>> physicalRelations;
	protected Map<E, Double> centralRelations;
	
	protected TrackFactory trackFactory;
	
	protected Logger logger = LoggerFactory.getLogger(ConcreteCircularOrbit.class);
	
	public ConcreteCircularOrbit() {
		tracks = new HashSet<>();
		positions = new HashMap<>();
		physicalRelations = new HashMap<>();
		centralRelations = new HashMap<>();
		trackFactory = new TrackFactory();
	}
	
	@Override
	public void addTrack(Double radius) {
		Track track = getTrack(radius);
		if(track != null) {
			logger.error("Trying to add a duplicate track!");
			return;
		}
		tracks.add(trackFactory.build(radius));
		logger.info("add a track with radius {}", radius);
	}
	
	@Override
	public void removeTrack(Double radius) {
		Track track = getTrack(radius);
		if(track == null) {
			logger.error("Trying to remove a non-existent track!");
			return;
		}
		tracks.remove(track);
		List<E> physicalObjectToRemove = new ArrayList<>();
		for(Entry<E, Position> entry : positions.entrySet()) {
			if(entry.getValue().getTrack().getRadius().equals(radius)) {
				physicalObjectToRemove.add(entry.getKey());
			}
		}
		for(E physicalObject : physicalObjectToRemove) {
			removePhysicalObject(physicalObject);
		}
		logger.info("remove the track {}", track);
	}
	
	@Override
	public void addCentralObject(L object) {
		if(centralObject != null) {
			logger.error("Trying to add central objects repeatedly");
			return;
		}
		centralObject = object;
		logger.info("add the central object {}", object);
	}
	
	@Override
	public void addCentralRelation(E object, Double value) {
		if(centralObject == null) {
			logger.error("Trying to add relation with a non-existent central object!");
			return;
		}
		centralRelations.put(object, value);
		logger.info("add central relation with {}", object);
	}
	
	@Override
	public void addRelation(E object1, E object2, Double value) {
		if(!positions.containsKey(object1)) {
			logger.error("cannot find object {}", object1);
			return;
		}
		if(!positions.containsKey(object2)) {
			logger.error("cannot find object {}", object2);
			return;
		}
		physicalRelations.get(object1).put(object2, value);
		physicalRelations.get(object2).put(object1, value);
		logger.info("add physical relation between {} and {} with value {}", object1, object2, value);
	}
	
	@Override
	public abstract void readFromFile(File file);
	
	@Override
	public Track getTrack(E object) {
		if(positions.containsKey(object)) {
			return positions.get(object).getTrack();
		}
		logger.warn("cannot find track with object {}", object);
		return null;
	}
	
	@Override
	public Track getTrack(Double radius) {
		for(Track track : tracks) {
			if(track.getRadius().equals(radius)) {
				return track;
			}
		}
		logger.warn("cannot find track with radius {}", radius);
		return null;
	}
	
	@Override
	public CircularOrbitIterator<E> iterator() {
		List<E> resultList = new ArrayList<>();
		List<Track> trackList = new ArrayList<>(tracks);
		Collections.sort(trackList, new Comparator<Track>() {
			@Override
			public int compare(Track o1, Track o2) {
				return o1.getRadius().compareTo(o2.getRadius());
			}
		});
		for(Track track : trackList) {
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
		positions.remove(physicalObject);
		physicalRelations.remove(physicalObject);
		for(Map.Entry<E, Map<E, Double>> entry : physicalRelations.entrySet()) {
			if(entry.getValue().containsKey(physicalObject)) {
				entry.getValue().remove(physicalObject);
			}
		}
		if(centralRelations.containsKey(physicalObject)) {
			centralRelations.remove(physicalObject);
		}
		logger.info("remove a physical object {}", physicalObject);
	}
	
	@Override
	public List<E> getPhysicalObjects(Track track) {
		List<Entry<E, Position>> entryList = new ArrayList<>();
		Boolean isAngel = false;
		for(Entry<E, Position> entry : positions.entrySet()) {
			if(entry.getValue().getTrack().getRadius().equals(track.getRadius())) {
				if(entry.getValue() instanceof AnglePosition) {
					isAngel = true;
				}
				entryList.add(entry);
			}
		}
		if(!isAngel) {
			return entryList.stream().map(Entry::getKey).collect(Collectors.toList());
		} else {
			entryList.sort(new Comparator<Entry<E, Position>>() {
				@Override
				public int compare(Entry<E, Position> o1, Entry<E, Position> o2) {
					return ((AnglePosition)o1.getValue()).getAngle().compareTo(((AnglePosition)o2.getValue()).getAngle());
				}
			});
			return entryList.stream().map(Entry::getKey).collect(Collectors.toList());
		}
	}
	
	@Override
	public Map<E, Position> getPositions() {
		return positions;
	}
	
	@Override
	public Map<E, Map<E, Double>> getRelations() {
		return physicalRelations;
	}
	
	@Override
	public Set<E> getPhysicalObjects() {
		Set<E> set = new HashSet<>();
		for(Track track : getTracks()) {
			set.addAll(getPhysicalObjects(track));
		}
		return set;
	}
	
	@Override
	public abstract E getObject(String name);
	
	@Override
	public Map<E, Double> getCentralRelations() {
		return centralRelations;
	}
	
}
