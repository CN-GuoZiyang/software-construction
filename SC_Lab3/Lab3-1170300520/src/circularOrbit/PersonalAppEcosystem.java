package circularOrbit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import centralObject.User;
import configuration.AppConfiguration;
import otherBean.Relation;
import otherBean.UsageLog;
import physicalObject.App;

/**
 * the implement of a personal app ecosystem consisting of a user and some apps
 * 
 * @author Guo Ziyang
 */
public class PersonalAppEcosystem extends ConcreteCircularOrbitWithoutPosition<User, App> {
	
	public static List<PersonalAppEcosystem> ecosystems = new ArrayList<>();
	
	public PersonalAppEcosystem() {
		super();
	}
	
	@Override
	public void readFromFile(File file) {
		throw new RuntimeException();
	}
	
	/**
	 * build the system using the configuration object
	 * 
	 * @param configuration the configuration object
	 */
	public void buildFromConfiguration(AppConfiguration configuration) {
		addCentralObject(configuration.getUser());
		Map<App, Integer> radiusMap = new HashMap<>();
		Map<App, Integer> timeMap = new HashMap<>();
		Map<App, Integer> durationMap = new HashMap<>();
		Map<String, App> appNameMap = new HashMap<>();
		Set<App> allApps = configuration.getApps();
		for(App app : allApps) {
			appNameMap.put(app.getName(), app);
			radiusMap.put(app, -1);
			timeMap.put(app, 0);
			durationMap.put(app, 0);
			//System.out.print(app.getName() + " ");
		}
		//System.out.println();
		for(UsageLog usageLog : configuration.getUsageLogs()) {
			//System.out.println(usageLog.getName() + " ");
			App app = appNameMap.get(usageLog.getName());
			timeMap.put(app, timeMap.get(app) + 1);
			durationMap.put(app, durationMap.get(app) + usageLog.getDuration());
		}
		//System.out.println();
		int maxValue = 0;
		for(Map.Entry<String, App> entry : appNameMap.entrySet()) {
			App app = entry.getValue();
			int tempValue = 0;
			if(timeMap.get(app) != null) {
				tempValue = timeMap.get(app) * durationMap.get(app);
			}
			radiusMap.put(app, tempValue);
			if(maxValue < tempValue) {
				maxValue = tempValue;
			}
		}
		for(int i = 1; i <= 10; i ++) {
			addTrack((double)i);
		}
		for(Map.Entry<App, Integer> entry : radiusMap.entrySet()) {
			int radius = 0;
			if(entry.getValue() == -1) {
				radius = 10;
			} else {
				//System.out.println(entry.getValue().doubleValue() + "/" + maxValue + "=" + entry.getValue().doubleValue() / maxValue);
				radius = (int) ((1 - entry.getValue().doubleValue() / maxValue) * 10) + 1;
				radius = radius == 11 ? 10 : radius;
			}
			//logger.info("App: {}, Radius: {}", entry.getKey(), radius);
			addPhysicalObject(entry.getKey(), (double)radius);
		}
		for(Relation relation : configuration.getRelations()) {
			App app1 = appNameMap.get(relation.getAppName1());
			App app2 = appNameMap.get(relation.getAppName2());
			if(app1 != null && app2 != null) {
				addRelation(app1, app2, 0D);
			}
		}
	}
	
	@Override
	public App getObject(String name) {
		for(App app : getPhysicalObjects()) {
			if(name.equals(app.getName())) {
				return app;
			}
		}
		return null;
	}

}
