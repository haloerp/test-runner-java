package com.haloerp.selenium.action.assertion;

import com.haloerp.selenium.AbstractAction;
import com.haloerp.selenium.TestStatus;
import com.haloerp.selenium.TestcaseContext;

public class WaitForNotText extends AbstractAction {

	public boolean validate() {
		this.validateMessage = "Not implemented.";
		return false;
	}

	public TestStatus execute(TestcaseContext context) {
		return TestStatus.FAIL;
	}

}
