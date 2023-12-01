package framework.ui;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static framework.loging.Log.logInfoMessage;
import static framework.ui.LogManager.*;

public class Element {

    public static final String DISABLED_ATTRIBUTE = "disabled";
    public static final String ENABLED_ATTRIBUTE = "enabled";
    public static final String ATTRIBUTE_CLASS = "class";
    public static final String ATTRIBUTE_CHECKED = "checked";

    private final By by;
    private final LocatorManager locatorManager;
    private final WebDriver webDriver = new ChromeDriver();


    private Element(By by, LocatorManager locatorManager) {
        this.by = by;
        this.locatorManager = locatorManager;
    }



    /* Strategies*/


    public static Element byXpath(String xpath, Object... params) {
        String locator = String.format(xpath, params);
        logSearchElement("xpath", locator);
        return new Element(By.xpath(locator),
                LocatorManager.byXpath(xpath, params));
    }

    public static Element byCss(String css, Object... params) {
        String locator = String.format(css, params);
        logSearchElement("css", locator);
        return new Element(By.cssSelector(String.format(css, params)),
                LocatorManager.byCss(css, params));
    }

    public static Element byClassName(String className) {
        By locator = By.className(className);
        logSearchElement("className", className);
        return new Element(locator, LocatorManager.byClassName(className));
    }

    public static Element byId(String id) {
        By locator = By.id(id);
        logSearchElement("id", id);
        return new Element(locator, LocatorManager.byId(id));
    }

    public static Element byName(String name) {
        By locator = By.name(name);
        logSearchElement("name", name);
        return new Element(locator, LocatorManager.byName(name));
    }

    public static Element byLinkText(String text) {
        By locator = By.linkText(text);
        logSearchElement("linkText", text);
        return new Element(locator, LocatorManager.byLinkText(text));
    }

    public static Element byTagName(String tagName) {
        By locator = By.tagName(tagName);
        logSearchElement("tagName", tagName);
        return new Element(locator, LocatorManager.byTagName(tagName));
    }


    /*Actions*/


    public void click() {
        logClickOnAction();
        waitForClickable();
        nativeClick();
    }

    public void waitForClickableAndClick() {
        logClickOnAction();
        waitForClickable();
        getWrappedWebElement().click();
    }

    public void tryClick() {
        logClickOnAction();
        waitForVisibility().click();
    }

    public void tryClickNoWait() {
        logClickOnAction();
        nativeClick();
    }

    public void doubleClick() {
        logClickOnAction();
        new Actions(webDriver).doubleClick(waitForClickable());
    }

    public void mouseOverAndClick() {
        new Actions(webDriver).moveToElement(getWrappedWebElement()).click().build().perform();
        logInfoMessage("Mouse over and click", getWrappedWebElement());
    }

    public void mouseOverAndClickAndHold() {
        new Actions(webDriver).moveToElement(getWrappedWebElement()).clickAndHold().perform();
        logInfoMessage("Mouse over and click and hold", getWrappedWebElement());
    }



    public void selectByText(String value) {
        logFillInAction(value);
        new Select(getWrappedWebElement()).selectByVisibleText(value);
    }

    public void selectByValue(String value) {
        logFillInAction(value);
        new Select(getWrappedWebElement()).selectByValue(value);
    }

    public void selectByIndex(int value) {
        logFillInAction(Integer.toString(value));
        new Select(getWrappedWebElement()).selectByIndex(value);
    }

    public void deselectAll() {
        logFillInAction("Deselecting all options");
        new Select(getWrappedWebElement()).deselectAll();
    }

    public void type(String value) {
        logFillInAction(value);
        WebElement element = getWrappedWebElement();
        clearInput();
        element.sendKeys(value);
    }

    public void typeWithoutClear(String value) {
        logFillInAction(value);
        getWrappedWebElement().sendKeys(value);
    }

    public void clearInput() {
        logClickOnAction();
        waitForVisibility().clear();
    }

    public void clearInputUsingBackspace(){
        logClickOnAction();
        WebElement fieldToBeCleared = waitForVisibility();
        for(int i = fieldToBeCleared.getAttribute("value").length(); i > 0; i--){
            fieldToBeCleared.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void nativeClearAndType(String value) {
        logFillInAction(value);
        waitForVisibility().sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), value);
    }

    private void nativeClick() {
        logClickOnAction();
        new Actions(webDriver).click(getWrappedWebElement()).build().perform();
    }

    public void pageDown() {
//        logInfoMessage("Page Down", webDriver);
        new Actions(webDriver).sendKeys(Keys.PAGE_DOWN).build().perform();
    }

    public void enter() {
//        logInfoMessage("ENTER", webDriver);
        new Actions(webDriver).sendKeys(Keys.ENTER).build().perform();
    }


    public void clickOnInvisibleElement() {
        String script = "var object = arguments[0];"
                + "var theEvent = document.createEvent(\"MouseEvent\");"
                + "theEvent.initMouseEvent(\"click\", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
                + "object.dispatchEvent(theEvent);";
        ((JavascriptExecutor) webDriver).executeScript(script, getWrappedWebElement());
    }

    public void clickJs() {
        getJavascriptExecutor().executeScript("arguments[0].click();", getWrappedWebElement());
    }

    /*States*/


    public boolean isDisplayed(int timeOutInSeconds) {
        boolean isDisplayed;
        try {
            isDisplayed = waitForVisibility(timeOutInSeconds) != null;
        } catch (TimeoutException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isNotDisplayed() {
        boolean isNotDisplayed;
        try {
            isNotDisplayed = waitForInvisibility();
        } catch (TimeoutException e) {
            isNotDisplayed = true;
        }
        return isNotDisplayed;
    }

    public boolean isDisplayed() {
        return isDisplayed(30);
    }

    public boolean isDisplayedShortTimeOut() {
        return isDisplayed(15);
    }

    private boolean isPresent(boolean expected, int timeout) {
        try {
            webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            return (!webDriver.findElements(by).isEmpty()) == expected;
        } finally {
            webDriver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);
        }
    }

    public boolean isPresent() {
        return isPresent(15);
    }

    public boolean isPresent(int timeout) {
        return isPresent(true, timeout);
    }

    public boolean isPresentNoWait() {
        return isPresent(true, 0);
    }

    public boolean isAbsent(int timeout) {
        return isPresent(false, timeout);
    }

    public boolean isAbsent() {
        return isPresent(false, 15);
    }

    public boolean isClickable() {
        return waitForClickable() != null;
    }

    public boolean isClickable(int timeout) {
        return waitForClickable(timeout) != null;
    }



    public boolean isAllElementsDisplayed() {
        return getElements().stream().allMatch(Element::isDisplayed);
    }

    public boolean isAllElementsPresent() {
        return getElements().stream().allMatch(Element::isPresent);
    }

    /*Waits*/


    public WebElement waitForPresence() {
        return new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public List<WebElement> waitForPresenceOfAllElements(int timeout) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public WebElement waitForPresenceOfNestedElement(By parentLocator, By childLocator, int timeout) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentLocator, childLocator));
    }

    public WebElement waitForPresenceOfNestedElement(By parentLocator, By childLocator) {
        return waitForPresenceOfNestedElement(parentLocator, childLocator, 30);
    }

    public List<WebElement> waitForPresenceOfNestedElements(By parentLocator, By childLocator) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfNestedElementsLocatedBy(parentLocator, childLocator));
    }

    public WebElement waitForVisibility(int timeout) {
        logInfoMessage("Wait element will be visibility");
        return new WebDriverWait(webDriver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }


    public WebElement waitForVisibility() {
        return waitForVisibility(30);
    }

    public WebElement waitForClickable() {
        logInfoMessage("Wait element will be clickable");
        return new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement waitForClickable(int timeout) {
        logInfoMessage("Wait element will be clickable");
        return new WebDriverWait(webDriver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    public boolean waitForInvisibility() {
        logInfoMessage("Wait until the element is invisible");
        return new WebDriverWait(webDriver, Duration.ofSeconds(150))
                .until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /*Getters*/


    public WebElement getWrappedWebElement() {
        return waitForPresence();
    }


    public List<Element> getElements() {
        return getElements();
    }


    public String getText() {
        String text = waitForVisibility().getText();
        return text;
    }

    public List<String> getElementsText() {
        return getElements().stream().map(Element::getText).collect(Collectors.toList());
    }



    public String getAttributeValue(String attribute) {
        String attributeValue = getWrappedWebElement().getAttribute(attribute);
        return attributeValue;
    }

    public String getCssValue(String property) {
        String cssValue = getWrappedWebElement().getCssValue(property);
        return cssValue;
    }

    public By getBy() {
        return by;
    }

    private LocatorManager getLocatorManager() {
        return locatorManager;
    }

    private JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) webDriver;
    }

    public boolean isDisabled() {
        boolean isDisabled;
        try {
            isDisabled = getAttributeValue(DISABLED_ATTRIBUTE).equals("true");
        } catch (NullPointerException e) {
            isDisabled = false;
        }
        return isDisabled;
    }
}
