package com.haloerp.selenium.resource;

public interface PathMatcher {

	boolean isPattern(String substring);

	boolean matchStart(String fullPattern, String string);

	boolean match(String fullPattern, String currPath);

}
