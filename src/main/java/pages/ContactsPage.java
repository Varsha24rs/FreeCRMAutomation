package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import sun.awt.windows.WBufferStrategy;

public class ContactsPage extends TestBase {

    //OR

    @FindBy(xpath="//*[@class='datacardtitle' and contains(text(),'Contacts')]")
    WebElement header_contacts;

    @FindBy(name="title")
    WebElement title;

    @FindBy(id="first_name")
    WebElement firstName;

    @FindBy(id="middle_initial")
    WebElement middleName;

    @FindBy(id="surname")
    WebElement lastName;

    @FindBy(xpath="//*[@type='submit' and @value='Save and Create Another (same company)']//preceding-sibling::input[@type='submit' and @value='Save']")
    WebElement saveBtn;

    public ContactsPage(){
        PageFactory.initElements(driver, this);
    }

    public boolean contactLabelVerification(){
        return header_contacts.isDisplayed();
    }

    public void selectContactByName(String name){
        driver.findElement(By.xpath("//*[contains(text(),'"+name+"')]//parent::td//preceding-sibling::td/input[@type='checkbox']")).click();
    }

    public void createNewContact(String titleValue, String firstNameValue, String middleNameValue, String lastNameValue){
        Select sel = new Select(title);
        sel.selectByValue(titleValue);
        firstName.sendKeys(firstNameValue);
        middleName.sendKeys(middleNameValue);
        lastName.sendKeys(lastNameValue);
        saveBtn.click();
    }
}
