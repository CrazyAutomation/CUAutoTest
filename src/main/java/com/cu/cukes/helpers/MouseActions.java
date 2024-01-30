package com.cu.cukes.helpers;//package com.cu.cukes.helpers;
//
//
//import com.cu.cukes.TestBase;
//import com.cu.util.DriverUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.interactions.PointerInput;
//
//import java.time.Duration;
//
///**
// * Class for holding more basic mouse actions.
// */
//@Slf4j
//public class MouseActions extends TestBase{
//
//    /**
//     * Constants
//     */
//    private static final int LEFT_MOUSE_BUTTON = PointerInput.MouseButton.LEFT.asArg();
//    private static final int RIGHT_MOUSE_BUTTON = PointerInput.MouseButton.RIGHT.asArg();
//    private static final Duration DEFAULT_ACTION_DURATION = Duration.ofMillis(100L);
//    private static final int NO_OFFSET = 0;
//
//
//    /**
//     * Left clicks the mouse at its current location, or on the provided element.
//     *
//     * @param by An element to start the mouse cursor from.
//     */
//    public void leftClick(By by) {
//        WebElement webElement = DriverUtils.find(by);
//        try {
//            Actions action = new Actions(driver);
//            if (webElement != null) {
//                action.click(webElement).perform();
//            } else {
//                action.click().perform();
//            }
//        } catch (Exception e) {
//            log.debug("Unable to left click : " + e.toString());
//        }
//    }
//
//    /**
//     * Double clicks the mouse at its current location, or on the provided element.
//     *
//     * @param by An element to start the mouse cursor from.
//     */
//    public void doubleClick(By by) {
//        WebElement webElement = DriverUtils.find(by);
//        try {
//            Actions action = new Actions(driver);
//            if (webElement != null) {
//                action.doubleClick(webElement).perform();
//            } else {
//                action.doubleClick().perform();
//            }
//        } catch (Exception e) {
//            log.debug("Unable to double click : " + e.toString());
//        }
//    }
//
//
//    /**
//     * Right clicks the mouse at its current location, or on the provided element.
//     *
//     * @param by An element to start the mouse cursor from.
//     */
//    public void rightClick(By by) {
//        WebElement webElement = DriverUtils.find(by);
//        try {
//            Actions action = new Actions(driver);
//            if (webElement != null) {
//                action.contextClick(webElement).perform();
//            } else {
//                action.contextClick().perform();
//            }
//        } catch (Exception e) {
//            log.debug("Unable to right click : " + e.toString());
//        }
//    }
//
//
//    /**
//     * Moves the mouse from its current position to the provided element offset by x and y.
//     *
//     * @param by An element to start the mouse cursor from.
//     * @param x  The x offset. Navigate the means move left.
//     * @param y  The y offset. Navigate the means move up.
//     * @return Do we think it was successful?
//     */
//    public boolean moveMouseToElement(By by, int x, int y) {
//        WebElement webElement = DriverUtils.find(by);
//        try {
//            Actions actions = new Actions(driver);
//            actions.moveToElement(webElement, x, y).perform();
//            return true;
//        } catch (Exception e) {
//            log.debug("Unable to move mouse by offset : " + e.toString());
//            return false;
//        }
//    }
//
//    /**
//     * Moves the mouse to the desired element, and returns whether we think we were successful.
//     *
//     * @param by An element to start the mouse cursor from.
//     * @return Do we think it was successful?
//     */
//    public boolean hoverMouseOverToElement(By by) {
//        WebElement webElement = DriverUtils.find(by);
//        boolean result;
//        try {
//            Actions action = new Actions(driver);
//            action.moveToElement(webElement).perform();
//            result = true;
//        } catch (Exception e) {
//            log.debug("Unable to move mouse by offset : " + e.toString());
//            result = false;
//        }
//        return result;
//    }
//
//
//    /**
//     * Moves the mouse from its current position to the provided element offset by x and y.
//     *
//     * @param by An element to start the mouse cursor from.
//     */
//    public void releaseMouse(By by) {
//        WebElement webElement = DriverUtils.find(by);
//        try {
//            Actions action = new Actions(driver);
//            if (webElement != null) {
//                action.release(webElement).perform();
//            } else {
//                action.release().perform();
//            }
//        } catch (Exception e) {
//            log.debug("Unable to release the mouse : " + e.toString());
//        }
//    }
//
//    /**
//     * Left clicks the mouse and holds at its current location, or on the provided element.
//     *
//     * @param by An element to start the mouse cursor from.
//     */
//    public void leftClickAndHold(By by) {
//        WebElement webElement = DriverUtils.find(by);
//        try {
//            Actions action = new Actions(driver);
//            if (webElement != null) {
//                action.clickAndHold(webElement).perform();
//            } else {
//                action.clickAndHold().perform();
//            }
//        } catch (Exception e) {
//            log.debug("Unable to left click and hold : " + e.toString());
//        }
//    }
//
//
//    /**
//     * Holds down the left mouse button (centered on the element) and drags to the offset before releasing.
//     *
//     * @param by An element to start the mouse cursor from.
//     * @param x  The x offset. Navigate the means move left.
//     * @param y  The y offset. Navigate the means move up.
//     */
//    public void leftClickDragAndRelease(By by, int x, int y) {
//        leftClickAndHold(by);
//        moveMouseToElement(by, x, y);
//        releaseMouse(by);
//    }
//
//    /**
//     *  Holds down the left mouse button (centered on the element) and drags to the ending element before releasing.
//     *
//     * @param by An element to start the drag operation from.
//     * @param by1 An element to end the drag operation on.
//     */
//    public void leftClickDragAndReleaseOnElement(By by, By by1) {
//        WebElement startingElement = DriverUtils.find(by);
//        WebElement endingElement = DriverUtils.find(by1);
//        PointerInput pointerInput = new PointerInput(PointerInput.Kind.MOUSE, "default mouse");
//        Actions actions = new Actions(driver);
//
//        actions.tick(pointerInput.createPointerMove(DEFAULT_ACTION_DURATION, PointerInput.Origin.fromElement(startingElement), NO_OFFSET, NO_OFFSET));
//        actions.tick(pointerInput.createPointerDown(LEFT_MOUSE_BUTTON));
//        actions.tick(pointerInput.createPointerMove(DEFAULT_ACTION_DURATION, PointerInput.Origin.fromElement(endingElement), NO_OFFSET, NO_OFFSET));
//        actions.tick(pointerInput.createPointerUp(LEFT_MOUSE_BUTTON));
//        actions.perform();
//    }
//
//    /**
//     * Holds down the right mouse button (centered on the element) and drags to the offset before releasing.
//     *
//     * @param by An element to start the mouse cursor from.
//     * @param x  The x offset. Navigate the means move left.
//     * @param y  The y offset. Navigate the means move up.
//     */
//    public void rightClickDragAndRelease(By by, int x, int y) {
//        WebElement webElement = DriverUtils.find(by);
//        PointerInput pointerInput = new PointerInput(PointerInput.Kind.MOUSE, "default mouse");
//        Actions actions = new Actions(driver);
//        actions.tick(pointerInput.createPointerDown(RIGHT_MOUSE_BUTTON));
//        actions.tick(pointerInput.createPointerMove(DEFAULT_ACTION_DURATION, PointerInput.Origin.fromElement(webElement), x, y));
//        actions.tick(pointerInput.createPointerUp(RIGHT_MOUSE_BUTTON));
//        actions.perform();
//    }
//
//
//}
