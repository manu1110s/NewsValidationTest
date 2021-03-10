package pageobjects;

import managers.FileReaderManager;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.Wait;

import java.util.List;

/*Page Object Class for the News Home Page which is the Guardian dot com website */
public class NewsHomePage {

    WebDriver driver;
    WebDriverWait wait;
    /*News pillar Web Element*/
    @FindBy(how = How.XPATH, using = "//header/nav[1]/ul[1]/li[1]/a[1]")
    private WebElement pillar_News;
    /*List of all the articles Web Elements*/
    @FindBy(how = How.XPATH, using = "//a[@data-link-name = 'article']")
    private List<WebElement> container_FirstArticle;
    /*List of all the iframes windows*/
    @FindBy(how = How.TAG_NAME, using = "iframe")
    private List<WebElement> frames;
    /*Yes I'm Happy Button Web Element on the iframe*/
    @FindBy(how = How.XPATH, using = "/html/body/div/div[3]/div[3]/div/div/button[1]")
    private WebElement button_yesImHappy;

    /*Constructor to initialize the page elements*/
    public NewsHomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, FileReaderManager.getInstance().getConfigReader().getTimeOutInSeconds());
    }

    /*This method will perform the click operation on the 'Yes I'm Happy' button on the iframe*/
    public void acceptCookiesCondition() {
        // Wait.untilJqueryIsDone(driver);
        button_yesImHappy.click();
    }

    /*This method will switch the control from parent window to iframe*/
    public void switchToIframe() throws InterruptedException {
        Thread.sleep(5000);
        for (WebElement frame : frames) {
            System.out.println(frame.toString());
        }
        driver.switchTo().frame(2);
    }

    /*This method will perform the click operation on the News pillar on the web page*/
    public void clickOnNews() {
        try {
            pillar_News.click();
        } catch (ElementClickInterceptedException exception) {
            System.out.println(exception.toString());
        }
    }

    /*This method will return the first article from the web page*/
    public String getFirstArticleHeadline() {
        return container_FirstArticle.get(0).getText();
    }


    /*This method will switch the control back to parent window*/
    public void switchToHomePage() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }

    }

    /*This method will launch the Guardian dot com News page*/
    public void navigateToNewsHomePage() {
        try {
            driver.get(FileReaderManager.getInstance().getConfigReader().getNewsPageUrl());
            Wait.untilPageLoadComplete(driver, FileReaderManager.getInstance().getConfigReader().getImplicitWait());
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }

    }

    /*This method will launch the Google Home page*/
    public void navigateToGoogleHomePage() {
        try {
            driver.get(FileReaderManager.getInstance().getConfigReader().getGoogleHomePageUrl());
            Wait.untilPageLoadComplete(driver, FileReaderManager.getInstance().getConfigReader().getImplicitWait());
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }

    }
}
