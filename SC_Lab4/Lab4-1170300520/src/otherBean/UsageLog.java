package otherBean;

import java.util.Calendar;

/**
 * the usage log in the personal app ecosystem
 *
 * @author Guo Ziyang
 */
public class UsageLog extends TimeLog {

	protected String name;
	protected int duration;

	public UsageLog(Calendar time, String name, int duration) {
		super(time);
		this.name = name;
		this.duration = duration;
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
