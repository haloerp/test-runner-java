package com.haloerp.selenium.resource;

public interface ProtocolResolver {

	Resource resolve(String resourceName, String location, ClassLoader resourceClassLoader);

}
