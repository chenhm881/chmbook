package com.chm.book.selenium.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {
    public static void sleep(long millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static WebElement waitWebElement(WebDriver driver, final By by, int second) {
        WebDriverWait wait = new WebDriverWait(driver, second, 1000);
        WebElement element = wait.until(d->d.findElement(by));
        return element;
    }

    public static boolean waitElementToBeDisplayed(WebDriver driver, final By by, int second) {
        boolean wait = new WebDriverWait(driver, second).until(d->  d.findElement(by)).isDisplayed();
        return wait;
    }


    public static boolean waitElementToBeNoDisplayed(WebDriver driver, final By by, int second) {
        boolean wait = new WebDriverWait(driver, second).until(d-> !d.findElement(by).isDisplayed());
        return wait;
    }


    public static WebElement waitElementCanBeClicked(WebDriver driver, final By by, int second) {
        WebElement element = new WebDriverWait(driver, second).until(ExpectedConditions.elementToBeClickable(by));
        return element;
    }


    public static void waitElemenToBeClicked(WebDriver driver, final By by, int second) {
        WebElement element = new WebDriverWait(driver, second).until(ExpectedConditions.elementToBeClickable(by));
        element.click();
    }

}
