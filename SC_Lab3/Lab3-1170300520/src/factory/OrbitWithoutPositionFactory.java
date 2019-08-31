package factory;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import circularOrbit.AtomStructure;
import circularOrbit.PersonalAppEcosystem;
import configuration.AppConfiguration;
import configuration.AppConfigurationReader;
import otherBean.InstallLog;
import otherBean.UninstallLog;
import otherBean.UsageLog;
import physicalObject.App;

/**
 * the factory building the non-position-sensitive circular orbit
 * 
 * @author Guo Ziyang
 */
public class OrbitWithoutPositionFactory {
	
	private Logger logger = LoggerFactory.getLogger(OrbitWithoutPositionFactory.class);
	
	/**
	 * build an atom structure using a name
	 * 
	 * @param name the name of the atom
	 * @return the atom structure object
	 */
	public AtomStructure buildAtomStructure(String name) {
		logger.info("build a AtomStructure with name {}", name);
		return new AtomStructure(name);
	}
	
	/**
	 * build an atom structure via the configuration file
	 * 
	 * @param file the configuration file 
	 * @return the atom structure object
	 */
	public AtomStructure buildAtomStructure(File file) {
		logger.info("build a AtomStructure with file {}", file.getAbsolutePath());
		return new AtomStructure(file);
	}
	
	/**
	 * build a personal app ecosystem via configuration file
	 * 
	 * @param file the configuration file
	 * @return the personal app ecosystem
	 */
	public PersonalAppEcosystem buildPersonalAppEcosystem(File file) {
		AppConfigurationReader reader = new AppConfigurationReader(file);
		AppConfiguration appConfiguration = reader.readFile();
		Calendar earliestTime = null;
		Calendar latestTime = null;
		for(UsageLog usageLog : appConfiguration.getUsageLogs()) {
			if(usageLog != null) {
				earliestTime = usageLog.getTime();
				latestTime = usageLog.getTime();
				break;
			}
		}
		for(InstallLog installLog : appConfiguration.getInstallLogs()) {
			if(installLog.getTime().before(earliestTime)) {
				earliestTime = installLog.getTime();
			}
			if(installLog.getTime().after(latestTime)) {
				latestTime = installLog.getTime();
			}
		}
		for(UsageLog usageLog : appConfiguration.getUsageLogs()) {
			if(usageLog.getTime().before(earliestTime)) {
				earliestTime = usageLog.getTime();
			}
			if(usageLog.getTime().after(latestTime)) {
				latestTime = usageLog.getTime();
			}
		}
		for(UninstallLog uninstallLog : appConfiguration.getUninstallLogs()) {
			if(uninstallLog.getTime().before(earliestTime)) {
				earliestTime = uninstallLog.getTime();
			}
			if(uninstallLog.getTime().after(latestTime)) {
				latestTime = uninstallLog.getTime();
			}
		}
		PersonalAppEcosystem.ecosystems.clear();
		Map<String, App> appNameMap = new HashMap<>();
		for(App app : appConfiguration.getApps()) {
			//System.out.println(app.getName());
			appNameMap.put(app.getName(), app);
		}
		List<AppConfiguration> configurations = new ArrayList<>();
		Calendar start = null;
		boolean lastTime = false;
		Set<App> currentApps = new HashSet<>();
		switch (appConfiguration.getPeriod()) {
		case AppConfiguration.HOUR:
			start = earliestTime;
			while(true) {
				Calendar end = (Calendar) start.clone();
				end.add(Calendar.HOUR, 1);
//				System.out.println(start.get(Calendar.MONTH) + "-" + start.get(Calendar.DAY_OF_MONTH));
//				System.out.println(end.get(Calendar.MONTH) + "-" + end.get(Calendar.DAY_OF_MONTH));
				AppConfiguration configuration = new AppConfiguration();
				configuration.setUser(appConfiguration.getUser());
				configuration.getRelations().addAll(appConfiguration.getRelations());
				if(end.after(latestTime)) {
					lastTime = true;
					end = latestTime;
				}
				for(InstallLog installLog : appConfiguration.getInstallLogs()) {
					if(installLog.getTime().before(end) && installLog.getTime().after(start)) {
						configuration.addInstallLog(installLog);
						currentApps.add(appNameMap.get(installLog.getName()));
					}
				}
				for(UsageLog usageLog : appConfiguration.getUsageLogs()) {
					if(usageLog.getTime().before(end) && usageLog.getTime().after(start)) {
						configuration.addUsageLog(usageLog);
						currentApps.add(appNameMap.get(usageLog.getName()));
					}
				}
				Set<App> appToRemove = new HashSet<>();
				for(UninstallLog uninstallLog : appConfiguration.getUninstallLogs()) {
					if(uninstallLog.getTime().before(end) && uninstallLog.getTime().after(start)) {
						configuration.addUninstallLog(uninstallLog);
						appToRemove.add(appNameMap.get(uninstallLog.getName()));
					}
				}
				configuration.getApps().addAll(new HashSet<>(currentApps)); 
				configurations.add(configuration);
				currentApps.removeAll(appToRemove);
				if(lastTime) {
					break;
				}
				start = end;
			}
		case AppConfiguration.DAY:
			start = earliestTime;
			while(true) {
				Calendar end = (Calendar) start.clone();
				end.add(Calendar.DATE, 1);
//				System.out.println(start.get(Calendar.MONTH) + "-" + start.get(Calendar.DAY_OF_MONTH));
//				System.out.println(end.get(Calendar.MONTH) + "-" + end.get(Calendar.DAY_OF_MONTH));
				AppConfiguration configuration = new AppConfiguration();
				configuration.setUser(appConfiguration.getUser());
				configuration.getRelations().addAll(appConfiguration.getRelations());
				if(end.after(latestTime)) {
					lastTime = true;
					end = latestTime;
				}
				for(InstallLog installLog : appConfiguration.getInstallLogs()) {
					if(installLog.getTime().before(end) && installLog.getTime().after(start)) {
						configuration.addInstallLog(installLog);
						currentApps.add(appNameMap.get(installLog.getName()));
					}
				}
				for(UsageLog usageLog : appConfiguration.getUsageLogs()) {
					if(usageLog.getTime().before(end) && usageLog.getTime().after(start)) {
						configuration.addUsageLog(usageLog);
						currentApps.add(appNameMap.get(usageLog.getName()));
					}
				}
				Set<App> appToRemove = new HashSet<>();
				for(UninstallLog uninstallLog : appConfiguration.getUninstallLogs()) {
					if(uninstallLog.getTime().before(end) && uninstallLog.getTime().after(start)) {
						configuration.addUninstallLog(uninstallLog);
						appToRemove.add(appNameMap.get(uninstallLog.getName()));
					}
				}
				configuration.getApps().addAll(new HashSet<>(currentApps)); 
				configurations.add(configuration);
				currentApps.removeAll(appToRemove);
				if(lastTime) {
					break;
				}
				start = end;
			}
			break;
		case AppConfiguration.WEEK:
			start = earliestTime;
			while(true) {
				Calendar end = (Calendar) start.clone();
				end.add(Calendar.DATE, 7);
//				System.out.println(start.get(Calendar.MONTH) + "-" + start.get(Calendar.DAY_OF_MONTH));
//				System.out.println(end.get(Calendar.MONTH) + "-" + end.get(Calendar.DAY_OF_MONTH));
				AppConfiguration configuration = new AppConfiguration();
				configuration.setUser(appConfiguration.getUser());
				configuration.getRelations().addAll(appConfiguration.getRelations());
				if(end.after(latestTime)) {
					lastTime = true;
					end = latestTime;
				}
				for(InstallLog installLog : appConfiguration.getInstallLogs()) {
					if(installLog.getTime().before(end) && installLog.getTime().after(start)) {
						configuration.addInstallLog(installLog);
						currentApps.add(appNameMap.get(installLog.getName()));
					}
				}
				for(UsageLog usageLog : appConfiguration.getUsageLogs()) {
					if(usageLog.getTime().before(end) && usageLog.getTime().after(start)) {
						configuration.addUsageLog(usageLog);
						currentApps.add(appNameMap.get(usageLog.getName()));
					}
				}
				Set<App> appToRemove = new HashSet<>();
				for(UninstallLog uninstallLog : appConfiguration.getUninstallLogs()) {
					if(uninstallLog.getTime().before(end) && uninstallLog.getTime().after(start)) {
						configuration.addUninstallLog(uninstallLog);
						appToRemove.add(appNameMap.get(uninstallLog.getName()));
					}
				}
				configuration.getApps().addAll(new HashSet<>(currentApps)); 
				configurations.add(configuration);
				currentApps.removeAll(appToRemove);
				if(lastTime) {
					break;
				}
				start = end;
			}
			break;
			
		case AppConfiguration.MONTH:
			start = earliestTime;
			while(true) {
				Calendar end = (Calendar) start.clone();
				end.add(Calendar.MONTH, 1);
//				System.out.println(start.get(Calendar.MONTH) + "-" + start.get(Calendar.DAY_OF_MONTH));
//				System.out.println(end.get(Calendar.MONTH) + "-" + end.get(Calendar.DAY_OF_MONTH));
				AppConfiguration configuration = new AppConfiguration();
				configuration.setUser(appConfiguration.getUser());
				configuration.getRelations().addAll(appConfiguration.getRelations());
				if(end.after(latestTime)) {
					lastTime = true;
					end = latestTime;
				}
				for(InstallLog installLog : appConfiguration.getInstallLogs()) {
					if(installLog.getTime().before(end) && installLog.getTime().after(start)) {
						configuration.addInstallLog(installLog);
						currentApps.add(appNameMap.get(installLog.getName()));
					}
				}
				for(UsageLog usageLog : appConfiguration.getUsageLogs()) {
					if(usageLog.getTime().before(end) && usageLog.getTime().after(start)) {
						configuration.addUsageLog(usageLog);
						currentApps.add(appNameMap.get(usageLog.getName()));
					}
				}
				Set<App> appToRemove = new HashSet<>();
				for(UninstallLog uninstallLog : appConfiguration.getUninstallLogs()) {
					if(uninstallLog.getTime().before(end) && uninstallLog.getTime().after(start)) {
						configuration.addUninstallLog(uninstallLog);
						appToRemove.add(appNameMap.get(uninstallLog.getName()));
					}
				}
				configuration.getApps().addAll(new HashSet<>(currentApps)); 
				configurations.add(configuration);
				currentApps.removeAll(appToRemove);
				if(lastTime) {
					break;
				}
				start = end;
			}
			break;
		}
		for(AppConfiguration configuration : configurations) {
			PersonalAppEcosystem appEcosystem = new PersonalAppEcosystem();
			appEcosystem.buildFromConfiguration(configuration);
			PersonalAppEcosystem.ecosystems.add(appEcosystem);
		}
		return null;
	}
	
}
