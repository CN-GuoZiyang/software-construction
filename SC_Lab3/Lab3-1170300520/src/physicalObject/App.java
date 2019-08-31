package physicalObject;

/**
 * the app in personal app ecosystem
 * 
 * @author Guo Ziyang
 */
public class App extends PhysicalObject {
	
	private final String name;
	private final String cooperation;
	private final String version;
	private final String description;
	private final String domain;
	
	public App(String name, String cooperation, String version, String description, String domain) {
		this.name = name;
		this.cooperation = cooperation;
		this.version = version;
		this.description = description;
		this.domain = domain;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the cooperation
	 */
	public String getCooperation() {
		return cooperation;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
