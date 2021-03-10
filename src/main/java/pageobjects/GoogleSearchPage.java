package pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/*Page Object Model for Google Search Page*/
public class GoogleSearchPage {

    /*Text box element in the Google Search page*/
    @FindBy(how = How.NAME, using = "q")
    private WebElement textbox_Search;
    /*List of all the Web elements for search results on Google search */
    @FindBy(how = How.XPATH, using = "//div[@id =\"rso\"]/div/descendant::div[@class = \"tF2Cxc\"]")
    private List<WebElement> searchResultsCollection;

    /*Constructor to initialize the page elements*/
    public GoogleSearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /*This method enters the search string in the search text box of Google . It accepts search string as parameter*/
    public void enterSearchCriteria(String firstArticleHeadline) {
        try {
            textbox_Search.sendKeys(firstArticleHeadline);
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }

    /*This method searches for the results on Google search page.*/
    public void searchForResults() {
        try {
            textbox_Search.sendKeys(Keys.ENTER);
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }

    /*This method fetches all the results from Google search page and returns the results in the form of List of Strings*/
    public List<String> getAllResultsHeadline() {
        List<String> allResultStrings = new ArrayList<>();
        for (WebElement webElement : searchResultsCollection) {
            allResultStrings.add(webElement.getText());
        }
        return allResultStrings;
    }

    /*This method considers all the results from search engine as first parameter in the form of list of strings, and first article headline from
    The guardian dot com as second parameter in the form of a string.
    This method returns true if the news is valid and false if the news is not valid*/
    public boolean compareNews(List<String> allSearchResults, String firstArticleHeadline) {
        String[] eachWordsFromFirstArticleHeadline = firstArticleHeadline.split("\\W+");
        int count = 0;
        float requiredMatchingPercentage;
        float actualMatchingPercentageLowerLimit;
        float actualMatchingPercentageUpperLimit;
        boolean compareResult = false;
        count = getCount(allSearchResults, eachWordsFromFirstArticleHeadline, count);
        // if the count is zero then there is no match and the news is not valid
        if (count == 0) compareResult = false;
        else if (count > 0) {
            // by dividing the count by number of results article found , we get the average matching words in each individual article
            count = count / allSearchResults.size();
            //Assuming headlines consist of at least 5 words and if we find at least 70 percent matching results
            // then we are assuming to say that the news is valid
            requiredMatchingPercentage = (0.7f * eachWordsFromFirstArticleHeadline.length);
            // adding plus  to the required matching percentage and setting it  as upper limit and
            // subtracting  3 to the required matching percentage and setting it  as lower limit
            actualMatchingPercentageLowerLimit = (requiredMatchingPercentage - 3);
            actualMatchingPercentageUpperLimit = (requiredMatchingPercentage + 3);
            // if count .i.e. matching percentage falls within range then we say the news is valid
            if (count > actualMatchingPercentageLowerLimit && count < actualMatchingPercentageUpperLimit)
                compareResult = true;
        }
        return compareResult;
    }

    /*This method will get the count of matching words from the News article and Google search results*/
    private int getCount(List<String> allSearchResults, String[] eachWordsFromFirstArticleHeadline, int count) {
        for (String allSearchResult : allSearchResults) {
            for (String s : eachWordsFromFirstArticleHeadline) {
                if (allSearchResult.contains(s)) count++;
                //the count here is incremented for all the matches in all each of the result from the web page
            }
        }
        return count;
    }

    /*This method will return true if at least one result exist in Google search*/
    public boolean isArticlePresentInSearchEngine(List<String> allSearchResults, String firstArticleHeadline) {
        boolean testResult = false;
        int count = 0;
        String[] eachWordsFromFirstArticleHeadline = firstArticleHeadline.split("\\W+");
        count = getCount(allSearchResults, eachWordsFromFirstArticleHeadline, count);
        if (count != 0) testResult = true;
        return testResult;
    }

}
