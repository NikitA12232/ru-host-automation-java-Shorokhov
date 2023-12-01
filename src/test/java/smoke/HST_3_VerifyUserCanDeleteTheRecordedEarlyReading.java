package smoke;

import framework.page.HomePageNavigation;
import framework.page.MiscellaneousPageNavigation;
import framework.testListener.OpenCloseBrowserEachClassListener;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(description = "HST_3_VerifyUserCanDeleteTheRecordedEarlyReading",
        groups = {"full", "smoke"})

public class HST_3_VerifyUserCanDeleteTheRecordedEarlyReading extends OpenCloseBrowserEachClassListener {

    @BeforeClass(inheritGroups = false, alwaysRun = true)
    public void setup() {
        HomePageNavigation homePageNavigation = new HomePageNavigation()
                .clickLoginButton()
                .fillSignInForm()
                .clickSignInButton()
                .clickLoginButton()
                .clickPersonalCabinetButton()
                .clickMiscellaneousButton();
    }

    @Owner(value = "Белин Константин")
    @Test
    public void VerifyTheRecordedEarlyReadingsAreDisplayed() {
        MiscellaneousPageNavigation miscellaneousPageNavigation = new MiscellaneousPageNavigation()
                .clickDeleteButtonLastRecordedValue();
        Assert.assertTrue(miscellaneousPageNavigation.checkYesButtonInPopUpIsClickable(),
                "Delete button is not clickable!");
        Assert.assertTrue(miscellaneousPageNavigation.deleteButtonIsDisplayed(),
                "Delete button is not displayed!");
    }
}
