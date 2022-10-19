package tests;

import base.BaseTests;
import org.junit.Test;

import static framework.pages.enums.CheckBox.*;
import static framework.pages.enums.Field.*;


public class SberTest extends BaseTests {

    @Test
    public void sberTest() {
        pageManager.getStartPage()
                .closeCookie().getNavigation()
                .selectBaseMenu("Ипотека")
                .selectSubMenu("Ипотека на вторичное жильё");
        pageManager.getCreditPage()
                .selectAndFillField(HOME_PRICE, "5180000")
                .selectAndFillField(INITIAL_PAYMENT, "3058000")
                .selectAndFillField(CREDIT_PERIOD, "30")
                .changeStateCheckBox(INSURANCE_LIFE_AND_HEALTH, false)
                .changeStateCheckBox(BUYING_IN_DOMCLICK, false)
                .changeStateCheckBox(BUYING_IN_DOMCLICK, true)
                .changeStateCheckBox(BUYING_IN_DOMCLICK, false)
                .changeStateCheckBox(GET_SALARY_IN_SBERBANK, false);


    }
}
