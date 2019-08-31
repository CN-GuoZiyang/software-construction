package configuration;

public interface Parser {

  void parseConfiguration(String line, Configuration configuration, long lineNumber, String fileName);

}
