package ioperformance;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**.
 * read line by line using mapped byte bufer
 *
 * @author Guo Ziyang
 */
public class MappedLineReader {

  private File file;
  private MappedByteBuffer buffer;
  private byte[] bytes;
  String[] lines;
  int index = -1;

  private FileChannel fc;

  public MappedLineReader(File file) {
    this.file = file;
  }

  /**.
   * read a line once
   *
   * @return the next line
   * @throws IOException some io exception
   */
  public String readLine() throws IOException {
    if (buffer == null) {
      RandomAccessFile randomFile = new RandomAccessFile(file, "r");
      fc = randomFile.getChannel();
      int len = (int) randomFile.length();
      buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, len);
      bytes = new byte[len];
      for (int i = 0; i < len; i++) {
        bytes[i] = buffer.get();
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
    fc.close();
  }

}
