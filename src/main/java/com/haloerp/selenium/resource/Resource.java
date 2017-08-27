package com.haloerp.selenium.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public interface Resource {

	URL getURL() throws IOException;

	String getResourceName();

	Class<?> getResourceClass();

	ClassLoader getResourceClassLoader();

	boolean isReadable();

	Resource createRelative(String relativePath) throws MalformedURLException;

	File getFile() throws IOException;

	InputStream getInputStream() throws IOException;

}
