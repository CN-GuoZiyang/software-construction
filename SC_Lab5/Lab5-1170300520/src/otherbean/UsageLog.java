package otherbean;

import java.util.Calendar;

/**.
 * the usage log in the personal app ecosystem
 *
 * @author Guo Ziyang
 */
public class UsageLog extends AbstractLog {

  protected String name;
  protected int duration;

  /**.
   * build an usage log
   *
   * @param time time
   * @param name name
   * @param duration duration
   */
  public UsageLog(Calendar time, String name, int duration) {
    super(time);
    this.name = name;
    this.duration = duration;
  }

  /**.
   * get the name of the app of the log
   *
   * @return the name of the app of the log
   */
  public String getName() {
    return name;
  }

  /**.
   * get the duration of the usage
   *
   * @return the duration of the usage
   */
  public int getDuration() {
    return duration;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + duration;
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
    UsageLog other = (UsageLog) obj;
    if (duration != other.duration) {
      return false;
    }
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
