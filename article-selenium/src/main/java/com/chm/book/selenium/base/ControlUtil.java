package com.chm.book.selenium.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ControlUtil
{
    public static void selectCheckBox(WebDriver driver, By checkboxBtn, By valueToSelect, int second) {
        WebElement element = ElementUtil.waitElementCanBeClicked(driver, checkboxBtn, second);
        element.click();
        //check the checkbox
        WebElement checkbox = ElementUtil.waitElementCanBeClicked(driver, valueToSelect, second);
        if(!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public static void deselectCheckBox(WebDriver driver, By checkboxBtn, By valueToSelect, int second) {
        WebElement element = ElementUtil.waitElementCanBeClicked(driver, checkboxBtn, second);
        element.click();
        //uncheck the checkbox
        WebElement checkbox = ElementUtil.waitElementCanBeClicked(driver, valueToSelect, second);
        if(checkbox.isSelected())
        {
            checkbox.click();
        }
    }

    public static String selectDateRange(WebDriver driver, int range) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, range);
        Date lastDate = calendar.getTime();
        return sdf.format(lastDate) + " - " + sdf.format(date);
    }

    public static WebElement findElementByTagName(WebDriver driver, By parent, By tagName) {
        WebElement parentElement = ElementUtil.waitWebElement(driver, parent, 10);
        WebElement element = parentElement.findElement(tagName);
        return element;
    }

    public static List<WebElement> findAllElements(WebDriver driver, String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    public static double parseElementTextToDouble(WebDriver driver, By parent, By tagName, String oldChar, String newChar) {
        double doubleValue = 0.0;
        WebElement element = findElementByTagName(driver, parent, tagName);
        if(!element.getText().contains("no data"))
        {
            doubleValue = Double.parseDouble(element.getText().replace(oldChar, newChar).trim());
        }
        return doubleValue;
    }

    public static int parseElementTextToInt(WebDriver driver, By parent, By tagName) {
        int intValue = 0;
        WebElement element = findElementByTagName(driver, parent, tagName);
        if(!element.getText().contains("no data")) {
            intValue = Integer.parseInt(element.getText().trim());
        }
        return intValue;
    }

    public static int parseElementTextToInt(WebDriver driver, By parent, By tagName, String oldChar, String newChar) {
        int intValue = 0;
        WebElement element = findElementByTagName(driver, parent, tagName);
        if(!element.getText().contains("no data")) {
            intValue = Integer.parseInt(element.getText().replace(oldChar, newChar).trim());
        }
        return intValue;
    }

    public static void multipleSelectCheckBox(WebDriver driver, By checkboxBtn, String[]values, int second) {
        WebElement element = ElementUtil.waitElementCanBeClicked(driver, checkboxBtn, second);
        element.click();
        for(String optionValue: values) {
            WebElement checkbox = ElementUtil.waitElementCanBeClicked(driver, By.xpath("//input[@value='"+optionValue.trim()+"']"), second);
            if(!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    public static void setValueToInputElement(WebDriver driver, By by, String value, int second) {
        WebElement inputElement = ElementUtil.waitWebElement(driver, by, second);
        inputElement.clear();
        inputElement.sendKeys(value);
    }

    public static Date getDateFromTextField(WebDriver driver, By by, String pattern, int second) throws ParseException {
        WebElement dateTimeField = ElementUtil.waitWebElement(driver, by, second);
        String dateTimeString = dateTimeField.getAttribute("value");
        DateFormat timeFormat = new SimpleDateFormat(pattern);
        Date startTime = timeFormat.parse(dateTimeString);
        return startTime;
    }

}
