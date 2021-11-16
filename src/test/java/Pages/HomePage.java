package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(className = "avatar-full-name")
    private WebElement avatarFullName;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getProfileName() {
        waitForVisibilityOf(avatarFullName);
        return avatarFullName.getText();
    }
}
