package otherBean;

import java.util.Calendar;

/**
 * the install log in the personal app ecosystem
 * 
 * @author Guo Ziyang
 */
public class InstallLog {
	
	private final Calendar time;
	private final String name;
	
	public InstallLog(Calendar time, String name) {
		this.time = time;
		this.name = name;
	}

	/**
	 * get the time of the install log
	 * 
	 * @return the time of the install log
	 */
	public Calendar getTime() {
		return time;
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
