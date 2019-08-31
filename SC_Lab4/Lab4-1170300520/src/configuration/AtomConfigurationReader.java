package configuration;

import configuration.Exception.ConfigurationDependencyException;
import configuration.Exception.ConfigurationLabelException;
import configuration.Exception.ConfigurationSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * the implement of the atom configuration reader
 *
 * @author Guo Ziyang
 */
public class AtomConfigurationReader extends ConfigurationReader {

	private AtomConfiguration atomConfiguration;

	protected Logger logger = LoggerFactory.getLogger(AtomConfigurationReader.class);

	public AtomConfigurationReader(File file) {
		super(file);
		configuration = new AtomConfiguration();
		atomConfiguration = (AtomConfiguration) configuration;
	}

	@Override
	public void checkRep() {
		super.checkRep();
		assert configuration != null;
		assert atomConfiguration != null;
	}

	@Override
	public AtomConfiguration readFile() throws IOException {
		checkRep();
		AtomConfiguration atomConfiguration = (AtomConfiguration) super.readFile();
		if (atomConfiguration.getNumberOfTracks() != atomConfiguration.getNumberOfElectron().size()) {
			ConfigurationDependencyException e = new ConfigurationDependencyException("Tracks number error", lineNumber,
					file.getName());
			logger.error("Tracks number error", e);
			throw e;
		}
		return atomConfiguration;
	}

	@Override
	public void parseConfiguration(String line) {
		checkRep();
		if (line.startsWith("ElementName ::= ")) {
			if (atomConfiguration.getElementName() != null) {
				ConfigurationLabelException e = new ConfigurationLabelException("Duplicate element name label",
						lineNumber, file.getName());
				logger.error("Duplicate element name label", e);
				throw e;
			}
			Pattern elementNamePattern = Pattern.compile("(?<=ElementName ::= )[A-Z][a-z]?$");
			Matcher elementNameMatcher = elementNamePattern.matcher(line);
			if (elementNameMatcher.find()) {
				logger.info("ElementName: {}", elementNameMatcher.group());
				atomConfiguration.setElementName(elementNameMatcher.group());
			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of element name",
						lineNumber, file.getName());
				logger.error("Wrong format of element name", e);
				throw e;
			}
		} else if (line.startsWith("NumberOfTracks ::= ")) {
			Pattern numberOfTracksPattern = Pattern.compile("(?<=NumberOfTracks ::= )\\d+$");
			Matcher numberOfTracksMatcher = numberOfTracksPattern.matcher(line);
			if (numberOfTracksMatcher.find()) {
				logger.info("NumberOfTracks: {}", numberOfTracksMatcher.group());
				atomConfiguration.setNumberOfTracks(Integer.valueOf(numberOfTracksMatcher.group()));
			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of tracks number",
						lineNumber, file.getName());
				logger.error("Wrong format of tracks number", e);
				throw e;
			}
		} else if (line.startsWith("NumberOfElectron ::= ")) {
			Pattern numberOfElectronPattern = Pattern.compile("(?<=NumberOfElectron ::= )[\\d+/\\d+;?]+$");
			Matcher numberOfElectronMatcher = numberOfElectronPattern.matcher(line);
			if (numberOfElectronMatcher.find()) {
				logger.info("NumberOfElectron: {}", numberOfElectronMatcher.group());
				String[] strings = numberOfElectronMatcher.group().split(";");
				for (String string : strings) {
					atomConfiguration.getNumberOfElectron().put(Integer.valueOf(string.split("/")[0]),
							Integer.valueOf(string.split("/")[1]));
				}
			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of electon number",
						lineNumber, file.getName());
				logger.error("Wrong format of electon number", e);
				throw e;
			}
		} else {
			String s = "Invalid label '" + line.substring(0, line.indexOf(" ")) + "'";
			ConfigurationSyntaxException e = new ConfigurationSyntaxException(s, lineNumber, file.getName());
			logger.error(s, e);
			throw e;
		}

	}

}
