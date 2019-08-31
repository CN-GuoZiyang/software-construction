package circularorbit;

import configuration.Configuration;
import factory.TrackFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import position.AbstractPosition;
import position.AnglePosition;
import track.Track;

/**
 * .
 * the abstraction of the concrete circular orbit with some implements of the
 * common methods between position-sensitive and non-position-sensitive orbit
 *
 * @param <L> the type of central object
 * @param <E> the type of the physical object around the central object
 * @author Guo Ziyang
 */
public abstract class AbstractCircularOrbit<L, E> implements CircularOrbit<L, E> {

  protected L centralObject;
  protected Map<Double, Track> tracks;
  protected Map<Double, Set<E>> objectOnTracks;
  protected Map<E, AbstractPosition> positions;
  protected Map<E, Map<E, Double>> physicalRelations;
  protected Map<E, Double> centralRelations;

  protected TrackFactory trackFactory;

  private Logger logger = LoggerFactory.getLogger(AbstractCircularOrbit.class);

  /**.
   * default constructor
   */
  public AbstractCircularOrbit() {
    tracks = new HashMap<>();
    positions = new HashMap<>();
    physicalRelations = new HashMap<>();
    centralRelations = new HashMap<>();
    trackFactory = new TrackFactory();
    objectOnTracks = new HashMap<>();
  }

  /**.
   * check rep
   */
  public void checkRep() {
    assert tracks != null;
    assert positions != null;
    assert physicalRelations != null;
    assert centralRelations != null;
    assert objectOnTracks != null;
  }

  @Override
  public void addTrack(Double radius) {
    checkRep();
    if (getTrack(radius) != null) {
      logger.error("Trying to add a duplicate track!");
      return;
    }
    tracks.put(radius, trackFactory.build(radius));
    objectOnTracks.put(radius, new HashSet<>());
    logger.info("add a track with radius {}", radius);
  }

  @Override
  public void removeTrack(Double radius) {
    checkRep();
    if (getTrack(radius) == null) {
      System.out.println("半径为" + radius + "的轨道不存在！");
      logger.error("Trying to remove a non-existent track!");
      return;
    }
    Set<E> physicalObjectToRemove = objectOnTracks.get(radius);
    for (E physicalObject : physicalObjectToRemove) {
      removePhysicalObject(physicalObject);
    }
    objectOnTracks.remove(radius);
    tracks.remove(radius);
    logger.info("remove the track {}", radius);
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
  public abstract void buildFromConfiguration(Configuration configuration);

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
    if (tracks.containsKey(radius)) {
      return tracks.get(radius);
    }
    logger.warn("cannot find track with radius {}", radius);
    return null;
  }

  @Override
  public CircularOrbitIterator<E> iterator() {
    checkRep();
    List<E> resultList = new ArrayList<>();
    List<Track> trackList = new ArrayList<>(tracks.values());
    trackList.sort(Comparator.comparing(Track::getRadius));
    for (Track track : trackList) {
      resultList.addAll(getPhysicalObjects(track));
    }
    logger.info("successfully build the iterator of {}", this);
    return new CircularOrbitIterator<>(resultList);
  }

  @Override
  public Set<Track> getTracks() {
    return new HashSet<>(tracks.values());
  }

  @Override
  public L getCentralObject() {
    return centralObject;
  }

  @Override
  public void removePhysicalObject(E physicalObject) {
    checkRep();
    objectOnTracks.get(getTrack(physicalObject).getRadius()).remove(physicalObject);
    positions.remove(physicalObject);
    logger.info("remove a physical object {}", physicalObject);
  }

  @Override
  public List<E> getPhysicalObjects(Track track) {
    checkRep();
    List<E> objectList = new ArrayList<>(objectOnTracks.get(track.getRadius()));
    if (this instanceof AbstractOrbitWithPosition) {
      Map<E, AbstractPosition> positionMap = new HashMap<>();
      for (E object : objectList) {
        positionMap.put(object, positions.get(object));
      }
      List<Entry<E, AbstractPosition>> positionList = new ArrayList<>(positionMap.entrySet());
      positionList.sort(Comparator.comparing(o -> ((AnglePosition) o.getValue()).getAngle()));
      return positionList.stream().map(Entry::getKey).collect(Collectors.toList());
    } else {
      return new ArrayList<>(objectList);
    }
  }

  @Override
  public Set<E> getPhysicalObjects() {
    checkRep();
    Set<E> set = new HashSet<>();
    for (Entry<Double, Set<E>> entry : objectOnTracks.entrySet()) {
      set.addAll(entry.getValue());
    }
    return set;
  }

  @Override
  public Map<E, AbstractPosition> getPositions() {
    return new HashMap<>(positions);
  }

  @Override
  public Map<E, Map<E, Double>> getRelations() {
    return new HashMap<>(physicalRelations);
  }

  @Override
  public Map<E, Double> getCentralRelations() {
    checkRep();
    return new HashMap<>(centralRelations);
  }

  @Override
  public abstract E getObject(String name);

  @Override
  public Map<Double, Set<E>> getObjectOnTracks() {
    return objectOnTracks;
  }

}
