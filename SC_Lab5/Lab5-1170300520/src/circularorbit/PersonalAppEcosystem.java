package circularorbit;

import centralobject.User;
import configuration.AppConfiguration;

import java.io.File;

import java.util.*;

import configuration.Configuration;
import otherbean.Relation;
import otherbean.UsageLog;
import physicalobject.App;

/**.
 * the implement of a personal app ecosystem consisting of a user and some apps
 *
 * @author Guo Ziyang
 */
public class PersonalAppEcosystem extends AbstractOrbit<User, App> {

  public static List<PersonalAppEcosystem> ecosystems = new ArrayList<>();
  public static AppConfiguration totalConfiguration;

  private static final int NUMBER_OF_TRACKS = 10;

  @Override
  public void addPhysicalObject(App object, Double radius) {
    super.addPhysicalObject(object, radius);
    physicalRelations.put(object, new HashMap<>());
  }

  /**.
   * build the system using the configuration object
   *
   * @param configuration the configuration object
   */
  @Override
  public void buildFromConfiguration(Configuration configuration) {
    super.checkRep();
    AppConfiguration appConfiguration = (AppConfiguration) configuration;
    addCentralObject(appConfiguration.getUser());
    Map<App, Integer> radiusMap = new HashMap<>();
    Map<App, Integer> timeMap = new HashMap<>();
    Map<App, Integer> durationMap = new HashMap<>();
    Map<String, App> appNameMap = new HashMap<>();
    Set<App> allApps = appConfiguration.getApps();
    for (App app : allApps) {
      appNameMap.put(app.getName(), app);
      radiusMap.put(app, -1);
      timeMap.put(app, 0);
      durationMap.put(app, 0);
    }
    for (UsageLog usageLog : appConfiguration.getUsageLogs()) {
      App app = appNameMap.get(usageLog.getName());
      timeMap.put(app, timeMap.get(app) + 1);
      durationMap.put(app, durationMap.get(app) + usageLog.getDuration());
    }
    int maxValue = 0;
    for (Map.Entry<String, App> entry : appNameMap.entrySet()) {
      App app = entry.getValue();
      int tempValue = 0;
      if (timeMap.get(app) != null) {
        tempValue = timeMap.get(app) * durationMap.get(app);
      }
      radiusMap.put(app, tempValue);
      if (maxValue < tempValue) {
        maxValue = tempValue;
      }
    }
    for (int i = 1; i <= NUMBER_OF_TRACKS; i++) {
      addTrack((double) i);
    }
    for (Map.Entry<App, Integer> entry : radiusMap.entrySet()) {
      int radius;
      if (entry.getValue() == -1) {
        radius = NUMBER_OF_TRACKS;
      } else {
        radius = (int) ((1 - entry.getValue().doubleValue() / maxValue) * NUMBER_OF_TRACKS) + 1;
        radius = radius == NUMBER_OF_TRACKS + 1 ? NUMBER_OF_TRACKS : radius;
      }
      addPhysicalObject(entry.getKey(), (double) radius);
    }
    for (Relation relation : appConfiguration.getRelations()) {
      App app1 = appNameMap.get(relation.getAppName1());
      App app2 = appNameMap.get(relation.getAppName2());
      if (app1 != null && app2 != null) {
        addRelation(app1, app2, 0D);
      }
    }
  }

  @Override
  public App getObject(String name) {
    super.checkRep();
    for (App app : getPhysicalObjects()) {
      if (name.equals(app.getName())) {
        return app;
      }
    }
    return null;
  }

  @Override
  public void removePhysicalObject(App app) {
    super.removePhysicalObject(app);
    physicalRelations.remove(app);
    for (Map.Entry<App, Map<App, Double>> entry : physicalRelations.entrySet()) {
      entry.getValue().remove(app);
    }
    centralRelations.remove(app);
  }

}
