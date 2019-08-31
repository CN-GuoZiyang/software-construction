package configuration;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import configuration.Exception.ConfigurationLabelException;
import configuration.Exception.ConfigurationSyntaxException;

public class StellarSystemException {
	
	@Test(expected = ConfigurationLabelException.class)
	public void duplicateStellar() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_DuplicateStellar.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void stellarFormat() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_StellarFormat.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationLabelException.class)
	public void duplicatePlanet() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_DuplicatePlanet.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void wrongRotation() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_WrongRotation.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void wrongStart() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_WrongStart.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void planetFormat() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_PlanetFormat.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void unknownLabel() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_UnknownLabel.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void noScience() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_NoScience.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void wrongCoefficient() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_WrongCoefficient.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void wrongPower() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_WrongPower.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void wrongPower2() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_WrongPower2.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void improperScience() throws IOException {
		StellarConfigurationReader reader = new StellarConfigurationReader(new File("src/applications/configurations/exception/StellarSystem_ImproperScience.txt"));
		reader.readFile();
	}
	
	
	
}
