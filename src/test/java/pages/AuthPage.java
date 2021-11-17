package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import testData.AccInfo;

public class AuthPage extends BasePage {
    @FindBy(xpath = "//input[@name='user']")
    private WebElement loginTextArea;
    @FindBy(xpath = "//input[@name='password']")
    private WebElement passTextArea;
    @FindBy(id = "button_submit_login_form")
    private WebElement buttonSignIn;

    public AuthPage(WebDriver driver) {
        super(driver);
    }

    public void fillLogInForm(AccInfo accInfo) {
        waitForVisibilityOf(loginTextArea);
        typeInField(loginTextArea, accInfo.getLogin());
        typeInField(passTextArea, accInfo.getPass());
    }

    public void submitSignUpForm() {
        buttonSignIn.click();
    }

    private void typeInField(WebElement field, String value) {
        sendKeys(field, value);
    }

    public String getAlertErrorText() {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

}
