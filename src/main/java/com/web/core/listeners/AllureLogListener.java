package com.web.core.listeners;

import com.web.core.manager.driver.DriverManager;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureLogListener implements ITestListener, ISuiteListener {

private String testResult;
    @Override
    public void onStart(ISuite suite) {
        File directory = new File("./allure-results");
        try {
            deleteDirectory(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Attachment(value = "{0}", type = "text/plain")
    private static String saveTextLog(String message) {
        return message;
    }
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }
    private String getTestResult() {
        return testResult;
    }
    private void setTestResult(String testResult) {
        this.testResult = testResult;
    }
    @Attachment(value = "Page screenshot", type = "imag/png",fileExtension = ".png")
    private byte[] saveScreenshotPNG(WebDriver driver) {

    return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);


    }
    @Override
    public void onFinish(ISuite suite) {

    }

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("I am in onTestStart method " + getTestMethodName(result) + " start");

    }



    @Override
    public void onStart(ITestContext result) {

    //    result.setAttribute("WebDriver", DriverManager.getDriver());
        DriverManager.setDriver(DriverManager.getDriver());

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("I am in onTestFailure method " + getTestMethodName(result) + " failed");

        // Allure ScreenShotRobot and SaveTestLog
        if (DriverManager.getDriver()!=null) {
          Allure.addAttachment("Screen shot",new ByteArrayInputStream(saveScreenshotPNG(DriverManager.getDriver())));
            System.out.println("Screenshot captured for test case:" + getTestMethodName(result));
        }
        // Save a log on allure.
        saveTextLog(getTestMethodName(result) + " failed and screenshot taken!");

    }

    @Override
    public void onTestSkipped(ITestResult result) {


    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }



    @Override
    public void onFinish(ITestContext result) {

    }

    public static void deleteDirectory(File directory) throws IOException, IOException {
        Files.walk(directory.toPath())
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach(File::delete);
    }



}
