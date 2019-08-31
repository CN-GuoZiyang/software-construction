package circularOrbit;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import centralObject.AtomicNucleus;
import configuration.AtomConfiguration;
import configuration.AtomConfigurationReader;
import factory.AtomicNucleusFactory;
import factory.ElectronFactory;
import physicalObject.Electron;

/**
 * the implement of an atom structure consisting of an atomic nucleus and some electrons around the core
 * 
 * @author Guo Ziyang
 */
public class AtomStructure extends ConcreteCircularOrbitWithoutPosition<AtomicNucleus, Electron> {
	
	private String elementName;
	
	private AtomicNucleusFactory centralObjectFactory;
	
	private ElectronFactory electronFactory;
	
	private Logger logger = LoggerFactory.getLogger(AtomStructure.class);
	
	public AtomStructure(String elementName) {
		super();
		this.elementName = elementName;
		centralObjectFactory = new AtomicNucleusFactory();
		addCentralObject(centralObjectFactory.build(elementName));
		electronFactory = new ElectronFactory();
	}
	
	public AtomStructure(File file) {
		super();
		centralObjectFactory = new AtomicNucleusFactory();
		electronFactory = new ElectronFactory();
		readFromFile(file);
	}

	@Override
	public void readFromFile(File file) {
		AtomConfigurationReader reader = new AtomConfigurationReader(file);
		AtomConfiguration configuration = reader.readFile();
		elementName = configuration.getElementName();
		addCentralObject(centralObjectFactory.build(elementName));
		Integer numberOfTracks = configuration.getNumberOfTracks();
		for(int i = 1; i <= numberOfTracks; i ++) {
			addTrack(Double.valueOf(i));
		}
		Map<Integer, Integer> numberOfElectron = configuration.getNumberOfElectron();
		for(Map.Entry<Integer, Integer> entry : numberOfElectron.entrySet()) {
			for(int i = 0; i < entry.getValue(); i ++) {
				addPhysicalObject(electronFactory.build(), Double.valueOf(entry.getKey()));
			}
		}
		logger.info("successfully build from configuration");
	}
	
	/**
	 * get the name of the element
	 * 
	 * @return the element's name
	 */
	public String getElementName() {
		return elementName;
	}
	
	@Override
	public Electron getObject(String name) {
		return null;
	}

}
