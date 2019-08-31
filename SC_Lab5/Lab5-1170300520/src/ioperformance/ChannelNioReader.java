package ioperformance;

import configuration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class ChannelNioReader implements Reader {

  private ChannelLineReader reader;
  private File file;
  private Configuration configuration;
  private Parser parser;

  private long lineNumber = 0;

  private Logger logger = LoggerFactory.getLogger(ChannelNioReader.class);

  public ChannelNioReader(File file, Parser parser) {
    this.file = file;
    this.parser = parser;
  }

  @Override
  public Configuration readFile() {
    if (!file.exists()) {
      logger.error("file does not exist", new FileNotFoundException(file.getName()));
      return null;
    }
    reader = new ChannelLineReader(file);
    if (parser instanceof StellarParser) {
      configuration = new StellarConfiguration();
      StellarParser.planetNameSet.clear();
    } else if (parser instanceof AtomParser) {
      configuration = new AtomConfiguration();
    } else if (parser instanceof AppParser) {
      AppParser.appNameSet.clear();
      configuration = new AppConfiguration();
    }
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        lineNumber++;
        if(!"".equals(line.trim())) {
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
