package configuration;

import configuration.Exception.ConfigurationLabelException;
import configuration.Exception.ConfigurationSyntaxException;
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

/**
 * the implement of a configuration reader of a stellar system
 *
 * @author Guo Ziyang
 */
public class StellarConfigurationReader extends ConfigurationReader {

	private StellarConfiguration stellarConfiguration;
	private StellarFactory stellarFactory;
	private PlanetFactory planetFactory;
	private static Set<String> planetNameSet = new HashSet<>();

	protected Logger logger = LoggerFactory.getLogger(StellarConfigurationReader.class);

	public StellarConfigurationReader(File file) {
		super(file);
		configuration = new StellarConfiguration();
		stellarFactory = new StellarFactory();
		planetFactory = new PlanetFactory();
		stellarConfiguration = (StellarConfiguration) configuration;
		planetNameSet.clear();
	}

	@Override
	public void checkRep() {
		super.checkRep();
		assert configuration != null;
		assert stellarConfiguration != null;
		assert planetNameSet != null;
	}

	@Override
	public void parseConfiguration(String line) {
		checkRep();
		if (line.startsWith("Stellar ::= <")) {
			if (stellarConfiguration.getStellar() != null) {
				ConfigurationLabelException e = new ConfigurationLabelException("Duplicate Stellar label", lineNumber,
						file.getName());
				logger.error("Duplicate Stellar label", e);
				throw e;
			}
			Pattern stellarPattern = Pattern.compile(
					"(?<=Stellar ::= <)[A-Za-z0-9]+,((\\d+[\\.]?\\d*e\\d+)|(\\d+[\\.]?\\d*)),((\\d+[\\.]?\\d*e\\d+)|(\\d+[\\.]?\\d*))(?=>)");
			Matcher stellarMatcher = stellarPattern.matcher(line);
			if (stellarMatcher.find()) {
				logger.info("Stellar: {}", stellarMatcher.group());
				String[] splits = stellarMatcher.group().split(",");
				validateNumber(splits[1], lineNumber, file.getName());
				validateNumber(splits[2], lineNumber, file.getName());
				stellarConfiguration.setStellar(
						stellarFactory.build(splits[0], number2Double(splits[1]), number2Double(splits[2])));
			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of stellar", lineNumber,
						file.getName());
				logger.error("Wrong format of stellar", e);
				throw e;
			}
		} else if (line.startsWith("Planet ::= <")) {
			Pattern planetPattern = Pattern.compile(
					"(?<=Planet ::= <)[A-Za-z0-9]+,[A-Za-z]+,[A-Za-z]+,(((\\d+[\\.]?\\d*e\\d+)|(\\d+[\\.]?\\d*)),){3}[A-Z]{2,3},\\d+[\\.]?\\d*(?=>)");
			Matcher planetMatcher = planetPattern.matcher(line);
			if (planetMatcher.find()) {
				String string = planetMatcher.group();
				logger.info("Planet: {}", string);
				String[] splits = string.split(",");
				if (planetNameSet.contains(splits[0])) {
					ConfigurationLabelException e = new ConfigurationLabelException("Duplicate planet with same name",
							lineNumber, file.getName());
					logger.error("Duplicate planet with same name", e);
					throw e;
				} else {
					planetNameSet.add(splits[0]);
				}
				validateNumber(splits[3], lineNumber, file.getName());
				validateNumber(splits[4], lineNumber, file.getName());
				validateNumber(splits[5], lineNumber, file.getName());
				if (!"CW".equals(splits[6]) && !"CCW".equals(splits[6])) {
					ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong rotation", lineNumber,
							file.getName());
					logger.error("Wrong rotation", e);
					throw e;
				}
				double startAngle = number2Double(splits[7]);
				if (startAngle > 360 || startAngle < 0) {
					ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong start angle", lineNumber,
							file.getName());
					logger.error("Wrong start angle", e);
					throw e;
				}
				stellarConfiguration.getPlanets()
						.add(planetFactory.build(splits[0], splits[1], splits[2], number2Double(splits[3]),
								number2Double(splits[4]),
								(number2Double(splits[5]) * 180) / (number2Double(splits[4]) * Math.PI),
								"CW".equals(splits[6]), startAngle));

			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of planet", lineNumber,
						file.getName());
				logger.error("Wrong format of planet", e);
				throw e;
			}
		} else {
			ConfigurationSyntaxException e = new ConfigurationSyntaxException(
					"Invalid label '" + line.substring(0, line.indexOf(" ")) + "'", lineNumber, file.getName());
			logger.error("Invalid label '" + line.substring(0, line.indexOf(" ")) + "'", e);
			throw e;
		}
	}

	/**
	 * validate whether the number string is valid
	 *
	 * @param numberString the number string to be validated
	 */
	private void validateNumber(String numberString, long line, String fileName) {
		checkRep();
		Double number = number2Double(numberString);
		if (number > 10000) {
			if (!numberString.contains("e")) {
				throw new ConfigurationSyntaxException("Missing scientific notation", line, fileName);
			} else {
				String[] strings = numberString.split("e");
				double coefficient = Double.valueOf(strings[0]);
				if (coefficient < 1 || coefficient >= 10) {
					ConfigurationSyntaxException e = new ConfigurationSyntaxException(
							"Wrong format for coefficient of scientific notation", lineNumber, file.getName());
					logger.error("Wrong format for coefficient of scientific notation", e);
					throw e;
				} else if (strings[1].contains(".")) {
					ConfigurationSyntaxException e = new ConfigurationSyntaxException(
							"Wrong format for power of scientific notation", lineNumber, file.getName());
					logger.error("Wrong format for power of scientific notation", e);
					throw e;
				} else {
					int power = Integer.parseInt(strings[1]);
					if (power < 3) {
						ConfigurationSyntaxException e = new ConfigurationSyntaxException(
								"Wrong power (< 3) of scientific notation", lineNumber, file.getName());
						logger.error("Wrong power (< 3) of scientific notation", e);
						throw e;
					}
				}
			}
		} else {
			if (numberString.contains("e")) {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Improper use of Scientific notation",
						lineNumber, file.getName());
				logger.error("Improper use of Scientific notation", e);
				throw e;
			}
		}
	}

	/**
	 * convert the number string to double
	 *
	 * @param numberStr the number string to be converted
	 * @return the double number
	 */
	private Double number2Double(String numberStr) {
		checkRep();
		if (!numberStr.contains("e")) {
			return Double.valueOf(numberStr);
		} else {
			BigDecimal bd = new BigDecimal(numberStr);
			return bd.doubleValue();
		}
	}

}
