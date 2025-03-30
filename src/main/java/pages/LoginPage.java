package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends TestBase {

    //Page Factory or Obj Repo

    @FindBy(name="username")
    private WebElement txt_username;

    @FindBy(name = "password")
    private WebElement txt_password;

    @FindBy(xpath = "//input[@type='submit' and @value='Login']")
    private WebElement btn_login;

    @FindBy(xpath = "//img[contains(@alt,'Free CRM Software')]")
    private WebElement img_login_logo;

    //Initialising Page Objects
    public LoginPage(){
        PageFactory.initElements(driver, this);
    }

    //Actions
    public String getLoginPageTitle()
    {
        return driver.getTitle();
    }

    public void enterUserName(String username){
        txt_username.sendKeys(username);
    }

    public void enterPassword(String password){
        txt_password.sendKeys(password);
    }

    public void clickOnLogin(){
        btn_login.click();
    }

    public boolean validateLoginPage(){
        return img_login_logo.isDisplayed();
    }

    public HomePage login(String username, String password){
        enterUserName(username);
        enterPassword(password);
        clickOnLogin();

        return new HomePage();
    }
}
