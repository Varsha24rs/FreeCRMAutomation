package ExtentReportListener;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG implements IReporter {
    private ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static void logEvent(String message) {
        if (extentTest.get() != null) {
            extentTest.get().info("🔹 " + message); // Logs event details to Extent Report
        }
    }

    public static void logFailure(String message) {
        if (extentTest.get() != null) {
            extentTest.get().fail("❌ " + message);
        }
    }

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        // Define the report file path
        String reportPath = outputDirectory + File.separator + "ExtentReport.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Test Execution Summary");
        sparkReporter.config().setTimelineEnabled(true);

        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Tester", "Varsha");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();

            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();

                buildTestNodes(context.getPassedTests(), Status.PASS);
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
            }
        }

        extent.flush(); // Save report
    }

    private void buildTestNodes(IResultMap tests, Status status) {
        ExtentTest test;
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extent.createTest(result.getMethod().getMethodName());
                extentTest.set(test);

                // 🔹 Log WebEventListener events FIRST
                ExtentReportNG.logEvent("📌 Starting test: " + result.getMethod().getMethodName());

                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));

                for (String group : result.getMethod().getGroups()) {
                    test.assignCategory(group);
                }

                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                } else {
                    test.log(status, "✅ Test " + status.toString().toLowerCase() + "ed");
                }
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
