package otherBean;

/**
 * the relation in the personal app ecosystem
 * 
 * @author Guo Ziyang
 */
public class Relation {
	
	private final String appName1;
	private final String appName2;
	
	public Relation(String appName1, String appName2) {
		this.appName1 = appName1;
		this.appName2 = appName2;
	}

	/**
	 * get the name of app1
	 * 
	 * @return the name of app1
	 */
	public String getAppName1() {
		return appName1;
	}

	/**
	 * get the name of app2
	 * 
	 * @return the name of app2
	 */
	public String getAppName2() {
		return appName2;
	}
	
}
