import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT_IN_SECONDS.getTimeOutValue()));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
    }

    private void openChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    private void openEdge() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
    }

    private void openFireFox() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }
}
