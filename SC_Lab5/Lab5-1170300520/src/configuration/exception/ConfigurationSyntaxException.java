package configuration.exception;

/**.
 * Configuration Syntax exception
 *
 * @author Guo Ziyang
 */
public class ConfigurationSyntaxException extends ConfigurationException {

  private static final long serialVersionUID = 8140121285734822264L;

  public ConfigurationSyntaxException(String message, long line, String fileName) {
    super("Configuration Syntax error: " + message + " at line " + line, fileName);
  }

}
