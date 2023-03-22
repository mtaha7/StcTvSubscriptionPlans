package com.stc.tv.automation.strategy.testData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import com.stc.tv.automation.constants.GeneralConstants;
import com.stc.tv.automation.utils.ExcelHandler;
import com.stc.tv.automation.utils.PropertiesFilesHandler;
import com.stc.tv.automation.constants.excelIndices.ExcelIndices;
import org.apache.poi.ss.usermodel.Row;


public class ExcelTestData implements TestDataStrategy {

	public ArrayList<ArrayList<Object>> loadTestData(String filePathAndSheetNo)
	{
		ArrayList<ArrayList<Object>> results = new ArrayList<ArrayList<Object>>();
		try {

			String filePath = filePathAndSheetNo.split(";")[0];
			String sheetNo = filePathAndSheetNo.split(";")[1];

			Iterator<Row> rows = ExcelHandler.loadExcelSheetRows(filePath, Integer.parseInt(sheetNo));

			//get get header columns number
			short headerColumnsNum = rows.next().getLastCellNum();

			// get smoke test scope flag config from properties file
			PropertiesFilesHandler propLoader = new PropertiesFilesHandler();
			Properties prop = propLoader.loadPropertiesFile(GeneralConstants.GENERAL_CONFIG_FILE_NAME);
			String isSmockTestScopeEnabled = prop.getProperty(GeneralConstants.SMOKE_TEST_FLAG);

			while (rows.hasNext()) {
				Row row = rows.next();
				ArrayList<Object> cellsObjects = new ArrayList<Object>();
				ArrayList<String> rowCells = ExcelHandler.getExcelRowCells(row, headerColumnsNum);

				for (int i = 0; i < rowCells.size(); i++) {
					Object cell = new Object();
					cell = rowCells.get(i);
					cellsObjects.add(cell);
				}
					results.add(cellsObjects);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return results;

	}





}
