package circularOrbit;

import centralObject.AtomicNucleus;
import configuration.AtomConfiguration;
import configuration.AtomConfigurationReader;
import factory.AtomicNucleusFactory;
import factory.ElectronFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import physicalObject.Electron;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * the implement of an atom structure consisting of an atomic nucleus and some
 * electrons around the core
 *
 * @author Guo Ziyang
 */
public class AtomStructure extends OrbitWithoutPosition<AtomicNucleus, Electron> {

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

	public AtomStructure(File file) throws IOException {
		super();
		centralObjectFactory = new AtomicNucleusFactory();
		electronFactory = new ElectronFactory();
		readFromFile(file);
	}

	@Override
	public void readFromFile(File file) throws IOException {
		super.checkRep();
		logger.info("Start read from configuration file {}", file.getName());
		AtomConfigurationReader reader = new AtomConfigurationReader(file);
		AtomConfiguration configuration = reader.readFile();
		elementName = configuration.getElementName();
		addCentralObject(centralObjectFactory.build(elementName));
		Integer numberOfTracks = configuration.getNumberOfTracks();
		for (int i = 1; i <= numberOfTracks; i++) {
			addTrack((double) i);
		}
		Map<Integer, Integer> numberOfElectron = configuration.getNumberOfElectron();
		for (Map.Entry<Integer, Integer> entry : numberOfElectron.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
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
		super.checkRep();
		return elementName;
	}

	@Override
	public Electron getObject(String name) {
		super.checkRep();
		logger.warn("Cannot invoke method getObject in Atomic Structure");
		return null;
	}

}
