package smoke;

import framework.page.HomePageNavigation;
import framework.page.MiscellaneousPageNavigation;
import framework.testListener.OpenCloseBrowserEachClassListener;
import framework.utils.RandomUtils;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(description = "HST_2_VerifyTheRecordedEarlyReadingsAreDisplayed",
    groups = {"full", "smoke"})

public class HST_2_VerifyTheRecordedEarlyReadingsAreDisplayed extends OpenCloseBrowserEachClassListener {

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
                .clickRecordReadingButton()
                .clickInputTemperatureField();
        Assert.assertEquals(miscellaneousPageNavigation.getLastRecordedTemperatureText(),
                miscellaneousPageNavigation.getInputTemperatureFieldText(),
                "Value does not match !");
    }
}
