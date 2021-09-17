package com.chm.book.selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage
{

    @FindBy(id = "session_email_or_mobile_number")
    public WebElement username;

    @FindBy(id = "session_password")
    public WebElement password;


    @FindBy(id = "sign-in-form-submit-btn")
    public WebElement signIn;

    public LoginPage() throws Exception
    {
    }

    public MainPage LoginToMain(
            String user,
            String pass) throws Exception
    {

        sendUserName(username, user);
        password.sendKeys(pass);
        signIn.click();
        return new MainPage();
    }

    private void sendUserName(WebElement username, String user)
    {
        username.sendKeys(" ");
        username.clear();
        username.sendKeys(user);
    }
}
