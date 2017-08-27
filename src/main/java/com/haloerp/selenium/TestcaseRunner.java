package com.haloerp.selenium;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.haloerp.selenium.template.XLSTemplate;

public class TestcaseRunner {

	public static void main(String args[]) {

		for (String str : args) {
			if (str.toLowerCase().equals("-t")) {
				DateFormat format = new SimpleDateFormat("yyyyMMdd_hhmmss_sss");

				TestcaseTemplate tpl = new XLSTemplate();
				String filename = "TS_" + format.format(new Date()) + ".xls";
				tpl.generate(filename);
				System.out.println("Create template file " + filename);
			} else {
				System.out.println("Start " + str);
				TestcaseThread runner = new TestcaseThread(str);
				runner.run(); // run each file at same main thread.
				System.out.println();
			}
		}

	}

}
