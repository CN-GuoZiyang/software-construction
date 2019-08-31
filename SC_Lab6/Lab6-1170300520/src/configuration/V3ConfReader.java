package configuration;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class V3ConfReader {

  private V3Configurtion configuration;
  private File file;

  public V3ConfReader(String src) {
    File temp = new File(src);
    if (!temp.exists()) {
      //TODO
      return;
    }
    file = temp;
  }

  public V3Configurtion getConfiguration() {
    configuration = new V3Configurtion();
    try(FileChannel channel = FileChannel.open(Paths.get(file.getPath()), StandardOpenOption.READ)) {
      MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
      StringBuilder builder = new StringBuilder();
      while (buffer.hasRemaining()) {
        builder.append((char)buffer.get());
      }
      String[] strings = builder.toString().split("\n");
      for (String string : strings) {
        parse(string);
      }
      return configuration;
    } catch (IOException e) {
      //TODO
      e.printStackTrace();
      return null;
    }
  }

  private void parse(String line) {
    if(line.startsWith("n=")) {
      configuration.setLadderNumber(Integer.valueOf(line.split("=")[1]));
    } else if(line.startsWith("h=")) {
      configuration.setLadderLength(Integer.valueOf(line.split("=")[1]));
    } else if(line.startsWith("monkey=")) {
      line = line.split("=")[1].substring(1);
      line = line.substring(0, line.length() - 1);
      String[] lines = line.split(",");
      configuration.addMonkey(Integer.valueOf(lines[0]), Integer.valueOf(lines[1]), lines[2], Integer.valueOf(lines[3]));
    }
  }

}
