package testcases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import util.TestUtil;

import java.nio.file.Paths;

public class ContactPageTest extends TestBase {
    LoginPage loginpage;
    HomePage homePage;
    TestUtil testUtil;
    ContactsPage contactsPage;

    public ContactPageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        testUtil = new TestUtil();
        loginpage = new LoginPage();
        homePage = loginpage.login(properties.getProperty("username"), properties.getProperty("password"));
        testUtil.switchToFrame("mainpanel");
        contactsPage = homePage.selectContactTab();
    }

    @Test(priority = 1)
    public void verifyContactsLabel() {
        Assert.assertTrue(contactsPage.contactLabelVerification(), "Contacts label is missing");
    }

    @Test(priority = 2)
    public void selectSingleContact() {
        contactsPage.selectContactByName("Aarav Mehra");
    }

    @Test(priority = 3)
    public void selectMultipleContact() {
        contactsPage.selectContactByName("Aarav Mehra");
        contactsPage.selectContactByName("00Himanshu sharma");
    }

    @DataProvider
    public Object[][] getNewContactData() {
        //C:\Users\kumariv\IdeaProjects\TestAutomations\src\main\java\testdata\NewContactDetails.xlsx
        String filePath = Paths.get("C:", "Users", "kumariv", "IdeaProjects", "TestAutomations", "src", "main", "java", "testdata", "NewContactDetails.xlsx").toString();
        Object[][] data = TestUtil.excelReader(filePath, "Sheet1");
        return data;
    }

    @Test(priority = 4, dataProvider = "getNewContactData")
    public void createNewContact(String title, String firstName, String middleName, String lastName) {
        homePage.selectNewContactDropdown();
        contactsPage.createNewContact(title, firstName, middleName, lastName);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
