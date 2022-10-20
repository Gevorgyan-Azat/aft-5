package tests;

import base.BaseTests;
import framework.pages.CreditPage;
import framework.pages.StartPage;
import org.junit.Test;

import static framework.pages.CreditPage.CheckBox.*;
import static framework.pages.CreditPage.Field.*;
import static framework.pages.CreditPage.Result.*;


public class SberTest extends BaseTests {

    @Test
    public void sberTest() {
        pageManager.getPage(StartPage.class)
                .closeCookie().getNavigation()
                .selectBaseMenu("Ипотека")
                .selectSubMenu("Ипотека на вторичное жильё");
        pageManager.getPage(CreditPage.class)
                .selectAndFillField(HOME_PRICE, "5180000")
                .selectAndFillField(INITIAL_PAYMENT, "3058000")
                .selectAndFillField(CREDIT_PERIOD, "30")
                .changeStateCheckBox(INSURANCE_LIFE_AND_HEALTH, false)
                .checkResultValue(CREDIT_SUM, "2122000")
                .checkResultValue(MONTHLY_PAYMENT, "20852")
                .checkResultValue(REQUIRED_INCOME, "35448")
                .checkResultValue(PERCENT_RATE, "11%");


    }
}
