package tests;

import base.BaseTests;
import org.junit.Test;

import static framework.pages.enums.Field.*;
import static framework.pages.enums.CheckBox.*;

public class SberTest extends BaseTests {

    @Test
    public void sberTest() {
        pageManager.getStartPage()
                .closeCookie().getNavigation()
                .selectBaseMenu("Ипотека")
                .selectSubMenu("Ипотека на вторичное жильё");
//        pageManager.getCreditPage()
//                .selectField("Стоимость недвижимости", "5180000")
//                .selectField("Первоначальный взнос", "3058000")
//                .selectField("Срок кредита", "30");
        pageManager.getCreditPage()
                .selectField(HOME_PRICE, "5180000")
                .selectField(INITIAL_PAYMENT, "3058000")
                .selectField(CREDIT_PERIOD, "30")
                .selectCheckBox(INSURANCE_LIFE_AND_HEALTH, false);


    }
}
