package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTest extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {

        String name_of_folder = "Learning programming";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        NavigationUI NavigationUI = new NavigationUI(driver);
        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Java (programming language)");
        ArticlePageObject.waitForTitleElement();

        String article_title = ArticlePageObject.getArticleTitle();

        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();
        NavigationUI.clickMyLists();
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title);

    }

    @Test
    public void testCheckSearchArticleInBackground() {

        String search_line = "java";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

        this.backgroundApp(2);

        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testSaveTwoArticlesToMyListAndDeleteOne() {
        String name_of_folder = "Learning programming";
        String first_search = "Java";
        String first_article_name = "Java (programming language)";
        String second_article_name = "JavaScript";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(first_search);
        SearchPageObject.clickByArticleWithSubstring(first_article_name);
        String title_of_first_article = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.initNewSearch();
        SearchPageObject.typeSearchLine(first_search);
        SearchPageObject.clickByArticleWithSubstring(second_article_name);
        String title_of_second_article = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToMyListAlreadyCreate(name_of_folder);
        ArticlePageObject.closeArticle();
        ArticlePageObject.openMyLists();
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.findArticleInFolderByName(title_of_first_article);
        MyListsPageObject.findArticleInFolderByName(title_of_second_article);

        MyListsPageObject.deleteArticleInFolderByName(first_article_name);

        MyListsPageObject.findArticleInFolderByName(title_of_second_article);
        MyListsPageObject.openArticleInFolderByName(second_article_name);
        String title_of_second_article_in_folder = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title is not the same as in article in folder",
                title_of_second_article,
                title_of_second_article_in_folder
        );
    }
}
