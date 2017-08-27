package com.haloerp.selenium.action;

import com.haloerp.selenium.AbstractAction;
import com.haloerp.selenium.TestStatus;
import com.haloerp.selenium.TestcaseContext;

public class OpenUrl extends AbstractAction {

	public TestStatus execute(TestcaseContext context) {
		context.getDriver().get(args[0]);
		return TestStatus.PASS;
	}

}
