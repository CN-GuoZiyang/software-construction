package ioperformance;

import java.io.IOException;

public interface Writer {

  void writeLine(String line) throws IOException;

  void close() throws IOException;

}
