package configuration;

import configuration.exception.ConfigurationLabelException;
import configuration.exception.ConfigurationSyntaxException;
import factory.AppFactory;
import factory.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otherbean.InstallLog;
import otherbean.Relation;
import otherbean.UninstallLog;
import otherbean.UsageLog;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppParser implements Parser {

  private AppConfiguration appConfiguration;

  private long lineNumber;
  private String fileName;

  private UserFactory userFactory = new UserFactory();
  public static Set<String> appNameSet = new HashSet<>();

  private Logger logger = LoggerFactory.getLogger(AppParser.class);

  private static final String USER_START = "User ::= ";
  private static final String APP_START = "App ::= ";
  private static final String INSTALL_LOG_START = "InstallLog ::= <";
  private static final String USAGE_LOG_START = "UsageLog ::= <";
  private static final String UNINSTALL_LOG_START = "UninstallLog ::= ";
  private static final String RELATION_START = "Relation ::= ";
  private static final String PERIOD_START = "Period ::= ";

  private static final Pattern NAME_PATTERN = Pattern.compile(
          "(?<=User ::= )[A-Za-z0-9]+$");
  private static final Pattern APP_PATTERN = Pattern.compile(
          "(?<=App ::= <)([A-Za-z0-9]+,){2}[a-zA-Z0-9._-]+,\".+?\",\".+?\"(?=>)");
  private static final Pattern INSTALL_LOG_PATTERN = Pattern.compile(
          "(?<=InstallLog ::= <)\\d{4}(-\\d{2}){2},\\d{2}(:\\d{2}){2},[A-Za-z0-9]+(?=>)");
  private static final Pattern USAGE_LOG_PATTERN = Pattern.compile(
          "(?<=UsageLog ::= <)\\d{4}(-\\d{2}){2},\\d{2}(:\\d{2}){2},[A-Za-z0-9]+,\\d+(?=>)");
  private static final Pattern UNINSTALL_LOG_PATTERN = Pattern.compile(
          "(?<=UninstallLog ::= <)\\d{4}(-\\d{2}){2},\\d{2}(:\\d{2}){2},[A-Za-z0-9]+(?=>)");
  private static final Pattern RELATION_PATTERN = Pattern.compile(
          "(?<=Relation ::= <)([A-Za-z0-9]+),([A-Za-z0-9]+)(?=>)");
  private static final Pattern PERIOD_PATTERN = Pattern.compile(
          "(?<=Period ::= )((Hour)|(Day)|(Week)|(Month))$");

  @Override
  public void parseConfiguration(String line, Configuration configuration,
                                 long lineNumber, String fileName) {
    this.lineNumber = lineNumber;
    this.fileName = fileName;
    appConfiguration = (AppConfiguration) configuration;
    if (line.startsWith(USER_START)) {
      Matcher nameMatcher = NAME_PATTERN.matcher(line);
      if (nameMatcher.find()) {
        appConfiguration.setUser(userFactory.build(nameMatcher.group()));
      } else {
        ConfigurationSyntaxException e = new ConfigurationSyntaxException(
                "Wrong format of user name", lineNumber, fileName);
        logger.error("Wrong format of user name", e);
        throw e;
      }
    } else if (line.startsWith(APP_START)) {
      Matcher appMatcher = APP_PATTERN.matcher(line);
      AppFactory appFactory = new AppFactory();
      if (appMatcher.find()) {
        String string = appMatcher.group();
        String[] splits = string.split(",");
        if (appNameSet.contains(splits[0])) {
          ConfigurationLabelException e = new ConfigurationLabelException(
                  "Duplicate app with same name", lineNumber, fileName);
          logger.error("Duplicate app with same name", e);
          throw e;
        } else {
          appNameSet.add(splits[0]);
        }
        appConfiguration.addApps(appFactory.build(splits[0], splits[1], splits[2],
                splits[3].substring(1, splits[3].length() - 1),
                splits[4].substring(1, splits[4].length() - 1)));
      } else {
        ConfigurationSyntaxException e = new ConfigurationSyntaxException(
                "Wrong format of app", lineNumber, fileName);
        logger.error("Wrong format of app", e);
        throw e;
      }
    } else if (line.startsWith(INSTALL_LOG_START)) {
      Matcher installLogMatcher = INSTALL_LOG_PATTERN.matcher(line);
      if (installLogMatcher.find()) {
        String string = installLogMatcher.group();
        String[] splits = string.split(",");
        Calendar calendar = string2Calender(splits[0] + " " + splits[1]);
        appConfiguration.addInstallLog(new InstallLog(calendar, splits[2]));
      } else {
        ConfigurationSyntaxException e = new ConfigurationSyntaxException(
                "Wrong format of install log", lineNumber, fileName);
        logger.error("Wrong format of install log", e);
        throw e;
      }
    } else if (line.startsWith(USAGE_LOG_START)) {
      Matcher usageLogMatcher = USAGE_LOG_PATTERN.matcher(line);
      if (usageLogMatcher.find()) {
        String string = usageLogMatcher.group();
        String[] splits = string.split(",");
        Calendar calendar = string2Calender(splits[0] + " " + splits[1]);
        int duration;
        try {
          duration = Integer.parseInt(splits[3]);
        } catch (NumberFormatException e) {
          ConfigurationSyntaxException e1 = new ConfigurationSyntaxException(
                  "Wrong format of duration", lineNumber, fileName);
          logger.error("Wrong format of duration", e1);
          throw e1;
        }
        appConfiguration.addUsageLog(new UsageLog(calendar, splits[2], duration));
      } else {
        ConfigurationSyntaxException e = new ConfigurationSyntaxException(
                "Wrong format of usage log", lineNumber, fileName);
        logger.error("Wrong format of usage log", e);
        throw e;
      }
    } else if (line.startsWith(UNINSTALL_LOG_START)) {
      Matcher uninstallLogMatcher = UNINSTALL_LOG_PATTERN.matcher(line);
      if (uninstallLogMatcher.find()) {
        String string = uninstallLogMatcher.group();
        String[] splits = string.split(",");
        Calendar calendar = string2Calender(splits[0] + " " + splits[1]);
        appConfiguration.addUninstallLog(new UninstallLog(calendar, splits[2]));
      } else {
        ConfigurationSyntaxException e = new ConfigurationSyntaxException(
                "Wrong format of uninstall log", lineNumber, fileName);
        logger.error("Wrong format of uninstall log", e);
        throw e;
      }
    } else if (line.startsWith(RELATION_START)) {
      Matcher relationMatcher = RELATION_PATTERN.matcher(line);
      if (relationMatcher.find()) {
        String string = relationMatcher.group();
        String[] splits = string.split(",");
        if (splits[0].equals(splits[1])) {
          ConfigurationLabelException e = new ConfigurationLabelException(
                  "Invalid relation with duplicate app name", lineNumber, fileName);
          logger.error("Invalid relation with duplicate app name", e);
          throw e;
        }
        appConfiguration.addRelations(new Relation(splits[0], splits[1]));
      } else {
        ConfigurationSyntaxException e = new ConfigurationSyntaxException(
                "Wrong format of uninstall log", lineNumber, fileName);
        logger.error("Wrong format of uninstall log", e);
        throw e;
      }
    } else if (line.startsWith(PERIOD_START)) {
      Matcher periodMatcher = PERIOD_PATTERN.matcher(line);
      if (periodMatcher.find()) {
        String period = periodMatcher.group();
        switch (period) {
          case "Hour":
            appConfiguration.setPeriod(AppConfiguration.HOUR);
            break;
          case "Day":
            appConfiguration.setPeriod(AppConfiguration.DAY);
            break;
          case "Week":
            appConfiguration.setPeriod(AppConfiguration.WEEK);
            break;
          case "Month":
            appConfiguration.setPeriod(AppConfiguration.MONTH);
            break;
          default:
            logger.error("Unknown error occors!", new RuntimeException());
        }
      } else {
        ConfigurationSyntaxException e = new ConfigurationSyntaxException(
                "Wrong format of period", lineNumber, fileName);
        logger.error("Wrong format of period", e);
        throw e;
      }
    } else {
      String s = "Invalid label '" + line.substring(0, line.indexOf(" ")) + "'";
      ConfigurationSyntaxException e = new ConfigurationSyntaxException(
              s, lineNumber, fileName
      );
      logger.error(s, e);
      throw e;
    }
  }

  private Calendar string2Calender(String string) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date;
    try {
      date = sdf.parse(string);
    } catch (ParseException e) {
      ConfigurationSyntaxException e1 = new ConfigurationSyntaxException(
              "Wrong date format", lineNumber, fileName);
      logger.error("Wrong date format", e1);
      throw e1;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

}
