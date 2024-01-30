package com.cu.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class DriverFactory {
	private static ThreadLocal<RemoteWebDriver> threadSafeDriver = new ThreadLocal<>();
/**
	private static String LOCAL_DRIVER_BINARY_PATH = "src/main/resources/binaries/";
	private static final String EDGE_DRIVER_V = "edgedriver-v";
	private static final String CHROME_DRIVER_V = "chromedriver-v";
	private static final String GECKO_DRIVER_V = "geckodriver-v";
	private static final String CHROME_DRIVER_VERSION = "111.0";
	private static final String EDGE_DRIVER_VERSION = "111.0";
	private static final String GECKO_DRIVER_VERSION = "0.32";
 */
	public enum LocalBrowser{
		FIREFOX("firefox"),
		CHROME("chrome"),
		EDGE("edge");

		private final String description;
		LocalBrowser(String browser){this.description = browser;}
		public String toString(){return this.description;}
		public static LocalBrowser getMatch(String text){
			for(LocalBrowser browser : LocalBrowser.values()){
				if(browser.toString().equals(text)){
					return browser;
				}
			}
			return null;
		}
	}

	public synchronized static void setLocalDriver (LocalBrowser browser) {
		switch(browser) {
			case CHROME: {
				threadSafeDriver = ThreadLocal.withInitial(DriverFactory::getChromeDriver);
				break;
			}
			case EDGE: {
				threadSafeDriver = ThreadLocal.withInitial(DriverFactory::getEdgeDriver);
				break;
			}
			case FIREFOX: {
				threadSafeDriver = ThreadLocal.withInitial(DriverFactory::getFirefoxDriver);
				break;
			}
		}
	}

	public static synchronized void setRemoteDriver (String url, Capabilities caps) {
		try {
			threadSafeDriver.set(new RemoteWebDriver(new URL(url),caps));
			initDriverConfig(threadSafeDriver.get());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static synchronized RemoteWebDriver getDriver ()
	{
		return threadSafeDriver.get();
	}

	public static synchronized void closeDriver() {
		threadSafeDriver.get().close();
		threadSafeDriver.get().quit();
	}

	private static FirefoxDriver getFirefoxDriver() {
		System.clearProperty("webdriver.firefox.driver");
		/**
		if(SystemUtils.IS_OS_MAC){
			LOCAL_DRIVER_BINARY_PATH += GECKO_DRIVER_V + GECKO_DRIVER_VERSION + "-macos/geckodriver";
		} else if(SystemUtils.IS_OS_LINUX){
			LOCAL_DRIVER_BINARY_PATH += GECKO_DRIVER_V + GECKO_DRIVER_VERSION + "-linux64/geckodriver";
		} else if(SystemUtils.IS_OS_WINDOWS){
			LOCAL_DRIVER_BINARY_PATH += GECKO_DRIVER_V + GECKO_DRIVER_VERSION + "-win64/geckodriver.exe";
		}
		File f = new File(LOCAL_DRIVER_BINARY_PATH);
		System.setProperty("webdriver.gecko.driver", f.getAbsolutePath());
		*/

		FirefoxProfile fxProfile = new FirefoxProfile();
		fxProfile.setPreference("browser.download.folderList", 2);
		fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		/**
		fxProfile.setPreference("browser.download.dir",
		this.downloadDir.getAbsolutePath());
		 */
		fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/zip,application/x-compress,application/octet-stream");
		FirefoxOptions firefoxOptions = new FirefoxOptions().setProfile(fxProfile);
		WebDriverManager.firefoxdriver().setup();
		FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
		initDriverConfig(driver);
		return driver;
	}

	private static ChromeDriver getChromeDriver() {
		System.clearProperty("webdriver.chrome.driver");
//		WebDriverManager.chromedriver().setup();
		WebDriverManager.chromedriver().driverVersion("120.0.6099.71").setup();
		/**
		if(SystemUtils.IS_OS_MAC){
			LOCAL_DRIVER_BINARY_PATH += CHROME_DRIVER_V + CHROME_DRIVER_VERSION + "-mac64/chromedriver";
		} else if(SystemUtils.IS_OS_LINUX){
			LOCAL_DRIVER_BINARY_PATH += CHROME_DRIVER_V + CHROME_DRIVER_VERSION + "-linux64/chromedriver";
		} else if(SystemUtils.IS_OS_WINDOWS){
			LOCAL_DRIVER_BINARY_PATH += CHROME_DRIVER_V + CHROME_DRIVER_VERSION + "-win32/chromedriver.exe";
		}
		File f = new File(LOCAL_DRIVER_BINARY_PATH);
		System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
		*/
		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--disable-notifications");
//		options.addArguments("--disable-gpu");
//		options.addArguments("--disable-extensions");
//		options.addArguments("--no-sandbox");
		options.addArguments("--start-maximized");
//		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--remote-allow-origins=*");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(capabilities);
		ChromeDriver driver = new ChromeDriver(options);
		initDriverConfig(driver);
		return driver;
	}

	private static EdgeDriver getEdgeDriver() {
		System.clearProperty("webdriver.edge.driver");
		/**
		if(SystemUtils.IS_OS_MAC){
			LOCAL_DRIVER_BINARY_PATH += EDGE_DRIVER_V + EDGE_DRIVER_VERSION + "-mac64/msedgedriver";
		} else if(SystemUtils.IS_OS_LINUX){
			LOCAL_DRIVER_BINARY_PATH += EDGE_DRIVER_V + EDGE_DRIVER_VERSION + "-linux64/msedgedriver";
		} else if(SystemUtils.IS_OS_WINDOWS){
			LOCAL_DRIVER_BINARY_PATH += EDGE_DRIVER_V + EDGE_DRIVER_VERSION + "-win64/msedgedriver.exe";
		}
		File f = new File(LOCAL_DRIVER_BINARY_PATH);
		System.setProperty("webdriver.edge.driver", f.getAbsolutePath());
		 */

		EdgeOptions options = new EdgeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-extensions");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--remote-allow-origins=*");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(EdgeOptions.CAPABILITY, options);
		options.merge(capabilities);
		WebDriverManager.edgedriver().setup();
		EdgeDriver driver = new EdgeDriver(options);
		initDriverConfig(driver);
		return driver;
	}

	public static void resetDriverTimeouts(RemoteWebDriver driver){
        // The driver will wait x seconds for every page to load
        driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        /** The driver will wait x seconds for every element to be visibly
         Increased the timeout to 10 seconds as IE needs more than 5 */
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

	/** Method for setting the default driver settings. */
	private static void initDriverConfig(RemoteWebDriver driver){
		resetDriverTimeouts(driver);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
}
