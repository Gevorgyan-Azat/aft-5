package framework.pages.enums;

public enum CheckBox {

    GET_SALARY_IN_SBERBANK("Получаю зарплату на счёт"),
    USE_MATERNITY_CAPITAL("Использовать материнский капитал"),
    MY_PERCENT("Своя ставка"),
    BUYING_IN_DOMCLICK("покупке недвижимости на Домклик"),
    INSURANCE_LIFE_AND_HEALTH("Страхование жизни и здоровья"),
    EL_REGISTRATION_DEAL("Электронная регистрация сделки");

    private String value;

    CheckBox(String value) {
        this.value = value;
    }

    public String getValue() {
        return "//div[./div[./span[contains(text(), '" + value + "')]]]//input[@type='checkbox']";
    }
}
