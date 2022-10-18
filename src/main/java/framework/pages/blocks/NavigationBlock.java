package framework.pages.blocks;

import framework.pages.BasePage;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NavigationBlock extends BasePage {

    @FindBy(xpath = "//a[@role='button' and @aria-expanded]")
    private List<WebElement> baseMenuList;

    @FindBy(xpath = "//a[contains(@class, 'kitt-top-menu__link_second')]")
    private List<WebElement> subMenuList;

    public NavigationBlock selectBaseMenu(String baseMenuValue) {
        for (WebElement baseMenu: baseMenuList) {
            if(baseMenu.getAttribute("textContent").contains(baseMenuValue)){
                waitUtilElementToBeVisible(baseMenu);
                waitUtilElementToBeClickable(baseMenu).click();
                Assert.assertTrue("Меню '"+ baseMenuValue +"' не открыто.",
                        Boolean.parseBoolean(baseMenu.getAttribute("aria-expanded")));
                return this;
            }
        }
        Assert.fail("Меню со значением '" + baseMenuValue + "' не найдено.");
        return this;
    }

    public NavigationBlock selectSubMenu(String subMenuValue) {
        for (WebElement subMenu: subMenuList) {
            if(subMenu.getAttribute("text").contains(subMenuValue)){
                waitUtilElementToBeVisible(subMenu);
                clickAndCheckTitle(subMenu);
                return this;
            }
        }
        Assert.fail("Подменю со значением '" + subMenuValue + "' не найдено.");
        return this;
    }
}
