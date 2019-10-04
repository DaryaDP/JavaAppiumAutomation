package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListsPageObject extends MyListsPageObject {

    static {
         FOLDER_BY_NAME_TPL = "xpath://*[contains(@text,'{FOLDER_NAME}')]";
         ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";
         NAME_OF_ARTICLE_IN_FOLDER_TPL = "xpath://*[contains(@text,'{SUBSTRING}')]";
    }

    public AndroidMyListsPageObject(RemoteWebDriver driver){
        super(driver);
    }
}
