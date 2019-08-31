package configuration;

import configuration.exception.ConfigurationDependencyException;
import configuration.exception.ConfigurationLabelException;
import configuration.exception.ConfigurationSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtomParser implements Parser {

  private AtomConfiguration atomConfiguration;

  private long lineNumber;
  private String fileName;

  private static final String ELEMENT_NAME_START = "ElementName ::= ";
  private static final String NUMBER_OF_TRACKS_START = "NumberOfTracks ::= ";
  private static final String NUMBER_OF_ELECTRON_START = "NumberOfElectron ::= ";

  private static final Pattern ELEMENT_NAME_PATTERN
          = Pattern.compile("(?<=ElementName ::= )[A-Z][a-z]?$");
  private static final Pattern NUMBER_OF_TRACKS_PATTERN
          = Pattern.compile("(?<=NumberOfTracks ::= )\\d+$");
  private static final Pattern NUMBER_OF_ELECTRON_PATTERN
          = Pattern.compile("(?<=NumberOfElectron ::= )[\\d+/\\d+;?]+$");

  private Logger logger = LoggerFactory.getLogger(AtomParser.class);

  @Override
  public void parseConfiguration(String line, Configuration configuration, long lineNumber, String fileName) {
    this.lineNumber = lineNumber;
    this.fileName = fileName;
    atomConfiguration = (AtomConfiguration) configuration;
    if (line.startsWith(ELEMENT_NAME_START)) {
      if (atomConfiguration.getElementName() != null) {
        ConfigurationLabelException e
                = new ConfigurationLabelException(
                "Duplicate element name label", lineNumber, fileName);
        logger.error("Duplicate element name label", e);
        throw e;
      }
      Matcher elementNameMatcher = ELEMENT_NAME_PATTERN.matcher(line);
      if (elementNameMatcher.find()) {
        atomConfiguration.setElementName(elementNameMatcher.group());
      } else {
        ConfigurationSyntaxException e
                = new ConfigurationSyntaxException(
                "Wrong format of element name", lineNumber, fileName);
        logger.error("Wrong format of element name", e);
        throw e;
      }
    } else if (line.startsWith(NUMBER_OF_TRACKS_START)) {
      Matcher numberOfTracksMatcher = NUMBER_OF_TRACKS_PATTERN.matcher(line);
      if (numberOfTracksMatcher.find()) {
        atomConfiguration.setNumberOfTracks(Integer.valueOf(numberOfTracksMatcher.group()));
      } else {
        ConfigurationSyntaxException e
                = new ConfigurationSyntaxException(
                "Wrong format of tracks number", lineNumber, fileName);
        logger.error("Wrong format of tracks number", e);
        throw e;
      }
      if (atomConfiguration.getNumberOfElectron().size() != 0) {
        if (atomConfiguration.getNumberOfElectron().size() != atomConfiguration.getNumberOfTracks()) {
          ConfigurationDependencyException e
                  = new ConfigurationDependencyException(
                  "Dependency error: track number conflicts", lineNumber, fileName);
          logger.error("Dependency error: track number conflicts", e);
          throw e;
        }
      }
    } else if (line.startsWith(NUMBER_OF_ELECTRON_START)) {
      Matcher numberOfElectronMatcher = NUMBER_OF_ELECTRON_PATTERN.matcher(line);
      if (numberOfElectronMatcher.find()) {
        String[] strings = numberOfElectronMatcher.group().split(";");
        for (String string : strings) {
          atomConfiguration.getNumberOfElectron().put(Integer.valueOf(string.split("/")[0]),
                  Integer.valueOf(string.split("/")[1]));
        }
      } else {
        ConfigurationSyntaxException e
                = new ConfigurationSyntaxException(
                "Wrong format of electon number", lineNumber, fileName);
        logger.error("Wrong format of electron number", e);
        throw e;
      }
      if (atomConfiguration.getNumberOfTracks() != null) {
        if (atomConfiguration.getNumberOfTracks() != atomConfiguration.getNumberOfElectron().size()) {
          ConfigurationDependencyException e
                  = new ConfigurationDependencyException(
                  "Dependency error: track number conflicts", lineNumber, fileName);
          logger.error("Dependency error: track number conflicts", e);
          throw e;
        }
      }
    } else {
      String s = "Invalid label '" + line.substring(0, line.indexOf(" ")) + "'";
      ConfigurationSyntaxException e
              = new ConfigurationSyntaxException(s, lineNumber, fileName);
      logger.error(s, e);
      throw e;
    }
  }
}
