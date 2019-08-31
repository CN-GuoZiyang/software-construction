package otherBean;

import java.util.Calendar;

/**
 * the install log in the personal app ecosystem
 *
 * @author Guo Ziyang
 */
public class InstallLog extends TimeLog {

	protected String name;

	public InstallLog(Calendar time, String name) {
		super(time);
		this.name = name;
	}

	/**
	 * get the name of the app of the install log
	 *
	 * @return the name of the app of the install log
	 */
	public String getName() {
		return name;
	}

}
