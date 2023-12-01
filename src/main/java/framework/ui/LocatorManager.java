package framework.ui;

import org.openqa.selenium.By;

public class LocatorManager {

    private String[] parameters;

    private LocatorStrategy strategy;

    private String locator;

    private LocatorManager(LocatorStrategy strategy, String locator) {
        this.strategy = strategy;
        this.locator = locator;
    }

    static LocatorManager byClassName(String className) {
        return new LocatorManager(LocatorStrategy.CLASS_NAME, className);
    }

    static LocatorManager byCss(String locator, Object... parameters) {
        return new LocatorManager(LocatorStrategy.CSS, String.format(locator, parameters));
    }

    static LocatorManager byId(String id) {
        return new LocatorManager(LocatorStrategy.ID, id);
    }

    static LocatorManager byLinkText(String linkText) {
        return new LocatorManager(LocatorStrategy.LINK_TEXT, linkText);
    }

    static LocatorManager byName(String name) {
        return new LocatorManager(LocatorStrategy.NAME, name);
    }

    static LocatorManager byTagName(String tagName) {
        return new LocatorManager(LocatorStrategy.TAG_NAME, tagName);
    }

    static LocatorManager byXpath(String locator, Object... parameters) {
        return new LocatorManager(LocatorStrategy.XPATH, String.format(locator, parameters));
    }

    public By getBy(LocatorStrategy strategy) {
        switch (strategy) {
            case CLASS_NAME:
                return By.className(getLocatorString());
            case ID:
                return By.id(getLocatorString());
            case NAME:
                return By.name(getLocatorString());
            case LINK_TEXT:
                return By.linkText(getLocatorString());
            case CSS:
                return By.cssSelector(getLocatorString());
            case XPATH:
                return By.xpath(getLocatorString());
            default:
                return null;
        }
    }

    String getLocatorString() {
        return String.format(locator, (Object[]) parameters);
    }

    public LocatorStrategy getStrategy() {
        return strategy;
    }

    public enum LocatorStrategy {
        CLASS_NAME,
        CSS,
        ID,
        LINK_TEXT,
        NAME,
        TAG_NAME,
        XPATH
    }
}

