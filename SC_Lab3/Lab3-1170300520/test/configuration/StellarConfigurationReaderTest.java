package configuration;

import java.io.File;

import org.junit.Test;

public class StellarConfigurationReaderTest {
	
	@Test
	public void readFileTest() {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/StellarSystem.txt"));
		reader.readFile();
	}
	
}
