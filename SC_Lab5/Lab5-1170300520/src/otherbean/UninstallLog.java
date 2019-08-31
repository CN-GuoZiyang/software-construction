package otherbean;

import java.util.Calendar;

/**.
 * the uninstall log in the personal app ecosystem
 *
 * @author Guo Ziyang
 */
public class UninstallLog extends AbstractLog {

  protected String name;

  public UninstallLog(Calendar time, String name) {
    super(time);
    this.name = name;
  }

  /**.
   * get the name of the app of the log
   *
   * @return the name of the app of the log
   */
  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result;
    result = prime * result + ((name == null) ? 0 : name.hashCode())
            + ((time == null) ? 0 : time.hashCode());
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
    UninstallLog other = (UninstallLog) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!time.equals(other.time)) {
      return false;
    }
    return true;
  }
}
