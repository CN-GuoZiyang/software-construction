package factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import physicalObject.Electron;

/**
 * an electron factory
 *
 * @author Guo Ziyang
 */
public class ElectronFactory {

	private Logger logger = LoggerFactory.getLogger(ElectronFactory.class);

	/**
	 * build an electron
	 *
	 * @return an electron
	 */
	public Electron build() {
		logger.info("build an electron");
		return new Electron();
	}

}
