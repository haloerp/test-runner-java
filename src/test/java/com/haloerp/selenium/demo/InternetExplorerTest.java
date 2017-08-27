package com.haloerp.selenium.demo;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class InternetExplorerTest {

	@Before
	public void setup() {
		System.setProperty("webdriver.ie.driver",
				"D:\\dev\\test\\selenium\\IEDriverServer_Win32_3.4.0\\IEDriverServer.exe");
	}

	@Test
	public void test() {
		WebDriver driver = new InternetExplorerDriver();
		JavascriptExecutor exec = (JavascriptExecutor) driver;

		driver.get("http://localhost/selenium/index.html");

		// by id
		WebElement e = driver.findElement(By.id("div1"));
		Assert.assertEquals("div1", e.getText());

		// by class
		e = driver.findElement(By.className("class1"));
		Assert.assertEquals("class1", e.getText());

		// by tagname
		e = driver.findElement(By.tagName("span"));
		Assert.assertEquals("tag", e.getText());

		// by javascript
		e = driver.findElement(By.name("username"));
		e.sendKeys("haloc");
		WebElement o = (WebElement) exec.executeScript("return document.getElementById('div1')");
		Assert.assertEquals("div1", o.getText());

		e = driver.findElement(By.tagName("label"));
		String s = "var labels = arguments[0], inputs=[];" + "for (var i = 0; i < labels.length; i++) {"
				+ "  inputs.push(documents.getElementById(labels[i].getAttribute('for')));" + "}" + "return inputs;";
		List<WebElement> inputs = (List<WebElement>) exec.executeScript(s, e);

		// by linkText
		e = driver.findElement(By.linkText("LinkText"));
		Assert.assertEquals("LinkText", e.getText());
		e = driver.findElement(By.partialLinkText("Link"));
		Assert.assertEquals("LinkText", e.getText());

		// css selector
		// e = driver.findElement(By.cssSelector("#food.span.dairy.aged"));
		// Assert.assertEquals("LinkText", e.getText());

		// select (not recommend)
		WebElement select = driver.findElement(By.tagName("select"));
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement opt : options) {
			System.out.println(opt.getAttribute("value"));
			opt.click();
		}

		// select
		Select sel = new Select(driver.findElement(By.tagName("select")));
		sel.deselectAll();
		sel.selectByVisibleText("Option Text");

		// submit
		driver.findElement(By.id("submit")).click();
		select.submit();

		// window
		driver.switchTo().window("win");
		for (String winname : driver.getWindowHandles()) {
			driver.switchTo().window(winname);
		}
		driver.switchTo().frame("frame.0.child");

		// dialog
		Alert alert = driver.switchTo().alert();

		// navigate
		driver.navigate().to("page.html");
		driver.navigate().forward();
		driver.navigate().back();

		// cookie
		Cookie cookie = new Cookie("key", "value");
		driver.manage().addCookie(cookie);
		Set<Cookie> cookies = driver.manage().getCookies();
		for (Cookie c : cookies) {
			System.out.println(String.format("%s -> %s", c.getName(), c.getValue()));
		}
		driver.manage().deleteCookieNamed("key");
		driver.manage().deleteCookie(cookie);
		driver.manage().deleteAllCookies();
		
		// proxy
		
		// drag
		WebElement source = driver.findElement(By.name("source"));
		WebElement target = driver.findElement(By.name("target"));
		(new Actions(driver)).dragAndDrop(source, target).perform();
		

		// xpath
		By by = By.xpath("//input");

		// wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.quit();
	}

}
