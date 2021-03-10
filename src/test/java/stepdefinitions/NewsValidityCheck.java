package stepdefinitions;

import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import managers.PageObjectManager;
import managers.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import pageobjects.GoogleSearchPage;
import pageobjects.NewsHomePage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class NewsValidityCheck {

    WebDriver driver;
    NewsHomePage newsHomePage ;
    GoogleSearchPage googleSearchPage;
    PageObjectManager pageObjectManager;
    WebDriverManager webDriverManager;
    String articleHeadline;
    List<String> allSearchResultsHeadline;
    boolean testResult;

    //All the steps inside this method is run before every scenario. driver will launch the browser
    @Before
    public void BeforeSteps() {
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.getDriver();
        articleHeadline = null;
        allSearchResultsHeadline = null;
    }

    //This method will load the Guardian dot com home page
    @Given("^User is on The Guardian Home Page$")
    public void userIsOnTheGuardianHomePage() throws InterruptedException {
        pageObjectManager = new PageObjectManager(driver);
        newsHomePage = pageObjectManager.getNewsHomePage();
        newsHomePage.navigateToNewsHomePage();
        newsHomePage.switchToIframe();
        newsHomePage.acceptCookiesCondition();
        newsHomePage.switchToHomePage();
    }

    //This method will load the news section of the Guardian dot com website
    @Given("^User Navigates News Section$")
    public void userNavigatesNewsSection() {
        newsHomePage.clickOnNews();
    }

    //This method will get the headline of the first article in the Guardian dot com news page
    @Given("^User Considers first Article Headline$")
    public void userConsidersFirstArticleHeadline() {
        articleHeadline = newsHomePage.getFirstArticleHeadline();
    }

    //This method will launch the Google Home page
    @Given("^User Navigates to Google Home Page$")
    public void userNavigateToGoogleHomePage() {
        newsHomePage.navigateToGoogleHomePage();
    }

    //This method will search for the article in Google search page
    @When("^User search for Article in Google$")
    public void userSearchForArticleInGoogle() {
        googleSearchPage = pageObjectManager.getGoogleSearchPage();
        googleSearchPage.enterSearchCriteria(articleHeadline);
        googleSearchPage.searchForResults();
    }

    //This method will check if the article exists in the Google search results
    @Then("^User sees article in Search results$")
    public void userSeesArticleInSearchResults() {
        allSearchResultsHeadline = googleSearchPage.getAllResultsHeadline();
        testResult = googleSearchPage.isArticlePresentInSearchEngine(allSearchResultsHeadline, articleHeadline);
        assertTrue(testResult);
    }

    //This method will compare the article headlines with the Google search results and will check for the validity of the news article
    @Then("^User checks for Validity of Article$")
    public void userChecksForValidityOfArticle() {
        allSearchResultsHeadline = googleSearchPage.getAllResultsHeadline();
        testResult = googleSearchPage.compareNews(allSearchResultsHeadline, articleHeadline);
        assertTrue(testResult);
    }

    //This method will take screenshots in case of scenario failure and stores it under the cucumber-reports
    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            try {
                //This takes a screenshot from the driver at save it to the specified location
                File sourcePath = ((TakesScreenshot) webDriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

                //Building up the destination path for the screenshot to save
                //Also make sure to create a folder 'screenshots' with in the cucumber-report folder
                File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenshotName + ".png");

                //Copy taken screenshot from source location to destination location
                Files.copy(sourcePath.toPath(),destinationPath.toPath());

                //This attach the specified screenshot to the test
                Reporter.addScreenCaptureFromPath(destinationPath.toString());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //After each scenario the browser windows are closed and quit
    @After(order = 0)
    public void AfterSteps() {
        webDriverManager.closeDriver();
    }

}

