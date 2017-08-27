package com.haloerp.selenium.template;

import org.junit.Test;

public class XLSTemplateTest {

	@Test
	public void test() {
		XLSTemplate tpl = new XLSTemplate();
		tpl.generate("d:\\ts_test.xls");
	}

}
