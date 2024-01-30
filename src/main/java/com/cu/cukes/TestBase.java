package com.cu.cukes;

import com.google.common.base.Preconditions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cu.util.CapabilityManager;
import com.cu.util.DriverFactory;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Properties;


public abstract class TestBase {

    protected static Properties properties;
    protected static Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected static final ThreadLocal<Properties> propertiesWrapper = new ThreadLocal<>();

    protected static RemoteWebDriver driver;


    //Config/Property names
    protected static String ENV_PROPERTY_NAME = "environment";
    protected static String ENV_PROPERTY_OS = "Windows";
    protected static String ENV_PROPERTY_MOJAVE = "Mojave";
    protected static String BASE_URL_PROPERTY_NAME = "base.url";
    public static String GRID_URL_PROPERTY_NAME = "grid.url";
    protected static String LOCAL_ID_PROPERTY_NAME = "localidentifier";
    protected static String PROJECT_NAME_PROPERTY_NAME = "projectname";
    protected static String BUILD_NUM_PROPERTY_NAME = "build";

    protected static String BROWSER_PROPERTY_NAME = "browser";

    protected static String BSTACK_URL_PROPERTY_NAME = "browserstackurl";
    protected static String BSTACK_LOCAL_BOOL_PROPERTY_NAME = "browserstack.local";

    protected static String cuAppUrl;


    public enum Environments {
        LOCAL("local"),
        BROWSERSTACK_LOCAL("bstack"),
        GRID("grid");

        private final String description;

        Environments(String browser) {
            this.description = browser;
        }

        @Override
        public String toString() {
            return this.description;
        }

        public static Environments getMatch(String text) {
            for (Environments env : Environments.values()) {
                if (env.toString().equalsIgnoreCase(text)) {
                    return env;
                }
            }
            return null;
        }
    }

    protected static void setProperties(Properties props) {
        propertiesWrapper.set(props);
    }

    protected static void setupTestContext() {
        Environments env = Environments.getMatch(System.getProperty(ENV_PROPERTY_NAME));
        properties = new Properties();

        Preconditions.checkArgument(env != null);
        try {
            properties.load(new FileReader("src/main/resources/config/" + env + ".config.properties"));

            // Any command line arguments should be defined here to override the environment config
            if (System.getProperty(BASE_URL_PROPERTY_NAME) != null) {
                properties.setProperty(BASE_URL_PROPERTY_NAME, System.getProperty(BASE_URL_PROPERTY_NAME));
            }
            if (System.getProperty(BSTACK_URL_PROPERTY_NAME) != null) {
                properties.setProperty(BSTACK_URL_PROPERTY_NAME, System.getProperty(BSTACK_URL_PROPERTY_NAME));
            }
            if (System.getProperty(GRID_URL_PROPERTY_NAME) != null) {
                properties.setProperty(GRID_URL_PROPERTY_NAME, System.getProperty(GRID_URL_PROPERTY_NAME));
            }
            if (System.getProperty(LOCAL_ID_PROPERTY_NAME) != null) {
                properties.setProperty(LOCAL_ID_PROPERTY_NAME, System.getProperty(LOCAL_ID_PROPERTY_NAME));
            }
            if (System.getProperty(PROJECT_NAME_PROPERTY_NAME) != null) {
                properties.setProperty(PROJECT_NAME_PROPERTY_NAME, System.getProperty(PROJECT_NAME_PROPERTY_NAME));
            }
            if (System.getProperty(BROWSER_PROPERTY_NAME) != null) {
                properties.setProperty(BROWSER_PROPERTY_NAME, System.getProperty(BROWSER_PROPERTY_NAME));
            }
            if (System.getProperty(BUILD_NUM_PROPERTY_NAME) != null) {
                properties.setProperty(BUILD_NUM_PROPERTY_NAME, System.getProperty(BUILD_NUM_PROPERTY_NAME));
            } else {
                properties.setProperty(BUILD_NUM_PROPERTY_NAME,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")));
            }

            properties.setProperty(ENV_PROPERTY_NAME, env.toString());
            TestBase.setProperties(properties);

            cuAppUrl = properties.getProperty(BASE_URL_PROPERTY_NAME);

            String os = null;
            String osVersion = null;
            String browser = null;
            String browserVersion = null;

            switch(properties.getProperty(BROWSER_PROPERTY_NAME)){
                case "winIE":{
                    os = ENV_PROPERTY_OS;
                    osVersion = "11";
                    browser = "Internet Explorer";
                    browserVersion = "11";
                    break;
                }
                case "winEdge":{
                    os = ENV_PROPERTY_OS;
                    osVersion = "11";
                    browser = "Edge";
                    browserVersion = "120";
                    break;
                }
                case "winChrome":{
                    os = ENV_PROPERTY_OS;
                    osVersion = "11";
                    browser = "Chrome";
                    browserVersion = "120";
                    break;
                }
                case "winFF":{
                    os = ENV_PROPERTY_OS;
                    osVersion = "10";
                    browser = "Firefox";
                    browserVersion = "63";
                    break;
                }
                case "osxSafari":{
                    os = "OS X";
                    osVersion = ENV_PROPERTY_MOJAVE;
                    browser = "Safari";
                    browserVersion = "12";
                    break;
                }
                case "osxChrome":{
                    os = "OS X";
                    osVersion = ENV_PROPERTY_MOJAVE;
                    browser = "Chrome";
                    browserVersion = "70";
                    break;
                }
                case "osxFF":{
                    os = "OS X";
                    osVersion = ENV_PROPERTY_MOJAVE;
                    browser = "Firefox";
                    browserVersion = "63";
                    break;
                }
                case "ipadSafari":{
                    os = "iPad Pro";
                    osVersion = "11.2";
                    browser = "Safari";
                    break;
                }
                case "samsungChrome":{
                    os = "Samsung Galaxy Note 8";
                    osVersion = "7.1";
                    browser = "Google Chrome";
                    break;
                }
                default:
                    // comment for Sonar Qube
            }


            /**
             * Change browser name to chrome, firefox or edge
             */
            switch (env) {
                case LOCAL : {
                    DriverFactory.setLocalDriver(Objects.requireNonNull(DriverFactory.LocalBrowser.getMatch("chrome")));
                    driver = DriverFactory.getDriver();
                    break;
                }
                case GRID : {
                    Preconditions.checkArgument(properties.getProperty(GRID_URL_PROPERTY_NAME) != null,
                            "In order to use the selenium grid maven profile, pass in the grid url as " +
                                    "'-Dgrid.url=http://127.0.0.1:4444' or set it in the grid config file.");
                    DriverFactory.setRemoteDriver(
                            properties.getProperty(GRID_URL_PROPERTY_NAME),
                            CapabilityManager.generateDefaultChromeCapabilities(
                                    properties.getProperty(PROJECT_NAME_PROPERTY_NAME),
                                    properties.getProperty(BUILD_NUM_PROPERTY_NAME)));
                    driver = DriverFactory.getDriver();
                    break;
                }
                case BROWSERSTACK_LOCAL : {
                    // Null values from mobile devices handled in the 'generateBrowserStackCapabilities' method.
                    DriverFactory.setRemoteDriver(
                            properties.getProperty(BSTACK_URL_PROPERTY_NAME),
                            CapabilityManager.generateBrowserStackCapabilities(
                                    os,
                                    osVersion,
                                    browser,
                                    browserVersion,
                                    properties.getProperty(PROJECT_NAME_PROPERTY_NAME),
                                    properties.getProperty(BUILD_NUM_PROPERTY_NAME),
                                    properties.getProperty(LOCAL_ID_PROPERTY_NAME),
                                    Boolean.parseBoolean(properties.getProperty(BSTACK_LOCAL_BOOL_PROPERTY_NAME))));
                    driver = DriverFactory.getDriver();
                    break;
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void navigateToCU()
    {
        driver.get(properties.getProperty(BASE_URL_PROPERTY_NAME));
    }


}
