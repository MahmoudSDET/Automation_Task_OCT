

package com.web.core.utils;




import java.io.IOException;
import java.util.Properties;

/*Singleton Design pattern*/
public class ConfigLoader {

	private static final String SEND_EMAIL_TO_USERS = "send_email_to_users";
	private static final String OVERRIDE_REPORTS = "override_reports";
	private static final String SKIPPED_STEPS_SCREENSHOT = "skipped_steps_screenshot";
	private static final String PASSED_STEPS_SCREENSHOT = "passed_steps_screenshot";
	private static final String FAILED_STEPS_SCREENSHOT = "failed_steps_screenshot";
	private static final String RETRY_FAILED_TESTS = "retry_failed_tests";
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";
	private static final String BASE_URL = "baseUrl";
	private static final String REQUEST_DETAILS_IN_REPORTS = "request_details_in_reports";
	private static final String ENV = "env";
	private static final String ENV_Type="envtype";
	private static final String CONFIG_PROPERTIES = "_config.properties";

	/* Default config file is stg_config.properties */


	private static final String TEST_CONFIG_PROPERTIES = "test" + CONFIG_PROPERTIES;


	private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources/";
	private Properties properties;

	private static ConfigLoader configLoader;

	private ConfigLoader() throws IOException {

		/* Setting EnvType.STAGE as default environment */
		/*
		 * This will check for the env value from Jenkins/Maven first. If it does not
		 * get any input from Jenkins/mvn cmd line, then, will take
		 * stg_config.properties file as default
		 */

		//properties=getConfigPropertyFile(Global_CONFIG_PROPERTIES);
		properties=new Properties();
		properties = getConfigPropertyFile(TEST_CONFIG_PROPERTIES);



	}

	private Properties getConfigPropertyFile(String configFile) {
		return PropertyUtils.propertyLoader(RESOURCES_PATH +configFile);
	}

	private String getPropertyValue(String propertyKey) {
		String prop = properties.getProperty(propertyKey);
		if (prop != null) {
			return prop.trim();
		} else {
			throw new RuntimeException("Property " + propertyKey + " is not specified in the config.properties file");
		}
	}

	public static ConfigLoader getInstance() throws IOException {
		if (configLoader == null) {
			configLoader = new ConfigLoader();
		}
		return configLoader;
	}

	public String getBaseUrl() {
		return getPropertyValue(BASE_URL);
	}



	public String getFailedStepsScreenshot() {
		return getPropertyValue(FAILED_STEPS_SCREENSHOT);
	}

	public String getPassedStepsScreenshot() {
		return getPropertyValue(PASSED_STEPS_SCREENSHOT);
	}

	public String getSkippedStepsScreenshot() {
		return getPropertyValue(SKIPPED_STEPS_SCREENSHOT);
	}

	public String getRetryFailedTests() {
		return getPropertyValue(RETRY_FAILED_TESTS);
	}

	public String getOverrideReports() {
		return getPropertyValue(OVERRIDE_REPORTS);
	}

	public String getSendEmailToUsers() {
		return getPropertyValue(SEND_EMAIL_TO_USERS);
	}

	public String getRequestDetailsInReports() {
		return getPropertyValue(REQUEST_DETAILS_IN_REPORTS);
	}

}
