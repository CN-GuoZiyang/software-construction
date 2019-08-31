package configuration;

import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class AtomConfigurationReaderTest {

  @Test
  public void readFileTest() throws IOException {
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/AtomicStructure.txt"), parser);
    reader.readFile();
  }

}
