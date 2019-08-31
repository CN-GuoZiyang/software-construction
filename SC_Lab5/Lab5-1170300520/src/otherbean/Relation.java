package otherbean;

/**.
 * the relation in the personal app ecosystem
 *
 * @author Guo Ziyang
 */
public class Relation {

  private final String appName1;
  private final String appName2;

  public Relation(String appName1, String appName2) {
    this.appName1 = appName1;
    this.appName2 = appName2;
  }

  /**.
   * get the name of app1
   *
   * @return the name of app1
   */
  public String getAppName1() {
    return appName1;
  }

  /**.
   * get the name of app2
   *
   * @return the name of app2
   */
  public String getAppName2() {
    return appName2;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result;
    result = prime * result + ((appName1 == null) ? 0 : appName1.hashCode())
            + ((appName2 == null) ? 0 : appName2.hashCode());
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
    Relation other = (Relation) obj;
    if (appName1 == null) {
      if (other.appName1 != null) {
        return false;
      }
    } else if (!appName1.equals(other.appName1)) {
      return false;
    }
    if (appName2 == null) {
      if (other.appName2 != null) {
        return false;
      }
    } else if (!appName2.equals(other.appName2)) {
      return false;
    }
    return true;
  }

}
