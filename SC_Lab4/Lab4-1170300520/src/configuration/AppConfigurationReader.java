package configuration;

import configuration.Exception.ConfigurationLabelException;
import configuration.Exception.ConfigurationSyntaxException;
import factory.AppFactory;
import factory.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otherBean.InstallLog;
import otherBean.Relation;
import otherBean.UninstallLog;
import otherBean.UsageLog;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * the implement of a configuration reader of the app ecosystem
 *
 * @author Guo Ziyang
 */
public class AppConfigurationReader extends ConfigurationReader {

	private AppConfiguration appConfiguration;
	private UserFactory userFactory = new UserFactory();
	private static Set<String> appNameSet = new HashSet<>();

	protected Logger logger = LoggerFactory.getLogger(AppConfigurationReader.class);

	public AppConfigurationReader(File file) {
		super(file);
		configuration = new AppConfiguration();
		appConfiguration = (AppConfiguration) configuration;
		appNameSet.clear();
	}

	@Override
	public void checkRep() {
		super.checkRep();
		assert configuration != null;
		assert appConfiguration != null;
		assert appNameSet != null;
	}

	@Override
	public void parseConfiguration(String line) {
		checkRep();
		if (line.startsWith("User ::= ")) {
			Pattern namePattern = Pattern.compile("(?<=User ::= )[A-Za-z0-9]+$");
			Matcher nameMatcher = namePattern.matcher(line);
			if (nameMatcher.find()) {
				logger.info("User: {}", nameMatcher.group());
				appConfiguration.setUser(userFactory.build(nameMatcher.group()));
			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of user name",
						lineNumber, file.getName());
				logger.error("Wrong format of user name", e);
				throw e;
			}
		} else if (line.startsWith("App ::= <")) {
			Pattern appPattern = Pattern
					.compile("(?<=App ::= <)([A-Za-z0-9]+,){2}[a-zA-Z0-9._-]+,\".+?\",\".+?\"(?=>)");
			Matcher appMatcher = appPattern.matcher(line);
			AppFactory appFactory = new AppFactory();
			if (appMatcher.find()) {
				String string = appMatcher.group();
				logger.info("App: {}", string);
				String[] splits = string.split(",");
				if (appNameSet.contains(splits[0])) {
					ConfigurationLabelException e = new ConfigurationLabelException("Duplicate app with same name",
							lineNumber, file.getName());
					logger.error("Duplicate app with same name", e);
					throw e;
				} else {
					appNameSet.add(splits[0]);
				}
				appConfiguration.addApps(appFactory.build(splits[0], splits[1], splits[2],
						splits[3].substring(1, splits[3].length() - 1),
						splits[4].substring(1, splits[4].length() - 1)));
			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of app", lineNumber,
						file.getName());
				logger.error("Wrong format of app", e);
				throw e;
			}
		} else if (line.startsWith("InstallLog ::= <")) {
			Pattern installLogPattern = Pattern
					.compile("(?<=InstallLog ::= <)\\d{4}(-\\d{2}){2},\\d{2}(:\\d{2}){2},[A-Za-z0-9]+(?=>)");
			Matcher installLogMatcher = installLogPattern.matcher(line);
			if (installLogMatcher.find()) {
				String string = installLogMatcher.group();
				logger.info("InstallLog: {}", string);
				String[] splits = string.split(",");
				Calendar calendar = string2Calender(splits[0] + " " + splits[1]);
				appConfiguration.addInstallLog(new InstallLog(calendar, splits[2]));
			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of install log",
						lineNumber, file.getName());
				logger.error("Wrong format of install log", e);
				throw e;
			}
		} else if (line.startsWith("UsageLog ::= <")) {
			Pattern usageLogPattern = Pattern
					.compile("(?<=UsageLog ::= <)\\d{4}(-\\d{2}){2},\\d{2}(:\\d{2}){2},[A-Za-z0-9]+,\\d+(?=>)");
			Matcher usageLogMatcher = usageLogPattern.matcher(line);
			if (usageLogMatcher.find()) {
				String string = usageLogMatcher.group();
				logger.info("UsageLog: {}", string);
				String[] splits = string.split(",");
				Calendar calendar = string2Calender(splits[0] + " " + splits[1]);
				int duration;
				try {
					duration = Integer.parseInt(splits[3]);
				} catch (NumberFormatException e) {
					ConfigurationSyntaxException e1 = new ConfigurationSyntaxException("Wrong format of duration",
							lineNumber, file.getName());
					logger.error("Wrong format of duration", e1);
					throw e1;
				}
				appConfiguration.addUsageLog(new UsageLog(calendar, splits[2], duration));
			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of usage log",
						lineNumber, file.getName());
				logger.error("Wrong format of usage log", e);
				throw e;
			}
		} else if (line.startsWith("UninstallLog ::= <")) {
			Pattern uninstallLogPattern = Pattern
					.compile("(?<=UninstallLog ::= <)\\d{4}(-\\d{2}){2},\\d{2}(:\\d{2}){2},[A-Za-z0-9]+(?=>)");
			Matcher uninstallLogMatcher = uninstallLogPattern.matcher(line);
			if (uninstallLogMatcher.find()) {
				String string = uninstallLogMatcher.group();
				logger.info("uninstallLog: {}", string);
				String[] splits = string.split(",");
				Calendar calendar = string2Calender(splits[0] + " " + splits[1]);
				appConfiguration.addUninstallLog(new UninstallLog(calendar, splits[2]));
			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of uninstall log",
						lineNumber, file.getName());
				logger.error("Wrong format of uninstall log", e);
				throw e;
			}
		} else if (line.startsWith("Relation ::= <")) {
			Pattern relationPattern = Pattern.compile("(?<=Relation ::= <)([A-Za-z0-9]+),([A-Za-z0-9]+)(?=>)");
			Matcher relationMatcher = relationPattern.matcher(line);
			if (relationMatcher.find()) {
				String string = relationMatcher.group();
				logger.info("Relation: {}", string);
				String[] splits = string.split(",");
				if (splits[0].equals(splits[1])) {
					ConfigurationLabelException e = new ConfigurationLabelException(
							"Invalid relation with duplicate app name", lineNumber, file.getName());
					logger.error("Invalid relation with duplicate app name", e);
					throw e;
				}
				appConfiguration.addRelations(new Relation(splits[0], splits[1]));
			} else {
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of uninstall log",
						lineNumber, file.getName());
				logger.error("Wrong format of uninstall log", e);
				throw e;
			}
		} else if (line.startsWith("Period ::= ")) {
			Pattern periodPattern = Pattern.compile("(?<=Period ::= )((Hour)|(Day)|(Week)|(Month))$");
			Matcher periodMatcher = periodPattern.matcher(line);
			if (periodMatcher.find()) {
				String period = periodMatcher.group();
				logger.info("Period: {}", period);
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
				ConfigurationSyntaxException e = new ConfigurationSyntaxException("Wrong format of period", lineNumber,
						file.getName());
				logger.error("Wrong format of period", e);
				throw e;
			}
		} else {
			String s = "Invalid label '" + line.substring(0, line.indexOf(" ")) + "'";
			ConfigurationSyntaxException e = new ConfigurationSyntaxException(s, lineNumber, file.getName());
			logger.error(s, e);
			throw e;
		}
	}

	private Calendar string2Calender(String string) {
		checkRep();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(string);
		} catch (ParseException e) {
			ConfigurationSyntaxException e1 = new ConfigurationSyntaxException("Wrong date format", lineNumber,
					file.getName());
			logger.error("Wrong date format", e1);
			throw e1;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

}
