package framework.pages;

import framework.managers.DriverManager;
import framework.managers.PageManager;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected DriverManager driverManager = DriverManager.getINSTANCE();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 500);
    protected PageManager pageManager = PageManager.getINSTANCE();
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    protected BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected void scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollElementInCenter(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    protected WebElement waitUtilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitUtilElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected boolean waitForChangeStatus(WebElement element, boolean expectedStatus){
       return wait.until(ExpectedConditions.attributeContains(element, "ariaChecked", String.valueOf(expectedStatus)));
    }

    protected WebElement waitForChangeValue(WebElement element, String value){
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
        Assert.fail("Значение " + element + " не соответствует ожидаемому");
        return element;
    }

    protected void waitForChangeURL(String value) {
        double startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + 5000) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
            if (!driverManager.getDriver().getCurrentUrl().equals(value)) {
                return;
            }
        }
    }

    protected void waitForJavascript() {
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

    protected String convertTextToNumb(WebElement element) {
        return element.getText().replaceAll("[^\\d%,]", "");
    }

    protected String convertValueToNumb(WebElement element) {
        return element.getAttribute("value").replaceAll("\\D", "");
    }

    protected boolean checkNoneElement(WebElement element) {
        try {
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void iFrameOn(WebElement iframe){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
    }

    protected void iFrameOff(){
        driverManager.getDriver().switchTo().defaultContent();
    }

    protected void fillField(WebElement element, String value) {
        scrollElementInCenter(element);
        js.executeScript("arguments[0].click();", element);
        element.sendKeys(Keys.CONTROL + "a");
        sendKeyArray(element, value);
        element.sendKeys(Keys.ENTER);
    }

    protected void sendKeyArray(WebElement element, String value) {
        String[] strValue = value.split("");
        for (String s: strValue) {
            element.sendKeys(s);
        }
    }

    protected void scrollAndClick(WebElement element) {
        scrollElementInCenter(element);
        js.executeScript("arguments[0].click();", element);
    }

    protected void clickAndCheckTitle(WebElement element){
        String thisPageTitle = driverManager.getDriver().findElement(By.xpath("//title")).getText();
        waitUtilElementToBeClickable(element).click();
        Assert.assertEquals("Переход на другую страницу не осуществлен",
                thisPageTitle, driverManager.getDriver().findElement(By.xpath("//title")).getText());
    }
}
