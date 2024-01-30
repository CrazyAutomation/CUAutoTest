package com.cu.util;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.cu.util.DriverUtils.*;
import static org.junit.Assert.assertEquals;


public abstract class UIUtils {
    static RemoteWebDriver driver = DriverFactory.getDriver();


    private UIUtils()
    {
        System.out.println("This is default constructor");
    }
    public static void selectDropdownByVisibleText(By element, String selection) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(find(element)));
        waitUntilPageFullyLoaded();
        Select select = new Select(find(element));
        List<WebElement> options = select.getOptions();
        for (WebElement option : options) {
            String optiontext = option.getText();
            if (optiontext.equals(selection)) {
                select.selectByVisibleText(optiontext);
                break;
            }
        }
    }

    public static void typeSelect(By by, String value) {
        waitUntilPageFullyLoaded();
        find(by).click();
        find(by).clear();
        for (int i = 0; i < value.length(); i++) {
            char myChar = value.charAt(i);
            String typeString = String.valueOf(myChar);
            Actions actions = new Actions(driver);
            actions.moveToElement(find(by)).build().perform();
            waitUntilPageFullyLoaded();
            find(by).sendKeys(typeString);
            waitUntilPageFullyLoaded();
        }
    }


    public static WebElement getWebElement(String locType, String element) {
        WebElement webElement = null;
        waitUntilPageFullyLoaded();
        switch (locType) {
            case "id":
                webElement = find(By.id(element));
                break;
            case "name":
                webElement = find(By.name(element));
                break;
            case "class":
                webElement = find(By.className(element));
                break;
            case "css":
                webElement = find(By.cssSelector(element));
                break;
            case "link":
                webElement = find(By.linkText(element));
                break;
            case "partialLink":
                webElement = find(By.partialLinkText(element));
                break;
            case "tag":
                webElement = find(By.tagName(element));
                break;
            case "xpath":
                webElement = find(By.xpath(element));
                break;
            default:
                System.out.println("There is no such " + locType + " locator type and WebElement " + element + "!");
        }
        return webElement;
    }


    public static void validateTitle(String desc, String title) {
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%s : %s%n", desc, driver.getTitle());
        Assert.assertEquals(desc, title, driver.getTitle());
        System.out.println("---------------------------------------------------------------------");
    }

    public static void validateText(String desc, String str, WebElement ele) {
        System.out.println(desc + " : " + ele.getText());
        assertEquals(desc, str, ele.getText());
    }

    public static void validateButtonsExistence(String desc, WebElement ele) {
        if (ele.isDisplayed()) {
            System.out.println("'" + desc + "' : " + ele.isDisplayed());
        }
        Assert.assertTrue(desc, ele.isDisplayed());
    }

    public static void clickButton(WebElement ele) {
        ele.click();
    }

    public static boolean isElementExists(String desc, WebElement ele) {
        // Set a small element timeout because it is a try catch.
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        boolean status;
        try {
            if (ele.isDisplayed()) {
                System.out.printf("%s existing status is : %s%n", desc, ele.isDisplayed());
                Assert.assertTrue(desc + " existing status is : ", ele.isDisplayed());
                DriverFactory.resetDriverTimeouts(driver);
                status= true;
            } else {
                System.out.println(desc + " existing status is : " + ele.isDisplayed());
                status = false;
            }
        } catch (NoSuchElementException e) {
            DriverFactory.resetDriverTimeouts(driver);
            status= false;
        }
        return status;
    }

    public static void clickSubMenu(String subBy )
    {
        waitUntilPageFullyLoaded();
        WebElement menu = find(By.xpath(".//*[@id='academic']"));
        WebElement sub_menu =  find(By.xpath(subBy));
//        WebElement sub_menu =  driver.findElement(subBy);
        Actions action = new Actions(driver);
        action.moveToElement(menu).moveToElement(sub_menu).click().build().perform();
    }

}
