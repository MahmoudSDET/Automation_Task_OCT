
package com.web.core.tests;

import com.web.core.enums.DriverType;
import com.web.core.listeners.AllureLogListener;
import com.web.core.listeners.ListenerClass;
import com.web.core.listeners.MethodInterceptor;

import com.web.core.manager.driver.DriverManager;
import com.web.core.manager.driver.DriverManagerFactory;

import com.web.core.reports.ExtentLogger;
import com.web.core.reports.ExtentReport;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import static com.web.core.constants.FrameworkConstants.*;
@Listeners(value = {
		AllureLogListener.class
		, MethodInterceptor.class, ListenerClass.class })
public class BaseTest {


	protected WebDriver getDriver() {
		// return this.driver.get();
		return DriverManager.getDriver();
	}

	private void setDriver(WebDriver driver) {
		// this.driver.set(driver);
		DriverManager.setDriver(driver);
	}

	/*
	 * @Optional -> You can run the test case individually directly from Java class
	 */
	@Parameters("browser")
	@BeforeClass
	public synchronized void startDriver(@Optional ("CHROME") String browser) {
        ExtentReport.createTest("Start Driver");
		ExtentLogger.info("This Test is created to insure the driver is created");
		// System.out.println("@BeforeMethod: @BeforeMethod" + browser);
		browser = setBrowserValue(browser);

		// setDriver(new DriverManagerOriginal().initializeDriver(browser));
		// setDriver(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createDriver());

		// driver = new DriverManager().initializeDriver(browser);
		// setDriver(new OriginalDriverManager().initializeDriver(browser));
		setDriver(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createDriver());
		ExtentLogger.info("This Test is created to insure the driver is created");
		System.out.println("Current Thread info = " + Thread.currentThread().getId() + ", Driver = " + getDriver());
	}

	@AfterClass
	public void terminateDriver(){
		getDriver().quit();
	}
	private String setBrowserValue(String browser) {



		/* This is for test case execution individually from Java class */
		if (browser == null) {
			// browser = BrowserType.EDGE;
			// browser = String.valueOf(BrowserType.EDGE);
			// browser = BrowserType.EDGE.toString().toUpperCase();
			// browser = (BrowserType.EDGE).name().toLowerCase();
			browser = "EDGE";
			System.out.println(
					"Test execution not done by Maven cmd or TestNG.xml file ->  setting the value: " + "EDGE");
		}

		/* This is for test case execution from Maven command line or testng.xml file */
		browser = System.getProperty("browser", browser);
		return browser;
	}



}
