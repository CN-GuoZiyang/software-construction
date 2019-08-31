package ioperformance;

import configuration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MappedNioReader implements Reader {

  private File file;
  private Parser parser;
  private Configuration configuration;

  private long lineNumber = 0;

  private Logger logger = LoggerFactory.getLogger(MappedNioReader.class);

  public MappedNioReader(File file, Parser parser) {
    this.file = file;
    this.parser = parser;
  }

  @Override
  public Configuration readFile() {
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
    MappedLineReader reader = new MappedLineReader(file);
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        lineNumber++;
        if (!"".equals(line.trim())) {
          parser.parseConfiguration(line, configuration, lineNumber, file.getName());
        }
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return configuration;
  }
}
