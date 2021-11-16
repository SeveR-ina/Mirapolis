package Pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthPage extends BasePage {
    @FindBy(xpath = "//input[@type='text']")
    private WebElement loginTextArea;
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passTextArea;
    @FindBy(id = "button_submit_login_form")
    private WebElement buttonSignIn;

    public AuthPage(WebDriver driver) {
        super(driver);
    }

    public void fillLogInForm(String login, String pass) {
        waitForVisibilityOf(loginTextArea);
        typeInField(loginTextArea, login);
        typeInField(passTextArea, pass);
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
