package factory;

import circularorbit.StellarSystem;

import java.io.File;
import java.io.IOException;

import configuration.*;
import ioperformance.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**.
 * the factory building the non-position-sensitive circular orbit
 *
 * @author Guo Ziyang
 */
public class OrbitWithPositionFactory {

  private Logger logger = LoggerFactory.getLogger(OrbitWithPositionFactory.class);

  /**.
   * build a stellar system via the configuration file
   *
   * @param file the configuration file
   * @return the stellar file
   */
  public StellarSystem buildStellarSystem(File file) throws IOException {
    logger.info("Build a stellar system with file {}", file.getAbsolutePath());
    Parser parser = new StellarParser();
    Reader reader = OrbitWithoutPositionFactory.chooseStrategy(file, parser);
    return buildStellarSystem(reader);
  }

  public StellarSystem buildStellarSystem(Reader reader) throws IOException {
    return new StellarSystem(reader.readFile());
  }

}
