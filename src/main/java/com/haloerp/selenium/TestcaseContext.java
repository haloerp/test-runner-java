package com.haloerp.selenium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.haloerp.selenium.loader.ClassTestcaseLoader;
import com.haloerp.selenium.loader.XLSTestcaseLoader;
import com.haloerp.selenium.report.DefaultReport;
import com.haloerp.selenium.report.HtmlReport;

/**
 * 
 * @author Halo_Chen
 *
 */
public class TestcaseContext {

	private static Map<String, Class<? extends WebDriver>> driverFactory = new HashMap<String, Class<? extends WebDriver>>();
	private static Map<String, Class<? extends TestcaseLoader>> loaderFactory = new HashMap<String, Class<? extends TestcaseLoader>>();
	private static Map<String, Class<? extends ReportGenerator>> reportFactory = new HashMap<String, Class<? extends ReportGenerator>>();

	static {
		driverFactory.put("ie", InternetExplorerDriver.class);
		driverFactory.put("chrome", ChromeDriver.class);
		
		loaderFactory.put("xlsx", XLSTestcaseLoader.class);
		loaderFactory.put("xls", XLSTestcaseLoader.class);
		loaderFactory.put("class", ClassTestcaseLoader.class);

		reportFactory.put("html", HtmlReport.class);
	}

	private TestcaseConfig config;
	private TestcaseLoader testcaseloader;
	private WebDriver driver;
	private ReportGenerator reportGenerator;
	private Scanner in = new Scanner(System.in);

	public TestcaseContext(TestcaseConfig config) {
		this.config = config;
		if (config != null) {
			init();
		}
	}

	private void init() {
		if (config.getFileType() == null) {
			String filepath = config.getFilepath();
			if (filepath != null) {
				int pos = filepath.lastIndexOf(".");
				if (pos != -1) {
					config.setFileType(filepath.substring(pos + 1));
				}
			}
		}

		// TestcaseLoader
		if (config.getFileType() != null) {
			Class<? extends TestcaseLoader> clazz = loaderFactory.get(config.getFileType().toLowerCase());
			if (clazz != null) {
				try {
					this.testcaseloader = clazz.newInstance();
					testcaseloader.setContext(this);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		if (testcaseloader != null) {
			testcaseloader.init();
		}

		// WebDriver
		if (config.getBrowser() != null) {
			Class<? extends WebDriver> clazz = driverFactory.get(config.getBrowser().toLowerCase());
			if (clazz != null) {
				try {
					// EventFiringWebDriver eventDriver = new
					// EventFiringWebDriver(clazz.newInstance());
					// eventDriver.register(new TestcaseDriverListener());
					this.driver = clazz.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		// ReportGenerator
		if (config.getReportType() != null) {
			Class<? extends ReportGenerator> clazz = reportFactory.get(config.getReportType().toLowerCase());
			if (clazz != null) {
				try {
					this.reportGenerator = clazz.newInstance();
					this.reportGenerator.setContext(this);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} else {
			reportGenerator = new DefaultReport();
		}
	}

	public WebElement getPresentElement(By by) {
		try {
			return this.driver.findElement(by);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public void alert(String message) {
		if (this.driver instanceof JavascriptExecutor) {
			// JavascriptExecutor.class.cast(this.driver).executeScript("alert('"
			// + message.replace("'", "\\'") + "')");
		}
	}

	public TestcaseConfig getConfig() {
		return config;
	}

	public void setConfig(TestcaseConfig config) {
		this.config = config;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public ReportGenerator getReportGenerator() {
		return reportGenerator;
	}

	public void setReportGenerator(ReportGenerator reportGenerator) {
		this.reportGenerator = reportGenerator;
	}

	public TestcaseLoader getTestcaseloader() {
		return testcaseloader;
	}

	public void setTestcaseloader(TestcaseLoader testcaseloader) {
		this.testcaseloader = testcaseloader;
	}

	public void run(Testcase testcase) {
		Scenario scenario = testcase.getScenario();
		System.out.println("----------------");
		System.out.println("Start " + testcase.getId() + " scenario " + scenario.getName());
		for (Action action : scenario.getActions()) {
			do {
				if (action.getStatus() != TestStatus.UNTEST) {
					alert(action.getStatus().name() + " " + action
							+ "\r\n\r\nPlease check console for detail information and select next step option.");
					// user input: retry, continue, terminate
					String input = getUserInput(
							action.getStatus().name() + " " + action + " Please select [1) Retry; 2) Ignore;]:", "1",
							"2");
					if ("2".equals(input)) {
						action.setStatus(TestStatus.CONDITION_PASS);
						break;
					}
				}

				TestStatus status = action.execute(this);

				if (status == TestStatus.PASS && action.getStatus() == TestStatus.FAIL) {
					status = TestStatus.CONDITION_PASS;
				}
				action.setStatus(status);

			} while (action.getStatus() == TestStatus.FAIL && action.getFailStrategy() == FailStrategy.PAUSE);
			System.out.println(action.getStatus().name() + " " + action);
		}
		System.out.println("Complete " + testcase.getId() + " scenario " + scenario.getName());
	}

	private String getUserInput(String message, String... options) {
		List<String> opt = Arrays.asList(options);
		String input = null;

		while (!opt.contains(input)) {
			System.out.println(message);
			input = in.nextLine();
		}

		return input;
	}

	public void dispose() {
		if (this.driver != null) {
			driver.close();
		}
		in.close();
	}

}
