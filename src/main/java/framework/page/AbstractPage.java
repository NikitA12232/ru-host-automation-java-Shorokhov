package framework.page;

import static framework.loging.Log.logInfoMessage;
import static java.lang.String.format;

import framework.webdriver.WebDriverSingleton;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public abstract class AbstractPage {

    protected final WebDriver webDriver = WebDriverSingleton.getInstance().getDriver();

    protected JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) webDriver;
    }

    public abstract boolean isScreenLoaded();

    public void clickBackButton() {
        logInfoMessage("Pressing browser's 'Back' button");
        webDriver.navigate().back();
    }

    public void openPage(String url) {
        logInfoMessage("Loading web-page: " + url);
        webDriver.get(url);
    }

    public void setWindowSize(int width, int height) {
        logInfoMessage(format("Setting window size to width:%d height:%d", width, height));
        webDriver.manage().window().setSize(new Dimension(width, height));
    }

    public void closeBrowser() {
        WebDriverSingleton.getInstance().closeDriver();
    }

}
