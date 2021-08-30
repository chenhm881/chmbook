package com.chm.book.selenium.base;

import org.openqa.selenium.WebDriver;


import java.util.*;

public class PageUtil {
    public static final boolean HALT_ON_FAILURE = true;
    private static Integer unspecifiedPageWaitTime = 60;
    private static Integer waitTime = 90;

    public static String getCurrentURL() {
        WebDriver driver = (WebDriver) Context.get("webDriver");
        return driver.getCurrentUrl();
    }

    public static Map<String, String> getQueryMap() {
        String paramsString = getCurrentURL().split("/?")[1];
        String[] params = getCurrentURL().split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String[] nameValue = param.split("=");
            map.put(nameValue[0], nameValue[1]);
        }
        return map;
    }

    public static boolean navigateTo(String url) {
        return navigateTo(url, HALT_ON_FAILURE);
    }

    public static boolean navigateTo(String url, boolean haltOnFailure) {
        WebDriver driver = (WebDriver) Context.get("webDriver");
        String logMessage = String.format("Navigating to [%s]... ", url.toString());
        try {
            driver.navigate().to(url);

        } catch (Exception e) {
            if (haltOnFailure) {
                throw new RuntimeException(logMessage + "FAILURE (HALTING)", e);
            }

        }
        return true;
    }

    public static boolean navigateBack() {
        return navigateBack(HALT_ON_FAILURE);
    }

    public static boolean navigateBack(boolean haltOnFailure) {
        WebDriver driver = (WebDriver) Context.get("webDriver");

        try {
            driver.navigate().back();

        } catch (Exception e) {
            if (haltOnFailure) {
                throw new RuntimeException( "FAILURE (HALTING)", e);
            }

        }
        return true;
    }

    public static boolean navigateForward() {
        return navigateForward(HALT_ON_FAILURE);
    }


    public static boolean navigateForward(boolean haltOnFailure) {
        WebDriver driver = (WebDriver) Context.get("webDriver");

        String logMessage = "Navigating forward a page... ";
        try {
            driver.navigate().forward();

        } catch (Exception e) {
            if (haltOnFailure) {
                throw new RuntimeException(logMessage + "FAILURE (HALTING)", e);
            }

        }
        return true;
    }

    public static boolean refreshPage(boolean haltOnFailure) {
        WebDriver driver = (WebDriver) Context.get("webDriver");
        String logMessage = "Refreshing page... ";
        try {
            driver.navigate().refresh();

        } catch (Exception e) {
            if (haltOnFailure) {
                throw new RuntimeException(logMessage + "FAILURE (HALTING)", e);
            }

        }
        return true;
    }
}
