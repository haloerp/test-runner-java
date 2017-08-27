package com.haloerp.selenium.resource.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceUtils;

public class ClassPathResource extends AbstractResource {

	private final String path;

	public ClassPathResource(String resourceName, String path, ClassLoader classLoader) {
		super(resourceName, classLoader);
		String pathToUse = ResourceUtils.cleanPath(path);
		if (pathToUse.startsWith("/")) {
			pathToUse = pathToUse.substring(1);
		}
		this.path = pathToUse;
	}

	public URL getURL() throws IOException {
		URL url = resolveURL();
		if (url == null) {
			throw new FileNotFoundException(getDescription() + " cannot be resolved to URL because it does not exist");
		}
		return url;
	}

	public Resource createRelative(String relativePath) {
		String pathToUse = ResourceUtils.applyRelativePath(this.path, relativePath);
		String relativeName = relativePath.replace(ResourceUtils.PATH_SEPARATOR, ResourceUtils.PACKAGE_SEPARATOR);
		if (relativeName.endsWith(".class")) {
			relativeName = relativeName.replace(".class", "");
		}
		return new ClassPathResource(getResourceName() + ResourceUtils.PACKAGE_SEPARATOR + relativeName, pathToUse,
				getResourceClassLoader());
	}

	public File getFile() throws IOException {
		URL url = getURL();
		return ResourceUtils.getFile(url, getDescription());
	}

	public String getDescription() {
		StringBuilder builder = new StringBuilder("class path resource [");
		String pathToUse = path;
		if (getResourceClass() != null && !pathToUse.startsWith("/")) {
			builder.append(ResourceUtils.classPackageAsResourcePath(getResourceClass()));
			builder.append('/');
		}
		if (pathToUse.startsWith("/")) {
			pathToUse = pathToUse.substring(1);
		}
		builder.append(pathToUse);
		builder.append(']');
		return builder.toString();
	}

	protected URL resolveURL() {
		if (getResourceClass() != null) {
			return getResourceClass().getResource(this.path);
		} else if (getResourceClassLoader() != null) {
			return getResourceClassLoader().getResource(this.path);
		} else {
			return ClassLoader.getSystemResource(this.path);
		}
	}

	public final String getPath() {
		return this.path;
	}

	public InputStream getInputStream() throws IOException {
		InputStream is;
		if (getResourceClass() != null) {
			is = getResourceClass().getResourceAsStream(this.path);
		} else if (getResourceClassLoader() != null) {
			is = getResourceClassLoader().getResourceAsStream(this.path);
		} else {
			is = ClassLoader.getSystemResourceAsStream(this.path);
		}
		if (is == null) {
			throw new FileNotFoundException(getDescription() + " cannot be opened because it does not exist");
		}
		return is;
	}

}
