package configuration;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import configuration.Exception.ConfigurationDependencyException;
import configuration.Exception.ConfigurationLabelException;
import configuration.Exception.ConfigurationSyntaxException;

public class AtomicStructureException {
	
	@Test(expected = ConfigurationDependencyException.class)
	public void trackNumber() throws IOException {
		AtomConfigurationReader reader = new AtomConfigurationReader(new File("src/applications/configurations/exception/AtomicStructure_TrackNumber.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationLabelException.class)
	public void duplicateName() throws IOException {
		AtomConfigurationReader reader = new AtomConfigurationReader(new File("src/applications/configurations/exception/AtomicStructure_DuplicateName.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void nameFormat() throws IOException {
		AtomConfigurationReader reader = new AtomConfigurationReader(new File("src/applications/configurations/exception/AtomicStructure_NameFormat.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void numberFormat() throws IOException {
		AtomConfigurationReader reader = new AtomConfigurationReader(new File("src/applications/configurations/exception/AtomicStructure_NumberFormat.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void electronFormat() throws IOException {
		AtomConfigurationReader reader = new AtomConfigurationReader(new File("src/applications/configurations/exception/AtomicStructure_ElectronFormat.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void unknownLabel() throws IOException {
		AtomConfigurationReader reader = new AtomConfigurationReader(new File("src/applications/configurations/exception/AtomicStructure_UnknownLabel.txt"));
		reader.readFile();
	}
	
}
