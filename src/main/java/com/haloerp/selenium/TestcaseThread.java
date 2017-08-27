package com.haloerp.selenium;

import java.util.List;

public class TestcaseThread extends Thread {

	private TestcaseContext context;

	public TestcaseThread(TestcaseContext context) {
		this.context = context;
	}

	public TestcaseThread(TestcaseConfig config) {
		this.context = new TestcaseContext(config);
	}

	public TestcaseThread(String filepath) {
		TestcaseConfig config = new TestcaseConfig();
		config.setFilepath(filepath);
		this.context = new TestcaseContext(config);
	}

	public void run() {
		loadTest();
		if (validateTest()) {
			runtest();
		}
		ReportGenerator report = context.getReportGenerator();
		if (report != null) {
			report.save();
		}
		context.dispose();
	}

	private boolean loadTest() {
		TestcaseLoader loader = context.getTestcaseloader();
		if (loader.getMessages().size() > 0) {
			System.out.println(loader.getMessages());
			return false;
		}
		return true;
	}

	private boolean validateTest() {
		return true;
	}

	private void runtest() {
		List<Testcase> cases = context.getTestcaseloader().getTestcases();
		for (Testcase testcase : cases) {
			try {
				context.run(testcase);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
