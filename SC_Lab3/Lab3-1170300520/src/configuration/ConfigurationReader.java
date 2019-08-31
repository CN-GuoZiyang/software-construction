package configuration;

/**
 * the interface of all configuration readers
 * 
 * @author Guo Ziyang
 */
public interface ConfigurationReader {
	
	/**
	 * read the configuration file line by line
	 * 
	 * @return the configuration object
	 */
	Configuration readFile();
	
	/**
	 * parse the line read by the reader using regex
	 * 
	 * @param line the line read by the reader
	 */
	void parseConfiguration(String line);
	
}
