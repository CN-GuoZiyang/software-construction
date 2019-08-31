package applications;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import APIs.Difference;
import centralObject.CentralObject;
import circularOrbit.AtomStructure;
import circularOrbit.CircularOrbit;
import circularOrbit.PersonalAppEcosystem;
import circularOrbit.StellarSystem;
import factory.AppFactory;
import factory.ElectronFactory;
import factory.OrbitWithPositionFactory;
import factory.OrbitWithoutPositionFactory;
import factory.PlanetFactory;
import physicalObject.App;
import physicalObject.Electron;
import physicalObject.PhysicalObject;
import physicalObject.Planet;
import track.Track;

/**
 * the application of the project
 * 
 * @author Guo Ziyang
 */
public class Application {

	private static Scanner scanner = new Scanner(System.in);

	private static CircularOrbit<? extends CentralObject, ? extends PhysicalObject> circularOrbit;
	private static CircularOrbitAPIs<CentralObject, PhysicalObject> circularOrbitAPIs = new CircularOrbitAPIs<>();
	private static int typeChoice = 4;

	public static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		System.out.println("请选择轨道系统类型：");
		System.out.println("1. Stellar System");
		System.out.println("2. Atom Structure");
		System.out.println("3. Personal App Ecosystem");
		while(typeChoice > 3) {
			System.out.print("请选择：");
			typeChoice = scanner.nextInt();
		}
		while (true) {
			menu();
		}
	}

	@SuppressWarnings("unchecked")
	private static void menu() {
		System.out.println("1 . 从文件中创建系统");
		System.out.println("2 . 可视化当前系统");
		System.out.println("3 . 添加轨道");
		System.out.println("4 . 添加物体");
		System.out.println("5 . 删除物体");
		System.out.println("6 . 删除轨道");
		System.out.println("7 . 计算系统的熵");
		switch (typeChoice) {
		case 1:
			System.out.println("8 . 计算t时刻行星位置");
			System.out.println("9 . 计算恒星与行星的物理距离");
			System.out.println("10. 计算行星之间的物理距离");
			break;
		case 2:
			System.out.println("8 . 电子跃迁");
			break;
		case 3:
			System.out.println("8 . 不同时间段轨道差异");
			System.out.println("9 . 计算App的逻辑距离");
			break;
		}
		System.out.print("请输入选择：");
		int choice = scanner.nextInt();
		scanner = new Scanner(System.in);
		switch (choice) {
		case 1:
			System.out.println("请输入文件路径：");
			String filePath = scanner.nextLine();
			switch (typeChoice) {
			case 1:
				OrbitWithPositionFactory orbitWithPositionFactory = new OrbitWithPositionFactory();
				circularOrbit = orbitWithPositionFactory.buildStellarSystem(new File(filePath));
				break;
			case 2:
				OrbitWithoutPositionFactory orbitWithoutPositionFactory = new OrbitWithoutPositionFactory();
				circularOrbit = orbitWithoutPositionFactory.buildAtomStructure(new File(filePath));
				break;
			case 3:
				OrbitWithoutPositionFactory orbitWithoutPositionFactory2 = new OrbitWithoutPositionFactory();
				orbitWithoutPositionFactory2.buildPersonalAppEcosystem(new File(filePath));
				break;
			}
			break;
		case 2:
			if (typeChoice == 3) {
				System.out.print("请选择时间段的索引：");
				int index = scanner.nextInt();
				circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
			}
			CircularOrbitHelper.visualize(circularOrbit);
			break;
		case 3:
			if (typeChoice == 3) {
				System.out.println("请选择时间段索引：");
				int index = scanner.nextInt();
				circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
			}
			System.out.println("请输入轨道半径：");
			double radius = scanner.nextDouble();
			circularOrbit.addTrack(radius);
			break;
		case 4:
			if (typeChoice == 3) {
				System.out.println("请选择时间段索引：");
				int index = scanner.nextInt();
				circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
			}
			switch (typeChoice) {
			case 1:
				System.out.println("请按顺序输入名称,形态,颜色,行星半径,轨道半径,公转速度,公转方向,初始位置的角度，以英文逗号分割");
				String string = scanner.nextLine();
				String[] strings = string.split(",");
				PlanetFactory planetFactory = new PlanetFactory();
				Planet planet = planetFactory.build(strings[0], strings[1], strings[2], number2Double(strings[3]),
						number2Double(strings[4]),
						(number2Double(strings[5]) * 180) / (number2Double(strings[4]) * Math.PI),
						"CW".equals(strings[6]), number2Double(strings[7]));
				((StellarSystem) circularOrbit).addPhysicalObject(planet, planet.getTrackRadius(),
						planet.getStartAngle());
				break;
			case 2:
				System.out.print("请输入轨道半径：");
				double atomRadius = scanner.nextDouble();
				ElectronFactory electronFactory = new ElectronFactory();
				Electron electron = electronFactory.build();
				((AtomStructure) circularOrbit).addPhysicalObject(electron, atomRadius);
				break;
			case 3:
				System.out.println("请按顺序输入App名称,公司,版本,功能描述,业务领域，英文逗号分割");
				String string2 = scanner.nextLine();
				String[] strings2 = string2.split(",");
				AppFactory appFactory = new AppFactory();
				App app = appFactory.build(strings2[0], strings2[1], strings2[2], strings2[3], strings2[4]);
				System.out.print("请输入轨道半径：");
				double appRadius = scanner.nextDouble();
				((PersonalAppEcosystem) circularOrbit).addPhysicalObject(app, appRadius);
				break;
			}
			break;
		case 5:
			if (typeChoice == 3) {
				System.out.println("请选择时间段索引：");
				int index = scanner.nextInt();
				circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
			}
			if (typeChoice == 2) {
				System.out.print("请输入要删除的电子的轨道半径：");
				double removeObjectRadius = scanner.nextDouble();
				Track removeObjectTrack = circularOrbit.getTrack(removeObjectRadius);
				List<? extends PhysicalObject> physicalObjects = circularOrbit.getPhysicalObjects(removeObjectTrack);
				if (physicalObjects.isEmpty()) {
					System.out.println("当前轨道暂无电子");
					return;
				} else {
					physicalObjects.remove(0);
				}
			} else {
				System.out.println("请输入删除物体的名字：");
				String removeObjectName = scanner.next();
				PhysicalObject removeObject = circularOrbit.getObject(removeObjectName);
				((CircularOrbit<?, ? super PhysicalObject>) circularOrbit).removePhysicalObject(removeObject);
			}
			break;
		case 6:
			if (typeChoice == 3) {
				System.out.println("请选择时间段索引：");
				int index = scanner.nextInt();
				circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
			}
			System.out.print("请输入需要删除的轨道半径：");
			double trackRadius = scanner.nextDouble();
			circularOrbit.removeTrack(trackRadius);
			break;
		case 7:
			if (typeChoice == 3) {
				System.out.println("请选择时间段索引：");
				int index = scanner.nextInt();
				circularOrbit = PersonalAppEcosystem.ecosystems.get(index);
			}
			System.out.println("该系统的熵为" + circularOrbitAPIs
					.getObjectDistributionEntropy((CircularOrbit<CentralObject, PhysicalObject>) circularOrbit));
			break;
		case 8:
			switch (typeChoice) {
			case 1:
				System.out.print("请输入时刻（s）：");
				int second = scanner.nextInt();
				System.out.print("请输入要查看的行星名称：");
				String planetName = scanner.next();
				Planet planet = (Planet) circularOrbit.getObject(planetName);
				Double angle = planet.getStartAngle() + planet.getSpeed() * second;
				System.out.println(second + "秒后，该行星位于半径" + planet.getTrackRadius() + "，角度" + angle + "处");
				break;
			case 2:
				System.out.print("请输入电子所在轨道半径：");
				double fromRadius = scanner.nextDouble();
				Electron fromElectron = (Electron) circularOrbit.getPhysicalObjects(circularOrbit.getTrack(fromRadius))
						.get(0);
				System.out.print("请输入目标轨道半径：");
				double toRadius = scanner.nextDouble();
				((AtomStructure) circularOrbit).transit(fromElectron, toRadius);
				break;
			case 3:
				System.out.print("请输入时间段1：");
				int time1 = scanner.nextInt();
				System.out.print("请输入时间段2：");
				int time2 = scanner.nextInt();
				PersonalAppEcosystem personalAppEcosystem1 = PersonalAppEcosystem.ecosystems.get(time1);
				PersonalAppEcosystem personalAppEcosystem2 = PersonalAppEcosystem.ecosystems.get(time2);
				Difference difference = circularOrbitAPIs
						.getDifference(personalAppEcosystem1, personalAppEcosystem2);
				System.out.println(difference);
				break;
			}
			break;
		case 9:
			switch (typeChoice) {
			case 1:
				System.out.print("请输入行星名称：");
				String planetName = scanner.next();
				Planet planet = (Planet) circularOrbit.getObject(planetName);
				System.out.println(planetName + "与恒星之间距离为" + planet.getTrackRadius());
				break;
			case 3:
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
				System.out.println("无此选项！");
				break;
			}
		case 10:
			if (typeChoice == 1) {
				System.out.print("请输入行星1的名字：");
				String planetName1 = scanner.next();
				Planet planet1 = (Planet) circularOrbit.getObject(planetName1);
				System.out.print("请输入行星2的名字：");
				String planetName2 = scanner.next();
				Planet planet2 = (Planet) circularOrbit.getObject(planetName2);
				double x1 = planet1.getTrackRadius() * Math.sin(planet1.getTrackRadius() * Math.PI / 180);
				double y1 = planet1.getTrackRadius() * Math.cos(planet1.getTrackRadius() * Math.PI / 180);
				double x2 = planet2.getTrackRadius() * Math.sin(planet2.getTrackRadius() * Math.PI / 180);
				double y2 = planet2.getTrackRadius() * Math.cos(planet2.getTrackRadius() * Math.PI / 180);
				double physicalDistance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
				System.out.println("两行星间的物理距离为" + physicalDistance);
				break;
			} else {
				System.out.println("无此选项！");
				break;
			}
		default:
			System.out.println("无此选项！");
			break;
		}
	}

	private static Double number2Double(String numberStr) {
		if (!numberStr.contains("e")) {
			return Double.valueOf(numberStr);
		} else {
			BigDecimal bd = new BigDecimal(numberStr);
			return bd.doubleValue();
		}
	}

}
