package configuration.Exception;

public class ConfigurationLabelException extends ConfigurationException {

	private static final long serialVersionUID = -7451507657662107180L;

	public ConfigurationLabelException(String message, long line, String fileName) {
		super("Conflict Label Found: " + message + " at line " + line, fileName);
	}

}
