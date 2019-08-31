package ioperformance;

import configuration.Configuration;

import java.io.IOException;

public interface Reader {

  Configuration readFile() throws IOException;

}
