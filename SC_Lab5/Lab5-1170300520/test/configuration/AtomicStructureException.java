package configuration;

import configuration.exception.ConfigurationDependencyException;
import configuration.exception.ConfigurationLabelException;
import configuration.exception.ConfigurationSyntaxException;
import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class AtomicStructureException {

  @Test(expected = ConfigurationDependencyException.class)
  public void trackNumber() throws IOException {
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/AtomicStructure_TrackNumber.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationLabelException.class)
  public void duplicateName() throws IOException {
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/AtomicStructure_DuplicateName.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void nameFormat() throws IOException {
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/AtomicStructure_NameFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void numberFormat() throws IOException {
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/AtomicStructure_NumberFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void electronFormat() throws IOException {
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/AtomicStructure_ElectronFormat.txt"), parser);
    reader.readFile();
  }

  @Test(expected = ConfigurationSyntaxException.class)
  public void unknownLabel() throws IOException {
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/exception/AtomicStructure_UnknownLabel.txt"), parser);
    reader.readFile();
  }

}
