package smoke;

import framework.page.HomePageNavigation;
import framework.page.MiscellaneousPageNavigation;
import framework.testListener.OpenCloseBrowserEachClassListener;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(description = "HST_4_VerifyUserCanEditTheRecordedEarlyReading",
        groups = {"full", "smoke"})

public class HST_4_VerifyUserCanEditTheRecordedEarlyReading extends OpenCloseBrowserEachClassListener {

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
        MiscellaneousPageNavigation miscellaneousPageNavigation = new MiscellaneousPageNavigation();
        Assert.assertTrue(miscellaneousPageNavigation.editButtonIsDislayed(),
                "Edit button does not displayed!");
        Assert.assertTrue(miscellaneousPageNavigation.checkEditButtonIsClickable(),
                "Edit button does not clickable!");
    }
}
