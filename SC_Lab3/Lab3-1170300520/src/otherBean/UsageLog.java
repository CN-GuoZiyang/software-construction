package otherBean;

import java.util.Calendar;

/**
 * the usage log in the personal app ecosystem
 * 
 * @author Guo Ziyang
 */
public class UsageLog {
	
	private final Calendar time;
	private final String name;
	private final int duration;
	
	public UsageLog(Calendar time, String name, int duration) {
		this.time = time;
		this.name = name;
		this.duration = duration;
	}

	/**
	 * get the time of the usage log
	 * 
	 * @return the time of the usage log
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

	/**
	 * get the duration of the usage
	 * 
	 * @return the duration of the usage
	 */
	public int getDuration() {
		return duration;
	}
	
}
