package configuration;

import centralObject.User;
import otherBean.InstallLog;
import otherBean.Relation;
import otherBean.UninstallLog;
import otherBean.UsageLog;
import physicalObject.App;

import java.util.HashSet;
import java.util.Set;

/**
 * the configuration object of the app ecosystem
 *
 * @author Guo Ziyang
 */
public class AppConfiguration implements Configuration {

	/**
	 * the configuration is divided by hour
	 */
	public static final int HOUR = 1;
	/**
	 * the configuration is divided by day
	 */
	public static final int DAY = 2;
	/**
	 * the configuration is divided by week
	 */
	public static final int WEEK = 3;
	/**
	 * the configuration is divided by month
	 */
	public static final int MONTH = 4;

	private User user;
	private Set<App> apps;
	private Set<InstallLog> installLogs;
	private Set<UninstallLog> uninstallLogs;
	private Set<UsageLog> usageLogs;
	private Set<Relation> relations;
	private int period;

	public AppConfiguration() {
		apps = new HashSet<>();
		installLogs = new HashSet<>();
		uninstallLogs = new HashSet<>();
		usageLogs = new HashSet<>();
		relations = new HashSet<>();
	}

	/**
	 * get the user
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * set the user
	 *
	 * @param user the user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * add a app to the app list
	 *
	 * @param app app to be added
	 */
	public void addApps(App app) {
		apps.add(app);
	}

	/**
	 * add an installlog
	 *
	 * @param installLog installlog to be added
	 */
	public void addInstallLog(InstallLog installLog) {
		installLogs.add(installLog);
	}

	/**
	 * add an uninstalllog
	 *
	 * @param uninstallLog uninstalllog to be added
	 */
	public void addUninstallLog(UninstallLog uninstallLog) {
		uninstallLogs.add(uninstallLog);
	}

	/**
	 * add a usage log
	 *
	 * @param usageLog usage log to be added
	 */
	public void addUsageLog(UsageLog usageLog) {
		usageLogs.add(usageLog);
	}

	/**
	 * add a relation between apps
	 *
	 * @param relation relation to be added
	 */
	public void addRelations(Relation relation) {
		relations.add(relation);
	}

	/**
	 * get the way the configuration is divided
	 *
	 * @return the way the configuration is divided
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * set the way the configuration is divided
	 *
	 * @param period the way the configuration is divided
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @return the apps
	 */
	public Set<App> getApps() {
		return apps;
	}

	/**
	 * @return the installLogs
	 */
	public Set<InstallLog> getInstallLogs() {
		return installLogs;
	}

	/**
	 * @return the uninstallLogs
	 */
	public Set<UninstallLog> getUninstallLogs() {
		return uninstallLogs;
	}

	/**
	 * @return the usageLogs
	 */
	public Set<UsageLog> getUsageLogs() {
		return usageLogs;
	}

	/**
	 * @return the relations
	 */
	public Set<Relation> getRelations() {
		return relations;
	}

}
