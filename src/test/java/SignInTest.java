import Pages.AuthPage;
import Pages.HomePage;
import org.testng.annotations.*;

import java.io.IOException;

import static net.andreinc.mockneat.types.enums.PassStrengthType.WEAK;
import static net.andreinc.mockneat.unit.user.Passwords.passwords;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class SignInTest extends BeforeTest {
    private AuthPage authPage;
    private AccInfo accInfo;


    @Parameters({"browser"})
    public SignInTest(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openAuthPage(String browser) {
        openBrowsers(browser);

        authPage = getAuthPage();
        assertNotNull(authPage);
    }

    @AfterMethod
    public void closeBrowser() {
        quitDriver();
    }

    @Test
    public void successSignInTest() {
        accInfo = setAccInfo();
        authPage.fillLogInForm(accInfo.login, accInfo.pass);
        authPage.submitSignUpForm();

        HomePage homePage = getHomePage();
        assertNotNull(homePage);

        assertTrue(homePage.getProfileName().contains(accInfo.fullName));
    }

    @Test(dataProvider = "dataProvider")
    public void failedSignInTest(AccInfo accInfo, boolean areCredentialsEmpty) {
        //TEST OF CORRECT LOGIN AND WRONG PASS:
        if (!areCredentialsEmpty) {
            accInfo = setAccInfo();
            authPage.fillLogInForm(accInfo.login, passwords().type(WEAK).get());
        }
        //NEXT STEPS / TEST OF EMPTY LOGIN CREDENTIALS:
        authPage.submitSignUpForm();
        String authErrorText = testProperties.getProperty("errorAuthText");
        assertTrue(authPage.getAlertErrorText().contains(authErrorText));
    }

    @DataProvider(name = "dataProvider")
    public Object[][] dpMethod() {
        accInfo = setAccInfo();
        return new Object[][]{{accInfo, true}, {accInfo, false}};
    }

    private AccInfo setAccInfo() {
        return AccInfo.builder()
                .login(testProperties.getProperty("login"))
                .pass(testProperties.getProperty("pass"))
                .fullName(testProperties.getProperty("avatarFullName"))
                .build();
    }
}