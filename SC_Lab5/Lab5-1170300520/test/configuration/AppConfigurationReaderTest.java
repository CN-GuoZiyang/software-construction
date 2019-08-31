package configuration;

import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class AppConfigurationReaderTest {
  @Test
  public void readFileTest() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/PersonalAppEcosystem.txt"), parser);
    reader.readFile();
  }

}
