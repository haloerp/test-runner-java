package com.haloerp.selenium.action;

import com.haloerp.selenium.AbstractAction;
import com.haloerp.selenium.TestStatus;
import com.haloerp.selenium.TestcaseContext;

public class Pause extends AbstractAction {

	public boolean validate() {
		this.validateMessages = "Can not get action [" + key + "].";
		return false;
	}

	public TestStatus execute(TestcaseContext context) {
		return TestStatus.FAIL;
	}

}
