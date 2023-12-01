package framework.page;

import framework.property.UserPropertyConst;
import framework.ui.Element;
import io.qameta.allure.Step;


public class HomePageNavigation extends AbstractPage {

    private Element loginButton = Element.byXpath("(//*[@ class='dropdown-container']/div/a)[3]");
    private Element signInButton = Element.byXpath("//*[@value='Sign In']");
    private Element usernameField = Element.byXpath("//*[@id='username']");
    private Element passwordField = Element.byXpath("//*[@id='password']");
    private Element personalCabinetButton = Element.byXpath("(//*[@ class='ui-menuitem-text'])[2]");
    private Element miscellaneousButton = Element.byXpath("(//*[@ class='block ng-star-inserted'])[4]");
    private Element mainNameOfPage = Element.byXpath("//*[@title='Электронная регистратура']");
    private Element mainLogo = Element.byXpath("//*[@class='imask imask-logo c-dsea']");


    @Step("Click Log in button")
    public HomePageNavigation clickLoginButton() {
        loginButton.waitForVisibility();
        loginButton.click();

        return this;
    }

    @Step("Fill Sign in form")
    public HomePageNavigation fillSignInForm() {
        usernameField.type(UserPropertyConst.USER_LOGIN);
        passwordField.type(UserPropertyConst.USER_PASSWORD);
        return this;
    }

    @Step("Click Sign in button")
    public HomePageNavigation clickSignInButton() {
        signInButton.click();
        return this;
    }

    @Step("Click personal cabinet button")
    public HomePageNavigation clickPersonalCabinetButton() {
        personalCabinetButton.click();
        return this;
    }

    @Step("Click miscellaneous button")
    public HomePageNavigation clickMiscellaneousButton() {
        miscellaneousButton.click();
        return this;
    }

    @Step("Check main name of page")
    public boolean mainLogoDisplayed() {
        return mainNameOfPage.isDisplayed();
    }

    @Step("Check main logo")
    public boolean checkMainLogo() {
        return mainLogo.isDisplayed();
    }


    @Override
    public boolean isScreenLoaded() {
        return mainLogoDisplayed() && checkMainLogo();
    }
}
