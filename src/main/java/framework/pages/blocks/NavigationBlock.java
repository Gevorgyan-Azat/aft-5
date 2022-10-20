package framework.pages.blocks;

import framework.pages.BasePage;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.not;

public class NavigationBlock extends BasePage {

    @FindBy(xpath = "//a[@role='button' and @aria-expanded]")
    private List<WebElement> baseMenuList;

    @FindBy(xpath = "//a[contains(@class, 'kitt-top-menu__link_second')]")
    private List<WebElement> subMenuList;

    @FindBy(xpath = "//title")
    private WebElement title;

    @Step("Выбор пункта '{baseMenuValue}'  главного меню")
    public NavigationBlock selectBaseMenu(String baseMenuValue) {
        for (WebElement baseMenu: baseMenuList) {
            if(baseMenu.getAttribute("textContent").contains(baseMenuValue)){
                waitUtilElementToBeVisible(baseMenu);
                waitUtilElementToBeClickable(baseMenu).click();
                wait.until(ExpectedConditions.attributeToBe(baseMenu, "aria-expanded", "true"));
                Assert.assertTrue("Меню '"+ baseMenuValue +"' не открыто.",
                        Boolean.parseBoolean(baseMenu.getAttribute("aria-expanded")));
                return this;
            }
        }
        Assert.fail("Меню со значением '" + baseMenuValue + "' не найдено.");
        return this;
    }

    @Step("Выбор пункта '{subMenuValue}'  подменю")
    public NavigationBlock selectSubMenu(String subMenuValue) {
        for (WebElement subMenu: subMenuList) {
            if(subMenu.getAttribute("text").contains(subMenuValue)){
                String thisPageURL = driverManager.getDriver().getCurrentUrl();
                waitUtilElementToBeVisible(subMenu);
                waitUtilElementToBeClickable(subMenu).click();
                waitForChangeURL(thisPageURL);
                Assert.assertNotEquals("Переход на другую страницу не осуществлен",
                        driverManager.getDriver().getCurrentUrl(), thisPageURL);
                return this;
            }
        }
        Assert.fail("Подменю со значением '" + subMenuValue + "' не найдено.");
        return this;
    }
}
