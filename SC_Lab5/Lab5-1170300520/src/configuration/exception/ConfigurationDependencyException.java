package configuration.exception;

/**.
 * Configuration Dependency exception
 *
 * @author Guo Ziyang
 */
public class ConfigurationDependencyException extends ConfigurationException {

  private static final long serialVersionUID = -5368676962451975591L;

  public ConfigurationDependencyException(String message, Long line, String fileName) {
    super("Dependency Error: " + message + " at line " + line, fileName);
  }

}
