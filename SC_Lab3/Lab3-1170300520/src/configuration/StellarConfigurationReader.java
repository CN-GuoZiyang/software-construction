package configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import factory.PlanetFactory;
import factory.StellarFactory;

/**
 * the implement of a configuration reader of a stellar system
 * 
 * @author Guo Ziyang
 */
public class StellarConfigurationReader implements ConfigurationReader {
	
	private File file;
	private StellarConfiguration configuration;
	
	private StellarFactory stellarFactory;
	private PlanetFactory planetFactory;
	
	protected Logger logger = LoggerFactory.getLogger(StellarConfigurationReader.class);
	
	public StellarConfigurationReader(File file) {
		this.file = file;
		configuration = new StellarConfiguration();
		stellarFactory = new StellarFactory();
		planetFactory = new PlanetFactory();
	}
	
	@Override
	public StellarConfiguration readFile() {
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
		Pattern stellarPattern = Pattern.compile("(?<=Stellar ::= <)[A-Za-z0-9]+,((\\d+[\\.]?\\d*e\\d+)|(\\d+[\\.]?\\d*)),((\\d+[\\.]?\\d*e\\d+)|(\\d+[\\.]?\\d*))(?=>)");
		Matcher stellarMatcher = stellarPattern.matcher(line);
		if(stellarMatcher.find()) {
			logger.info("Stellar: {}", stellarMatcher.group());
			String[] splits = stellarMatcher.group().split(",");
			configuration.setStellar(stellarFactory.build(splits[0], number2Double(splits[1]), number2Double(splits[2])));
		} else {
			logger.error("Missing configuration: {}", "Stellar");
			return;
		}
		Pattern planetPattern = Pattern.compile("(?<=Planet ::= <)[A-Za-z0-9]+,[A-Za-z]+,[A-Za-z]+,(((\\d+[\\.]?\\d*e\\d+)|(\\d+[\\.]?\\d*)),){3}((CCW)|(CW)),\\d+[\\.]?\\d*(?=>)");
		Matcher planetMatcher = planetPattern.matcher(line);
		if(planetMatcher.groupCount() == 0) {
			logger.error("Missing configuration: {}", "Planet");
			return;
		}
		while(planetMatcher.find()) {
			String string = planetMatcher.group();
			logger.info("Planet: {}", string);
			String[] splits = string.split(",");
			configuration.getPlanets().add(planetFactory.build(splits[0], splits[1],
					splits[2], number2Double(splits[3]), number2Double(splits[4]),
					(number2Double(splits[5])*180)/(number2Double(splits[4])*Math.PI), "CW".equals(splits[6]), number2Double(splits[7])));
		
		}
	}
	
	private Double number2Double(String numberStr) {
		if(!numberStr.contains("e")) {
			return Double.valueOf(numberStr);
		} else {
			BigDecimal bd = new BigDecimal(numberStr);
			return bd.doubleValue();
		}
	}

}
