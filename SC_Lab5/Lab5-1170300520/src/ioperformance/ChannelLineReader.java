package ioperformance;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**.
 * the line reader using nio channel
 *
 * @author Guo Ziyang
 */
public class ChannelLineReader {

  private FileChannel fileChannel;
  private File file;
  private byte[] bytes;
  private String[] lines;
  private int index = -1;

  public static final int BUFFER_SIZE = 1024;

  public ChannelLineReader(File file) {
    this.file = file;
  }

  /**.
   * read one line once
   *
   * @return the next line
   * @throws IOException Channel fails
   */
  public String readLine() throws IOException {
    if (fileChannel == null) {
      fileChannel = new RandomAccessFile(file, "r").getChannel();
      int len = (int)fileChannel.size();
      bytes = new byte[len];
      ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
      int i = 0;
      while (true) {
        int count = fileChannel.read(buffer);
        if (count <= -1) {
          break;
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
          bytes[i] = buffer.get();
          i++;
        }
        buffer.compact();
      }
      lines = new String(bytes).split("\n");
    }
    index++;
    if (index >= lines.length) {
      return null;
    }
    return lines[index];
  }

  public void close() throws IOException {
    fileChannel.close();
  }

}
