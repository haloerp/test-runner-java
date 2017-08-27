package com.haloerp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class TestcaseDriverListener implements WebDriverEventListener {

	public void beforeAlertAccept(WebDriver driver) {
		System.out.println("beforeAlertAccept");
	}

	public void afterAlertAccept(WebDriver driver) {
		System.out.println("afterAlertAccept");

	}

	public void afterAlertDismiss(WebDriver driver) {
		System.out.println("afterAlertDismiss");

	}

	public void beforeAlertDismiss(WebDriver driver) {
		System.out.println("beforeAlertDismiss");

	}

	public void beforeNavigateTo(String url, WebDriver driver) {
		System.out.println("beforeNavigateTo");

	}

	public void afterNavigateTo(String url, WebDriver driver) {
		System.out.println("afterNavigateTo");

	}

	public void beforeNavigateBack(WebDriver driver) {
		System.out.println("beforeNavigateBack");

	}

	public void afterNavigateBack(WebDriver driver) {
		System.out.println("afterNavigateBack");

	}

	public void beforeNavigateForward(WebDriver driver) {
		System.out.println("beforeNavigateForward");

	}

	public void afterNavigateForward(WebDriver driver) {
		System.out.println("afterNavigateForward");

	}

	public void beforeNavigateRefresh(WebDriver driver) {
		System.out.println("beforeNavigateRefresh");

	}

	public void afterNavigateRefresh(WebDriver driver) {
		System.out.println("afterNavigateRefresh");

	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		System.out.println("beforeFindBy");

	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		System.out.println("afterFindBy");

	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		System.out.println("beforeClickOn");

	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		System.out.println("afterClickOn");

	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		System.out.println("beforeChangeValueOf");

	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		System.out.println("afterChangeValueOf");

	}

	public void beforeScript(String script, WebDriver driver) {
		System.out.println("beforeScript");

	}

	public void afterScript(String script, WebDriver driver) {
		System.out.println("afterScript");

	}

	public void onException(Throwable throwable, WebDriver driver) {
		System.out.println("onException");

	}

}
