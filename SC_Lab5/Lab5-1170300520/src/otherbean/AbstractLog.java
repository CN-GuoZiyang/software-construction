package otherbean;

import java.util.Calendar;

/**.
 * the common log style read from config
 *
 * @author Guo Ziyang
 */
public abstract class AbstractLog {

  protected Calendar time;

  public AbstractLog(Calendar time) {
    this.time = time;
  }

  /**.
   * get the time of the install log
   *
   * @return the time of the install log
   */
  public Calendar getTime() {
    return time;
  }

}
