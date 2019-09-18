package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    private static final String

        TITLE= "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[contains(@text,'Add to reading list')]",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "//*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
        OPEN_NEW_SEARCH_BUTTON = "//android.widget.TextView[@content-desc='Search Wikipedia']",
        NAME_OF_FOLDER_TPL = "//*[contains(@text,'{SUBSTRING}')]",
        MY_LISTS_BUTTON = "//android.widget.FrameLayout[@content-desc='My lists']";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }


    /* TEMPLATE METHODS */
    private static String getNameOfFolderTpl(String substring) {
        return NAME_OF_FOLDER_TPL.replace("{SUBSTRING}",substring);
    }

    /* TEMPLATE METHODS */

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(
                By.id(TITLE),
                "Cannot find article title on the page",
                15);
    }

    public void checkTitleElementPresentWithoutWaiting(){
        this.assertElementPresent(
                By.id(TITLE),
                "We don't found page title"
        );
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of the article",
                20);
    }

    public void addArticleToMyList(String name_of_folder){
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find options to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of article folder",
                5
        );

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                5
        );

    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X link",
                5
        );
    }

    public void initNewSearch(){
        this.waitForElementAndClick(
                By.xpath(OPEN_NEW_SEARCH_BUTTON),
                "Cannot find button to open new search",
                5
        );
    }


    public void addArticleToMyListAlreadyCreate(String name_of_folder){

        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find options to add article to reading list",
                5
        );

        String folder_name = getNameOfFolderTpl(name_of_folder);

        this.waitForElementAndClick(
                By.xpath(folder_name),
                "Cannot find just created folder",
                5
        );
    }

    public void openMyLists(){
        this.waitForElementAndClick(
                By.xpath(MY_LISTS_BUTTON),
                "Cannot find navigation button to 'My lists'",
                5
        );

    }
}