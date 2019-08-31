package centralObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the abstraction of the star in the stellar system
 * 
 * @author Guo Ziyang
 */
public class Stellar extends CentralObject{
	
	protected Logger logger = LoggerFactory.getLogger(Stellar.class);
	
	private final Double radius;
	private final Double weight;
	
	public Stellar(final String name, final Double radius, final Double weight) {
		super(name);
		this.radius = radius;
		this.weight = weight;
	}

	/**
	 * get the radius of the stellar
	 * 
	 * @return radius
	 */
	public Double getRadius() {
		return radius;
	}
	
	/**
	 * get the weight of the stellar
	 * 
	 * @return weight
	 */
	public Double getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
