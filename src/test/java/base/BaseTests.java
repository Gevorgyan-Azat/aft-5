package base;

import framework.managers.DriverManager;
import framework.managers.InitManager;
import framework.managers.PageManager;
import framework.managers.TestPropManager;
import framework.utils.PropConst;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class BaseTests {

    private DriverManager driverManager = DriverManager.getINSTANCE();
    private TestPropManager propManager = TestPropManager.getINSTANCE();
    protected PageManager pageManager = PageManager.getINSTANCE();

    @BeforeClass
    public static void beforeClass() {
        InitManager.initFramework();
    }

    @Before
    public void before(){
        driverManager.getDriver().get(propManager.getProperty(PropConst.BASE_URL));
    }

    @AfterClass
    public static void afterClass(){
        InitManager.quitFramework();
    }
}
