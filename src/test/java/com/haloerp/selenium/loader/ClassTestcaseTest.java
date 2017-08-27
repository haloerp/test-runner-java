package com.haloerp.selenium.loader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.haloerp.selenium.Action;
import com.haloerp.selenium.ActionFactory;
import com.haloerp.selenium.Scenario;
import com.haloerp.selenium.TestStatus;
import com.haloerp.selenium.Testcase;
import com.haloerp.selenium.TestcaseConfig;
import com.haloerp.selenium.TestcaseContext;
import com.haloerp.selenium.TestcaseLoader;
import com.haloerp.selenium.TestcaseThread;

public class ClassTestcaseTest {

	private TestcaseLoader loader;
	private TestcaseThread test;

	@Before
	public void setup() {
		System.setProperty("webdriver.ie.driver",
				"D:\\dev\\test\\selenium\\IEDriverServer_Win32_3.4.0\\IEDriverServer.exe");
		TestcaseConfig config = new TestcaseConfig();
		config.setBrowser("ie");
		config.setFileType("class");
		TestcaseContext context = new TestcaseContext(config);
		test = new TestcaseThread(context);
		loader = context.getTestcaseloader();
	}

	@Test
	public void test() {

		Testcase testcase = new Testcase();
		testcase.setId("TC_01");
		testcase.setDescription("New");

		Scenario scenario = new Scenario("test");
		scenario.add(ActionFactory.get(1, "openurl", null, "http://localhost"));

		scenario.add(ActionFactory.get(2, "SetValueByName", "textField", "textField"));
		scenario.add(ActionFactory.get(3, "SetValueByName", "textareaField", "textareaField"));

		scenario.add(ActionFactory.get(4, "ExecScript", null, "alert('Done')"));
		testcase.setScenario(scenario);
		loader.setTestcase(testcase);

		test.run();

		for (Testcase tc : loader.getTestcases()) {
			for (Action action : tc.getScenario().getActions()) {
				Assert.assertEquals(TestStatus.PASS, action.getStatus());
			}
		}
	}

}
