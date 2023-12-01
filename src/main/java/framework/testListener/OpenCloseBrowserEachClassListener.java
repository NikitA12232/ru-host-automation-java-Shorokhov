package framework.testListener;

import framework.loging.Log;
import framework.property.EnvironmentPropertyConst;
import framework.webdriver.WebDriverSingleton;
import io.qameta.allure.Step;
import org.testng.IClassListener;
import org.testng.ITestClass;

public class OpenCloseBrowserEachClassListener extends TestListener implements IClassListener {

    @Override
    public void onBeforeClass(ITestClass testClass) {
        openHomePage();
    }

    @Override
    @Step("Close browser")
    public void onAfterClass(ITestClass testClass) {
        Log.logInfoMessage("Close browser");
        WebDriverSingleton.getInstance().closeDriver();
    }

    @Step("Open browser")
    public static void openHomePage() {
        WebDriverSingleton.getInstance().getDriver().get(EnvironmentPropertyConst.QA_PP86);
        Log.logInfoMessage("Open browser");
    }

}
