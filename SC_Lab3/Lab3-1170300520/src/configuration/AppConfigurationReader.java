package configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import factory.AppFactory;
import factory.UserFactory;
import otherBean.InstallLog;
import otherBean.Relation;
import otherBean.UninstallLog;
import otherBean.UsageLog;

/**
 * the implement of a configuration reader of the app ecosystem
 * 
 * @author Guo Ziyang
 */
public class AppConfigurationReader implements ConfigurationReader {
	
	private File file;
	private AppConfiguration configuration;
	private UserFactory userFactory = new UserFactory();
	
	protected Logger logger = LoggerFactory.getLogger(AppConfigurationReader.class);
	
	public AppConfigurationReader(File file) {
		this.file = file;
		configuration = new AppConfiguration();
	}
	
	
	@Override
	public AppConfiguration readFile() {
		if(!file.exists()) {
			logger.error("file does not exist", new FileNotFoundException(file.getName()));
			return null;
		}
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null) {
				if(line.isBlank()) {
					continue;
				} else {
					builder.append(line);
				}
			}
			parseConfiguration(builder.toString());
		} catch (IOException e) {
			logger.error("error happened when parsing configuration file", e);
			return null;
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("error happened when closing the stream", e);
				}
			}
		}
		return configuration;
	}

	@Override
	public void parseConfiguration(String line) {
		Pattern namePattern = Pattern.compile("(?<=User ::= )[A-Za-z0-9]+");
		Matcher nameMatcher = namePattern.matcher(line);
		if(nameMatcher.find()) {
			logger.info("User: {}", nameMatcher.group());
			configuration.setUser(userFactory.build(nameMatcher.group()));
		} else {
			logger.error("Missing configuration: {}", "User");
			return;
		}
		
		Pattern appPattern = Pattern.compile("(?<=App ::= <)([A-Za-z0-9]+,){2}[a-zA-Z0-9._-]+,\".+?\",\".+?\"(?=>)");
		Matcher appMatcher = appPattern.matcher(line);
		AppFactory appFactory = new AppFactory();
		if(appMatcher.groupCount() == 0) {
			logger.error("Missing configuration: {}", "App");
			return;
		}
		while(appMatcher.find()) {
			String string = appMatcher.group();
			logger.info("App: {}", string);
			String[] splits = string.split(",");
			configuration.addApps(appFactory.build(splits[0], splits[1], splits[2], splits[3].substring(1, splits[3].length() - 1), splits[4].substring(1, splits[4].length() - 1)));
		}
		
		Pattern installLogPattern = Pattern.compile("(?<=InstallLog ::= <)\\d{4}(-\\d{2}){2},\\d{2}(:\\d{2}){2},[A-Za-z0-9]+(?=>)");
		Matcher installLogMatcher = installLogPattern.matcher(line);
		if(installLogMatcher.groupCount() == 0) {
			logger.error("Missing configuration: {}", "InstallLog");
			return;
		}
		while(installLogMatcher.find()) {
			String string = installLogMatcher.group();
			logger.info("InstallLog: {}", string);
			String[] splits = string.split(",");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = sdf.parse(splits[0] + " " + splits[1]);
			} catch (ParseException e) {
				logger.error("Error parse date {}", splits[0] + " " + splits[1], e);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			configuration.addInstallLog(new InstallLog(calendar, splits[2]));
		}
		
		Pattern usageLogPattern = Pattern.compile("(?<=UsageLog ::= <)\\d{4}(-\\d{2}){2},\\d{2}(:\\d{2}){2},[A-Za-z0-9]+,\\d+(?=>)");
		Matcher usageLogMatcher = usageLogPattern.matcher(line);
		if(usageLogMatcher.groupCount() == 0) {
			logger.error("Missing configuration: {}", "UsageLog");
			return;
		}
		while(usageLogMatcher.find()) {
			String string = usageLogMatcher.group();
			logger.info("UsageLog: {}", string);
			String[] splits = string.split(",");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = sdf.parse(splits[0] + " " + splits[1]);
			} catch (ParseException e) {
				logger.error("Error parse date {}", splits[0] + " " + splits[1], e);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			configuration.addUsageLog(new UsageLog(calendar, splits[2], Integer.valueOf(splits[3])));
		}
		
		Pattern uninstallLogPattern = Pattern.compile("(?<=UninstallLog ::= <)\\d{4}(-\\d{2}){2},\\d{2}(:\\d{2}){2},[A-Za-z0-9]+(?=>)");
		Matcher uninstallLogMatcher = uninstallLogPattern.matcher(line);
		if(uninstallLogMatcher.groupCount() == 0) {
			logger.error("Missing configuration: {}", "UninstallLog");
			return;
		}
		while(uninstallLogMatcher.find()) {
			String string = uninstallLogMatcher.group();
			logger.info("uninstallLog: {}", string);
			String[] splits = string.split(",");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = sdf.parse(splits[0] + " " + splits[1]);
			} catch (ParseException e) {
				logger.error("Error parse date {}", splits[0] + " " + splits[1], e);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			configuration.addUninstallLog(new UninstallLog(calendar, splits[2]));
		}
		
		Pattern relationPattern = Pattern.compile("(?<=Relation ::= <)([A-Za-z0-9]+),([A-Za-z0-9]+)(?=>)");
		Matcher relationMatcher = relationPattern.matcher(line);
		if(relationMatcher.groupCount() == 0) {
			logger.error("Missing configuration: {}", "Relation");
			return;
		}
		while(relationMatcher.find()) {
			String string = relationMatcher.group();
			logger.info("Relation: {}", string);
			String[] splits = string.split(",");
			configuration.addRelations(new Relation(splits[0], splits[1]));
		}
		Pattern periodPattern = Pattern.compile("(?<=Period ::= )((Hour)|(Day)|(Week)|(Month))");
		Matcher periodMatcher = periodPattern.matcher(line);
		if(periodMatcher.groupCount() == 0) {
			logger.error("Missing configuration: {}", "Period");
			return;
		}
		if(periodMatcher.find()) {
			String period = periodMatcher.group();
			logger.info("Period: {}", period);
			switch(period) {
				case "Hour": configuration.setPeriod(AppConfiguration.HOUR);break;
				case "Day": configuration.setPeriod(AppConfiguration.DAY);break;
				case "Week": configuration.setPeriod(AppConfiguration.WEEK);break;
				case "Month": configuration.setPeriod(AppConfiguration.MONTH);break;
			}
		}
	}
}
