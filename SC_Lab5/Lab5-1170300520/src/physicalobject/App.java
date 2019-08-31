package physicalobject;

/**.
 * the app in personal app ecosystem
 *
 * @author Guo Ziyang
 */
public class App extends AbstractPhysicalObject {

  private final String name;
  private final String cooperation;
  private final String version;
  private final String description;
  private final String domain;

  /**.
   * construct an app
   *
   * @param name the name of the app
   * @param cooperation the cooperation of the app
   * @param version the version of the app
   * @param description the description of the app
   * @param domain the domain of the app
   */
  public App(String name, String cooperation, String version, String description, String domain) {
    this.name = name;
    this.cooperation = cooperation;
    this.version = version;
    this.description = description;
    this.domain = domain;
  }

  /**.
   * get the name of the app
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**.
   * get the cooperation of the app
   *
   * @return the cooperation
   */
  public String getCooperation() {
    return cooperation;
  }

  /**.
   * get the version of the app
   *
   * @return the version
   */
  public String getVersion() {
    return version;
  }

  /**.
   * get the description of the app
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**.
   * get the domain of the app
   *
   * @return the domain
   */
  public String getDomain() {
    return domain;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result;
    result = prime * result + ((name == null) ? 0 : name.hashCode())
            + ((cooperation == null) ? 0 : cooperation.hashCode())
            + ((version == null) ? 0 : version.hashCode())
            + ((description == null) ? 0 : description.hashCode())
            + ((domain == null) ? 0 : domain.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    App other = (App) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (cooperation == null) {
      if (other.cooperation != null) {
        return false;
      }
    } else if (!cooperation.equals(other.cooperation)) {
      return false;
    }
    if (version == null) {
      if (other.version != null) {
        return false;
      }
    } else if (!version.equals(other.version)) {
      return false;
    }
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }
    if (domain == null) {
      if (other.domain != null) {
        return false;
      }
    } else if (!domain.equals(other.domain)) {
      return false;
    }
    return true;
  }

}
