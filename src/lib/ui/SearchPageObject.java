package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL="//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
            SEARCH_LABEL_ELEMENT = "org.wikipedia:id/search_src_text",
            SEARCH_RESULT_TITLE_ELEMENT = "//*[@resource-id='org.wikipedia:id/page_list_item_title']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
    }

    /* TEMPLATE METHODS */


    public void initSearchInput() {
        this.waitForElementAndClick(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find 'Search Wikipedia' input",
                5);
        this.waitForElementPresent(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(
                By.xpath(SEARCH_INPUT),search_line,
                "Cannot find search input",
                5);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "Cannot find search result with substring " + substring,
                15);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find 'Close' button",
                5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Search cancel button is still present",
                5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find and click cancel search button",
                5);
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                By.xpath(search_result_xpath),
                "Cannot find and click search result with substring " + substring,
                10);
    }

    public int getAmountOfFoundArticles(){

        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElements(
                By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResulsLabel(){
        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot finf empty resilts label by the requst ",
                15
        );
    }

    public void assertThereIsNoResultOfSearch(){
        this.assertElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "We have found some results by request"
        );
    }

    public void checkSearchLabelPresent(){
        this.waitForElementPresent(
                By.id(SEARCH_LABEL_ELEMENT),
                "Cannot find search label",
                5
        );
    }

    public String getSearchLabelTitle(){
        WebElement search_title = waitForElementPresent(
                By.id(SEARCH_LABEL_ELEMENT),
                "Cannot find search label",
                5
        );
        return search_title.getAttribute("text");
    }

    public void clearSearchField(){
        this.waitForElementAndClear(
                By.id(SEARCH_LABEL_ELEMENT),
                "Cannot find search field",
                5
        );
    }

    public void checkNoResultsOfSearch(){
        this.waitForElementNotPresent(
                By.xpath(SEARCH_RESULT_TITLE_ELEMENT),
                "Results of search still present",
                5
        );
    }

    public List<WebElement>  getTheListOfFoundedResults(){
        List<WebElement> search_results = waitForSeveralElements(
                By.xpath(SEARCH_RESULT_TITLE_ELEMENT),
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
        search_results.forEach(webElement -> {
            if (webElement.getAttribute("text").toLowerCase().contains(search_term)) {
                counter.getAndIncrement();
            }
            ;
        });
        return search_results.size();
    }


}

