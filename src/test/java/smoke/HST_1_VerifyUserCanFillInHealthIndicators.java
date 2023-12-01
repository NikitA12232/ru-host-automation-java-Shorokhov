package smoke;

import framework.page.MiscellaneousPageNavigation;
import framework.testListener.OpenCloseBrowserEachClassListener;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.page.HomePageNavigation;

import java.util.List;

@Test(description = "HST_1_VerifyUserCanFillInHealthIndicators",
    groups = {"full", "smoke"})

public class HST_1_VerifyUserCanFillInHealthIndicators {

    private List<String> recordReadingList;
    private MiscellaneousPageNavigation miscellaneousPageNavigation;

    @BeforeClass(inheritGroups = false, alwaysRun = true)
    public void setup() {
         new HomePageNavigation()
                .clickLoginButton()
                .fillSignInForm()
                .clickSignInButton()
                .clickLoginButton()
                .clickPersonalCabinetButton()
                .clickMiscellaneousButton();
        recordReadingList = miscellaneousPageNavigation.getRecordReadingText();
    }

    @Owner(value = "Белин Константин")
    @Test
    public void verifyUserCanFillInHealthIndicators() {
        MiscellaneousPageNavigation miscellaneousPageNavigation = new MiscellaneousPageNavigation()
                .clickRecordReadingButton();
        for (int recordReadingIndex = 0; recordReadingIndex < recordReadingList.size(); recordReadingIndex++) {
            Assert.assertTrue(miscellaneousPageNavigation.checkRecordReadingFieldByIndexIsDisplayed(recordReadingIndex + 1),
                    "Field number \"%s\" is not displayed!");
        }
    }
}

