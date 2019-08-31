package physicalObject;

/**
 * the planet in the stellar system
 * 
 * @author Guo Ziyang
 */
public class Planet extends PhysicalObjectWithSpeed{
	
	private final String name;
	private final String state;
	private final String color;
	private final Double radius;
	
	public Planet(final String name, final String state, final String color, final Double radius, 
			final Double trackRadius, final Double speed, final Boolean clockwise, final Double startAngle) {
		super(speed, trackRadius, clockwise, startAngle);
		this.name = name;
		this.state = state;
		this.color = color;
		this.radius = radius;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return the radius
	 */
	public Double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
