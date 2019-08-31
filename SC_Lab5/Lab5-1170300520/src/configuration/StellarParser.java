package configuration;

import configuration.exception.ConfigurationLabelException;
import configuration.exception.ConfigurationSyntaxException;
import factory.PlanetFactory;
import factory.StellarFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StellarParser implements Parser {

  private StellarConfiguration stellarConfiguration;

  private StellarFactory stellarFactory = new StellarFactory();
  private PlanetFactory planetFactory = new PlanetFactory();
  public static Set<String> planetNameSet = new HashSet<>();

  private long lineNumber;
  private String fileName;

  private static final String STELLAR_START = "Stellar ::= <";
  private static final String PLANET_START = "Planet ::= <";
  private static final String CLOCKWISE = "CW";
  private static final String COUNTERCLOCKWISE = "CCW";

  private static final Pattern STELLAR_PATTERN = Pattern.compile(
          "(?<=Stellar ::= <)[A-Za-z0-9]+,((\\d+[\\.]?\\d*e\\d+)|(\\d+[\\.]?\\d*)),"
                  + "((\\d+[\\.]?\\d*e\\d+)|(\\d+[\\.]?\\d*))(?=>)");
  private static final Pattern PLANET_PATTERN = Pattern.compile(
          "(?<=Planet ::= <)[A-Za-z0-9]+,[A-Za-z]+,[A-Za-z]+,"
                  + "(((\\d+[\\.]?\\d*e\\d+)|(\\d+[\\.]?\\d*)),){3}[A-Z]{2,3},\\d+[\\.]?\\d*(?=>)");


  private Logger logger = LoggerFactory.getLogger(StellarParser.class);

  @Override
  public void parseConfiguration(String line, Configuration configuration, long lineNumber, String fileName) {
    this.lineNumber = lineNumber;
    this.fileName = fileName;
    stellarConfiguration = (StellarConfiguration) configuration;
    if (line.startsWith(STELLAR_START)) {
      if (stellarConfiguration.getStellar() != null) {
        ConfigurationLabelException e
                = new ConfigurationLabelException("Duplicate Stellar label", lineNumber,
                fileName);
        logger.error("Duplicate Stellar label", e);
        throw e;
      }
      Matcher stellarMatcher = STELLAR_PATTERN.matcher(line);
      if (stellarMatcher.find()) {
        String[] splits = stellarMatcher.group().split(",");
        validateNumber(splits[1], lineNumber, fileName);
        validateNumber(splits[2], lineNumber, fileName);
        stellarConfiguration.setStellar(
                stellarFactory.build(splits[0],
                        number2Double(splits[1]), number2Double(splits[2])));
      } else {
        ConfigurationSyntaxException e
                = new ConfigurationSyntaxException("Wrong format of stellar", lineNumber,
                fileName);
        logger.error("Wrong format of stellar", e);
        throw e;
      }
    } else if (line.startsWith(PLANET_START)) {
      Matcher planetMatcher = PLANET_PATTERN.matcher(line);
      if (planetMatcher.find()) {
        String string = planetMatcher.group();
        String[] splits = string.split(",");
        if (planetNameSet.contains(splits[0])) {
          ConfigurationLabelException e
                  = new ConfigurationLabelException("Duplicate planet with same name",
                  lineNumber, fileName);
          logger.error("Duplicate planet with same name", e);
          throw e;
        } else {
          planetNameSet.add(splits[0]);
        }
        validateNumber(splits[3], lineNumber, fileName);
        validateNumber(splits[4], lineNumber, fileName);
        validateNumber(splits[5], lineNumber, fileName);
        if (!CLOCKWISE.equals(splits[6]) && !COUNTERCLOCKWISE.equals(splits[6])) {
          ConfigurationSyntaxException e
                  = new ConfigurationSyntaxException("Wrong rotation", lineNumber,
                  fileName);
          logger.error("Wrong rotation", e);
          throw e;
        }
        double startAngle = number2Double(splits[7]);
        if (startAngle > 360 || startAngle < 0) {
          ConfigurationSyntaxException e
                  = new ConfigurationSyntaxException("Wrong start angle", lineNumber,
                  fileName);
          logger.error("Wrong start angle", e);
          throw e;
        }
        stellarConfiguration.getPlanets()
                .add(planetFactory.build(splits[0], splits[1], splits[2], number2Double(splits[3]),
                        number2Double(splits[4]),
                        (number2Double(splits[5]) * 180) / (number2Double(splits[4]) * Math.PI),
                        "CW".equals(splits[6]), startAngle));

      } else {
        ConfigurationSyntaxException e
                = new ConfigurationSyntaxException("Wrong format of planet", lineNumber,
                fileName);
        logger.error("Wrong format of planet", e);
        throw e;
      }
    } else {
      ConfigurationSyntaxException e = new ConfigurationSyntaxException(
              "Invalid label '" + line.substring(0, line.indexOf(" "))
                      + "'", lineNumber, fileName);
      logger.error("Invalid label '" + line.substring(0, line.indexOf(" ")) + "'", e);
      throw e;
    }
  }

  private void validateNumber(String numberString, long line, String fileName) {
    Double number = number2Double(numberString);
    if (number > 10000) {
      if (!numberString.contains("e")) {
        throw new ConfigurationSyntaxException("Missing scientific notation", line, fileName);
      } else {
        String[] strings = numberString.split("e");
        double coefficient = Double.valueOf(strings[0]);
        if (coefficient < 1 || coefficient > 10) {
          ConfigurationSyntaxException e = new ConfigurationSyntaxException(
                  "Wrong format for coefficient of scientific notation",
                  lineNumber, fileName);
          logger.error("Wrong format for coefficient of scientific notation", e);
          throw e;
        } else if (strings[1].contains(".")) {
          ConfigurationSyntaxException e = new ConfigurationSyntaxException(
                  "Wrong format for power of scientific notation", lineNumber, fileName);
          logger.error("Wrong format for power of scientific notation", e);
          throw e;
        } else {
          int power = Integer.parseInt(strings[1]);
          if (power < 3) {
            ConfigurationSyntaxException e = new ConfigurationSyntaxException(
                    "Wrong power (< 3) of scientific notation", lineNumber, fileName);
            logger.error("Wrong power (< 3) of scientific notation", e);
            throw e;
          }
        }
      }
    } else {
      if (numberString.contains("e")) {
        ConfigurationSyntaxException e
                = new ConfigurationSyntaxException("Improper use of Scientific notation",
                lineNumber, fileName);
        logger.error("Improper use of Scientific notation", e);
        throw e;
      }
    }
  }

  private Double number2Double(String numberStr) {
    if (!numberStr.contains("e")) {
      return Double.valueOf(numberStr);
    } else {
      BigDecimal bd = new BigDecimal(numberStr);
      return bd.doubleValue();
    }
  }
}
