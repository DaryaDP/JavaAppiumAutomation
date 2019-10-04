package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract  public class ArticlePageObject extends MainPageObject {

    protected static String
        TITLE,
        TITLE_TPL,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        OPEN_NEW_SEARCH_BUTTON,
        NAME_OF_FOLDER_TPL,
        MY_LISTS_BUTTON,
        SYNC_ARTICLES_TITLE,
        CLOSE_SYNC_ARTICLES_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }


    /* TEMPLATE METHODS */
    private static String getNameOfFolderTpl(String substring) {
        return NAME_OF_FOLDER_TPL.replace("{SUBSTRING}",substring);
    }

    private static String getTitleOfArticleTpl(String substring) {
        return TITLE_TPL.replace("{TITLE}",substring);
    }

    /* TEMPLATE METHODS */

    public WebElement waitForTitleElement(){
        return waitForTitleElement(null);
    }

    public WebElement waitForTitleElement(String name){
        if (Platform.getInstance().isIOS()) {
            String name_of_title = getTitleOfArticleTpl(name);
            return this.waitForElementPresent(
                    name_of_title,
                    "Cannot find article title on the page",
                    15);

        }
        else {
            return this.waitForElementPresent(
                    TITLE,
                    "Cannot find article title on the page",
                    15);
        }
    }


    public void checkTitleElementPresentWithoutWaiting(){
        this.assertElementPresent(
                TITLE,
                "We don't found page title"
        );
    }

    public String getArticleTitle(){
        return getArticleTitle(null);
    }


    public String getArticleTitle(String title){
        if (Platform.getInstance().isIOS()){
            WebElement title_element = waitForTitleElement(title);
            return title_element.getAttribute("name");

        }
        else if (Platform.getInstance().isIOS()){
            WebElement title_element = waitForTitleElement();
            return title_element.getAttribute("text");
        }
        else{
            WebElement title_element = waitForTitleElement();
            return title_element.getText();
        }
    }


    public void swipeToFooter(){
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40);
        }
        else{
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    1000);
        }
    }

    public void addArticleToMyList(String name_of_folder){
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find options to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );

    }

    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );
    }

    public void initNewSearch(){
        this.waitForElementAndClick(
                OPEN_NEW_SEARCH_BUTTON,
                "Cannot find button to open new search",
                5
        );
    }


    public void addArticleToMyListAlreadyCreate(String name_of_folder){

        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find options to add article to reading list",
                5
        );

        String folder_name = getNameOfFolderTpl(name_of_folder);

        this.waitForElementAndClick(
                folder_name,
                "Cannot find just created folder",
                5
        );
    }

    public void openMyLists(){
        this.waitForElementAndClick(
                MY_LISTS_BUTTON,
                "Cannot find navigation button to 'My lists'",
                5
        );
    }

    public void addArticlesToMySaved(){
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        int count = this.getAmountOfElements(
                SYNC_ARTICLES_TITLE
        );
        if (count >0){
            this.waitForElementAndClick(
                    CLOSE_SYNC_ARTICLES_BUTTON,
                    "Unable to find close button",
                    5
            );
        }
    }

}
