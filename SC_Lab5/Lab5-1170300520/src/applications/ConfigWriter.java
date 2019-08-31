package applications;

import centralobject.Stellar;
import circularorbit.AtomStructure;
import circularorbit.CircularOrbit;
import circularorbit.PersonalAppEcosystem;
import circularorbit.StellarSystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import configuration.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otherbean.InstallLog;
import otherbean.Relation;
import otherbean.UninstallLog;
import otherbean.UsageLog;
import physicalobject.App;
import physicalobject.Electron;
import physicalobject.Planet;
import position.AbstractPosition;

/**.
 * Write class to file
 *
 * @author Guo Ziyang
 */
public class ConfigWriter {

  private static Logger logger = LoggerFactory.getLogger(ConfigWriter.class);

  /**.
   * Common write to file
   *
   * @param circularOrbit the system to be written
   */
  public static void writeToFile(CircularOrbit circularOrbit) {
    if (circularOrbit instanceof StellarSystem) {
      writeStellarSystem(circularOrbit);
    } else if (circularOrbit instanceof AtomStructure) {
      writeAtomicStructure(circularOrbit);
    } else {
      if (Application.readSuccess) {
        System.out.println("由于内存调优，暂时不支持PersonalAppEcosystem的写入");
        return;
        // writePersonalAppEcosystem(circularOrbit);
      } else {
        logger.error("Unknown error occurs");
        System.out.println("出现未知错误");
      }
    }
  }

  private static File createFile() {
    System.out.println("请输入文件路径：");
    Scanner scanner = new Scanner(System.in);
    while (true) {
      String filePath = scanner.nextLine();
      File file = new File(filePath);
      if (file.exists()) {
        System.out.println("文件已存在，请重新输入：");
      } else {
        return file;
      }
    }
  }

  private static void writeStellarSystem(CircularOrbit circularOrbit) {
    StellarSystem c = (StellarSystem) circularOrbit;
    File configFile = createFile();
    logger.info("ready to save to file {}", configFile.getName());
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
      Stellar stellar = c.getCentralObject();
      writer.write("Stellar ::= <" + stellar.getName() + ","
              + number2String(stellar.getRadius()) + ","
              + number2String(stellar.getWeight()) + ">");
      writer.newLine();
      Set<Planet> planets = c.getPhysicalObjects();
      for (Planet planet : planets) {
        writer.write("Planet ::= <" + planet.getName() + ","
                + planet.getState() + ","
                + planet.getColor() + ","
                + number2String(planet.getRadius()) + ","
                + number2String(planet.getTrackRadius()) + ","
                + number2String(planet.getSpeed() * Math.PI * planet.getTrackRadius() / 180) + ","
                + (planet.getClockwise() ? "CW" : "CCW") + ","
                + number2String(planet.getStartAngle()) + ">");
        writer.newLine();
      }
      writer.flush();
    } catch (IOException e) {
      logger.error("Error writing to file {}", configFile.getName(), e);
      System.out.println("写入文件失败，请检查文件" + configFile.getName());
    }
  }

  private static void writeAtomicStructure(CircularOrbit circularOrbit) {
    AtomStructure c = (AtomStructure) circularOrbit;
    File configFile = createFile();
    logger.info("ready to save to file {}", configFile.getName());
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
      writer.write("ElementName ::= " + c.getElementName());
      writer.newLine();
      writer.write("NumberOfTracks ::= " + c.getTracks().size());
      writer.newLine();
      writer.write("NumberOfElectron ::= ");
      Map<Electron, AbstractPosition> positionMap = c.getPositions();
      Map<Integer, Integer> numberMap = new HashMap<>(10);
      for (Map.Entry<Electron, AbstractPosition> entry : positionMap.entrySet()) {
        int radius = entry.getValue().getTrack().getRadius().intValue();
        if (!numberMap.containsKey(radius)) {
          numberMap.put(radius, 0);
        }
        numberMap.put(radius, numberMap.get(radius) + 1);
      }
      List<Map.Entry<Integer, Integer>> numberList = new ArrayList<>(numberMap.entrySet());
      numberList.sort(Comparator.comparing((Map.Entry<Integer, Integer> o) -> o.getKey()));
      for (Map.Entry<Integer, Integer> entry : numberList) {
        writer.write(entry.getKey() + "/" + entry.getValue());
        if (numberList.indexOf(entry) != numberList.size() - 1) {
          writer.write(";");
        }
      }
      writer.flush();
    } catch (IOException e) {
      logger.error("Error writing to file {}", configFile.getName(), e);
      System.out.println("写入文件失败，请检查文件" + configFile.getName());
    }
  }

  private static void writePersonalAppEcosystem(CircularOrbit circularOrbit) {
    PersonalAppEcosystem c = (PersonalAppEcosystem) circularOrbit;
    File configFile = createFile();
    logger.info("ready to save to file {}", configFile.getName());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
      AppConfiguration configuration = PersonalAppEcosystem.totalConfiguration;
      writer.write("User ::= " + configuration.getUser().getName());
      writer.newLine();
      for (App app : configuration.getApps()) {
        writer.write("App ::= <" + app.getName() + "," + app.getCooperation()
                + "," + app.getVersion() + ",\"" + app.getDescription() + "\""
                + ",\"" + app.getDomain() + "\">");
        writer.newLine();
      }
      for (InstallLog installLog : configuration.getInstallLogs()) {
        writer.write("InstallLog ::= <" + sdf.format(installLog.getTime().getTime())
                + "," + installLog.getName() + ">");
        writer.newLine();
      }
      for (UsageLog usageLog : configuration.getUsageLogs()) {
        writer.write("UsageLog ::= <" + sdf.format(usageLog.getTime().getTime()) + ","
                + usageLog.getName() + "," + usageLog.getDuration() + ">");
        writer.newLine();
      }
      for (UninstallLog uninstallLog : configuration.getUninstallLogs()) {
        writer.write("UninstallLog ::= <" + sdf.format(uninstallLog.getTime().getTime())
                + "," + uninstallLog.getName() + ">");
        writer.newLine();
      }
      for (Relation relation : configuration.getRelations()) {
        writer.write("Relation ::= <" + relation.getAppName1()
                + "," + relation.getAppName2() + ">");
        writer.newLine();
      }
      writer.write("Period ::= ");
      switch (configuration.getPeriod()) {
        case AppConfiguration.HOUR:
          writer.write("Hour");
          break;
        case AppConfiguration.DAY:
          writer.write("Day");
          break;
        case AppConfiguration.WEEK:
          writer.write("Week");
          break;
        case AppConfiguration.MONTH:
          writer.write("Month");
          break;
        default:
          logger.error("Unrecognized period type: {}", configuration.getPeriod());
          System.out.println("写入文件错误：不支持的Period类型: " + configuration.getPeriod());
      }
    } catch (IOException e) {
      logger.error("Error writing to file {}", configFile.getName(), e);
      System.out.println("写入文件失败，请检查文件" + configFile.getName());
    }
  }

  private static String number2String(Double number) {
    int limit = 10000;
    if (number > limit) {
      String numString = number.toString();
      if (!numString.contains("E")) {
        int power;
        if (numString.contains(".")) {
          power = numString.split("\\.")[0].length() - 1;
          numString = numString.replace(".", "");
        } else {
          power = numString.length() - 1;
        }
        numString = deleteZeros(numString.substring(0, 1) + "."
                + numString.substring(1)) + "e" + power;
        return numString;
      } else {
        String[] splits = numString.split("E");
        numString = deleteZeros(splits[0]) + "e" + splits[1];
        return numString;
      }
    } else {
      String numString = String.format("%.3f", number);
      return deleteZeros(numString);
    }
  }

  private static String deleteZeros(String numString) {
    if (!numString.contains(".")) {
      return numString;
    } else {
      while (numString.split("\\.").length == 2) {
        if (numString.split("\\.")[1].endsWith("0")) {
          numString = numString.substring(0, numString.length() - 1);
        } else {
          break;
        }
      }
      if (numString.split("\\.").length == 1) {
        numString = numString.substring(0, numString.length() - 1);
      }
      return numString;
    }
  }

}
