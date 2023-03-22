package com.stc.tv.automation.pages;

import com.stc.tv.automation.constants.GeneralConstants;
import com.stc.tv.automation.dataModels.PlanDM;
import com.stc.tv.automation.utils.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;


public class HomePage extends MainPage {
    //invoke parent's constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Initialize web elements

    @FindBy(xpath = "//h2[contains(text(),'Choose Your Plan')]")
    WebElement planChoiceTitle;

    @FindBy(id = "name-lite")
    WebElement litePlanFld;

    @FindBy(id = "name-classic")
    WebElement classicPlanFld;

    @FindBy(id = "name-premium")
    WebElement premiumPlanFld;

    @FindBy(xpath = "//*[@id='currency-lite']/b")
    WebElement litePlanPriceFld;

    @FindBy(xpath = "//*[@id='currency-classic']/b")
    WebElement classicPlanPriceFld;

    @FindBy(xpath = "//*[@id='currency-premium']/b")
    WebElement premiumPlanPriceFld;

    @FindBy(xpath = "//*[@id='currency-lite']/i")
    WebElement litePlanCurrencyFld;

    @FindBy(xpath = "//*[@id='currency-classic']/i")
    WebElement classicPlanCurrencyFld;

    @FindBy(xpath = "//*[@id='currency-premium']/i")
    WebElement premiumPlanCurrencyFld;

    @FindBy(xpath = "//button[@aria-label='Support']")
    WebElement supportIconBtn;

    @FindBy(xpath = "//iframe[@id='launcher']")
    WebElement launcherFrameId;

    @FindBy(id = "country")
    WebElement countryBtn;

    @FindBy(xpath = "//span[contains(@id,'contry-lable')]")
    List<WebElement> countries;


    public void waitForHomePageToLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOf(planChoiceTitle));
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
        }
    }

    public void selectCountry(PlanDM planDM) {
        try {
            Log.info("Selecting " + planDM.getCountry() + " as Country ... ");
            wait.until(ExpectedConditions.elementToBeClickable(countryBtn)).click();
            wait.until(ExpectedConditions.visibilityOfAllElements(countries));
            for (WebElement country : countries) {
                if (country.getText().trim().equalsIgnoreCase(planDM.getCountry())) {
                    country.click();
                    break;
                }
            }

        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
        }
    }

    public void waitForSupportIconToLoad() {
        try {
            Log.info("Waiting for Support Icon to be Visible ...");
            driver.switchTo().frame(launcherFrameId);
            wait.until(ExpectedConditions.visibilityOf(supportIconBtn));
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
        }

    }

    public void navigateToDefaultContent() {
        try {
            Log.info("Switching out of the child frame to the default content ... ");
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.visibilityOf(planChoiceTitle));
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
        }
    }

    public String getPlanAName() {
        String name = "";
        try {
            name = litePlanFld.getText();
            Log.info("Plan A is " + name + " ... ");
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return name;
    }

    public String getPlanBName() {
        String name = "";
        try {
            name = classicPlanFld.getText();
            Log.info("Plan B is " + name + " ... ");
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return name;
    }

    public String getPlanCName() {
        String name = "";
        try {
            name = premiumPlanFld.getText();
            Log.info("Plan C is " + name + " ... ");
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return name;
    }

    public String getPlanAPrice() {
        String price = "";
        try {
            price = litePlanPriceFld.getText();
            Log.info("Plan A Price is " + price + " ... ");
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return price;
    }

    public String getPlanBPrice() {
        String price = "";
        try {
            price = classicPlanPriceFld.getText();
            Log.info("Plan B Price is " + price + " ... ");
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return price;
    }

    public String getPlanCPrice() {
        String price = "";
        try {
            price = premiumPlanPriceFld.getText();
            Log.info("Plan C Price is " + price + " ... ");
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return price;
    }

    public String getPlanCurrency() {
        String currency = "";
        try {
            currency = litePlanCurrencyFld.getText();
            Log.info("Plan Currency is " + currency.split("/")[0] + " ... ");
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return currency;
    }
}