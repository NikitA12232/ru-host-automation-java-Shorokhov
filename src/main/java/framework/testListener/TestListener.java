package framework.testListener;


import framework.webdriver.WebDriverFactory;
import framework.webdriver.WebDriverSingleton;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogType;

public class TestListener  {


    public void testFailed(){
        Allure.getLifecycle().addAttachment("Скриншот на месте падения теста", "image/png", "png",
                ((TakesScreenshot) WebDriverFactory.getWebDriver()).getScreenshotAs(OutputType.BYTES));
        Allure.addAttachment("Логи после падения теста: ",
                String.valueOf(WebDriverFactory.getWebDriver().manage().logs().get(LogType.BROWSER).getAll()));


        WebDriverSingleton.getInstance().closeDriver();
    }


    public void testSuccessful(){
        Allure.getLifecycle().addAttachment("Скриншот после успешного прохождения теста", "image/png", "png",
                ((TakesScreenshot) WebDriverFactory.getWebDriver()).getScreenshotAs(OutputType.BYTES));
        Allure.addAttachment("Логи после успешного прохождения теста: ",
                String.valueOf(WebDriverFactory.getWebDriver().manage().logs().get(LogType.BROWSER).getAll()));

        WebDriverSingleton.getInstance().closeDriver();
    }
}
