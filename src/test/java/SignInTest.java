import org.openqa.selenium.support.PageFactory;
import pages.AuthPage;
import pages.HomePage;
import org.testng.annotations.*;
import testData.AccInfo;

import java.io.IOException;

import static net.andreinc.mockneat.types.enums.PassStrengthType.WEAK;
import static net.andreinc.mockneat.unit.user.Passwords.passwords;
import static org.testng.Assert.*;

public class SignInTest extends BeforeTest {
    private AuthPage authPage;
    private final String expectedAuthErrorText = testProperties.getProperty("errorAuthText");

    @Parameters({"browser"})
    public SignInTest(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openAuthPage(String browser) {
        openBrowsers(browser);
        authPage = new AuthPage(driver);

        assertNotNull(authPage, "Auth page was not loaded");
    }

    @AfterMethod
    public void closeBrowser() {
        quitDriver();
    }

    @Test(dataProvider = "createCorrectTestData")
    public void successSignInTest(AccInfo accInfo) {
        authPage.fillLogInForm(accInfo);
        authPage.submitSignUpForm();
        HomePage homePage = new HomePage(driver);

        assertNotNull(homePage, "Home page was not loaded");
        assertEquals(homePage.getProfileName().trim(), accInfo.getFullName(),
                "Expected full name differs from actual full name");
    }

    @Test(dataProvider = "createWrongTestData")
    public void failedSignInTest(AccInfo accInfo) {
        authPage.fillLogInForm(accInfo);
        authPage.submitSignUpForm();

        assertEquals(authPage.getAlertErrorText(), expectedAuthErrorText,
                "Expected error text differs from actual error text");
    }

    @DataProvider
    public Object[][] createWrongTestData() {

        AccInfo emptyAcc = AccInfo.builder()
                .login("")
                .pass("")
                .build();

        AccInfo wrongDataAcc = AccInfo.builder()
                .login(testProperties.getProperty("login"))
                .pass(passwords().type(WEAK).get())
                .build();

        return new Object[][]{{emptyAcc}, {wrongDataAcc}};
    }

    @DataProvider
    public Object[][] createCorrectTestData() {
        AccInfo correctAcc = AccInfo.builder()
                .login(testProperties.getProperty("login"))
                .pass(testProperties.getProperty("pass"))
                .fullName(testProperties.getProperty("avatarFullName"))
                .build();

        return new Object[][]{{correctAcc}};
    }

}