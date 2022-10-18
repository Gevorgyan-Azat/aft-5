package framework.pages;

import framework.pages.blocks.NavigationBlock;
import framework.pages.enums.CheckBox;
import framework.pages.enums.Field;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CreditPage extends BasePage {

    @FindBy(xpath = "//iframe[@id='iFrameResizer0']")
    private WebElement filterIframe;

    @FindBy(xpath = "//input[@class='dc-input__input-6-1-2']")
    private List<WebElement> creditFilterField;

    @FindBy(xpath = "")
    private WebElement creditGoal;

    @FindBy(xpath = "")
    private WebElement region;

    @FindBy(xpath = "//div[@class='dc-input__input-container-6-1-2' and contains(., 'Стоимость недвижимости')]/input")
    private WebElement homePrice;

    @FindBy(xpath = "//div[@class='dc-input__input-container-6-1-2' and contains(., 'Первоначальный взнос')]/input")
    private WebElement initialPayment;

    @FindBy(xpath = "//div[@class='dc-input__input-container-6-1-2' and contains(., 'Срок кредита')]/input")
    private WebElement creditPeriod;

    @FindBy(xpath = "//div[@class='_2cwqL96CPji6zNQD6fZqsT']//span[contains(., 'Страхование жизни и здоровья')]/../..//input")
    private WebElement checkBoxInsurance;

    @FindBy(xpath = "//div[@class='_1WaG3oaWG0Bju0cwYluhmL']//span[contains(., 'Получаю зарплату ')]/../..//input")
    private WebElement checkBoxGetSalaryInSber;

    @FindBy(xpath = "//div[@class='_1WaG3oaWG0Bju0cwYluhmL']//span[contains(., 'материнский капитал')]/../..//input")
    private WebElement checkBoxUseMaternityCapital;

    @FindBy(xpath = "//div[@class='_2Vf5EdTyDQkLO5LJU3Rr8f']//span[contains(., 'Своя ставка')]/../..//input")
    private WebElement checkBoxUseMyPercent;

    @FindBy(xpath = "//div[@class='_1HBo3kyy3N2OeBiebn9A3b']//span[contains(., 'на Домклик')]/../..//input")
    private WebElement checkBoxBuyingInDomclick;

    @FindBy(xpath = "//div[@class='_1HBo3kyy3N2OeBiebn9A3b']//span[contains(., 'Электронная регистрация')]/../..//input")
    private WebElement checkBoxElRegistrationDeal;


    private final NavigationBlock navigation = new NavigationBlock();

    public CreditPage selectCheckBox(CheckBox checkBoxName, boolean expectedStatus){
        switch (checkBoxName){
            case GET_SALARY_IN_SBERBANK:
                checkBox(checkBoxGetSalaryInSber, expectedStatus);
                return this;
            case USE_MATERNITY_CAPITAL:
                checkBox(checkBoxUseMaternityCapital, expectedStatus);
                return this;
            case MY_PERCENT:
                checkBox(checkBoxUseMyPercent, expectedStatus);
                return this;
            case BUYING_IN_DOMCLICK:
                checkBox(checkBoxBuyingInDomclick, expectedStatus);
                return this;
            case INSURANCE_LIFE_AND_HEALTH:
                checkBox(checkBoxInsurance, expectedStatus);
                return this;
            case EL_REGISTRATION_DEAL:
                checkBox(checkBoxElRegistrationDeal, expectedStatus);
                return this;
            default:
                Assert.fail("Не удалось найти" + checkBoxName);
                break;
        }
        return this;
    }

    private void checkBox(WebElement checkBox, boolean expectedStatus) {
        driverManager.getDriver().switchTo().frame(filterIframe);
        waitForJavascript();
        if (expectedStatus) {
            if (Boolean.parseBoolean(checkBox.getAttribute("ariaChecked"))) {
                driverManager.getDriver().switchTo().defaultContent();
            } else {
                checkBoxClick(checkBox);
            }
        }
        if (!(expectedStatus)) {
            if (!(Boolean.parseBoolean(checkBox.getAttribute("ariaChecked")))) {
                driverManager.getDriver().switchTo().defaultContent();
            } else {
                checkBoxClick(checkBox);
            }
        }
    }

    private void checkBoxClick(WebElement checkBox) {
        scrollElementInCenter(checkBox);
        js.executeScript("arguments[0].click();", checkBox);
        driverManager.getDriver().switchTo().defaultContent();
    }

    public CreditPage selectField(Field fieldName, String value) {
        switch (fieldName){
            case CREDIT_GOAL:
            case REGION:
                return this;
            case HOME_PRICE:
                field(homePrice,value);
                return this;
            case INITIAL_PAYMENT:
                field(initialPayment,value);
                return this;
            case CREDIT_PERIOD:
                field(creditPeriod,value);
                return this;
            default:
                Assert.fail("Не удалось найти" + fieldName);
                break;
        }
        return this;
    }

    private void field(WebElement element, String fieldValue){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(filterIframe));
        waitForJavascript();
        fillField(element, fieldValue);
        waitUtilElementToBeVisible(checkValue(element, fieldValue));
        Assert.assertEquals("", fieldValue, convertValueToNumb(element));
        driverManager.getDriver().switchTo().defaultContent();
    }

//    public CreditPage selectField(String fieldName, String fieldValue) {
//        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(filterIframe));
//        waitForJavascript();
//        for (WebElement creditFilter : creditFilterField) {
//            if (creditFilter.findElement(By.xpath("./..//label")).getAttribute("textContent").contains(fieldName)) {
//                fillField(creditFilter, fieldValue);
//                waitUtilElementToBeVisible(checkValue(creditFilter, fieldValue));
//                Assert.assertEquals("", fieldValue, convertValueToNumb(creditFilter));
//                driverManager.getDriver().switchTo().defaultContent();
//                return this;
//            }
//        }
//        Assert.fail("");
//        return this;
//    }



    private void fillField(WebElement element, String value) {
        scrollElementInCenter(element);
        js.executeScript("arguments[0].click();", element);
        element.sendKeys(Keys.CONTROL + "a");
        sendKeyArray(element, value);
        element.sendKeys(Keys.ENTER);
    }

    private void sendKeyArray(WebElement element, String value) {
        String[] strValue = value.split("");
        for (String s: strValue) {
            element.sendKeys(s);
        }
    }

    private String convertTextToNumb(WebElement element) {
        return element.getText().replaceAll("\\D", "");
    }

    private String convertValueToNumb(WebElement element) {
        return element.getAttribute("value").replaceAll("\\D", "");
    }

    private WebElement checkValue(WebElement element, String value){
        double startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + 5000){
            String val = convertValueToNumb(element);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) { }
            if(val.equals(value)){
                return element;
            }
        }
        return element;
    }

    private void waitForJavascript() {
        double startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + 5000) {
            String prevState = driverManager.getDriver().getPageSource();
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignore) { }
            if (prevState.equals(driverManager.getDriver().getPageSource())) {
                return;
            }
        }
    }

    public NavigationBlock getNavigation() {
        return navigation;
    }
}
