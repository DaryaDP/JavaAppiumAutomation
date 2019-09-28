package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {

        String search_line = "Linkin Park Diskography";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {

        String search_line = "sdfrtgyhui";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResulsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }


    @Test
    public void testSearchLabel() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.checkSearchLabelPresent();
        String label = SearchPageObject.getSearchLabelTitle();
        assertEquals(
                "There is no label 'Search…'!",
                "Search…",
                label
        );
    }

    @Test
    public void testSearchSeveralArticles() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        int search_result_size = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(search_result_size > 0);
        SearchPageObject.clearSearchField();
        SearchPageObject.checkNoResultsOfSearch();
    }

    @Test
    public void testSearchResultWithWord() {

        String search_term = "java";
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_term);
        int founded_results = SearchPageObject.getTHeCountOfSearchResult();
        int founded_result_by_search_term = SearchPageObject.getTheCountOfSearchResultsByTerm(search_term);

        assertTrue(founded_results == founded_result_by_search_term);

    }

    @Test
    public void testSearchByTitleAndDescription() {

        String search_term = "java";
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_term);
        SearchPageObject.waitForElementByTitleAndDescription("Java", "Island of Indonesia");
        SearchPageObject.waitForElementByTitleAndDescription("JavaScript","Programming language");
        SearchPageObject.waitForElementByTitleAndDescription("Java (programming language)","Object-oriented programming language");
    }


}
