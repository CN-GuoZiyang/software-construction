package otherBean;

import java.util.Calendar;

/**
 * the uninstall log in the personal app ecosystem
 *
 * @author Guo Ziyang
 */
public class UninstallLog extends TimeLog {

	protected String name;

	public UninstallLog(Calendar time, String name) {
		super(time);
		this.name = name;
	}

	/**
	 * get the name of the app of the log
	 *
	 * @return the name of the app of the log
	 */
	public String getName() {
		return name;
	}
}
