package com.stc.tv.automation.tests;

import com.stc.tv.automation.constants.GeneralConstants;
import com.stc.tv.automation.constants.excelIndices.ExcelIndices;
import com.stc.tv.automation.dataModels.PlanDM;
import com.stc.tv.automation.utils.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;

public class SubscriptionPlansTest extends BaseTest {

    ArrayList verifyTestData = new ArrayList();

    @Test(description = "Validate Different Plans Subscription", dataProvider = "verifyPlanDP")
    public void validatePlansDetails(PlanDM planDM){
        //Create extent test to be logged in report using test case title
        test = extent.createTest(planDM.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(planDM.getTestCaseTitle() + " For " + planDM.getCountry());

        homepage.selectCountry(planDM);
        homepage.waitForSupportIconToLoad();
        homepage.navigateToDefaultContent();

        String planAName = homepage.getPlanAName();
        Assert.assertEquals(planAName,planDM.getPlanAName(), GeneralConstants.MISMATCH_ERR_MSG);

        String planAPrice = homepage.getPlanAPrice();
        Assert.assertEquals(planAPrice,planDM.getPlanAPrice(), GeneralConstants.MISMATCH_ERR_MSG);


        String planBName = homepage.getPlanBName();
        Assert.assertEquals(planBName,planDM.getPlanBName(), GeneralConstants.MISMATCH_ERR_MSG);

        String planBPrice = homepage.getPlanBPrice();
        Assert.assertEquals(planBPrice,planDM.getPlanBPrice(), GeneralConstants.MISMATCH_ERR_MSG);


        String planCName = homepage.getPlanCName();
        Assert.assertEquals(planCName,planDM.getPlanCName(), GeneralConstants.MISMATCH_ERR_MSG);

        String planCPrice = homepage.getPlanCPrice();
        Assert.assertEquals(planCPrice,planDM.getPlanCPrice(), GeneralConstants.MISMATCH_ERR_MSG);

        String currency = homepage.getPlanCurrency();
        Assert.assertEquals(currency.split("/")[0],planDM.getCurrency(), GeneralConstants.MISMATCH_ERR_MSG);
    }

    @DataProvider(name = "verifyPlanDP")
    public Object[][] provideSubscriptionPlanTD() {
        Object[][] verifyPlanDP = new Object[verifyTestData.size()][1];
        for (int i = 0; i < verifyTestData.size(); i++)
            verifyPlanDP[i][0] = verifyTestData.get(i);

        return verifyPlanDP;
    }

    @BeforeClass
    public void provideSubscriptionPlanData() {
        ArrayList<ArrayList<Object>> resultArray = provideTestData("subscriptionPlan");
        for (int i = 0; i < resultArray.size(); i++) {
            PlanDM planDM = new PlanDM();
            //fill plan data model
            planDM.setTestCaseTitle(resultArray.get(i).get(ExcelIndices.TEST_CASE_TITLE_INDEX).toString());
            planDM.setCountry(resultArray.get(i).get(ExcelIndices.COUNTRY).toString());
            planDM.setPlanAName(resultArray.get(i).get(ExcelIndices.PLAN_A_NAME).toString());
            planDM.setPlanAPrice(resultArray.get(i).get(ExcelIndices.PLAN_A_PRICE).toString());
            planDM.setPlanBName(resultArray.get(i).get(ExcelIndices.PLAN_B_NAME).toString());
            planDM.setPlanBPrice(resultArray.get(i).get(ExcelIndices.PLAN_B_PRICE).toString());
            planDM.setPlanCName(resultArray.get(i).get(ExcelIndices.PLAN_C_NAME).toString());
            planDM.setPlanCPrice(resultArray.get(i).get(ExcelIndices.PLAN_C_PRICE).toString());
            planDM.setCurrency(resultArray.get(i).get(ExcelIndices.CURRENCY).toString());

            verifyTestData.add(planDM);

        }
    }
}