package configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the implement of the atom configuration reader
 * 
 * @author Guo Ziyang
 */
public class AtomConfigurationReader implements ConfigurationReader{
	
	private File file;
	private AtomConfiguration configuration;
	
	protected Logger logger = LoggerFactory.getLogger(AtomConfigurationReader.class);
	
	public AtomConfigurationReader(File file) {
		this.file = file;
		configuration = new AtomConfiguration();
	}
	
	@Override
	public AtomConfiguration readFile() {
		if(!file.exists()) {
			logger.error("file does not exist", new FileNotFoundException(file.getName()));
			return null;
		}
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null) {
				if(line.isBlank()) {
					continue;
				} else {
					builder.append(line);
				}
			}
			parseConfiguration(builder.toString());
		} catch (IOException e) {
			logger.error("error happened when parsing configuration file", e);
			return null;
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("error happened when closing the stream", e);
				}
			}
		}
		return configuration;
	}

	@Override
	public void parseConfiguration(String line) {
		Pattern elementNamePattern = Pattern.compile("(?<=ElementName ::= )[A-Z][a-z]?");
		Matcher elementNameMatcher = elementNamePattern.matcher(line);
		if(elementNameMatcher.find()) {
			logger.info("ElementName: {}", elementNameMatcher.group());
			configuration.setElementName(elementNameMatcher.group());
		} else {
			logger.error("Missing configuration: {}", "ElementName");
		}
		Pattern numberOfTracksPattern = Pattern.compile("(?<=NumberOfTracks ::= )\\d+");
		Matcher numberOfTracksMatcher = numberOfTracksPattern.matcher(line);
		if(numberOfTracksMatcher.find()) {
			logger.info("NumberOfTracks: {}", numberOfTracksMatcher.group());
			configuration.setNumberOfTracks(Integer.valueOf(numberOfTracksMatcher.group()));
		} else {
			logger.error("Missing configuration: {}", "NumberOfTracks");
		}
		Pattern numberOfElectronPattern = Pattern.compile("(?<=NumberOfElectron ::= )[\\d+/\\d+;?]+");
		Matcher numberOfElectronMatcher = numberOfElectronPattern.matcher(line);
		if(numberOfElectronMatcher.find()) {
			logger.info("NumberOfElectron: {}", numberOfElectronMatcher.group());
			String[] strings = numberOfElectronMatcher.group().split(";");
			for(String string : strings) {
				configuration.getNumberOfElectron().put(Integer.valueOf(string.split("/")[0]), Integer.valueOf(string.split("/")[1]));
			}
		} else {
			logger.error("Missing configuration: {}", "NumberOfElectron");
		}
	}
	
}
