package com.stc.tv.automation.strategy.testData;

import java.util.ArrayList;


public interface TestDataStrategy {

	ArrayList<ArrayList<Object>> loadTestData(String connectionProperties);

}
