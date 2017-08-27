package com.haloerp.selenium;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public abstract class AbstractAction implements Action {

	// parameters
	protected int id;
	protected String action; // action text in excel
	protected String key;
	protected String[] args;
	protected String validateMessage;
	protected FailStrategy fail;

	// validate
	protected boolean validated = false;
	protected String validateMessages;

	// execute result
	protected List<String> executeMessage = new ArrayList<String>();
	protected TestStatus status = TestStatus.UNTEST;
	protected long startTime;
	protected long endTime;
	protected Map<File, String> screenshots = new HashMap<File, String>();

	public void setParameter(String action, int id, String key, String[] args) {
		this.action = action;
		this.id = id;
		this.key = key;
		this.args = args;
	}

	public int getId() {
		return id;
	}

	public String getAction() {
		return action;
	}

	public List<String> getMessage() {
		return executeMessage;
	}

	public FailStrategy getFailStrategy() {
		return FailStrategy.PAUSE;
	}

	public String toString() {
		StringBuffer ret = new StringBuffer();
		String simpleName = this.getClass().getSimpleName();
		if (this.action != null && simpleName.toLowerCase().equals(this.action.toLowerCase())) {
			ret.append(simpleName);
		} else {
			ret.append(simpleName).append("[").append(this.action).append("]");
		}

		ret.append("(");
		if (key != null && key.length() > 0) {
			ret.append("\"").append(key).append("\"");
		}
		if (args != null && args.length > 0) {
			if (key != null && key.length() > 0) {
				ret.append(", ");
			}
			ret.append("\"").append(String.join("\", \"", args)).append("\"");
		}
		ret.append(")");
		return ret.toString();
	}

	public boolean validate() {
		return true;
	}

	public String getValidateMessage() {
		return null;
	}

	public long getStartTime() {
		return startTime;
	}

	public TestStatus getStatus() {
		return status;
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}

	public void saveScreenShot(TestcaseContext context, String message) {
		if (context != null && context.getDriver() instanceof TakesScreenshot) {
			TakesScreenshot tss = TakesScreenshot.class.cast(context.getDriver());
			this.screenshots.put(tss.getScreenshotAs(OutputType.FILE), message);
		}
	}

	public long getEndTime() {
		return endTime;
	}

}
