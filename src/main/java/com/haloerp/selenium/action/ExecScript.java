package com.haloerp.selenium.action;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.haloerp.selenium.AbstractAction;
import com.haloerp.selenium.TestStatus;
import com.haloerp.selenium.TestcaseContext;

public class ExecScript extends AbstractAction {

	public boolean validate() {
		return (args != null && args.length > 0 && args[0] != null);
	}

	public TestStatus execute(TestcaseContext context) {
		WebDriver driver = context.getDriver();
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor exec = JavascriptExecutor.class.cast(driver);
			exec.executeScript(args[0]);
			return TestStatus.PASS;
		}
		return TestStatus.PASS;
	}

}
