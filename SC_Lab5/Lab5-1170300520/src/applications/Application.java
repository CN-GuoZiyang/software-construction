package applications;

import apis.CircularOrbitApis;
import apis.CircularOrbitHelper;
import apis.Difference;
import centralobject.AbstractCentralObject;
import circularorbit.AtomStructure;
import circularorbit.CircularOrbit;
import circularorbit.PersonalAppEcosystem;
import circularorbit.StellarSystem;
import configuration.exception.ConfigurationException;
import factory.AppFactory;
import factory.ElectronFactory;
import factory.OrbitWithPositionFactory;
import factory.OrbitWithoutPositionFactory;
import factory.PlanetFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import physicalobject.AbstractPhysicalObject;
import physicalobject.App;
import physicalobject.Electron;
import physicalobject.Planet;
import track.Track;

/**
 * .
 * the application of the project
 *
 * @author Guo Ziyang
 */
public class Application {

  private static Scanner scanner = new Scanner(System.in, "UTF-8");

  private static
  CircularOrbit<? extends AbstractCentralObject, ? extends AbstractPhysicalObject>
          circularOrbit;
  private static CircularOrbitApis<AbstractCentralObject, AbstractPhysicalObject> circularOrbitAPIs
          = new CircularOrbitApis<>();
  private static int typeChoice = 4;

  public static boolean readSuccess = false;

  public static final Logger logger = LoggerFactory.getLogger(Application.class);

  public static boolean logOn = false;

  private static final int STELLAR_SYSTEM_TYPE = 1;
  private static final int ATOM_STRUCTURE_TYPE = 2;
  private static final int PERSONAL_APP_ECOSYSTEM_TYPE = 3;

  /**
   * .
   * the main method of the application
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    System.out.println("是否要将日志输出到文件（影响日志查询）（yes or no）：");
    String choice = scanner.nextLine();
    switch (choice) {
      case "yes":
        logOn = true;
        break;
      case "no":
        logOn = false;
        break;
      default:
        System.out.println("选择错误，默认打开日志系统");
        logOn = true;
        break;
    }
    if (logOn) {
      LogReader.createAppender();
    }
    System.out.println("请选择轨道系统类型：");
    System.out.println("1. Stellar System");
    System.out.println("2. Atom Structure");
    System.out.println("3. Personal App Ecosystem");
    while (typeChoice > PERSONAL_APP_ECOSYSTEM_TYPE) {
      System.out.print("请选择：");
      typeChoice = scanner.nextInt();
    }
    while (true) {
      menu();
    }
  }

  @SuppressWarnings("unchecked")
  private static void menu() {
    System.out.println();
    System.out.println("1 . 从文件中创建系统");
    System.out.println("2 . 可视化当前系统");
    System.out.println("3 . 添加轨道");
    System.out.println("4 . 添加物体");
    System.out.println("5 . 删除物体");
    System.out.println("6 . 删除轨道");
    System.out.println("7 . 计算系统的熵");
    switch (typeChoice) {
      case STELLAR_SYSTEM_TYPE:
        System.out.println("8 . 计算t时刻行星位置");
        System.out.println("9 . 计算恒星与行星的物理距离");
        System.out.println("10. 计算行星之间的物理距离");
        System.out.println("11. 日志查询");
        System.out.println("12. 输出到文件");
        System.out.println("13. 退出");
        break;
      case ATOM_STRUCTURE_TYPE:
        System.out.println("8 . 电子跃迁");
        System.out.println("9 . 日志查询");
        System.out.println("10. 输出到文件");
        System.out.println("11. 退出");
        break;
      case PERSONAL_APP_ECOSYSTEM_TYPE:
        System.out.println("8 . 不同时间段轨道差异");
        System.out.println("9 . 计算App的逻辑距离");
        System.out.println("10. 日志查询");
        System.out.println("11. 输出到文件");
        System.out.println("12. 退出");
        break;
      default:
        logger.error("Unknown error occors!");
        System.exit(1);
    }
    System.out.print("请输入选择：");
    int choice = scanner.nextInt();
    scanner = new Scanner(System.in, "UTF-8");
    switch (choice) {
      case 1:
        System.out.println("请输入文件路径：");
        while (true) {
          try {
            String filePath = scanner.nextLine();
            File configFile = new File(filePath);
            if (!configFile.exists()) {
              logger.error("configuration file {} does not exist", filePath);
              System.out.println("配置文件不存在，请重新输入：");
              continue;
            }
            long start;
            long end;
            switch (typeChoice) {
              case STELLAR_SYSTEM_TYPE:
                start = Calendar.getInstance().getTimeInMillis();
                OrbitWithPositionFactory orbitWithPositionFactory = new OrbitWithPositionFactory();
                circularOrbit = orbitWithPositionFactory.buildStellarSystem(configFile);
                end = Calendar.getInstance().getTimeInMillis();
                System.out.println("构建系统完成，耗时" + (end - start) / 1000.0 + "秒");
                break;
              case ATOM_STRUCTURE_TYPE:
                start = Calendar.getInstance().getTimeInMillis();
                OrbitWithoutPositionFactory orbitWithoutPositionFactory
                        = new OrbitWithoutPositionFactory();
                circularOrbit = orbitWithoutPositionFactory.buildAtomStructure(configFile);
                end = Calendar.getInstance().getTimeInMillis();
                System.out.println("构建系统完成，耗时" + (end - start) / 1000.0 + "秒");
                break;
              case PERSONAL_APP_ECOSYSTEM_TYPE:
                start = Calendar.getInstance().getTimeInMillis();
                OrbitWithoutPositionFactory orbitWithoutPositionFactory2
                        = new OrbitWithoutPositionFactory();
                orbitWithoutPositionFactory2.buildPersonalAppEcosystem(configFile);
                end = Calendar.getInstance().getTimeInMillis();
                System.out.println("构建系统完成，耗时" + (end - start) / 1000.0 + "秒");
                break;
              default:
                logger.error("Unknown error occurs!", new RuntimeException());
                break;
            }
            readSuccess = true;
            break;
          } catch (ConfigurationException e1) {
            e1.printStackTrace();
            System.out.println("配置格式错误，请重新输入文件路径：");
          } catch (IOException e2) {
            e2.printStackTrace();
            System.out.println("读取文件错误，请重新输入文件路径：");
          }
        }
        break;
      case 2:
        if (!readSuccess) {
          System.out.println("请首先读取配置文件");
          break;
        }
        if (typeChoice == PERSONAL_APP_ECOSYSTEM_TYPE) {
          System.out.print("请选择时间段的索引：");
          int index = scanner.nextInt();
          circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
        }
        CircularOrbitHelper.visualize(circularOrbit);
        break;
      case 3:
        if (!readSuccess) {
          System.out.println("请首先读取配置文件");
          break;
        }
        if (typeChoice == PERSONAL_APP_ECOSYSTEM_TYPE) {
          System.out.println("请选择时间段索引：");
          int index = scanner.nextInt();
          circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
        }
        System.out.println("请输入轨道半径：");
        double radius = scanner.nextDouble();
        circularOrbit.addTrack(radius);
        break;
      case 4:
        if (!readSuccess) {
          System.out.println("请首先读取配置文件");
          break;
        }
        if (typeChoice == PERSONAL_APP_ECOSYSTEM_TYPE) {
          System.out.println("请选择时间段索引：");
          int index = scanner.nextInt();
          circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
        }
        switch (typeChoice) {
          case STELLAR_SYSTEM_TYPE:
            System.out.println("请按顺序输入名称,形态,颜色,行星半径,轨道半径,公转速度,公转方向,初始位置的角度，以英文逗号分割");
            String string = scanner.nextLine();
            String[] strings = string.split(",");
            PlanetFactory planetFactory = new PlanetFactory();
            Planet planet = planetFactory.build(strings[0], strings[1], strings[2],
                    number2Double(strings[3]),
                    number2Double(strings[4]),
                    (number2Double(strings[5]) * 180) / (number2Double(strings[4]) * Math.PI),
                    "CW".equals(strings[6]), number2Double(strings[7]));
            ((StellarSystem) circularOrbit).addPhysicalObject(planet, planet.getTrackRadius(),
                    planet.getStartAngle());
            break;
          case ATOM_STRUCTURE_TYPE:
            System.out.print("请输入轨道半径：");
            double atomRadius = scanner.nextDouble();
            ElectronFactory electronFactory = new ElectronFactory();
            Electron electron = electronFactory.build();
            ((AtomStructure) circularOrbit).addPhysicalObject(electron, atomRadius);
            break;
          case PERSONAL_APP_ECOSYSTEM_TYPE:
            System.out.println("请按顺序输入App名称,公司,版本,功能描述,业务领域，英文逗号分割");
            String string2 = scanner.nextLine();
            String[] strings2 = string2.split(",");
            AppFactory appFactory = new AppFactory();
            App app = appFactory.build(strings2[0], strings2[1], strings2[2],
                    strings2[3], strings2[4]);
            System.out.print("请输入轨道半径：");
            double appRadius = scanner.nextDouble();
            ((PersonalAppEcosystem) circularOrbit).addPhysicalObject(app, appRadius);
            break;
          default:
            logger.error("Unknown error occurs", new RuntimeException());
            break;
        }
        break;
      case 5:
        if (!readSuccess) {
          System.out.println("请首先读取配置文件");
          break;
        }
        switch (typeChoice) {
          case STELLAR_SYSTEM_TYPE:
            System.out.println("请输入删除物体的名字：");
            String removeObjectName = scanner.next();
            AbstractPhysicalObject removeObject = circularOrbit.getObject(removeObjectName);
            ((CircularOrbit<?, ? super AbstractPhysicalObject>) circularOrbit).removePhysicalObject(
                    removeObject);
            break;
          case ATOM_STRUCTURE_TYPE:
            System.out.print("请输入要删除的电子的轨道半径：");
            double removeObjectRadius = scanner.nextDouble();
            Track removeObjectTrack = circularOrbit.getTrack(removeObjectRadius);
            List<? extends AbstractPhysicalObject> physicalObjects
                    = circularOrbit.getPhysicalObjects(removeObjectTrack);
            if (physicalObjects.isEmpty()) {
              System.out.println("当前轨道暂无电子");
              return;
            } else {
              physicalObjects.remove(0);
            }
            break;
          case PERSONAL_APP_ECOSYSTEM_TYPE:
            System.out.println("请选择时间段索引：");
            int index = scanner.nextInt();
            circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
            System.out.println("请输入要删除的App名称：");
            String removeAppName = scanner.next();
            AbstractPhysicalObject removeApp = circularOrbit.getObject(removeAppName);
            ((CircularOrbit<?, ? super AbstractPhysicalObject>) circularOrbit).removePhysicalObject(
                    removeApp);
            break;
          default:
            logger.error("Unknown error occurs", new RuntimeException());
            break;
        }
        break;
      case 6:
        if (!readSuccess) {
          System.out.println("请首先读取配置文件");
          break;
        }
        if (typeChoice == PERSONAL_APP_ECOSYSTEM_TYPE) {
          System.out.println("请选择时间段索引：");
          int index = scanner.nextInt();
          circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
        }
        System.out.print("请输入需要删除的轨道半径：");
        double trackRadius = scanner.nextDouble();
        circularOrbit.removeTrack(trackRadius);
        break;
      case 7:
        if (typeChoice == PERSONAL_APP_ECOSYSTEM_TYPE) {
          System.out.println("请选择时间段索引：");
          int index = scanner.nextInt();
          circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
        }
        System.out.println("该系统的熵为" + circularOrbitAPIs
                .getObjectDistributionEntropy((
                        CircularOrbit<AbstractCentralObject, AbstractPhysicalObject>) circularOrbit)
        );
        break;
      case 8:
        if (!readSuccess) {
          System.out.println("请首先读取配置文件");
          break;
        }
        switch (typeChoice) {
          case STELLAR_SYSTEM_TYPE:
            System.out.print("请输入时刻（s）：");
            int second = scanner.nextInt();
            System.out.print("请输入要查看的行星名称：");
            String planetName = scanner.next();
            Planet planet = (Planet) circularOrbit.getObject(planetName);
            double angle = planet.getStartAngle() + planet.getSpeed() * second;
            System.out.println(second + "秒后，该行星位于半径"
                    + planet.getTrackRadius() + "，角度" + angle + "处");
            break;
          case ATOM_STRUCTURE_TYPE:
            System.out.print("请输入电子所在轨道半径：");
            double fromRadius = scanner.nextDouble();
            Electron fromElectron = (Electron) circularOrbit
                    .getPhysicalObjects(circularOrbit.getTrack(fromRadius))
                    .get(0);
            System.out.print("请输入目标轨道半径：");
            double toRadius = scanner.nextDouble();
            ((AtomStructure) circularOrbit).transit(fromElectron, toRadius);
            break;
          case PERSONAL_APP_ECOSYSTEM_TYPE:
            System.out.print("请输入时间段1：");
            int time1 = scanner.nextInt();
            System.out.print("请输入时间段2：");
            int time2 = scanner.nextInt();
            PersonalAppEcosystem personalAppEcosystem1 = PersonalAppEcosystem.ecosystems.get(time1);
            PersonalAppEcosystem personalAppEcosystem2 = PersonalAppEcosystem.ecosystems.get(time2);
            Difference difference
                    = circularOrbitAPIs.getDifference(personalAppEcosystem1, personalAppEcosystem2);
            System.out.println(difference);
            break;
          default:
            logger.error("Unknown error occurs", new RuntimeException());
            break;
        }
        break;
      case 9:
        if (!readSuccess) {
          System.out.println("请首先读取配置文件");
          break;
        }
        switch (typeChoice) {
          case STELLAR_SYSTEM_TYPE:
            System.out.print("请输入行星名称：");
            String planetName = scanner.nextLine();
            Planet planet = (Planet) circularOrbit.getObject(planetName);
            System.out.println(planetName + "与恒星之间距离为" + planet.getTrackRadius());
            break;
          case ATOM_STRUCTURE_TYPE:
            searchLogs();
            break;
          case PERSONAL_APP_ECOSYSTEM_TYPE:
            System.out.print("请输入App1的名字：");
            String appName1 = scanner.next();
            System.out.print("请输入App2的名字：");
            String appName2 = scanner.next();
            App app1 = (App) circularOrbit.getObject(appName1);
            App app2 = (App) circularOrbit.getObject(appName2);
            int logicalDistance = circularOrbitAPIs.getLogicalDistance(circularOrbit, app1, app2);
            System.out.println("两个App的逻辑距离为" + logicalDistance);
            break;
          default:
            logger.error("Unknown error occurs", new RuntimeException());
            break;
        }
        break;
      case 10:
        if (!readSuccess) {
          System.out.println("请首先读取配置文件");
          break;
        }
        switch (typeChoice) {
          case STELLAR_SYSTEM_TYPE:
            System.out.print("请输入行星1的名字：");
            String planetName1 = scanner.next();
            Planet planet1 = (Planet) circularOrbit.getObject(planetName1);
            System.out.print("请输入行星2的名字：");
            String planetName2 = scanner.next();
            Planet planet2 = (Planet) circularOrbit.getObject(planetName2);
            double x1
                    = planet1.getTrackRadius() * Math.sin(planet1.getTrackRadius() * Math.PI / 180);
            double y1
                    = planet1.getTrackRadius() * Math.cos(planet1.getTrackRadius() * Math.PI / 180);
            double x2
                    = planet2.getTrackRadius() * Math.sin(planet2.getTrackRadius() * Math.PI / 180);
            double y2
                    = planet2.getTrackRadius() * Math.cos(planet2.getTrackRadius() * Math.PI / 180);
            double physicalDistance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
            System.out.println("两行星间的物理距离为" + physicalDistance);
            break;
          case ATOM_STRUCTURE_TYPE:
            ConfigWriter.writeToFile(circularOrbit);
            break;
          case PERSONAL_APP_ECOSYSTEM_TYPE:
            searchLogs();
            break;
          default:
            logger.error("Unknown error occurs", new RuntimeException());
            break;
        }
        break;
      case 11:
        switch (typeChoice) {
          case STELLAR_SYSTEM_TYPE:
            if (!readSuccess) {
              System.out.println("请首先读取配置文件");
              break;
            }
            searchLogs();
            break;
          case ATOM_STRUCTURE_TYPE:
            System.exit(0);
            break;
          case PERSONAL_APP_ECOSYSTEM_TYPE:
            if (!readSuccess) {
              System.out.println("请首先读取配置文件");
              break;
            }
            ConfigWriter.writeToFile(null);
            break;
          default:
            logger.error("Unknown error occurs", new RuntimeException());
            break;
        }
        break;
      case 12:
        switch (typeChoice) {
          case STELLAR_SYSTEM_TYPE:
            if (!readSuccess) {
              System.out.println("请首先读取配置文件");
              break;
            }
            ConfigWriter.writeToFile(circularOrbit);
            break;
          case ATOM_STRUCTURE_TYPE:
            System.out.println("无此选项！");
            break;
          case PERSONAL_APP_ECOSYSTEM_TYPE:
            System.exit(0);
            break;
          default:
            logger.error("Unknown error occurs", new RuntimeException());
            break;
        }
        break;
      case 13:
        switch (typeChoice) {
          case STELLAR_SYSTEM_TYPE:
            System.exit(0);
            break;
          case ATOM_STRUCTURE_TYPE:
          case PERSONAL_APP_ECOSYSTEM_TYPE:
            System.out.println("无此选项！");
            break;
          default:
            logger.error("Unknown error occurs", new RuntimeException());
            break;
        }
        break;
      default:
        System.out.println("无此选项！");
        break;
    }
  }

  private static void searchLogs() {
    if (!logOn) {
      System.out.println("未开启日志输出，无法查询日志！");
      return;
    }
    LogReader.readLog();
    System.out.println("1. 时间段");
    System.out.println("2. 日志类型");
    System.out.println("3. 日志产生类");
    System.out.println("4. 日志产生方法");
    System.out.println("5. 输出所有");
    System.out.print("请输入选择：");
    while (true) {
      int choice = scanner.nextInt();
      scanner = new Scanner(System.in);
      switch (choice) {
        case 1:
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
          System.out.println("请输入初始时间(yyyy-MM-dd HH:mm:ss): ");
          String date1String = null;
          Date date1;
          while (true) {
            try {
              date1String = scanner.nextLine();
              date1 = dateFormat.parse(date1String);
              break;
            } catch (ParseException e) {
              logger.error("Wrong format for date {}", date1String);
              System.out.println("日期格式错误，请重新输入：");
            }
          }
          System.out.println("请输入终止时间(yyyy-MM-dd HH:mm:ss): ");
          String date2String = null;
          Date date2;
          while (true) {
            try {
              date2String = scanner.nextLine();
              date2 = dateFormat.parse(date2String);
              break;
            } catch (ParseException e) {
              logger.error("Wrong format for date {}", date2String);
              System.out.println("日期格式错误，请重新输入：");
            }
          }
          System.out.println("\n" + LogReader.searchLogsByDate(date1, date2));
          break;
        case 2:
          System.out.print("请输入日志类型(INFO|WARN|DEBUG|TRACE|ERROR)：");
          while (true) {
            String typeString = scanner.nextLine();
            if (!"INFO".equals(typeString) && !"WARN".equals(typeString)
                    && !"DEBUG".equals(typeString)
                    && !"TRACE".equals(typeString)
                    && !"ERROR".equals(typeString)) {
              logger.error("Wrong type of log :{}", typeString);
              System.out.print("日志类型错误！请重新输入：");
            } else {
              System.out.println("\n" + LogReader.searchLogsByType(typeString));
              break;
            }
          }
          break;
        case 3:
          System.out.print("请输入日志的产生类名(简单类名): ");
          String className = scanner.nextLine();
          System.out.println("\n" + LogReader.searchLogsByClass(className));
          break;
        case 4:
          System.out.print("请输入日志的产生方法: ");
          String method = scanner.nextLine();
          System.out.println("\n" + LogReader.searchLogsByMethod(method));
          break;
        case 5:
          System.out.println(LogReader.allLogs());
          break;
        default:
          System.out.println("输入有误，请重新选择：");
          continue;
      }
      break;
    }
  }

  private static Double number2Double(String numberStr) {
    String exponent = "e";

    if (!numberStr.contains(exponent)) {
      return Double.valueOf(numberStr);
    } else {
      BigDecimal bd = new BigDecimal(numberStr);
      return bd.doubleValue();
    }
  }

}
