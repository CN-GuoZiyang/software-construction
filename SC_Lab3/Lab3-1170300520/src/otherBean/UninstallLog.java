package otherBean;

import java.util.Calendar;

/**
 * the uninstall log in the personal app ecosystem
 * 
 * @author Guo Ziyang
 */
public class UninstallLog {
	
	private final Calendar time;
	private final String name;
	
	public UninstallLog(Calendar time, String name) {
		this.time = time;
		this.name = name;
	}

	/**
	 * get the time of the log
	 * 
	 * @return the time of the log
	 */
	public Calendar getTime() {
		return time;
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
