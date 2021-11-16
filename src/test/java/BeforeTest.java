import Pages.AuthPage;
import Pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

abstract class BeforeTest {
    WebDriver driver;
    Properties testProperties = new Properties();
    private final static String PROPERTIES_PATH = "./src/test/resources/test.properties";

    public BeforeTest(String browser) throws IOException {
        loadPropertiesFromFile();
        setCapabilities(browser);
    }

    private void loadPropertiesFromFile() throws IOException {
        testProperties.load(new FileInputStream(PROPERTIES_PATH));
    }

    private void setCapabilities(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browser", browser);
    }

    public void openBrowsers(String browser) {
        openBrowser(browser);
    }

    public void quitDriver() {
        driver.quit();
    }

    public AuthPage getAuthPage() {
        return PageFactory.initElements(driver, AuthPage.class);
    }

    public HomePage getHomePage() {
        return PageFactory.initElements(driver, HomePage.class);
    }

    private void openBrowser(String browser) {
        openAllBrowsers(browser);
        driver.get(testProperties.getProperty("siteURL"));
        maximizeWindow();
    }

    private void openAllBrowsers(String browser) {
        switch (browser) {
            case "Chrome":
                openChrome();
                break;
            case "FireFox":
                openFireFox();
                break;
            case "Edge":
                openEdge();
                break;
            default:
                break;
        }
    }

    private void maximizeWindow() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().deleteAllCookies();
    }

    private void openChrome() {
        //TODO: https://stackoverflow.com/questions/35867102/how-to-work-with-selenium-chrome-driver-in-maven-without-chromedriver-exe
        System.setProperty("webdriver.chrome.driver", getDriverUrl("chromeDir"));
        driver = new ChromeDriver();
        System.out.println("Chrome was launched Successfully");
    }

    private void openEdge() {
        System.setProperty("webdriver.edge.driver", getDriverUrl("edgeDir"));
        driver = new EdgeDriver();
        System.out.println("Edge was launched Successfully");
    }

    private void openFireFox() {
        System.setProperty("webdriver.gecko.driver", getDriverUrl("geckoDir"));
        driver = new FirefoxDriver();
        System.out.println("Firefox Launched Successfully");
    }

    private String getDriverUrl(String driverURL) {
        return testProperties.getProperty(driverURL);
    }
}
