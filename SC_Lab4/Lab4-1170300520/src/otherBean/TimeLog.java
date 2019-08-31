package otherBean;

import java.util.Calendar;

public abstract class TimeLog {

	protected Calendar time;

	public TimeLog(Calendar time) {
		this.time = time;
	}

	/**
	 * get the time of the install log
	 *
	 * @return the time of the install log
	 */
	public Calendar getTime() {
		return time;
	}

}
