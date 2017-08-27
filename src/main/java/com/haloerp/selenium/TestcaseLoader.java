package com.haloerp.selenium;

import java.util.List;

public interface TestcaseLoader {

	void setContext(TestcaseContext context);

	void init();

	List<Testcase> getTestcases();

	void setTestcase(Testcase testcase);

	void addTestcase(Testcase testcase);

	void setTestcases(List<Testcase> testcases);

	List<String> getMessages();
	
	void addMessage(String message);

}
