package configuration;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class StellarConfigurationReaderTest {

	@Test
	public void readFileTest() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(
				new File("src/applications/configurations/StellarSystem.txt"));
		reader.readFile();
	}

}
