package com.cu.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import java.io.File;

/**
 * Test Utility Class - used to contain all RemoteWebDriver related utility methods.
 */
public abstract class DriverUtils {
    static RemoteWebDriver driver = DriverFactory.getDriver();

    private DriverUtils()
    {

    }

    public static WebElement findElementWrapper(By element) {
        RemoteWebDriver driver = DriverFactory.getDriver();
        int retries = 2;

        for (int i = 0; i <= retries; i++) {
            try {
                return find(element);
            } catch (StaleElementReferenceException e) {
                driver.navigate().refresh();
                return find(element);
            }
        }
        return new RemoteWebElement();             //Should never actually be called.
    }

    public static boolean elementExists(By by) {
        // Set a small element timeout because it is a try catch.
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        try {
            driver.findElement(by);
            DriverFactory.resetDriverTimeouts(driver);
            return true;
        } catch (NoSuchElementException e) {
            DriverFactory.resetDriverTimeouts(driver);
            return false;
        }
    }

    public static void clearFieldAndSendKeys(By identifier, String value) {
        findElementWrapper(identifier).clear();
        findElementWrapper(identifier).sendKeys(value);
    }

    public static void clickFieldAndSendKeys(By identifier, String value) {
        findElementWrapper(identifier).click();
        findElementWrapper(identifier).sendKeys(value);
    }

    public static List<WebElement> getElements(By by) {
        return driver.findElements(by);
    }

    public static void search(By catchSpeciesList) {
        try {
            List<WebElement> speciesElement = getElements(catchSpeciesList);

            for (int i = 0; i < speciesElement.size(); i++) {
                int spe_List = speciesElement.size();
                System.out.print(spe_List);
                if (!speciesElement.get(0).isSelected()) {
                    speciesElement.get(0).click();
                    break;
                }
            }
        } catch (NotFoundException e) {

        }
    }

    /**
     * Generic utility method which clicks an element based on its text.
     * @param xpathElement - Used to specify an element's type more uniquely
     * @param text         - The search text of the element
     */
    public static void clickByText(String xpathElement, String text) {
        ArrayList<WebElement> elements = (ArrayList<WebElement>) findElementsByText(xpathElement, text);
        scrollToElement(elements.get(0));
        elements.get(0).click();
    }

    public static List<WebElement> findElementsByText(String xpathElement, String text) {
        return driver.findElements(By.xpath("//" + xpathElement +
                "[text()[contains(normalize-space(.),'" + text + "')]]"));
    }

    public static ArrayList<WebElement> getWebElementsByText() {
        return (ArrayList<WebElement>) findElementsByText("button", "Edit");
    }


    /**
     * Will scroll to the element provided, meeting the prerequisite of visibility before interaction.
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Overload method for clickByText which clicks any(the first) matching element
     */
    public static void clickByText(String text) {
        clickByText("*", text);
    }

    /**
     * Generic utility method which finds any element with the specified search text, returning the element found.
     *
     * @param searchString - The text used to search for within the current page (ignoring irregular formatting).
     * @return WebElement - The element found on the current page, or null if the element is not found.
     */
    public static boolean isStringVisible(String xpathElement, String searchString) {
        try {
            driver.findElement(By.xpath(xpathElement + "[text()[contains(normalize-space(.),'" + searchString + "')]]"));
            return true;
        } catch (NoSuchElementException e) {
            System.out.println(getErrorString(driver, searchString));
            return false;
        }
    }

    /**
     * Overload method for isStringVisible which matches any element regardless of identifier
     */
    public static boolean isStringVisible(String searchString) {
        return isStringVisible("//*", searchString);
    }

    /**
     * Overload method for isStringVisible which matches any element regardless of identifier
     */
    public static String isStringVisible1(String searchString) {
        isStringVisible("//*", searchString);
        return searchString;
    }

    /**
     * Generic utility method which determines whether an alert matching the alert text is visible.
     *
     * @param alert - text used to determine if there is an expected alert visible.
     * @return true if an alert with the specified text is visible, false if not.
     */
    public static boolean isAlertVisible(String alert) {
        Alert alertWindow = new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.alertIsPresent());

        driver.switchTo().alert();
        if (normaliseString(alertWindow.getText()).equals(alert)) {
            alertWindow.accept();
            return true;
        } else {
            alertWindow.accept();
            return false;
        }
    }

    public static boolean pageTitle(String title) {
        return driver.getTitle().contains(title);
    }

    /**
     * Internal utility method which formats all input strings correctly by removing erroneous formatting
     */
    private static String normaliseString(String input) {
        return input.trim().replace("\n", " ").replace("  ", " ");
    }

    /**
     * Generic utility method which clears and fills in all fields declared with the associated values.
     * @param fields A HashMap of Key (fieldname) Value (fieldtext) pairs.
     */
    public static void fillFields(HashMap<WebElement, String> fields) {
        for (Entry<WebElement, String> entry : fields.entrySet()) {
            entry.getKey().clear();
            entry.getKey().sendKeys(entry.getValue());
        }
    }

    public static boolean waitForElementToBeVisible(By by, int waitTime) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(waitTime)).until((ExpectedCondition<Boolean>) driver ->
                    driver.findElement(by).getText().length() != 0);
            return true;
        } catch (TimeoutException e) {

            return false;
        }

    }

    public static boolean isElementClickable(int waitTime, By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            wait.until(ExpectedConditions.elementToBeClickable(by)).isDisplayed();

            return true;
        } catch (TimeoutException e) {
            System.out.println("Cannot locate the element within " + e.getMessage());
            return false;
        }
    }

    public static boolean waitForElementToDisplay(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
            return true;
        } catch (TimeoutException e) {
            System.out.println("Cannot locate the element within " + e.getMessage());
            return false;
        }
    }

    /**
     * DriverUtils method which creates a custom error used within the DriverUtils class.
     * @param driver a WebDriver
     * @param searchCriteria searching for a string on the page.
     * @return Custom error including extra information on the issue.
     */
    private static String getErrorString(RemoteWebDriver driver, String searchCriteria) {
        return "\nNoSuchElement Exception: Unable to locate element with text of: " +
                searchCriteria +
                "\nCurrent Page Title: \"" +
                driver.getTitle() +
                "\"" +
                "\nCurrent URL: " +
                driver.getCurrentUrl();
    }


    public static WebElement find(By locator) {
        return driver.findElement(locator);
    }

    public static void type(By locator, String text) {
        find(locator).sendKeys(text);

    }

    public static void selectByVisibleText(By locator, String value) {
        Select select = new Select(find(locator));
        select.selectByVisibleText(value);

    }

    public static void click(By locator) {
        find(locator).click();
    }

    public static void moveToElementAndClick(By locator) {
        Actions actions = new Actions(driver);
        actions.doubleClick(find(locator)).perform();
    }

    public static void takeScreenshot(String filePrefix) {
        RemoteWebDriver driver = DriverFactory.getDriver();
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenshotFile, new File("./target/reports/" + filePrefix + "." +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("'T'yyyyMMddHHmmssS"))
                    + "." + driver.getSessionId() + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     */
    public static void waitUntilPageFullyLoaded() {
        new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(120)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }


    public static void currentUrl(String urlValue, int waitTime) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            wait.until(ExpectedConditions.urlContains(urlValue));
            System.out.println("Current url not display " + urlValue);
        } catch (TimeoutException e) {
          e.getMessage();
        }

    }

    public static boolean waitForAllListToDisplay(By by, int waitTime) {
        boolean status;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            status = true;
        } catch (TimeoutException e) {
            System.out.print("Element not present in the list with stipulated " + waitTime);
            status = false;
        }
        return status;
    }

    public static void typeByCharacter(By by, String value) {
        for (int i = 0; i < value.length(); i++) {
            char myChar = value.charAt(i);
            String typeString = String.valueOf(myChar);
            clickFieldAndSendKeys(by, typeString);
        }

    }


}
