package util;

import ExtentReportListener.ExtentReportNG;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalTime;

public class WebEventListener implements WebDriverListener {

    private String getTime() {
        return LocalTime.now().toString(); // Logs time for better debugging
    }

    public void beforeGet(WebDriver driver, String url) {
        String log = "🌍 [" + getTime() + "] Navigating to: " + url;
        System.out.println(log);
        ExtentReportNG.logEvent(log);
    }

    public void afterGet(WebDriver driver, String url) {
        String log = "✅ [" + getTime() + "] Successfully navigated to: " + url;
        System.out.println(log);
        ExtentReportNG.logEvent(log);
    }

    public void beforeClick(WebElement element) {
        String log = "🖱️ [" + getTime() + "] Attempting to click on: " + element.getText();
        System.out.println(log);
        ExtentReportNG.logEvent(log);
    }

    public void afterClick(WebElement element) {
        String log = "✅ [" + getTime() + "] Clicked on: " + element.getText();
        System.out.println(log);
        ExtentReportNG.logEvent(log);
    }

    public void beforeFindElement(WebDriver driver, By by) {
        String log = "🔍 [" + getTime() + "] Searching for element: " + by.toString();
        System.out.println(log);
        ExtentReportNG.logEvent(log);
    }

    public void afterFindElement(WebDriver driver, By by, WebElement element) {
        String log = "✅ [" + getTime() + "] Element found: " + by.toString();
        System.out.println(log);
        ExtentReportNG.logEvent(log);
    }

    public void beforeGetText(WebElement element) {
        String log = "📝 [" + getTime() + "] Fetching text from element";
        System.out.println(log);
        ExtentReportNG.logEvent(log);
    }

    public void afterGetText(WebElement element, String text) {
        String log = "✅ [" + getTime() + "] Text retrieved: " + text;
        System.out.println(log);
        ExtentReportNG.logEvent(log);
    }

    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        String log = "❌ Exception in method: " + method.getName() + " - " + e.getCause();
        System.out.println(log);
        ExtentReportNG.logFailure(log);

        // Check if the target is an instance of WebDriver
        if (target instanceof WebDriver) {
            WebDriver driver = (WebDriver) target;

            // Take a screenshot
            try {
                String screenshotPath = "will take later";
                TestUtil.takeScreenshotAtEndOfTest();
                if (screenshotPath != null) {
                    ExtentReportNG.logFailure("📸 Screenshot captured: " + screenshotPath);
                } else {
                    ExtentReportNG.logFailure("❌ Screenshot failed - WebDriver might be null!");
                }
            } catch (IOException ioException) {
                System.out.println("❌ Failed to capture screenshot: " + ioException.getMessage());
            }
        } else {
            System.out.println("⚠️ Warning: Target is not an instance of WebDriver. Screenshot skipped.");
        }
    }

    public void beforeQuit(WebDriver driver) {
        String log = "🛑 [" + getTime() + "] Closing the browser...";
        System.out.println(log);
        ExtentReportNG.logEvent(log);
    }

    public void afterQuit(WebDriver driver) {
        String log = "✅ [" + getTime() + "] Browser closed.";
        System.out.println(log);
        ExtentReportNG.logEvent(log);
    }
}
