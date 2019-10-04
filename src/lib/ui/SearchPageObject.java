package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

abstract public class SearchPageObject extends MainPageObject {

     protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_LABEL_ELEMENT,
            SEARCH_RESULT_TITLE_ELEMENT,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL,
             SEARCH_CLEAR_MINI_BUTTON;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
    }

    private static String getResultSearchElementByTitleAndDescription(String title, String description){
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}",description);
    }

    /* TEMPLATE METHODS */


    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find 'Search Wikipedia' input",
                5);
        this.waitForElementPresent(
                SEARCH_INIT_ELEMENT,
                "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find search input",
                5);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with substring " + substring,
                15);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find 'Close' button",
                5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click cancel search button",
                5);
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                10);
    }

    public int getAmountOfFoundArticles(){

        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResulsLabel(){
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot finf empty resilts label by the requst ",
                15
        );
    }

    public void assertThereIsNoResultOfSearch(){
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We have found some results by request"
        );
    }

    public void checkSearchLabelPresent(){
        this.waitForElementPresent(
                SEARCH_LABEL_ELEMENT,
                "Cannot find search label",
                5
        );
    }

    public String getSearchLabelTitle(){
        WebElement search_title = waitForElementPresent(
                SEARCH_LABEL_ELEMENT,
                "Cannot find search label",
                5
        );
        return search_title.getAttribute("text");
    }

    public void clearSearchField(){
        if (Platform.getInstance().isAndroid()) {

            this.waitForElementAndClear(
                    SEARCH_LABEL_ELEMENT,
                    "Cannot find search field",
                    5
            );
        }
        if (Platform.getInstance().isIOS()){
            this.waitForElementAndClick(
                    SEARCH_LABEL_ELEMENT,
                    "Cannot find search field",
                    5
            );
            this.waitForElementAndClick(
                    SEARCH_CLEAR_MINI_BUTTON,
                    "Unable to press mini button",
                    5);
        }
    }

    public void checkNoResultsOfSearch(){
        this.waitForElementNotPresent(
                SEARCH_RESULT_TITLE_ELEMENT,
                "Results of search still present",
                5
        );
    }

    public List<WebElement>  getTheListOfFoundedResults(){
        List<WebElement> search_results = waitForSeveralElements(
                SEARCH_RESULT_TITLE_ELEMENT,
                "there is no results of search",
                10
        );
        return  search_results;
    }

    public int getTHeCountOfSearchResult(){
        List<WebElement> search_results = getTheListOfFoundedResults();
        return search_results.size();
    }



    public int getTheCountOfSearchResultsByTerm(String search_term){
        List<WebElement> search_results = getTheListOfFoundedResults();
        AtomicInteger counter = new AtomicInteger();
        if (Platform.getInstance().isAndroid()) {

            search_results.forEach(webElement -> {
                if (webElement.getAttribute("text").toLowerCase().contains(search_term)) {
                    counter.getAndIncrement();
                }
                ;
            });
        }else {

            search_results.forEach(webElement -> {
                if (webElement.getAttribute("name").toLowerCase().contains(search_term)) {
                    counter.getAndIncrement();
                }
                ;
            });

        }
        return search_results.size();
    }

    public void waitForElementByTitleAndDescription(String title, String description){
        String name = getResultSearchElementByTitleAndDescription(title,description);
        this.waitForElementPresent(
                name,
                "Unable to find result of search by title " + title + " and description " + description,
                15
    );
    }


}

