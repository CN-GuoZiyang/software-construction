package configuration;

import configuration.exception.ConfigurationLabelException;
import configuration.exception.ConfigurationSyntaxException;
import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class StellarSystemException {

  @Test(expected = ConfigurationLabelException.class)
  public void duplicateStellar() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_DuplicateStellar.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void stellarFormat() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_StellarFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationLabelException.class)
  public void duplicatePlanet() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_DuplicatePlanet.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void wrongRotation() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_WrongRotation.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void wrongStart() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_WrongStart.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void planetFormat() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_PlanetFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void unknownLabel() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_UnknownLabel.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void noScience() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_NoScience.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void wrongCoefficient() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_WrongCoefficient.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void wrongPower() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_WrongPower.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void wrongPower2() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_WrongPower2.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void improperScience() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/StellarSystem_ImproperScience.txt"), parser);
    reader.readFile();
  }


}
