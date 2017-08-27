package com.haloerp.selenium.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.haloerp.selenium.AbstractAction;
import com.haloerp.selenium.TestStatus;
import com.haloerp.selenium.TestcaseContext;

public class SetValueByName extends AbstractAction {

	public boolean validate() {
		if (!validated) {
			validated = (key != null && args != null && args.length > 0 && args[0] != null);
		}
		return validated;
	}

	public TestStatus execute(TestcaseContext context) {
		if (validate()) {
			WebElement e = context.getPresentElement(By.name(key));
			if (e == null) {
				this.executeMessage.add(this.getClass().getSimpleName() + "can not find element: " + key);
				return TestStatus.FAIL;
			}
			e.sendKeys(args[0]);
			return TestStatus.PASS;
		}
		return TestStatus.FAIL;
	}

}
