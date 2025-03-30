package testcases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import static org.testng.Assert.assertEquals;

public class LoginPageTest extends TestBase {

    LoginPage loginPage;
    HomePage homePage;
    //Assertion assert;

    public LoginPageTest(){
        super(); //To call testbase class constructor
    }

    @BeforeMethod
    public void setUp(){
        initialization();
        loginPage = new LoginPage();
    }

    @Test(priority = 1)
    public void loginPageTitleTest(){
        String title = loginPage.getLoginPageTitle();
        //System.out.println("title is:" + title);
        Assert.assertEquals(title, "Free CRMM software for customer relationship management, sales, and support.");
    }
    @Test(priority = 2)
    public void LoginPageCRMImgTest(){
        boolean found = loginPage.validateLoginPage();
        Assert.assertTrue(found);
    }

    @Test(priority = 3)
    public void loginTest(){
        homePage = loginPage.login(properties.getProperty("username"),properties.getProperty("password"));
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
