package ioperformance;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**.
 * writing line by line using nio channel
 *
 * @author Guo Ziyang
 */
public class ChannelNioWriter implements Writer {

  private FileChannel writer;

  public ChannelNioWriter(File file) throws IOException {
    writer = new RandomAccessFile(file, "rw").getChannel();
    writer.position(writer.size());
  }

  @Override
  public void writeLine(String line) throws IOException {
    writer.write(ByteBuffer.wrap((line + "\n").getBytes()));
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }
}
