package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "id:Java (programming language)";
        TITLE_TPL = "id:{TITLE}";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        SYNC_ARTICLES_TITLE = "id:Sync your saved articles?";
        CLOSE_SYNC_ARTICLES_BUTTON = "id:places auth close";
        OPEN_NEW_SEARCH_BUTTON = "id:Search Wikipedia";

        MY_LISTS_BUTTON = "xpath://android.widget.FrameLayout[@content-desc='My lists']";
    }

    public iOSArticlePageObject (RemoteWebDriver driver){
        super(driver);
    }


}
