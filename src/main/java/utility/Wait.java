package utility;

import managers.FileReaderManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/*This class duty is to provide explicit waits */
public class Wait {
    /*This method will execute the javascript and considers the parameters as driver and can only depict the Thread dot sleep method*/
    public static void untilJqueryIsDone(WebDriver driver) {
        untilJqueryIsDone(driver, FileReaderManager.getInstance().getConfigReader().getImplicitWait());
    }

    /*This method will execute the javascript and considers the parameters as driver and timeout in seconds*/
    public static void untilJqueryIsDone(WebDriver driver, Long timeOutInSeconds) {
        until(driver, (d) ->
        {
            Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
            if (!isJqueryCallDone) System.out.println("JQuery Call is In Progress");
            return isJqueryCallDone;
        }, timeOutInSeconds);
    }

    /*This method will wait until the wait conditon is met or implicit time is lapsed*/
    public static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition) {
        until(driver, waitCondition, FileReaderManager.getInstance().getConfigReader().getImplicitWait());
    }

    /*This method will wait until the wait conditon is met or timeout  time is lapsed*/
    private static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, Long timeoutInSeconds) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
        webDriverWait.withTimeout(timeoutInSeconds, TimeUnit.SECONDS);
        try {
            webDriverWait.until(waitCondition);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*This method will wait until the page loads or the wait condition is met or implicit time is lapsed*/
    public static void untilPageLoadComplete(WebDriver driver) {
        untilPageLoadComplete(driver, FileReaderManager.getInstance().getConfigReader().getImplicitWait());
    }

    /*This method will wait until the page loads or the wait condition is met or implicit time is lapsed . parameters are different from the above method*/
    public static void untilPageLoadComplete(WebDriver driver, Long timeoutInSeconds) {
        until(driver, (d) ->
        {
            Boolean isPageLoaded = ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            if (!isPageLoaded) System.out.println("Document is loading");
            return isPageLoaded;
        }, timeoutInSeconds);
    }
}
