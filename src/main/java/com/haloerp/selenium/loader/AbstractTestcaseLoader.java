package com.haloerp.selenium.loader;

import java.util.ArrayList;
import java.util.List;

import com.haloerp.selenium.Testcase;
import com.haloerp.selenium.TestcaseContext;
import com.haloerp.selenium.TestcaseLoader;

public abstract class AbstractTestcaseLoader implements TestcaseLoader {

	protected List<Testcase> testcases;
	protected TestcaseContext context;
	protected List<String> messages = new ArrayList<String>();

	public void setContext(TestcaseContext context) {
		this.context = context;
	}

	public void init() {
	}

	public void setTestcase(Testcase testcase) {
		this.testcases = new ArrayList<Testcase>();
		testcases.add(testcase);
	}

	public void addTestcase(Testcase testcase) {
		getTestcases().add(testcase);
	}

	public void setTestcases(List<Testcase> testcases) {
		this.testcases = testcases;
	}

	public void addMessage(String message) {
		messages.add(message);
	}

	public List<String> getMessages() {
		return messages;
	}

}
