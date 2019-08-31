package ioperformance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedIoWriter implements Writer {

  private File file;
  private BufferedWriter writer;

  public BufferedIoWriter(File file) throws IOException {
    this.file = file;
    writer = new BufferedWriter(new FileWriter(file));
  }

  @Override
  public void writeLine(String line) throws IOException {
    writer.write(line);
    writer.newLine();
  }

  @Override
  public void close() throws IOException {
    writer.flush();
    writer.close();
  }

}
