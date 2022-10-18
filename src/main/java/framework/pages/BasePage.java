package framework.pages;

import framework.managers.DriverManager;
import framework.managers.PageManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
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

    protected void waitUtilElementToBeInvisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected boolean checkNoneTitle(WebElement element) {
        try {
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void clickAndCheckLink(WebElement element){
        String subMenuLink = element.getAttribute("href");
        waitUtilElementToBeClickable(element).click();
        Assert.assertEquals("Переход на другую страницу не осуществлен",
                subMenuLink, driverManager.getDriver().getCurrentUrl());
    }

    protected void clickAndCheckTitle(WebElement element){
        String thisPageTitle = driverManager.getDriver().findElement(By.xpath("//title")).getText();
        waitUtilElementToBeClickable(element).click();
        Assert.assertEquals("Переход на другую страницу не осуществлен",
                thisPageTitle, driverManager.getDriver().findElement(By.xpath("//title")).getText());
    }
}
