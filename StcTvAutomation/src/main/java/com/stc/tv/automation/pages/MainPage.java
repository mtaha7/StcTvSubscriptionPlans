package com.stc.tv.automation.pages;

import com.stc.tv.automation.constants.GeneralConstants;
import com.stc.tv.automation.utils.PropertiesFilesHandler;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Properties;

public class MainPage {

    // Initialize web drivers
    public WebDriver driver;
    public JavascriptExecutor jsDriver;
    public WebDriverWait wait;
    PropertiesFilesHandler propHandler = new PropertiesFilesHandler();
    Properties generalConfigsProps = propHandler.loadPropertiesFile(GeneralConstants.GENERAL_CONFIG_FILE_NAME);

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 60);
    }
}