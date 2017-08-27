package com.haloerp.selenium.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.haloerp.selenium.ReportGenerator;
import com.haloerp.selenium.TestcaseContext;

public class DefaultReport implements ReportGenerator {

	public void setContext(TestcaseContext context) {
		DateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println(formater.format(new Date()));
	}

	public void save() {

	}

}
