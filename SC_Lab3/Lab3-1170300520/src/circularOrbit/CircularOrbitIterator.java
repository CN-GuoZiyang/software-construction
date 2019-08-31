package circularOrbit;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the iterator tool of the circular orbit 
 * 
 * @author Guo Ziyang
 *
 * @param <E> the type of the objects to be iterated
 */
public class CircularOrbitIterator<E> {
	
	private int cursor;
	private int lastRet;
	
	private List<E> objectList;
	
	private Logger logger = LoggerFactory.getLogger(CircularOrbitIterator.class);
	
	public CircularOrbitIterator(List<E> objectList) {
		this.cursor = 0;
		this.lastRet = -1;
		this.objectList = objectList;
	}
	
	/**
	 * whether there is a next object
	 * 
	 * @return true if there's an object next
	 */
	public boolean hasNext() {
		return cursor != objectList.size();
	}
	
	/**
	 * get the next object
	 * 
	 * @return the object next
	 * @throws NoSuchElementException if the iteration has no more elements
	 */
	public E next() {
		int i = cursor;
		if(i >= objectList.size()) {
			logger.error("reach last index of the iterator");
			throw new NoSuchElementException();
		}
		cursor = i + 1;
		return objectList.get(lastRet++);
	}
	
}
