package managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.GoogleSearchPage;
import pageObjects.NewsHomePage;


public class PageObjectManager {
    private NewsHomePage newsHomePage;
    private GoogleSearchPage googleSearchPage;
    private WebDriver driver;



    public PageObjectManager(WebDriver driver){
        this.driver=driver;

    }

    public NewsHomePage getNewsHomePage() {
        return (newsHomePage == null) ? newsHomePage = new NewsHomePage(driver) : newsHomePage;
    }

    public GoogleSearchPage getGoogleSearchPage() {
        return (googleSearchPage == null) ? googleSearchPage = new GoogleSearchPage(driver): googleSearchPage;
    }

}
