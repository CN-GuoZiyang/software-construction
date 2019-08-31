package configuration;

import java.io.File;

import org.junit.Test;

import APIs.CircularOrbitHelper;
import circularOrbit.PersonalAppEcosystem;
import factory.OrbitWithoutPositionFactory;

public class AppConfigurationReaderTest {
	@Test
	public void readFileTest() {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/PersonalAppEcosystem.txt"));
		reader.readFile();
	}
	
	public static void main(String[] args) {
		OrbitWithoutPositionFactory factory = new OrbitWithoutPositionFactory();
		factory.buildPersonalAppEcosystem(new File("src/applications/configurations/PersonalAppEcosystem_Medium2.txt"));
		CircularOrbitHelper.visualize(PersonalAppEcosystem.ecosystems.get(2));
	}
}
