package managers;

import org.openqa.selenium.WebDriver;
import pageobjects.GoogleSearchPage;
import pageobjects.NewsHomePage;

/*The duty of the Page Object Manager is to create the page’s object and also to make sure that the same object should not be created again
and again. But to use a single object for all the step definition files*/
public class PageObjectManager {
    private NewsHomePage newsHomePage;
    private GoogleSearchPage googleSearchPage;
    private final WebDriver driver;


    /*Constructor for the Class*/
    public PageObjectManager(WebDriver driver){
        this.driver=driver;

    }

    /*This method creates object of News Home Page only if it is null and supply already created object if not null*/
    public NewsHomePage getNewsHomePage() {
        return (newsHomePage == null) ? newsHomePage = new NewsHomePage(driver) : newsHomePage;
    }

    /*This method creates object of Google Home Page only if it is null and supply already created object if not null*/
    public GoogleSearchPage getGoogleSearchPage() {
        return (googleSearchPage == null) ? googleSearchPage = new GoogleSearchPage(driver): googleSearchPage;
    }

}
