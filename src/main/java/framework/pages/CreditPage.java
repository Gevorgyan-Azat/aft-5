package framework.pages;

import framework.pages.blocks.NavigationBlock;
import framework.pages.enums.CheckBox;
import framework.pages.enums.Field;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreditPage extends BasePage {

    @FindBy(xpath = "//iframe[@id='iFrameResizer0']")
    private WebElement calculatorIframe;

    private final NavigationBlock navigation = new NavigationBlock();

    public CreditPage selectAndFillField(Field fieldName, String fieldValue) {
        iFrameOn(calculatorIframe);
        waitForJavascript();
        WebElement field = driverManager.getDriver().findElement(By.xpath(fieldName.getValue()));
        fillField(field, fieldValue);
        waitUtilElementToBeVisible(waitForChangeValue(field, fieldValue));
        Assert.assertEquals("Значение поля '" + fieldName + "' не соответствут ожидаемому значению",
                fieldValue, convertValueToNumb(field));
        iFrameOff();
        return this;
    }

    public CreditPage changeStateCheckBox(CheckBox checkBoxName, boolean expectedStatus) {
        iFrameOn(calculatorIframe);
        waitForJavascript();
        WebElement checkBox = driverManager.getDriver().findElement(By.xpath(checkBoxName.getValue()));
        if (expectedStatus) {
            if (!Boolean.parseBoolean(checkBox.getAttribute("ariaChecked"))) {
                scrollAndClick(checkBox);
                waitForChangeStatus(checkBox, expectedStatus);
                Assert.assertTrue("Состояние CheckBox: " + checkBoxName + " не изменилось",
                        Boolean.parseBoolean(checkBox.getAttribute("ariaChecked")));
            }
            iFrameOff();
            return this;
        }
        if (Boolean.parseBoolean(checkBox.getAttribute("ariaChecked"))) {
            scrollAndClick(checkBox);
            waitForChangeStatus(checkBox, expectedStatus);
            Assert.assertFalse("Состояние CheckBox: " + checkBoxName + " не изменилось",
                    Boolean.parseBoolean(checkBox.getAttribute("ariaChecked")));
        }
        iFrameOff();
        return this;
    }

    public NavigationBlock getNavigation() {
        return navigation;
    }
}
