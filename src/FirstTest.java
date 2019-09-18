import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class FirstTest extends CoreTestCase {


    @Test
    public void testSearchLabel() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();

        WebElement search_label = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search label",
                5
        );
        String label = search_label.getAttribute("text");
        assertEquals(
                "There is no label 'Search…'!",
                "Search…",
                label
        );

    }

    @Test
    public void testSearchSeveralArticles() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");

        List search_results = MainPageObject.waitForSeveralElements(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "there is no results of search",
                10
        );
        assertTrue(search_results.size() > 0);
        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Results of search still present",
                5
        );
    }

    @Test
    public void testSearchResultWithWord() {

        String search_term = "java";
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_term);

        List<WebElement> search_results = MainPageObject.waitForSeveralElements(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "there is no results of search",
                10
        );
        AtomicInteger counter = new AtomicInteger();
        search_results.forEach(webElement -> {
            if (webElement.getAttribute("text").toLowerCase().contains(search_term)) {
                counter.getAndIncrement();
            }
            ;
        });
        assertTrue(search_results.size() == counter.get());

    }










    @Test
    public void testSaveTwoArticlesToMyListAndDeleteOne() {

        String name_of_folder = "Learning programming";
        String first_search = "Java";
        String first_article_name = "Java (programming language)";
        String second_article_name = "JavaScript";


        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(first_search);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + first_article_name + "']"),
                "Cannot find first article",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Add to reading list')]"),
                "Cannot find options to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@content-desc='Search Wikipedia']"),
                "Cannot find button to open new search",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                first_search,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_article_name + "']"),
                "Cannot find 'second article name",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String title_of_second_article = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Add to reading list')]"),
                "Cannot find options to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                //     By.xpath("//*[@text='" + name_of_folder + "']"),

                By.xpath("//*[contains(@text,'" + name_of_folder + "')]"),
                "Cannot find just created folder",
                5
        );


        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to 'My lists'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'" + name_of_folder + "')]"),
                "Cannot find created folder",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='" + first_article_name + "']"),
                "Unable to find first added article",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@text,'" + second_article_name + "')]"),
                "Unable to find second added article",
                5
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[contains(@text,'" + first_article_name + "')]"),
                "Cannot find saved article"
        );


        MainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@text,'" + second_article_name + "')]"),
                "First article is not deleted",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'" + second_article_name + "')]"),
                "Unable to find second added article",
                5
        );


        String title_of_second_article_in_folder = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        assertEquals(
                "Article title is not the same as in article in folder",
                title_of_second_article,
                title_of_second_article_in_folder
        );

    }

    @Test
    public void testCheckArticleHasTitleWithoutWaiting() {

        String search = "Java";
        String article_name = "Java (programming language)";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + article_name + "']"),
                "Cannot find first article",
                5
        );


        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "We don't found page title"
        );
    }
}