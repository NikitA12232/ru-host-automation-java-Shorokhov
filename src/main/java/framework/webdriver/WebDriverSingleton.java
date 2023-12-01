package framework.webdriver;


import framework.loging.Log;
import org.openqa.selenium.WebDriver;

public class WebDriverSingleton {


    private WebDriver wrappedDriver;

    private static ThreadLocal<WebDriverSingleton> instance = new ThreadLocal<>();

    private WebDriverSingleton() {
        wrappedDriver = WebDriverFactory.getWebDriver();
    }

    public static synchronized WebDriverSingleton getInstance() {
        if (instance.get() == null) {
            instance.set(new WebDriverSingleton());
        }
        return instance.get();
    }

    public WebDriver getDriver() {
        return wrappedDriver;
    }

    public void closeDriver() {
        Log.logDebug("Stop browser");
        try {
            wrappedDriver.quit();
        } finally {
            instance.remove();
        }
    }
}