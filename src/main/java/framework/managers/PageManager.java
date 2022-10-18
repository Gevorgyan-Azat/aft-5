package framework.managers;

import framework.pages.CreditPage;
import framework.pages.StartPage;

public class PageManager {

    private static PageManager INSTANCE = null;

    private StartPage startPage;
    private CreditPage creditPage;

    public static PageManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    public CreditPage getCreditPage() {
        if (creditPage == null) {
            creditPage = new CreditPage();
        }
        return creditPage;
    }
}