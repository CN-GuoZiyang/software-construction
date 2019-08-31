package configuration;

import configuration.exception.ConfigurationLabelException;
import configuration.exception.ConfigurationSyntaxException;
import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PersonalAppEcosystemException {

  @Test(expected = ConfigurationSyntaxException.class)
  public void appUserNameFormat() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_Name.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationLabelException.class)
  public void appSameApp() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_SameApp.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void appAppFormat() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_AppFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void appInstallLogFormat() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_InstallLogFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void appUninstallLogFormat() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_UninstallLogFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void appUsageLogFormat() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_UsageLogFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void durationFormat() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_DurationFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void relationFormat() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_RelationFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationLabelException.class)
  public void relationLabel() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_RelationLabel.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void periodFormat() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_PeriodFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void unknownLabel() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_UnknownLabel.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void calendar() throws IOException {
    Parser parser = new AppParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/PersonalAppEcosystem_Calendar.txt"), parser);
    reader.readFile();
  }

}
