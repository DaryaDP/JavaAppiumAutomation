package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON_BEFORE_LOGIN = "css:li#page-actions-watch>a#ca-watch.mw-ui-icon";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://a[contains(@class,'mw-ui-icon mw-ui-icon-element mw-ui-icon-wikimedia-star-base20 watch-this-article view-border-box')]";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://a[contains(@class,'mw-ui-icon mw-ui-icon-element mw-ui-icon-wikimedia-unStar-progressive watch-this-article watched view-border-box')]";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}