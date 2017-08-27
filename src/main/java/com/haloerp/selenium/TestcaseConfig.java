package com.haloerp.selenium;

/**
 * TestcaseConfig
 * 
 * @author Halo_Chen
 *
 */
public class TestcaseConfig {

	// IE, CHROME, FIREFIX
	private String browser;

	// xls, class, json, script
	private String fileType;

	// file name
	private String filepath;

	// take screen shot when pass one action
	private boolean passScreenShot;

	// when action failed, next step strategy: continue/ignore, pause, terminate
	// testcase/all.
	// user can choice retry, continue, terminate when pause.
	private FailStrategy failStrategy = FailStrategy.PAUSE;

	// take screenshot when action failed.
	private boolean failScreenShot;

	// report file format: html, json, xls
	private String reportType;

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public boolean isPassScreenShot() {
		return passScreenShot;
	}

	public void setPassScreenShot(boolean passScreenShot) {
		this.passScreenShot = passScreenShot;
	}

	public FailStrategy getFailStrategy() {
		return failStrategy;
	}

	public void setFailStrategy(FailStrategy failStrategy) {
		this.failStrategy = failStrategy;
	}

	public boolean isFailScreenShot() {
		return failScreenShot;
	}

	public void setFailScreenShot(boolean failScreenShot) {
		this.failScreenShot = failScreenShot;
	}

}
