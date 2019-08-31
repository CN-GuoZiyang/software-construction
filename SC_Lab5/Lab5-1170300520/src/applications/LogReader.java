package applications;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**.
 * Log related tools
 *
 * @author Guo Ziyang
 */
public class LogReader {
  private static long lastTimeFileSize = 0;
  private static final Logger logger = LoggerFactory.getLogger(LogReader.class);

  private static StringBuilder logBuilder = new StringBuilder();

  private static String logFileName;

  /**.
   * read log from file
   */
  public static void readLog() {
    File logFile = new File(logFileName);
    logger.info("start update file {}", logFileName);
    try {
      long len = logFile.length();
      if (len < lastTimeFileSize) {
        logger.info("Log file was reset. Restarting logging from start of file.");
        lastTimeFileSize = 0;
      } else {
        RandomAccessFile randomFile = new RandomAccessFile(logFile, "r");
        FileChannel channel = randomFile.getChannel();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, lastTimeFileSize, channel.size() - lastTimeFileSize);
        while (buffer.hasRemaining()) {
          logBuilder.append((char)buffer.get());
        }
        lastTimeFileSize = channel.size();
        channel.close();
        randomFile.close();
      }
    } catch (IOException e) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
      logger.error(dateFormat.format(new Date())
                      + " File read error, lastTimeFileSize: " + lastTimeFileSize);
    }
  }

  /**.
   * create an appender to write log to file
   */
  public static void createAppender() {
    LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    org.apache.logging.log4j.core.config.Configuration config = ctx.getConfiguration();
    PatternLayout layout = PatternLayout.newBuilder().withCharset(Charset.forName("UTF-8"))
            .withPattern("%d{yyyy-MM-dd} %d{HH:mm:ss} %-5level %-27C{1} - [%M] %msg%n")
            .withConfiguration(config)
            .build();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    logFileName = "logs/log_" + sdf.format(new Date()) + ".log";
    Appender appender = FileAppender.newBuilder().setName("MyRollingFile").withImmediateFlush(true)
            .setLayout(layout).withFileName(logFileName).build();
    appender.start();
    config.addAppender(appender);
    config.getRootLogger().addAppender(appender, Level.ALL, null);
    ctx.updateLoggers(config);
  }

  /**.
   * search log by date
   *
   * @param date1 date1
   * @param date2 date2
   * @return the log
   */
  public static String searchLogsByDate(Date date1, Date date2) {
    if (date1.after(date2)) {
      logger.error("Wrong order of date");
      return "\u001B[m\u001B[1;31m" + "日期顺序错误！\n\u001B[m";
    }
    BufferedReader stringReader = new BufferedReader(
            new InputStreamReader(
                    new ByteArrayInputStream(
                            logBuilder.toString().getBytes(Charset.forName("utf8"))),
                    Charset.forName("utf8")));
    StringBuilder resultBuilder = new StringBuilder();
    String line;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      while ((line = stringReader.readLine()) != null) {
        if (Pattern.matches(
                "\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2} (INFO|WARN|DEBUG|TRACE|ERROR).*", line)) {
          String dateString = line.substring(0, 19);
          Date currentDate = sdf.parse(dateString);
          if ((currentDate.after(date1) && currentDate.before(date2)) || currentDate.equals(date1)
                  || currentDate.equals(date2)) {
            resultBuilder.append(line).append("\n");
          }
        }
      }
      resultBuilder.append("\u001B[m");
    } catch (IOException e) {
      logger.error("IOException when parsing string", e);
      return "\u001B[m\u001B[1;31m" + "读取日志时有错误发生！\n\u001B[m";
    } catch (ParseException e1) {
      logger.error("ParseException when parsing date string", e1);
      return "\u001B[m\u001B[1;31m" + "解析日志日期时有错误发生！\n\u001B[m";
    }
    try {
      return prettifyLog(resultBuilder.toString());
    } catch (IOException e) {
      logger.error("IOException when prettify string", e);
      return resultBuilder.toString();
    }
  }

  public static String searchLogsByType(String type) {
    return commonLogPattern("\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2} " + type + ".*");
  }

  /**.
   * search log by class name
   *
   * @param classString class name
   * @return the log
   */
  public static String searchLogsByClass(String classString) {
    return commonLogPattern(
            "\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2} (INFO|WARN|DEBUG|TRACE|ERROR)\\s+"
                    + classString + ".*");
  }

  /**.
   * search log by method name
   *
   * @param method method name
   * @return the log
   */
  public static String searchLogsByMethod(String method) {
    return commonLogPattern(
            "\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2} (INFO|WARN|DEBUG|TRACE|ERROR)"
                    + "\\s+[A-Za-z0-9_]+\\s+- \\["
                    + method
                    + "\\].*");
  }

  /**.
   * get all logs
   *
   * @return all logs
   */
  public static String allLogs() {
    try {
      return prettifyLog(logBuilder.toString());
    } catch (IOException e) {
      logger.error("IOException when parsing string", e);
      return logBuilder.toString();
    }
  }

  /**.
   * get log by the regular expression
   *
   * @param pattern the regular expression
   * @return the log
   */
  public static String commonLogPattern(String pattern) {
    BufferedReader stringReader = new BufferedReader(
            new InputStreamReader(
                    new ByteArrayInputStream(
                            logBuilder.toString().getBytes(Charset.forName("utf8"))),
                    Charset.forName("utf8")));
    StringBuilder resultBuilder = new StringBuilder();
    String line;
    try {
      while ((line = stringReader.readLine()) != null) {
        if (Pattern.matches(pattern, line)) {
          resultBuilder.append(line).append("\n");
        }
      }
      resultBuilder.append("\u001B[m");
    } catch (IOException e) {
      logger.error("IOException when parsing string", e);
      return "\u001B[m\u001B[1;31m" + "读取日志时有错误发生！\n\u001B[m";
    }
    try {
      return prettifyLog(resultBuilder.toString());
    } catch (IOException e) {
      logger.error("IOException when prettify string", e);
      return resultBuilder.toString();
    }
  }

  /**.
   * color the log
   *
   * @param log the log
   * @return the colored log
   * @throws IOException Stream exception
   */
  public static String prettifyLog(String log) throws IOException {
    BufferedReader stringReader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(
                    log.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
    StringBuilder resultBuilder = new StringBuilder();
    String line;
    String infoPattern = "\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2} INFO.*";
    String warnPattern = "\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2} WARN.*";
    String debugPattern = "\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2} DEBUG.*";
    String tracePattern = "\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2} TRACE.*";
    String errorPattern = "\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2} ERROR.*";
    while ((line = stringReader.readLine()) != null) {
      if (Pattern.matches(infoPattern, line)) {
        resultBuilder.append("\u001B[m\u001B[32m").append(line).append("\n");
      } else if (Pattern.matches(warnPattern, line)) {
        resultBuilder.append("\u001B[m\u001B[33m").append(line).append("\n");
      } else if (Pattern.matches(debugPattern, line)) {
        resultBuilder.append("\u001B[m\u001B[36m").append(line).append("\n");
      } else if (Pattern.matches(tracePattern, line)) {
        resultBuilder.append("\u001B[m\u001B[30m").append(line).append("\n");
      } else if (Pattern.matches(errorPattern, line)) {
        resultBuilder.append("\u001B[m\u001B[1;31m").append(line).append("\n");
      } else {
        logger.warn("Cannot prettify log {}", line);
        resultBuilder.append(line).append("\n");
      }
    }
    resultBuilder.append("\u001B[m");
    return resultBuilder.toString();
  }
}
