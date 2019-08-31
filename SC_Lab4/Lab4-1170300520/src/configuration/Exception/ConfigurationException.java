package configuration.Exception;

public class ConfigurationException extends RuntimeException {

	private static final long serialVersionUID = -3373883315344098636L;

	public ConfigurationException(String message, String fileName) {
		super("\n" + message + "\nFile: " + fileName);
	}

}
