package APIs;

import physicalObject.PhysicalObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * the difference of the difference with object difference
 *
 * @author Guo Ziyang
 */
public class DifferenceWithObject extends Difference {

	protected Map<Double, List<Set<PhysicalObject>>> physicalObjectDifference;

	public DifferenceWithObject(int trackNumberDifference) {
		super(trackNumberDifference);
		physicalObjectDifference = new HashMap<>();
	}

	/**
	 * get the difference of the objects on each track
	 *
	 * @return the difference of the objects on each track
	 */
	public Map<Double, List<Set<PhysicalObject>>> getPhysicalObjectDifference() {
		return physicalObjectDifference;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("轨道数差异：" + trackNumberDifference + "\n");
		for (Map.Entry<Double, Integer> entry : physicalObjectNumberDifference.entrySet()) {
			builder.append("轨道 " + entry.getKey() + " 的物体数量差异：" + entry.getValue() + "；物体差异：");
			List<Set<PhysicalObject>> list = physicalObjectDifference.get(entry.getKey());
			builder.append(list.get(0) + "-" + list.get(1) + "\n");
		}
		return builder.toString();
	}

}
