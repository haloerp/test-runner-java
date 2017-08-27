package com.haloerp.selenium.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.haloerp.selenium.Action;
import com.haloerp.selenium.ActionFactory;
import com.haloerp.selenium.Scenario;
import com.haloerp.selenium.Testcase;
import com.haloerp.selenium.TestcaseConfig;

import jxl.Sheet;
import jxl.Workbook;

public class XLSTestcaseLoader extends AbstractTestcaseLoader {

	public void init() {

		TestcaseConfig config = context.getConfig();
		if (config.getFilepath() == null) {
			return;
		}

		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(config.getFilepath()));

			// config
			Sheet configSheet = workbook.getSheet("Config");
			config.setFileType("xls");
			config.setBrowser(configSheet.getCell(1, 0).getContents());
			config.setReportType(configSheet.getCell(1, 1).getContents());

			// test cases
			Sheet tcSheet = workbook.getSheet("Testcases");
			// Cell[] header = tcSheet.getRow(0);

			for (int row = 1; row < tcSheet.getRows(); row++) {
				Testcase testcase = new Testcase();
				testcase.setId(tcSheet.getCell(1, row).getContents());
				testcase.setDescription(tcSheet.getCell(3, row).getContents());
				String scenarioSheetName = tcSheet.getCell(2, row).getContents();
				Sheet scenarioSheet = workbook.getSheet(scenarioSheetName);

				// actions
				Scenario scenario = new Scenario(scenarioSheetName);
				for (int actionRow = 1; actionRow < scenarioSheet.getRows(); actionRow++) {
					String command = scenarioSheet.getCell(1, actionRow).getContents();
					if (command != null && command.length() > 0) {
						Action action = ActionFactory.get(actionRow, scenarioSheet.getCell(1, actionRow).getContents(),
								scenarioSheet.getCell(2, actionRow).getContents(),
								scenarioSheet.getCell(3, actionRow).getContents());
						if (action == null) {
							throw new RuntimeException("unknown action at sheet[" + scenarioSheetName + "!col2,row "
									+ actionRow + "] " + scenarioSheet.getCell(1, actionRow).getContents());
							// Error no action: scenarioSheet.getCell(1,
							// actionRow).getContents()
						}
						scenario.add(action);
					}
				}
				testcase.setScenario(scenario);
				this.addTestcase(testcase);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
	}

	public List<Testcase> getTestcases() {
		if (testcases == null) {
			testcases = new ArrayList<Testcase>();
		}
		return testcases;
	}

}