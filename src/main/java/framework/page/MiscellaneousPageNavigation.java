package framework.page;

import framework.utils.RandomUtils;
import framework.ui.Element;
import io.qameta.allure.Step;

import java.util.List;

public class MiscellaneousPageNavigation extends AbstractPage {

    private static final String RECORD_READING_BY_INDEX_PATTERN ="(//*[@ class='ui-g ng-star-inserted'])[%s]";

    private Element recordReadingsList = Element.byXpath("//*[@ class='ui-g ng-star-inserted']");
    private Element recordReadingsButton = Element.byXpath("//*[@ class='create-block']/a");
    private Element inputTemperatureField = Element.byXpath("(//*[@class='ui-g-6'])[1]");
    private Element saveButtonInRecordReadingWindow = Element.byXpath(" (//*[@class='dark btn-shadow'])");
    private Element lastRecordedTemperature = Element.byXpath(" (//*[@class='row mychildren ng-star-inserted']//span)[1]");
    private Element deleteButtonLastRecordedValue = Element.byXpath(
            "(//*[@class='button bordered lower rel action-button' and contains(text(),'Удалить')])[1]");
    private Element yesButtonInPopUp = Element.byXpath("(//*[@class='button'])[1]");
    private Element editButton = Element.byXpath("(//*[@class='ico icon-pencil'])[1]");


    @Step("Click record reading button")
    public MiscellaneousPageNavigation clickRecordReadingButton() {
        recordReadingsButton.click();
        return this;
    }

    @Step("Get record reading list")
    public List<String> getRecordReadingText() {
        return recordReadingsList.getElementsText();
    }

    @Step("Check record reading field by index")
    public boolean checkRecordReadingFieldByIndexIsDisplayed(int fieldIndex) {
       return Element.byXpath(RECORD_READING_BY_INDEX_PATTERN,fieldIndex).isDisplayed(15);
    }

    @Step("Click input temperature field")
    public MiscellaneousPageNavigation clickInputTemperatureField() {
        inputTemperatureField.click();
        inputTemperatureField.type(String.valueOf(RandomUtils.getRandomNumber(333)));
        saveButtonInRecordReadingWindow.click();
        return this;
    }

    @Step("Get input temperature field text")
    public String getInputTemperatureFieldText() {
        return inputTemperatureField.getText();
    }

    @Step("Get last recorded temperature text")
    public String getLastRecordedTemperatureText() {
        return lastRecordedTemperature.getText();
    }

    @Step("Click Delete button last recorded value")
    public MiscellaneousPageNavigation clickDeleteButtonLastRecordedValue() {
        deleteButtonLastRecordedValue.click();
        return this;
    }

    @Step("Delete button is displayed ")
    public boolean deleteButtonIsDisplayed() {
        return deleteButtonLastRecordedValue.isDisplayed();
    }

    @Step("Check yes button in pop up window is clickable")
    public boolean checkYesButtonInPopUpIsClickable() {
        return yesButtonInPopUp.isClickable();
    }

    @Step("Check edit button is displayed")
    public boolean editButtonIsDislayed() {
        return editButton.isDisplayed();
    }
    @Step("Check edit button is clickable")
    public boolean checkEditButtonIsClickable() {
        return editButton.isClickable();
    }


    @Override
    public boolean isScreenLoaded() {
        return false;
    }
}
