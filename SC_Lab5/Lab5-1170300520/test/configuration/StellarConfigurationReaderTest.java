package configuration;

import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class StellarConfigurationReaderTest {

  @Test
  public void readFileTest() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/StellarSystem.txt"), parser);
    reader.readFile();
  }

}
