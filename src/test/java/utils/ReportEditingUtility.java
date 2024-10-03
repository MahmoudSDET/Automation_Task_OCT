package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Step;
import org.testng.ITestResult;
import org.testng.Reporter;

public class ReportEditingUtility {
    private static AllureLifecycle lifecycle = Allure.getLifecycle();

    public static void editTestCaseNameAndDescription(String testcase){

        lifecycle.updateTestCase(testResult -> testResult.setName(testcase));
        lifecycle.updateTestCase(testResult -> testResult.setDescription(testcase));

    }


    public static void Description_for_Scenario(String description){
        ITestResult report = Reporter.getCurrentTestResult();
        report.setTestName(description);

    }
}
