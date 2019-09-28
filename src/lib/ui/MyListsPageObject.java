package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            NAME_OF_ARTICLE_IN_FOLDER_TPL;


    /* TEMPLATE METHODS */
    private static String getFolderXpathByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getNameOfArticleInFolderTpl(String article_title){
        return NAME_OF_ARTICLE_IN_FOLDER_TPL.replace("{SUBSTRING}", article_title);
    }

    /* TEMPLATE METHODS */


    public MyListsPageObject(AppiumDriver driver){
        super(driver);
    }

    public void openFolderByName(String name_of_folder){

        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    public void swipeByArticleToDelete(String article_title){
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                article_xpath,
                "Cannot find saved article"
        );

        if (Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(
                    article_xpath,
                    "Cannot find saved article");
        }
    }

    public void waitForArticleToDisappearByTitle(String article_title){
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title " + article_title,
                15
        );
    }

    public void waitForArticleToAppearByTitle(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title " + article_title,
                15
        );
    }

    public void findArticleInFolderByName(String article_name){
        String name = getNameOfArticleInFolderTpl(article_name);
        this.waitForElementPresent(
                name,
                "Unable to find added article",
                5
        );
    }

    public void deleteArticleInFolderByName(String article_name){
        String name = getNameOfArticleInFolderTpl(article_name);
            this.swipeElementToLeft(
                name,
                "Cannot find saved article"
        );
    }

    public void openArticleInFolderByName(String article_name){
        String name = getNameOfArticleInFolderTpl(article_name);
            this.waitForElementAndClick(
                    name,
                    "Unable to find added article",
                    5
        );
    }


}
