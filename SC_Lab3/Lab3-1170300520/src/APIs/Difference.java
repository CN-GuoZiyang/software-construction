package APIs;

import java.util.HashMap;
import java.util.Map;

/**
 * the class describing the difference between two circular orbits
 * 
 * @author Guo Ziyang
 */
public class Difference {
	
	protected int trackNumberDifference;
	
	protected Map<Double, Integer> physicalObjectNumberDifference;
	
	public Difference(int trackNumberDifference) {
		this.trackNumberDifference = trackNumberDifference;
		physicalObjectNumberDifference = new HashMap<>();
	}
	
	/**
	 * get the difference of the tarck number
	 * 
	 * @return the difference of the tarck number
	 */
	public int getTrackNumberDifference() {
		return trackNumberDifference;
	}
	
	/**
	 * get the difference of the number of objects on each track
	 * 
	 * @return the difference of the number of objects on each track
	 */
	public Map<Double, Integer> getPhysicalObjectNumberDifference() {
		return physicalObjectNumberDifference;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("轨道数差异：" + trackNumberDifference + "\n");
		for(Map.Entry<Double, Integer> entry : physicalObjectNumberDifference.entrySet()) {
			builder.append("轨道 " + entry.getKey() + " 的物体数量差异：" + entry.getValue() + "\n");
		}
		return builder.toString();
	}
	
}
