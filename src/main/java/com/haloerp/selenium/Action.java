package com.haloerp.selenium;

import java.util.List;

public interface Action {

	int getId();

	String getAction();

	void setParameter(String action, int id, String key, String[] args);

	boolean validate();

	List<String> getMessage();

	TestStatus execute(TestcaseContext context);

	long getStartTime();

	long getEndTime();

	TestStatus getStatus();

	void setStatus(TestStatus status);

	FailStrategy getFailStrategy();

}
