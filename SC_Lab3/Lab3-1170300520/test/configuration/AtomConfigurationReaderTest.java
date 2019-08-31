package configuration;

import java.io.File;

import org.junit.Test;

public class AtomConfigurationReaderTest {
	
	@Test
	public void readFileTest() {
		AtomConfigurationReader reader = new AtomConfigurationReader(new File("src/applications/configurations/AtomicStructure.txt"));
		reader.readFile();
	}
	
}
