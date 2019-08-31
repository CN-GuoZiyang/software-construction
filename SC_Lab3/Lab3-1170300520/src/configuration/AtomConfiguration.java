package configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * the information of a configuration file
 * 
 * @author Guo Ziyang
 */
public class AtomConfiguration implements Configuration{
	private String elementName;
	private Integer numberOfTracks;
	private Map<Integer, Integer> numberOfElectron;
	
	public AtomConfiguration() {
		numberOfElectron = new HashMap<>();
	}

	/**
	 * @return the elementName
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * @param elementName the elementName to set
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	/**
	 * @return the numberOfTracks
	 */
	public Integer getNumberOfTracks() {
		return numberOfTracks;
	}

	/**
	 * @param numberOfTracks the numberOfTracks to set
	 */
	public void setNumberOfTracks(Integer numberOfTracks) {
		this.numberOfTracks = numberOfTracks;
	}

	/**
	 * @return the numberOfElectron
	 */
	public Map<Integer, Integer> getNumberOfElectron() {
		return numberOfElectron;
	}

	/**
	 * @param numberOfElectron the numberOfElectron to set
	 */
	public void setNumberOfElectron(Map<Integer, Integer> numberOfElectron) {
		this.numberOfElectron = numberOfElectron;
	}
	
}
