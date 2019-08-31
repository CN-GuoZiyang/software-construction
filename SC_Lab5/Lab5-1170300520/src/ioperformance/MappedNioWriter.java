package ioperformance;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**.
 * write to file line by line using nio mapped byte buffer
 *
 * @author Guo Ziyang
 */
public class MappedNioWriter implements Writer {

  private long size;
  private FileChannel fileChannel;
  private MappedByteBuffer buffer;

  public MappedNioWriter(File file) throws IOException {
    fileChannel = new RandomAccessFile(file, "rw").getChannel();
    size = fileChannel.size();
  }

  @Override
  public void writeLine(String line) throws IOException {
    byte[] bytes = (line + "\n").getBytes();
    size += bytes.length;
    buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, size);
    buffer.position(buffer.limit() - bytes.length);
    buffer.put(bytes);
    buffer.force();
    buffer.flip();
  }

  @Override
  public void close() throws IOException {
    fileChannel.close();
  }
}
