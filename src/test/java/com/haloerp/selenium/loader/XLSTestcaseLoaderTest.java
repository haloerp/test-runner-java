package com.haloerp.selenium.loader;

import org.junit.Before;
import org.junit.Test;

import com.haloerp.selenium.TestcaseConfig;
import com.haloerp.selenium.TestcaseContext;
import com.haloerp.selenium.TestcaseRunner;
import com.haloerp.selenium.TestcaseThread;

public class XLSTestcaseLoaderTest {

	@Before
	public void setup() {
		System.setProperty("webdriver.ie.driver",
				"D:\\dev\\test\\selenium\\IEDriverServer_Win32_3.4.0\\IEDriverServer.exe");
	}

	
	@Test
	public void test() {
		TestcaseRunner.main(new String[] { "D:\\halo\\workspace\\java\\selenium\\selenium-demo\\bin\\tpl.xls" });
	}

}
