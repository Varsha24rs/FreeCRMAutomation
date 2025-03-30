package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverListener;
import util.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import util.WebEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static util.TestUtil.PAGE_LOAD_TIMEOUT;

public class TestBase {
    public static WebDriver driver;
    public static Properties properties;
    public static WebDriver baseDriver;

    public TestBase() {
        try{
        properties = new Properties();
        FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/config/"+"config.properties");
        properties.load(ip);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void initialization() {
        String browsername = properties.getProperty("browser");

        if (browsername.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            baseDriver = new ChromeDriver();
        } else {
            System.out.println("Please enter valid browser name");
        }

        // Use Selenium 4's EventFiringDecorator
        WebDriverListener eventListener = new WebEventListener();
        driver = new EventFiringDecorator<>(eventListener).decorate(baseDriver);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));

        driver.get(properties.getProperty("url"));
    }
}
