package centralObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the implement of an atomic nucleus with the element's name
 *
 * @author Guo Ziyang
 */
public class AtomicNucleus extends CentralObject {

	protected Logger logger = LoggerFactory.getLogger(AtomicNucleus.class);

	/**
	 * init the atomic nucleus with the element name
	 *
	 * @param name the name of nucleus(element name)
	 */
	public AtomicNucleus(final String name) {
		super(name);
	}

	@Override
	public String toString() {
		return "AtomicNucleus (" + name + ")";
	}

}
