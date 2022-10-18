package framework.pages;

import framework.pages.blocks.NavigationBlock;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class StartPage extends BasePage {

    @FindBy(xpath = "//button[@class='kitt-cookie-warning__close']")
    private WebElement closeCookieBtn;

    @FindBy(xpath = "//div[@class='kitt-cookie-warning']")
    private WebElement cookieIsClose;

    private final NavigationBlock navigation = new NavigationBlock();

    public StartPage closeCookie() {
            if (!(closeCookieBtn.isDisplayed())) {
                try {
                waitUtilElementToBeVisible(closeCookieBtn);
                } catch (Exception ignore) {}
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
