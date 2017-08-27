package com.haloerp.selenium.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.haloerp.selenium.AbstractAction;
import com.haloerp.selenium.TestStatus;
import com.haloerp.selenium.TestcaseContext;

public class ClickByXpath extends AbstractAction {

	public boolean validate() {
		return (args != null && args.length >= 1 && args[0] != null);
	}

	public TestStatus execute(TestcaseContext context) {
		WebElement element = context.getPresentElement(By.xpath(key));
		if (element == null) {
			this.saveScreenShot(context, this.getClass().getSimpleName() + "(" + key + "): Can not get element.");
			return TestStatus.FAIL;
		} else {
			element.click();
			return TestStatus.PASS;
		}
	}

}
