package configuration;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class AtomConfigurationReaderTest {

	@Test
	public void readFileTest() throws IOException {
		AtomConfigurationReader reader = new AtomConfigurationReader(
				new File("src/applications/configurations/AtomicStructure.txt"));
		reader.readFile();
	}

}
