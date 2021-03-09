package pageObjects;

import managers.FileReaderManager;
import managers.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.Wait;

import java.util.List;

public class NewsHomePage {

    WebDriver driver;
    WebDriverWait wait;


    public NewsHomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver=driver;
    }

    @FindBy(how = How.XPATH, using = "//header/nav[1]/ul[1]/li[1]/a[1]")
    private WebElement pillar_News ;

    @FindBy(how = How.XPATH, using = "//a[@data-link-name = 'article']")
    private List<WebElement> container_FirstArticle;

    @FindBy(how = How.TAG_NAME , using = "iframe")
    private List<WebElement> frames;

    @FindBy(how = How.XPATH, using = "/html/body/div/div[3]/div[3]/div/div/button[1]")
    private WebElement button_yesImHappy;


    public void acceptCookiesCondition(){
       Wait.untilJqueryIsDone(driver,FileReaderManager.getInstance().getConfigReader().getTimeOutInSeconds());
        button_yesImHappy.click();
    }


    public void switchToIframe() throws InterruptedException {
       // for(int i=2;i<frames.size();i++){
            Wait.untilJqueryIsDone(driver);
            Thread.sleep(5000);
        //    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[3]/div[3]/div/div/button[1]")));
            driver.switchTo().frame(2);
             //      }
    }

    public void clickOnNews(){
         pillar_News.click();
    }

    public String getFirstArticleHeadline(){
        return container_FirstArticle.get(0).getText();
    }


    public void switchToHomePage() {
        driver.switchTo().defaultContent();
    }

    public void navigateToNewsHomePage() {
        driver.get(FileReaderManager.getInstance().getConfigReader().getNewsPageUrl());
    }
    public void navigateToGoogleHomePage() {
        driver.get(FileReaderManager.getInstance().getConfigReader().getGoogleHomePageUrl());
    }
}
