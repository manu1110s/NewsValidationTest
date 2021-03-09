package stepDefinition;

import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import managers.FileReaderManager;
import managers.PageObjectManager;
import managers.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.NewsHomePage;
import pageObjects.GoogleSearchPage;
import utility.Wait;
import org.junit.Test;
import org.junit.Assert;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class NewsValidityCheck {

    WebDriverWait wait;
    WebDriver driver;
    NewsHomePage newsHomePage ;
    GoogleSearchPage googleSearchPage;
    PageObjectManager pageObjectManager;
    WebDriverManager webDriverManager;
    String articleHeadline;
    List<String> allSearchResultsHeadline;
    boolean testResult;


    @Before
    public void BeforeSteps() {
       // configFileReader = new ConfigFileReader();
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.getDriver();
        articleHeadline =null;
        allSearchResultsHeadline =null;
    }

    @Given("^User is on The Guardian Home Page$")
    public void user_is_on_The_Guardian_Home_Page() throws InterruptedException {

       // driver.get(FileReaderManager.getInstance().getConfigReader().getNewsPageUrl());
        pageObjectManager = new PageObjectManager(driver);
        newsHomePage = pageObjectManager.getNewsHomePage();
        newsHomePage.navigateToNewsHomePage();
        Wait.untilPageLoadComplete(driver,FileReaderManager.getInstance().getConfigReader().getTimeOutInSeconds());
        Wait.untilJqueryIsDone(driver,FileReaderManager.getInstance().getConfigReader().getTimeOutInSeconds());
        newsHomePage.switchToIframe();
        newsHomePage.acceptCookiesCondition();
        newsHomePage.switchToHomePage();
    }

    @Given("^User Navigates News Section$")
    public void user_Navigates_News_Section() { newsHomePage.clickOnNews(); }

    @Given("^User Considers first Article Headline$")
    public void user_Considers_first_Article_Headline(){
        articleHeadline=newsHomePage.getFirstArticleHeadline();
    }

    @Given("^User Navigates to Google Home Page$")
    public void user_Navigates_to_Google_Home_Page() {newsHomePage.navigateToGoogleHomePage();    }

    @When("^User search for Article in Google$")
    public void user_search_for_Article_in_Google(){
        googleSearchPage = pageObjectManager.getGoogleSearchPage();
        googleSearchPage.enterSearchCriteria(articleHeadline);
        googleSearchPage.searchForResults();
    }

    @Then("^User sees article in Search results$")
    public void user_sees_article_in_Search_results() {
        allSearchResultsHeadline = googleSearchPage.getAllResultsHeadline();
        testResult = googleSearchPage.compareResults(allSearchResultsHeadline,articleHeadline);
        assertEquals(false,testResult);
    }

    @Then("^User checks for Validity of Article$")
    public void user_checks_for_Validity_of_Article() {
        allSearchResultsHeadline = googleSearchPage.getAllResultsHeadline();
        testResult = googleSearchPage.compareResults(allSearchResultsHeadline,articleHeadline);
        assertEquals(false,testResult);
    }

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
            }
        }
    }


    @After(order = 0)
    public void AfterSteps() {
       webDriverManager.closeDriver();
    }

}

