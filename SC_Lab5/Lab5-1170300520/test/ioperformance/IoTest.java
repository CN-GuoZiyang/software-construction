package ioperformance;

import configuration.*;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Calendar;

public class IoTest {

  long start;
  long end;

  Reader reader;
  Parser parser = new NullParser();
  File file = new File("src/ioperformance/configurations/StellarSystem.txt");

  String line = "All work and no play makes jack a dull boy";

  Writer writer;

  @Test
  public void readTest() throws IOException {
    start = Calendar.getInstance().getTimeInMillis();
    reader = new BufferedIoReader(file, parser);
    reader.readFile();
    end = Calendar.getInstance().getTimeInMillis();
    System.out.println("BufferedReader: " + (end - start) / 1000.0 + "s");

    start = Calendar.getInstance().getTimeInMillis();
    reader = new ChannelNioReader(file, parser);
    reader.readFile();
    end = Calendar.getInstance().getTimeInMillis();
    System.out.println("ChannelNioReader: " + (end - start) / 1000.0 + "s");

    start = Calendar.getInstance().getTimeInMillis();
    reader = new MappedNioReader(file, parser);
    reader.readFile();
    end = Calendar.getInstance().getTimeInMillis();
    System.out.println("MappedNioReader: " + (end - start) / 1000.0 + "s");

  }

  @Test
  public void writeTest() throws IOException {
    start = Calendar.getInstance().getTimeInMillis();
    writer = new BufferedIoWriter(new File("src/applications/BufferedIoWriter.txt"));
    for (int i = 0; i < 30000; i ++) {
      writer.writeLine(line);
    }
    end = Calendar.getInstance().getTimeInMillis();
    System.out.println("BufferedIoWriter: " + (end - start) / 1000.0 + "s");

    start = Calendar.getInstance().getTimeInMillis();
    writer = new ChannelNioWriter(new File("src/applications/ChannelNioWriter.txt"));
    for (int i = 0; i < 30000; i ++) {
      writer.writeLine(line);
    }
    writer.close();
    end = Calendar.getInstance().getTimeInMillis();
    System.out.println("ChannelNioWriter: " + (end - start) / 1000.0 + "s");

    start = Calendar.getInstance().getTimeInMillis();
    writer = new MappedNioWriter(new File("src/applications/MappedNioWriter.txt"));
    for (int i = 0; i < 30000; i ++) {
      writer.writeLine(line);
    }
    writer.close();
    end = Calendar.getInstance().getTimeInMillis();
    System.out.println("MappedNioWriter: " + (end - start) / 1000.0 + "s");
  }

  @Test
  public void writeStaticTest() throws IOException {
    int len = 1290000;
    System.out.println("文件大小：" + len);
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("src/applications/configurations/BufferedIoWriter.txt")));
    start = Calendar.getInstance().getTimeInMillis();
    for(int i = 0; i < 30000; i ++) {
      bufferedWriter.write(line);
    }
    bufferedWriter.flush();
    end = Calendar.getInstance().getTimeInMillis();
    System.out.println("BufferedIoWriter: " + (end - start) / 1000.0 + "s");
    bufferedWriter.close();

    RandomAccessFile outputRandomFile = new RandomAccessFile(new File("src/applications/configurations/ChannelNioWriter.txt"), "rw");
    FileChannel fileChannel = outputRandomFile.getChannel();
    byte[] bytes = line.getBytes();
    start = Calendar.getInstance().getTimeInMillis();
    for (int i = 0; i < 30000; i ++) {
      fileChannel.write(ByteBuffer.wrap(bytes));
    }
    end = Calendar.getInstance().getTimeInMillis();
    System.out.println("ChannelNioWriter: " + (end - start) / 1000.0 + "s");
    fileChannel.close();

    RandomAccessFile randomFile = new RandomAccessFile(new File("src/applications/configurations/MappedNioWriter.txt"), "rw");
    FileChannel outputChannel = randomFile.getChannel();
    MappedByteBuffer outputBuffer = outputChannel.map(FileChannel.MapMode.READ_WRITE, 0, len);
    start = Calendar.getInstance().getTimeInMillis();
    for (int i = 0; i < 30000; i ++) {
      outputBuffer.put(bytes);
    }
    end = Calendar.getInstance().getTimeInMillis();
    System.out.println("MappedNioWriter: " + (end - start) / 1000.0 + "s");

  }

  @Test
  public void mappedPaste() throws IOException {
    File inputFile = new File("src/ioperformance/configurations/PersonalAppEcosystem.txt");
    File outputFile = new File("src/ioperformance/configurations/PersonalAppEcosystem_Output.txt");
    RandomAccessFile inputRandomFile = new RandomAccessFile(inputFile, "r");
    FileChannel inputFc = inputRandomFile.getChannel();
    int len = (int) inputFc.size();
    MappedByteBuffer inputBuffer = inputFc.map(FileChannel.MapMode.READ_ONLY, 0, len);
    RandomAccessFile outputRandomFile = new RandomAccessFile(outputFile, "rw");
    FileChannel outputChannel = outputRandomFile.getChannel();
    MappedByteBuffer outputBuffer = outputChannel.map(FileChannel.MapMode.READ_WRITE, 0, len);
    long start = Calendar.getInstance().getTimeInMillis();
    while (inputBuffer.hasRemaining()) {
      outputBuffer.put(inputBuffer.get());
    }
    long end = Calendar.getInstance().getTimeInMillis();
    System.out.println((end - start) / 1000.0);
  }

  @Test
  public void channelPaste() throws IOException {
    File inputFile = new File("src/ioperformance/configurations/PersonalAppEcosystem.txt");
    File outputFile = new File("src/ioperformance/configurations/PersonalAppEcosystem_Output.txt");
    RandomAccessFile inputRandomFile = new RandomAccessFile(inputFile, "r");
    FileChannel inputFc = inputRandomFile.getChannel();
    RandomAccessFile outputRandomFile = new RandomAccessFile(outputFile, "rw");
    FileChannel outputChannel = outputRandomFile.getChannel();
    long start = Calendar.getInstance().getTimeInMillis();
    inputFc.transferTo(0, inputFc.size(), outputChannel);
    long end = Calendar.getInstance().getTimeInMillis();
    System.out.println((end - start) / 1000.0);
  }

  @Test
  public void bufferedPaste() throws IOException {
    File inputFile = new File("src/ioperformance/configurations/PersonalAppEcosystem.txt");
    File outputFile = new File("src/ioperformance/configurations/PersonalAppEcosystem_Output.txt");
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
    long start = Calendar.getInstance().getTimeInMillis();
    String temp;
    while ((temp = reader.readLine()) != null) {
      writer.write(temp);
      writer.newLine();
    }
    writer.flush();
    long end = Calendar.getInstance().getTimeInMillis();
    System.out.println((end - start) / 1000.0);
  }

}
