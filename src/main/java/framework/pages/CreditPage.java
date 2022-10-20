package framework.pages;

import framework.pages.blocks.NavigationBlock;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreditPage extends BasePage {

    @FindBy(xpath = "//iframe[@id='iFrameResizer0']")
    private WebElement calculatorIframe;

    private final NavigationBlock navigation = new NavigationBlock();

    @Step("Выбор поля '{fieldName}' и заполнение его данными '{fieldValue}'")
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

    @Step("Изменение состояния CheckBox '{checkBoxName}' на '{expectedStatus}'")
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

    @Step("Проверка результата поля {resultName}")
    public CreditPage checkResultValue(Result resultName, String checkValue) {
        iFrameOn(calculatorIframe);
        waitForJavascript();
        WebElement resultValue = driverManager.getDriver().findElement(By.xpath(resultName.getValue()));
        scrollElementInCenter(resultValue);
        String a = convertTextToNumb(resultValue);
        Assert.assertEquals("Значение '" + resultName + "' не совпадает с ожидаемым значением",
                checkValue, convertTextToNumb(resultValue));
        iFrameOff();
        return this;
    }

    public NavigationBlock getNavigation() {
        return navigation;
    }

    public enum Field {

        HOME_PRICE("Стоимость недвижимости"),
        INITIAL_PAYMENT("Первоначальный взнос"),
        CREDIT_PERIOD("Срок кредита");

        private String value;

        Field(String value) {
            this.value = value;
        }

        public String getValue() {
            return "//div[./label[contains(text(), '"+ value +"')]]//input[@class='dc-input__input-6-1-2' and @inputmode]";
        }
    }

    public enum CheckBox {

        GET_SALARY_IN_SBERBANK("Получаю зарплату на счёт"),
        USE_MATERNITY_CAPITAL("Использовать материнский капитал"),
        MY_PERCENT("Своя ставка"),
        BUYING_IN_DOMCLICK("покупке недвижимости на Домклик"),
        INSURANCE_LIFE_AND_HEALTH("Страхование жизни и здоровья"),
        EL_REGISTRATION_DEAL("Электронная регистрация сделки");

        private String value;

        CheckBox(String value) {
            this.value = value;
        }

        public String getValue() {
            return "//div[./div[./span[contains(text(), '" + value + "')]]]//input[@type='checkbox']";
        }
    }

    public enum Result {

        MONTHLY_PAYMENT("Ежемесячный платеж"),
        PERCENT_RATE("Процентная ставка"),
        CREDIT_SUM("Сумма кредита"),
        REQUIRED_INCOME("Необходимый доход");

        private String value;

        Result(String value) {
            this.value = value;
        }

        public String getValue() {
            return "//div[@class='_1aQ5fS4QO26GDWmJlBTrB6']//span[@class='_2K-vC4nTzrGG1TQHQ2HGL' and contains(text(), '"
                    + value + "')]/following-sibling::span/span";
        }
    }
}
