package testcases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import util.TestUtil;

public class HomePageTest extends TestBase {

    LoginPage loginPage;
    HomePage homePage;
    TestUtil testUtil;

    public HomePageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        loginPage = new LoginPage();
        testUtil = new TestUtil();
        homePage = loginPage.login(properties.getProperty("username"), properties.getProperty("password"));
    }

    @Test(priority = 1)
    public void homePageTitleTest() {
        String title = homePage.getHomePageTitle();
        //System.out.println("Title of homepage is "+title);
        Assert.assertEquals(title, "CRMPRO", "Home page title not matched");
    }

    @Test(priority = 2)
    public void verifyUsernameAfterLogin() {
        testUtil.switchToFrame("mainpanel");
        Assert.assertTrue(homePage.usernamePostLogin());
    }

    @Test(priority = 3)
    public void clickOnContactTab() {
        testUtil.switchToFrame("mainpanel");
        homePage.selectContactTab();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
