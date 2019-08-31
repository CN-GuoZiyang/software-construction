package configuration;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import configuration.Exception.ConfigurationLabelException;
import configuration.Exception.ConfigurationSyntaxException;

public class PersonalAppEcosystemException {

	@Test(expected = ConfigurationSyntaxException.class)
	public void appUserNameFormat() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_Name.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationLabelException.class)
	public void appSameApp() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_SameApp.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void appAppFormat() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_AppFormat.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void appInstallLogFormat() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_InstallLogFormat.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void appUninstallLogFormat() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_UninstallLogFormat.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void appUsageLogFormat() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_UsageLogFormat.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void durationFormat() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_DurationFormat.txt"));
		reader.readFile(); 
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void relationFormat() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_RelationFormat.txt"));
		reader.readFile(); 
	}
	
	@Test(expected = ConfigurationLabelException.class)
	public void relationLabel() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_RelationLabel.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void periodFormat() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_PeriodFormat.txt"));
		reader.readFile(); 
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void unknownLabel() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_UnknownLabel.txt"));
		reader.readFile();
	}
	
	@Test(expected = ConfigurationSyntaxException.class)
	public void calendar() throws IOException {
		AppConfigurationReader reader = new AppConfigurationReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_Calendar.txt"));
		reader.readFile(); 
	}

}
