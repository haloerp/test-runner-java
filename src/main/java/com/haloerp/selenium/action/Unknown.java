package com.haloerp.selenium.action;

import com.haloerp.selenium.AbstractAction;
import com.haloerp.selenium.TestStatus;
import com.haloerp.selenium.TestcaseContext;

public class Unknown extends AbstractAction {

	public TestStatus execute(TestcaseContext context) {
		return TestStatus.IGNORE;
	}

	public void setParameter(int id, String key, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
