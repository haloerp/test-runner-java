package com.haloerp.selenium.resource.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceUtils;

public abstract class AbstractResource implements Resource {

	private String resourceName;
	private ClassLoader resourceClassLoader;
	private Class<?> resourceClass;

	protected AbstractResource(String resourceName, ClassLoader resourceClassLoader) {
		this.resourceName = resourceName;
		this.resourceClassLoader = (resourceClassLoader != null ? resourceClassLoader
				: ResourceUtils.getDefaultClassLoader());
	}

	protected AbstractResource(Class<?> resourceClass) {
		this.resourceClass = resourceClass;
	}

	public boolean isReadable() {
		try {
			URL url = getURL();
			if (ResourceUtils.isFileURL(url)) {
				File file = getFile();
				return (file.canRead() && !file.isDirectory());
			} else {
				return true;
			}
		} catch (IOException ex) {
			return false;
		}
	}

	public Class<?> getResourceClass() {
		if (resourceClass == null && resourceName != null && getResourceClassLoader() != null) {
			try {
				resourceClass = resourceClassLoader.loadClass(resourceName);
			} catch (ClassNotFoundException e) {
			}
		}

		return resourceClass;
	}

	public String getResourceName() {
		if (resourceName == null && resourceClass != null) {
			resourceName = resourceClass.getName();
		}
		return resourceName;
	}

	public ClassLoader getResourceClassLoader() {
		if (resourceClassLoader == null) {
			resourceClassLoader = resourceClass == null ? ResourceUtils.getDefaultClassLoader()
					: resourceClass.getClassLoader();
		}
		return resourceClassLoader;
	}

}
