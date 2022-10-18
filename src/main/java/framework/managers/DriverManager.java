package framework.managers;

import org.apache.commons.exec.OS;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

import static framework.utils.PropConst.*;

public class DriverManager {

        private WebDriver driver;
        private final TestPropManager propManager = TestPropManager.getINSTANCE();

        private static DriverManager INSTANCE = null;

        private static final String mvnBrowserName = System.getProperty("browser");
        private String selectedBrowserName = propManager.getProperty(TYPE_BROWSER);

        public static DriverManager getINSTANCE() {
            if (INSTANCE == null) {
                INSTANCE = new DriverManager();
            }
            return INSTANCE;
        }

        public WebDriver getDriver() {
            if (driver == null) {
                initDriver();
            }
            return driver;
        }

        private void initDriver() {
            if (OS.isFamilyWindows()) {
                initDriverWindowsOsFamily();
            } else if (OS.isFamilyMac()) {
                initDriverMacOsFamily();
            } else if (OS.isFamilyUnix()) {
                initDriverUnixOsFamily();
            }
        }

    private void initDriverWindowsOsFamily() {
        initDriverAnyOsFamily(PATH_GECKO_DRIVER_WINDOWS, PATH_CHROME_DRIVER_WINDOWS);
    }

    private void initDriverMacOsFamily() {
        initDriverAnyOsFamily(PATH_GECKO_DRIVER_MAC, PATH_CHROME_DRIVER_MAC);
    }

    private void initDriverUnixOsFamily() {
        initDriverAnyOsFamily(PATH_GECKO_DRIVER_UNIX, PATH_CHROME_DRIVER_UNIX);
    }

    private void initDriverAnyOsFamily(String gecko, String chrome) {
            if(mvnBrowserName != null){
                selectedBrowserName = mvnBrowserName;
            }
        switch (selectedBrowserName) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", propManager.getProperty(chrome));
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", propManager.getProperty(gecko));
                driver = new FirefoxDriver();
                break;
            case "remote":
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                capabilities.setVersion("84.0");
                capabilities.setCapability("enableVNC", true);
                capabilities.setCapability("enableVideo", false);
                try {
                    driver = new RemoteWebDriver(
                            URI.create("http://130.193.49.85:4444/wd/hub").toURL(),
                            capabilities);
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                Assert.fail("Типа браузера '" + selectedBrowserName + "' не существует во фреймворке");
        }
    }

//    private void initDriver() {
//            switch (propManager.getProperty(TYPE_BROWSER)) {
//                case "chrome":
//                    System.setProperty("webdriver.chrome.driver", propManager.getProperty(PATH_CHROME_DRIVER_WINDOWS));
//                    driver = new ChromeDriver();
//                    break;
//                case "firefox":
//                    System.setProperty("webdriver.gecko.driver", propManager.getProperty(PATH_GECKO_DRIVER_WINDOWS));
//                    driver = new FirefoxDriver();
//                    break;
//                case "remote":
//                    DesiredCapabilities capabilities = new DesiredCapabilities();
//                    capabilities.setBrowserName("chrome");
//                    capabilities.setVersion("84.0");
//                    capabilities.setCapability("enableVNC", true);
//                    capabilities.setCapability("enableVideo", false);
//                    try {
//                        driver = new RemoteWebDriver(
//                                URI.create("http://130.193.49.85:4444/wd/hub").toURL(),
//                                capabilities);
//                    }catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                default: break;
//            }
//        }

        public void quitDriver() {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        }

    }
