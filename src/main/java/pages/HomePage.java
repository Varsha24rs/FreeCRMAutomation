package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends TestBase {

    //Page Factory or Obj Repo

    @FindBy(xpath="//*[@class='logo_text' and text()='CRMPRO']")
    WebElement txt_homepagelogo;

    @FindBy(xpath="//*[@class='headertext' and contains(text(),'Gagan Khanna')]")
    @CacheLookup  //Instead of fetching its value from the page class, it will store and fetch from cache --> This would speed up the framework execution
    //If the page gets refreshed after storing data in cache, stable element exception will come

    WebElement usernameplaceholder;

    @FindBy(xpath="//a[contains(text(),'Contacts')]")
    WebElement tab_Contacts;

    @FindBy(xpath="//a[contains(text(),'New Contact')]")
    WebElement dropdown_newContact;

    public HomePage(){
        PageFactory.initElements(driver,this);
    }

    //Actions
    public String getHomePageTitle(){
        return driver.getTitle();
    }

    public boolean usernamePostLogin(){
        return usernameplaceholder.isDisplayed();

    }
    public ContactsPage selectContactTab(){
        tab_Contacts.click();
        return new ContactsPage();
    }

    public void selectNewContactDropdown() {
        Actions action = new Actions(driver);
        action.moveToElement(tab_Contacts).perform();
        //action.moveToElement(tab_Contacts).build().perform();
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.visibilityOf(dropdown_newContact));
        dropdown_newContact.click();

    }
}
