package configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * the interface of all configuration readers
 *
 * @author Guo Ziyang
 */
public abstract class ConfigurationReader {

	protected File file;
	protected Configuration configuration;
	protected long lineNumber = 0;

	private Logger logger = LoggerFactory.getLogger(ConfigurationReader.class);

	public ConfigurationReader(File file) {
		this.file = file;
	}

	public void checkRep() {
		assert file != null;
	}

	/**
	 * read the configuration file line by line
	 *
	 * @return the configuration object
	 */
	public Configuration readFile() throws IOException {
		checkRep();
		if (!file.exists()) {
			logger.error("file does not exist", new FileNotFoundException(file.getName()));
			return null;
		}
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				if (!"".equals(line)) {
					parseConfiguration(line.trim());
				}
			}
		} catch (IOException e) {
			logger.error("error happened when parsing configuration file", e);
			throw e;
		}
		return configuration;
	}

	/**
	 * parse the line read by the reader using regex
	 *
	 * @param line the line read by the reader
	 */
	public abstract void parseConfiguration(String line);

}
