package lib.ui.mobile_web;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://ul[contains(@class,'watchlist')]//h3[contains(text(),'{TITLE}')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://li[@title='{TITLE}']/div[contains(@class,'unStar')]";
        NAME_OF_ARTICLE_IN_FOLDER_TPL = "xpath://a[contains(@class,'title')]//h3[text()='{SUBSTRING}']";

    }

    public MWMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}