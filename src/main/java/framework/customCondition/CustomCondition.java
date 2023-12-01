package framework.customCondition;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomCondition {


    public static ExpectedCondition<Boolean> pageLoadCompleted() {
        return driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }
}