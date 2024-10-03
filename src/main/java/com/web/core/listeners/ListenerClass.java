

package com.web.core.listeners;

import java.io.IOException;
import java.util.Arrays;

import com.web.core.constants.FrameworkConstants;
import com.web.core.reports.ExtentLogger;
import com.web.core.reports.ExtentReport;
import com.web.core.utils.EmailSendUtils;
import com.web.core.utils.ZipUtils;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.web.core.annotations.FrameworkAnnotation;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ListenerClass implements ITestListener, ISuiteListener {

	static int count_passedTCs;
	static int count_skippedTCs;
	static int count_failedTCs;
	static int count_totalTCs;

	public void onStart(ISuite suite) {
        try {
            ExtentReport.initReports();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	public void onFinish(ISuite suite) {
		ExtentReport.flushReports();
		ZipUtils.zip();
		//EmailSendUtils.sendEmail(count_totalTCs, count_passedTCs, count_failedTCs, count_skippedTCs);
	}

	public void onTestStart(ITestResult result) {

		// System.out.println("onTestStart() ");
		count_totalTCs = count_totalTCs + 1;
	//	ExtentReport.createTest(result.getMethod().getDescription());
		// ExtentReport.createTest(result.getMethod().getDescription());

		ExtentReport.addAuthors(result.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(FrameworkAnnotation.class).author());

		ExtentReport.addCategories(result.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(FrameworkAnnotation.class).category());


		// ExtentLogger.info("<b>" +
		// BrowserOSInfoUtils.getOS_Browser_BrowserVersionInfo() + "</b>");

	}

	public void onTestSuccess(ITestResult result) {
		count_passedTCs = count_passedTCs + 1;

		String logText = "<b>" + result.getMethod().getMethodName() + " is passed.</b>" + "  " + FrameworkConstants.ICON_SMILEY_PASS;
		Markup markup_message = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		// ExtentLogger.pass(markup_message, true);
		ExtentLogger.pass(markup_message);

	}

	public void onTestFailure(ITestResult result) {
		count_failedTCs = count_failedTCs + 1;
		ExtentLogger.fail(FrameworkConstants.ICON_BUG + "  " + "<b><i>" + result.getThrowable().toString() + "</i></b>");
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
	
		String message = "<details><summary><b><font color=red> Exception occured, click to see details: "
				+ FrameworkConstants.ICON_SMILEY_FAIL + " </font></b>" + "</summary>" + exceptionMessage.replaceAll(",", "<br>")
				+ "</details> \n";
		ExtentLogger.fail(message);
		
		String logText = "<b>" + result.getMethod().getMethodName() + " is failed.</b>" + "  " + FrameworkConstants.ICON_SMILEY_FAIL;
		Markup markup_message = MarkupHelper.createLabel(logText, ExtentColor.RED);
        try {
            ExtentLogger.fail(markup_message, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

	public void onTestSkipped(ITestResult result) {

		count_skippedTCs = count_skippedTCs + 1;

		ExtentLogger.skip(FrameworkConstants.ICON_BUG + "  " + "<b><i>" + result.getThrowable().toString() + "</i></b>");
		String logText = "<b>" + result.getMethod().getMethodName() + " is skipped.</b>" + "  " + FrameworkConstants.ICON_SMILEY_FAIL;
		Markup markup_message = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        try {
            ExtentLogger.skip(markup_message, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		/*
		 * As of now, we are not using it
		 */
	}

	public void onStart(ITestContext result) {
		/*
		 * As of now, we are not using it.
		 * 
		 */
	}

	public void onFinish(ITestContext result) {
		/*
		 * As of now, we are not using it.
		 * 
		 */
	}

}
