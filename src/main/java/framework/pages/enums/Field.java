package framework.pages.enums;

public enum Field {

//    CREDIT_GOAL(""),
//    REGION(""),
    HOME_PRICE("Стоимость недвижимости"),
    INITIAL_PAYMENT("Первоначальный взнос"),
    CREDIT_PERIOD("Срок кредита");

    private String value;

    Field(String value) {
        this.value = value;
    }

    public String getValue() {
        return "//div[./label[contains(text(), '"+ value +"')]]//input[@class='dc-input__input-6-1-2' and @inputmode]";
    }
}
