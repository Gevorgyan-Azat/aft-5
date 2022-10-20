package framework.pages;

import framework.pages.blocks.NavigationBlock;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class StartPage extends BasePage {

    @FindBy(xpath = "//button[@class='kitt-cookie-warning__close']")
    private WebElement closeCookieBtn;

    @FindBy(xpath = "//div[@class='kitt-cookie-warning']")
    private WebElement cookieIsClose;

    private final NavigationBlock navigation = new NavigationBlock();

    @Step("Закрытие окна Cookie")
    public StartPage closeCookie() {
            if (!(closeCookieBtn.isDisplayed())) {
                try {
                waitUtilElementToBeVisible(closeCookieBtn);
                } catch (NoSuchElementException | TimeoutException ignore) { }
            } else if (closeCookieBtn.isDisplayed()) {
                waitUtilElementToBeClickable(closeCookieBtn).click();
                waitUtilElementToBeVisible(cookieIsClose);
            }
            Assert.assertTrue("Не удалось закрыть окно Cookie", cookieIsClose.isDisplayed());
            return this;
    }

    public NavigationBlock getNavigation() {
        return navigation;
    }

}
