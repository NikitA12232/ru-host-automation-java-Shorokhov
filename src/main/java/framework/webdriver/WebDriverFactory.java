package framework.webdriver;


import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import framework.loging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {

    private WebDriverFactory() {
    }

    private static final String HOST = "http://localhost:4445";


    public static WebDriver getWebDriver() {
        RemoteWebDriver webDriver = null;
        DesiredCapabilities capabilities;
        if ("chrome".equals(System.getProperty("browser"))) {
            capabilities = new DesiredCapabilities();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--disable-web-security");
            options.addArguments("--disable-popup-blocking");
            HashMap<String, Object> chromePref = new HashMap<>();
            chromePref.put("profile.default_content_settings.popups", 0);
            chromePref.put("download.directory_upgrade", true);

            options.setExperimentalOption("prefs", chromePref);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            options.merge(capabilities);
            try {
                webDriver = new RemoteWebDriver(new URL(HOST), options);
                webDriver.manage().timeouts()
                        .implicitlyWait(Duration.ofSeconds(15));
                webDriver.manage().timeouts()
                        .pageLoadTimeout(Duration.ofSeconds(30));
                webDriver.manage().timeouts()
                        .scriptTimeout(Duration.ofSeconds(30));
                webDriver.manage().window().maximize();
                Log.logInfoMessage("Current browser is Chrome. Screen resolution is: " +
                        webDriver.manage().window().getSize());
            } catch (MalformedURLException e) {
                Log.logInfoMessage("URL is not correct ");
            }
        } else {
            throw new RuntimeException("No support ");
        }
        return webDriver;
    }
}
