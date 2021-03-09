package pageObjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleSearchPage {


    public GoogleSearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

    }

    @FindBy(how = How.NAME, using = "q")
    private WebElement textbox_Search;

    @FindBy(how = How.XPATH, using = "//div[@id =\"rso\"]/div/descendant::div[@class = \"tF2Cxc\"]")
    private List<WebElement> searchResultsCollection;

    public void enterSearchCriteria(String firstArticleHeadline) {
        textbox_Search.sendKeys(firstArticleHeadline);
    }

    public void searchForResults() {
        textbox_Search.sendKeys(Keys.ENTER);
    }

    public List<String> getAllResultsHeadline() {
        List<String> allResultStrings = new ArrayList<>();
        for (int i = 0; i < searchResultsCollection.size(); i++) {
            allResultStrings.add(searchResultsCollection.get(i).getText());
        }
        return allResultStrings;
    }

    public boolean compareResults(List<String> allResultStrings , String firstArticleHeadline){
        boolean result = false;
        int count = 0;
        for (int i =0; i<allResultStrings.size();i++){
            if(allResultStrings.get(i).toLowerCase(Locale.ROOT).compareTo(firstArticleHeadline) >1){
                count++;
            }
        }
        if(count>4){
            result=true;
        }
        return result;
    }
}