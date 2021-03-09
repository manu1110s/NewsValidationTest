package managers;

import enums.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

/*The duty of this Class is to get us the WebDriver , driver types and driver path in case of chrome, when we ask for it*/
public class WebDriverManager {
    private WebDriver driver;
    private static DriverType driverType;
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";

    /*Constructor for the class*/
    public WebDriverManager() {
        driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
    }

    /*This method returns the driver object , if null then it creates one and returns the object */
    public WebDriver getDriver() {
        if(driver == null) driver = createLocalDriver();
        return driver;
    }

    /*This method creates driver based on the driver type provided*/
    private WebDriver createLocalDriver() {
        switch (driverType) {
            case FIREFOX : driver = new FirefoxDriver();
                break;
            case CHROME :
                System.setProperty(CHROME_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getDriverPath());
                driver = new ChromeDriver();
                break;
            case INTERNETEXPLORER : driver = new InternetExplorerDriver();
                break;
        }
        //setting window size to always maximum and also setting the implicit wait time
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitWait(), TimeUnit.SECONDS);
        return driver;
    }

    /*This method will close all the browser windows and will quit the driver instance*/
    public void closeDriver() {
        driver.close();
        driver.quit();
    }

}
