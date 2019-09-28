package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTest extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {

        String name_of_folder = "Learning programming";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Java (programming language)");
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);
        }
        else{
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();
        NavigationUI.clickMyLists();

        if(Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.swipeByArticleToDelete(article_title);
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title);

    }

    @Test
    public void testCheckSearchArticleInBackground() {

        String search_line = "java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

        this.backgroundApp(2);

        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testSaveTwoArticlesToMyListAndDeleteOne() throws InterruptedException {
        String name_of_folder = "Learning programming";
        String first_search = "Java";
        String first_article_name = "Java (programming language)";
        String second_article_name = "JavaScript";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(first_search);
        SearchPageObject.clickByArticleWithSubstring(first_article_name);
        String title_of_first_article = ArticlePageObject.getArticleTitle(first_article_name);

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);
        }
        else{
            ArticlePageObject.addArticlesToMySaved();
        }

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.initNewSearch();
        }
        else{
            ArticlePageObject.closeArticle();
        }
        if(Platform.getInstance().isIOS()){
            SearchPageObject.clearSearchField();
        }

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(first_search);
        SearchPageObject.clickByArticleWithSubstring(second_article_name);

        String title_of_second_article = ArticlePageObject.getArticleTitle(second_article_name);

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyListAlreadyCreate(name_of_folder);
        }
        else{
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();
        NavigationUI.clickMyLists();
        if(Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.findArticleInFolderByName(title_of_first_article);
        MyListsPageObject.findArticleInFolderByName(title_of_second_article);

        MyListsPageObject.swipeByArticleToDelete(first_article_name);

        MyListsPageObject.findArticleInFolderByName(title_of_second_article);
        MyListsPageObject.openArticleInFolderByName(second_article_name);
        Thread.sleep(20000);
        String title_of_second_article_in_folder = ArticlePageObject.getArticleTitle(second_article_name);

        assertEquals(
                "Article title is not the same as in article in folder",
                title_of_second_article,
                title_of_second_article_in_folder
        );
    }
}
