package com.haloerp.selenium.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.haloerp.selenium.AbstractAction;
import com.haloerp.selenium.TestStatus;
import com.haloerp.selenium.TestcaseContext;

public class SetValueById extends AbstractAction {

	public TestStatus execute(TestcaseContext context) {
		WebElement e = context.getPresentElement(By.id(key));
		if (e == null) {
			return TestStatus.FAIL;
		}
		e.sendKeys(args[0]);

		return TestStatus.PASS;
	}

}
