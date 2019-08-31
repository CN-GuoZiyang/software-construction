package ioperformance;

import configuration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class BufferedIoReader implements Reader {

  private File file;
  private Configuration configuration;
  private Parser parser;

  private long lineNumber = 0;

  private Logger logger = LoggerFactory.getLogger(BufferedIoReader.class);

  public BufferedIoReader(File file, Parser parser) {
    this.file = file;
    this.parser = parser;
  }

  @Override
  public Configuration readFile() throws IOException {
    if (!file.exists()) {
      logger.error("file does not exist", new FileNotFoundException(file.getName()));
      return null;
    }
    if (parser instanceof StellarParser) {
      configuration = new StellarConfiguration();
      StellarParser.planetNameSet.clear();
    } else if (parser instanceof AtomParser) {
      configuration = new AtomConfiguration();
    } else if (parser instanceof AppParser) {
      AppParser.appNameSet.clear();
      configuration = new AppConfiguration();
    }
    try {
      BufferedReader reader = new BufferedReader(
              new InputStreamReader(new FileInputStream(file)));
      String line;
      while ((line = reader.readLine()) != null) {
        lineNumber++;
        if (!"".equals(line.trim())) {
          parser.parseConfiguration(line, configuration, lineNumber, file.getName());
        }
      }
      reader.close();
    } catch (IOException e) {
      logger.error("error happened when parsing configuration file", e);
      throw e;
    }
    return configuration;
  }
}
