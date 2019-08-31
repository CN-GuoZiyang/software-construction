package configuration;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ConfReader {

  private Configuration configuration;
  private File file;

  public ConfReader() {
    File temp = new File("src/conf/default.txt");
    if (!temp.exists()) {
      //TODO
      return;
    }
    file = temp;
  }

  public ConfReader(String src) {
    File temp = new File(src);
    if (!temp.exists()) {
      //TODO
      return;
    }
    file = temp;
  }

  public Configuration getConfiguration() {
    configuration = new Configuration();
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
      configuration.setLadderNumber(Integer.parseInt(line.split("=")[1]));
    } else if(line.startsWith("h=")) {
      configuration.setLadderLength(Integer.parseInt(line.split("=")[1]));
    } else if(line.startsWith("t=")) {
      configuration.setInterval(Integer.parseInt(line.split("=")[1]));
    } else if(line.startsWith("N=")) {
      configuration.setMonkeyNumber(Integer.parseInt(line.split("=")[1]));
    } else if(line.startsWith("k=")) {
      configuration.setMonkeyPerTime(Integer.parseInt(line.split("=")[1]));
    } else if(line.startsWith("MV=")) {
      configuration.setMaxSpeed(Integer.parseInt(line.split("=")[1]));
    } else {
      //TODO
    }
  }

}
