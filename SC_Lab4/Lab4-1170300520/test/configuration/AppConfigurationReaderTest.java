package configuration;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class AppConfigurationReaderTest {
	@Test
	public void readFileTest() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(
				new File("src/applications/configurations/PersonalAppEcosystem.txt"));
		reader.readFile();
	}

}
