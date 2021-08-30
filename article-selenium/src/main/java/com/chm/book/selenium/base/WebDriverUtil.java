package com.chm.book.selenium.base;

import com.chm.book.selenium.enums.BrowserType;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.logging.Level;

public class WebDriverUtil
{

    public static void startWebDriver(String browserType) throws Exception
    {
        startWebDriver(browserType, false, null);
    }

    public static void startWebDriver(String browserType, boolean isRemoteGrid, String gridUrl) throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        configureGenericCapabilities(capabilities);

        WebDriver webDriver = null;
        switch (getBrowserEnum(browserType))
        {
            case chrome:
                webDriver = generateChromeDriver(capabilities, isRemoteGrid, gridUrl);
                break;
            case phantomjs:
                webDriver = generatePhantomjsDriver(capabilities, isRemoteGrid, gridUrl);
                break;
            default:
                webDriver = generatePhantomjsDriver(capabilities, isRemoteGrid, gridUrl);
                break;
        }
        webDriver.manage().window().maximize();
        Context.put("webDriver", webDriver);
    }

    protected static BrowserType getBrowserEnum(String browserType) {
        try {
            return BrowserType.valueOf(browserType);
        }
        catch (Exception ex)
        {

        }
        return null;

    }

    protected static void configureGenericCapabilities(DesiredCapabilities capabilities)
    {
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        capabilities.setCapability(CapabilityType.SUPPORTS_WEB_STORAGE, false);

        configureLoggingCapability(capabilities);
    }

    private static void configureLoggingCapability(DesiredCapabilities capabilities) {
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        logPrefs.enable(LogType.CLIENT, Level.ALL);
        logPrefs.enable(LogType.DRIVER, Level.ALL);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    }

    private static WebDriver generateChromeDriver(DesiredCapabilities capabilities, boolean useGrid, String gridUrl) throws Exception {

        configureChromeDriver();
        WebDriver driver = new ChromeDriver();
        return driver;
    }

    private static WebDriver generatePhantomjsDriver(DesiredCapabilities capabilities, boolean useGrid, String gridUrl) throws Exception {

        configurePhantomjsDriver();

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, System.getProperty("phantomjs.binary.path"));
        caps.setCapability("takesScreenshot", true);
        WebDriver driver = new PhantomJSDriver(caps);
        return driver;
    }

    protected static void configureChromeDriver() throws Exception {

        String chromeDriverVersion = "92.0";

        String chromeDriverName = "";

        chromeDriverName = "chromedriver_" + chromeDriverVersion + "_win.exe";

        String chromeDriverPath = "drivers/chromedriver/" + chromeDriverName;
        File file1 = new ClassPathResource(chromeDriverPath).getFile();
        File file = ResourceUtils.getFile("classpath:" + chromeDriverPath);
        String temporaryChromeDriverLocation = file.getAbsolutePath();
        System.setProperty("webdriver.chrome.driver", temporaryChromeDriverLocation);
    }

    protected static void configurePhantomjsDriver() throws Exception {

        String phantomjsDriverVersion = "2.1.1";


        String phantomjsDriverName = "phantomjs_" + phantomjsDriverVersion + "_win.exe";

        String chromeDriverPath = "drivers/phantomjsdriver/" + phantomjsDriverName;
        File file1 = new ClassPathResource(chromeDriverPath).getFile();
        File file = ResourceUtils.getFile("classpath:" + chromeDriverPath);
        String temporaryChromeDriverLocation = file.getAbsolutePath();
        System.setProperty("phantomjs.binary.path", temporaryChromeDriverLocation);
    }


    public static void resetWebDriver(WebDriver driver)
    {
        driver.manage().deleteAllCookies();
    }

    public static void setBrowserSize(int width, int height)
    {
        WebDriver webDriver = (WebDriver)Context.get("webDriver");
        webDriver.manage().window().setSize(new Dimension(width, height));
    }

    public static void stopWebDriver()
    {

        WebDriver webDriver = (WebDriver)Context.get("webDriver");
        if(webDriver != null)
        {
            String webDriverStopStatus = "Stopping WebDriver... ";
            try
            {
                webDriver.quit();
            }
            catch (Exception e)
            {

            }
        }
    }
}
