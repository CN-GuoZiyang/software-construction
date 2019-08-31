package factory;

import circularorbit.AtomStructure;
import circularorbit.PersonalAppEcosystem;
import configuration.*;
import configuration.exception.ConfigurationDependencyException;

import java.io.File;
import java.io.IOException;

import java.util.*;

import ioperformance.BufferedIoReader;
import ioperformance.ChannelNioReader;
import ioperformance.MappedNioReader;
import ioperformance.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otherbean.AbstractLog;
import otherbean.InstallLog;
import otherbean.UninstallLog;
import otherbean.UsageLog;
import physicalobject.App;

/**.
 * the factory building the non-position-sensitive circular orbit
 *
 * @author Guo Ziyang
 */
public class OrbitWithoutPositionFactory {

  private Logger logger = LoggerFactory.getLogger(OrbitWithoutPositionFactory.class);

  /**.
   * build an atom structure using a name
   *
   * @param name the name of the atom
   * @return the atom structure object
   */
  public AtomStructure buildAtomStructure(String name) {
    logger.info("build a AtomStructure with name {}", name);
    return new AtomStructure(name);
  }

  /**.
   * build an atom structure via the configuration file
   *
   * @param file the configuration file
   * @return the atom structure object
   */
  public AtomStructure buildAtomStructure(File file) throws IOException {
    logger.info("build a AtomStructure with file {}", file.getAbsolutePath());
    Parser parser = new AtomParser();
    Reader reader = chooseStrategy(file, parser);
    return buildAtomStructure(reader);
  }

  public AtomStructure buildAtomStructure(Reader reader) throws IOException {
    return new AtomStructure(reader.readFile());
  }

  /**.
   * build a personal app ecosystem using a file
   *
   * @param file the config file
   * @throws IOException some io exception
   */
  public void buildPersonalAppEcosystem(File file) throws IOException {
    Parser parser = new AppParser();
    Reader reader = chooseStrategy(file, parser);
    buildPersonalAppEcosystem(reader);
  }

  /**.
   * build a personal app ecosystem via a reader
   *
   * @param reader the reader
   */
  public void buildPersonalAppEcosystem(Reader reader) throws IOException {
    AppConfiguration appConfiguration = (AppConfiguration) reader.readFile();
    //PersonalAppEcosystem.totalConfiguration = totalConfiguration;
    Calendar earliestTime = null;
    Calendar latestTime = null;
    for (UsageLog usageLog : appConfiguration.getUsageLogs()) {
      if (usageLog != null) {
        earliestTime = usageLog.getTime();
        latestTime = usageLog.getTime();
        break;
      }
    }
    for (InstallLog installLog : appConfiguration.getInstallLogs()) {
      if (installLog.getTime().before(earliestTime)) {
        earliestTime = installLog.getTime();
      }
      if (installLog.getTime().after(latestTime)) {
        latestTime = installLog.getTime();
      }
    }
    for (UsageLog usageLog : appConfiguration.getUsageLogs()) {
      if (usageLog.getTime().before(earliestTime)) {
        earliestTime = usageLog.getTime();
      }
      if (usageLog.getTime().after(latestTime)) {
        latestTime = usageLog.getTime();
      }
    }
    for (UninstallLog uninstallLog : appConfiguration.getUninstallLogs()) {
      if (uninstallLog.getTime().before(earliestTime)) {
        earliestTime = uninstallLog.getTime();
      }
      if (uninstallLog.getTime().after(latestTime)) {
        latestTime = uninstallLog.getTime();
      }
    }
    PersonalAppEcosystem.ecosystems.clear();
    Map<String, App> appNameMap = new HashMap<>();
    Map<String, Boolean> uninstalled = new HashMap<>();
    for (App app : appConfiguration.getApps()) {
      appNameMap.put(app.getName(), app);
      uninstalled.put(app.getName(), false);
    }
    List<AppConfiguration> configurations = new ArrayList<>();
    Set<App> currentApps = new HashSet<>();
    List<Calendar> timeList = new ArrayList<>();
    Map<Calendar, List<AbstractLog>> timeLogs = new HashMap<>();
    Calendar start = earliestTime;
    do {
      assert start != null;
      Calendar current = (Calendar) start.clone();
      timeLogs.put(current, new ArrayList<>());
      switch (appConfiguration.getPeriod()) {
        case AppConfiguration.HOUR:
          start.add(Calendar.HOUR, 1);
          break;
        case AppConfiguration.DAY:
          start.add(Calendar.DATE, 1);
          break;
        case AppConfiguration.WEEK:
          start.add(Calendar.DATE, 7);
          break;
        case AppConfiguration.MONTH:
          start.add(Calendar.MONTH, 1);
          break;
        default:
          logger.error("Unknown error occors!", new RuntimeException());
          return;
      }
      timeList.add(current);
    } while (!start.after(latestTime));
    for (InstallLog installLog : appConfiguration.getInstallLogs()) {
      boolean done = false;
      for (int i = 0; i < timeList.size(); i++) {
        if (timeList.get(i).after(installLog.getTime())) {
          timeLogs.get(timeList.get(i - 1)).add(installLog);
          done = true;
          break;
        }
      }
      if (!done) {
        timeLogs.get(timeList.get(timeList.size() - 1)).add(installLog);
      }
    }
    for (UsageLog usageLog : appConfiguration.getUsageLogs()) {
      boolean done = false;
      for (int i = 0; i < timeList.size(); i++) {
        if (timeList.get(i).after(usageLog.getTime())) {
          timeLogs.get(timeList.get(i - 1)).add(usageLog);
          done = true;
          break;
        }
      }
      if (!done) {
        timeLogs.get(timeList.get(timeList.size() - 1)).add(usageLog);
      }
    }
    for (UninstallLog uninstallLog : appConfiguration.getUninstallLogs()) {
      boolean done = false;
      for (int i = 0; i < timeList.size(); i++) {
        if (timeList.get(i).after(uninstallLog.getTime())) {
          timeLogs.get(timeList.get(i - 1)).add(uninstallLog);
          done = true;
          break;
        }
      }
      if (!done) {
        timeLogs.get(timeList.get(timeList.size() - 1)).add(uninstallLog);
      }
    }
    for (Map.Entry<Calendar, List<AbstractLog>> entry : timeLogs.entrySet()) {
      List<AbstractLog> list = entry.getValue();
      list.sort((o1, o2) -> {
        if (o1.getTime().before(o2.getTime())) {
          return -1;
        } else if (o1.getTime().after(o2.getTime())) {
          return 1;
        } else {
          return 0;
        }
      });
    }
    List<Map.Entry<Calendar, List<AbstractLog>>> entryList = new ArrayList<>(timeLogs.entrySet());
    entryList.sort((o1, o2) -> {
      if (o1.getKey().before(o2.getKey())) {
        return -1;
      } else if (o1.getKey().after(o2.getKey())) {
        return 1;
      } else {
        return 0;
      }
    });
    for (Map.Entry<Calendar, List<AbstractLog>> entry : entryList) {
      AppConfiguration configuration = new AppConfiguration();
      configuration.setUser(appConfiguration.getUser());
      configuration.getRelations().addAll(appConfiguration.getRelations());
      List<AbstractLog> timeLogList = entry.getValue();
      Set<App> appToRemove = new HashSet<>();
      for (AbstractLog timeLog : timeLogList) {
        if (timeLog instanceof InstallLog) {
          configuration.addInstallLog((InstallLog) timeLog);
          currentApps.add(appNameMap.get(((InstallLog) timeLog).getName()));
          uninstalled.put(((InstallLog) timeLog).getName(), false);
        } else if (timeLog instanceof UsageLog) {
          if (uninstalled.get(((UsageLog) timeLog).getName())) {
            throw new ConfigurationDependencyException(
                    "App '" + ((UsageLog) timeLog).getName() + "' does not exist", null, null);
          }
          configuration.addUsageLog((UsageLog) timeLog);
          currentApps.add(appNameMap.get(((UsageLog) timeLog).getName()));
        } else {
          configuration.addUninstallLog((UninstallLog) timeLog);
          appToRemove.add(appNameMap.get(((UninstallLog) timeLog).getName()));
          uninstalled.put(((UninstallLog) timeLog).getName(), true);
        }
      }
      configuration.getApps().addAll(new HashSet<>(currentApps));
      configurations.add(configuration);
      currentApps.removeAll(appToRemove);
    }
    for (AppConfiguration configuration : configurations) {
      PersonalAppEcosystem appEcosystem = new PersonalAppEcosystem();
      appEcosystem.buildFromConfiguration(configuration);
      PersonalAppEcosystem.ecosystems.add(appEcosystem);
    }
    appConfiguration = null;
    System.gc();
  }

  /**.
   * let the user to choose the reading strategy
   *
   * @param file the file
   * @param parser the specified parser
   * @return the reader constructed
   */
  public static Reader chooseStrategy(File file, Parser parser) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("1. BufferedReader");
    System.out.println("2. NIO Channel");
    System.out.println("3. NIO Mapped Buffer");
    System.out.print("请选择读取策略：");
    int choice = 4;
    while (choice > 3) {
      choice = scanner.nextInt();
      if (choice <= 3) {
        break;
      }
      System.out.print("无此选项，请重新选择：");
    }
    switch (choice) {
      case 1:
        return new BufferedIoReader(file, parser);
      case 2:
        return new ChannelNioReader(file, parser);
      case 3:
        return new MappedNioReader(file, parser);
      default:
        System.out.println("异常错误！");
        return null;
    }
  }

}
